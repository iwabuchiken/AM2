package am1.items;

public class Memo {

//	int db_id;
	String text;

	long activity_id;
	long group_id;
	
	long created_at;
	long modified_at;
	
	long db_id;
	
	
//	String name, long group_id, long created_at, long modified_at
	
	/****************************************
	 * Constructors
	 ****************************************/
	public Memo(String text, long activity_id, long created_at, long modified_at) {
		
		this.text = text;

		this.activity_id = activity_id;
		
		this.created_at = created_at;
		this.modified_at = modified_at;
		
	}//public FileItem()



	public String getText() {
		return text;
	}


	public long getActivity_id() {
		return activity_id;
	}


	public long getGroup_id() {
		return group_id;
	}


	public long getCreated_at() {
		return created_at;
	}


	public long getModified_at() {
		return modified_at;
	}


	public long getDb_id() {
		return db_id;
	}


	public void setText(String text) {
		this.text = text;
	}


	public void setActivity_id(long activity_id) {
		this.activity_id = activity_id;
	}


	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}


	public void setModified_at(long modified_at) {
		this.modified_at = modified_at;
	}


	public void setDb_id(long db_id) {
		this.db_id = db_id;
	}

}//public class FileItem
