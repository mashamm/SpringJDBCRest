package dao;

import java.io.IOException;
import java.util.List;

import model.Lead;
import model.LeadNotFoundException;

public interface LeadDAO {
	
	    public int create(final String name,final String info);
	   
	    public Lead get(int id);
	    
	    public int update(int id,String name,String info)throws LeadNotFoundException;
	   
	    public void delete(int id) throws LeadNotFoundException;
	    
	    public List <Lead> getAll();
	    
	  
}
