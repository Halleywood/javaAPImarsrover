package com.kelseyhalley.marsroverapiproject.web;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.kelseyhalley.marsroverapiproject.model.HomeDto;
import com.kelseyhalley.marsroverapiproject.response.MarsRoverApiResponse;
import com.kelseyhalley.marsroverapiproject.service.MarsRoverApiService;

@Controller
public class HomeController {
	
	@Autowired
	private MarsRoverApiService roverService; 
	
	@GetMapping("/")
	public String getHomePage(Model model, HomeDto homeDto) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(!StringUtils.hasLength(homeDto.getMarsApiRoverData())) {
			homeDto.setMarsApiRoverData("curiosity");
		}
		if(homeDto.getMarsSol() == null ) {
			homeDto.setMarsSol(1);
		}
		
		MarsRoverApiResponse roverData = roverService.getRoverData(homeDto);
		System.out.println("****" + roverData);
		model.addAttribute("roverData", roverData);
		model.addAttribute("homeDto", homeDto);
		model.addAttribute("validCameras", roverService.getValidCameras());
		return "homepage";
	}

}
