package mytoday.controller;


import mytoday.object.Result;
import mytoday.object.Type;
import mytoday.object.User;
import easyjdbc.dao.DBMethods;
import easymapping.annotation.Controller;
import easymapping.annotation.Post;
import easymapping.mapping.Http;
import easymapping.response.Json;
import easymapping.response.Response;

@Controller
public class TypeController {

	@Post("/type/getlist.my")
	public Response getTypeList(Http http){
		User user = http.getSessionAttribute(User.class, "user");
		if(user==null)
			return null;
		return new Json(DBMethods.getList(Type.class, "userId=?", user.getId()));
	}
	
	@Post("/type/insert.my")
	public Response insert(Http http){
		User user = http.getSessionAttribute(User.class, "user");
		if(user==null)
			return null;
		Type type = http.getJsonObject(Type.class, "type");
		type.setUserId(user.getId());
		String id = DBMethods.insertAndGetPrimaryKey(type).toString();
		return new Json(new Result(true, id));
	}
	
}
