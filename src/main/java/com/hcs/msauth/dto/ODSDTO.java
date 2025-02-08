package com.hcs.msauth.dto;

import com.hcs.msauth.entities.ODS;
import com.hcs.msauth.entities.Users;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;

public class ODSDTO extends RepresentationModel<ODSDTO> {
    private String id;
    private String title;
    private String description;
    private int points;
    private String category;
    private Instant createdAt;
    private UsersDTO user;

    public ODSDTO(){}
    public ODSDTO(String id, String title, String description, int points, String category, Instant createdAt){
        this.id = id;
        this.title = title;
        this.description = description;
        this.points = points;
        this.category = category;
        this.createdAt = createdAt;
    }
    public ODSDTO(ODS entity){
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        points = entity.getPoints();
        category = entity.getCategory();
        createdAt = entity.getCreatedAt();
    }
    public ODSDTO(ODS entity, Users user){
        this(entity);
        this.user = new UsersDTO(user);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public UsersDTO getUser() {
        return user;
    }

    public void setUser(UsersDTO user) {
        this.user = user;
    }
}
