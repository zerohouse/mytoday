package mytoday.controller;

import mytoday.object.DateHeader;
import mytoday.object.Result;
import mytoday.object.User;
import easyjdbc.query.QueryExecuter;
import easymapping.annotation.Controller;
import easymapping.annotation.Post;
import easymapping.mapping.Http;
import easymapping.response.Json;
import easymapping.response.Response;

@Controller
public class DateHeaderController {
	
	@Post("/dateheader/get.my")
	public Response get(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null)
			return null;
		QueryExecuter qe = new QueryExecuter();
		DateHeader dateheader = qe.get(DateHeader.class, http.getParameter("date"),  user.getId());
		qe.close();
		return new Json(dateheader);
	}
	

	@Post("/dateheader/update.my")
	public Response update(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null)
			return null;
		QueryExecuter qe = new QueryExecuter();
		DateHeader dateheader = http.getJsonObject(DateHeader.class, "dateheader");
		dateheader.setUserId(user.getId());
		qe.insert(dateheader);
		qe.close();
		return new Json(new Result(true, null));
	}
}
