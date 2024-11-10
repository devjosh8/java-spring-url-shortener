package de.josh.url_shortener.gen;

import java.time.Instant;

public class ShortCodeGenerator {
    

    private final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private String convertUrlToShortCode(long id) {

        long time = Instant.now().getEpochSecond() & 0xFFFFFFFF;

        long combined = time ^ id;

        String base62 = encodeBase62(combined);

        return base62.substring(0, Math.min(10, base62.length()));
    }


    private String encodeBase62(long num) {
        StringBuilder b = new StringBuilder();
        while(num > 0) {
            int rest = (int) (num % 62);
            b.append(BASE62.charAt(rest));
            num /= 62;
        }
        return b.reverse().toString();
    }
}
