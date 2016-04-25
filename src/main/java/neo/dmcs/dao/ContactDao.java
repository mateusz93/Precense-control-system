package neo.dmcs.dao;

import neo.dmcs.model.Contact;
import neo.dmcs.model.User;

import java.util.List;

public interface ContactDao extends GenericDao<Contact, Integer> {

    Contact findByEmail(String email);
    List<Contact> findByGroup(String group);
    Contact findByPhone(String phone);
    List<Contact> findByCity(String city);
    List<Contact> findAll();
}
