package mytoday.mapping;

import java.io.IOException;

import javax.servlet.ServletException;

public interface Response {

	void render(Http http) throws IOException, ServletException;

}
