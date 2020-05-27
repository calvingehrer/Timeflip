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

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("(" + new Date() + ") Sending...");

                BluetoothManager manager = BluetoothManager.getBluetoothManager();
                manager.startDiscovery();
                
                List<BluetoothDevice> sensors = null;
                try {
                    sensors = TimeFlipService.getTimeFlipDevices();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    manager.stopDiscovery();
                } catch (BluetoothException e) {
                    System.err.println("Discovery could not be stopped.");
                }


                if (sensors == null) {
                    System.exit(-1);
                }

                JSONArray historyObjects = null;
                try {
                    historyObjects = TimeFlipService.getHistoryObjects(sensors);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String uri = DEFAULT_MESSAGING_SERVICE_URI;

                if (args.length > 0) {
                    uri = args[0];
                }

                Thread t = new Thread(new Client("admin", "passwd", historyObjects, uri));
                t.start();
            }
        };

        timer.schedule(task, 1, 1000*60);
    }
}

