package mytoday.controller;

import java.io.IOException;
import java.util.Date;

import mytoday.object.Result;
import mytoday.object.User;
import easyjdbc.dao.DBMethods;
import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.annotation.Post;
import easymapping.mapping.Http;
import easymapping.response.Json;
import easymapping.response.Jsp;
import easymapping.response.Response;

@Controller
public class UserController {

	@Get("/users/logout.my")
	public void logout(Http http) throws IOException {
		http.removeSessionAttribute("user");
		http.sendRedirect("/");
	}

	@Post("/users/checkid.my")
	public Response checkId(Http http) {
		String id = http.getParameter("id");
		if (null == DBMethods.get(User.class, id))
			return new Json(new Result(true, null));
		return new Json(new Result(false, null));
	}

	@Post("/users/login.my")
	public Response login(Http http) {
		User userpassed = http.getJsonObject(User.class, "user");
		if (userpassed == null)
			return new Json(new Result(false, "허용되지 않은 접근입니다."));
		User fromDB = DBMethods.get(User.class, userpassed.getId());
		if (fromDB == null)
			return new Json(new Result(false, "없는 사용자 입니다."));
		if (!fromDB.isPasswordCorrect(userpassed))
			return new Json(new Result(false, "패스워드가 틀렸습니다."));
		http.setSessionAttribute("user", fromDB);
		return new Json(new Result(true, null));
	}

	@Get("/users/login.my")
	public Response loginPage(Http http) {
		return new Jsp("schedule.jsp");
	}

	@Get("/users/register.my")
	public Response register(Http http) {
		return new Jsp("register.jsp");
	}

	@Post("/users/register.my")
	public Response registerId(Http http) {
		User user = http.getJsonObject(User.class, "user");
		user.setTimestamp(new Date());
		if (DBMethods.insert(user)) {
			user.insertDefaultTypes();
			return new Json(new Result(true, null));
		}
		return new Json(new Result(false, null));
	}
}
