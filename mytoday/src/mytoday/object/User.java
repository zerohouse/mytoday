package mytoday.object;

import java.util.Date;

import easyjdbc.annotation.Key;
import easyjdbc.annotation.Table;
import easyjdbc.dao.DBMethods;

@Table("user")
public class User {

	@Key
	private String id;
	private String password;
	private String email;
	private String nickname;
	private String gender;
	private Date timestamp;
	
	public boolean isPasswordCorrect(User userpassed) {
		return password.equals(userpassed.getPassword());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void insertDefaultTypes() {
		DBMethods.insert(new Type(null, id, "공부", "#F7464A"), new Type(null, id, "일", "#46BFBD"), new Type(null, id, "운동", "#FDB45C"), new Type(null, id, "기타", "#949FB1"));
	}

}
