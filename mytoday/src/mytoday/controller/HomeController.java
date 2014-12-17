package mytoday.controller;

import java.io.IOException;

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
	
	@Get("/test.my")
	public String sss(Http http) throws IOException{
		http.getResp().getWriter().write("ssssss");
		return null;
	}
		
	@Post("/home")
	public String homes(Http http){
		return null;
	}
}
