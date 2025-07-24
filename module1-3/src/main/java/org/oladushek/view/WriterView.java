package org.oladushek.view;

import lombok.AllArgsConstructor;
import org.oladushek.controller.LabelController;
import org.oladushek.controller.PostController;
import org.oladushek.controller.WriterController;
import org.oladushek.controller.dto.PostDTO;
import org.oladushek.controller.dto.WriterDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class WriterView {
    private final LabelController labelController;
    private final PostController postController;
    private final WriterController writerController;
    private final Scanner scanner;

    public WriterView() {
        this.labelController = new LabelController();
        this.postController = new PostController();
        this.writerController = new WriterController();
        this.scanner = new Scanner(System.in);
    }

    public void handle() {
        boolean running = true;
        while (running) {
            System.out.println("\nWriter CRUD:");
            System.out.println("1. Read writer by ID");
            System.out.println("2. Read All writers");
            System.out.println("3. Create writer with posts");
            System.out.println("4. Create writer without posts");
            System.out.println("5. Update writer");
            System.out.println("6. Add new posts for writer");
            System.out.println("7. Delete writer");
            System.out.println("8. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> readById();
                case "2" -> readAll();
                case "3" -> create();
                case "4" -> createEmpty();
                case "5" -> update();
                case "6" -> addPost();
                case "7" -> delete();
                case "8" -> running = false;
                default -> System.out.println("Invalid input.");
            }
        }
    }


    private void readById() {
        System.out.print("Enter writer id: ");
        long id;
        try{
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e){
            System.err.println("Invalid input post ID.");
            return;
        }
        System.out.println("Desired writer: " + writerController.getById(id));
    }

    private void readAll() {
        List<WriterDTO> writers = writerController.getAll();
        if (writers.isEmpty()) {
            System.out.println("No writers found.");
        } else {
            System.out.print("All active writers: \n");
            writers.forEach(System.out::println);
        }
    }

    private void create() {
        System.out.print("Enter writer first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter writer second name: ");
        String secondName = scanner.nextLine();

        WriterDTO savedWriter = writerController.createWithPosts(firstName, secondName, postsIdForNewWriter());
        System.out.println("Created writer: " + savedWriter);
    }

    private void createEmpty() {
        System.out.print("Enter writer first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter writer second name: ");
        String secondName = scanner.nextLine();
        WriterDTO savedWriter = writerController.createWithoutPosts(firstName, secondName);
        System.out.println("Created writer without posts: " + savedWriter);
    }

    private void update() {
        System.out.print("Enter writer ID to update: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.print("Enter new first name (\"exit\" for skip): ");
        String firstName = scanner.nextLine();

        System.out.print("Enter new second name (\"exit\" for skip): ");
        String secondName = scanner.nextLine();

        WriterDTO updated = writerController.update(id, firstName, secondName, postsIdForNewWriter());
        if (updated != null) {
            System.out.println("Updated: " + updated);
        } else {
            System.out.println("Writer not found.");
        }
    }

    private void addPost() {
        System.out.print("Enter writer ID for add post: ");
        Long id = Long.parseLong(scanner.nextLine());

        WriterDTO updated = writerController.addNewPost(id, createPostForWriter().id());
        if (updated != null) {
            System.out.println("Updated: " + updated);
        } else {
            System.out.println("Writer not found.");
        }
    }

    private void delete() {
        System.out.print("Delete writer with id: ");
        long id;
        try{
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e){
            System.err.println("Invalid input post ID.");
            return;
        }
        writerController.delete(id);
        System.out.println("Writer deleted.");
    }

    private List<Long> postsIdForNewWriter() {
        System.out.println("Existing posts:");
        postController.getAll().forEach(System.out::println);

        List<Long> postsIdForNewWriter = new ArrayList<>();

        System.out.println("Enter \"exit\" for stopping chose posts");
        while (true){
            System.out.print("Chose number post for attach to writer: ");
            String choice = scanner.nextLine();
            if (choice.equals("exit")) {
                return postsIdForNewWriter;
            }
            else {
                try{
                    postsIdForNewWriter.add(Long.parseLong(choice));
                } catch (NumberFormatException e){
                    System.err.println("Invalid input post ID.");
                }
            }
        }
    }

    private PostDTO createPostForWriter() {
        System.out.print("Enter post title: ");
        String title = scanner.nextLine();
        System.out.print("Enter post content: ");
        String content = scanner.nextLine();

        PostDTO savedPost = postController.create(title, content, labelsIdForNewPost());
        System.out.println("Created new post: " + savedPost);
        return savedPost;
    }

    private List<Long> labelsIdForNewPost() {
        System.out.println("Existing labels:");
        labelController.getAll().forEach(System.out::println);

        List<Long> labelsIdForNewPost = new ArrayList<>();

        System.out.println("Enter \"exit\" for stopping chose labels");
        while (true){
            System.out.print("Chose number label for attach to post: ");
            String choice = scanner.nextLine();
            if (choice.equals("exit")) {
                return labelsIdForNewPost;
            }
            else {
                try{
                    labelsIdForNewPost.add(Long.parseLong(choice));
                } catch (NumberFormatException e){
                    System.err.println("Invalid input label ID.");
                }
            }
        }
    }
}
