package edu.upc.dsa.models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class User {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String birthDate;
    private List<PointOfInterest> pointsOfInterest;


    public User() {
        this.pointsOfInterest = new ArrayList<>();
    }
    public User(String id, String name, String surname, String email, String birthDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.pointsOfInterest = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public List<PointOfInterest> getPointsOfInterest() { return pointsOfInterest; }
    public void setPointsOfInterest(List<PointOfInterest> pointsOfInterest) { this.pointsOfInterest = pointsOfInterest; }
}