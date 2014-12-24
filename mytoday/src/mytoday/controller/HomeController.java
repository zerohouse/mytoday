package mytoday.controller;

import java.io.IOException;

import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.annotation.Post;
import easymapping.mapping.Http;
import easymapping.response.Json;
import easymapping.response.Jsp;
import easymapping.response.Response;




@Controller
public class HomeController {

	@Get("/home.my")
	public Response home() {
		return new Jsp("home.jsp");
	}

	@Get("/index/{}.my")
	public Response index(Http http) throws IOException {
		http.getUriVariable(0); // returns {}value
		http.getReq(); // servlet Request
		http.getReq().getRequestURI();
		http.getResp(); // servlet Response
		http.getResp().getWriter();
		Jsp jsp = new Jsp("home.jsp");
		jsp.put("user", http.getUriVariable(0));
		return jsp;
	}

	@Post("/test.my") //Post requst api
	public Response sss(Http http) throws IOException {
		return new Json("고로니");
	}

}
