package com.matrimony.matrimony_backend.util;

import java.util.Base64;

public class Base64Utils {

    public static byte[] decode(String base64String) {
        if (base64String == null) return null;
        
        // Remove data:image/...;base64, prefix if present
        if (base64String.contains(",")) {
            base64String = base64String.split(",")[1];
        }
        
        return Base64.getDecoder().decode(base64String);
    }

    public static String encode(byte[] data) {
        if (data == null) return null;
        return Base64.getEncoder().encodeToString(data);
    }

    public static long getFileSizeInKb(String base64String) {
        byte[] decodedBytes = decode(base64String);
        return decodedBytes != null ? decodedBytes.length / 1024 : 0;
    }

    public static String getMimeType(String base64String) {
        if (base64String != null && base64String.contains("data:") && base64String.contains(";base64")) {
            return base64String.substring(base64String.indexOf("data:") + 5, base64String.indexOf(";base64"));
        }
        return "image/jpeg"; // Default
    }
}
