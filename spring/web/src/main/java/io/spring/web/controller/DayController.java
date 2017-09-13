package io.spring.web.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.spring.web.dto.Day;

@RestController
public class DayController {

	@GetMapping(value = "/")
	public ResponseEntity<String> version() {
		String body = "1.0";
		HttpStatus status = HttpStatus.OK;
		return new ResponseEntity<String>(body , status );
	}
	
	@GetMapping(value = "/day")
	public @ResponseBody ResponseEntity<Day> day() {
		Day day = new Day();
		day.setDate(new Date());
		return new ResponseEntity<Day>(day, HttpStatus.OK);
	}

}
