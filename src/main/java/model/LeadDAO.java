package model;

import java.util.List;

public interface LeadDAO {
	//CRUD operations
	     
	    //Create
	    public void save(Lead lead);
	    //Read
	    public Lead getById(int id);
	    //Update
	    public void update(Lead lead);
	    //Delete
	    public void deleteById(int id);
	    //Get All
	    public List <Lead> getAll();;
}
