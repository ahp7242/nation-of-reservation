package com.picaboo.nor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
	@GetMapping({"/", "/index"})
	   public String test() {
	      
	      return "test";
	   }

	@GetMapping("/franchiseeSeatIn")
	   public String franchiseeSeatIn() {
	      
	      return "franchiseeSeatIn";
	   }

}
