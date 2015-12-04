package com.avaya.plds.service;

import java.util.List;

import com.avaya.plds.beans.ProductFamily;

public interface ChangeLogService {
	
	public abstract List<ProductFamily> getApplicationListValues(String filePath) throws Exception;
	public abstract List<ProductFamily> getProductFamilyListValues(String filePath) throws Exception;

}
