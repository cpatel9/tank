/**
 * 
 */
package com.avaya.plds.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.avaya.plds.beans.Entitlements;
import com.avaya.plds.beans.PLDSFormBean;
import com.avaya.plds.service.PLDSService;

@Repository("pldsRead")
public class PLDSFileRead
{

    private File file = null;
    private OPCPackage opcp = null;
    private XSSFWorkbook xssfWorkbook = null; // To handle xlsx files(97-2003 excel format)
    private HSSFWorkbook hworkbook = null, hworkbook1 = null; // to handle xls files (new version of excel format)
    private List<PLDSFormBean> BOMQuoteList = null;
    private List<Entitlements> PLDSRecordList = null, PLDSConvRecordList = null;
    // private Logger log = Logger.getLogger("ReadBOMFile");
    private String fileType = null;
    private Iterator rowIterator = null;
    private String validationErrorInfo; // storing error information if data contains negative quantity.
    private String materialCode;
    private Logger log = Logger.getLogger(getClass().getName());
    private Set<String> salesDocumentSet, convSalesDocumentSet;
    private HSSFSheet originalSheet = null;

    // @Autowired
    private PLDSService pldsService;

    public PLDSFileRead()
    {

    }

    public PLDSFileRead(InputStream stream) throws InvalidFormatException, IOException
    {
        // if("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(fileType)){
        /*
         * opcp = OPCPackage.open(stream); xssfWorkbook = new XSSFWorkbook(opcp); log.info("Creating xlsx object");
         */

        hworkbook = new HSSFWorkbook(stream);
        hworkbook1 = new HSSFWorkbook();
        log.info("Creating xls object");

        /*
         * }else if("application/vnd.ms-excel".equals(fileType) && !file.getName().contains(".csv") ){ hworkbook = new HSSFWorkbook(stream);
         * hworkbook1 = new HSSFWorkbook(); log.info("Creating xls object"); }
         */
    }

    public String getFileType()
    {
        return fileType;
    }

    public void setFileStream(InputStream stream) throws InvalidFormatException, IOException
    {
        hworkbook = new HSSFWorkbook(stream);
        hworkbook1 = new HSSFWorkbook();
        log.info("Creating xls object");
    }

    public void createCellHeaders(HSSFRow inPldsRow, HSSFRow nonPldsRow, HSSFRow summaryRow)
    {
        List<String> headerName = new ArrayList<String>();
        headerName.add("OLD_SALES_ORDER_NO");
        headerName.add("NEW_SALES_ORDER_NO");
        headerName.add("MATERIAL_CODE");
        headerName.add("UNIQUE_KEY");
        headerName.add("ENTITLEMENT_ID");
        headerName.add("AUTHORIZATION_KEY");
        headerName.add("CREATED_DATE");

        short position = 0;
        HSSFFont font = hworkbook1.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        HSSFCellStyle style = hworkbook1.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.GREEN.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderTop((short)1); // single line border
        style.setBorderBottom((short)1); // single line border
        for (String cellLabel : headerName)
        {
            HSSFCell cell = inPldsRow.createCell(position);
            cell.setCellValue(cellLabel);
            cell.setCellStyle(style);
            HSSFCell cell1 = nonPldsRow.createCell(position);
            cell1.setCellValue(cellLabel);
            cell1.setCellStyle(style);
            position++;
        }

        HSSFCell cell = summaryRow.createCell((short)0);
        HSSFCellStyle sStyle = hworkbook1.createCellStyle();
        sStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        sStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cell.setCellValue("Status");
        cell.setCellStyle(sStyle);
        cell = summaryRow.createCell((short)1);
        cell.setCellValue("Count of Sales Document");
        cell.setCellStyle(sStyle);

    }

    public boolean getResult(String fileName) throws IOException, SQLException, ClassNotFoundException, Exception
    {

        log.info("started getResult method ...");
        PLDSService service = new PLDSService();
        log.info("created pldsservice object ...");

        File file = new File("In-plds-data.xls");
        log.info("File path " + file.getAbsolutePath());
        if (file.exists())
        {
            file.delete();
            log.info("deleted existed file form disk...");
        }
        else
        {
            file.createNewFile();
            log.info("file created successfully " + file.getAbsolutePath());
        }
        FileOutputStream fos = new FileOutputStream("In-plds-data.xls", true);

        List<Entitlements> list = service.getEntitlementLists(getPLDSXlsFileData());
        HSSFSheet inPLDSSheet = hworkbook1.createSheet("SAP-PLDS");
        HSSFSheet nonPLDSSheet = hworkbook1.createSheet("SAP-PLDS-Missing");
        HSSFSheet summarySheet = hworkbook1.createSheet("Summary");
        HSSFSheet oSheet = hworkbook1.createSheet("Original Sheet");

        int inpld = 1, nonpld = 1, ocount = 0;
        HSSFRow inPldsRow = inPLDSSheet.createRow(0);
        HSSFRow nonPldsRow = nonPLDSSheet.createRow(0);
        HSSFRow summaryRow = summarySheet.createRow(0);
        createCellHeaders(inPldsRow, nonPldsRow, summaryRow);

        for (Entitlements ent : list)
        {
            short inpldd = 0, nonpldd = 0;
            if (ent.getAuthorizationKey() == null)
            {
                HSSFRow r = nonPLDSSheet.createRow(nonpld);
                r.createCell(nonpldd).setCellValue(ent.getSalesDocument_No());
                r.createCell(++nonpldd).setCellValue(ent.getSalesDocument());
                r.createCell(++nonpldd).setCellValue(ent.getMaterialCode());
                r.createCell(++nonpldd).setCellValue(ent.getUniqueKey());
                r.createCell(++nonpldd).setCellValue(ent.getEntitlementId());
                r.createCell(++nonpldd).setCellValue(ent.getAuthorizationKey());
                r.createCell(++nonpldd).setCellValue(ent.getCreationDate());
                HSSFRow oRow = oSheet.createRow(ocount);
                oRow.createCell((short)0).setCellValue(ent.getSalesDocument_No());
                oRow.createCell((short)1).setCellValue(ent.getMaterialCode_NO());
                nonpld++;
            }
            else
            {
                HSSFRow r = inPLDSSheet.createRow(inpld);
                r.createCell(nonpldd).setCellValue(ent.getSalesDocument_No());
                r.createCell(++nonpldd).setCellValue(ent.getSalesDocument());
                r.createCell(++nonpldd).setCellValue(ent.getMaterialCode());
                r.createCell(++nonpldd).setCellValue(ent.getUniqueKey());
                r.createCell(++nonpldd).setCellValue(ent.getEntitlementId());
                r.createCell(++nonpldd).setCellValue(ent.getAuthorizationKey());
                r.createCell(++nonpldd).setCellValue(ent.getCreationDate());
                HSSFRow oRow = oSheet.createRow(ocount);
                oRow.createCell((short)0).setCellValue(ent.getSalesDocument_No());
                oRow.createCell((short)1).setCellValue(ent.getMaterialCode_NO());
                inpld++;
            }

            ocount++;

        }

        getSummaryReport(summarySheet, nonpld, inpld);

        hworkbook1.write(fos);
        fos.close();
        log.info("file created successfully");
        return true;

    }

    public void getSummaryReport(HSSFSheet summarySheet, int nonpld, int inpld)
    {

        HSSFRow summaryRow1 = summarySheet.createRow(1);

        HSSFCellStyle cStyle = hworkbook1.createCellStyle();
        HSSFCellStyle cStyle1 = hworkbook1.createCellStyle();
        HSSFCellStyle cStyle2 = hworkbook1.createCellStyle();
        cStyle.setFillForegroundColor(HSSFColor.LIME.index);
        cStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        HSSFCell cell = summaryRow1.createCell((short)0);
        cell.setCellValue("SAP-PLDS");
        cell.setCellStyle(cStyle);
        cell = summaryRow1.createCell((short)1);
        cell.setCellValue(inpld - 1);
        cell.setCellStyle(cStyle);

        HSSFRow summaryRow2 = summarySheet.createRow(2);
        cStyle1.setFillForegroundColor(HSSFColor.GOLD.index);
        cStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cell = summaryRow2.createCell((short)0);
        cell.setCellValue("SAP-PLDS-Missing");
        cell.setCellStyle(cStyle1);
        cell = summaryRow2.createCell((short)1);
        cell.setCellValue(nonpld - 1);
        cell.setCellStyle(cStyle1);

        HSSFRow summaryRow3 = summarySheet.createRow(3);
        cStyle2.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        cStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cell = summaryRow3.createCell((short)0);
        cell.setCellStyle(cStyle2);
        cell.setCellValue("Total");
        cell = summaryRow3.createCell((short)1);
        cell.setCellStyle(cStyle2);
        cell.setCellValue(nonpld + inpld - 2);
    }

    /**
     * Comparing file type with different format and returning relative code for a particular file. .xls = 0; .xlsx = 1; .txt = 2; .csv = 3
     * 
     * @return above possible values (0|1|2|3)
     */
    /*
     * public int getTypeCode(){
     * 
     * int fileTypeCode = 0;
     * 
     * if(ErrorMessages.XLS.equals(fileType)){ fileTypeCode= file.getName().contains(ErrorMessages.CSV) ? 3 : 0;} // for .xls(0) file and .csv(3)
     * else if(ErrorMessages.XLSX.equals(fileType)) fileTypeCode = 1; // for .xsls(1) file else if(ErrorMessages.TXT.equals(fileType))
     * fileTypeCode = 2; // for .txt(2) file. else if(ErrorMessages.CSV.equals(fileType)) fileTypeCode = 3; // for .csv file(3).
     * log.info("returning fileTypeCode "+fileTypeCode); return fileTypeCode; }
     */

    /**
     * Method to handle .xls file.
     * 
     * @return
     * @throws IOException
     */
    public List<Entitlements> getPLDSXlsFileData() throws Exception
    {
        log.info("started etPLDSXlsFileData method");
        PLDSRecordList = new ArrayList<Entitlements>();

        PLDSConvRecordList = new ArrayList<Entitlements>();
        if (hworkbook.getSheetAt(0).getPhysicalNumberOfRows() <= 0)
        { // finding no of rows in excel sheet before iterating excel sheet.
            throw new Exception("There are no records found in Excel sheet. Please upload Excel sheet with data");
        }
        else
        {
            iterateExcelSheets(hworkbook.getSheetAt(0), PLDSRecordList, false);
            // iterateExcelSheetsC(hworkbook.getSheetAt(1), PLDSConvRecordList,true);
        }

        iterateExcelSheetsC(PLDSConvRecordList, true);

        log.info("ended with result");

        // log.info(PLDSRecordList.size());
        return PLDSRecordList;
    }

    public String iterateExcelSheetsC(List<Entitlements> BFbeanList, boolean start) throws Exception
    {

        // code to Generate list.ser object to avoid reading converted records from excel.
        /*
         * rowIterator = hssfSheet.rowIterator(); Iterator cellIterator = null; String saleDoc = ""; String matCode = "";
         * 
         * while(rowIterator.hasNext()){ Entitlements BFbean = new Entitlements(); HSSFRow row = (HSSFRow) rowIterator.next(); cellIterator =
         * row.cellIterator(); int type = 0; while(cellIterator.hasNext()){ HSSFCell cell = (HSSFCell)cellIterator.next(); String value ="";
         * switch (cell.getCellType()) {
         * 
         * case HSSFCell.CELL_TYPE_NUMERIC: value = Integer.toString((int)(cell.getNumericCellValue())); if(type <1 ){ saleDoc = value;
         * BFbean.setSalesDocument(saleDoc); BFbean.setSalesDocument_No(saleDoc); }else{ matCode = value; BFbean.setMaterialCode(matCode);
         * BFbean.setMaterialCode_NO(matCode); } break;
         * 
         * case HSSFCell.CELL_TYPE_STRING: value = cell.getStringCellValue(); if(type <1 ){ saleDoc = value; BFbean.setSalesDocument(saleDoc);
         * BFbean.setSalesDocument_No(saleDoc); }else{ matCode = value; BFbean.setMaterialCode(matCode); BFbean.setMaterialCode_NO(matCode); }
         * 
         * break; case HSSFCell.CELL_TYPE_BLANK: default: break; }
         * 
         * type++; } BFbeanList.add(BFbean);
         * 
         * 
         * }
         */

        try
        {

            /*
             * FileOutputStream fileOut; fileOut = new FileOutputStream("D:\\list.ser"); ObjectOutputStream out = new
             * ObjectOutputStream(fileOut); out.writeObject(BFbeanList); out.close(); fileOut.close()
             */;
            ObjectInputStream ois = null;

            FileInputStream fis = new FileInputStream("D:\\list.ser");
            ois = new ObjectInputStream(fis);

            BFbeanList = (List<Entitlements>)ois.readObject();

            ois.close();
            fis.close();
            System.out.println(" bean list length " + BFbeanList.size());
            for (Entitlements ent : BFbeanList)
            {
                System.out.println("salesDOc" + ent.getSalesDocument());
                System.out.println("mat" + ent.getMaterialCode());

                if (start)
                {
                    for (Entitlements record : PLDSRecordList)
                    {
                        // System.out.println(ent.getSalesDocument());
                        if (ent.getSalesDocument() != null && record.getSalesDocument().equals(ent.getSalesDocument()))
                        {
                            record.setSalesDocument(ent.getMaterialCode());
                        }
                    }
                }

            }

        }
        catch (ClassNotFoundException e)
        {
            throw new Exception(" Exception occured at reading parsing Converted Records  " + e.getMessage());
        }
        catch (IOException e)
        {
            throw new Exception(" Exception occured at reading parsing Converted Records  " + e.getMessage());
        }

        log.info("list of beans " + BFbeanList);
        return "";

    }

    public void iterateExcelSheets(HSSFSheet sheet, List<Entitlements> BFbeanList, boolean start)
    {
        originalSheet = sheet;
        rowIterator = sheet.rowIterator();
        Iterator cellIterator = null;
        String saleDoc = "";
        String matCode = "";

        while (rowIterator.hasNext())
        {
            Entitlements BFbean = new Entitlements();
            HSSFRow row = (HSSFRow)rowIterator.next();
            cellIterator = row.cellIterator();
            int type = 0;
            while (cellIterator.hasNext())
            {
                HSSFCell cell = (HSSFCell)cellIterator.next();
                String value = "";
                switch (cell.getCellType())
                {

                    case HSSFCell.CELL_TYPE_NUMERIC:
                        value = Integer.toString((int)(cell.getNumericCellValue()));
                        if (type < 1)
                        {
                            saleDoc = value;
                            BFbean.setSalesDocument(saleDoc);
                            BFbean.setSalesDocument_No(saleDoc);
                        }
                        else
                        {
                            matCode = value;
                            BFbean.setMaterialCode(matCode);
                            BFbean.setMaterialCode_NO(matCode);
                        }
                        break;

                    case HSSFCell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        if (type < 1)
                        {
                            saleDoc = value;
                            BFbean.setSalesDocument(saleDoc);
                            BFbean.setSalesDocument_No(saleDoc);
                        }
                        else
                        {
                            matCode = value;
                            BFbean.setMaterialCode(matCode);
                            BFbean.setMaterialCode_NO(matCode);
                        }

                        break;
                    case HSSFCell.CELL_TYPE_BLANK:
                    default:
                        break;
                }

                type++;
            }
            BFbeanList.add(BFbean);
            BFbean = null;
            if (start)
            {
                for (Entitlements record : PLDSRecordList)
                {
                    if (record.getSalesDocument().equals(saleDoc))
                    {
                        record.setSalesDocument(matCode);
                    }
                }
            }
        }

    }

    /**
     * Method to handle data from textarea. It will split the data based on new line and split based on [\t, \n]
     * 
     * @param tableInputData
     * @return
     */
    public List<PLDSFormBean> formatData(String tableInputData)
    {
        String[] rowItems = tableInputData.split("[,\t ]");
        List<PLDSFormBean> rowItemsList = new ArrayList<PLDSFormBean>();

        if (rowItems.length > 0 && rowItems.length <= 1000)
        {

            for (String id : Arrays.asList(rowItems))
            {
                PLDSFormBean pldsFileBean = new PLDSFormBean();
                pldsFileBean.setEntitlementId(id.trim());
                rowItemsList.add(pldsFileBean);
            }

        }
        else
        {
            // log.info("Error:: "+ErrorMessages.DATA_INFORMAT);
            // setValidationErrorInfo(ErrorMessages.DATA_INFORMAT);
        }

        return rowItemsList;
    }

    public String getValidationErrorInfo()
    {
        return validationErrorInfo;
    }

    public void setValidationErrorInfo(String validationErrorInfo)
    {
        log.info("Setting error Information :: " + validationErrorInfo);
        if (this.validationErrorInfo == "" || this.validationErrorInfo == null)
            this.validationErrorInfo = validationErrorInfo;
    }

    public static void main(String[] args)
    {

        // commented this code as part of UI testing ...
        /*
         * Date Sdate = new Date(); System.out.println("started at " +Sdate.toLocaleString()); String fileName = "D:\\plds.xls"; try {
         * FileInputStream stream = new FileInputStream(new File(fileName)); PLDSFileRead read = new PLDSFileRead(stream);
         * 
         * read.getResult(fileName); } catch (Exception e) { e.printStackTrace(); } System.out.println("end at " +new Date().toLocaleString());
         */
    }

}
