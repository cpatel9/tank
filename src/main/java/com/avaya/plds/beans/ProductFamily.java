/**
 * 
 */
package com.avaya.plds.beans;

import java.io.Serializable;

/**
 * @author Medamoni_Karunakar
 *
 */
public class ProductFamily implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String listName = "FAMILY";
	private String code;
	private String language = "EN"; 
	private String description;
	private String context ="DEFAULT";
	private String is_active = "-1";
	private String data1="";
	private String data2 ="";
	private String data3 ="";
	private String release="";
	private int display_sequence;
	
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setContext(String context){
		this.context = context;
	}
	
	public String getContext(){
		return context;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getData3() {
		return data3;
	}
	public void setData3(String data3) {
		this.data3 = data3;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	
	
	public int getDisplay_sequence() {
		return display_sequence;
	}
	public void setDisplay_sequence(int display_sequence) {
		this.display_sequence = display_sequence;
	}
	
	
	public String toString(){
		
		//#LIST_NAME	CODE	LANGUAGE	CONTEXT	DESCRIPTION	DISPLAY_SEQUENCE	IS_ACTIVE	DATA1	DATA2	DATA3	Release
		return this.listName+"\t"+this.code+"\t"+this.language+"\t"+this.context+"\t"+this.description+"\t"+this.display_sequence+"\t"+this.is_active+"\t"+this.data1+
				"\t"+this.data2+"\t"+this.data3+"\t"+this.release;
	}
	
}
