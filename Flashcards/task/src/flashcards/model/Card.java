package flashcards.model;

public class Card {

    private String definition;
    private int wrongAttempts;

    public Card(String definition) {
        this(definition,0);
    }
    public Card(String definition, int wrongAttempts) {
        this.definition = definition;
        this.wrongAttempts = wrongAttempts;
    }

    public String getDefinition() {
        return definition;
    }

    public void setWrongAttemptsZero() {
        this.wrongAttempts = 0;
        System.out.println("Card statistics have been reset.");
        Log.addLogText("Card statistics have been reset.");
    }

    public int getWrongAttempts() {
        return wrongAttempts;
    }

    public void incrementWrongAttempts() {
        this.wrongAttempts += 1;
    }


}
