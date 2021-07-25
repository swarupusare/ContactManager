/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloud.contact.contactmanager.dao;

import com.cloud.contact.contactmanager.Entities.Contact;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ContactRepository extends JpaRepository<Contact,Integer> {

    @Modifying
    @Query(nativeQuery = true,value = "delete from contact where cid =  :coid")
    @Transactional
    public int deletecontactbyID(@Param("coid") int contactid);
    

    @Query(nativeQuery = true,value = "select * from contact where cid =  :conid")
    public Contact getcontactbyID(@Param("conid") int contactid);
    
    
}
