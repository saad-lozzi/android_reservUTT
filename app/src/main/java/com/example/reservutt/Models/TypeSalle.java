package com.example.reservutt.Models;

public class TypeSalle
{
    private String image;

    private String nom;

    public TypeSalle(String image, String nom) {
        this.image = image;
        this.nom = nom;
    }

    public TypeSalle() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
