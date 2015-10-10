	package model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Lead {

	@NotNull
	@Min(1)
	private Integer id;
	@NotNull
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
	
}
