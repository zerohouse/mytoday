package mytoday.controller;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mytoday.object.Result;
import mytoday.object.User;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.Gson;

import easymapping.mapping.Http;
import easymapping.response.Json;

public class UserControllerTest {
	
	Gson gson = new Gson();
	HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
	HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	Http http = new Http(request, response);
	UserController usercon = new UserController();
	
	@Test
	public void loginTest() {
		//없는 유저
		User user = new User();
		user.set("zaertz", "sss");
		Mockito.when(request.getParameter("user")).thenReturn(gson.toJson(user));
		
		Json json = (Json) usercon.login(http);
		Result result = (Result) json.getJsonObj();
		assertFalse(result.isSuccess());
		
		//패스워드 틀릴떄
		user.set("zz", "ssdfss");
		Mockito.when(request.getParameter("user")).thenReturn(gson.toJson(user));
		
		json = (Json) usercon.login(http);
		result = (Result) json.getJsonObj();
		assertFalse(result.isSuccess());
		
		//성공
		user.set("zz", "ss");
		Mockito.when(request.getParameter("user")).thenReturn(gson.toJson(user));
		
		json = (Json) usercon.login(http);
		result = (Result) json.getJsonObj();
		assertTrue(result.isSuccess());
	}
	
	

}
