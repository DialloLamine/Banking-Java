package com.banque.model;

public enum TypeTransaction {
    DEPOT("Dépôt"),
    RETRAIT("Retrait"),
    VIREMENT_ENTRANT("Virement reçu"),
    VIREMENT_SORTANT("Virement émis"),
    INTERETS("Intérêts");

    private final String libelle;

    TypeTransaction(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
