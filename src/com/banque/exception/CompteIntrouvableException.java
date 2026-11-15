package com.banque.exception;

public class CompteIntrouvableException extends Exception {
    public CompteIntrouvableException(String numero) {
        super("Aucun compte avec le numéro : " + numero);
    }
}
