package com.codeofus.rent_a_park;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TestUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Convert an object to JSON byte array.
     *
     * @param object the object to convert.
     * @return the JSON byte array.
     * @throws IOException
     */
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }

}
