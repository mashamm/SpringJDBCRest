package dao;

public class Constants {
	public static final String INSERT="insert into leads (name, info) values (?,?)";
	public static final String UPDATE="update leads set name=?, info=? where id=?";
	public static final String GETBYID="select id, name, info from leads where id = ?";
	public static final String DELETE="delete from leads where id=?";
	public static final String GETALL="select id, name, info from leads";
	public static final int INSERT_FAILED = -1;
}
