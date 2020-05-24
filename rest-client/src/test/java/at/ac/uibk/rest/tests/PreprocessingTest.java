package at.ac.uibk.rest.tests;

import at.ac.uibk.rest.Preprocessing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreprocessingTest {

    @Test
    void testHexToBinary() {
        byte[] bytes = {0x1D, 0x1B, 0x04};
        String binary = Preprocessing.hexToBinary(bytes);
        String expectedResult = "000001000001101100011101";
        assertEquals(binary, expectedResult);
    }

    @Test
    void testGetFacetNumber() {
        String binary = "000001000001101100011101";
        int facet = Preprocessing.getFacetNumber(binary);
        int expectedFacet = 1;
        assertEquals(facet, expectedFacet);
    }

    @Test
    void testGetTimeInSeconds() {
        String binary = "000001000001101100011101";
        int seconds = Preprocessing.getTimeInSeconds(binary);
        int expectedSeconds = 6941;
        assertEquals(seconds, expectedSeconds);
    }
}