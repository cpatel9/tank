package com.avaya.plds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avaya.plds.model.Shift;
import com.avaya.plds.service.ShiftService;


@Controller
@RequestMapping("/shifts")
public class TimeSlotController {

    @Autowired
    ShiftService shiftService;

    @RequestMapping(value = "/shiftinfo/all", method = RequestMethod.GET)
    public @ResponseBody List<Shift> getAllShifts(){
        return shiftService.getAllShifts();
    }

    /*@RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody void addAddressBookEntry(@RequestBody AddressBook addressBook){
        addressBookService.createAddressBook(addressBook);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteAddressBookEntry(@PathVariable("id") String id){
        addressBookService.deleteAddressBook(Integer.valueOf(id));
    }

    @RequestMapping(value = "/update/{pos}", method = RequestMethod.PUT)
    public @ResponseBody void updateAddressBook(@RequestBody AddressBook addressBook, @PathVariable("pos") String pos){
        addressBookService.updateAddressBook(Integer.valueOf(pos), addressBook);
    }

    @RequestMapping(value="/delete/all", method = RequestMethod.DELETE)
    public @ResponseBody void deleteAllAddressBook(){
        addressBookService.deleteAllAddressBook();
    }*/

    @RequestMapping("/layout")
    public String getTodoPartialPage() {
        return "shifts/layout";
    }
}


