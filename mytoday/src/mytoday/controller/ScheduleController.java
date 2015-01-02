package mytoday.controller;

import java.util.List;

import mytoday.object.Result;
import mytoday.object.Schedule;
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
public class ScheduleController {

	@Post("/schedule/insert.my")
	public Response insert(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		Schedule schedule = http.getJsonObject(Schedule.class, "schedule");
		if (!user.getId().equals(schedule.getUserId())) {
			return new Json(new Result(false, "잘못된 접근입니다."));
		}
		QueryExecuter qe = new QueryExecuter();
		qe.insert(schedule);
		qe.close();
		return new Json(new Result(true, null));
	}

	@Post("/schedule/getlist.my")
	public Response getList(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null)
			return null;
		QueryExecuter qe = new QueryExecuter();
		List<Schedule> schedulelist = qe.getList(Schedule.class, "userId=? and date=?", user.getId(), http.getParameter("date"));
		qe.close();
		return new Json(schedulelist);
	}

	@Post("/schedule/update.my")
	public Response update(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null)
			return null;
		QueryExecuter qe = new QueryExecuter();
		Schedule schedule = http.getJsonObject(Schedule.class, "schedule");
		int effected = qe.update(schedule);
		qe.close();
		if (effected == 0)
			return new Json(new Result(false, "sqlError"));
		return new Json(new Result(true, null));
	}

	@Post("/schedule/delete.my")
	public Response delete(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null)
			return null;
		QueryExecuter qe = new QueryExecuter();
		boolean deleted = qe.delete(Schedule.class, "userId=? and id=?", user.getId(), http.getParameter("id"));
		qe.close();
		if (!deleted)
			return new Json(new Result(false, "sqlError"));
		return new Json(new Result(true, null));
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

	@Get("/myweek.my")
	public Response myweek(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null) {
			http.sendRedirect("/");
			return null;
		}
		return new Jsp("scheduletable.jsp");
	}

	@Post("/schedule/getbetween.my")
	public Response between(Http http) {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null) {
			return null;
		}
		
		QueryExecuter qe = new QueryExecuter();
		List<Schedule> schedulelist = qe.getList(Schedule.class, "userId=? and `date` BETWEEN ? AND ?", user.getId(), http.getParameter("dateFrom"),
				http.getParameter("dateTo"));
		qe.close();
		Json json = new Json(schedulelist);
		json.setDateformat("yyyy-MM-dd");
		return json;
	}
}
