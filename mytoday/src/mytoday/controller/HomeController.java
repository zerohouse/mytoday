package mytoday.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mytoday.annotation.Controller;
import mytoday.annotation.Get;
import mytoday.mapping.Http;
import mytoday.mapping.Json;
import mytoday.mapping.Jsp;
import mytoday.mapping.Response;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@Get("/home.my")
	public Response home(Http http) {
		logger.debug("home");
		return new Jsp("home.jsp");
	}

	@Get("/index.my")
	public Response ss(Http http) {
		return new Jsp("home.jsp");
	}

	@Get("/test.my")
	public Response sss(Http http) throws IOException {
		return new Json("home.jsp");
	}

}
