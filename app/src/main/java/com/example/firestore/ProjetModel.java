package com.example.firestore;

public class ProjetModel {
    private String name,description,reference,prix,libelle;
    private String imgproduit ;

    public ProjetModel() {
    }

    public ProjetModel(String name, String description, String reference, String prix, String libelle, String imgproduit) {
        this.name = name;
        this.description = description;
        this.reference = reference;
        this.prix = prix;
        this.libelle = libelle;
        this.imgproduit = imgproduit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getImgproduit() {
        return imgproduit;
    }

    public void setImgproduit(String imgproduit) {
        this.imgproduit = imgproduit;
    }
}
