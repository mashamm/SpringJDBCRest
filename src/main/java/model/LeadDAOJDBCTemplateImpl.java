package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
 
import javax.sql.DataSource;
 
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;



public class LeadDAOJDBCTemplateImpl implements LeadDAO {
   

	private DataSource dataSource;
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
	
	public void save(Lead lead) {
		String query = "insert into leads (name, info) values (?,?)";
        
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
         
        Object[] args = new Object[] {lead.getName(), lead.getInfo()};
        
        int out = jdbcTemplate.update(query, args);
         
        if(out !=0){
            System.out.println("Lead saved with name="+lead.getName());
        }else System.out.println("Lead save failed with id="+lead.getId());
		
	}

	
	public Lead getById(int id) {
		 String query = "select id, name, info from leads where id = ?";
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	         
	        //using RowMapper anonymous class
	        
	        Lead lead = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<Lead>(){
	 
	            
	            public Lead mapRow(ResultSet rs, int rowNum)
	                    throws SQLException {
	                Lead lead = new Lead();
	                lead.setId(rs.getInt("id"));
	                lead.setName(rs.getString("name"));
	                lead.setInfo(rs.getString("info"));
	                return lead;
	            }});
	         
	        return lead;
	}

	
	public void update(Lead lead) {
		 String query = "update leads set name=?, info=? where id=?";
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	        Object[] args = new Object[] {lead.getName(), lead.getInfo(), lead.getId()};
	         
	        int out = jdbcTemplate.update(query, args);
	        if(out !=0){
	            System.out.println("Lead updated with id="+lead.getId());
	        }else System.out.println("No lead found with id="+lead.getId());
	}

	public void deleteById(int id) {
		String query = "delete from leads where id=?";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
         
        int out = jdbcTemplate.update(query, id);
        if(out !=0){
            System.out.println("Lead deleted with id="+id);
        }else System.out.println("No lead found with id="+id);
		
	}

	public List<Lead> getAll() {
		  String query = "select id, name, info from leads";
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	        List<Lead> leadList = new ArrayList<Lead>();
	 
	        List<Map<String,Object>> leadRows = jdbcTemplate.queryForList(query);
	         
	        for(Map<String,Object> leadRow : leadRows){
	            Lead lead = new Lead();
	            lead.setId(Integer.parseInt(String.valueOf(leadRow.get("id"))));
	            lead.setName(String.valueOf(leadRow.get("name")));
	            lead.setInfo(String.valueOf(leadRow.get("info")));
	            leadList.add(lead);
	        }
	        return leadList;
	}

}
