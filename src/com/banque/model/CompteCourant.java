package com.banque.model;

public class CompteCourant extends Compte {
    private final double decouvertAutorise;

    public CompteCourant(String numero, String titulaire, double decouvertAutorise) {
        super(numero, titulaire);
        this.decouvertAutorise = decouvertAutorise;
    }

    @Override
    public double soldeDisponible() {
        return solde + decouvertAutorise;
    }

    @Override
    public String getType() {
        return "Courant";
    }

    public double getDecouvertAutorise() {
        return decouvertAutorise;
    }
}
