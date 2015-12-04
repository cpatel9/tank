/**
 * 
 */
package com.avaya.plds.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.avaya.plds.beans.ProductFamily;


/**
 * @author Medamoni_Karunakar
 *
 */

public interface FileUploadService {
	
	public void getProductFamilyListValues(Map<String,String> productMap) throws Exception;
	public void getApplicationListValues(Map<String,String> applicationMap) throws Exception;
	public Map<String,Map> generateProductAndComponentLoad(InputStream stream,String path);
	public List<ProductFamily> prepareBeanWithList(Map<String,String> dataMap, List<ProductFamily> dataList );
	public String generateExcelFile(List<ProductFamily> listValues,String fileName,String path);

}
