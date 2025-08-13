package org.oladushek.view;

import lombok.AllArgsConstructor;
import org.oladushek.controller.LabelController;
import org.oladushek.controller.dto.LabelDTO;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class LabelView {
    private final LabelController labelController;
    private final Scanner scanner;

    public LabelView() {
        this.labelController = new LabelController();
        this.scanner = new Scanner(System.in);
    }

    public void handle() {
        boolean running = true;
        while (running) {
            System.out.println("\nLabel CRUD:");
            System.out.println("1. Read Label by ID");
            System.out.println("2. Read All Labels");
            System.out.println("3. Create Label");
            System.out.println("4. Update Label");
            System.out.println("5. Delete Label");
            System.out.println("6. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> readById();
                case "2" -> readAll();
                case "3" -> create();
                case "4" -> update();
                case "5" -> delete();
                case "6" -> running = false;
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void readById() {
        System.out.print("Enter label id: ");
        long id;
        try{
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e){
            System.err.println("Invalid input label ID.");
            return;
        }
        System.out.println("Desired label: " + labelController.getById(id));
    }

    private void readAll() {
        List<LabelDTO> labels = labelController.getAll();
        if (labels.isEmpty()) {
            System.out.println("No labels found.");
        } else {
            System.out.print("All active labels: \n");
            labels.forEach(System.out::println);
        }
    }

    private void create() {
        System.out.print("Enter label name: ");
        String name = scanner.nextLine();
        LabelDTO label = labelController.create(name);
        System.out.println("Created label: " + label);
    }

    private void update() {
        System.out.print("Enter label ID to update: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();

        LabelDTO updated = labelController.update(id, newName);
        if (updated != null) {
            System.out.println("Updated: " + updated);
        } else {
            System.out.println("Label not found.");
        }
    }

    private void delete() {
        System.out.print("Enter label ID to delete: ");
        long id;
        try{
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e){
            System.err.println("Invalid input label ID.");
            return;
        }
        labelController.delete(id);
        System.out.println("Label deleted.");
    }
}
