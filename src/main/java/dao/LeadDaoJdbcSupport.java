package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import model.lead;
import model.LeadNotFoundException;

@Component
@Repository
public class LeadDaoJdbcSupport extends JdbcDaoSupport implements leadDao {
	
	public static final String INSERT="insert into leads (name, info) values (?,?)";
	public static final String UPDATE="update leads set name=?, info=? where id=?";
	public static final String GETBYID="select id, name, info from leads where id = ?";
	public static final String DELETE="delete from leads where id=?";
	public static final String GETALL="select id, name, info from leads";
	
	public int create(final String name,final String info) throws InsertException {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		 int out=getJdbcTemplate().update(new PreparedStatementCreator() {  
		        public PreparedStatement createPreparedStatement(Connection con)  
		            throws SQLException {  
		        PreparedStatement ps = con.prepareStatement(INSERT,  
		            new String[] { "id" });  
		        ps.setString(1, name);  
		        ps.setString(2, info);  
		        return ps;  
		        }  
		    }, keyHolder); 
		 
		if (out > 0) 
			 return  keyHolder.getKey().intValue();
		else{
		throw new InsertException();}
	}

	
	public lead get(int id) {
		lead lead = getJdbcTemplate().queryForObject(GETBYID, new Object[] { id },
		new RowMapper<lead>() {
		public lead mapRow(ResultSet rs, int rowNum) throws SQLException {
				lead lead = new lead();
				lead.setId(rs.getInt("id"));
				lead.setName(rs.getString("name"));
				lead.setInfo(rs.getString("info"));
				return lead;}});
		return lead;
		}
	
	
	public int update(int id, String name, String info) throws LeadNotFoundException {
		Object[] args = new Object[] { name, info, id };
		int out = getJdbcTemplate().update(UPDATE, args);
		if (out > 0) 
			return id;
		 else
			 throw new LeadNotFoundException("There is no lead with id="+id);
		}
	
	
	public void delete (int id) throws LeadNotFoundException {
		int out = getJdbcTemplate().update(DELETE, id);
		if (out<0) 
			throw new LeadNotFoundException("Lead with id="+id +"wasn't deleted");		
	}
	
	
	public List<lead> getAll() {
		List<lead> leadList = new ArrayList<lead>();
		List<Map<String, Object>> leadRows = getJdbcTemplate().queryForList(GETALL);
		for (Map<String, Object> leadRow : leadRows) {
			lead lead = new lead();
			lead.setId(Integer.parseInt(String.valueOf(leadRow.get("id"))));
			lead.setName(String.valueOf(leadRow.get("name")));
			lead.setInfo(String.valueOf(leadRow.get("info")));
			leadList.add(lead);
		}
		return leadList;
	}

}
