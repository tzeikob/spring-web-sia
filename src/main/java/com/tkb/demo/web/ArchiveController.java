package com.tkb.demo.web;

import com.tkb.demo.exc.PersonNotFoundException;
import com.tkb.demo.db.ItemRepository;
import com.tkb.demo.db.PersonRepository;
import com.tkb.demo.model.Person;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ArchiveController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/a/register")
    public String getPersonRegistrationViewName(Model model) {
        model.addAttribute("person", new Person());

        return "archive/registration";
    }

    @PostMapping("/a/register")
    public String register(@Valid Person person, @RequestPart MultipartFile avatar,
            RedirectAttributes model, Errors errors) {
        if (errors.hasErrors()) {
            return "archive/registration";
        }
        
        if (personRepository.isNameTaken(person.getName())) {
            return "archive/registration";
        }

        personRepository.save(person);

        model.addAttribute("id", person.getId());
        model.addFlashAttribute("person", person);

        return "redirect:/a/persons/{id}";
    }

    @GetMapping("/a/persons")
    public String getPersonsViewName(Model model) {
        model.addAttribute("persons", personRepository.select());

        return "archive/persons";
    }

    @GetMapping("/a/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Person person = personRepository.find(id);

        if (person == null) {
            throw new PersonNotFoundException(String.valueOf(id));
        } else {
            personRepository.delete(person);
        }

        List<Person> persons = personRepository.select();
        model.addAttribute("persons", persons);

        return "archive/persons";
    }

    @GetMapping("/a/persons/{id}")
    public String getPersonViewName(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("person")) {
            Person person = personRepository.find(id);

            if (person == null) {
                throw new PersonNotFoundException(String.valueOf(id));
            }

            model.addAttribute("person", person);
        }

        model.addAttribute("items", itemRepository.findById(id));

        return "archive/person";
    }
}
