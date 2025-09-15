package org.oladushek;

import org.flywaydb.core.Flyway;
import org.oladushek.view.MainView;

import java.util.Scanner;

import static org.oladushek.config.FlywayConfig.applyMigrations;

public class Main {
    public static void main(String[] args) {

        applyMigrations();
        MainView mainV = new MainView(new Scanner(System.in));
        mainV.startProgram();
    }
}
