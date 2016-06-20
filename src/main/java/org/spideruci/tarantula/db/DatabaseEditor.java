package org.spideruci.tarantula.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseEditor extends DatabaseUtil{


	public DatabaseEditor(Connection c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	public void createSuspiciousnessTable(){
		String projectTable="CREATE TABLE IF NOT EXISTS `SUSPICIOUS` ( "
				+ "`STMT_ID`		INTEGER,"
				+ "`SUSPICIOUS`	    DOUBLE,"
				+ "PRIMARY KEY(STMT_ID)"
				+ ");";

		execute(projectTable);
	}

	public void insertSuspiciousness(int stmtId, double s){
		System.out.println("Insert statement " + stmtId);
		String sql = "INSERT INTO SUSPICIOUS "
				+"VALUES(?,?)";
		executePsmt(sql, stmtId, s);
	}

	public void createConfidenceTable(){
		String projectTable="CREATE TABLE IF NOT EXISTS `CONFIDENCE` ( "
				+ "`STMT_ID`		INTEGER,"
				+ "`CONFIDENCE`	    DOUBLE,"
				+ "PRIMARY KEY(STMT_ID)"
				+ ");";

		execute(projectTable);
	}

	public void insertConfidence(int stmtId, double c){
		System.out.println("Insert statement " + stmtId);
		String sql = "INSERT INTO CONFIDENCE "
				+"VALUES(?,?)";
		executePsmt(sql, stmtId, c);
	}

	
	
	public void createSTMTTable(){

		String STMT = "CREATE TABLE IF NOT EXISTS `STMT` ( "
				+ "`STMT_ID`  INTEGER,"
				+ "`SOURCE_ID` 	 INTEGER,"
				+ "`LINE_NUM` 	 INTEGER,"
				+ "PRIMARY KEY(STMT_ID)"
				+ ");";

		execute(STMT);

		int sid = 0;

		try {
			Statement s = c.createStatement();

			String sql = "SELECT DISTINCT SOURCE_ID, LINE_NUM FROM STMT_COVERAGE";
			ResultSet rs = s.executeQuery(sql);

			while(rs.next()){
				int sourceId = rs.getInt("SOURCE_ID");
				int lineNum = rs.getInt("LINE_NUM");

				insertSTMT(sid, sourceId, lineNum);

				sid++;
			}

			s.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("STMT table created");

	}

	public void insertSTMT(int id, int sourceId, int lineNum){
		String sql = "INSERT INTO STMT "
				+"VALUES(?,?,?)";
		executePsmt(sql, id, sourceId, lineNum);
	}

	public void createSTMT_ID_CoverageTable(){

		String STMT = "CREATE TABLE IF NOT EXISTS `STMT_ID_COVERAGE` ( "
				+ "`TEST_ID`  INTEGER,"
				+ "`STMT_ID`  INTEGER,"
				+ "PRIMARY KEY(TEST_ID, STMT_ID)"
				+ ");";

		execute(STMT);

		try {
			Statement s1 = c.createStatement();
			Statement s2 = c.createStatement();

			String sql1 = "SELECT * FROM STMT_COVERAGE";
			ResultSet rs1 = s1.executeQuery(sql1);


			while(rs1.next()){
				
				int testId = rs1.getInt("TEST_ID");
				int sourceId = rs1.getInt("SOURCE_ID");
				int lineNum = rs1.getInt("LINE_NUM");

				String sql2 = "SELECT STMT_ID "
						+ "FROM STMT "
						+ "WHERE SOURCE_ID = " + sourceId 
						+ " AND LINE_NUM = " + lineNum;
				ResultSet rs2 = s2.executeQuery(sql2);

				int stmtId = rs2.getInt("STMT_ID");


				insertSTMT_ID_COVERAGE(testId, stmtId);
//				System.out.println(testId + " " + stmtId);
			}

			s1.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("STMT_ID_COVERAGE table created");

	}
	
	public void insertSTMT_ID_COVERAGE(int testId, int stmtId){
		String sql = "INSERT INTO STMT_ID_COVERAGE "
				+"VALUES(?,?)";
		executePsmt(sql, testId, stmtId);
	}
}
