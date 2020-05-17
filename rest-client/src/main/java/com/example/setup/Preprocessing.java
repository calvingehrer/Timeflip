package com.example.setup;

import java.sql.Timestamp;
import java.util.Date;

/*
 * Utility class to convert and format history
 */
public class Preprocessing {

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

    static Timestamp getCurrentTimestamp(){
        Date date = new Date();
        long time = date.getTime();
        Timestamp current = new Timestamp(time);

        return current;
    }
    
}
