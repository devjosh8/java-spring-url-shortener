package de.josh.url_shortener.gen;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShortCodeGenerator {
    

    private MessageDigest messageDigest;

    private final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public ShortCodeGenerator() {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public String generateUniqueCode(String url) {
        // Zeitstempel und URL kombinieren
        long timestamp = System.currentTimeMillis();
        String input = url + timestamp;

        // SHA-256-Hash der kombinierten Zeichenkette berechnen
        byte[] hash = hash(input);

        // Hash in Base62 umwandeln und auf 10 Zeichen kürzen
        return toBase62(hash).substring(0, 10);
    }

    private byte[] hash(String input) {
        return messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private String toBase62(byte[] hash) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            int index = b & 0xFF; // Byte zu positivem int konvertieren
            sb.append(BASE62.charAt(index % BASE62.length()));
        }
        return sb.toString();
    }
}
