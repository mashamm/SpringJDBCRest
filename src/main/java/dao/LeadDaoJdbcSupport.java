package dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import model.Lead;

@Component
public class LeadDaoJdbcSupport extends JdbcDaoSupport implements LeadDAO {

	public void save(String name, String info) {
		Lead lead = new Lead();
		lead.setName(name);
		lead.setInfo(info);
		String query = "insert into leads (name, info) values (?,?)";
		Object[] args = new Object[] { lead.getName(), lead.getInfo() };
		int out = getJdbcTemplate().update(query, args);
		if (out != 0) {
			System.out.println("Lead saved with name=" + lead.getName());
		} else
			System.out.println("Lead save failed with id=" + lead.getId());
	}

	public Lead getById(int id) {
		String query = "select id, name, info from leads where id = ?";
		Lead lead = getJdbcTemplate().queryForObject(query, new Object[] { id }, new RowMapper<Lead>() {
			public Lead mapRow(ResultSet rs, int rowNum) throws SQLException {
				Lead lead = new Lead();
				lead.setId(rs.getInt("id"));
				lead.setName(rs.getString("name"));
				lead.setInfo(rs.getString("info"));
				return lead;
			}
		});
		return lead;
	}
	public void update(int id, String name, String info) throws IOException {
		String query = "update leads set name=?, info=? where id=?";
		Lead lead = new Lead();
		lead.setId(id);
		lead.setName(name);
		lead.setInfo(info);
		Object[] args = new Object[] { lead.getName(), lead.getInfo(), lead.getId() };
		int out = getJdbcTemplate().update(query, args);
		if (out != 0) {
			System.out.println("Lead updated with id=" + lead.getId());
		} else
			System.out.println("No lead found with id=" + lead.getId());
	}
	public void deleteById(int id) {
		String query = "delete from leads where id=?";
		int out = getJdbcTemplate().update(query, id);
		if (out != 0) {
			System.out.println("Lead deleted with id=" + id);
		} else
			System.out.println("No lead found with id=" + id);
	}
	public List<Lead> getAll() {
		String query = "select id, name, info from leads";
		List<Lead> leadList = new ArrayList<Lead>();
		List<Map<String, Object>> leadRows = getJdbcTemplate().queryForList(query);
		for (Map<String, Object> leadRow : leadRows) {
			Lead lead = new Lead();
			lead.setId(Integer.parseInt(String.valueOf(leadRow.get("id"))));
			lead.setName(String.valueOf(leadRow.get("name")));
			lead.setInfo(String.valueOf(leadRow.get("info")));
			leadList.add(lead);
		}
		return leadList;
	}

}
