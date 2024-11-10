package de.josh.url_shortener.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @PostMapping("/shorten")
    @ResponseStatus( HttpStatus.OK )
    public ObjectNode shortenUrl(@RequestBody URLShortenRequest url) {
        String short_code = shortCodeGenerator.generateUniqueCode(url.getUrl());
        Optional<ShortUrl> short_url = urlRepository.findByShortCode(short_code);

        if(short_url.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "URL could not be shortened. Try again at a later time.");
        }

        ShortUrl new_short_url = new ShortUrl(url.getUrl(), short_code, LocalDateTime.now(), LocalDateTime.now(), 0);

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

    @GetMapping("/shorten/{shortCode}")
    @ResponseStatus( HttpStatus.OK )
    public ObjectNode getUrlFromShortCode(@PathVariable String shortCode) {
        Optional<ShortUrl> optional_short_url = urlRepository.findByShortCode(shortCode);
        if(optional_short_url.isPresent()) {
            ShortUrl new_short_url = optional_short_url.get();

            new_short_url.setTimesUsed(new_short_url.getTimesUsed() + 1);

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
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL could not be fetched. Short Code does not exist.");
    }

    @DeleteMapping("/shorten/{shortCode}")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void deleteUrl(@PathVariable String shortCode) {
        Optional<ShortUrl> optional_short_url = urlRepository.findByShortCode(shortCode);
        if(optional_short_url.isPresent()) {
            urlRepository.deleteById(optional_short_url.get().getId());
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL could not be deleted. Short Code does not exist.");
    }

    @PutMapping("/shorten/{shortCode}")
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    public ResponseEntity<?> updateUrl(@PathVariable String shortCode, @RequestBody URLShortenRequest url) {
        Optional<ShortUrl> optional_short_url = urlRepository.findByShortCode(shortCode);
        if(optional_short_url.isPresent()) {
            ShortUrl shortUrl = optional_short_url.get();
            shortUrl.setOriginalUrl(url.getUrl());
            shortUrl.setTimeChanged(LocalDateTime.now());
            urlRepository.save(shortUrl);

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("id", shortUrl.getId());
            objectNode.put("url", shortUrl.getOriginalUrl());
            objectNode.put("shortCode", shortUrl.getShortCode());

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            objectNode.put("createdAt", shortUrl.getTimeCreated().format(dtf));
            objectNode.put("updatedAt", shortUrl.getTimeChanged().format(dtf));

            return ResponseEntity.ok().body(objectNode);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL could not be updated. Short Code does not exist.");
    }

    @GetMapping("/shorten/{shortCode}/stats")
    public ResponseEntity<?> getUrlStats(@PathVariable String shortCode) {
        Optional<ShortUrl> optional_short_url = urlRepository.findByShortCode(shortCode);
        if(optional_short_url.isPresent()) {
            ShortUrl shortUrl = optional_short_url.get();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("id", shortUrl.getId());
            objectNode.put("url", shortUrl.getOriginalUrl());
            objectNode.put("shortCode", shortUrl.getShortCode());

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            objectNode.put("createdAt", shortUrl.getTimeCreated().format(dtf));
            objectNode.put("updatedAt", shortUrl.getTimeChanged().format(dtf));

            objectNode.put("accessCount", shortUrl.getTimesUsed());

            return ResponseEntity.ok().body(objectNode);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found. Short Code does not exist.");
    }
}
