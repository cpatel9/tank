package com.avaya.plds.dao;

import java.util.List;

import com.avaya.plds.model.EmployeeVO;

public interface EmployeeDAO
{
    public List<EmployeeVO> getAllEmployees();
}