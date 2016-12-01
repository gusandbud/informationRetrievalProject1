package Analysis;

import java.sql.Date;

public class Status {
	
	long id;
	String userName, text;
	Date dt;
	String hashtag;
	
	public Status(){
		
	}

	public Status(long id, String userName, String text, Date dt, String hashtag) {
		super();
		this.id = id;
		this.userName = userName;
		this.text = text;
		this.dt = dt;
		this.hashtag = hashtag;
	}
	
	
}
