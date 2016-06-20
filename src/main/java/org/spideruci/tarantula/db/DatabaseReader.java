package org.spideruci.tarantula.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseReader extends DatabaseUtil{
	private int stmtSize;
	private int testcaseSize;
	private boolean[][] M;
	private boolean[] F;
	private boolean[] C;

	public DatabaseReader(Connection c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	
	
	public void getSize(){
		Statement s;

			try {
				s = c.createStatement();
								
				String sql1 = "SELECT COUNT(*) FROM TESTCASE";
				ResultSet rs1 = s.executeQuery(sql1);
				testcaseSize = rs1.getInt(1);
				
				System.out.println("testcase size is " + testcaseSize);
				
				String sql2 = "SELECT COUNT(*) FROM STMT";
				ResultSet rs2 = s.executeQuery(sql2);
				stmtSize = rs2.getInt(1);
				
				System.out.println("statement size is " + stmtSize);
				
				s.close();
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	protected void createM(){
		M = new boolean[testcaseSize][stmtSize];

		for(int i = 0; i < testcaseSize; i++)
			for(int j = 0; j < stmtSize; j++)
				M[i][j] = false;
	}
	
	protected void createF(){
		F = new boolean[testcaseSize];
		
		for(int i = 0; i < testcaseSize; i++)
			F[i] = false;
	}
	
	protected void createC(){
		C = new boolean[stmtSize];
		
		for(int i = 0; i < stmtSize; i++)
			C[i] = false;
	}
	
	public boolean[][] getM(){
		return M;
	}
	
	public boolean[] getF(){
		return F;
	}
	
	public boolean[] getC(){
		return C;
	}
	
	public void getTestcase(){
		Statement s;
		try {
			s = c.createStatement();
			String sql = "SELECT * FROM TESTCASE";
			ResultSet rs = s.executeQuery(sql);

			System.out.println("Getting the TESTCASE table");
						
			while(rs.next()){
				int testID = rs.getInt("TEST_ID");
				int status = rs.getInt("STATUS");
				
				// if status is 1, it means the test case fails.
				if(status == 1)
					F[testID] = true;
			}
			
			s.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public void getCoverage(){
		Statement s;
		try {
			s = c.createStatement();
			String sql = "SELECT * FROM STMT_ID_COVERAGE";
			ResultSet rs = s.executeQuery(sql);

			System.out.println("Getting the STMT_ID_COVERAGE table");
			
//			rs.getFetchSize()
			
			while(rs.next()){
				int testID = rs.getInt("TEST_ID");
				int stmtID = rs.getInt("STMT_ID");
				
				M[testID][stmtID] = true;
				C[stmtID] = true;

//				System.out.println(getStmtID(sourceID, lineNum) + "  " + sourceID + "   " + lineNum);
				
			}
			
			s.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
}
