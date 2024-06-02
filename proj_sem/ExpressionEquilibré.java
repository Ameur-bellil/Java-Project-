package proj_sem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class ExpressionEquilibré extends JFrame {

    private JTextArea expressionField;
    private JLabel resultLabel;
    private JButton checkButton;

    public ExpressionEquilibré() {
        setTitle("Vérification d'expression");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        expressionField = new JTextArea(3, 20);
        expressionField.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(expressionField);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        resultLabel = new JLabel();
        mainPanel.add(resultLabel, BorderLayout.SOUTH);

        checkButton = new JButton("Vérifier");
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = expressionField.getText();
                if (expression.isEmpty()) {
                    JOptionPane.showMessageDialog(ExpressionEquilibré.this, "Veuillez entrer une expression.");
                    return;
                }

                boolean estEquilibree = estEquilibree(expression);
                if (estEquilibree) {
                    resultLabel.setText("L'expression est équilibrée.");
                } else {
                    int position = positionPremierDelimiteurIncorrect(expression);
                    if (position == -1) {
                        resultLabel.setText("Aucun délimiteur incorrect trouvé.");
                    } else {
                        resultLabel.setText("Délimiteur incorrect à la position : " + position);
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(checkButton);
        mainPanel.add(buttonPanel, BorderLayout.EAST);

        add(mainPanel);
    }

    public boolean estEquilibree(String expression) {
        Stack<Character> pile = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char caractere = expression.charAt(i);

            if (caractere == '(' || caractere == '[' || caractere == '{') {
                pile.push(caractere);
            } else if (caractere == ')' || caractere == ']' || caractere == '}') {
                if (pile.isEmpty()) {
                    return false;
                }

                char delimitateurOuvert = pile.pop();
                if ((caractere == ')' && delimitateurOuvert != '(') ||
                        (caractere == ']' && delimitateurOuvert != '[') ||
                        (caractere == '}' && delimitateurOuvert != '{')) {
                    return false;
                }
            }
        }

        return pile.isEmpty();
    }

    public int positionPremierDelimiteurIncorrect(String expression) {
        Stack<Integer> pile = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char caractere = expression.charAt(i);

            if (caractere == '(' || caractere == '[' || caractere == '{') {
                pile.push(i);
            } else if (caractere == ')' || caractere == ']' || caractere == '}') {
                if (pile.isEmpty()) {
                    return i;
                }

                char delimitateurOuvert = expression.charAt(pile.pop());
                if ((caractere == ')' && delimitateurOuvert != '(') ||
                        (caractere == ']' && delimitateurOuvert != '[') ||
                        (caractere == '}' && delimitateurOuvert != '{')) {
                    return i;
                }
            }
        }

        return pile.isEmpty() ? -1 : pile.pop();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ExpressionEquilibré gui = new ExpressionEquilibré();
                gui.setVisible(true);
            }
        });
    }
}
