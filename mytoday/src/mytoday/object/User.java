package mytoday.object;

import java.util.Date;

import easyjdbc.annotation.Column;
import easyjdbc.annotation.Key;
import easyjdbc.annotation.Table;
import easyjdbc.query.QueryExecuter;

@Table("user")
public class User {

	@Key
	private String id;
	@Column(value = "password", valueFormat = "HEX(AES_ENCRYPT(?, ?))")
	private String password;
	private String email;
	private String name;
	private String nickname;
	private String gender;
	private Date timestamp;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", email=" + email + ", name=" + name + ", nickname=" + nickname + ", gender=" + gender
				+ ", timestamp=" + timestamp + "]";
	}

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
		QueryExecuter qe = new QueryExecuter();
		qe.insert(new Type(null, id, "공부", "#F7464A"), new Type(null, id, "일", "#46BFBD"), new Type(null, id, "운동", "#FDB45C"), new Type(null, id,
				"기타", "#949FB1"));
		qe.close();
	}

	public void update(User usermod) {
		String password = usermod.getPassword();
		String email = usermod.getEmail();
		String name = usermod.getName();
		String nickname = usermod.getNickname();
		String gender = usermod.getGender();

		if (password != null)
			this.password = password;
		if (email != null)
			this.email = email;
		if (name != null)
			this.name = name;
		if (nickname != null)
			this.nickname = nickname;
		if (gender != null)
			this.gender = gender;
	}

}
