package mytoday.controller;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mytoday.object.User;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.Gson;

import easymapping.mapping.Http;

public class SchduleControllerTest {
	
	Gson gson = new Gson();
	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	Http http = new Http(request, response);
	ScheduleController con = new ScheduleController();
	
	@Test
	public void getListTest() {
		//없는 유저
		User user = new User();
		user.setId("zerohouse");
		Mockito.when(request.getSession().getAttribute("user")).thenReturn(user);
		Mockito.when(request.getParameter("date")).thenReturn("2014-12-30");
		
		System.out.println(con.getList(http).toString());
		assertTrue(true);
	}
	
	

}
