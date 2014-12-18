package mytoday.object;

import java.util.Date;
import java.util.List;

import mytoday.Util;
import mytoday.annotation.DefaultCondition;
import mytoday.annotation.PrimaryKey;
import mytoday.annotation.TableName;
import mytoday.dao.DBMethods;

@TableName("contents")
@DefaultCondition("order by id limit 0,10")
public class Content {

	@PrimaryKey
	private Integer id;
	private String type;
	private String userid;
	private String head;
	private String content;
	private Date timestamp;

	public Content(Integer id) {
		this.id = id;
	}

	public Content(Integer id, String type, String userid, String head,
			String content, Date timestamp) {
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
		this.timestamp = Util.parseDate(initargs.get(5), "yyyy-MM-dd HH:mm:ss");
	}

	@Override
	public String toString() {
		return "Content [id=" + id + ", type=" + type + ", userid=" + userid
				+ ", head=" + head + ", content=" + content + ", timestamp="
				+ timestamp + "]";
	}
	
	public boolean insert(){
		return DBMethods.insert(this);
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
