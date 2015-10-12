package model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class lead {

	
	private int id;
	@NotNull
	@Size(min=3,max=10)
	private String name;
	
	private String info;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	@Override
	public String toString(){
		return String.format("[%d - %s - %s ]",id, name, info);
		
	}
	 @Override
	    public boolean equals(Object o) {
		 if(o == null)                return false;
		    if(!(o instanceof lead)) return false;

		    lead other = (lead) o;
		    if(this.id != other.id)      return false;
		    if(! this.name.equals(other.name)) return false;
		    if(! this.info.equals(other.info))   return false;
		    return true;
		  }
	 @Override
	 	public int hashCode(){
		 	return (int) id*name.hashCode()*info.hashCode();
		 
	 }
	         
	    }
	

