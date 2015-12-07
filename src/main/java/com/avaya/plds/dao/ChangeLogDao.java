package com.avaya.plds.dao;

import java.util.List;

import com.avaya.plds.beans.ProductFamily;

public interface ChangeLogDao {

	public List<ProductFamily> getApplicationListValues();
	public List<ProductFamily> getProductFamilyValues();
}
