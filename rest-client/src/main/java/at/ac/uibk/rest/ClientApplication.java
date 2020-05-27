package at.ac.uibk.rest;

import org.json.JSONArray;
import tinyb.*;
import java.util.*;

/**
 * Client application receives data from the TimeFlip device,
 * coverts the data in a convenient and human readable format
 * and sends the data to the backend server (in an interval
 * of every 10 minutes)
 */

public class ClientApplication {
    private static final String DEFAULT_MESSAGING_SERVICE_URI = "http://localhost:8080/history";
    private static final float SCALE_LSB = 0.03125f;


    public static void main(String[] args) throws InterruptedException {

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                BluetoothManager manager = BluetoothManager.getBluetoothManager();

                manager.startDiscovery();

                System.out.println("\nDiscovery started...");
                List<BluetoothDevice> sensors = null;
                try {
                    sensors = TimeFlipService.getTimeFlipDevices();
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException");
                }

                try {
                    manager.stopDiscovery();
                } catch (BluetoothException e) {
                    //ignore
                }

                if (sensors == null) {
                    System.err.println("No sensor found with the provided name.");
                    System.exit(-1);
                }

                System.out.println("Found devices");

                JSONArray historyObjects = null;
                try {
                    historyObjects = TimeFlipService.getHistoryObjects(sensors);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException");
                }

                String uri = DEFAULT_MESSAGING_SERVICE_URI;

                if (args.length > 0) {
                    uri = args[0];
                }

                Thread t = new Thread(new Client("admin", "passwd", historyObjects, uri));
                t.start();
            }
        };
        timer.schedule(task, 1, 1000*61);
    }
}


