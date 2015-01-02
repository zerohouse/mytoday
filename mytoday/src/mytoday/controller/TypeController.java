package mytoday.controller;

import java.util.List;

import mytoday.object.Result;
import mytoday.object.Schedule;
import mytoday.object.Type;
import mytoday.object.User;
import easyjdbc.query.QueryExecuter;
import easymapping.annotation.Controller;
import easymapping.annotation.Post;
import easymapping.mapping.Http;
import easymapping.response.Json;
import easymapping.response.Response;

@Controller
public class TypeController {

	@Post("/type/getlist.my")
	public Response getTypeList(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null)
			return null;
		QueryExecuter qe = new QueryExecuter();
		List<Type> type = qe.getList(Type.class, "userId=?", user.getId());
		qe.close();
		return new Json(type);
	}

	@Post("/type/insert.my")
	public Response insert(Http http) {
		QueryExecuter qe = new QueryExecuter();
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null)
			return null;
		Type type = http.getJsonObject(Type.class, "type");
		type.setUserId(user.getId());
		String id = qe.insertAndGetPrimaryKey(type).toString();
		qe.close();
		return new Json(new Result(true, id));
	}

	@Post("/type/delete.my")
	public Response delete(Http http) {
		QueryExecuter qe = new QueryExecuter();
		User user = http.getSessionAttribute(User.class, "user");
		int id = Integer.parseInt(http.getParameter("id"));
		if (user == null)
			return null;
		qe.delete(Schedule.class, "type=?", id);
		qe.delete(Type.class, "id=?", id);
		qe.close();
		return new Json(new Result(true, null));
	}

	@Post("/type/update.my")
	public Response update(Http http) {
		QueryExecuter qe = new QueryExecuter();
		User user = http.getSessionAttribute(User.class, "user");
		Type type = http.getJsonObject(Type.class, "type");
		if (user == null)
			return null;
		if (!user.getId().equals(type.getUserId()))
			return null;
		int result = qe.update(type);
		qe.close();
		if (result == 0)
			return new Json(new Result(false, null));
		return new Json(new Result(true, null));
	}

}
