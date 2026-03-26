package io.student.rangiffler.utils;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@UtilityClass
public class PhotoDecoder {

    public byte[] decodeDataUriBase64(String src) {
        if (src == null || src.isBlank()) {
            return null;
        }

        int comma = src.indexOf(',');
        var base64 = (comma >= 0) ? src.substring(comma + 1) : src;

        return Base64.getDecoder().decode(base64);
    }

    public String encodeDataUriBase64(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }

        return "data:image/png;base64," + new String(bytes, StandardCharsets.UTF_8);
    }
}

