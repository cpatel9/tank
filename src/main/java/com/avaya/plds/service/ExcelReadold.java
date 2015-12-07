/**
 * 
 */
package com.avaya.plds.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.springframework.stereotype.Service;

/**
 * @author Medamoni_Karunakar
 *
 */

@Service
public class ExcelReadold {


	private OPCPackage opcp = null;
	private XSSFWorkbook xssfWorkbook = null; 
	private InputStream stream = null;
	private XSSFSheet sheet = null;
	private Iterator<Row> rowIterator = null;
	private Iterator<Cell> cellIterator = null;
	private Map<String,String> familycodeMaterailMap = new HashMap<String, String>();
	private Map<String,String> productLineDescMap = new HashMap<String, String>();
	private String filePath;


	public ExcelReadold(){

	}

	public void getExcelObject() throws InvalidFormatException, IOException{

		this.opcp = OPCPackage.open(stream);
		this.xssfWorkbook = new XSSFWorkbook(opcp);
	}


	public void readPLDSPoeticFamilyData(InputStream stream ,String filePath) throws InvalidFormatException, IOException{
		this.stream = stream;
		this.filePath = filePath+"/generate";
		this.getExcelObject();
		this.getPoeticProductFamily();
		this.getPoeticFeatureLoad();
	}


	public void getPoeticFeatureLoad(){
		sheet = xssfWorkbook.getSheetAt(4);
		boolean read = false;
		List<String> dataList = new ArrayList<String>();
		rowIterator = sheet.iterator();
		while(rowIterator.hasNext()){
			StringBuilder builder = new StringBuilder();
			Row row = rowIterator.next();
				System.out.println("row numbers "+ row.getRowNum());
			//cellIterator = row.cellIterator();
				System.out.println(row.getFirstCellNum());
			if(row !=  null ){
				for(short i =row.getFirstCellNum(); i< row.getLastCellNum();i++){
					System.out.println(row.getCell(0).getStringCellValue());
					if(row.getCell(i)!= null && row.getCell(i).getStringCellValue().equals("publisherID") && i ==0){
						read = true;
						builder.append("publisherID").append("\t");
						System.out.println("find publisher Id");
					}
					else if(row.getCell(i)!= null &&  row.getCell(i).getStringCellValue().equals("Instructions : Complete for NPI or Lifecycle Change logs")){
						read = false;
						System.out.println("find Instructions ");
						//break;
					}else if(read){
						System.out.println(i);
						Cell cell = row.getCell(i);
						System.out.println(cell.getStringCellValue());
						builder.append(cell != null ? cell.getStringCellValue(): "null").append("\t");
						System.out.println(builder.toString());
					}
				}
				dataList.add(builder.toString().replaceFirst(",", ""));
			}

			/*while(cellIterator.hasNext()){
				Cell cell = cellIterator.next();
				if(cell.getCellType() == Cell.CELL_TYPE_STRING){
					if(cell.getStringCellValue().equals("publisherID") && cell.getColumnIndex() == 0){
						read = true;
						System.out.print(cell.getStringCellValue()+"\t");
						//System.out.println("got the condition ....to make read true ..."+ read);
					}else if(cell.getStringCellValue().equals("Instructions : Complete for NPI or Lifecycle Change logs")){
						read = false;
						//System.out.println("got the condition ....to make read false ..."+ read);
					}else if(read){
					//	System.out.println("read values  "+ read);
						System.out.print(cell.getStringCellValue()+"\t");
					}
				}
				if(cell.getCellType() == cell.CELL_TYPE_BLANK && read){
					//System.out.println(" cell is blank ...." + cell.getColumnIndex() + row.getRowNum());
				}
				//if(cell.)
			}*/
			//System.out.println(builder.toString());
		}
		System.out.println(dataList);

	}

	public List<String> getPoeticProductFamily(){

		sheet = xssfWorkbook.getSheetAt(3);
		List<String> family = new ArrayList<String>();
		List<String> familyCodes = new ArrayList<String>();
		List<String> pld = new ArrayList<String>(); // pld product line description
		List<String> plc = new ArrayList<String>(); // plc product line code
		rowIterator = sheet.iterator();
		int count = 0;
		boolean status = false;
		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();
			cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if(cell.getCellType() == Cell.CELL_TYPE_STRING){
					if(cell.getStringCellValue().equals("NEW FAMILY Configurations")){
						System.out.println(cell.getStringCellValue());
						status = true;
						count++;
					}
					else if(cell.getStringCellValue().equals("NPI Pre-Configuration Data only")){
						status = true;
						count+=2;
						System.out.println(cell.getStringCellValue());
					}
					else if(count > 3 && count < 6 ){
						if(count == 4){
							family.add(cell.getStringCellValue());
						}else{
							familyCodes.add(cell.getStringCellValue());
						}
						System.out.print(cell.getStringCellValue() +"\t");
					}
					else if(count == 6){
						status = false;
					}

					else if(count > 9 && count < 12 ){
						if(count == 10){
							pld.add(cell.getStringCellValue());
						}else{
							plc.add(cell.getStringCellValue());
						}
					}
				}
			}
			if(status){
				count++;
			}


		}
		/*	System.out.println("\n");
			System.out.println("family :: "+ family);
			System.out.println("familyCode:: "+ familyCodes);
			System.out.println("family :: "+ pld);
			System.out.println("familyCode:: "+ plc);*/

		this.storeMap(family, familyCodes, 0);
		this.storeMap(pld, plc, 1);
		for(String key : familycodeMaterailMap.keySet()){
			System.out.println("key = "+key +" and value  ="+familycodeMaterailMap.get(key));
		}

		for(String key : productLineDescMap.keySet()){
			System.out.println("key = "+key +" and value  ="+productLineDescMap.get(key));
		}


		return null;
	}



	public void storeMap(List<String> family, List<String> familyCodes,int status ){

		Object[] familyArray =  family.toArray();
		int i = 0;
		for(String familyCode: familyCodes){
			if(status == 0 && i >0 )
				familycodeMaterailMap.put(familyCode, familyArray[i].toString());
			else if(status == 1 && i> 0)
				productLineDescMap.put(familyCode, familyArray[i].toString());
			i++;
		}




	}





	/*public static void main(String[] args){

		String file = "G:\\AVA-PLD-NPI-TR094E_Desktop-WallboardDec2014_Baselined.xlsx";
		//String file = "D:\\Sridhar_komalla\\PLDS LAC Request_April 1-3.xlsx";
		try {
			new ExcelRead().readPLDSPoeticFamilyData(new FileInputStream(file),"file");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}*/

}
