package org.spideruci.tarantula.db;

import static org.spideruci.tarantula.TarantulaFaultLocalizer.SUSPICIOUSNESS;
import static org.spideruci.tarantula.TarantulaFaultLocalizer.CONFIDENCE;

import org.spideruci.tarantula.TarantulaData;
import org.spideruci.tarantula.TarantulaFaultLocalizer;

public class InjectData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String db = "/Users/Ku/Documents/uci/research/tacoco/tacoco_output/joda-time.db";

		SQLiteDB sqliteDB = new SQLiteDB();		
		sqliteDB.open(db);
		
		DatabaseEditor de = sqliteDB.runDatabaseEditor();

		DatabaseReader dr = sqliteDB.runDatabaseReader();
		
		de.createSTMTTable();
		de.createSTMT_ID_CoverageTable();
		
		
		
//		//M, C, F
		dr.getSize();
		dr.createM();
		dr.createC();
		dr.createF();
		dr.getCoverage();

		dr.getTestcase();

		TarantulaData data = new TarantulaData(dr.getM());
		data.setC(dr.getC());
		data.setF(dr.getF());
		TarantulaFaultLocalizer localizer = new TarantulaFaultLocalizer();
		double[][] suspiciousnessAndConfidence = localizer.compute(data, false);
		double[] suspiciousness = suspiciousnessAndConfidence[SUSPICIOUSNESS];
		double[] confidence = suspiciousnessAndConfidence[CONFIDENCE];
		
		
		de.createSuspiciousnessTable();
		
		
	    for(int i = 0; i < suspiciousness.length; i += 1) {
	    	de.insertSuspiciousness(i, suspiciousness[i]);
	    	
//	        System.out.printf("%f ", suspiciousness[i]);
	      }
	    
		de.createConfidenceTable();
	    
	    for(int i = 0; i < confidence.length; i += 1) {
	    	de.insertConfidence(i, confidence[i]);
	    	
//	        System.out.printf("%f ", suspiciousness[i]);
	      }
	    
	    
	    
	}

}
