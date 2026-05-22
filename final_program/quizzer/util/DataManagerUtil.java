package final_program.quizzer.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.geoxify.model.*;

public class DataManagerUtil {
    private static final String QUESTION_FILE = "data/Quiz.txt";
    private static final String PLAYER_FILE = "data/PlayerRecords.txt";

    // --- QUESTION I/O ---
    public List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();
        File file = new File(QUESTION_FILE);
        if (!file.exists()) return questions;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String promptText = line;
                String[] options = new String[4];
                for (int i = 0; i < 4; i++) {
                    options[i] = br.readLine();
                }
                String ansLine = br.readLine();
                if (ansLine != null && ansLine.contains(":")) {
                    char ans = ansLine.split(":")[1].trim().toLowerCase().charAt(0);
                    questions.add(new Question(promptText, options, ans));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading questions: " + e.getMessage());
        }
        return questions;
    }

    public void saveQuestions(List<Question> questions) {
        File file = new File(QUESTION_FILE);
        file.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(QUESTION_FILE))) {
            for (Question q : questions) {
                bw.write(q.getPromptText() + "\n");
                for (String opt : q.getOptions()) {
                    bw.write(opt + "\n");
                }
                bw.write("Correct Answer: " + q.getCorrectAnswer() + "\n\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving questions: " + e.getMessage());
        }
    }

    // --- PLAYER I/O ---
    public List<Player> loadPlayers() {
        List<Player> players = new ArrayList<>();
        File file = new File(PLAYER_FILE);
        if (!file.exists()) return players;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("--- PLAYER RECORD ---")) {
                    String name = br.readLine().substring(6).trim();
                    String pass = br.readLine().substring(10).trim();
                    String scoreStr = br.readLine().substring(7).trim();
                    br.readLine(); // Read the closing dashes

                    String[] scoreParts = scoreStr.split("/");
                    int score = Integer.parseInt(scoreParts[0]);
                    int total = Integer.parseInt(scoreParts[1]);

                    players.add(new Player(name, pass, score, total));
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading players: " + e.getMessage());
        }
        return players;
    }

    public void savePlayers(List<Player> players) {
        File file = new File(PLAYER_FILE);
        file.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PLAYER_FILE))) {
            for (Player p : players) {
                bw.write("--- PLAYER RECORD ---\n");
                bw.write("Name: " + p.getName() + "\n");
                bw.write("Password: " + p.getPassword() + "\n");
                bw.write("Score: " + p.getScoreString() + "\n");
                bw.write("----------------------------------\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving players: " + e.getMessage());
        }
    }
}