package com.avaya.plds.excel;

public class DatabaseConnectionDetails {
	
	
	public static final String DEV_URL ="jdbc:oracle:thin:@dlpldovdb01.nonprod.avaya.com:1526:PLDDEV";
	public static final String DEV_USERNAME ="devpoetic";
	public static final String DEV_PASSWORD ="poeticdev1";
	
	public static final String STAGE_URL ="jdbc:oracle:thin:@lshrdbd4.dr.avaya.com:1526:PLDCONV";
	public static final String STAGE_USERNAME ="stagepoetic";
	public static final String STAGE_PASSWORD = "poetic1stage";
	
	public static final String PROD_URL ="jdbc:oracle:thin:@pldprd.avaya.com:1526:PLDPRD";
	public static final String PROD_USERNAME ="prodpoetic";
	public static final String PROD_PASSWORD ="poeticprod1";
	
	
	/*public static final String ENT_QUERY ="SELECT ENTITLEMENT_ID  from entitlement where entitlement_id in(";
	public static final String PRO_QUERY="SELECT ENTITLEMENT_ID   FROM ENTITLEMENT E, PRODUCT P WHERE E.PRODUCT_ID = P.PRODUCT_ID AND E.STATUS IN ('AVAIL', 'ACTIV') "+ 
	" AND E.ENT_LICENSE_TYPE_CODE = 'LIC'  AND P.SKU_NUMBER IN (";
	public static final String ORD_QUERY ="select ENTITLEMENT_ID  from entitlement where ENT_ISV_PROPERTY_4 IN (";
	public static final String ORD_QUERY1= ")and ENT_LICENSE_TYPE_CODE = 'LIC' and STATUS IN ('AVAIL', 'ACTIV')";
	
	public static final String INSERT_QUERY = "INSERT INTO event"+
        "(EVENT_ID,NAME,TYPE,STATUS,CREATE_DATE,EVENT_PUB_XREF_NUM,PARAM_MESSAGE,EVENT_SOURCE_CODE,UPD_USER,UPD_DATE)"+
       " VALUES (?1,'SET_EXTERNAL_ENTITLEMENT','SEENT','ACTIV',sysdate,?2,?3,?4,'USER_0',sysdate)";	*/
	
	
/*	public static final String EVENT_QUERY ="select MIN(event_id) from event";
	*/
	public static final String ENT_QUERY2 = "SELECT ENT_ISV_PROPERTY_4, "+
        "        ENT_ISV_PROPERTY_2, "+
        "        AUTHORIZATION_KEY, "+
        "        CREATION_DATE, "+
        "        ENTITLEMENT_ID "+
        "   FROM ENTITLEMENT "+
       "   WHERE CONCAT (ENT_ISV_PROPERTY_4, ENT_ISV_PROPERTY_2) IN ( ";
	
	/*public static final String ENT_QUERY2 ="WITH completeSet "+
     "AS (    SELECT TRIM (REGEXP_SUBSTR (uniquekey,"+
                                       "  '[^,]+',"+
                                       "  1,"+
                                       "  LEVEL)) "+
                      " uniquekey,"+
                   " TRIM (REGEXP_SUBSTR (sdn,"+
                       "                  '[^,]+',"+
                      "                   1,"+
                      "                   LEVEL)) "+
                    "   sdn,"+
                  "  TRIM (REGEXP_SUBSTR (mcn,"+
                  "                       '[^,]+',"+
                  "                       1,"+
                  "                       LEVEL)) "+
                 " mcn,"+
                  " TRIM (REGEXP_SUBSTR (mcdt,"+
                 "                        '[^,]+',"+
                  "                       1,"+
                  "                       LEVEL)) "+
                  "     mcdt,"+
                 "  TRIM (REGEXP_SUBSTR (sddt,"+
                 "                        '[^,]+',"+
                 "                        1,"+
                 "                        LEVEL)) "+
                 "      sddt"+
              " FROM (SELECT ?1 uniquekey, "+
             "               ?2 sdn, "+
             "               ?3 mcn, "+
             "               ?4 sddt, "+
             "               ?5 mcdt "+
             "          FROM DUAL) "+
        " CONNECT BY INSTR (uniquekey,"+
           "                ',',"+
           "                1,"+
           "                LEVEL - 1) > 0), "+
   "  pldsset"+
   "  AS (SELECT ENT_ISV_PROPERTY_4, "+
        "        ENT_ISV_PROPERTY_2, "+
        "        AUTHORIZATION_KEY, "+
        "        CREATION_DATE, "+
        "        ENTITLEMENT_ID "+
        "   FROM ENTITLEMENT "+
       "   WHERE CONCAT (ENT_ISV_PROPERTY_4, ENT_ISV_PROPERTY_2) IN ( ";

   	public static final String ENT_QUERY3= " )), "+
    " combinedset "+
    " AS (SELECT *"+
    "       FROM completeset A"+
     "           LEFT JOIN pldsset B"+
        "           ON A.uniquekey ="+
        "                 CONCAT (B.ENT_ISV_PROPERTY_4, B.ENT_ISV_PROPERTY_2)) "+
"SELECT * "+
 " FROM combinedset";*/
	

}
