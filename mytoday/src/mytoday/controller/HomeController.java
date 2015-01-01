package mytoday.controller;

import mytoday.object.User;
import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.mapping.Http;

@Controller
public class HomeController {
	
	@Get("/index.my")
	public void home(Http http){
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null){
			http.sendRedirect("/users/login.my");
			return;
			}
		http.sendRedirect("/mytoday.my");
	}
}