package flashcards.view;

import flashcards.model.FlashCards;
import flashcards.model.Log;

import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;
    private FlashCards cards;
    private String exportFile;

    public UserInterface(Scanner scanner, FlashCards cards, String exportFile) {
        this.scanner = scanner;
        this.cards = cards;
        this.exportFile = exportFile;
    }

    public void start() {
        System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
        Log.addLogText("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
        String userAction = this.scanner.nextLine();
        Log.addLogText(userAction);

        while (!"exit".equals(userAction)) {
            switch (userAction) {
                case "add" -> this.cards.addCard();
                case "remove" -> this.cards.removeCard();
                case "import" -> this.cards.importCard();
                case "export" -> this.cards.exportCard();
                case "ask" -> this.cards.reviewCards();
                case "log" -> Log.exportLog();
                case "hardest card" -> this.cards.checkForMostDifficult();
                case "reset stats" -> this.cards.resetStats();
                default -> System.out.println("Not an action");
            }
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            Log.addLogText("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            userAction = scanner.nextLine();
            Log.addLogText(userAction);
        }
        if (!this.exportFile.isEmpty()) {
            this.cards.exportCard(this.exportFile);
        }
        System.out.println("Bye bye!");
        Log.addLogText("Bye bye!");
    }

}
