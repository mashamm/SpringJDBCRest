package dao;

import java.util.List;
import exception.InsertException;
import exception.LeadNotFoundException;
import model.Lead;

public interface leadDao {
	
	    public Long create(final String name,final String info) throws InsertException;
	   
	    public Lead get(Long id);
	    
	    public Long update(Long id,String name,String info)throws LeadNotFoundException;
	   
	    public Long delete(Long id) throws LeadNotFoundException;
	    
	    public List <Lead> getAll();  
	  
}
