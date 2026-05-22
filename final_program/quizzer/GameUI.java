package final_program.quizzer;

import com.geoxify.model.Player;
import com.geoxify.model.Question;
import com.geoxify.service.QuizEngineService;
import com.geoxify.util.DataManagerUtil;
import com.geoxify.util.InputValidator;


import java.util.List;

public class GameUI {
    private DataManagerUtil dataManager;
    private List<Question> questionBank;
    private List<Player> players;
    private Player currentPlayer;

    public GameUI() {
        dataManager = new DataManagerUtil();
        questionBank = dataManager.loadQuestions();
        players = dataManager.loadPlayers();
    }

    public static void main(String[] args) {
        new GameUI().start();
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n┌──────────────────────────────────────┐");
            System.out.println("│           TRIVIA MACHINE             │");
            System.out.println("├──────────────────────────────────────┤");
            System.out.println("│        [1] Player Management         │");
            System.out.println("│        [2] Question Bank             │");
            System.out.println("│        [3] Play Game                 │");
            System.out.println("│        [4] Exit                      │");
            System.out.println("└──────────────────────────────────────┘");

            int choice = new InputValidator<Integer>().getValidInput("Selection ", Integer::parseInt, i -> i >= 1 && i <= 4);

            switch (choice) {
                case 1 -> playerManagementMenu();
                case 2 -> questionBankMenu();
                case 3 -> startPlaySession();
                case 4 -> {
                    dataManager.saveQuestions(questionBank);
                    dataManager.savePlayers(players);
                    System.out.println("\n[!] All records updated. Goodbye!");
                    running = false;
                }
            }
        }
    }

    // --- PLAYER MANAGEMENT UI ---
    private void playerManagementMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- PLAYER MANAGEMENT ---");
            if (currentPlayer != null) System.out.println("Current Player: " + currentPlayer.getName());
            System.out.println("[1] Register/Login\n[2] List All\n[3] Leaderboard\n[4] Back");
            int c = new InputValidator<Integer>().getValidInput("Choice ", Integer::parseInt, i -> i >= 1 && i <= 4);

            switch (c) {
                case 1 -> authenticatePlayer();
                case 2 -> listPlayers();
                case 3 -> displayLeaderboard();
                case 4 -> inMenu = false;
            }
        }
    }

    private void authenticatePlayer() {
        InputValidator<String> s = new InputValidator<>();
        String name = s.getValidInput("Name", v -> v.trim(), v -> !v.isEmpty());
        String password = s.getValidInput("Password", v -> v.trim(), v -> !v.isEmpty());

        currentPlayer = players.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        if (currentPlayer == null) {
            currentPlayer = new Player(name, password, 0, 0);
            players.add(currentPlayer);
            dataManager.savePlayers(players);
            System.out.println("[✔] Registration Success.");
        } else if (currentPlayer.getPassword().equals(password)) {
            System.out.println("[✔] Login Success.");
        } else {
            System.out.println("[!] Incorrect Password.");
            currentPlayer = null;
        }
    }

    private void listPlayers() {
        if (players.isEmpty()) System.out.println("No records found.");
        for (Player p : players) System.out.println("Name: " + p.getName() + " | Score: " + p.getScoreString());
    }

    private void displayLeaderboard() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║            GAME LEADERBOARD              ║");
        System.out.println("╠═══════════════════════════╦══════════════╣");
        System.out.printf("║ %-25s ║ %-12s ║\n", "PLAYER NAME", "SCORE");
        System.out.println("╠═══════════════════════════╬══════════════╣");

        if (players.isEmpty()) {
            System.out.printf("║ %-25s ║ %-12s ║\n", "No records", "N/A");
        } else {
            for (Player p : players) {
                System.out.printf("║ %-25s ║ %-12s ║\n", p.getName(), p.getScoreString());
            }
        }
        System.out.println("╚═══════════════════════════╩══════════════╝");
    }

    // --- QUESTION BANK UI ---
    private void questionBankMenu() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- QUESTION BANK ---");
            System.out.println("[1] Add Question\n[2] List All\n[3] Back");
            int choice = new InputValidator<Integer>().getValidInput("Choice ", Integer::parseInt, i -> i >= 1 && i <= 3);

            switch (choice) {
                case 1 -> addQuestion();
                case 2 -> listQuestions();
                case 3 -> inMenu = false;
            }
        }
    }

    private void addQuestion() {
        InputValidator<String> s = new InputValidator<>();
        System.out.println("\n(Type 'back' to cancel)");
        String qText = s.getValidInput("Enter Question", val -> val.trim(), val -> !val.isEmpty());

        if (qText.equalsIgnoreCase("back")) return;

        String[] opts = {
                "a. " + s.getValidInput("Option A", v -> v.trim(), v -> !v.isEmpty()),
                "b. " + s.getValidInput("Option B", v -> v.trim(), v -> !v.isEmpty()),
                "c. " + s.getValidInput("Option C", v -> v.trim(), v -> !v.isEmpty()),
                "d. " + s.getValidInput("Option D", v -> v.trim(), v -> !v.isEmpty())
        };
        char ans = new InputValidator<Character>().getValidInput("Correct Answer (a-d)",
                v -> v.toLowerCase().trim().charAt(0), v -> v >= 'a' && v <= 'd');

        questionBank.add(new Question(qText, opts, ans));
        dataManager.saveQuestions(questionBank);
        System.out.println("[✔] Question added.");
    }

    private void listQuestions() {
        if (questionBank.isEmpty()) System.out.println("Empty.");
        else for (int i = 0; i < questionBank.size(); i++) System.out.println((i + 1) + ". " + questionBank.get(i).getPromptText());
    }

    // --- PLAY MODULE UI ---
    private void startPlaySession() {
        if (currentPlayer == null) {
            System.out.println("\n[!] Please register/login first.");
            return;
        }
        if (questionBank.isEmpty()) {
            System.out.println("\n[!] No questions found in file.");
            return;
        }

        QuizEngineService engine = new QuizEngineService(questionBank);
        boolean isPlaying = true;

        while (isPlaying) {
            Question q = engine.getCurrentQuestion();
            System.out.println("\n========================================");
            System.out.println(" QUESTION " + engine.getCurrentIndex() + " of " + engine.getTotalQuestions());
            System.out.println(" Current Score: " + engine.getScore());
            System.out.println("========================================");
            System.out.println(q.getPromptText());
            for (String opt : q.getOptions()) System.out.println(opt);

            String menu = q.isAnswered() ? "| [2] Next | [3] Prev | [4] Submit |" : "| [1] Answer | [2] Next | [3] Prev | [4] Submit |";
            System.out.println("\n" + menu);

            int choice = new InputValidator<Integer>().getValidInput("Selection ", Integer::parseInt, i -> i >= 1 && i <= 4);

            switch (choice) {
                case 1 -> {
                    if (!q.isAnswered()) {
                        char a = new InputValidator<Character>().getValidInput("Your Answer (a-d)",
                                v -> v.toLowerCase().trim().isEmpty() ? ' ' : v.toLowerCase().trim().charAt(0),
                                v -> v >= 'a' && v <= 'd');

                        boolean correct = engine.answerCurrentQuestion(a);
                        if (correct) System.out.println("\n>>> CORRECT!");
                        else System.out.println("\n>>> INCORRECT! Correct answer was: " + q.getCorrectAnswer());

                        engine.goNext();
                    } else {
                        System.out.println("\n[!] You have already answered this question.");
                    }
                }
                case 2 -> {
                    if (!engine.goNext()) System.out.println("\n[!] You're already on the last question.");
                }
                case 3 -> {
                    if (!engine.goPrevious()) System.out.println("\n[!] You're already on the first question.");
                }
                case 4 -> {
                    if (engine.areAllAnswered()) {
                        currentPlayer.updateScore(engine.getScore(), engine.getTotalQuestions());
                        dataManager.savePlayers(players);
                        System.out.println("\n[✔] Final Score Saved: " + engine.getScore() + "/" + engine.getTotalQuestions());
                        isPlaying = false;
                    } else {
                        System.out.println("\n[!] CANNOT SUBMIT: Please answer all questions first.");
                    }
                }
            }
        }
    }
}