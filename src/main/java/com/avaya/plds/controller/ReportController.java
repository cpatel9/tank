/**
 * 
 */
package com.avaya.plds.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.avaya.plds.email.PLDSEmail;
import com.avaya.plds.excel.PLDSFileRead;

/**
 * @author Kannaiah Yadav
 *
 */
@Controller
@RequestMapping("/report")
public class ReportController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -111954590483183756L;
	private Logger log = Logger.getLogger(getClass().getName());
	
	//@Autowired
	private PLDSFileRead pldsRead;

	@RequestMapping("/layout")
	public String report(){
		return "report/layout";
		
	}
	
	 @RequestMapping(value="/upload" , method=RequestMethod.POST)
		public @ResponseBody String fileUpload(@RequestParam("file") MultipartFile file,@RequestParam("email") String email,HttpServletRequest request) {

			
		// 	log.info("actual servelt context path "+ getServletContext().getRealPath("/"));
			System.out.println(request.getContextPath());
			String path= request.getContextPath()+"/generate".replaceFirst("/", "");
			System.out.println("email value "+ email);
			try{
				if(!file.isEmpty()){
					InputStream stream  = new ByteArrayInputStream(file.getBytes());
					/*BufferedOutputStream bis = new BufferedOutputStream(new FileOutputStream(new File("test.txt")));
					bis.write(file.getBytes());
					bis.close();*/
					/*FileReader fReader = new FileReader(new File("test.txt"));
					if(fReader != null){
						System.out.println(fReader.read());
					}*/
					//System.out.println(new PLDSEmail().sendEmail(email, "test.txt",file.getName()));
				//	PLDSFileRead rbFile = new PLDSFileRead(stream); // added extra parameter Formfile.
					PLDSFileRead pldsRead = new PLDSFileRead();
					pldsRead.setFileStream(stream);
					boolean result = pldsRead.getResult("dummyFilename");
					String finalResult ="";
					if(result){
						 finalResult = new PLDSEmail().sendEmail(email, "In-plds-data.xls",file.getName());
					}
						request.setAttribute("status",finalResult );
					System.out.println("upload done ... ");
					//excelRead.readPLDSPoeticFamilyData(stream,request.getContextPath());
				}
			}
			catch(Exception  e){
			//System.out.println(e);
				return e.getMessage();
			}

			return "success";
		}

}
