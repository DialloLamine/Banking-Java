package com.banque.service;

import com.banque.exception.CompteIntrouvableException;
import com.banque.exception.SoldeInsuffisantException;
import com.banque.model.Compte;
import com.banque.model.CompteCourant;
import com.banque.model.CompteEpargne;
import com.banque.model.TypeTransaction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Banque {
    private final Map<String, Compte> comptes = new LinkedHashMap<>();
    private int compteur = 0;

    public CompteCourant creerCompteCourant(String titulaire, double decouvert) {
        CompteCourant compte = new CompteCourant(genererNumero(), titulaire, decouvert);
        comptes.put(compte.getNumero(), compte);
        return compte;
    }

    public CompteEpargne creerCompteEpargne(String titulaire, double taux) {
        CompteEpargne compte = new CompteEpargne(genererNumero(), titulaire, taux);
        comptes.put(compte.getNumero(), compte);
        return compte;
    }

    public Compte trouver(String numero) throws CompteIntrouvableException {
        Compte compte = comptes.get(numero.trim().toUpperCase());
        if (compte == null) {
            throw new CompteIntrouvableException(numero);
        }
        return compte;
    }

    public void virement(String source, String destination, double montant)
            throws CompteIntrouvableException, SoldeInsuffisantException {
        if (source.trim().equalsIgnoreCase(destination.trim())) {
            throw new IllegalArgumentException("Les deux comptes sont identiques.");
        }
        Compte a = trouver(source);
        Compte b = trouver(destination);
        // On retire d'abord : si le solde est insuffisant, rien n'est modifié.
        a.retirer(montant, TypeTransaction.VIREMENT_SORTANT);
        b.deposer(montant, TypeTransaction.VIREMENT_ENTRANT);
    }

    public double appliquerInterets(String numero) throws CompteIntrouvableException {
        Compte compte = trouver(numero);
        if (!(compte instanceof CompteEpargne epargne)) {
            throw new IllegalArgumentException("Ce n'est pas un compte épargne.");
        }
        return epargne.appliquerInterets();
    }

    public List<Compte> tousLesComptes() {
        return new ArrayList<>(comptes.values());
    }

    private String genererNumero() {
        return String.format("CPT-%04d", ++compteur);
    }
}
