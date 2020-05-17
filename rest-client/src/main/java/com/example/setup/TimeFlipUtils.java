package com.example.setup;

import org.json.JSONArray;
import org.json.JSONObject;
import tinyb.BluetoothDevice;
import tinyb.BluetoothGattCharacteristic;
import tinyb.BluetoothGattService;
import tinyb.BluetoothManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TimeFlipUtils {
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
    static List<BluetoothDevice> getTimeFlipDevices() throws InterruptedException {
        BluetoothManager manager = BluetoothManager.getBluetoothManager();
        List <BluetoothDevice> sensors = new ArrayList<>();
        for (int i = 0; (i < 15) && running; ++i) {
            List<BluetoothDevice> list = manager.getDevices();
            if (list == null)
                return null;

            for (BluetoothDevice device : list) {
                printDevice(device);

                if (device.getName().toLowerCase().contains("timeflip"))
                    sensors.add(device);
            }

            if (!sensors.isEmpty()) {
                return sensors;
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
    
    public static JSONArray getHistoryObjects(List<BluetoothDevice> sensors) throws InterruptedException {
        JSONArray historyEntries = new JSONArray();
        List<HistoryEntry> entries = new ArrayList<>();
        
        for(BluetoothDevice sensor : sensors) {
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

            while (running) {
                byte[] historyRaw = commandResult.readValue();

                // finish reading when first package full of zeros appears
                if (historyRaw[2] == 0x00) {
                    break;
                }

                /*
                 * separate package into blocks (each block for a facet time period) and convert
                 * raw history data to human readable history blocks with format: [facet, time]
                 */
                byte[][] historyRawFormatted = new byte[7][3];
                int index = 0;

                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 3; j++) {
                        historyRawFormatted[i][j] = historyRaw[index++];
                    }
                    HistoryEntry entry = new HistoryEntry();
                    entry.setMacAddress(sensor.getAddress());
                    entry.setFacet(Preprocessing.getFacetNumber(Preprocessing.hexToBinary(historyRawFormatted[i])));
                    entry.setSeconds(Preprocessing.getTimeInSeconds(Preprocessing.hexToBinary(historyRawFormatted[i])));

                    if (entry.getFacet() != 0) {
                        entries.add(entry);
                    }
                }
            }

            List<HistoryEntry> updatedEntries = Preprocessing.calculateStartEndTimes(entries, Preprocessing.getCurrentTimestamp());

            for(HistoryEntry entry : entries){
                historyEntries.put(entry);
            }

            // write command 0X02 to delete history
            byte[] deleteHistory = {0x02};
            command.writeValue(deleteHistory);
            
            sensor.disconnect();
        }
        
        return historyEntries;
    }
    
}
