package ui;

import core.ResultManager;
import model.Result;
import model.User;

import javax.swing.*;
import java.awt.*;

public class ResultsTableScreen extends BaseScreen {
    private final User user;
    private final Result[] results;
    private final int firstCellWidth;
    private final int secondCellWidth;
    private final int thirdCellWidth;
    private final int stroke;
    private final int margin;
    private int currentHeight;
    private int currentCellHeight;

    ResultsTableScreen(JFrame parent, User user) {
        super(parent);
        this.user = user;
        this.results = new ResultManager().getAllResults();
        this.firstCellWidth = (int) (width * 0.4);
        this.secondCellWidth = (int) (width * 0.15);
        this.thirdCellWidth = width - firstCellWidth - secondCellWidth;
        this.stroke = 1;
        this.margin = 10;
        this.currentHeight = 30 + exitButtonSize.height;
        this.currentCellHeight = 30;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawBackButton();
        drawTableHeader(g2);
        for (Result result : results) {
            drawResult(g2, result);
        }
        currentHeight = 30 + exitButtonSize.height;
        currentCellHeight = 30;
    }

    private void drawBackButton() {
        JButton backButton = createBackButton();
        backButton.addActionListener(actionEvent -> {
            parentFrame.getContentPane().remove(0);
            parentFrame.add(new TestScreen(parentFrame, user));
            parentFrame.setVisible(true);
        });
        add(backButton);
    }

    private void drawTableCarcass(Graphics2D g2) {
        g2.drawLine(firstCellWidth, currentHeight, firstCellWidth, currentHeight + currentCellHeight);
        g2.drawLine(
                firstCellWidth + secondCellWidth,
                currentHeight,
                firstCellWidth + secondCellWidth,
                currentHeight + currentCellHeight
        );
        currentHeight += currentCellHeight;
        g2.drawLine(0, currentHeight, width, currentHeight);
        currentHeight += stroke;
    }

    private void drawTableHeader(Graphics2D g2) {
        g2.setStroke(new BasicStroke(stroke));
        g2.drawLine(0, currentHeight, width, currentHeight);
        String name = "Имя пользователя";
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        int x = (firstCellWidth - fm.stringWidth(name)) / 2;
        int y = currentHeight + currentCellHeight - (currentCellHeight - font.getSize()) / 2;
        g2.drawString(name, x, y);
        name = "Баллы";
        x = firstCellWidth + (secondCellWidth - fm.stringWidth(name)) / 2;
        g2.drawString(name, x, y);
        name = "Дата";
        x = firstCellWidth + secondCellWidth + (thirdCellWidth - fm.stringWidth(name)) / 2;
        g2.drawString(name, x, y);
        drawTableCarcass(g2);
    }

    private void drawResult(Graphics2D g2, Result result) {
        drawUsername(g2, result);
        drawScore(g2, result);
        drawDate(g2, result);
        drawTableCarcass(g2);
    }

    private void drawUsername(Graphics2D g2, Result result) {
        FontMetrics fm = g2.getFontMetrics();
        int linesNumber = (fm.stringWidth(result.getName()) / (firstCellWidth - 2 * margin)) + 1;
        JTextArea textArea = createTextArea(
                result.getName(),
                firstCellWidth - 2 * margin,
                linesNumber * fm.getHeight(),
                margin,
                currentHeight
        );
        add(textArea);
        currentCellHeight = textArea.getHeight();
    }

    private void drawScore(Graphics2D g2, Result result) {
        FontMetrics fm = g2.getFontMetrics();
        int linesNumber = (fm.stringWidth(String.valueOf(result.getScore())) / (secondCellWidth - 2 * margin)) + 1;
        JTextArea textArea = createTextArea(
                String.valueOf(result.getScore()),
                secondCellWidth - 2 * margin,
                linesNumber * fm.getHeight(),
                firstCellWidth + margin,
                currentHeight
        );
        add(textArea);
        currentCellHeight = Math.max(textArea.getHeight(), currentCellHeight);
    }

    private void drawDate(Graphics2D g2, Result result) {
        FontMetrics fm = g2.getFontMetrics();
        int linesNumber = (fm.stringWidth(result.getDate().toString()) / (thirdCellWidth - 2 * margin)) + 1;
        JTextArea textArea = createTextArea(
                result.getDate().toString(),
                thirdCellWidth - 2 * margin,
                linesNumber * fm.getHeight(),
                firstCellWidth + secondCellWidth + margin,
                currentHeight
        );
        add(textArea);
        currentCellHeight = Math.max(textArea.getHeight(), currentCellHeight);
    }
}
