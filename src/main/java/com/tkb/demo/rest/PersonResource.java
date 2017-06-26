package com.tkb.demo.rest;

import com.tkb.demo.db.ItemRepository;
import com.tkb.demo.db.PersonRepository;
import com.tkb.demo.model.Item;
import com.tkb.demo.model.Person;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class PersonResource {

    @Autowired
    private PersonRepository personDao;

    @Autowired
    private ItemRepository itemDao;

    @RequestMapping(value = "/persons", method = RequestMethod.GET,
            produces = {"application/xml", "application/json"})
    public ResponseEntity<List<Person>> listAll() {
        List<Person> list = personDao.select();

        if (list.isEmpty()) {
            return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Person>>(list, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/persons/{name}", method = RequestMethod.GET,
            produces = {"application/xml", "application/json"})
    public ResponseEntity<Person> findByName(@PathVariable String name) {
        Person person = personDao.findByName(name);

        if (person == null) {
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Person>(person, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/persons", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Person person) {
        if (personDao.isNameTaken(person.getName())) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        personDao.save(person);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/persons/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> update(@PathVariable long id, @RequestBody Person person) {
        Person currentPerson = personDao.find(id);

        if (currentPerson == null) {
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }

        String name = currentPerson.getName();

        if (!name.equals(person.getName()) && personDao.isNameTaken(person.getName())) {
            return new ResponseEntity<Person>(HttpStatus.CONFLICT);
        }

        currentPerson.setName(person.getName());

        personDao.update(currentPerson);

        return new ResponseEntity<Person>(currentPerson, HttpStatus.OK);
    }

    @RequestMapping(value = "/persons/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Person> delete(@PathVariable long id) {
        Person person = personDao.find(id);

        if (person == null) {
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }

        personDao.delete(person);

        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    @RequestMapping(value = "/persons/{id}/items", method = RequestMethod.POST)
    public ResponseEntity<Void> createItem(@PathVariable long id, @RequestBody Item item) {
        Person person = personDao.find(id);

        if (person == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        personDao.createItem(person, item);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
