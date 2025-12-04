package bookstore.view;

import bookstore.model.Book;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CheckoutForm extends JPanel {

    private JLabel totalLabel;
    private MainWindow mainWindow;

    public CheckoutForm(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        Color babyYellow = new Color(255, 250, 205);
        Color accentBlue = new Color(130, 180, 225);
        Color textColor  = new Color(50, 50, 50);

        setLayout(new GridBagLayout());
        setBackground(babyYellow);

        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, Color.WHITE,
                        getWidth(), getHeight(), new Color(255, 247, 210)
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.setColor(new Color(220, 225, 235));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.7;
        gbc.weighty = 1;
        gbc.insets = new Insets(10, 60, 10, 60);
        add(card, gbc);

        JLabel title = new JLabel("Checkout");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(textColor);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(title);

        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();

        card.add(sectionLabel("Customer Information", textColor));
        card.add(createRoundedField("Full Name", nameField));
        card.add(Box.createVerticalStrut(12));
        card.add(createRoundedField("Email", emailField));
        card.add(Box.createVerticalStrut(12));
        card.add(createRoundedField("Phone Number", phoneField));
        card.add(Box.createVerticalStrut(12));
        card.add(createRoundedField("Address", addressField));

        card.add(Box.createVerticalStrut(18));
        card.add(sectionLabel("Payment Method", textColor));
        card.add(Box.createVerticalStrut(6));

        JComboBox<String> paymentBox = new JComboBox<>(new String[]{
                "Cash on Delivery", "Credit Card", "PayPal"
        });
        styleCombo(paymentBox);
        paymentBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(paymentBox);

        card.add(Box.createVerticalStrut(18));
        card.add(sectionLabel("Order Summary", textColor));
        card.add(Box.createVerticalStrut(6));

        totalLabel = new JLabel("Total: DA 0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLabel.setForeground(new Color(33, 150, 83));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(totalLabel);

        card.add(Box.createVerticalStrut(24));

        JButton submitBtn = createPrimaryButton("Place Order", accentBlue);
        submitBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(submitBtn);

        submitBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();

            if (name.isEmpty()) {
                showError("Please enter your full name.");
                nameField.requestFocus();
                return;
            }
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                showError("Please enter a valid email address.");
                emailField.requestFocus();
                return;
            }
            if (!phone.matches("\\d{8,15}")) {
                showError("Phone must contain 8–15 digits.");
                phoneField.requestFocus();
                return;
            }
            if (address.isEmpty()) {
                showError("Please enter your address.");
                addressField.requestFocus();
                return;
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Your order has been placed!\nThank you for shopping with us.",
                    "Order confirmed",
                    JOptionPane.INFORMATION_MESSAGE
            );

            mainWindow.switchPanel("home");
        });

        updateTotal();
    }

    private JLabel sectionLabel(String text, Color textColor) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setForeground(textColor);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JPanel createRoundedField(String labelText, JTextField field) {
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(90, 95, 105));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setPreferredSize(new Dimension(320, 36));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(new Color(252, 253, 254));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        wrapper.add(label);
        wrapper.add(Box.createVerticalStrut(5));
        wrapper.add(field);
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        return wrapper;
    }

    private void styleCombo(JComboBox<String> box) {
        box.setPreferredSize(new Dimension(320, 36));
        box.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.setBorder(BorderFactory.createLineBorder(new Color(210, 215, 225), 1));
        box.setBackground(new Color(252, 253, 254));
    }

    private JButton createPrimaryButton(String text, Color accentBlue) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g2);
            }
        };

        btn.setPreferredSize(new Dimension(320, 42));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(accentBlue);
        btn.setContentAreaFilled(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(110, 165, 215));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(accentBlue);
            }
        });

        return btn;
    }

    public void updateTotal() {
        double total = 0;
        List<Book> cart = mainWindow.getCart();
        for (Book b : cart) {
            total += b.getPrice();
        }
        totalLabel.setText(String.format("Total: DA %.2f", total));
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
    }
}
