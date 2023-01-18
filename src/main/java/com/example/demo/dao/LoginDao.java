package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class LoginDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public void saveName() {
		String qry= "insert into name (name) values ('shubham');";
		System.out.println("str111"+"==========================");
		jdbcTemplate.execute(qry);
	}
	
	

}
