package com.app.db;

import com.app.model.Dokument;
import java.util.List;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

public interface CollectionRepository {
    
    @PostFilter("filterObject.author == principal.username or hasRole('ROLE_ADMIN')")
    public List<Dokument> findAll();
    
    public List<Dokument> findByAuthor(String author);
    
    @PostAuthorize("returnObject.author == principal.username or hasRole('ROLE_ADMIN')")
    @Cacheable(value = "dokumentCache", key = "#title")
    public Dokument findByTitle(String title);
    
    @PreAuthorize("hasRole('ROLE_USER') and #dokument.title.length() <= 10 or hasRole('ROLE_ADMIN')")
    @CachePut(value = "dokumentCache", key = "#result.title")
    public Dokument save(Dokument dokument);
    
    @Secured("ROLE_ADMIN")
    public void delete(String id);
}
