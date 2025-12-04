package bookstore.view;

import bookstore.model.Book;
import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    public HomePanel(MainWindow mainWindow) {
        Color babyYellow = new Color(255, 250, 205);
        Color accentBlue  = new Color(130, 180, 225);
        Color accentYellow = new Color(255, 204, 0);
        Color textColor   = new Color(50, 50, 50);

        setLayout(new BorderLayout());
        setBackground(babyYellow);

        // Main vertical container
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(babyYellow);

        // ---------------- HERO SECTION ----------------
        JPanel heroPanel = new JPanel(new GridBagLayout());
        heroPanel.setBackground(babyYellow);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel bigText = new JLabel(
                "<html><div style='width:400px;'>Scroll and discover your next big read!</div></html>");
        bigText.setFont(new Font("Segoe UI", Font.BOLD, 36));
        bigText.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 0;
        heroPanel.add(bigText, gbc);

        JLabel bookImage = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/bookstore/assets/download.jpg"));
            Image scaled = icon.getImage().getScaledInstance(220, 300, Image.SCALE_SMOOTH);
            bookImage.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            bookImage.setText("Image not found");
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        heroPanel.add(bookImage, gbc);

        mainContainer.add(heroPanel);
        mainContainer.add(Box.createVerticalStrut(50));

        // ---------------- COLLECTION SECTION ----------------
        JPanel collectionPanel = new JPanel();
        collectionPanel.setLayout(new BoxLayout(collectionPanel, BoxLayout.Y_AXIS));
        collectionPanel.setBackground(new Color(255, 247, 210));

        JLabel collectionTitle = new JLabel("Our Collections", JLabel.CENTER);
        collectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        collectionTitle.setForeground(textColor);
        collectionTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        collectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        collectionPanel.add(collectionTitle);

        JPanel categoriesFlow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        categoriesFlow.setBackground(new Color(255, 247, 210));
        categoriesFlow.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));

        String[] categories = {"Fiction", "Non-Fiction", "Mystery", "Academic"};
        String[] categoryImages = {"fiction.jpg", "nonfiction.jpg", "mystery.jpg", "academic.jpg"};

        for (int i = 0; i < categories.length; i++) {
            categoriesFlow.add(createCategoryCard(categories[i], categoryImages[i], mainWindow, accentBlue, textColor));
        }

        collectionPanel.add(categoriesFlow);
        mainContainer.add(collectionPanel);
        mainContainer.add(Box.createVerticalStrut(50));

        // ---------------- POPULAR BOOKS SECTION ----------------
        JPanel popularPanel = new JPanel();
        popularPanel.setLayout(new BoxLayout(popularPanel, BoxLayout.Y_AXIS));
        popularPanel.setBackground(babyYellow);
        popularPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel popularTitle = new JLabel("Popular Books", JLabel.CENTER);
        popularTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        popularTitle.setForeground(textColor);
        popularTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        popularPanel.add(popularTitle);
        popularPanel.add(Box.createVerticalStrut(20));

        JPanel booksFlow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        booksFlow.setBackground(babyYellow);

        String[] bookNames = {"Clean Code", "The Alchemist", "Java Concurrency",
                "1984", "Atomic Habits", "Design Patterns"};
        String[] bookImages = {"cleancode.jpg", "alchemist.jpg", "java2.jpg",
                "1984.jpg", "atomic.jpg", "designpatterns.jpg"};
        double[] bookPrices = {1450, 1200, 1700, 1100, 1300, 1600};

        for (int i = 0; i < bookNames.length; i++) {
            booksFlow.add(createBookCard(bookNames[i], bookImages[i], bookPrices[i],
                    mainWindow, accentBlue, textColor));
        }

        JScrollPane horizontalScroll = new JScrollPane(booksFlow,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        horizontalScroll.setPreferredSize(new Dimension(900, 350));
        horizontalScroll.setBorder(null);
        horizontalScroll.getHorizontalScrollBar().setUnitIncrement(16);
        horizontalScroll.getViewport().setBackground(babyYellow);

        popularPanel.add(horizontalScroll);
        popularPanel.add(Box.createVerticalStrut(20));

        JButton seeMoreBtn = new JButton("See More") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        seeMoreBtn.setBackground(accentYellow);
        seeMoreBtn.setForeground(Color.WHITE);
        seeMoreBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        seeMoreBtn.setFocusPainted(false);
        seeMoreBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        seeMoreBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        seeMoreBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        seeMoreBtn.setOpaque(false);
        seeMoreBtn.setContentAreaFilled(false);

        seeMoreBtn.addActionListener(e -> mainWindow.switchPanel("books"));

        popularPanel.add(seeMoreBtn);
        mainContainer.add(popularPanel);

        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(babyYellow);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createCategoryCard(String name, String imageName, MainWindow mainWindow,
                                      Color accentBlue, Color textColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(255, 247, 210));
        card.setMaximumSize(new Dimension(160, 300));
        card.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel pic = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/bookstore/assets/" + imageName));
            Image scaled = icon.getImage().getScaledInstance(120, 160, Image.SCALE_SMOOTH);
            pic.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            pic.setText("Image not found");
        }
        pic.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel(name);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(textColor);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exploreBtn = new JButton("Explore") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        exploreBtn.setBackground(accentBlue);
        exploreBtn.setForeground(Color.WHITE);
        exploreBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exploreBtn.setFocusPainted(false);
        exploreBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exploreBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exploreBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        exploreBtn.setOpaque(false);
        exploreBtn.setContentAreaFilled(false);

        exploreBtn.addActionListener(e -> mainWindow.switchPanel("books"));

        card.add(pic);
        card.add(Box.createVerticalStrut(10));
        card.add(title);
        card.add(Box.createVerticalStrut(10));
        card.add(exploreBtn);

        return card;
    }

    private JPanel createBookCard(String title, String imageName, double price,
                                  MainWindow mainWindow, Color accentBlue, Color textColor) {
        Color babyYellow = new Color(255, 250, 205);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(babyYellow);
        card.setMaximumSize(new Dimension(160, 350));
        card.setAlignmentY(Component.TOP_ALIGNMENT);

        JLabel pic = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/bookstore/assets/" + imageName));
            Image scaled = icon.getImage().getScaledInstance(130, 170, Image.SCALE_SMOOTH);
            pic.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            pic.setText("Image not found");
            pic.setHorizontalAlignment(JLabel.CENTER);
        }
        pic.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = new JLabel(title);
        name.setFont(new Font("Segoe UI", Font.BOLD, 16));
        name.setForeground(textColor);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        name.setHorizontalAlignment(JLabel.CENTER);

        JLabel priceLabel = new JLabel("DA " + price);
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        priceLabel.setForeground(new Color(80, 80, 80));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton addBtn = new JButton("Add to Cart") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        addBtn.setBackground(accentBlue);
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setFocusPainted(false);
        addBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        addBtn.setOpaque(false);
        addBtn.setContentAreaFilled(false);

        addBtn.addActionListener(e -> {
            mainWindow.getCart().add(new Book(title, imageName, price));

            addBtn.setText("Added!");
            addBtn.setEnabled(false);

            Timer timer = new Timer(2000, evt -> {
                addBtn.setText("Add to Cart");
                addBtn.setEnabled(true);
            });
            timer.setRepeats(false);
            timer.start();
        });

        card.add(Box.createVerticalStrut(5));
        card.add(pic);
        card.add(Box.createVerticalStrut(10));
        card.add(name);
        card.add(Box.createVerticalStrut(5));
        card.add(priceLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(addBtn);
        card.add(Box.createVerticalStrut(5));

        return card;
    }
}
