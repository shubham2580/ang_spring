package com.example.demo.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.demo.dao.LoginDao;

@RestController
public class LoginController {
	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Value("${file.upload.base-path}" )
	private String basePath;
	
	@GetMapping("/save")
	public void saveData() {
		loginDao.saveName();
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("api/v1/signUpDataSave")
	public void signUpDataSave(@RequestBody String str) {
		try {
			jdbcTemplate.execute(str);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("api/v1/loginCheck")
	public int loginCheck(@RequestBody String str) {
		try {
			List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(str);
			System.out.println(queryForList);
			if(queryForList.size()>0) {
				return 1;
			}else {
				return 0;
			}
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return 1;
		
	}
	
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("api/v1/getCategory")
	ResponseEntity<String> getCategory(@RequestBody String str) {
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
	@PostMapping("api/v1/loginData")
	ResponseEntity<String> login(@RequestBody String str) {
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
	@PostMapping("api/v1/uploadDocument")
	public  ResponseEntity<String> uploadDocument(@RequestParam("files") MultipartFile file,
			@RequestParam("document_type") String document_type,@RequestParam("documentName") String documentName,@RequestParam("pkId") String pkId) throws Exception {
		System.out.println(document_type +" "+  documentName+" " + pkId);
		HashMap<String, String> map = new HashMap<>();
		try {
			String relativePath=File.separator.concat(file.getOriginalFilename());		
			File uploadedFile=new File(basePath.concat(relativePath));
			uploadedFile.mkdirs();
			file.transferTo(uploadedFile);
			String strQuery="insert into Z_ENTITYDTL_CORPORATE_DOCUMENTS_001 (DOCUMENT_TYPE,DOCUMENT_NAME,DOCUMENT,DOCUMENT_PATH,CORP_ID,is_add,created_date) values('"+document_type+"','"+file.getOriginalFilename()+"','"+documentName+"','"+basePath+"','"+pkId+"','new',current_timestamp)";
			int resQueueData = jdbcTemplate.update(strQuery);
			if(resQueueData !=0) {	
			map.put("filePath", basePath);
			map.put("filename", file.getOriginalFilename());
			return ResponseEntity.status(HttpStatus.OK).body(new ObjectMapper().writeValueAsString(map));
			}else {
				return ResponseEntity.badRequest().body("{\"status\": \"error\", \"errorcode\": \"400\",\"errormessage\":\"the request sent by the client was syntactically incorrect\"}");
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().body("{\"status\": \"error\", \"errorcode\": \"400\",\"errormessage\":\"the request sent by the client was syntactically incorrect\"}");
		}
	}
	
	
	
	
	
}
