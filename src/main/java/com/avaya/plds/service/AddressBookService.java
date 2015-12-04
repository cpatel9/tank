package com.avaya.plds.service;

import com.avaya.plds.model.AddressBook;

import java.util.List;

/**
 * Created by tmichels on 8/3/14.
 */
public interface AddressBookService {
     List<AddressBook> viewAllAddressBook();
     void createAddressBook(AddressBook addressBook);
     void updateAddressBook(int pos, AddressBook updateAddressBook);
     void deleteAddressBook(int id);
     void deleteAllAddressBook();
     AddressBook findAddressBook(int id);
}
