package am1.items;

public class ActivityItem {

//	int db_id;
	String name;

	long group_id;
	
	long created_at;
	long modified_at;
	long finished_at;
	
	long db_id;
	
	
//	String name, long group_id, long created_at, long modified_at
	
	/****************************************
	 * Constructors
	 ****************************************/
	public ActivityItem(String name, long group_id, long created_at, long modified_at) {
		
		this.name = name;

		this.group_id = group_id;
		this.created_at = created_at;
		this.modified_at = modified_at;
		
		
	}//public FileItem()

	public ActivityItem(String name, long group_id, 
										long created_at, long modified_at, long db_id) {
		
		this.name = name;

		this.group_id = group_id;
		
		this.created_at = created_at;
		this.modified_at = modified_at;
		
		this.db_id = db_id;
		
	}//public FileItem()

	public String getName() {
		return name;
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

	public long getFinished_at() {
		return finished_at;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setFinished_at(long finished_at) {
		this.finished_at = finished_at;
	}

}//public class FileItem
