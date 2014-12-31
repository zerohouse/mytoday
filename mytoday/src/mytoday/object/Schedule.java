package mytoday.object;

import java.util.Date;

import easyjdbc.annotation.Key;
import easyjdbc.annotation.Table;
import easymapping.annotation.DateFormat;

@Table("schedule")
public class Schedule {

	@Key
	private int id;
	@DateFormat("yyyy-MM-dd")
	private Date date;
	private String userId;
	private int type;
	private int time;
	
	@Override
	public String toString() {
		return "Schedule [id=" + id + ", date=" + date + ", userId=" + userId + ", type=" + type + ", time=" + time + ", startTime=" + startTime
				+ ", head=" + head + ", body=" + body + "]";
	}

	private int startTime;
	private String head;
	private String body;

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

}
