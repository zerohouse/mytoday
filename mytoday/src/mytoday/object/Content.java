package mytoday.object;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mytoday.annotation.Primarykey;
import mytoday.annotation.TableInfo;
import mytoday.dao.Record;

@TableInfo(tableName = "contents", dateFormat = "yyyy-MM-dd HH:mm:ss")
public class Content extends Record {

	@Primarykey
	private Integer id;
	private String type;
	private String userid;
	private String head;
	private String content;
	private Date timestamp;

	public Content(Integer id) {
		super();
		this.id = id;
	}

	public Content(Integer id, String type, String userid, String head,
			String content, Date timestamp) {
		super();
		this.id = id;
		this.type = type;
		this.userid = userid;
		this.head = head;
		this.content = content;
		this.timestamp = timestamp;
	}
	
	public Content(List<Object> initargs){
		this.id = (Integer) initargs.get(0);
		this.type = (String) initargs.get(1);
		this.userid = (String) initargs.get(2);
		this.head = (String) initargs.get(3);
		this.content = (String) initargs.get(4);
		this.timestamp = parseDate(initargs.get(5));
	}

	@Override
	public String toString() {
		return "Content [id=" + id + ", type=" + type + ", userid=" + userid
				+ ", head=" + head + ", content=" + content + ", timestamp="
				+ timestamp + "]";
	}

	@Override
	public boolean setByList(ArrayList<Object> user) {
		try {
			this.id = (Integer) user.get(0);
			this.type = (String) user.get(1);
			this.userid = (String) user.get(2);
			this.head = (String) user.get(3);
			this.content = (String) user.get(4);
			this.timestamp = parseDate(user.get(5));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Integer getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getUserid() {
		return userid;
	}

	public String getHead() {
		return head;
	}

	public String getContent() {
		return content;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
