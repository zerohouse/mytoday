package mytoday.controller;

import mytoday.Http;
import mytoday.annotation.Controller;
import mytoday.annotation.Get;
import mytoday.annotation.Post;

@Controller
public class HomeController {

	@Get("/home.my")
	public String home(Http http){
		return "home.jsp";
	}
	
	@Get("/index.my")
	public String ss(Http http){
		return "index.jsp";
	}
	

	
	
	@Post("/home")
	public String homes(Http http){
		return null;
	}
}
