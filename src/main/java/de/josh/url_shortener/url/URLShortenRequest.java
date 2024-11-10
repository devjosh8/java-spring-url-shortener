package de.josh.url_shortener.url;

import jakarta.persistence.Entity;

public class URLShortenRequest {
    
    private String url;

    public URLShortenRequest(String longUrl) {
        this.url = longUrl;
    }

    public URLShortenRequest() {}

    public String getUrl() {
        return url;
    }

    public void setUrl(String longUrl) {
        this.url = longUrl;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}
