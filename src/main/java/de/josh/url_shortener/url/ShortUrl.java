package de.josh.url_shortener.url;

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

    private String original_url;
}
