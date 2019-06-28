package com.gradle.sts4.helloGradle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class HelloGradleController {
	
	@RequestMapping(value= "/")
	public String meetingListView() {
		return "hellogradle";
	}
}