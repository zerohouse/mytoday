package mytoday.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.mapping.Http;
import easymapping.mapping.Json;
import easymapping.mapping.Jsp;
import easymapping.mapping.Response;


@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Get("/home.my")
	public Response home(Http http) {
		logger.debug("home");
		return new Jsp("home.jsp");
	}

	@Get("/index/{}.my")
	public Response ss(Http http) {
		System.out.println(http.getUriVariable(0));
		return new Jsp("home.jsp");
	}

	@Get("/test.my")
	public Response sss(Http http) throws IOException {
		
		return new Json("고로니");
	}

}
