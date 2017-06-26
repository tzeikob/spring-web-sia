package com.tkb.demo.rest;

import com.tkb.demo.db.ItemRepository;
import com.tkb.demo.model.Item;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class ItemResource {

    @Autowired
    private ItemRepository itemDao;

    @RequestMapping(value = "/items", method = RequestMethod.GET,
            produces = {"application/xml", "application/json"})
    public ResponseEntity<List<Item>> listAll() {
        List<Item> list = itemDao.select();

        if (list.isEmpty()) {
            return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Item>>(list, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET,
            produces = {"application/xml", "application/json"})
    public ResponseEntity<Item> findById(@PathVariable long id) {
        Item item = itemDao.find(id);

        if (item == null) {
            return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Item>(item, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/items/person/{name}", method = RequestMethod.GET,
            produces = {"application/xml", "application/json"})
    public ResponseEntity<List<Item>> findById(@PathVariable String name) {
        List<Item> list = itemDao.findByName(name);

        if (list.isEmpty()) {
            return new ResponseEntity<List<Item>>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Item>>(list, HttpStatus.OK);
        }
    }
}
