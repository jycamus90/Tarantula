package org.spideruci.tarantula.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {
	protected Connection c = null;

	public DatabaseUtil(Connection c){
		this.c = c;
	}
	
	protected void execute(String query){
		try {
			Statement s = c.createStatement();
			s.execute(query);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	protected void executePsmt(String sql, Object... args) {
		try{
			PreparedStatement psmt = c.prepareStatement(sql);
			for(int i=0; i<args.length; ++ i){
				psmt.setObject(i+1, args[i]);
			}
			psmt.executeUpdate();
			psmt.close();
		}catch(Exception e){
			System.out.println(sql);
			e.printStackTrace();
		}
	}

}
