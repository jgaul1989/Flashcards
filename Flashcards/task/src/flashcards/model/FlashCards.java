package flashcards.model;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class FlashCards {

    private Map<String, Card> cardSet = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public void addCard() {
        System.out.println("The card:");
        Log.addLogText("The card:");
        String term = scanner.nextLine();
        Log.addLogText(term);

        if (this.cardSet.containsKey(term)) {
            System.out.printf("The card \"%s\" already exists.\n", term);
            String string = String.format("The card \"%s\" already exists.", term);
            Log.addLogText(string);
            return;
        }
        System.out.println("The definition of the card:");
        Log.addLogText("The definition of the card:");
        String definition = this.scanner.nextLine();
        Log.addLogText(definition);

        for (var currentCard: this.cardSet.entrySet()) {
            if (currentCard.getValue().getDefinition().equals(definition)) {
                System.out.printf("The definition \"%s\" already exists.\n", definition);
                String string = String.format("The definition \"%s\" already exists.", definition);
                Log.addLogText(string);
                return;
            }
        }
        this.cardSet.put(term, new Card(definition));
        System.out.printf("The pair (\"%s\":\"%s\") has been added.\n", term, definition);
        String string = String.format("The pair (\"%s\":\"%s\") has been added.", term, definition);
        Log.addLogText(string);
    }

    public void removeCard() {
        System.out.println("Which card?");
        Log.addLogText("Which card?");
        String termToRemove = this.scanner.nextLine();
        Log.addLogText(termToRemove);

        if (this.cardSet.containsKey(termToRemove)) {
            this.cardSet.remove(termToRemove);
            System.out.println("The card has been removed.");
            Log.addLogText("The card has been removed.");
        } else {
            System.out.printf("Can't remove \"%s\": there is no such card.\n", termToRemove);
            String string = String.format("Can't remove \"%s\": there is no such card.", termToRemove);
            Log.addLogText(string);
        }
    }

    public void importCard() {
        System.out.println("File name:");
        Log.addLogText("File name:");
        String fileName = this.scanner.nextLine();
        Log.addLogText(fileName);

        File file = new File(fileName);
        try (Scanner reader = new Scanner(file)) {
            int countCards = 0;
            while (reader.hasNext()) {
                String currentCard = reader.nextLine();
                String[] parts = currentCard.split(",");
                this.cardSet.put(parts[0], new Card(parts[1], Integer.parseInt(parts[2])));
                countCards += 1;
            }
            System.out.println(countCards + " cards have been loaded.");
            Log.addLogText(countCards + " cards hav been loaded");

        } catch (Exception e) {
            System.out.println("File not found.");
            Log.addLogText("File not found.");
        }
    }

    public void importCard(String fileName) {

        File file = new File(fileName);
        try (Scanner reader = new Scanner(file)) {
            int countCards = 0;
            while (reader.hasNext()) {
                String currentCard = reader.nextLine();
                String[] parts = currentCard.split(",");
                this.cardSet.put(parts[0], new Card(parts[1], Integer.parseInt(parts[2])));
                countCards += 1;
            }
            System.out.println(countCards + " cards have been loaded.");
            Log.addLogText(countCards + " cards hav been loaded");

        } catch (Exception e) {
            System.out.println("File not found.");
            Log.addLogText("File not found.");
        }
    }
    public void exportCard() {
        System.out.println("File name:");
        Log.addLogText("File name:");
        String filePath = this.scanner.nextLine();
        Log.addLogText(filePath);

        File file = new File(filePath);

        try (PrintWriter printWriter = new PrintWriter(file)) {
            int numCards = 0;
            for (var currentCard: this.cardSet.entrySet()) {
                printWriter.println(currentCard.getKey() + "," + currentCard.getValue().getDefinition() + "," + currentCard.getValue().getWrongAttempts());
                numCards += 1;
            }
            System.out.println(numCards + " cards have been saved.");
            Log.addLogText(numCards + " cards have been saved.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void exportCard(String fileName) {
        File file = new File(fileName);

        try (PrintWriter printWriter = new PrintWriter(file)) {
            int numCards = 0;
            for (var currentCard: this.cardSet.entrySet()) {
                printWriter.println(currentCard.getKey() + "," + currentCard.getValue().getDefinition() + "," + currentCard.getValue().getWrongAttempts());
                numCards += 1;
            }
            System.out.println(numCards + " cards have been saved.");
            Log.addLogText(numCards + " cards have been saved.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void reviewCards() {
        List<String> keys = new ArrayList<>(this.cardSet.keySet());
        Random random = new Random();

        System.out.println("How many times to ask?");
        Log.addLogText("How many times to ask?");
        int numCards = Integer.valueOf(this.scanner.nextLine());
        Log.addLogText(String.valueOf(numCards));

        for (int i = 0; i < numCards; i++) {
            String randomKey = keys.get(random.nextInt(keys.size()));
            System.out.printf("Print the definition of \"%s\":\n", randomKey);
            String string = String.format("Print the definition of \"%s\":", randomKey);
            Log.addLogText(string);

            String userInput = this.scanner.nextLine();
            Log.addLogText(userInput);

            if (userInput.equals(this.cardSet.get(randomKey).getDefinition())) {
                System.out.println("Correct!");
                Log.addLogText("Correct");
                continue;
            }
            this.cardSet.get(randomKey).incrementWrongAttempts();
            boolean definitionFound = false;
            for (var currentCard: this.cardSet.entrySet()) {
                if (currentCard.getValue().getDefinition().equals(userInput)) {
                    System.out.printf("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".\n", this.cardSet.get(randomKey).getDefinition(), currentCard.getKey());
                    String addToLog = String.format("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".", this.cardSet.get(randomKey).getDefinition(), currentCard.getKey());
                    Log.addLogText(addToLog);
                    definitionFound = true;
                }
            }
            if(!definitionFound) {
                System.out.printf("Wrong. The right answer is \"%s\".\n", this.cardSet.get(randomKey).getDefinition());
                String addToLog = String.format("Wrong. The right answer is \"%s\".", this.cardSet.get(randomKey).getDefinition());
                Log.addLogText(addToLog);
            }
        }
    }

    public void checkForMostDifficult() {
        int numWrongAttempts = 0;
        ArrayList<String> hardestCards = new ArrayList<>();

        for (var currentCard: this.cardSet.entrySet()) {
            if (currentCard.getValue().getWrongAttempts() > numWrongAttempts) {
                numWrongAttempts = currentCard.getValue().getWrongAttempts();
            }
        }
        for (var currentCard: this.cardSet.entrySet()) {
            if (currentCard.getValue().getWrongAttempts() == numWrongAttempts) {
                hardestCards.add(currentCard.getKey());
            }
        }

        if (numWrongAttempts == 0) {
            System.out.println("There are no cards with errors.");
            Log.addLogText("There are no cards with errors.");
        } else if (hardestCards.size() > 1){
            String formatText = "The hardest cards are";
            for (String word: hardestCards) {
                formatText = String.format("%s \"%s\",", formatText, word);
            }
            String removeLastComma = formatText.substring(0,formatText.length() - 1);
            System.out.println(removeLastComma + "." + " You have " + numWrongAttempts + " errors answering them.");
            Log.addLogText(removeLastComma + "." + " You have " + numWrongAttempts + " errors answering them.");
        } else {
            System.out.printf("The hardest card is \"%s\". You have %d errors answering it.\n", hardestCards.get(0), numWrongAttempts);
            String addToLog = String.format("The hardest card is \"%s\". You have %d errors answering it.", hardestCards.get(0), numWrongAttempts);
            Log.addLogText(addToLog);
        }
    }

    public void resetStats(){
        for (Card currentCard: this.cardSet.values()) {
            currentCard.setWrongAttemptsZero();
        }
    }
}
