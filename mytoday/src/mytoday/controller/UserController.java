package mytoday.controller;

import mytoday.object.Result;
import mytoday.object.User;
import easyjdbc.dao.DBMethods;
import easymapping.annotation.Controller;
import easymapping.annotation.Get;
import easymapping.mapping.Http;
import easymapping.response.Json;
import easymapping.response.Response;

@Controller
public class UserController {

	@Get("/users/login.my")
	public Response login(Http http) {
		User userpassed = http.getJsonObject(User.class, "user");
		if(userpassed ==null)
			return new Json(new Result(false, "허용되지 않은 접근입니다."));
		User fromDB = DBMethods.get(User.class, userpassed.getId());
		if (fromDB == null)
			return new Json(new Result(false, "없는 사용자 입니다."));
		if (!fromDB.isPasswordCorrect(userpassed))
			return new Json(new Result(false, "패스워드가 틀렸습니다."));
		return new Json(new Result(true, null));
	}
}
