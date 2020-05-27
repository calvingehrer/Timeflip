package at.ac.uibk.rest.tests;

import at.ac.uibk.rest.Converter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    @Test
    void testHexToBinary() {
        byte[] bytes = {0x1D, 0x1B, 0x04};
        String binary = Converter.hexToBinary(bytes);
        String expectedResult = "000001000001101100011101";
        assertEquals(binary, expectedResult);
    }

    @Test
    void testGetFacetNumber() {
        String binary = "000001000001101100011101";
        int facet = Converter.getFacetNumber(binary);
        int expectedFacet = 1;
        assertEquals(facet, expectedFacet);
    }

    @Test
    void testGetTimeInSeconds() {
        String binary = "000001000001101100011101";
        int seconds = Converter.getTimeInSeconds(binary);
        int expectedSeconds = 6941;
        assertEquals(seconds, expectedSeconds);
    }
}