package com.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Dokument implements Serializable {

    @Id
    private String id;

    private String title;

    private String author;

    private List<Comment> comments;

    public Dokument() {
        comments = new ArrayList<>();
    }

    public Dokument(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        comments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public String toString() {
        return "Dokument{" + "id=" + id
                + ", title=" + title
                + ", author=" + author
                + ", comments=" + comments.size() + "}";
    }
}
