package com.avaya.plds.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.avaya.plds.beans.ProductFamily;
import com.avaya.plds.service.ChangeLogService;

@Service
public class ChangeLogServiceImpl implements ChangeLogService {

	@Override
	public List<ProductFamily> getApplicationListValues(String filePath) throws Exception {
		System.out.println("inside of ChangeLog service area1");
		return readExcelFiles(filePath);

	}

	@Override
	public List<ProductFamily> getProductFamilyListValues(String filePath) throws Exception{
		System.out.println("inside of ChangeLog service area2");
		return readExcelFiles(filePath);
	}


	/**
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public List<ProductFamily> readExcelFiles( String fileName) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("D:\\Sridhar_komalla\\ChangeLogFiles\\"+fileName)));
		String line ="";
		List<ProductFamily> list = new ArrayList<ProductFamily>();
		int i =0;
		while((line =br.readLine()) != null){

			
			String[] data = line.split("\t");
			System.out.println("data length "+ data.length);
			if(i>0 && data.length > 6){
				ProductFamily pFamily = new ProductFamily();
				pFamily.setListName(data[0]);
				pFamily.setCode(data[1]);
				pFamily.setLanguage(data[2]);
				pFamily.setContext(data[3]);
				pFamily.setDescription(data[4]);
				pFamily.setDisplay_sequence(Integer.parseInt(data[5]));
				pFamily.setIs_active(data[6]);
				pFamily.setData1(data.length > 7 ? data[7]:"");
				pFamily.setData2(data.length > 8 ? data[8]:"");
				pFamily.setData3(data.length > 9 ? data[9]:"");
				list.add(pFamily);
			}
			i++;
			System.out.println("i value "+i);
		}
		br.close();
		return list;

	}

}
