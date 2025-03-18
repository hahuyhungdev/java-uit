import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class QuizApp {
  private JFrame frame;
  private JPanel questionPanel;
  private CardLayout cardLayout;
  private JPanel cardPanel;
  private List<QuizQuestion> questions;
  private int currentIndex = 0;
  private JButton[] questionButtons; // To track buttons for highlighting

  public QuizApp() {
    // Initialize the main frame
    frame = new JFrame("Card Layout Quiz App");
    frame.setSize(600, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());

    // Split the frame into left (question) and right (question boxes)
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setDividerLocation(300); // Left panel width
    splitPane.setEnabled(false); // Optional: Disable manual resizing

    // Left Panel: Question Display
    questionPanel = new JPanel(new BorderLayout());
    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);
    questions = QuizQuestion.getSampleQuestions();
    for (int i = 0; i < questions.size(); i++) {
      cardPanel.add(createQuestionPanel(questions.get(i)), "card" + i);
    }
    questionPanel.add(cardPanel, BorderLayout.CENTER);

    // Right Panel: Question Boxes (5x3 Grid)
    JPanel listPanel = new JPanel(new BorderLayout());
    JPanel gridPanel = new JPanel(new GridLayout(5, 3, 5, 5)); // 5 rows, 3 columns
    gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    questionButtons = new JButton[questions.size()];
    for (int i = 0; i < questions.size(); i++) {
      JButton button = new JButton(String.valueOf(i + 1));
      button.setPreferredSize(new Dimension(40, 40)); // Square buttons
      final int index = i;
      button.addActionListener(e -> {
        currentIndex = index;
        cardLayout.show(cardPanel, "card" + currentIndex);
        updateButtonHighlight();
      });
      questionButtons[i] = button;
      gridPanel.add(button);
    }
    JScrollPane scrollPane = new JScrollPane(gridPanel);
    listPanel.add(scrollPane, BorderLayout.CENTER);

    // Add panels to split pane
    splitPane.setLeftComponent(questionPanel);
    splitPane.setRightComponent(listPanel);

    // Bottom Toolbar: Navigation Controls
    JPanel toolbar = new JPanel(new FlowLayout());
    JButton firstButton = new JButton("First");
    JButton prevButton = new JButton("<-");
    JButton nextButton = new JButton("->");
    JButton lastButton = new JButton("Last");

    // Add action listeners for navigation
    firstButton.addActionListener(e -> {
      currentIndex = 0;
      cardLayout.first(cardPanel);
      updateButtonHighlight();
    });
    prevButton.addActionListener(e -> {
      if (currentIndex > 0) {
        currentIndex--;
        cardLayout.previous(cardPanel);
        updateButtonHighlight();
      }
    });
    nextButton.addActionListener(e -> {
      if (currentIndex < questions.size() - 1) {
        currentIndex++;
        cardLayout.next(cardPanel);
        updateButtonHighlight();
      }
    });
    lastButton.addActionListener(e -> {
      currentIndex = questions.size() - 1;
      cardLayout.last(cardPanel);
      updateButtonHighlight();
    });

    // Add buttons to toolbar
    toolbar.add(firstButton);
    toolbar.add(prevButton);
    toolbar.add(nextButton);
    toolbar.add(lastButton);

    // Add components to frame
    frame.add(splitPane, BorderLayout.CENTER);
    frame.add(toolbar, BorderLayout.SOUTH);

    // Show the first question and highlight the first button
    cardLayout.first(cardPanel);
    updateButtonHighlight();
  }

  private JPanel createQuestionPanel(QuizQuestion question) {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Question label
    JLabel questionLabel = new JLabel(question.getQuestionText(), SwingConstants.CENTER);
    questionLabel.setFont(new Font("Arial", Font.BOLD, 16));

    // Options panel with radio buttons
    JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
    ButtonGroup buttonGroup = new ButtonGroup();
    for (String option : question.getOptions()) {
      JRadioButton radioButton = new JRadioButton(option);
      buttonGroup.add(radioButton);
      optionsPanel.add(radioButton);
    }

    panel.add(questionLabel, BorderLayout.NORTH);
    panel.add(optionsPanel, BorderLayout.CENTER);
    return panel;
  }

  private void updateButtonHighlight() {
    for (int i = 0; i < questionButtons.length; i++) {
      if (i == currentIndex) {
        questionButtons[i].setBackground(Color.LIGHT_GRAY); // Highlight current question
        questionButtons[i].setForeground(Color.WHITE);
      } else {
        questionButtons[i].setBackground(null); // Default background
        questionButtons[i].setForeground(Color.BLACK);
      }
    }
  }

  public void show() {
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      QuizApp app = new QuizApp();
      app.show();
    });
  }
}