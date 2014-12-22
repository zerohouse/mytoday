package mytoday.object;

import java.util.Date;

import annotation.Key;
import annotation.Table;
import dao.Record;


@Table("user")
public class User implements Record {

	@Key
	private String id;
	private String password;
	private String email;
	private String nickname;
	private String gender;
	private Date timestamp;

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", email=" + email
				+ ", nickname=" + nickname + ", gender=" + gender
				+ ", timestamp=" + timestamp + "]";
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public String getGender() {
		return gender;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public void set(Object... params) {
		id = params.length < 1 ? null : (String) params[0];
		password = params.length < 2 ? null : (String) params[1];
		email = params.length < 3 ? null : (String) params[2];
		nickname = params.length < 4 ? null : (String) params[3];
		gender = params.length < 5 ? null : (String) params[4];
		timestamp = params.length < 6 ? null : (Date) params[5];
	}

}
