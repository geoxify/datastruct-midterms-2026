package final_program.quizzer.model;

public class Question {
    private String promptText;
    private String[] options;
    private char correctAnswer;
    private boolean isAnswered;

    public Question(String promptText, String[] options, char correctAnswer) {
        this.promptText = promptText;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.isAnswered = false;
    }

    public String getPromptText() { return promptText; }
    public void setPromptText(String promptText) { this.promptText = promptText; }

    public String[] getOptions() { return options; }

    public char getCorrectAnswer() { return correctAnswer; }

    public boolean isAnswered() { return isAnswered; }
    public void setAnswered(boolean answered) { isAnswered = answered; }
}