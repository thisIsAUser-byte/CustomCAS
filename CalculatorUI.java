import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorUI {
    private JFrame frame;
    private JTextField display;

    private LargeFraction firstNumber = new LargeFraction(0);
    private String operator = "";
    private boolean startNewNumber = true;
    private boolean parenthesisClose = false;

    public CalculatorUI() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        display = new JTextField("0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 30));
        frame.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 6, 5, 5));

        String[] buttons = {
                "7", "8", "9", "/", "C", "()",
                "4", "5", "6", "*", "^", "[]",
                "1", "2", "3", "-", "mod", "{}",
                "0", ".", "=", "+", "del", "(-)"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("TimesNewRoman", Font.PLAIN, 10));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String command = ((JButton)e.getSource()).getText();

                if (command.matches("[0123456789./]")) {
                    if (startNewNumber) {
                        display.setText(command);
                        startNewNumber = false;
                    } else {
                        if(!display.getText().equals("0") || command.equals("."))
                            display.setText(display.getText() + command);
                        else display.setText(command);
                    }
                }else if(command.equals("()")) {
                    if(!parenthesisClose){
                        if(display.getText().equals("0"))
                            display.setText("(");
                        else display.setText(display.getText() + "(");
                        parenthesisClose = true;
                    }else{
                        display.setText(display.getText() + ")");
                        parenthesisClose = false;
                    }
                }else if(command.equals("(-)")){
                    display.setText("-" + display.getText());
                }
                else if (command.matches("[+\\-*^]") || command.equals("mod")) {
                    firstNumber = new LargeFraction(display.getText());
                    operator = command;
                    startNewNumber = true;
                } else if (command.equals("=")) {
                    LargeFraction secondNumber = new LargeFraction(display.getText());
                    ScalarAtom result = new LargeFraction(display.getText());;

                    switch (operator) {
                        case "+" -> result = firstNumber.add(secondNumber);
                        case "-" -> result = firstNumber.subtract(secondNumber);
                        case "*" -> result = firstNumber.multiply(secondNumber);
                        case "/" -> result = firstNumber.divide(secondNumber);
                        case "^" -> result = firstNumber.power(secondNumber);
                        case "mod" -> result = firstNumber.mod(secondNumber);
                    }

                    display.setText(result.toString());
                    startNewNumber = true;
                }else if(command.equals("del")){
                    if(display.getText().length() > 1){
                        if(!parenthesisClose){
                            if(display.getText().substring(display.getText().length()-1).equals(")"))
                                parenthesisClose = true;
                        }else if(display.getText().substring(display.getText().length()-1).equals("("))
                            parenthesisClose = false;
                        display.setText(display.getText().substring(0, display.getText().length()-1));
                    }
                    else display.setText("0");
                }
                else if (command.equals("C")) {
                    display.setText("0");
                    firstNumber = new LargeFraction(0);
                    operator = "";
                    startNewNumber = true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorUI::new);
    }
}
class GraphPanel extends JPanel {
    private int scale = 10; // pixels per grid unit

    public GraphPanel() {
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        int width = getWidth();
        int height = getHeight();

        int centerX = width / 2;
        int centerY = height / 2;

        drawGrid(g2, width, height, centerX, centerY);
        drawAxes(g2, width, height, centerX, centerY);
    }

    private void drawGrid(Graphics2D g2, int width, int height, int centerX, int centerY) {
        g2.setColor(new Color(220, 220, 220));
        g2.setStroke(new BasicStroke(1));

        // vertical grid lines
        for (int x = centerX; x < width; x += scale)
            g2.drawLine(x, 0, x, height);
        for (int x = centerX-scale; x > 0; x -= scale)
            g2.drawLine(x, 0, x, height);

        // horizontal grid lines
        for (int y = centerY; y < height; y += scale)
            g2.drawLine(0, y, width, y);

        for (int y = centerY; y > 0; y -= scale)
            g2.drawLine(0, y, width, y);
        //labels
        Font small = new Font("Arial", Font.PLAIN, Math.min(scale/2, 18));
        g2.setColor(Color.BLACK);
        g2.setFont(small);
        FontMetrics fm = g2.getFontMetrics();
        int labelY = centerY-3;
        for (int x = centerX; x < width; x += scale) {
            int value = (x - centerX) / scale;
            String text = String.valueOf(value);
            int textWidth = fm.stringWidth(text);
            g2.drawString(text, x - textWidth / 2, labelY);
        }

        for (int x = centerX - scale; x >= 0; x -= scale) {
            int value = (x - centerX) / scale;
            String text = String.valueOf(value);
            int textWidth = fm.stringWidth(text);
            g2.drawString(text, x - textWidth / 2, labelY);
        }
    }

    private void drawAxes(Graphics2D g2, int width, int height, int centerX, int centerY) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));

        // x-axis
        g2.drawLine(0, centerY, width, centerY);

        // y-axis
        g2.drawLine(centerX, 0, centerX, height);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Graph Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new GraphPanel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}