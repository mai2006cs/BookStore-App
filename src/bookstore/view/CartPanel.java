package bookstore.view;

import bookstore.model.Book;
import javax.swing.*;
import java.awt.*;

public class CartPanel extends JPanel {

    private JPanel itemsPanel;
    private MainWindow mainWindow;
    private JLabel totalLabel;

    public CartPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        Color babyYellow = new Color(255, 250, 205);
        Color accentBlue  = new Color(130, 180, 225);
        Color textColor   = new Color(50, 50, 50);

        setBackground(babyYellow);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel title = new JLabel("Shopping Cart", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(textColor);
        add(title, BorderLayout.NORTH);

        itemsPanel = new JPanel();
        itemsPanel.setBackground(babyYellow);
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(babyYellow);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setBackground(babyYellow);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        totalLabel = new JLabel("Total: DA 0", JLabel.CENTER);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalLabel.setForeground(textColor);
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton checkoutBtn = new JButton("Go to Checkout") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        checkoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        checkoutBtn.setBackground(accentBlue);
        checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setFocusPainted(false);
        checkoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkoutBtn.setPreferredSize(new Dimension(200, 40));
        checkoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkoutBtn.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));
        checkoutBtn.setContentAreaFilled(false);
        checkoutBtn.setOpaque(false);

        checkoutBtn.addActionListener(e -> {
            mainWindow.getCheckoutForm().updateTotal();
            mainWindow.switchPanel("checkout");
        });

        bottom.add(totalLabel);
        bottom.add(Box.createVerticalStrut(10));
        bottom.add(checkoutBtn);

        add(bottom, BorderLayout.SOUTH);

        refreshCart();
    }

    public void refreshCart() {
        itemsPanel.removeAll();
        double total = 0;

        for (Book book : mainWindow.getCart()) {
            addCartItem(book);
            total += book.getPrice();
        }

        totalLabel.setText("Total: DA " + total);

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    private void addCartItem(Book book) {
        Color babyYellow = new Color(255, 250, 205);
        Color textColor  = new Color(50, 50, 50);

        JPanel item = new JPanel(new BorderLayout(10, 10));
        item.setBackground(babyYellow);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel pic = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/bookstore/assets/" + book.getImageName()));
            Image scaled = icon.getImage().getScaledInstance(60, 80, Image.SCALE_SMOOTH);
            pic.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            pic.setText("No Image");
        }
        item.add(pic, BorderLayout.WEST);

        JPanel info = new JPanel();
        info.setBackground(babyYellow);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel(book.getTitle());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(textColor);
        JLabel priceLabel = new JLabel("DA " + book.getPrice());
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        priceLabel.setForeground(new Color(80, 80, 80));
        info.add(nameLabel);
        info.add(priceLabel);

        item.add(info, BorderLayout.CENTER);

        JButton removeBtn = new JButton("X");
        removeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        removeBtn.setForeground(new Color(200, 50, 50));
        removeBtn.setBackground(babyYellow);
        removeBtn.setFocusPainted(false);
        removeBtn.setBorder(null);
        removeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        removeBtn.setPreferredSize(new Dimension(40, 40));
        removeBtn.setContentAreaFilled(false);
        removeBtn.setOpaque(false);
        removeBtn.addActionListener(e -> {
            mainWindow.getCart().remove(book);
            refreshCart();
        });

        item.add(removeBtn, BorderLayout.EAST);

        itemsPanel.add(item);
        itemsPanel.add(Box.createVerticalStrut(10));
        itemsPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        itemsPanel.add(Box.createVerticalStrut(10));
    }
}
