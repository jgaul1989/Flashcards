package flashcards;

import flashcards.model.FlashCards;
import flashcards.view.UserInterface;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        FlashCards cards = new FlashCards();
        String importFile = "";
        String exportFile = "";

        for (int i = 0; i < args.length; i += 2) {
            if ("-import".equals(args[i])) {
                importFile = args[i + 1];
            } else if ("-export".equals(args[i])) {
                exportFile = args[i + 1];
            }
        }
        if (!importFile.isEmpty()) {
            cards.importCard(importFile);
        }

        UserInterface ui = new UserInterface(scanner, cards, exportFile);
        ui.start();

    }
}
