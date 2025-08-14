package org.oladushek;

import org.oladushek.config.JDBCConfig;
import org.oladushek.view.MainView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JDBCConfig.getConnection(); //Создаёт Connection и подтягивает liqubase таблицы
        Thread.sleep(1000);
        MainView mainV = new MainView(new Scanner(System.in));
        mainV.startProgram();
    }
}
