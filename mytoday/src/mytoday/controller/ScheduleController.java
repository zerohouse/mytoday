package mytoday.controller;

import mytoday.object.Result;
import mytoday.object.Schedule;
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
public class ScheduleController {

	@Post("/schedule/insert.my")
	public Response insert(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		Schedule schedule = http.getJsonObject(Schedule.class, "schedule");
		if (!user.getId().equals(schedule.getUserId())) {
			return new Json(new Result(false, "잘못된 접근입니다."));
		}
		DBMethods.insert(schedule);
		return new Json(new Result(true, null));
	}

	@Post("/schedule/getlist.my")
	public Response getList(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null)
			return null;
		return new Json(DBMethods.getList(Schedule.class, "userId=? and date=?", user.getId(), http.getParameter("date")));
	}

	@Get("/mytoday.my")
	public Response index(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null) {
			http.sendRedirect("/");
			return null;
		}
		return new Jsp("schedule.jsp");
	}

}
