package com.wabu.d2project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/news")
public class newsController {
	
	@RequestMapping(value="/{memberID}")
	private String news(@PathVariable String memberID) {
		// �ش� memberID�� ������ ������ �� �ְ� �Ѵ�.
		return "myNews";
	}

}
