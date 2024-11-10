package de.josh.url_shortener.url;

import jakarta.persistence.Entity;

public class URLShortenRequest {
    
    private String longUrl;

    public URLShortenRequest(String longUrl) {
        this.longUrl = longUrl;
    }

    public URLShortenRequest() {}

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}
