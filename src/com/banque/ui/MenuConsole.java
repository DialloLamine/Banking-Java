package com.banque.ui;

import com.banque.exception.CompteIntrouvableException;
import com.banque.exception.SoldeInsuffisantException;
import com.banque.model.Compte;
import com.banque.model.Transaction;
import com.banque.service.Banque;

import java.util.List;
import java.util.Scanner;

public class MenuConsole {
    private final Banque banque;
    private final Scanner scanner = new Scanner(System.in);

    public MenuConsole(Banque banque) {
        this.banque = banque;
    }

    public void demarrer() {
        boolean continuer = true;
        while (continuer) {
            afficherMenu();
            String choix = lireTexte("Ton choix : ");
            try {
                switch (choix) {
                    case "1" -> creerCourant();
                    case "2" -> creerEpargne();
                    case "3" -> depot();
                    case "4" -> retrait();
                    case "5" -> virement();
                    case "6" -> historique();
                    case "7" -> listerComptes();
                    case "8" -> interets();
                    case "0" -> continuer = false;
                    default -> System.out.println("Choix invalide.");
                }
            } catch (SoldeInsuffisantException | CompteIntrouvableException | IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
        System.out.println("Au revoir !");
    }

    private void afficherMenu() {
        System.out.println();
        System.out.println("===== BANQUE =====");
        System.out.println("1. Créer un compte courant");
        System.out.println("2. Créer un compte épargne");
        System.out.println("3. Dépôt");
        System.out.println("4. Retrait");
        System.out.println("5. Virement");
        System.out.println("6. Historique d'un compte");
        System.out.println("7. Lister les comptes");
        System.out.println("8. Appliquer les intérêts (épargne)");
        System.out.println("0. Quitter");
    }

    private void creerCourant() {
        String nom = lireTexte("Titulaire : ");
        double decouvert = lireMontant("Découvert autorisé (€) : ");
        Compte c = banque.creerCompteCourant(nom, decouvert);
        System.out.println("Compte créé : " + c);
    }

    private void creerEpargne() {
        String nom = lireTexte("Titulaire : ");
        double tauxPourcent = lireMontant("Taux d'intérêt (%) : ");
        Compte c = banque.creerCompteEpargne(nom, tauxPourcent / 100);
        System.out.println("Compte créé : " + c);
    }

    private void depot() throws CompteIntrouvableException {
        Compte c = banque.trouver(lireTexte("N° de compte : "));
        c.deposer(lireMontant("Montant (€) : "));
        System.out.println("Dépôt effectué. " + c);
    }

    private void retrait() throws CompteIntrouvableException, SoldeInsuffisantException {
        Compte c = banque.trouver(lireTexte("N° de compte : "));
        c.retirer(lireMontant("Montant (€) : "));
        System.out.println("Retrait effectué. " + c);
    }

    private void virement() throws CompteIntrouvableException, SoldeInsuffisantException {
        String source = lireTexte("Compte source : ");
        String destination = lireTexte("Compte destinataire : ");
        double montant = lireMontant("Montant (€) : ");
        banque.virement(source, destination, montant);
        System.out.println("Virement effectué.");
    }

    private void historique() throws CompteIntrouvableException {
        Compte c = banque.trouver(lireTexte("N° de compte : "));
        List<Transaction> transactions = c.getHistorique();
        System.out.println(c);
        if (transactions.isEmpty()) {
            System.out.println("(aucune opération)");
        }
        transactions.forEach(System.out::println);
    }

    private void listerComptes() {
        List<Compte> comptes = banque.tousLesComptes();
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte.");
        }
        comptes.forEach(System.out::println);
    }

    private void interets() throws CompteIntrouvableException {
        double montant = banque.appliquerInterets(lireTexte("N° de compte épargne : "));
        System.out.printf("Intérêts ajoutés : %.2f €%n", montant);
    }

    private String lireTexte(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private double lireMontant(String message) {
        while (true) {
            String saisie = lireTexte(message).replace(',', '.');
            try {
                return Double.parseDouble(saisie);
            } catch (NumberFormatException e) {
                System.out.println("Merci d'entrer un nombre valide.");
            }
        }
    }
}
