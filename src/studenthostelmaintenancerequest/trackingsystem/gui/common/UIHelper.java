package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public final class UIHelper {

    public static final int FIELD_WIDTH = 520;
    public static final int FIELD_HEIGHT = 52;
    public static final int HALF_FIELD_WIDTH = 248;
    public static final int SIGNUP_FIELD_WIDTH = 720;
    public static final int SIGNUP_HALF_WIDTH = 348;

    private UIHelper() {
    }

    public static Border inputBorder() {
        return new CompoundBorder(
                new LineBorder(AppColors.BORDER, 2, true),
                new EmptyBorder(10, 16, 10, 16));
    }

    public static Border comboBorder() {
        return new CompoundBorder(
                new LineBorder(AppColors.BORDER, 2, true),
                new EmptyBorder(8, 16, 8, 16));
    }

    public static JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(AppFonts.label());
        label.setForeground(AppColors.LABEL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    public static JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel("<html><div style='text-align:center;'>" + text + "</div></html>");
        label.setFont(AppFonts.header());
        label.setForeground(AppColors.PRIMARY);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    public static JLabel createInstructionLabel(String text) {
        JLabel label = new JLabel("<html><div style='text-align:center;width:520px;'>" + text + "</div></html>");
        label.setFont(AppFonts.body());
        label.setForeground(AppColors.LABEL);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    public static PlaceholderTextField createTextField(String placeholder) {
        PlaceholderTextField field = new PlaceholderTextField(placeholder);
        field.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        field.setMaximumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        return field;
    }

    public static PlaceholderTextField createSignupTextField(String placeholder) {
        PlaceholderTextField field = new PlaceholderTextField(placeholder);
        field.setPreferredSize(new Dimension(SIGNUP_FIELD_WIDTH, FIELD_HEIGHT));
        field.setMaximumSize(new Dimension(SIGNUP_FIELD_WIDTH, FIELD_HEIGHT));
        return field;
    }

    public static PlaceholderTextField createHalfTextField(String placeholder) {
        PlaceholderTextField field = new PlaceholderTextField(placeholder);
        field.setPreferredSize(new Dimension(HALF_FIELD_WIDTH, FIELD_HEIGHT));
        field.setMaximumSize(new Dimension(HALF_FIELD_WIDTH, FIELD_HEIGHT));
        return field;
    }

    public static PlaceholderTextField createSignupHalfTextField(String placeholder) {
        PlaceholderTextField field = new PlaceholderTextField(placeholder);
        field.setPreferredSize(new Dimension(SIGNUP_HALF_WIDTH, FIELD_HEIGHT));
        field.setMaximumSize(new Dimension(SIGNUP_HALF_WIDTH, FIELD_HEIGHT));
        return field;
    }

    public static PlaceholderPasswordField createPasswordField(String placeholder) {
        PlaceholderPasswordField field = new PlaceholderPasswordField(placeholder);
        field.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        field.setMaximumSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        return field;
    }

    public static PlaceholderPasswordField createSignupHalfPasswordField(String placeholder) {
        PlaceholderPasswordField field = new PlaceholderPasswordField(placeholder);
        field.setPreferredSize(new Dimension(SIGNUP_HALF_WIDTH, FIELD_HEIGHT));
        field.setMaximumSize(new Dimension(SIGNUP_HALF_WIDTH, FIELD_HEIGHT));
        return field;
    }

    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.PRIMARY);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(AppFonts.bodyBold());
        button.setForeground(AppColors.BUTTON_TEXT);
        button.setBackground(AppColors.PRIMARY);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(FIELD_WIDTH, 56));
        button.setMaximumSize(new Dimension(FIELD_WIDTH, 56));
        return button;
    }

    public static JButton createSignupPrimaryButton(String text) {
        JButton button = createPrimaryButton(text);
        button.setPreferredSize(new Dimension(SIGNUP_FIELD_WIDTH, 56));
        button.setMaximumSize(new Dimension(SIGNUP_FIELD_WIDTH, 56));
        return button;
    }

    public static JLabel createHyperlink(String text) {
        JLabel link = new JLabel("<html><u>" + text + "</u></html>");
        link.setFont(AppFonts.link());
        link.setForeground(AppColors.PRIMARY);
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return link;
    }

    public static void addHyperlinkAction(JLabel link, Runnable action) {
        link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <T> JComboBox<T> createComboBox(T[] items) {
        JComboBox<T> combo = new JComboBox<>(items);
        combo.setFont(AppFonts.body());
        combo.setBackground(Color.WHITE);
        combo.setForeground(AppColors.LABEL);
        combo.setBorder(comboBorder());
        combo.setPreferredSize(new Dimension(SIGNUP_FIELD_WIDTH, FIELD_HEIGHT));
        combo.setMaximumSize(new Dimension(SIGNUP_FIELD_WIDTH, FIELD_HEIGHT));
        return combo;
    }

    public static JPanel createFieldGroup(JLabel label, JComponent field) {
        JPanel group = new JPanel();
        group.setOpaque(false);
        group.setLayout(new javax.swing.BoxLayout(group, javax.swing.BoxLayout.Y_AXIS));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        group.add(label);
        group.add(javax.swing.Box.createVerticalStrut(8));
        group.add(field);
        return group;
    }

    public static JPanel createTwoColumnRow(JPanel left, JPanel right) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 24, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(left);
        row.add(right);
        return row;
    }

    public static JPanel centerCard(JPanel card, int cardWidth) {
        int height = Math.max(card.getPreferredSize().height, 400);
        card.setPreferredSize(new Dimension(cardWidth, height));
        card.setMaximumSize(new Dimension(cardWidth, Integer.MAX_VALUE));

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        wrapper.add(card, gbc);
        return wrapper;
    }

    public static void setupAuthFrame(javax.swing.JFrame frame, String title, boolean exitOnClose) {
        frame.setTitle(title);
        frame.setDefaultCloseOperation(
                exitOnClose ? javax.swing.WindowConstants.EXIT_ON_CLOSE
                        : javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setMinimumSize(new Dimension(1280, 800));
        frame.setPreferredSize(new Dimension(1280, 800));
        frame.setContentPane(new GradientBackgroundPanel());
        frame.getContentPane().setLayout(new GridBagLayout());
    }

    public static void showFrame(javax.swing.JFrame frame) {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void navigateTo(javax.swing.JFrame current, javax.swing.JFrame next) {
        current.dispose();
        showFrame(next);
    }

    public static JPanel createPasswordWithToggle(PlaceholderPasswordField field) {
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setPreferredSize(field.getPreferredSize());
        container.setMaximumSize(field.getMaximumSize());
        container.setBorder(inputBorder());
        container.setBackground(AppColors.INPUT_FILL);

        field.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 8));
        field.setPreferredSize(new Dimension(FIELD_WIDTH - 52, FIELD_HEIGHT - 4));
        field.setMaximumSize(new Dimension(FIELD_WIDTH - 52, FIELD_HEIGHT - 4));
        container.setOpaque(true);

        JButton toggle = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.EYE_ICON);
                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                g2.drawOval(cx - 10, cy - 7, 20, 14);
                g2.fillOval(cx - 4, cy - 2, 8, 8);
                g2.dispose();
            }
        };
        toggle.setPreferredSize(new Dimension(40, FIELD_HEIGHT - 4));
        toggle.setBorderPainted(false);
        toggle.setContentAreaFilled(false);
        toggle.setFocusPainted(false);
        toggle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toggle.setToolTipText("Toggle password visibility");

        final boolean[] visible = {false};
        toggle.addActionListener(e -> {
            visible[0] = !visible[0];
            field.setPasswordVisible(visible[0]);
        });

        container.add(field, BorderLayout.CENTER);
        container.add(toggle, BorderLayout.EAST);
        return container;
    }

    public static JPanel createRequirementsPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(AppColors.REQUIREMENTS_FILL);
        panel.setBorder(new CompoundBorder(
                new LineBorder(AppColors.BORDER, 2, true),
                new EmptyBorder(20, 24, 20, 24)));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(FIELD_WIDTH, 220));

        JLabel title = new JLabel("Password must contain:");
        title.setFont(AppFonts.reqTitle());
        title.setForeground(AppColors.LABEL);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(title);
        panel.add(javax.swing.Box.createVerticalStrut(12));
        panel.add(createRequirementItem("At least 8 characters"));
        panel.add(javax.swing.Box.createVerticalStrut(8));
        panel.add(createRequirementItem("One uppercase letter"));
        panel.add(javax.swing.Box.createVerticalStrut(8));
        panel.add(createRequirementItem("One number or symbol"));
        return panel;
    }

    private static JPanel createRequirementItem(String text) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icon = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.LABEL);
                g2.fillOval(2, 2, 22, 22);
                g2.setColor(Color.WHITE);
                g2.setStroke(new java.awt.BasicStroke(2f));
                g2.drawLine(7, 12, 11, 16);
                g2.drawLine(11, 16, 18, 8);
                g2.dispose();
            }
        };
        icon.setPreferredSize(new Dimension(26, 26));

        JLabel label = new JLabel(text);
        label.setFont(AppFonts.reqItem());
        label.setForeground(AppColors.LABEL);

        row.add(icon);
        row.add(label);
        return row;
    }

    public static JScrollPane wrapSignupCard(JPanel card) {
        JScrollPane scroll = new JScrollPane(card);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(900, 760));
        return scroll;
    }
}
