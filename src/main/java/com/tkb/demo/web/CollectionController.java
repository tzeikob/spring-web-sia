package com.tkb.demo.web;

import com.tkb.demo.model.Dokument;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.tkb.demo.db.CollectionRepository;

@Controller
public class CollectionController {

    @Autowired
    private CollectionRepository collectionRepository;

    @GetMapping("/c/dokuments")
    public String getDokumentsViewName(Model model) {
        List<Dokument> dokuments;
        
        dokuments = collectionRepository.findAll();

        model.addAttribute("dokuments", dokuments);

        return "collection/dokuments";
    }
    
    @GetMapping("/c/dokuments/{title}")
    public String getDokumentViewName(@PathVariable String title, Model model) {
        Dokument dokument = collectionRepository.findByTitle(title);
        
        model.addAttribute("dokument", dokument);
        
        return "collection/dokument";
    }

    @GetMapping("/c/create/{title}")
    public String createDokument(@PathVariable String title, Authentication auth, Model model) {
        Dokument dokument = new Dokument();
        dokument.setTitle(title);
        dokument.setAuthor(auth.getName());
        collectionRepository.save(dokument);
        
        List<Dokument> dokuments = collectionRepository.findAll();
        model.addAttribute("dokuments", dokuments);
        
        return "collection/dokuments";
    }
    
    @GetMapping("/c/delete/{id}")
    public String deleteDokument(@PathVariable String id, Authentication auth, Model model) {
        collectionRepository.delete(id);
        
        List<Dokument> dokuments = collectionRepository.findAll();
        model.addAttribute("dokuments", dokuments);
        
        return "collection/dokuments";
    }
}
