package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class LoginCardPanel extends JPanel {

    private static final int CONTENT_WIDTH = UIHelper.FIELD_WIDTH;
    private static final int H_PAD = 40;
    private static final int V_PAD = 40;
    private static final int CORNER_RADIUS = 10;
    private static final int SHADOW_OFFSET = 6;

    private final PlaceholderTextField txtEmail;
    private final PlaceholderPasswordField txtPassword;
    private final JButton btnLogin;

    public LoginCardPanel(Runnable onSignUp, Runnable onForgotPassword, Runnable onLogin) {
        setOpaque(false);
        setLayout(new GridBagLayout());

        txtEmail = UIHelper.createTextField("Enter your email");
        txtPassword = UIHelper.createPasswordField("Enter your password");
        btnLogin = UIHelper.createPrimaryButton("Log In");
        btnLogin.addActionListener(e -> onLogin.run());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 14, 0);
        add(new LogoPanel(), gbc);

        String[] titleLines = {
            "Student Hostel Maintenance",
            "Request",
            "&",
            "Tracking System"
        };
        for (String line : titleLines) {
            gbc.gridy++;
            gbc.insets = new Insets(0, 0, "&".equals(line) ? 0 : 2, 0);
            add(createTitleLine(line), gbc);
        }

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(30, H_PAD, 6, H_PAD);
        add(UIHelper.createFieldLabel("Email"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, H_PAD, 14, H_PAD);
        add(txtEmail, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, H_PAD, 6, H_PAD);
        add(UIHelper.createFieldLabel("Password"), gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, H_PAD, 10, H_PAD);
        add(txtPassword, gbc);

        JPanel linksRow = new JPanel(new java.awt.BorderLayout());
        linksRow.setOpaque(false);
        linksRow.setPreferredSize(new Dimension(CONTENT_WIDTH, 22));

        JLabel lblSignUp = UIHelper.createHyperlink("No account? Sign Up");
        UIHelper.addHyperlinkAction(lblSignUp, onSignUp);
        JLabel lblForgot = UIHelper.createHyperlink("Forgot password?");
        UIHelper.addHyperlinkAction(lblForgot, onForgotPassword);
        linksRow.add(lblSignUp, java.awt.BorderLayout.WEST);
        linksRow.add(lblForgot, java.awt.BorderLayout.EAST);

        gbc.gridy++;
        gbc.insets = new Insets(0, H_PAD, 22, H_PAD);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(linksRow, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, H_PAD, V_PAD, H_PAD);
        add(btnLogin, gbc);
    }

    public PlaceholderTextField getEmailField() {
        return txtEmail;
    }

    public PlaceholderPasswordField getPasswordField() {
        return txtPassword;
    }

    public JButton getLoginButton() {
        return btnLogin;
    }

    private static JLabel createTitleLine(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(AppFonts.loginHeader());
        label.setForeground(AppColors.PRIMARY);
        label.setPreferredSize(new Dimension(CONTENT_WIDTH, "&".equals(text) ? 22 : 30));
        return label;
    }

    @Override
    public Dimension getPreferredSize() {
        int width = CONTENT_WIDTH + H_PAD * 2 + SHADOW_OFFSET;
        int height = V_PAD * 2 + SHADOW_OFFSET + 560;
        return new Dimension(width, height);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cardW = getWidth() - SHADOW_OFFSET;
        int cardH = getHeight() - SHADOW_OFFSET;

        for (int i = 12; i >= 1; i--) {
            float alpha = 12f / 255f / (i * 0.5f + 1f);
            g2.setColor(new Color(0f, 0f, 0f, alpha));
            int spread = SHADOW_OFFSET + i / 2;
            g2.fillRoundRect(
                    spread / 2,
                    spread / 2,
                    cardW + spread / 2,
                    cardH + spread / 2,
                    CORNER_RADIUS + 2,
                    CORNER_RADIUS + 2);
        }

        g2.setColor(AppColors.CARD);
        g2.fillRoundRect(0, 0, cardW, cardH, CORNER_RADIUS, CORNER_RADIUS);
        g2.dispose();
        super.paintComponent(g);
    }
}
