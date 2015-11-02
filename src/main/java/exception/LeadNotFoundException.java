package exception;

public class LeadNotFoundException extends Exception {
	private  long Id;
	private String message;
	
	public LeadNotFoundException(long id) {
		Id = id;
	}
	
	public long getLeadId() {
		return Id;
	}
	
	public LeadNotFoundException(){}
	
	public LeadNotFoundException(long id,String message) {
		this.Id = id;
		this.message=message;
		
	}
	public String getMessage(){
		return this.message;
	}
}
