package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class taskController {
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("api/v1/savetaskdata")
	public void signUpDataSave(@RequestBody String str) {
		try {
			jdbcTemplate.execute(str);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("api/v1/getsaveddata")
	ResponseEntity<String> getsaveddata(@RequestBody String str) {
		String response="";
		try {
			List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(str);
			response =new ObjectMapper().writeValueAsString(queryForList);
			System.out.println(response);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("api/v1/deletelist")
	ResponseEntity<String> deleteTask(@RequestBody String str) {
		
		try {
			jdbcTemplate.execute(str);
			System.out.println("delete =====================================");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body("");
		
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("api/v1/updatelist")
	ResponseEntity<String> updatelist(@RequestBody String str) {
		String response="";
		try {
			jdbcTemplate.execute(str);
			System.out.println(response);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body("updated");
		
	}
	

}
