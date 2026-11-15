package com.banque;

import com.banque.model.Compte;
import com.banque.service.Banque;
import com.banque.ui.MenuConsole;

public class Main {
    public static void main(String[] args) {
        Banque banque = new Banque();

        // Quelques comptes de démonstration
        Compte alice = banque.creerCompteCourant("Alice", 200);
        alice.deposer(500);
        Compte bob = banque.creerCompteEpargne("Bob", 0.03);
        bob.deposer(1000);

        new MenuConsole(banque).demarrer();
    }
}
