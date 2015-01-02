package mytoday.controller;

import java.io.IOException;
import java.util.Date;

import mytoday.object.Result;
import mytoday.object.User;
import easyjdbc.query.QueryExecuter;
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
		QueryExecuter qe = new QueryExecuter();
		User user = qe.get(User.class, id);
		qe.close();
		if (null == user)
			return new Json(new Result(true, null));
		return new Json(new Result(false, null));
	}

	@Post("/users/login.my")
	public Response login(Http http) {
		QueryExecuter qe = new QueryExecuter();
		User userpassed = http.getJsonObject(User.class, "user");
		if (userpassed == null)
			return new Json(new Result(false, "허용되지 않은 접근입니다."));
		User fromDB = qe.get(User.class, userpassed.getId());
		qe.close();
		if (fromDB == null)
			return new Json(new Result(false, "없는 사용자 입니다."));
		if (!fromDB.isPasswordCorrect(userpassed))
			return new Json(new Result(false, "패스워드가 틀렸습니다."));
		http.setSessionAttribute("user", fromDB);
		return new Json(new Result(true, null));
	}

	@Get("/users/login.my")
	public Response loginPage(Http http) {
		return new Jsp("login.jsp");
	}

	@Get("/users/modify.my")
	public Response modify(Http http) {
		Jsp jsp = new Jsp("modify.jsp");
		jsp.put("user", http.getSessionAttribute(User.class, "user"));
		return jsp;
	}

	@Post("/users/modify.my")
	public Response modifyId(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		User usermod = http.getJsonObject(User.class, "user");
		String oldPassword = http.getParameter("oldPassword");
		usermod.setPassword(oldPassword);
		if (!user.isPasswordCorrect(usermod))
			return new Json(new Result(false, "기존 패스워드가 일치하지 않습니다."));
		user.update(usermod);
		QueryExecuter qe = new QueryExecuter();
		int effected = qe.update(user);
		qe.close();
		if (effected == 0) {
			return new Json(new Result(false, null));
		}
		return new Json(new Result(true, null));
	}

	@Get("/users/register.my")
	public Response register(Http http) {
		return new Jsp("register.jsp");
	}

	@Post("/users/register.my")
	public Response registerId(Http http) {
		User user = http.getJsonObject(User.class, "user");
		user.setTimestamp(new Date());
		QueryExecuter qe = new QueryExecuter();
		int result = qe.insert(user);
		if (result==0) {
			return new Json(new Result(false, null));
		}
		user.insertDefaultTypes();
		http.setSessionAttribute("user", user);
		return new Json(new Result(true, null));
	}
}
