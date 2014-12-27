package com.tcl.wonder.adclient.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class AdController
{
	public ResponseEntity<String> update()
	{
		
		return new ResponseEntity<String>("update ad success", HttpStatus.OK);
	}
}
