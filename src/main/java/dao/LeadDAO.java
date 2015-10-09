package dao;

import java.io.IOException;
import java.util.List;

import model.Lead;

public interface LeadDAO {
	//CRUD operations
	
	    public void save(String name,String info);
	   
	    public Lead getById(int id);
	    
	    public void update(int id,String name,String info)throws IOException;
	   
	    public void deleteById(int id);
	    
	    public List <Lead> getAll();
	    
	  
}
