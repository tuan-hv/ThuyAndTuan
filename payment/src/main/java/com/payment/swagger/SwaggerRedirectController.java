package com.payment.swagger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
class SwaggerRedirectController {

	@GetMapping(value = "/api/payment")
	public ModelAndView redirect1() {
		return new ModelAndView("redirect:/swagger-ui.html");
	}
}
