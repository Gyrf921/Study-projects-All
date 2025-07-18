package org.oladushek.view;

import lombok.AllArgsConstructor;
import org.oladushek.controller.LabelController;
import org.oladushek.controller.PostController;
import org.oladushek.controller.WriterController;

import java.util.Scanner;

@AllArgsConstructor
public class MainView {

    private final Scanner scanner;
    private final LabelController labelController = new LabelController();
    private final PostController postController = new PostController();
    private final WriterController writerController = new WriterController();

    public MainView() {
        this.scanner = new Scanner(System.in);
    }

    public void startProgram() {
        boolean running = true;
        while (running) {
            System.out.println("\nTake entity for work CRUD:");
            System.out.println("1. Writer");
            System.out.println("2. Post");
            System.out.println("3. Label");
            System.out.println("4. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> new WriterView(labelController, postController, writerController, scanner).handle();
                case "2" -> new PostView(labelController, postController, scanner).handle();
                case "3" -> new LabelView(labelController, scanner).handle();
                case "4" -> running = false;
                default -> System.out.println("Invalid input.");
            }
        }
    }
}
