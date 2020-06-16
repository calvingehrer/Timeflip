package at.ac.uibk.rest;

import org.json.JSONArray;
import sun.management.Sensor;
import tinyb.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Service class containing all the utility methods to receive data from TimeFlip devices
 */
public class TimeFlipService {
    static boolean running = true;

    /**
     * Detects all bluetooth devices with "timeflip" (case insensitive) in its name.
     *
     * @return list of all detected TimeFlip devices
     * @throws InterruptedException
     */
    static List<BluetoothDevice> getTimeFlipDevices() throws InterruptedException {
        BluetoothManager manager = BluetoothManager.getBluetoothManager();
        List <BluetoothDevice> sensors = new ArrayList<>();
        for (int i = 0; (i < 15) && running; ++i) {
            List<BluetoothDevice> list = manager.getDevices();
            if (list == null)
                return null;

            for (BluetoothDevice device : list) {

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



    /**
     * Reads the list of services of a given bluetooth device and searches the needed service.
     *
     * @param device the device where to look for the service
     * @param UUID the UUID of the requested service
     * @return the requested service, Null if service does not exist
     * @throws InterruptedException
     */
    static BluetoothGattService getService(BluetoothDevice device, String UUID) throws InterruptedException {
        BluetoothGattService tempService = null;
        List<BluetoothGattService> bluetoothServices = null;
        do
        {
            bluetoothServices = device.getServices();
            if (bluetoothServices == null)
                return null;

            for (BluetoothGattService service : bluetoothServices) {
                if (service.getUUID().equals(UUID))
                    tempService = service;
            }
            Thread.sleep(4000);
        } while (bluetoothServices.isEmpty() && running);
        return tempService;
    }



    /**
     * Reads the list of characteristics of a given service and searches the needed characteristics.
     *
     * @param service the service where to look for the characteristics
     * @param UUID the UUID of the requested characteristics
     * @return the requested characteristics, null if characteristic does not exist
     */
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

    
    /**
     * Detects all TimeFlip devices, reads services and characteristics and converts the output
     * into the needed format. After that the formatted HistoryEntry object gets added to the list
     * of HistoryEntries which gets converted to a JSONArray.
     *
     * @param sensors all detected TimeFlip devices
     * @return list of all received and converted HistoryEntries as JSONArray
     * @throws InterruptedException
     */
    public static JSONArray getHistoryObjects(List<BluetoothDevice> sensors) throws InterruptedException {
        JSONArray historyEntries = new JSONArray();
        List<HistoryEntry> entries = new ArrayList<>();

        for(BluetoothDevice sensor : sensors) {

            try{
                if (sensor.connect())
                    System.out.println("Sensor " + sensor.getAddress() + " connected");
                else {
                    System.out.println("Could not connect device with address " + sensor.getAddress());
                    continue;
                }
            }catch (Exception e){
                System.out.println("Connection abort - skip to next sensor if exists");
                continue;
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
            BluetoothGattService batteryService = getService(sensor, "0000180f-0000-1000-8000-00805f9b34fb");

            if (timeflipService == null || batteryService == null) {
                sensor.disconnect();
                System.exit(-1);
            }

            BluetoothGattCharacteristic facet = getCharacteristic(timeflipService, "f1196f52-71a4-11e6-bdf4-0800200c9a66");
            BluetoothGattCharacteristic password = getCharacteristic(timeflipService, "f1196f57-71a4-11e6-bdf4-0800200c9a66");
            BluetoothGattCharacteristic command = getCharacteristic(timeflipService, "f1196f54-71a4-11e6-bdf4-0800200c9a66");
            BluetoothGattCharacteristic commandResult = getCharacteristic(timeflipService, "f1196f53-71a4-11e6-bdf4-0800200c9a66");
            BluetoothGattCharacteristic battery = getCharacteristic(batteryService, "00002a19-0000-1000-8000-00805f9b34fb");



            if (facet == null || password == null || command == null || commandResult == null || battery == null) {
                System.err.println("Could not find the correct characteristics.");
                sensor.disconnect();
                System.exit(-1);
            }


            // before getting output of TimeFlip characteristics, a password must be send to the cube (just once and at every re-connect)
            byte[] passwd = {0x30, 0x30, 0x30, 0x30, 0x30, 0x30};
            password.writeValue(passwd);

            // write command 0X01 to receive history data
            byte[] history = {0x01};
            command.writeValue(history);

            // get battery status
            byte[] batteryRaw = battery.readValue();

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
                    entry.setFacet(Converter.getFacetNumber(Converter.hexToBinary(historyRawFormatted[i])));
                    entry.setSeconds(Converter.getTimeInSeconds(Converter.hexToBinary(historyRawFormatted[i])));
                    entry.setBattery(Integer.parseInt(Converter.hexToBinary(batteryRaw), 2));

                    if (entry.getFacet() != 0) {
                        entries.add(entry);
                    }
                }
            }

            Converter.calculateStartEndTimes(entries, Converter.getCurrentTimestamp());

            for(HistoryEntry entry : entries){
                historyEntries.put(entry);
            }

            entries.clear();

            // write command 0X02 to delete history
            byte[] deleteHistory = {0x02};
            command.writeValue(deleteHistory);

            sensor.disconnect();
        }

        return historyEntries;
    }

}
