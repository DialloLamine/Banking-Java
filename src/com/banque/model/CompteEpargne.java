package com.banque.model;

public class CompteEpargne extends Compte {
    private final double taux; // ex : 0.03 pour 3 %

    public CompteEpargne(String numero, String titulaire, double taux) {
        super(numero, titulaire);
        this.taux = taux;
    }

    /** Ajoute les intérêts au solde et retourne le montant ajouté. */
    public double appliquerInterets() {
        double interets = solde * taux;
        if (interets > 0) {
            deposer(interets, TypeTransaction.INTERETS);
        }
        return interets;
    }

    @Override
    public String getType() {
        return "Épargne";
    }

    public double getTaux() {
        return taux;
    }
}
