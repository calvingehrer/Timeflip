package at.ac.uibk.rest;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Utility class to convert and format history
 */
public class Converter {

    /**
     * Converts an array of bytes of size 3 to a 24 bit binary String
     *
     * @param bytes the array of bytes to be converted
     * @return the converted binary sequence as String
     */
    public static String hexToBinary(byte[] bytes){
        StringBuilder binary = new StringBuilder();
        for(int i = bytes.length - 1; i >= 0; i--){
            binary.append(String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(' ', '0'));
        }
        return binary.toString();
    }


    /**
     * Reads the first six characters of a binary String and
     * converts them to an int representing the facet number
     *
     * @param binary the binary String to be converted
     * @return the facet number as an int
     */
    public static int getFacetNumber(String binary){
        String facetBinary = binary.substring(0, 6);
        return Integer.parseInt(facetBinary, 2);
    }


    /**
     * Reads the last 18 characters of a binary String and
     * converts them to an int representing the time in seconds
     * of a facet
     *
     * @param binary the binary String to be converted
     * @return the time in seconds as an int
     */
    public static int getTimeInSeconds(String binary){
        String timeBinary = binary.substring(6);
        return Integer.parseInt(timeBinary, 2);
    }

    /**
     * Converts the battery status from a binary String
     * to an Integer
     * 
     * @param binary
     * @return the battery status as Integer
     */
    public static int getBatteryState(String binary){
        return Integer.parseInt(binary, 2);
    }
    
    
    /**
     * Creates timestamp of current time
     *
     * @return Timestamp
     */
    public static Timestamp getCurrentTimestamp(){
        Date date = new Date();
        long time = date.getTime();
        return new Timestamp(time);
    }


    /**
     * Calculates the start and end times of all history entries based on
     * the read time and the start and end times of the previous entries
     *
     * @param historyEntries the list of history entries to be modified
     * @param current the time the data was read from the timeflip device
     */
    public static void calculateStartEndTimes(List<HistoryEntry> historyEntries, Date current){
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
    }
    
}
