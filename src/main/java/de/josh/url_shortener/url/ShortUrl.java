package de.josh.url_shortener.url;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "shortened_urls")
public class ShortUrl {
     
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shortened_urls_seq")
    @SequenceGenerator(name = "shortened_urls_seq", sequenceName = "shortened_urls_seq", allocationSize = 1)
    private Long id;

    
    private String originalUrl;

    @Column(unique = true)
    private String shortCode;

    private LocalDateTime timeCreated;
    private LocalDateTime timeChanged;

    private int timesUsed;

    public ShortUrl(Long id, String originalUrl, String shortCode, LocalDateTime timeCreated, LocalDateTime timeChanged,
            int timesUsed) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.timeCreated = timeCreated;
        this.timeChanged = timeChanged;
        this.timesUsed = timesUsed;
    }

    public ShortUrl(String originalUrl, String shortCode, LocalDateTime timeCreated, LocalDateTime timeChanged,
            int timesUsed) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.timeCreated = timeCreated;
        this.timeChanged = timeChanged;
        this.timesUsed = timesUsed;
    }



    public ShortUrl() {
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public Long getId() {
        return id;
    }
    
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeChanged() {
        return timeChanged;
    }

    public void setTimeChanged(LocalDateTime timeChanged) {
        this.timeChanged = timeChanged;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}
