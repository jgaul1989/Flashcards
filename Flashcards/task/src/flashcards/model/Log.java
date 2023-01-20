package flashcards.model;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Log {
    public static ArrayList<String> logText = new ArrayList<>();

    public static void addLogText(String text) {
        logText.add(text);
    }
    public static void exportLog() {
        System.out.println("File name:");
        logText.add("File name:");
        Scanner scanner = new Scanner(System.in);

        String fileName = scanner.nextLine();
        logText.add(fileName);

        File file = new File(fileName);

        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String line: logText) {
                printWriter.println(line);
            }
            System.out.println("The log has been saved.");
            logText.add("The log has been saved.");
            printWriter.println("The log has been saved.");
            logText.add(String.valueOf(LocalDateTime.now()));
            printWriter.println(LocalDateTime.now());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
