package org.oladushek.view;

import lombok.AllArgsConstructor;
import org.oladushek.controller.LabelController;
import org.oladushek.controller.PostController;
import org.oladushek.dto.PostDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class PostView {
    private final LabelController labelController;
    private final PostController postController;
    private final Scanner scanner;

    public PostView() {
        this.postController = new PostController();
        this.labelController = new LabelController();
        this.scanner = new Scanner(System.in);
    }

    public void handle() {
        boolean running = true;
        while (running) {
            System.out.println("\nPost CRUD:");
            System.out.println("1. Read post by ID");
            System.out.println("2. Read All posts");
            System.out.println("3. Read some new posts");
            System.out.println("4. Create post");
            System.out.println("5. Update post");
            System.out.println("6. Delete post");
            System.out.println("7. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> readById();
                case "2" -> readAll();
                case "3" -> readSomeNewPosts();
                case "4" -> create();
                case "5" -> update();
                case "6" -> delete();
                case "7", "exit", "Exit" -> running = false;
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void readById() {
        System.out.print("Enter post id: ");
        long id;
        try{
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e){
            System.err.println("Invalid input label ID.");
            return;
        }
        System.out.println("Desired post: " + postController.getById(id));
    }

    private void readAll() {
        List<PostDTO> posts = postController.getAll();
        if (posts.isEmpty()) {
            System.out.println("No posts found.");
        } else {
            System.out.print("All active posts: \n");
            posts.forEach(System.out::println);
        }
    }

    private void readSomeNewPosts() {
        System.out.print("Enter how many post you want to see: ");
        try {
            int count = Integer.parseInt(scanner.nextLine());
            List<PostDTO> posts = postController.getNewPosts(count);
            if (posts.isEmpty()) {
                System.out.println("No posts found.");
            } else {
                System.out.print("All active posts: \n");
                posts.forEach(System.out::println);
            }
        } catch (NumberFormatException e){
            System.err.println("Invalid input count.");
        }
    }

    private void create() {
        System.out.print("Enter post content: ");
        String content = scanner.nextLine();

        PostDTO savedPost = postController.create(content, labelsIdForNewPost());
        System.out.println("Created post: " + savedPost);
    }

    private void update() {
        System.out.print("Enter post ID to update: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.print("Enter new content (\"exit\" for skip): ");
        String newContent = scanner.nextLine();

        PostDTO updated = postController.update(id, newContent, labelsIdForNewPost());
        if (updated != null) {
            System.out.println("Updated: " + updated);
        } else {
            System.out.println("Label not found.");
        }
    }
    private void delete() {
        System.out.print("Delete post with id: ");
        long id;
        try{
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e){
            System.err.println("Invalid input label ID.");
            return;
        }
        postController.delete(id);
        System.out.println("Post deleted.");
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
