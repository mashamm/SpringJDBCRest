package dao;

import java.util.List;


import model.LeadNotFoundException;
import model.lead;

public interface leadDao {
	
	    public Long create(final String name,final String info) throws InsertException;
	   
	    public lead get(Long id);
	    
	    public Long update(Long id,String name,String info)throws LeadNotFoundException;
	   
	    public Long delete(Long id) throws LeadNotFoundException;
	    
	    public List <lead> getAll();  
	  
}
