//$Id$
package com.urlShortner.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.urlShortner.model.UrlMapping;
import com.urlShortner.repository.UrlRepository;

@Service
public class UrlService {

	@Autowired UrlRepository repo;
	//use the annotation to import & use that repo class here
	
	public String shorten(String longUrl) throws NoSuchAlgorithmException {
		
		if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
	        longUrl = "https://" + longUrl;
	    }
		
		// Check if original URL already exists — reuse short code if so
		Optional<UrlMapping> existing = repo.findByOriginalUrl(longUrl);
	    if (existing.isPresent()) {
	        return existing.get().getShortCode();
	    }
		
	    // Otherwise, generate a unique short code
		String code;
		do {
		    code = generateShortCode(longUrl); // UUID, SHA-256, etc.
		} while (repo.findByShortCode(code).isPresent());

		// Save new mapping
		UrlMapping url = new UrlMapping();
		url.setOriginalUrl(longUrl);
		url.setShortCode(code);
		repo.save(url);
		return url.getShortCode();
	}
	
	public String getOriginalUrl(String shortCode) {
		return repo.findByShortCode(shortCode)
				.map(UrlMapping::getOriginalUrl)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Short code not found"));
	}
	
	public static String generateShortCode(String longUrl) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(longUrl.getBytes());
            String shortC = Base64.getUrlEncoder().encodeToString(hash).substring(0, 6);
            System.out.println(shortC);
            return shortC;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
