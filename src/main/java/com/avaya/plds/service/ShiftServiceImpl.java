package com.avaya.plds.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.avaya.plds.model.Shift;


@Service
public class ShiftServiceImpl implements ShiftService {

	@Override
	public List<Shift> getAllShifts() {
		// TODO Auto-generated method stub
		
		List<Shift> list=new ArrayList<Shift>();
		
		Shift shift=new Shift();
		shift.setName("M karunakar");
		shift.setShift("First Shift");
		shift.setEmail("mkarunakar@avaya.com");
		shift.setContact("9491388035");
		shift.setDate("30-03-2015");
		list.add(shift);
		
		Shift shift2=new Shift();
		
		shift2.setName("M karunakar2");
		shift2.setShift("First Shift");
		shift2.setEmail("mkarunakar@avaya.com");
		shift2.setContact("9491388035");
		shift2.setDate("29-03-2015");
		
		list.add(shift2);
		
		
		return list;
	}

    
}
