package de.josh.url_shortener.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.josh.url_shortener.url.URLShortenRequest;
import de.josh.url_shortener.gen.ShortCodeGenerator;
import de.josh.url_shortener.url.ShortUrl;
import de.josh.url_shortener.url.UrlRepository;

@RestController
public class ShortUrlController {
    
    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private ShortCodeGenerator shortCodeGenerator;

    @GetMapping("/hello")
    public String hello() {
        return "Hallo!";
    }

    @GetMapping("/test_add")
    public String test_add() {
        urlRepository.save(new ShortUrl("https://www.youtube.com/watch?v=mj2_GU1a7Qs&t=53s", "beet3", LocalDateTime.now(), LocalDateTime.now(), 0));
        return "erfolgreich hinzugefügt";
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody URLShortenRequest longUrl) {
        return longUrl.getLongUrl();
    }

    @GetMapping("/get/{shortCode}")
    public String get(@PathVariable String shortCode) {
        Optional<ShortUrl> sh = urlRepository.findByShortCode(shortCode);
        if(sh.isPresent()) {
            return sh.get().getOriginalUrl();
        }
        return "URL konnte nicht gefunden werden!";
    }
}
