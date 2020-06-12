package com.servicesecutity.swagger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
class SwaggerRedirectController {

	@RequestMapping(value = "/api/security", method = RequestMethod.GET)
	public ModelAndView redirect1() {
		return new ModelAndView("redirect:/swagger-ui.html");
	}
}
