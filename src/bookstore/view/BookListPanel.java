package bookstore.view;

import javax.swing.*;
import java.awt.*;
import bookstore.model.Book;

public class BookListPanel extends JPanel {

    private MainWindow mainWindow;

    public BookListPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        Color babyYellow = new Color(255, 250, 205);
        Color accentBlue  = new Color(130, 180, 225);
        Color textColor   = new Color(50, 50, 50);

        setLayout(new BorderLayout());
        setBackground(babyYellow);

        JLabel label = new JLabel("Our Book Collection", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 22));
        label.setForeground(textColor);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(label, BorderLayout.NORTH);

        JPanel booksGrid = new JPanel(new GridLayout(0, 5, 20, 20));
        booksGrid.setBackground(babyYellow);
        booksGrid.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        String[] bookNames = {
                "Clean Code", "موسم الهجرة إلى الشمال", "The Alchemist", "أولاد حارتنا", "Java Concurrency",
                "1984", "Atomic Habits", "الخبز الحافي", "Design Patterns", "ليالي ألف ليلة",
                "الجريمة والعقاب", "The Little Prince", "فن الحرب", "Not Nothing", "رحلة ابن فطومة"
        };

        String[] bookImages = {
                "cleancode.jpg", "mawsim.jpg", "alchemist.jpg", "awladharra.jpg", "java2.jpg",
                "1984.jpg", "atomic.jpg", "alkhubz.jpg", "designpatterns.jpg", "alflyl.jpg",
                "crime.jpg", "littleprince.jpg", "artofwar.jpg", "lanothing.jpg", "ibnfatouma.jpg"
        };

        double[] bookPrices = {
                1450, 1000, 1200, 950, 1700,
                1100, 1300, 900, 1600, 1200,
                1400, 800, 1100, 900, 1000
        };

        for (int i = 0; i < bookNames.length; i++) {
            booksGrid.add(createBookCard(bookNames[i], bookImages[i], bookPrices[i], accentBlue, textColor));
        }

        JScrollPane scroll = new JScrollPane(
                booksGrid,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getViewport().setBackground(babyYellow);

        add(scroll, BorderLayout.CENTER);
    }

    private JPanel createBookCard(String title, String imageName, double price,
                                  Color accentBlue, Color textColor) {
        Color babyYellow = new Color(255, 250, 205);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(babyYellow);
        card.setPreferredSize(new Dimension(150, 350));

        JLabel pic = new JLabel();
        pic.setAlignmentX(Component.CENTER_ALIGNMENT);

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/bookstore/assets/" + imageName));
            Image scaled = icon.getImage().getScaledInstance(130, 170, Image.SCALE_SMOOTH);
            pic.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            pic.setText("Image not found");
            pic.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        }

        JLabel name = new JLabel(title);
        name.setFont(new Font("Segoe UI", Font.BOLD, 16));
        name.setForeground(textColor);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel priceLabel = new JLabel("DA " + price);
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        priceLabel.setForeground(new Color(80, 80, 80));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addBtn.setBackground(new Color(111, 183, 214));
                addBtn.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addBtn.setBackground(accentBlue);
                addBtn.repaint();
            }
        });

        addBtn.addActionListener(e -> {
            mainWindow.getCart().add(new Book(title, imageName, price));
            addBtn.setText("Added!");
            addBtn.setEnabled(false);
            addBtn.setBackground(accentBlue);

            Timer timer = new Timer(2000, evt -> {
                addBtn.setText("Add to Cart");
                addBtn.setEnabled(true);
                addBtn.setBackground(accentBlue);
            });
            timer.setRepeats(false);
            timer.start();
        });

        card.add(Box.createVerticalStrut(10));
        card.add(pic);
        card.add(Box.createVerticalStrut(10));
        card.add(name);
        card.add(priceLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(addBtn);
        card.add(Box.createVerticalStrut(10));

        return card;
    }
}


