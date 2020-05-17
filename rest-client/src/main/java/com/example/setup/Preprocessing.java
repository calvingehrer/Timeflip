package com.example.setup;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/*
 * Utility class to convert and format history
 */
public class Preprocessing {

    /*
     * Converts an array of bytes of size 3 to a 24 bit binary string
     */
    public static String hexToBinary(byte[] bytes){
        StringBuilder binary = new StringBuilder();
        for(int i = bytes.length - 1; i >= 0; i--){
            binary.append(String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0'));
        }
        return binary.toString();
    }

    public static int getFacetNumber(String binary){
        String facetBinary = binary.substring(0, 6);
        return Integer.parseInt(facetBinary, 2);
    }

    public static int getTimeInSeconds(String binary){
        String timeBinary = binary.substring(6);
        return Integer.parseInt(timeBinary, 2);
    }

    public static Timestamp getCurrentTimestamp(){
        Date date = new Date();
        long time = date.getTime();
        Timestamp current = new Timestamp(time);

        return current;
    }

    public static List<HistoryEntry> calculateStartEndTimes(List<HistoryEntry> historyEntries, Date current){
        Collections.reverse(historyEntries);
        Date end = current;

        for(HistoryEntry entry : historyEntries){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            calendar.add(Calendar.SECOND, entry.getSeconds() * -1);
            Date start = calendar.getTime();

            entry.setStart(start);
            entry.setEnd(end);

            end = start;
        }

        return historyEntries;
    }
    
}
