package com.banque.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Record Java (Java 16+) : classe immuable avec getters automatiques. */
public record Transaction(LocalDateTime date, TypeTransaction type, double montant) {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Override
    public String toString() {
        return String.format("%s | %-14s | %10.2f €", date.format(FORMAT), type.getLibelle(), montant);
    }
}
