package com.avaya.plds.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.avaya.plds.beans.Entitlements;
import com.avaya.plds.excel.DatabaseConnection;



//@Repository("pldsService")
public class PLDSService {

	private Logger log = Logger.getLogger(getClass().getName());
	
	//@Autowired
	DatabaseConnection databaseDAO;

	public List<Entitlements> getEntitlementLists(List<Entitlements> inList) throws IOException,SQLException,ClassNotFoundException{

		System.out.println("Entering getEntitlementLists");

		//List<Entitlements> completeList=new ArrayList<Entitlements>();
		databaseDAO = new DatabaseConnection();
		Connection con= databaseDAO.getConnection("PROD");


		int size=inList.size();
		int count=(inList.size())/1000;
		System.out.println(size);
		
		if(count>0 && size > 0){
			for(int i=0;i<count;i++){
				
				doParse(databaseDAO.prepareQueryForPLDS(inList.subList( i> 0 ?((i*1000)-1):(i*1000), ((i+1)*1000)-1)),inList);
			}
			log.info("range value "+((count*1000)-1) +" - "+ (size));
			doParse(databaseDAO.prepareQueryForPLDS(inList.subList((count*1000)-1, size)),inList);
		}
		else{
			doParse(databaseDAO.prepareQueryForPLDS(inList),inList);
		}
		
		

		System.out.println("Leaving getEntitlementLists");	


		return inList;
	}

	public List<Entitlements> doParse(Map<String,Entitlements> PldsMap,List<Entitlements> inList){
		System.out.println("Entering doParse");
			List<String> testKeys = new ArrayList<String>();
			testKeys.add("1834204270052"); testKeys.add("1831792339439");testKeys.add("1830596339443");
			testKeys.add("1818554302271"); testKeys.add("1808505271180"); testKeys.add("1836085339423"); testKeys.add("1834606269422");
			for(Entitlements ent : inList){
				Entitlements entObj = PldsMap.get(ent.getUniqueKey());
				if(entObj != null){
					ent.setAuthorizationKey(entObj.getAuthorizationKey());
					ent.setEntitlementId(entObj.getEntitlementId());
					ent.setCreationDate(entObj.getCreationDate());
				}
			}
			
			
	
		return inList;
	}


}
