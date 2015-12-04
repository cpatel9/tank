/**
 * 
 */
package com.avaya.plds.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.avaya.plds.beans.ProductFamily;

/**
 * @author Medamoni_Karunakar
 *
 */


@Component
public class ExcelRead {


	private OPCPackage opcp = null;
	private XSSFWorkbook xssfWorkbook = null, xssfWorkBookfrOut = null; 
	private InputStream stream = null;
	private XSSFSheet sheet = null;
	private Iterator<Row> rowIterator = null;
	private Iterator<Cell> cellIterator = null;
	private Map<String,String> getProductFamilyListValueMap = new HashMap<String, String>();
	private Map<String,String> getApplicationListValueMap = new HashMap<String, String>();
	private String todayDate = "";
	private Map<Integer,Boolean> dataColumns = null;
	private int maxCellIndex =0;
	private FileWriter fWriter=  null;

	public ExcelRead(){
		/*File file = new File("D:\\Sridhar_komalla\\ChangeLogFiles\\test.txt");
		if(file.exists()){
			if(file.delete())
				System.out.println("test.txt was deleted form disk ...");


		}*/
		SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
		todayDate = format.format(new Date());
			System.out.println("today date is "+ todayDate);
		
		dataColumns = new HashMap<Integer, Boolean>();
	}
	

	public Map<String,String> getProductFamilyListValueMap(){
		return this.getProductFamilyListValueMap;
	}
	
	public Map<String,String> getApplicationListValueMap(){
		return this.getApplicationListValueMap;
	}
	

	public void getExcelObject() throws InvalidFormatException, IOException{
		System.out.println("  Inside of getPoeticProductFamily ");
		this.opcp = OPCPackage.open(stream);
		this.xssfWorkbook = new XSSFWorkbook(this.opcp);
		this.xssfWorkBookfrOut = new XSSFWorkbook();
	}


	public Map<String, Map> readPLDSPoeticFamilyData(InputStream stream,String path) throws InvalidFormatException, IOException{
		System.out.println("Inside of readPLDSPoeticFamilyData ");
		this.stream = stream;
		this.getExcelObject();
		this.getPoeticProductFamily();
		this.generateTextWithLicenseCodes(this.getPoeticFeatureLoad("see instructions","Instructions : Complete for NPI or Lifecycle Change logs",4,-10), "ComponentLoad_CCMV.txt",-1);
		
		this.generateTextWithLicenseCodes(this.getPoeticFeatureLoad("License and Upgrade Codes: ", "SMART Support codes", 5,1),"Product_load_ CCMV.txt",0);
		
		Map<String,Map> resultMap = new HashMap<String, Map>();
		resultMap.put("product",this.getProductFamilyListValueMap);
		resultMap.put("application",this.getApplicationListValueMap);
		return resultMap;
	}



	
	/*public void generateTextWithPoeticLoad(List<String> poeticLoadList) throws FileNotFoundException,IOException{
		
		FileWriter fWriter = new FileWriter("D:\\Sridhar_komalla\\test1.txt");
		for(String poetic)
		
		
	}*/
	
	public String generateExcelSheet(List<ProductFamily> familyApplicationList, String headers,String fileName,String path){
	
		try{
		
		File file = new File(path+fileName);
		if(file.exists()){
			file.delete();
		}
			
		fWriter = new FileWriter(path+fileName);
		int length = headers.split("\t").length;
		int rowIndex = 0;
		System.out.println("length of headers" + length);
		for(String header: headers.split("\t")){
			fWriter.write(header);
			fWriter.write( rowIndex < length ? "\t" : "");
			rowIndex++;
		}
		fWriter.write("\n");
		rowIndex=1;
			for (ProductFamily pf : familyApplicationList) {
				pf.setDisplay_sequence(rowIndex);
				
					fWriter.write(pf.toString());
					fWriter.write("\n");
					rowIndex++;

			}
		fWriter.close();
		}catch(IOException e){
			return "Unable to generate Excel file";
		}
		
		
		return "Generated Excel file";
	}
	
	
	public void generateTextWithLicenseCodes(List<String> poeticDataList,String fileName,int index) throws FileNotFoundException,IOException{

		System.out.println("generateTextWithLicenseCodes ...starts  with the file name " + fileName);
		
		
		 fWriter = new FileWriter("D:\\Sridhar_komalla\\ChangeLogFiles\\"+fileName);
		int rowIndex = 0,  cellIndex = 0;
		for(String poeticDataLine : poeticDataList){
			poeticDataLine = poeticDataLine.replaceAll("\t$", "");
			cellIndex = 0;
			for(String cellValue : poeticDataLine.split("\t")){
				if(cellIndex < maxCellIndex && cellIndex > index )
					
					if(index >= 0)
					fWriter.write( (cellIndex == 4 && rowIndex > 0) ? todayDate : cellValue);
					else
						fWriter.write(cellValue);
				/*	if(cellIndex ==5)
					fWriter.write(cellValue);
					else*/
						
				if(poeticDataLine.split("\t").length > cellIndex+1 && cellIndex > index && cellIndex < maxCellIndex )
						fWriter.write("\t");
				cellIndex++;
			}

			if(poeticDataLine.split("\t").length > 0  && poeticDataList.size() > rowIndex+2){
				fWriter.write("\n");
			}


			rowIndex++;
		}
		fWriter.flush();
		fWriter.close();
		System.out.println("generateTextWithLicenseCodes ...end with the file name " + fileName);

	}


	/*
	 * Start date should be Current date... 
	 */
	public List<String> getPoeticFeatureLoad(String value1, String value2, int sheetNo,int headers){
		System.out.println( " Inside of  getPoeticFeatureLoad method ...");
		sheet = xssfWorkbook.getSheetAt(sheetNo);
		boolean read = false;
		List<String> dataList = new ArrayList<String>();
		rowIterator = sheet.iterator();
		while(rowIterator.hasNext()){
			StringBuilder builder = new StringBuilder();
			Row row = rowIterator.next();
			int rowNumber = row.getRowNum();
			if(row !=  null ){
				for(short i =row.getFirstCellNum(); i< row.getLastCellNum();i++){

					if(row.getCell(i)!= null && (row.getCell(i).getCellType() == row.getCell(i).CELL_TYPE_STRING) && row.getCell(i).getStringCellValue().contains(value1) && i ==0){
						read = true;
						break;
							//builder.append(value1).append("\t");
					}



					else if(row.getCell(i)!= null  && (row.getCell(i).getCellType() == row.getCell(i).CELL_TYPE_STRING) &&  row.getCell(i).getStringCellValue().contains(value2)){
						read = false;
					}else if(read){
					//	System.out.println("rowNumber "+ rowNumber);
						maxCellIndex = (row.getLastCellNum() > maxCellIndex && rowNumber >0) ? row.getLastCellNum() : maxCellIndex;
					//	System.out.println("maxCellIndex "+ maxCellIndex);
						Cell cell = row.getCell(i);
						if(cell != null){
							if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							//if(i >0)
									builder.append(cell != null ? Double.valueOf(cell.getNumericCellValue()).longValue(): "").append("\t");
							}
							else{
								//if(i >0 )
									builder.append(cell != null ? cell.getStringCellValue(): "").append("\t");
							}
						}else{
							//if(i >0)
								builder.append("").append("\t");
						}
					}
					if(headers == rowNumber){
						//if(i>0)
							builder.append(row.getCell(i).getStringCellValue()).append("\t");
					}
				}
				if(!builder.toString().equals("") && !builder.toString().matches("^ null.*"))
					dataList.add(builder.toString().replaceFirst(",", ""));
			}


		}
		return dataList;

	}

	public List<String> getPoeticProductFamily(){
		
		System.out.println("  Inside of getPoeticProductFamily ");
		sheet = xssfWorkbook.getSheetAt(3);
		List<String> family = new ArrayList<String>();
		List<String> familyCodes = new ArrayList<String>();
		List<String> pld = new ArrayList<String>(); // pld product line description
		List<String> plc = new ArrayList<String>(); // plc product line code
		rowIterator = sheet.iterator();
		int count = 0;
		int appCodeCount=0;
		int appNameCount=0;
		boolean status = false;
		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();
			cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if(cell.getCellType() == Cell.CELL_TYPE_STRING){
					if(cell.getStringCellValue().equals("NEW FAMILY Configurations")){
						status = true;
						count++;
					}
					else if(cell.getStringCellValue().equals("NPI Pre-Configuration Data only")){
						status = true;
						count+=2;
						appCodeCount=0;
						appNameCount=0;
						System.out.println("cell value : tetsing "+cell.getStringCellValue());
					}
					else if(count > 3 && count < 6 ){
						if(count == 4){
							family.add(cell.getStringCellValue().trim());
						}else{
							familyCodes.add(cell.getStringCellValue().trim());
						}
						System.out.print(cell.getStringCellValue() +"\t");
					}
					else if(count == 6){
						status = false;
					}

					else if (count > 9 && count < 12) {
						if (count == 10) {
							if (appNameCount < 2) {
								pld.add(cell.getStringCellValue());
								appNameCount++;
							}

						} else {
							if (appCodeCount < 2) {
								plc.add(cell.getStringCellValue());
								appCodeCount++;
							}
						}
					}
				}
			}
			if(status){
				count++;
			}


		}

		this.storeMap(family, familyCodes, 0);
		this.storeMap(pld, plc, 1);
		for(String key : getProductFamilyListValueMap.keySet()){
			System.out.println("key = "+key +" and value  ="+getProductFamilyListValueMap.get(key));
		}

		for(String key : getApplicationListValueMap.keySet()){
			System.out.println("key = "+key +" and value  ="+getApplicationListValueMap.get(key));
		}


		return null;
	}



	public void storeMap(List<String> family, List<String> familyCodes,int status ){

		
		System.out.println("family="+family);
		System.out.println("familyCodes="+familyCodes);
		
		for (Iterator iterator = familyCodes.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println("familyCodes="+family);
			
		}
		
		
		for (Iterator iterator = family.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println("family="+family);
			
			
			
		}
		Object[] familyArray =  family.toArray();
		int i = 0;
		for(String familyCode: familyCodes){
			if(status == 0 && i >0 )
				getProductFamilyListValueMap.put(familyCode, familyArray[i].toString());
			else if(status == 1 && i> 0)
				getApplicationListValueMap.put(familyCode, familyArray[i].toString());
			i++;
		}




	}





	public static void main(String[] args){

		String file = "D:\\Sridhar_komalla\\AVA-PLD-NPI-TR094E_Desktop-WallboardDec2014_Baselined.xlsx";
		//String file = "D:\\Sridhar_komalla\\PLDS LAC Request_April 1-3.xlsx";
		try {
			new ExcelRead().readPLDSPoeticFamilyData(new FileInputStream(file),"testPath");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}


