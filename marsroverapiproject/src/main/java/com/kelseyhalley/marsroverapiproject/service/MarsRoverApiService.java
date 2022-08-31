package com.kelseyhalley.marsroverapiproject.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.kelseyhalley.marsroverapiproject.model.HomeDto;
import com.kelseyhalley.marsroverapiproject.response.MarsPhoto;
import com.kelseyhalley.marsroverapiproject.response.MarsRoverApiResponse;

@Service
public class MarsRoverApiService {

	// we have a constructor that puts the rovers and its possible cameras into a
	// HASHMAP on instantiation.
	// because these are static variables that dont change, ok to use with
	// singleton.
	public MarsRoverApiService() {
		getValidCameras().put("opportunity", Arrays.asList("FHAZ", "RHAZ", "NAVCAM", "PANCAM", "MINITES"));
		getValidCameras().put("spirit", Arrays.asList("FHAZ", "RHAZ", "NAVCAM", "PANCAM", "MINITES"));
		getValidCameras().put("curiosity", Arrays.asList("FHAZ", "RHAZ", "MAST", "CHEMCAM", "MAHLI", "MARDI", "NAVCAM"));
	}

	// fills this hash when the roverService is instantiated.
	private Map<String, List<String>> validCameras = new HashMap<>();

	public List<String> getUrlEndPoints(HomeDto homeDto) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		List<String> urls = new ArrayList<>();
		Method[] methods = homeDto.getClass().getMethods(); 
		
		for (Method method : methods) {
			if(method.getName().indexOf("getCamera") > -1 && Boolean.TRUE.equals(method.invoke(homeDto))){
				String cameraName = method.getName().split("getCamera")[1].toUpperCase();
				if(getValidCameras().get(homeDto.getMarsApiRoverData()).contains(cameraName)) {
					urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/"+homeDto.getMarsApiRoverData() +"/photos?sol="+homeDto.getMarsSol()+"&api_key=1Ka7x2gjUzzTlLcRuXgnrFooof6kDnIpjdIakePO" +"&camera=" + cameraName);
				}
			}
		}
		return urls; 
	}

	public MarsRoverApiResponse getRoverData(HomeDto homeDto) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		RestTemplate rt = new RestTemplate();

		List<String> apiUrlEndpoints =  getUrlEndPoints(homeDto); 
		List<MarsPhoto> photos = new ArrayList<>(); 
		MarsRoverApiResponse response = new MarsRoverApiResponse(); 
		
		apiUrlEndpoints.stream()
			.forEach(url ->{
				MarsRoverApiResponse apiResponse = rt.getForObject(url,  MarsRoverApiResponse.class);
				photos.addAll(apiResponse.getPhotos());
			});
		response.setPhotos(photos);
		return response;
	}

	public Map<String, List<String>> getValidCameras() {
		return validCameras;
	}

	public void setValidCameras(Map<String, List<String>> validCameras) {
		this.validCameras = validCameras;
	}
}
