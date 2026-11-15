package com.banque.model;

import com.banque.exception.SoldeInsuffisantException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.LocalDateTime;

public abstract class Compte {
    private final String numero;
    private final String titulaire;
    protected double solde;
    private final List<Transaction> historique = new ArrayList<>();

    protected Compte(String numero, String titulaire) {
        this.numero = numero;
        this.titulaire = titulaire;
    }

    public void deposer(double montant) {
        deposer(montant, TypeTransaction.DEPOT);
    }

    public void deposer(double montant, TypeTransaction type) {
        verifierMontant(montant);
        solde += montant;
        historique.add(new Transaction(LocalDateTime.now(), type, montant));
    }

    public void retirer(double montant) throws SoldeInsuffisantException {
        retirer(montant, TypeTransaction.RETRAIT);
    }

    public void retirer(double montant, TypeTransaction type) throws SoldeInsuffisantException {
        verifierMontant(montant);
        if (montant > soldeDisponible()) {
            throw new SoldeInsuffisantException(soldeDisponible(), montant);
        }
        solde -= montant;
        historique.add(new Transaction(LocalDateTime.now(), type, montant));
    }

    /** Montant maximum retirable (peut être redéfini, ex. découvert autorisé). */
    public double soldeDisponible() {
        return solde;
    }

    public abstract String getType();

    private void verifierMontant(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant doit être strictement positif.");
        }
    }

    public String getNumero() { return numero; }
    public String getTitulaire() { return titulaire; }
    public double getSolde() { return solde; }

    public List<Transaction> getHistorique() {
        return Collections.unmodifiableList(historique);
    }

    @Override
    public String toString() {
        return String.format("%s | %-8s | %-15s | solde : %10.2f €", numero, getType(), titulaire, solde);
    }
}
