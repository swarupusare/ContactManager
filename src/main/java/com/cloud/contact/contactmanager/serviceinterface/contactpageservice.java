
package com.cloud.contact.contactmanager.serviceinterface;

import com.cloud.contact.contactmanager.Entities.Contact;
import java.util.List;

public interface contactpageservice {
   List<Contact> findPaginated(int pageNo, int pageSize);
}
