
package com.cloud.contact.contactmanager.dao;

import com.cloud.contact.contactmanager.Entities.Contact;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PageContactRepositary extends PagingAndSortingRepository<Contact, Integer> {

    @Query("select c from Contact c where c.user.id = :uid ")
    public Page<Contact> getallContactByUserID(@Param("uid")int uid,Pageable paging);

    @Query("select c from Contact c where c.user.id = :uid and c.name like %:cname%  ")
    public Page<Contact> getallContactByUserIDandCName(@Param("uid")int uid,@Param("cname")String name, Pageable paging);


    
}
