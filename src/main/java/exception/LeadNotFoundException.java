package exception;

public class LeadNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -2859292084648724403L;
	private final long Id;
	
	public LeadNotFoundException(long id) {
		Id = id;
	}
	
	public long getLeadId() {
		return Id;
	}
	

}
