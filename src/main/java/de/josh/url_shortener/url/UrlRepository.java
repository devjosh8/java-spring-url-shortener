package de.josh.url_shortener.url;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends CrudRepository<ShortUrl, Long> {
    
    Optional<ShortUrl> findByShortCode(String shortCode);

    Optional<ShortUrl> findById(Long id);
}
