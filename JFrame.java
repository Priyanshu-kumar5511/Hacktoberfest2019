import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PaginationGUI extends JFrame {
    private List<Command> list;
    private List<Command> pageList;
    private int numberPerPage = 9;
    private int currentPage = 1;
    private int numberOfPages = 0;
    private JPanel buttonPanel;
    private JPanel contentPanel;
    private JButton prevButton, nextButton;
    private List<JButton> pageButtons;
    private JLabel statusLabel;

    public PaginationGUI(List<Command> commands) {
        this.list = commands;
        numberOfPages = (int) Math.ceil((double) list.size() / numberPerPage);
        pageButtons = new ArrayList<>();
        setTitle("Git Commands Pagination");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(numberPerPage, 1, 5, 5));
        add(contentPanel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        prevButton = new JButton("<");
        prevButton.addActionListener(e -> previousPage());
        buttonPanel.add(prevButton);

        for (int i = 1; i <= numberOfPages; i++) {
            JButton btn = new JButton(String.valueOf(i));
            int pageNum = i;
            btn.addActionListener(e -> goToPage(pageNum));
            pageButtons.add(btn);
            buttonPanel.add(btn);
        }

        nextButton = new JButton(">");
        nextButton.addActionListener(e -> nextPage());
        buttonPanel.add(nextButton);

        statusLabel = new JLabel();
        add(statusLabel, BorderLayout.SOUTH);

        add(buttonPanel, BorderLayout.NORTH);

        loadList();
        updateButtons();

        setVisible(true);
    }

    private void loadList() {
        contentPanel.removeAll();
        int start = (currentPage - 1) * numberPerPage;
        int end = Math.min(start + numberPerPage, list.size());
        pageList = list.subList(start, end);

        for (Command cmd : pageList) {
            JTextArea textArea = new JTextArea(cmd.title + "\n" + cmd.description);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            contentPanel.add(textArea);
        }
        contentPanel.revalidate();
        contentPanel.repaint();

        statusLabel.setText("Page " + currentPage + " of " + numberOfPages);
    }

    private void updateButtons() {
        prevButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < numberOfPages);

        for (int i = 0; i < pageButtons.size(); i++) {
            JButton btn = pageButtons.get(i);
            btn.setEnabled(true);
            if (i + 1 == currentPage) {
                btn.setBackground(Color.CYAN);
            } else {
                btn.setBackground(null);
            }
            // Logic for showing limited buttons like in JS can be implemented here
            // For brevity, showing all buttons
        }
    }

    private void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            loadList();
            updateButtons();
        }
    }

    private void nextPage() {
        if (currentPage < numberOfPages) {
            currentPage++;
            loadList();
            updateButtons();
        }
    }

    private void goToPage(int pageNum) {
        currentPage = pageNum;
        loadList();
        updateButtons();
    }

    public static void main(String[] args) {
        // Sample data simulating git commands JSON objects
        List<Command> commands = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            commands.add(new Command("Command " + i, "Description for command " + i));
        }
        new PaginationGUI(commands);
    }
}

class Command {
    String title;
    String description;

    Command(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
