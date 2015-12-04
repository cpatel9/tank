package com.avaya.plds.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.avaya.plds.excel.ExcelRead;
import com.avaya.plds.service.FileUploadService;



@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired
	public ExcelRead excelRead;
    //TODO: add different ways to drop files
	
	@Autowired
	public FileUploadService fileUploadService;

    @RequestMapping("/layout")
    public String layout(){
        return "file/layout";
    }

    @RequestMapping(value="/upload" , method=RequestMethod.POST)
	public @ResponseBody String fileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request){

		// getting context path 

		System.out.println(request.getContextPath());
		String path= request.getContextPath();

		try{
			if(!file.isEmpty()){
				InputStream stream  = new ByteArrayInputStream(file.getBytes());
				Map<String,Map> resultMap = fileUploadService.generateProductAndComponentLoad(stream,path);
				fileUploadService.getProductFamilyListValues(resultMap.get("product"));
				fileUploadService.getApplicationListValues(resultMap.get("application"));
			
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return e.getMessage();
		}

		return "success";
	}
}
