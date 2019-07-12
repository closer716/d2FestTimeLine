package com.wabu.d2project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/news")
public class newsController {
	
	@RequestMapping(value="/{memberID}")
	private String news(@PathVariable String memberID) {
		// 해당 memberID의 뉴스를 보여줄 수 있게 한다.
		return "myNews";
	}

}
