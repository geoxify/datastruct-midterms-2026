package final_program.quizzer.model;

public class Player {
    private String name;
    private String password;
    private int score;
    private int total;

    public Player(String name, String password, int score, int total) {
        this.name = name;
        this.password = password;
        this.score = score;
        this.total = total;
    }

    public String getName() { return name; }
    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }

    public int getScore() { return score; }
    public int getTotal() { return total; }

    public void updateScore(int score, int total) {
        this.score = score;
        this.total = total;
    }

    public String getScoreString() {
        return score + "/" + total;
    }
}