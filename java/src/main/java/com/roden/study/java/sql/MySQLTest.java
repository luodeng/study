package com.roden.study.java.sql;
import org.junit.Test;

import java.sql.*;

public class MySQLTest {
	private static final String URL = "jdbc:mysql://user.sql.sys.com:3306/user?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true";
	private static final String NAME = "user";
	private static final String PASSWORD = "pwd";

	@Test
	public void mysql5() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(URL, NAME, PASSWORD);
		Statement stmt = conn.createStatement();
		ResultSet resultSet = stmt.executeQuery("select  * from fastdfs_file limit 10");
		while(resultSet.next()){
			System.out.println(resultSet.getString("file_path"));
		}
	}

	@Test
	public void mysql8() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Class.forName("com.mysql.cj.jdbc.Driver");
		conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/spring?serverTimezone=UTC","luodeng","123456");
		ps = conn.prepareStatement("select id,name,create_time from study where name=?;");
		ps.setString(1,"mike");
		rs = ps.executeQuery();
		while(rs.next()) {
			int num = rs.getInt("id");
			String name = rs.getString("name");
			System.out.print(num+"\t"+name);
		}

	}


}