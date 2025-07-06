package com.urlShortner.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.urlShortner.model.UrlMapping;

//$Id$
public interface UrlRepository extends JpaRepository<UrlMapping, Long> { 
	
	/*
	 * extends JpaRepository gives readymade methods like save(entity), findById(id), findAll(), deleteById(id)	
	 * 
	 * Optional<UrlMapping> findByShortCode(String shortCode) :: custom code to get the long URL for the shortcode
	 * */
	
    Optional<UrlMapping> findByShortCode(String shortCode);
    Optional<UrlMapping> findByOriginalUrl(String originalUrl);
}
