//$Id$
package com.urlShortner.controller;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urlShortner.service.UrlService;

@Controller
public class UrlShortnerController {

	@Autowired UrlService service;
	
	@GetMapping("/")
    public String home() {
        return "home"; // looks for templates/home.html
    }
	
	@PostMapping("/api/shorten")
	@ResponseBody
	public String shorten(@RequestBody Map<String, String> payload) throws NoSuchAlgorithmException {
		try {
			String longurl = payload.get("originalUrl");
			String code = service.shorten(longurl);
			return code;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/{shortCode}")
	public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

		/*
		 * ResponseEntity used to return a HTTP response with no body, but with a status Code and headers
		 * */
		
		String url = service.getOriginalUrl(shortCode);
		return ResponseEntity.status(302).location(URI.create(url)).build();
	}
}
