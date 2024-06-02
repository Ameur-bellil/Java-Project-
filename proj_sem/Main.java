package proj_sem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TowerOfHanoiUI ui = new TowerOfHanoiUI();
                ui.setVisible(true);
            }
        });
    }
}

class TowerOfHanoiUI extends JFrame {
    private JTextField numOfDisksField;
    private JButton solveButton;
    private OutputPanel outputPanel;

    private int numOfDisks;

    public TowerOfHanoiUI() {
        setTitle("Tower of Hanoi Solver");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for input components
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel numOfDisksLabel = new JLabel("Number of Disks (less than 10): ");
        numOfDisksField = new JTextField(5);
        solveButton = new JButton("Solve");
        inputPanel.add(numOfDisksLabel);
        inputPanel.add(numOfDisksField);
        inputPanel.add(solveButton);

        add(inputPanel, BorderLayout.NORTH);

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveTowerOfHanoi();
            }
        });

        // Output panel
        outputPanel = new OutputPanel();
        add(outputPanel, BorderLayout.CENTER);
    }

    private void solveTowerOfHanoi() {
        numOfDisks = Integer.parseInt(numOfDisksField.getText());
        if (numOfDisks > 10) {
            JOptionPane.showMessageDialog(this, "The number of disks must be less than 10.");
            return;
        }
        outputPanel.solve(numOfDisks);
    }
}

class OutputPanel extends JPanel {
    private JTextArea outputArea;
    private int totalMoves;
    private int numOfDisks;

    public OutputPanel() {
        setLayout(new BorderLayout());
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.SOUTH);
    }

    public void solve(int numOfDisks) {
        this.numOfDisks = numOfDisks;
        outputArea.setText("");
        totalMoves = 0;

        // Add disks to peg A
        for (int i = numOfDisks; i >= 1; i--) {
            outputArea.append("Add disk " + i + " to peg A\n");
        }

        // Move disks from peg A to peg C
        moveTowerOfHanoi(numOfDisks, 'A', 'C', 'B');
        outputArea.append("Total moves: { 2^" + numOfDisks + " - 1} " + "= " + totalMoves);
    }

    private void moveTowerOfHanoi(int n, char fromPeg, char toPeg, char auxPeg) {
        if (n == 1) {
            outputArea.append("Move disk 1 from peg " + fromPeg + " to peg " + toPeg + "\n");
            totalMoves++;
            repaint();
            return;
        }
        moveTowerOfHanoi(n - 1, fromPeg, auxPeg, toPeg);
        outputArea.append("Move disk " + n + " from peg " + fromPeg + " to peg " + toPeg + "\n");
        totalMoves++;
        repaint();
        moveTowerOfHanoi(n - 1, auxPeg, toPeg, fromPeg);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int numOfPegs = 3;
        int pegWidth = 10;
        int pegHeight = 200;
        int pegGap = 400;
        int xStart = (getWidth() - (pegWidth + pegGap) * (numOfPegs - 1) - pegWidth) / 2;
        int yStart = getHeight() - pegHeight;
        for (int i = 0; i < numOfPegs; i++) {
            int x = xStart + i * (pegWidth + pegGap);
            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, yStart, pegWidth, pegHeight);
        }

        // Draw disks
        int diskWidthIncrement = 20;
        int diskWidth = 100;
        int diskHeight = 20;
        int diskY = yStart - diskHeight;
        for (int i = 1; i <= numOfDisks; i++) {
            int diskX = xStart + (pegWidth / 2) - (diskWidth / 2) - ((i - 1) * (diskWidthIncrement / 2));
            diskWidth -= diskWidthIncrement;
            g2d.setColor(Color.BLACK);
            g2d.fillOval(diskX, diskY, diskWidth, diskHeight);
        }
    }
}
