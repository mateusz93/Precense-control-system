package neo.dmcs.repository;

import neo.dmcs.model.Contact;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Integer> {

    Contact findByEmail(String email);
    List<Contact> findByGroup(String group);
    Contact findByPhone(String phone);
    List<Contact> findByCity(String city);
}
