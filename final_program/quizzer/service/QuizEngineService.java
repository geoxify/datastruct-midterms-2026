package final_program.quizzer.service;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import com.geoxify.model.Question;

public class QuizEngineService {
    private Stack<Question> unansweredStack;
    private Stack<Question> answeredStack;
    private Question currentQuestion;
    private int score;
    private int totalQuestions;
    private int currentIndexTracker;

    public QuizEngineService(List<Question> questionBank) {
        this.unansweredStack = new Stack<>();
        this.answeredStack = new Stack<>();
        this.score = 0;
        this.totalQuestions = questionBank.size();
        this.currentIndexTracker = 1;

        // Reset answered states and shuffle
        for(Question q : questionBank) q.setAnswered(false);
        Collections.shuffle(questionBank);

        // Push to unanswered stack in reverse order so the first question is on top
        for (int i = questionBank.size() - 1; i >= 0; i--) {
            unansweredStack.push(questionBank.get(i));
        }

        if (!unansweredStack.isEmpty()) {
            currentQuestion = unansweredStack.pop();
        }
    }

    public Question getCurrentQuestion() { return currentQuestion; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCurrentIndex() { return currentIndexTracker; }

    public boolean answerCurrentQuestion(char playerAnswer) {
        if (currentQuestion.isAnswered()) return false;

        boolean isCorrect = (playerAnswer == currentQuestion.getCorrectAnswer());
        if (isCorrect) {
            score++;
        }
        currentQuestion.setAnswered(true);
        return isCorrect;
    }

    public boolean goNext() {
        if (unansweredStack.isEmpty()) {
            return false; // Boundary alert
        }
        answeredStack.push(currentQuestion);
        currentQuestion = unansweredStack.pop();
        currentIndexTracker++;
        return true;
    }

    public boolean goPrevious() {
        if (answeredStack.isEmpty()) {
            return false; // Boundary alert
        }
        unansweredStack.push(currentQuestion);
        currentQuestion = answeredStack.pop();
        currentIndexTracker--;
        return true;
    }

    public boolean areAllAnswered() {
        if (!currentQuestion.isAnswered()) return false;
        for (Question q : unansweredStack) {
            if (!q.isAnswered()) return false;
        }
        for (Question q : answeredStack) {
            if (!q.isAnswered()) return false;
        }
        return true;
    }
}