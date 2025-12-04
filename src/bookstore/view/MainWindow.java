package bookstore.view;

import bookstore.model.Book;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainWindow extends JFrame {

    private JPanel mainPanel;
    private ArrayList<Book> cart = new ArrayList<>();
    private CheckoutForm checkoutForm;

    public ArrayList<Book> getCart() {
        return cart;
    }

    public CheckoutForm getCheckoutForm() {
        return checkoutForm;
    }

    public MainWindow() {
        setTitle("WordNest");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Colors
        Color babyYellow = new Color(255, 250, 205);   // page background
        Color navBlue    = new Color(130, 180, 225);   // baby blue nav
        Color navText    = Color.WHITE;                // white text on blue

        // ---------- NAV BAR ----------
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(navBlue);
        navBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel logo = new JLabel("WordNest");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setForeground(navText);
        logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logo.setHorizontalAlignment(JLabel.LEFT);
        logo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        navBar.add(logo, BorderLayout.WEST);

        logo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                switchPanel("home");
            }
        });

        JPanel navButtons = new JPanel();
        navButtons.setOpaque(false);
        navButtons.setLayout(new BoxLayout(navButtons, BoxLayout.X_AXIS));

        JButton booksBtn = createNavButton("Books");
        JButton cartBtn = createNavButton("Cart");
        JButton checkoutBtn = createNavButton("Checkout");

        // make nav buttons white text + hover
        styleNavButtonForBlue(booksBtn);
        styleNavButtonForBlue(cartBtn);
        styleNavButtonForBlue(checkoutBtn);

        navButtons.add(Box.createHorizontalGlue());
        navButtons.add(booksBtn);
        navButtons.add(Box.createHorizontalStrut(15));
        navButtons.add(cartBtn);
        navButtons.add(Box.createHorizontalStrut(15));
        navButtons.add(checkoutBtn);

        navBar.add(navButtons, BorderLayout.EAST);

        add(navBar, BorderLayout.NORTH);

        // ---------- MAIN CONTENT ----------
        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBackground(babyYellow);

        mainPanel.add(new HomePanel(this), "home");
        mainPanel.add(new BookListPanel(this), "books");
        mainPanel.add(new CartPanel(this), "cart");

        checkoutForm = new CheckoutForm(this);
        mainPanel.add(checkoutForm, "checkout");

        booksBtn.addActionListener(e -> switchPanel("books"));

        cartBtn.addActionListener(e -> {
            ((CartPanel) mainPanel.getComponent(2)).refreshCart();
            switchPanel("cart");
        });

        checkoutBtn.addActionListener(e -> {
            checkoutForm.updateTotal();
            switchPanel("checkout");
        });

        add(mainPanel, BorderLayout.CENTER);
        switchPanel("home");
        setVisible(true);
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // white text + light hover for blue nav bar
    private void styleNavButtonForBlue(JButton btn) {
        btn.setForeground(Color.WHITE);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setForeground(new Color(230, 230, 230));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setForeground(Color.WHITE);
            }
        });
    }

    public void switchPanel(String name) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}


