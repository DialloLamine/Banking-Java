package com.banque.exception;

public class SoldeInsuffisantException extends Exception {
    public SoldeInsuffisantException(double disponible, double demande) {
        super(String.format("Solde insuffisant : disponible %.2f €, demandé %.2f €", disponible, demande));
    }
}
