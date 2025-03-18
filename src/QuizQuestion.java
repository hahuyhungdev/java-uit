import java.util.ArrayList;
import java.util.List;

public class QuizQuestion {
  private String questionText;
  private List<String> options;

  public QuizQuestion(String questionText, List<String> options) {
    this.questionText = questionText;
    this.options = options;
  }

  public String getQuestionText() {
    return questionText;
  }

  public List<String> getOptions() {
    return options;
  }

  public static List<QuizQuestion> getSampleQuestions() {
    List<QuizQuestion> questions = new ArrayList<>();
    for (int i = 1; i <= 15; i++) { // Matching the 5x3 grid (15 questions)
      questions.add(new QuizQuestion("Câu hỏi " + i + "...?",
          List.of("Đáp án 1", "Đáp án 2", "Đáp án 3", "Đáp án 4")));
    }
    return questions;
  }
}