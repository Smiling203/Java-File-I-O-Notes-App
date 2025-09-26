import java.io.*;
import java.util.*;

public class NotesManager {
    private static final String FILE_NAME = "notes.txt";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            runMenu(scanner);
        }
    }

    private static void runMenu(Scanner scanner) {
        while (true) {
            printMenu();
            int choice = readChoice(scanner);

            switch (choice) {
                case 1 -> addNote(scanner);
                case 2 -> showNotes();
                case 3 -> deleteNote(scanner);
                case 4 -> {
                    System.out.println("Exiting... Bye!");
                    return;
                }
                default -> System.out.println("⚠ Invalid choice! Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== NOTES MANAGER =====");
        System.out.println("1. Add Note");
        System.out.println("2. Show All Notes");
        System.out.println("3. Delete a Note");
        System.out.println("4. Exit");
        System.out.print("Enter choice: ");
    }

    private static int readChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private static void addNote(Scanner scanner) {
        System.out.print("Write your note: ");
        String note = scanner.nextLine().trim();

        if (note.isEmpty()) {
            System.out.println(" Empty note not saved!");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            pw.println(note);
            System.out.println(" Note saved!");
        } catch (IOException e) {
            System.out.println(" Error saving note: " + e.getMessage());
        }
    }

    private static void showNotes() {
        List<String> notes = loadNotes();
        if (notes.isEmpty()) {
            System.out.println("(No notes found)");
            return;
        }

        System.out.println("\n--- Your Notes ---");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i));
        }
    }

    private static void deleteNote(Scanner scanner) {
        List<String> notes = loadNotes();
        if (notes.isEmpty()) {
            System.out.println(" No notes available to delete.");
            return;
        }

        showNotes();
        System.out.print("Enter note number to delete: ");
        int num = readChoice(scanner);

        if (num < 1 || num > notes.size()) {
            System.out.println(" Invalid note number!");
            return;
        }

        notes.remove(num - 1);
        saveNotes(notes);
        System.out.println(" Note deleted successfully!");
    }

    private static List<String> loadNotes() {
        List<String> notes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                notes.add(line);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("Error reading notes: " + e.getMessage());
        }
        return notes;
    }

    private static void saveNotes(List<String> notes) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String note : notes) {
                pw.println(note);
            }
        } catch (IOException e) {
            System.out.println("Error saving notes: " + e.getMessage());
        }
    }
}
