/**
 * 
 */
package com.avaya.plds.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.plds.beans.ProductFamily;
import com.avaya.plds.dao.ChangeLogDao;
import com.avaya.plds.excel.ExcelRead;
import com.avaya.plds.service.ChangeLogService;
import com.avaya.plds.service.FileUploadService;

/**
 * @author Medamoni_Karunakar@infosys.com
 * 
 */

@Service
public class FileUploadServiceImpl implements FileUploadService
{

    @Autowired
    public ExcelRead excelRead;

    @Autowired
    public ChangeLogDao changeLogDao;

    @Autowired
    public ChangeLogService changeLogService;

    List<ProductFamily> applicationList = null;
    List<ProductFamily> productFamilyList = null;
    String path = "D:\\Sridhar_komalla\\ChangeLogFiles\\";

    public void getProductFamilyListValues(Map<String, String> productMap) throws Exception
    {

        System.out.println("Product Family codes ");
        productMap.remove("VP1");
        for (Iterator iterator = productMap.entrySet().iterator(); iterator.hasNext();)
        {

            Map.Entry pair = (Map.Entry)iterator.next();

            System.out.println("printing ProductMap=" + pair.getKey() + " = " + pair.getValue());

        }
        // productFamilyList= prepareBeanWithList(excelRead.getProductFamilyListValueMap(),changeLogDao.getProductFamilyValues()); // database
        // code
        productFamilyList = prepareBeanWithList(productMap, changeLogService.getProductFamilyListValues("Product_Family_List_Values_Load.txt"));

        this.generateExcelFile(productFamilyList, "Product_Family_List_Values_Load.txt", path);

    }

    public void getApplicationListValues(Map<String, String> applicationMap) throws Exception
    {
        System.out.println("Application values ");
        // applicationList= prepareBeanWithList(excelRead.getApplicationListValueMap(), changeLogDao.getApplicationListValues()); // database
        // code if need in feature
        applicationList = prepareBeanWithList(applicationMap, changeLogService.getApplicationListValues("Application_List_Values_Load.txt"));
        this.generateExcelFile(applicationList, "Application_List_Values_Load.txt", path);
    }

    @Override
    public Map<String, Map> generateProductAndComponentLoad(InputStream stream, String path)
    {
        String fileName = "D:\\Sridhar_komalla\\AVA-PLD-NPI-TR094E_Desktop-WallboardDec2014_Baselined.xlsx";
        try
        {
            // excelRead.readPLDSPoeticFamilyData( new FileInputStream(fileName),"testpath");
            return excelRead.readPLDSPoeticFamilyData(stream, path);
        }
        catch (InvalidFormatException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new HashMap<String, Map>();
    }

    @Override
    public List<ProductFamily> prepareBeanWithList(Map<String, String> dataMap, List<ProductFamily> dataList)
    {

        System.out.println("prepareBeanwithList: " + dataList);

        for (String key : dataMap.keySet())
        {
            ProductFamily bean = new ProductFamily();
            bean.setCode(key);
            bean.setDescription(dataMap.get(key));
            dataList.add(bean);
        }

        /*
         * Sort object based on description...
         */

        if (dataList.size() > 0)
        {
            Collections.sort(dataList, new Comparator<ProductFamily>()
            {

                @Override
                public int compare(ProductFamily o1, ProductFamily o2)
                {
                    return o1.getDescription().compareTo(o2.getDescription());
                }
            });
        }

        return dataList;
    }

    @Override
    public String generateExcelFile(List<ProductFamily> listValues, String fileName, String path)
    {
        // Need to populate these values into properties files.
        String headers = "LIST_NAME	CODE	LANGUAGE	CONTEXT	DESCRIPTION	DISPLAY_SEQUENCE	IS_ACTIVE	DATA1	DATA2	DATA3	Release";
        return excelRead.generateExcelSheet(listValues, headers, fileName, path);
        
    }

}
