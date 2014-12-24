package mytoday.controller;

import mytoday.object.Result;
import mytoday.object.User;
import easyjdbc.dao.DBMethods;
import easymapping.annotation.Controller;
import easymapping.annotation.Post;
import easymapping.mapping.Http;
import easymapping.response.Json;
import easymapping.response.Response;

@Controller
public class UserController {

	@Post("/users/login")
	public Response Login(Http http) {
		User userpassed = http.getJsonObject(User.class, "user");
		User fromDB = DBMethods.get(User.class, userpassed.getId());
		if (fromDB == null)
			return new Json(new Result(false, "없는 사용자 입니다."));
		if (!fromDB.isPasswordCorrect(userpassed))
			return new Json(new Result(false, "패스워드가 틀렸습니다."));
		return new Json(new Result(true, null));
	}

}
