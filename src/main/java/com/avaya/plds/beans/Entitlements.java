package com.avaya.plds.beans;

import java.io.Serializable;

public class Entitlements implements Serializable {
	
	public String salesDocumentIn;
	public String materialCodeIn;
	private String entitlementId;
	private String creationDate;
	private String authorizationKey;
	private String materialCodeOut;
	private String salesDocumentOut;
	private String uniqueKey;
	private String salesDocument_No;
	private String materialCode_NO;


	
	
	public String getSalesDocument() {
		return salesDocumentIn;
	}
	public void setSalesDocument(String salesDocument) {
		this.salesDocumentIn = salesDocument;
	}
	public String getMaterialCode() {
		return materialCodeIn;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCodeIn = materialCode;
	}
	public String getEntitlementId() {
		return entitlementId;
	}
	public void setEntitlementId(String entitlementId) {
		this.entitlementId = entitlementId;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getAuthorizationKey() {
		return authorizationKey;
	}
	public void setAuthorizationKey(String authorizationKey) {
		this.authorizationKey = authorizationKey;
	}
	public String getMaterialCodeOut() {
		return materialCodeOut;
	}
	public void setMaterialCodeOut(String materialCodeOut) {
		this.materialCodeOut = materialCodeOut;
	}
	public String getSalesDocumentOut() {
		return salesDocumentOut;
	}
	public void setSalesDocumentOut(String salesDocumentOut) {
		this.salesDocumentOut = salesDocumentOut;
	}

	public String getUniqueKey() {
		//return uniqueKey;
		return this.salesDocumentIn+this.materialCodeIn;
	}
	public void setUniqueKey(String compositeKey) {
		this.uniqueKey = this.salesDocumentIn.trim()+this.materialCodeIn.trim();
	}
	public String getSalesDocument_No() {
		return salesDocument_No;
	}
	public void setSalesDocument_No(String salesDocument_No) {
		this.salesDocument_No = salesDocument_No;
	}
	public String getMaterialCode_NO() {
		return materialCode_NO;
	}
	public void setMaterialCode_NO(String materialCode_NO) {
		this.materialCode_NO = materialCode_NO;
	}
	

}
