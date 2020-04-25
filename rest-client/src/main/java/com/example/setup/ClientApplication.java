package com.example.setup;

import org.json.JSONArray;
import tinyb.*;
import java.util.*;
import java.util.concurrent.locks.*;


/*
 * Client application
 * - receives data from the TimeFlip device
 * - coverts the data in a convenient and human readable format [facet, time]
 * - sends the data to the backend server
 */

public class ClientApplication {
    private static final String DEFAULT_MESSAGING_SERVICE_URI = "http://localhost:8080/messages";
    private static final float SCALE_LSB = 0.03125f;
    static boolean running = true;

    static void printDevice(BluetoothDevice device) {
        System.out.print("Address = " + device.getAddress());
        System.out.print(" Name = " + device.getName());
        System.out.print(" Connected = " + device.getConnected());
        System.out.println();
    }

    /*
     * After discovery is started, new devices will be detected. We can get a list of all devices through the manager's
     * getDevices method. We can the look through the list of devices to find the device with the substring "timeflip" in its name.
     * We continue looking until we find it, or we try 15 times (1 minutes).
     */
    static BluetoothDevice getDevice() throws InterruptedException {
        BluetoothManager manager = BluetoothManager.getBluetoothManager();
        BluetoothDevice sensor = null;
        for (int i = 0; (i < 15) && running; ++i) {
            List<BluetoothDevice> list = manager.getDevices();
            if (list == null)
                return null;

            for (BluetoothDevice device : list) {
                printDevice(device);
                /*
                 * Here we check if the address matches.
                 */
                if (device.getName().toLowerCase().contains("timeflip"))
                    sensor = device;
            }

            if (sensor != null) {
                return sensor;
            }
            Thread.sleep(4000);
        }
        return null;
    }

    static BluetoothGattService getService(BluetoothDevice device, String UUID) throws InterruptedException {
        System.out.println("Services exposed by device:");
        BluetoothGattService tempService = null;
        List<BluetoothGattService> bluetoothServices = null;
        do
        {
            bluetoothServices = device.getServices();
            if (bluetoothServices == null)
                return null;

            for (BluetoothGattService service : bluetoothServices) {
                System.out.println("UUID: " + service.getUUID());
                if (service.getUUID().equals(UUID))
                    tempService = service;
            }
            Thread.sleep(4000);
        } while (bluetoothServices.isEmpty() && running);
        return tempService;
    }

    static BluetoothGattCharacteristic getCharacteristic(BluetoothGattService service, String UUID) {
        List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
        if (characteristics == null)
            return null;

        for (BluetoothGattCharacteristic characteristic : characteristics) {
            if (characteristic.getUUID().equals(UUID))
                return characteristic;
        }
        return null;
    }

    /*
     * Converts an array of bytes of size 3 to a 24 bit binary string
     */
    static String hexToBinary(byte[] bytes){
        StringBuilder binary = new StringBuilder();
        for(int i = bytes.length - 1; i >= 0; i--){
            binary.append(String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0'));
        }
        return binary.toString();
    }

    static int getFacetNumber(String binary){
        String facetBinary = binary.substring(0, 6);
        return Integer.parseInt(facetBinary, 2);
    }

    static int getTimeInSeconds(String binary){
        String timeBinary = binary.substring(6);
        return Integer.parseInt(timeBinary, 2);
    }


    public static void main(String[] args) throws InterruptedException {

        /*
         * To start looking of the device, we first must initialize the TinyB library. The way of interacting with the
         * library is through the BluetoothManager. There can be only one BluetoothManager at one time, and the
         * reference to it is obtained through the getBluetoothManager method.
         */
        BluetoothManager manager = BluetoothManager.getBluetoothManager();

        /*
         * The manager will try to initialize a BluetoothAdapter if any adapter is present in the system. To initialize
         * discovery we can call startDiscovery, which will put the default adapter in discovery mode.
         */
        boolean discoveryStarted = manager.startDiscovery();

        System.out.println("The discovery started: " + (discoveryStarted ? "true" : "false"));
        BluetoothDevice sensor = getDevice();

        /*
         * After we find the device we can stop looking for other devices.
         */
        try {
            manager.stopDiscovery();
        } catch (BluetoothException e) {
            System.err.println("Discovery could not be stopped.");
        }

        if (sensor == null) {
            System.err.println("No sensor found with the provided name.");
            System.exit(-1);
        }

        System.out.print("Found device: ");
        printDevice(sensor);

        if (sensor.connect())
            System.out.println("Sensor with the provided name connected");
        else {
            System.out.println("Could not connect device.");
            System.exit(-1);
        }

        Lock lock = new ReentrantLock();
        Condition cv = lock.newCondition();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                running = false;
                lock.lock();
                try {
                    cv.signalAll();
                } finally {
                    lock.unlock();
                }

            }
        });


        BluetoothGattService timeflipService = getService(sensor, "f1196f50-71a4-11e6-bdf4-0800200c9a66");

        if (timeflipService == null) {
            System.err.println("This device does not have the timeflip service we are looking for.");
            sensor.disconnect();
            System.exit(-1);
        }
        System.out.println("Found service " + timeflipService.getUUID());

        BluetoothGattCharacteristic facet = getCharacteristic(timeflipService, "f1196f52-71a4-11e6-bdf4-0800200c9a66");
        BluetoothGattCharacteristic password = getCharacteristic(timeflipService, "f1196f57-71a4-11e6-bdf4-0800200c9a66");
        BluetoothGattCharacteristic command = getCharacteristic(timeflipService, "f1196f54-71a4-11e6-bdf4-0800200c9a66");
        BluetoothGattCharacteristic commandResult = getCharacteristic(timeflipService, "f1196f53-71a4-11e6-bdf4-0800200c9a66");


        if (facet == null || password == null || command == null || commandResult == null) {
            System.err.println("Could not find the correct characteristics.");
            sensor.disconnect();
            System.exit(-1);
        }

        System.out.println("Found TimeFlip characteristics");


        // before getting output of TimeFlip characteristics, a password must be send to the cube (just once and at every re-connect)
        byte[] passwd = {0x30, 0x30, 0x30, 0x30, 0x30, 0x30};
        password.writeValue(passwd);

        // write command 0X01 to receive history data
        byte[] history = {0x01};
        command.writeValue(history);

        JSONArray historyList = new JSONArray();

        while (running) {
            byte[] historyRaw = commandResult.readValue();

            // finish reading when first package full of zeros appears
            if(historyRaw[2] == 0x00){
                break;
            }

            /*
             * separate package into blocks (each block for a facet time period) and convert
             * raw history data to human readable history blocks with format: [facet, time]
             */
            byte[][] historyRawFormatted = new byte[7][3];
            int[][] historyConverted = new int[7][2];
            int index = 0;

            for(int i = 0; i < 7; i++) {
                for (int j = 0; j < 3; j++) {
                    historyRawFormatted[i][j] = historyRaw[index++];
                }
                historyConverted[i][0] = getFacetNumber(hexToBinary(historyRawFormatted[i]));
                historyConverted[i][1] = getTimeInSeconds(hexToBinary(historyRawFormatted[i]));

                if (historyConverted[i][0] != 0) {
                    historyList.put(historyConverted[i]);
                }
            }
        }

        sensor.disconnect();


        /*
         * Connect to the given URI and send Data to the Server using the RestClient
         */

        if (args.length < 2) {
            System.out.println("No username and/or password provided, exiting ...");
            System.exit(1);
        }
        String username = args[0];
        String psswd = args[1];
        String uri = DEFAULT_MESSAGING_SERVICE_URI;

        if (args.length > 2) {
            uri = args[2];
        }

        Thread t = new Thread(new RestClient(username, psswd, historyList, uri));
        t.start();


    }
}

