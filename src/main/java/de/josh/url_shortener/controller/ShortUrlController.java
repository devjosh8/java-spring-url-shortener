package de.josh.url_shortener.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.josh.url_shortener.url.URLShortenRequest;
import de.josh.url_shortener.gen.ShortCodeGenerator;
import de.josh.url_shortener.url.ShortUrl;
import de.josh.url_shortener.url.UrlRepository;

@RestController
public class ShortUrlController {
    
    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private ObjectMapper objectMapper;
    
    private ShortCodeGenerator shortCodeGenerator;

    public ShortUrlController() {
        shortCodeGenerator = new ShortCodeGenerator();
    }

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
    public ObjectNode shortenUrl(@RequestBody URLShortenRequest longUrl) {
        String short_code = shortCodeGenerator.generateUniqueCode(longUrl.getLongUrl());
        Optional<ShortUrl> short_url = urlRepository.findByShortCode(short_code);

        if(short_url.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL could not be shortened. Try again at a later time.");
        }

        ShortUrl new_short_url = new ShortUrl(longUrl.getLongUrl(), short_code, LocalDateTime.now(), LocalDateTime.now(), 0);

        urlRepository.save(new_short_url);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("id", new_short_url.getId());
        objectNode.put("url", new_short_url.getOriginalUrl());
        objectNode.put("shortCode", new_short_url.getShortCode());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        objectNode.put("createdAt", new_short_url.getTimeCreated().format(dtf));
        objectNode.put("updatedAt", new_short_url.getTimeChanged().format(dtf));

        return objectNode;
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
