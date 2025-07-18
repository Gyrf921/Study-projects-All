package org.oladushek;

import org.oladushek.view.MainView;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        MainView mainV = new MainView(new Scanner(System.in));
        mainV.startProgram();
    }
}