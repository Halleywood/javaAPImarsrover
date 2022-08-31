package com.kelseyhalley.marsroverapiproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.kelseyhalley.marsroverapiproject.response.MarsRoverApiResponse;

@SpringBootTest
class MarsroverapiprojectApplicationTests {

	@Test
	void contextLoads() {
		
		RestTemplate rt = new RestTemplate();
		
		ResponseEntity<String> response = rt.getForEntity("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=2&api_key=1Ka7x2gjUzzTlLcRuXgnrFooof6kDnIpjdIakePO", String.class);
		System.out.println(response.getBody());
	}

}
