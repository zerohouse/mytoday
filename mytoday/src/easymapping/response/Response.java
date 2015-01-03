package easymapping.response;

import java.io.IOException;

import javax.servlet.ServletException;

import easymapping.mapping.Http;

public interface Response {

	void render(Http http) throws IOException, ServletException;
	
}
