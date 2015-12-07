/**
 * 
 */
package com.avaya.plds.excel;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.avaya.plds.beans.Entitlements;
import com.avaya.plds.constants.DatabaseConstants;

/**
 * @author Medamoni_Karunakar
 *
 */

public class DatabaseConnection implements PLDSFileInterface {

	private static Connection dbConn;
	private Statement statement;
	private PreparedStatement pstmt;
	private Logger log = Logger.getLogger(getClass().getName());
	private StringBuilder uniqueKeyBuilder = null, sdocBuilder = null, mcnBuilder = null, sddtBuilder = null,mcdtBuilder = null;
	private static String TEST ="Hello";
	

	public DatabaseConnection(){

	}
	public  Connection getConnection(String dbType) throws ClassNotFoundException, SQLException, IOException{
		System.out.println(TEST);
		log.info("Checking database Connection is null or not. If null create the new instance else return existing connectiono object...");
		log.info("this.dbConn = "+this.dbConn);
		//log.info("dataSource "+ dataSource);
		if(this.dbConn == null || this.dbConn.isClosed()){
		//	dataSource.getConnection();
			log.info("dbconnection is null: Creating database object of "+dbType);
			//Properties prop = new Properties();
			//prop.load(getClass().getClassLoader().getResourceAsStream("databasedetails.properties"));
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//log.info(" database type :"+prop.getProperty(dbType+"_URL"));
			//dbConn = DriverManager.getConnection(prop.getProperty(dbType+"_URL"),prop.getProperty(dbType+"_USERNAME"),prop.getProperty(dbType+"_PASSWORD"));
			this.dbConn = DriverManager.getConnection(DatabaseConstants.PROD_URL,DatabaseConstants.PROD_USERNAME,DatabaseConstants.PROD_PASSWORD);
			if(this.dbConn != null){
				this.dbConn.setAutoCommit(false);
				this.statement = dbConn.createStatement();

			}
		}
		return this.dbConn;
	}

	public String captureStrackTrace(Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter pwriter = new PrintWriter(sw);
		e.printStackTrace(pwriter);
		return sw.toString();
	}
	
	public Map<String,Entitlements> prepareQueryForPLDS(List<Entitlements> idList) throws ClassNotFoundException, SQLException, IOException{
		System.out.println("Preparing Query based on the select id(0-product-id/1=entitlement_id/2=order Number : id value is" );
		StringBuilder queryBuilder = null;
		List<Entitlements> entList=new ArrayList<Entitlements>();
		String status = null;
			queryBuilder = new StringBuilder(); 
			uniqueKeyBuilder = new StringBuilder(); 
			sdocBuilder = new StringBuilder(); 
			mcnBuilder = new StringBuilder(); 
			sddtBuilder = new StringBuilder(); 
			mcdtBuilder = new StringBuilder();
			
			queryBuilder.append(DatabaseConnectionDetails.ENT_QUERY2);
			for(Entitlements bean: idList){
				queryBuilder.append("'"+bean.getUniqueKey()+"',");
			}
			
			queryBuilder.replace(queryBuilder.lastIndexOf(","), queryBuilder.length(),"");
			queryBuilder.append(")");
			System.out.println("final query :"+queryBuilder.toString());
			return buildPLDS(queryBuilder.toString());
		//return entList;
	}
	
	
	
	public Map<String,Entitlements> buildPLDS(String q1) throws ClassNotFoundException, SQLException, IOException{
		
		getConnection("PROD");
		System.out.println("Rebuiling database Query .."+q1);
		ResultSet rSet = null;
		List<Entitlements> entList=new ArrayList<Entitlements>();
		Map<String,Entitlements> finalPLDSMap = new HashMap<String, Entitlements>();

		try {
			System.out.println("REterving max event id value from database  using following QUERY ::"+q1);
			 
			rSet = statement.executeQuery(q1);
			while(rSet.next()){
				Entitlements ent=new Entitlements();
				String ENT_ISV_PROPERTY_4 = rSet.getString("ENT_ISV_PROPERTY_4");
				String ENT_ISV_PROPERTY_2 =  rSet.getString("ENT_ISV_PROPERTY_2");
				ent.setSalesDocumentOut(ENT_ISV_PROPERTY_4);
				ent.setMaterialCodeOut(ENT_ISV_PROPERTY_2);
				ent.setAuthorizationKey(rSet.getString("AUTHORIZATION_KEY"));
				ent.setCreationDate(rSet.getString("CREATION_DATE"));
				ent.setEntitlementId(rSet.getString("ENTITLEMENT_ID"));
				
				String key = ENT_ISV_PROPERTY_4+ENT_ISV_PROPERTY_2;
				if(!finalPLDSMap.containsKey(key) )
					finalPLDSMap.put(key,ent);
				
			}
			dbConn.close();
			
		} catch (SQLException e) {
			System.out.println(captureStrackTrace(e));
		}
		finally{
			System.out.println("Making database connection null to avoid memory leaks and possible to connect different database");
			dbConn = null;
		}
		
		return finalPLDSMap;
	}

}
