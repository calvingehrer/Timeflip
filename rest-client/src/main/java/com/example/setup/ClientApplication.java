package com.example.setup;

import org.json.JSONArray;
import org.json.JSONObject;
import tinyb.*;

import java.util.*;


/*
 * Client application
 * - receives data from the TimeFlip device
 * - coverts the data in a convenient and human readable format [facet, time]
 * - sends the data to the backend server
 */

public class ClientApplication {
    private static final String DEFAULT_MESSAGING_SERVICE_URI = "http://localhost:8080/history";
    private static final float SCALE_LSB = 0.03125f;


    public static void main(String[] args) throws InterruptedException {

        BluetoothManager manager = BluetoothManager.getBluetoothManager();

        boolean discoveryStarted = manager.startDiscovery();

        System.out.println("The discovery started: " + (discoveryStarted ? "true" : "false"));
        List<BluetoothDevice> sensors = TimeFlipUtils.getTimeFlipDevices();

        try {
            manager.stopDiscovery();
        } catch (BluetoothException e) {
            System.err.println("Discovery could not be stopped.");
        }

        if (sensors == null) {
            System.err.println("No sensor found with the provided name.");
            System.exit(-1);
        }

        System.out.println("Found devices ... ");

        JSONArray historyObjects = TimeFlipUtils.getHistoryObjects(sensors);

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

        Thread t = new Thread(new Client(username, psswd, historyObjects, uri));
        t.start();

    }
}

