package dao;

import java.io.IOException;
import java.util.List;


import model.LeadNotFoundException;
import model.lead;

public interface leadDao {
	
	    public int create(final String name,final String info) throws InsertException;
	   
	    public lead get(int id);
	    
	    public int update(int id,String name,String info)throws LeadNotFoundException;
	   
	    public void delete(int id) throws LeadNotFoundException;
	    
	    public List <lead> getAll();  
	  
}
