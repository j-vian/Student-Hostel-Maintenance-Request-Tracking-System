package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import studenthostelmaintenancerequest.trackingsystem.SessionManager;
import studenthostelmaintenancerequest.trackingsystem.gui.auth.LoginFrame;
import javax.swing.border.LineBorder;

public final class UIHelper {

    public static final int FRAME_WIDTH = 1100;
    public static final int FRAME_HEIGHT = 720;

    public static final int FIELD_WIDTH = 420;
    public static final int FIELD_HEIGHT = 42;
    public static final int BUTTON_HEIGHT = 44;

    public static final int LOGIN_CONTENT_WIDTH = 420;
    public static final int AUTH_CONTENT_WIDTH = 500;

    public static final int SIGNUP_FIELD_WIDTH = 560;
    public static final int SIGNUP_HALF_WIDTH = 272;
    public static final int SIGNUP_CONTENT_WIDTH = 560;

    public static final int MANAGER_SIDEBAR_WIDTH = 220;
    public static final int MANAGER_HEADER_HEIGHT = 64;
    public static final int MANAGER_PAGE_HEADER_HEIGHT = 72;
    public static final int MANAGER_STAT_CARD_HEIGHT = 120;
    public static final int MANAGER_MAIN_PADDING = 32;
    public static final int MANAGER_SEARCH_HEIGHT = 36;

    private UIHelper() {
    }

    public static Border inputBorder() {
        return new CompoundBorder(
                new LineBorder(AppColors.BORDER, 1, true),
                new EmptyBorder(10, 14, 10, 14));
    }

    public static Border comboBorder() {
        return new CompoundBorder(
                new LineBorder(AppColors.BORDER, 1, true),
                new EmptyBorder(6, 12, 6, 12));
    }

    /** Fixed width for inputs/buttons only — never use on labels or cards. */
    public static void fixSize(JComponent comp, int width, int height) {
        comp.setAlignmentX(Component.LEFT_ALIGNMENT);
        Dimension size = new Dimension(width, height);
        comp.setPreferredSize(size);
        comp.setMinimumSize(size);
        comp.setMaximumSize(size);
    }

    /** Let height grow naturally; only cap width (for columns / text blocks). */
    public static void limitWidth(JComponent comp, int width) {
        comp.setAlignmentX(Component.LEFT_ALIGNMENT);
        comp.setMaximumSize(new Dimension(width, Integer.MAX_VALUE));
    }

    /** Fixed content width; height grows with children (auth form columns). */
    public static void fixWidth(JComponent comp, int width) {
        comp.setAlignmentX(Component.CENTER_ALIGNMENT);
        comp.setMinimumSize(new Dimension(width, 0));
        comp.setMaximumSize(new Dimension(width, Integer.MAX_VALUE));
    }

    public static JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(AppFonts.label());
        label.setForeground(AppColors.LABEL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    /**
     * Applies shared colours and fonts to a login form built in the NetBeans Form Editor.
     */
    public static void styleLoginFrame(
            JLabel lblTitle1, JLabel lblTitle2, JLabel lblTitle3, JLabel lblTitle4,
            JLabel lblEmail, JLabel lblPassword,
            JLabel lblSignUp, JLabel lblForgotPassword,
            JButton btnLogin,
            PlaceholderTextField txtEmail,
            JPanel pnlCard) {

        lblTitle1.setFont(AppFonts.loginHeader());
        lblTitle2.setFont(AppFonts.loginHeader());
        lblTitle3.setFont(AppFonts.loginHeader());
        lblTitle4.setFont(AppFonts.loginHeader());
        lblTitle1.setForeground(AppColors.PRIMARY);
        lblTitle2.setForeground(AppColors.PRIMARY);
        lblTitle3.setForeground(AppColors.PRIMARY);
        lblTitle4.setForeground(AppColors.PRIMARY);

        lblEmail.setFont(AppFonts.label());
        lblPassword.setFont(AppFonts.label());
        lblEmail.setForeground(AppColors.LABEL);
        lblPassword.setForeground(AppColors.LABEL);

        lblSignUp.setText("<html><u>No account? Sign Up</u></html>");
        lblForgotPassword.setText("<html><u>Forgot password?</u></html>");
        lblSignUp.setFont(AppFonts.link());
        lblForgotPassword.setFont(AppFonts.link());
        lblSignUp.setForeground(AppColors.PRIMARY);
        lblForgotPassword.setForeground(AppColors.PRIMARY);
        lblSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblForgotPassword.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        fixSize(txtEmail, FIELD_WIDTH, FIELD_HEIGHT);
        stylePrimaryButton(btnLogin);

        pnlCard.setBackground(AppColors.CARD);
        pnlCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.BORDER, 1, true),
                new EmptyBorder(0, 0, 0, 0)));
    }

    public static void stylePrimaryButton(JButton button) {
        stylePrimaryButton(button, FIELD_WIDTH);
    }

    public static void styleSignupPrimaryButton(JButton button) {
        stylePrimaryButton(button, SIGNUP_FIELD_WIDTH);
    }

    public static void stylePrimaryButton(JButton button, int width) {
        button.setFont(AppFonts.bodyBold());
        button.setForeground(AppColors.BUTTON_TEXT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setUI(PrimaryButtonUI.INSTANCE);
        fixSize(button, width, BUTTON_HEIGHT);
    }

    public static void styleSignupFieldLabel(JLabel... labels) {
        for (JLabel label : labels) {
            label.setFont(AppFonts.label());
            label.setForeground(AppColors.LABEL);
        }
    }

    public static void styleSignupTextField(PlaceholderTextField... fields) {
        for (PlaceholderTextField field : fields) {
            fixSize(field, SIGNUP_FIELD_WIDTH, FIELD_HEIGHT);
        }
    }

    public static void styleSignupHalfTextField(PlaceholderTextField... fields) {
        for (PlaceholderTextField field : fields) {
            fixSize(field, SIGNUP_HALF_WIDTH, FIELD_HEIGHT);
        }
    }

    public static void styleSignupHalfPasswordField(PlaceholderPasswordField... fields) {
        for (PlaceholderPasswordField field : fields) {
            fixSize(field, SIGNUP_HALF_WIDTH, FIELD_HEIGHT);
        }
    }

    @SuppressWarnings("unchecked")
    public static void styleSignupComboBox(JComboBox<?>... combos) {
        for (JComboBox<?> combo : combos) {
            combo.setFont(AppFonts.body());
            combo.setBackground(Color.WHITE);
            combo.setForeground(AppColors.LABEL);
            combo.setBorder(comboBorder());
            fixSize((JComponent) combo, SIGNUP_FIELD_WIDTH, FIELD_HEIGHT);
        }
    }

    public static void styleSignupCard(JPanel pnlCard, JLabel lblTitle, JButton btnSignUp) {
        lblTitle.setFont(AppFonts.header());
        lblTitle.setForeground(AppColors.PRIMARY);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        styleSignupPrimaryButton(btnSignUp);
        styleAuthCard(pnlCard);
    }

    public static void styleAuthCard(JPanel pnlCard) {
        pnlCard.setBackground(AppColors.CARD);
        pnlCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.BORDER, 1, true),
                new EmptyBorder(0, 0, 0, 0)));
    }

    public static void styleAuthTitle(JLabel lblTitle) {
        lblTitle.setFont(AppFonts.header());
        lblTitle.setForeground(AppColors.PRIMARY);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
    }

    public static void styleAuthInstructionLine(JLabel... labels) {
        for (JLabel label : labels) {
            label.setFont(AppFonts.body());
            label.setForeground(AppColors.LABEL);
            label.setHorizontalAlignment(JLabel.CENTER);
        }
    }

    /**
     * @deprecated Use {@link #styleAuthInstructionLine(JLabel...)} for reliable centered lines.
     */
    @Deprecated
    public static void styleAuthInstruction(JLabel lblInstruction, int contentWidth) {
        String text = lblInstruction.getText()
                .replace("<html>", "")
                .replace("</html>", "");
        String html = String.format(
                "<html><div style='text-align:center;width:%dpx;font-family:%s;font-size:%dpt;"
                        + "color:#1A1C1C;line-height:1.45;'>%s</div></html>",
                contentWidth, AppFonts.cssFamily(), AppFonts.BODY_SIZE, text);
        lblInstruction.setText(html);
        lblInstruction.setHorizontalAlignment(JLabel.CENTER);
        lblInstruction.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        int height = measureHtmlLabelHeight(lblInstruction, html, contentWidth);
        Dimension size = new Dimension(contentWidth, height);
        lblInstruction.setPreferredSize(size);
        lblInstruction.setMinimumSize(size);
        lblInstruction.setMaximumSize(size);
    }

    private static int measureHtmlLabelHeight(JLabel label, String html, int width) {
        View view = BasicHTML.createHTMLView(label, html);
        if (view == null) {
            return 48;
        }
        view.setSize(width, 0);
        return Math.max((int) Math.ceil(view.getPreferredSpan(View.Y_AXIS)) + 6, 40);
    }

    public static void styleAuthFieldLabel(JLabel... labels) {
        for (JLabel label : labels) {
            label.setFont(AppFonts.label());
            label.setForeground(AppColors.LABEL);
        }
    }

    public static void styleAuthTextField(PlaceholderTextField... fields) {
        for (PlaceholderTextField field : fields) {
            fixSize(field, FIELD_WIDTH, FIELD_HEIGHT);
        }
    }

    public static void styleReturnLink(JLabel lblReturn) {
        String text = lblReturn.getText();
        lblReturn.setText("<html><u>" + text + "</u></html>");
        lblReturn.setFont(AppFonts.link());
        lblReturn.setForeground(AppColors.PRIMARY);
        lblReturn.setHorizontalAlignment(JLabel.CENTER);
        lblReturn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void centerAuthCard(javax.swing.JFrame frame, JPanel pnlBackground) {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        pnlBackground.revalidate();
        pnlBackground.repaint();
    }

    private static final class PrimaryButtonUI extends BasicButtonUI {

        private static final PrimaryButtonUI INSTANCE = new PrimaryButtonUI();

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton button = (AbstractButton) c;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(button.isEnabled() ? AppColors.PRIMARY : AppColors.PRIMARY.darker());
            g2.fillRect(0, 0, c.getWidth(), c.getHeight());
            g2.dispose();
            super.paint(g, c);
        }
    }

    public static JLabel createHeaderLabel(String text, int contentWidth) {
        String html = String.format(
                "<html><div style='text-align:center;width:%dpx;font-family:%s;font-size:%dpt;"
                        + "font-weight:700;color:#0D5C63;line-height:1.3;'>%s</div></html>",
                contentWidth, AppFonts.cssFamily(), AppFonts.HEADER_SIZE, text);
        JLabel label = new JLabel(html);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        sizeMultilineLabel(label, contentWidth);
        return label;
    }

    public static JPanel createLoginHeader(String... lines) {
        JPanel block = new JPanel();
        block.setOpaque(false);
        block.setLayout(new javax.swing.BoxLayout(block, javax.swing.BoxLayout.Y_AXIS));
        block.setAlignmentX(Component.CENTER_ALIGNMENT);
        fixWidth(block, LOGIN_CONTENT_WIDTH);

        for (String line : lines) {
            JLabel label = new JLabel(line, JLabel.CENTER);
            label.setFont(AppFonts.loginHeader());
            label.setForeground(AppColors.PRIMARY);
            label.setHorizontalAlignment(JLabel.CENTER);
            int lineHeight = "&".equals(line) ? 22 : 30;
            fixSize(label, LOGIN_CONTENT_WIDTH, lineHeight);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            block.add(label);
        }
        return block;
    }

    public static JLabel createInstructionLabel(String text, int contentWidth) {
        String html = String.format(
                "<html><div style='text-align:center;width:%dpx;font-family:%s;font-size:%dpt;"
                        + "color:#1A1C1C;line-height:1.45;'>%s</div></html>",
                contentWidth, AppFonts.cssFamily(), AppFonts.BODY_SIZE, text);
        JLabel label = new JLabel(html);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        sizeMultilineLabel(label, contentWidth);
        return label;
    }

    private static void sizeMultilineLabel(JLabel label, int width) {
        Dimension pref = label.getPreferredSize();
        int height = Math.max(pref.height + 6, 24);
        Dimension size = new Dimension(width, height);
        label.setPreferredSize(size);
        label.setMinimumSize(size);
        label.setMaximumSize(new Dimension(width, Integer.MAX_VALUE));
    }

    public static JPanel centerHorizontally(JComponent component, int rowWidth) {
        JPanel row = new JPanel();
        row.setOpaque(false);
        row.setLayout(new javax.swing.BoxLayout(row, javax.swing.BoxLayout.X_AXIS));
        int rowHeight = Math.max(component.getPreferredSize().height, 1);
        fixSize(row, rowWidth, rowHeight);
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.add(javax.swing.Box.createHorizontalGlue());
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.add(component);
        row.add(javax.swing.Box.createHorizontalGlue());
        return row;
    }

    public static void initApplicationLook() {
        java.awt.Font body = AppFonts.body();
        javax.swing.UIManager.put("Label.font", body);
        javax.swing.UIManager.put("Button.font", AppFonts.bodyBold());
        javax.swing.UIManager.put("TextField.font", body);
        javax.swing.UIManager.put("PasswordField.font", body);
        javax.swing.UIManager.put("ComboBox.font", body);
        javax.swing.UIManager.put("ScrollPane.font", body);
    }

    public static PlaceholderTextField createTextField(String placeholder) {
        PlaceholderTextField field = new PlaceholderTextField(placeholder);
        fixSize(field, FIELD_WIDTH, FIELD_HEIGHT);
        return field;
    }

    public static PlaceholderTextField createSignupTextField(String placeholder) {
        PlaceholderTextField field = new PlaceholderTextField(placeholder);
        fixSize(field, SIGNUP_FIELD_WIDTH, FIELD_HEIGHT);
        return field;
    }

    public static PlaceholderTextField createSignupHalfTextField(String placeholder) {
        PlaceholderTextField field = new PlaceholderTextField(placeholder);
        fixSize(field, SIGNUP_HALF_WIDTH, FIELD_HEIGHT);
        return field;
    }

    public static PlaceholderPasswordField createPasswordField(String placeholder) {
        PlaceholderPasswordField field = new PlaceholderPasswordField(placeholder);
        fixSize(field, FIELD_WIDTH, FIELD_HEIGHT);
        return field;
    }

    public static PlaceholderPasswordField createSignupHalfPasswordField(String placeholder) {
        PlaceholderPasswordField field = new PlaceholderPasswordField(placeholder);
        fixSize(field, SIGNUP_HALF_WIDTH, FIELD_HEIGHT);
        return field;
    }

    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        stylePrimaryButton(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    public static JButton createSignupPrimaryButton(String text) {
        JButton button = new JButton(text);
        styleSignupPrimaryButton(button);
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
        fixSize(combo, SIGNUP_FIELD_WIDTH, FIELD_HEIGHT);
        return combo;
    }

    public static JPanel createFieldGroup(JLabel label, JComponent field) {
        JPanel group = new JPanel();
        group.setOpaque(false);
        group.setLayout(new javax.swing.BoxLayout(group, javax.swing.BoxLayout.Y_AXIS));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        group.add(label);
        group.add(javax.swing.Box.createVerticalStrut(6));
        group.add(field);
        group.setAlignmentX(Component.CENTER_ALIGNMENT);
        fixWidth(group, FIELD_WIDTH);
        return group;
    }

    public static JPanel createTwoColumnRow(JPanel left, JPanel right) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(left);
        row.add(right);
        return row;
    }

    public static JPanel createFormColumn(int width) {
        JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new javax.swing.BoxLayout(column, javax.swing.BoxLayout.Y_AXIS));
        column.setAlignmentX(Component.LEFT_ALIGNMENT);
        column.setMinimumSize(new Dimension(width, 0));
        limitWidth(column, width);
        return column;
    }

    /**
     * Background fills the whole window; card floats centered horizontally and vertically.
     */
    public static void setupAuthFrame(javax.swing.JFrame frame, String title, boolean exitOnClose) {
        frame.setTitle(title);
        frame.setDefaultCloseOperation(
                exitOnClose ? javax.swing.WindowConstants.EXIT_ON_CLOSE
                        : javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        frame.setResizable(true);

        GradientBackgroundPanel background = new GradientBackgroundPanel();
        background.setLayout(new BorderLayout());
        frame.setContentPane(background);
    }

    public static void mountFloatingCard(javax.swing.JFrame frame, JComponent card) {
        JPanel centerSlot = new JPanel(new GridBagLayout());
        centerSlot.setOpaque(false);

        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.gridx = 0;
        cardGbc.gridy = 0;
        cardGbc.anchor = GridBagConstraints.CENTER;
        cardGbc.fill = GridBagConstraints.NONE;
        cardGbc.weightx = 0;
        cardGbc.weighty = 0;
        centerSlot.add(card, cardGbc);

        frame.getContentPane().add(centerSlot, BorderLayout.CENTER);
    }

    /**
     * Pins the card to its preferred size and keeps it centered when the window resizes.
     */
    public static void mountCenteredCard(javax.swing.JFrame frame, JComponent card) {
        java.awt.Container background = frame.getContentPane();
        background.setLayout(null);
        background.add(card);
        background.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                centerComponent(background, card);
            }
        });
        centerComponent(background, card);
    }

    private static void centerComponent(java.awt.Container parent, JComponent child) {
        Dimension size = child.getPreferredSize();
        int x = Math.max(0, (parent.getWidth() - size.width) / 2);
        int y = Math.max(0, (parent.getHeight() - size.height) / 2);
        child.setBounds(x, y, size.width, size.height);
    }

    /**
     * Keeps a form card centered inside a scroll pane when it fits, and scrollable when it grows.
     */
    public static void centerCardInScrollPane(JScrollPane scrollPane, JPanel host, JComponent card) {
        host.removeAll();
        host.setLayout(new GridBagLayout());
        host.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new java.awt.Insets(32, 0, 32, 0);
        host.add(card, gbc);

        java.awt.event.ComponentAdapter resizeHandler = new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                refreshScrollHostSize(scrollPane, host, card, gbc.insets);
            }
        };

        scrollPane.getViewport().addComponentListener(resizeHandler);
        card.addComponentListener(resizeHandler);
        javax.swing.SwingUtilities.invokeLater(
                () -> refreshScrollHostSize(scrollPane, host, card, gbc.insets));
    }

    public static void refreshScrollHostSize(
            JScrollPane scrollPane, JPanel host, JComponent card, java.awt.Insets insets) {
        Dimension viewport = scrollPane.getViewport().getExtentSize();
        if (viewport.width <= 0 || viewport.height <= 0) {
            return;
        }
        int cardHeight = card.getPreferredSize().height + insets.top + insets.bottom;
        int cardWidth = card.getPreferredSize().width;
        host.setPreferredSize(new Dimension(
                Math.max(viewport.width, cardWidth),
                Math.max(viewport.height, cardHeight)));
        host.revalidate();
    }

    public static void refreshScrollHostSize(JScrollPane scrollPane, JPanel host, JComponent card) {
        refreshScrollHostSize(scrollPane, host, card, new java.awt.Insets(32, 0, 32, 0));
    }

    /**
     * For tall forms: background fills window, card scrolls if needed and stays centered in view.
     */
    public static void mountScrollableCard(javax.swing.JFrame frame, JComponent card) {
        JPanel viewportHost = new JPanel(new GridBagLayout());
        viewportHost.setOpaque(false);

        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.gridx = 0;
        cardGbc.gridy = 0;
        cardGbc.weightx = 1;
        cardGbc.weighty = 1;
        cardGbc.anchor = GridBagConstraints.CENTER;
        cardGbc.insets = new java.awt.Insets(32, 0, 32, 0);
        viewportHost.add(card, cardGbc);

        JScrollPane scroll = new JScrollPane(viewportHost);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        frame.getContentPane().add(scroll, BorderLayout.CENTER);
    }

    public static void showFrame(javax.swing.JFrame frame) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = Math.min(FRAME_WIDTH, screen.width - 48);
        int height = Math.min(FRAME_HEIGHT, screen.height - 96);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        javax.swing.SwingUtilities.invokeLater(() -> {
            java.awt.Container content = frame.getContentPane();
            if (content.getLayout() == null) {
                for (java.awt.Component child : content.getComponents()) {
                    if (child instanceof JComponent) {
                        centerComponent(content, (JComponent) child);
                    }
                }
            }
            frame.revalidate();
            frame.repaint();
        });
    }

    public static void navigateTo(javax.swing.JFrame current, javax.swing.JFrame next) {
        current.dispose();
        showFrame(next);
    }

    public static JPanel createPasswordWithToggle(PlaceholderPasswordField field) {
        return createPasswordWithToggle(field, FIELD_WIDTH);
    }

    public static JPanel createPasswordWithToggle(PlaceholderPasswordField field, int width) {
        JPanel container = new JPanel(new BorderLayout(0, 0));
        container.setOpaque(true);
        container.setBackground(AppColors.INPUT_FILL);
        container.setBorder(BorderFactory.createLineBorder(AppColors.BORDER, 1, true));
        fixSize(container, width, FIELD_HEIGHT);

        field.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 4));
        field.setOpaque(false);

        JButton toggle = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.EYE_ICON);
                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                g2.drawOval(cx - 9, cy - 6, 18, 12);
                g2.fillOval(cx - 3, cy - 2, 6, 6);
                g2.dispose();
            }
        };
        toggle.setPreferredSize(new Dimension(36, FIELD_HEIGHT - 2));
        toggle.setMinimumSize(new Dimension(36, FIELD_HEIGHT - 2));
        toggle.setMaximumSize(new Dimension(36, FIELD_HEIGHT - 2));
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
                new LineBorder(AppColors.BORDER, 1, true),
                new EmptyBorder(16, 18, 16, 18)));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        limitWidth(panel, FIELD_WIDTH);

        JLabel title = new JLabel("Password must contain:");
        title.setFont(AppFonts.reqTitle());
        title.setForeground(AppColors.LABEL);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(title);
        panel.add(javax.swing.Box.createVerticalStrut(10));
        panel.add(createRequirementItem("At least 8 characters"));
        panel.add(javax.swing.Box.createVerticalStrut(6));
        panel.add(createRequirementItem("One uppercase letter"));
        panel.add(javax.swing.Box.createVerticalStrut(6));
        panel.add(createRequirementItem("One number or symbol"));
        return panel;
    }

    public static void styleManagerShell(
            JPanel pnlHeader, JPanel pnlSidebar, JPanel pnlMain,
            JLabel lblAppTitle, LogoPanel pnlHeaderLogo) {

        pnlHeader.setBackground(AppColors.SURFACE);
        pnlHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        pnlSidebar.setBackground(AppColors.SURFACE);
        pnlSidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, AppColors.BORDER));
        pnlMain.setBackground(AppColors.BACKGROUND);

        lblAppTitle.setFont(AppFonts.appBarTitle());
        lblAppTitle.setForeground(AppColors.LABEL);

        pnlHeaderLogo.setPreferredSize(new Dimension(36, 36));
        pnlHeaderLogo.setMinimumSize(new Dimension(36, 36));
        pnlHeaderLogo.setMaximumSize(new Dimension(36, 36));
    }

    public static void styleManagerPageHeader(JPanel pnlPageHeader, JLabel lblPageTitle) {
        pnlPageHeader.setBackground(AppColors.SURFACE);
        pnlPageHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.BORDER));
        lblPageTitle.setFont(AppFonts.pageTitle());
        lblPageTitle.setForeground(AppColors.LABEL);
    }

    public static void styleManagerNavButton(ManagerNavButton button, boolean active) {
        button.setActive(active);
    }

    public static void layoutManagerSidebar(JPanel pnlSidebar, ManagerNavButton... buttons) {
        pnlSidebar.removeAll();
        pnlSidebar.setLayout(new javax.swing.BoxLayout(pnlSidebar, javax.swing.BoxLayout.Y_AXIS));
        pnlSidebar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, AppColors.BORDER),
                new EmptyBorder(MANAGER_PAGE_HEADER_HEIGHT, 12, 16, 12)));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new javax.swing.BoxLayout(menuPanel, javax.swing.BoxLayout.Y_AXIS));
        menuPanel.setBackground(AppColors.SURFACE);
        menuPanel.setBorder(BorderFactory.createLineBorder(AppColors.SURFACE, 1));
        menuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        for (int i = 0; i < buttons.length; i++) {
            if (i > 0) {
                menuPanel.add(javax.swing.Box.createVerticalStrut(ManagerNavButton.verticalGap()));
            }
            menuPanel.add(buttons[i]);
        }

        pnlSidebar.add(menuPanel);
        pnlSidebar.add(javax.swing.Box.createVerticalGlue());
    }

    public static void styleStatCard(StatCardPanel card, Color headerText, Color countText) {
        card.getTitleLabel().setFont(AppFonts.statTitle());
        card.getTitleLabel().setForeground(headerText);
        card.getCountLabel().setFont(AppFonts.statCount());
        card.getCountLabel().setForeground(countText);
    }

    public static void styleManagerTableSection(JLabel sectionTitle, JTable table, JScrollPane scroll) {
        sectionTitle.setFont(AppFonts.tableSection());
        sectionTitle.setForeground(AppColors.LABEL);
        sectionTitle.setHorizontalAlignment(JLabel.CENTER);
        sectionTitle.setOpaque(true);
        sectionTitle.setBackground(AppColors.STAT_TOTAL_HEADER);
        sectionTitle.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(AppColors.GRID_LINE, 1),
                new EmptyBorder(10, 14, 10, 14)));

        table.setFont(AppFonts.body());
        table.setForeground(AppColors.LABEL);
        table.setRowHeight(44);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        table.setGridColor(AppColors.GRID_LINE);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(false);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setFocusable(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JTableHeader header = table.getTableHeader();
        header.setFont(AppFonts.label());
        header.setForeground(AppColors.LABEL);
        header.setBackground(AppColors.STAT_TOTAL_HEADER);
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, AppColors.GRID_LINE));

        for (int column = 0; column < table.getColumnCount(); column++) {
            final int columnIndex = column;
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(
                        JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(
                            tbl, value, isSelected, hasFocus, row, col);
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setFont(AppFonts.label());
                    label.setForeground(AppColors.LABEL);
                    label.setBackground(AppColors.STAT_TOTAL_HEADER);
                    label.setOpaque(true);
                    boolean isLastColumn = columnIndex == tbl.getColumnCount() - 1;
                    label.setBorder(BorderFactory.createMatteBorder(
                            0, 0, 0, isLastColumn ? 0 : 1, AppColors.GRID_LINE));
                    return label;
                }
            };
            table.getColumnModel().getColumn(column).setHeaderRenderer(headerRenderer);
        }

        scroll.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, AppColors.GRID_LINE));
        scroll.getViewport().setBackground(AppColors.SURFACE);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    }

    public static void layoutManagerTableSectionFooter(
            JPanel tableSection, JLabel sectionTitle, JScrollPane scroll, JLabel footerLink) {

        tableSection.removeAll();
        tableSection.setLayout(new BorderLayout());
        tableSection.add(sectionTitle, BorderLayout.NORTH);
        tableSection.add(scroll, BorderLayout.CENTER);
        tableSection.add(footerLink, BorderLayout.SOUTH);

        footerLink.setHorizontalAlignment(JLabel.CENTER);
        footerLink.setOpaque(true);
        footerLink.setBackground(AppColors.SURFACE);
        footerLink.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 1, 1, AppColors.GRID_LINE),
                new EmptyBorder(12, 14, 12, 14)));
    }

    public static void sizeManagerOverviewTable(JTable table, JScrollPane scroll) {
        int headerHeight = table.getTableHeader().getPreferredSize().height;
        int bodyHeight = table.getRowHeight() * table.getRowCount();
        int height = headerHeight + bodyHeight + 2;
        Dimension size = new Dimension(scroll.getPreferredSize().width, height);
        scroll.setPreferredSize(size);
        scroll.setMinimumSize(size);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
    }

    private static final int MANAGER_TABLE_COL_ASSIGNED_STAFF = 3;
    private static final int MANAGER_TABLE_COL_STATUS = 4;

    public static DefaultTableModel createManagerRequestTableModel() {
        return createManagerRequestTableModel(new Object[0][5]);
    }

    public static DefaultTableModel createManagerRequestTableModel(Object[][] data) {
        return new DefaultTableModel(data, new String[]{
            "Request ID", "Request Type", "Student Name", "Assigned Staff", "Status"}) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private static boolean isSubmittedStatus(Object status) {
        return "SUBMITTED".equals(StatusBadgeLabel.formatStatus(String.valueOf(status)));
    }

    public static void applyManagerRequestTableRenderers(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setFont(AppFonts.body());
        centerRenderer.setForeground(AppColors.LABEL);
        centerRenderer.setBackground(AppColors.SURFACE);

        for (int column = 0; column < MANAGER_TABLE_COL_ASSIGNED_STAFF; column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(MANAGER_TABLE_COL_ASSIGNED_STAFF).setCellRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            Object status = tbl.getValueAt(row, MANAGER_TABLE_COL_STATUS);
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
            wrapper.setBackground(AppColors.SURFACE);

            if (isSubmittedStatus(status)) {
                wrapper.add(new NotAssignedBadgeLabel());
            } else {
                JLabel staffLabel = new JLabel(String.valueOf(value), JLabel.CENTER);
                staffLabel.setFont(AppFonts.bodyBold());
                staffLabel.setForeground(AppColors.LABEL);
                wrapper.add(staffLabel);
            }
            return wrapper;
        });

        table.getColumnModel().getColumn(MANAGER_TABLE_COL_STATUS).setCellRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            StatusBadgeLabel badge = new StatusBadgeLabel(String.valueOf(value));
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
            wrapper.setBackground(AppColors.SURFACE);
            wrapper.add(badge);
            return wrapper;
        });
    }

    public static void showManagerFrame(javax.swing.JFrame frame) {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void installManagerSession(
            javax.swing.JFrame frame,
            javax.swing.JPanel pnlUserProfile,
            javax.swing.JLabel lblUserName,
            javax.swing.JLabel lblUserRole,
            javax.swing.JButton btnUserMenu) {
        ManagerUserMenu.install(pnlUserProfile, lblUserName, lblUserRole, btnUserMenu,
                () -> {
                    SessionManager.clear();
                    navigateTo(frame, new LoginFrame());
                });
        if (SessionManager.isLoggedIn()) {
            lblUserName.setText(SessionManager.getDisplayUsername());
            lblUserRole.setText("ADMIN");
        }
    }

    public static void showDatabaseError(java.awt.Component parent, Exception ex) {
        String message = ex.getMessage() == null ? "An unexpected database error occurred." : ex.getMessage();
        Throwable cause = ex.getCause();
        if (cause != null && cause.getMessage() != null && !cause.getMessage().isBlank()) {
            message = message + "\n\n" + cause.getMessage();
        }
        javax.swing.JOptionPane.showMessageDialog(
                parent,
                message,
                "Database Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    public static JPanel createSearchField(PlaceholderTextField field) {
        int height = MANAGER_SEARCH_HEIGHT;
        JPanel container = new JPanel(new BorderLayout(4, 0));
        container.setOpaque(true);
        container.setBackground(AppColors.SURFACE);
        container.setBorder(new CompoundBorder(
                new LineBorder(AppColors.BORDER, 1, true),
                new EmptyBorder(0, 10, 0, 12)));
        container.setPreferredSize(new Dimension(0, height));
        container.setMinimumSize(new Dimension(200, height));
        container.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));

        JLabel icon = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.MUTED);
                g2.setStroke(new java.awt.BasicStroke(1.4f));
                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                g2.drawOval(cx - 6, cy - 6, 10, 10);
                g2.drawLine(cx + 2, cy + 2, cx + 7, cy + 7);
                g2.dispose();
            }
        };
        Dimension iconSize = new Dimension(24, height);
        icon.setPreferredSize(iconSize);
        icon.setMinimumSize(iconSize);
        icon.setMaximumSize(iconSize);
        icon.setOpaque(false);

        field.setBorder(new EmptyBorder(6, 0, 6, 0));
        field.setOpaque(false);
        field.setPreferredSize(new Dimension(0, height));
        field.setMinimumSize(new Dimension(0, height));

        container.add(icon, BorderLayout.WEST);
        container.add(field, BorderLayout.CENTER);
        return container;
    }

    public static void styleManagerToolbarButton(JButton button, int width) {
        button.setFont(AppFonts.bodyBold());
        button.setForeground(AppColors.LABEL);
        button.setBackground(AppColors.SURFACE);
        button.setBorder(new CompoundBorder(
                new LineBorder(AppColors.BORDER, 1, true),
                new EmptyBorder(6, 14, 6, 14)));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Dimension size = new Dimension(width, MANAGER_SEARCH_HEIGHT);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
    }

    public static void styleManagerFilterButton(JButton button) {
        styleManagerToolbarButton(button, 120);
        button.setIcon(new javax.swing.Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.LABEL);
                g2.setStroke(new java.awt.BasicStroke(1.5f));
                int[] xs = {x + 2, x + 14, x + 10, x + 6, x + 2};
                int[] ys = {y + 2, y + 2, y + 14, y + 14, y + 2};
                g2.drawPolyline(xs, ys, 5);
                g2.drawLine(x + 6, y + 14, x + 10, y + 14);
                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return 16;
            }

            @Override
            public int getIconHeight() {
                return 16;
            }
        });
        button.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        button.setIconTextGap(8);
    }

    public static void styleManagerUpdateButton(JButton button) {
        styleManagerToolbarButton(button, 120);
        button.setEnabled(true);
    }

    public static void styleManagerConfirmButton(JButton button) {
        styleManagerToolbarButton(button, 160);
        button.setEnabled(true);
    }

    public static void layoutManagerManageRequestToolbar(
            JPanel toolbar, JPanel searchField, JButton btnFilter, JButton btnAction) {

        toolbar.removeAll();
        toolbar.setLayout(new javax.swing.BoxLayout(toolbar, javax.swing.BoxLayout.Y_AXIS));
        toolbar.setOpaque(false);

        JPanel topRow = new JPanel(new BorderLayout(12, 0));
        topRow.setOpaque(false);
        topRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        topRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, MANAGER_SEARCH_HEIGHT));
        topRow.add(searchField, BorderLayout.CENTER);
        topRow.add(btnFilter, BorderLayout.EAST);

        JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomRow.setOpaque(false);
        bottomRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomRow.add(btnAction);

        toolbar.add(topRow);
        toolbar.add(javax.swing.Box.createVerticalStrut(10));
        toolbar.add(bottomRow);
    }

    public static final int MANAGE_REQUEST_PAGE_SIZE = 7;
    public static final int MANAGE_REQUEST_COL_ASSIGNED_STAFF = 3;
    public static final int MANAGE_REQUEST_COL_DATE_RAISED = 4;
    public static final int MANAGE_REQUEST_COL_STATUS = 5;

    public static ManagerManageRequestTableModel createManagerManageRequestTableModel() {
        return createManagerManageRequestTableModel(new Object[0][6]);
    }

    public static ManagerManageRequestTableModel createManagerManageRequestTableModel(Object[][] data) {
        return new ManagerManageRequestTableModel(data, new String[]{
            "Request ID", "Request Type", "Student Name",
            "Assigned Staff", "Date Raised", "Status"});
    }

    private static Object[][] buildManageRequestSampleData() {
        String[] types = {"Electrical", "Plumbing", "Furniture"};
        String[] students = {"Alex Johnson", "Maria Chen", "Sam Patel"};
        String[] staff = {"John Doe", "Jane Smith"};
        String[] dates = {"8 June 2026", "9 June 2026", "10 June 2026", "11 June 2026",
            "12 June 2026", "13 June 2026", "14 June 2026"};

        Object[][] rows = new Object[21][6];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Object[]{
                String.format("REQ%03d", i + 1),
                types[i % types.length],
                students[i % students.length],
                staff[i % staff.length],
                dates[i % dates.length],
                "IN PROGRESS"
            };
        }
        return rows;
    }

    public static final class ManagerManageRequestTableModel extends AbstractTableModel {

        private final java.util.List<Object[]> allRows = new java.util.ArrayList<>();
        private final String[] columnNames;
        private int currentPage;
        private boolean statusColumnEditable;

        public ManagerManageRequestTableModel(Object[][] data, Object[] columns) {
            this.columnNames = new String[columns.length];
            for (int i = 0; i < columns.length; i++) {
                this.columnNames[i] = String.valueOf(columns[i]);
            }
            for (Object[] row : data) {
                if (isActiveRequestStatus(row[MANAGE_REQUEST_COL_STATUS])) {
                    allRows.add(row.clone());
                }
            }
        }

        private static boolean isActiveRequestStatus(Object status) {
            return "IN PROGRESS".equals(StatusBadgeLabel.formatStatus(String.valueOf(status)));
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        public void setStatusColumnEditable(boolean editable) {
            this.statusColumnEditable = editable;
        }

        public int getTotalRowCount() {
            return allRows.size();
        }

        public int getPageSize() {
            return MANAGE_REQUEST_PAGE_SIZE;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getShowingFrom() {
            return currentPage * MANAGE_REQUEST_PAGE_SIZE + 1;
        }

        public int getShowingTo() {
            return Math.min((currentPage + 1) * MANAGE_REQUEST_PAGE_SIZE, allRows.size());
        }

        public boolean canGoPrevious() {
            return currentPage > 0;
        }

        public boolean canGoNext() {
            return (currentPage + 1) * MANAGE_REQUEST_PAGE_SIZE < allRows.size();
        }

        public void previousPage() {
            if (!canGoPrevious()) {
                return;
            }
            currentPage--;
            fireTableDataChanged();
        }

        public void nextPage() {
            if (!canGoNext()) {
                return;
            }
            currentPage++;
            fireTableDataChanged();
        }

        private void clampCurrentPage() {
            if (allRows.isEmpty()) {
                currentPage = 0;
                return;
            }
            int maxPage = (allRows.size() - 1) / MANAGE_REQUEST_PAGE_SIZE;
            if (currentPage > maxPage) {
                currentPage = maxPage;
            }
        }

        private int toDataIndex(int viewRow) {
            return currentPage * MANAGE_REQUEST_PAGE_SIZE + viewRow;
        }

        @Override
        public int getRowCount() {
            int remaining = allRows.size() - (currentPage * MANAGE_REQUEST_PAGE_SIZE);
            return Math.min(MANAGE_REQUEST_PAGE_SIZE, Math.max(remaining, 0));
        }

        @Override
        public Object getValueAt(int row, int column) {
            return allRows.get(toDataIndex(row))[column];
        }

        @Override
        public void setValueAt(Object value, int row, int column) {
            int dataIndex = toDataIndex(row);
            allRows.get(dataIndex)[column] = value;
            fireTableCellUpdated(row, column);
            if (column == MANAGE_REQUEST_COL_STATUS) {
                fireTableCellUpdated(row, MANAGE_REQUEST_COL_ASSIGNED_STAFF);
            }
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return statusColumnEditable && column == MANAGE_REQUEST_COL_STATUS;
        }

        public void replaceRows(Object[][] data) {
            allRows.clear();
            for (Object[] row : data) {
                if (isActiveRequestStatus(row[MANAGE_REQUEST_COL_STATUS])) {
                    allRows.add(row.clone());
                }
            }
            currentPage = 0;
            fireTableDataChanged();
        }

        public List<Object[]> getAllRows() {
            List<Object[]> copy = new java.util.ArrayList<>();
            for (Object[] row : allRows) {
                copy.add(row.clone());
            }
            return copy;
        }
    }

    public static void applyManagerManageRequestTableRenderers(
            JTable table, java.util.function.BooleanSupplier statusEditMode) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setFont(AppFonts.body());
        centerRenderer.setForeground(AppColors.LABEL);
        centerRenderer.setBackground(AppColors.SURFACE);

        for (int column = 0; column < MANAGE_REQUEST_COL_ASSIGNED_STAFF; column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(MANAGE_REQUEST_COL_DATE_RAISED).setCellRenderer(centerRenderer);

        table.getColumnModel().getColumn(MANAGE_REQUEST_COL_ASSIGNED_STAFF).setCellRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            Object status = tbl.getValueAt(row, MANAGE_REQUEST_COL_STATUS);
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
            wrapper.setBackground(AppColors.SURFACE);

            if (isSubmittedStatus(status)) {
                wrapper.add(new NotAssignedBadgeLabel());
            } else {
                JLabel staffLabel = new JLabel(String.valueOf(value), JLabel.CENTER);
                staffLabel.setFont(AppFonts.bodyBold());
                staffLabel.setForeground(AppColors.LABEL);
                wrapper.add(staffLabel);
            }
            return wrapper;
        });

        table.getColumnModel().getColumn(MANAGE_REQUEST_COL_STATUS).setCellRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 6));
            wrapper.setBackground(AppColors.SURFACE);

            if (statusEditMode.getAsBoolean()) {
                wrapper.add(new StatusDropdownPanel(String.valueOf(value)));
            } else {
                wrapper.add(new StatusBadgeLabel(String.valueOf(value)));
            }
            return wrapper;
        });
    }

    public static TableCellEditor createManagerManageRequestStatusEditor(JTable table) {
        return new DefaultCellEditor(new StatusBadgeComboBox()) {
            @Override
            public Component getTableCellEditorComponent(
                    JTable tbl, Object value, boolean isSelected, int row, int column) {
                StatusBadgeComboBox combo = (StatusBadgeComboBox) getComponent();
                combo.setSelectedStatus(String.valueOf(value));
                return combo;
            }

            @Override
            public Object getCellEditorValue() {
                return ((StatusBadgeComboBox) getComponent()).getSelectedItem();
            }
        };
    }

    public static void sizeManagerManageRequestTable(JTable table, JScrollPane scroll) {
        sizeManagerPaginatedTable(table, scroll);
    }

    private static void sizeManagerPaginatedTable(JTable table, JScrollPane scroll) {
        int headerHeight = table.getTableHeader().getPreferredSize().height;
        int bodyHeight = table.getRowHeight() * Math.max(table.getRowCount(), 0);
        int height = headerHeight + bodyHeight + 2;
        Dimension size = new Dimension(scroll.getPreferredSize().width, height);
        scroll.setPreferredSize(size);
        scroll.setMinimumSize(size);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
    }

    public static void styleManagerPaginationButton(JButton button) {
        button.setFont(AppFonts.bodyBold());
        button.setForeground(AppColors.LABEL);
        button.setBackground(AppColors.SURFACE);
        button.setBorder(new CompoundBorder(
                new LineBorder(AppColors.BORDER, 1, true),
                new EmptyBorder(4, 10, 4, 10)));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Dimension size = new Dimension(36, 36);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
    }

    public static void layoutManagerTableSectionWithPagination(
            JPanel tableSection,
            JLabel sectionTitle,
            JScrollPane scroll,
            JLabel lblShowing,
            JButton btnPrevious,
            JButton btnNext) {

        tableSection.removeAll();
        tableSection.setLayout(new BorderLayout());
        tableSection.add(sectionTitle, BorderLayout.NORTH);
        tableSection.add(scroll, BorderLayout.CENTER);

        lblShowing.setFont(AppFonts.body());
        lblShowing.setForeground(AppColors.LABEL);
        lblShowing.setBorder(new EmptyBorder(0, 14, 0, 0));

        styleManagerPaginationButton(btnPrevious);
        styleManagerPaginationButton(btnNext);

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        nav.setOpaque(false);
        nav.add(btnPrevious);
        nav.add(btnNext);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(true);
        footer.setBackground(AppColors.SURFACE);
        footer.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 1, 1, AppColors.GRID_LINE),
                new EmptyBorder(12, 0, 12, 14)));
        footer.add(lblShowing, BorderLayout.WEST);
        footer.add(nav, BorderLayout.EAST);

        tableSection.add(footer, BorderLayout.SOUTH);
    }

    public static void updateManagerPaginationFooter(
            JLabel lblShowing, JButton btnPrevious, JButton btnNext,
            ManagerManageRequestTableModel model) {

        setManagerPaginationFooter(lblShowing, btnPrevious, btnNext,
                model.getShowingFrom(), model.getShowingTo(), model.getTotalRowCount(),
                model.canGoPrevious(), model.canGoNext());
    }

    public static void layoutManagerAssignStaffToolbar(
            JPanel toolbar, JPanel searchField, JButton btnAction) {

        toolbar.removeAll();
        toolbar.setLayout(new javax.swing.BoxLayout(toolbar, javax.swing.BoxLayout.Y_AXIS));
        toolbar.setOpaque(false);

        JPanel topRow = new JPanel(new BorderLayout(12, 0));
        topRow.setOpaque(false);
        topRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        topRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, MANAGER_SEARCH_HEIGHT));
        topRow.add(searchField, BorderLayout.CENTER);

        JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomRow.setOpaque(false);
        bottomRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomRow.add(btnAction);

        toolbar.add(topRow);
        toolbar.add(javax.swing.Box.createVerticalStrut(10));
        toolbar.add(bottomRow);
    }

    public static final int ASSIGN_STAFF_COL_STATUS = 3;
    public static final int ASSIGN_STAFF_COL_ASSIGNED_STAFF = 4;

    public static ManagerAssignStaffTableModel createManagerAssignStaffTableModel() {
        return createManagerAssignStaffTableModel(new Object[0][5]);
    }

    public static ManagerAssignStaffTableModel createManagerAssignStaffTableModel(Object[][] data) {
        return new ManagerAssignStaffTableModel(data, new String[]{
            "Request ID", "Request Type", "Student Name", "Status", "Assigned Staff"});
    }

    private static Object[][] buildAssignStaffSampleData() {
        String[] types = {"Electrical", "Plumbing", "Furniture"};
        String[] students = {"Alex Johnson", "Maria Chen", "Sam Patel"};
        String[] staff = {"John Doe", "Jane Smith"};

        Object[][] rows = new Object[21][5];
        for (int i = 0; i < rows.length; i++) {
            String status = (i % 3 == 1) ? "SUBMITTED" : "IN PROGRESS";
            Object assignedStaff = "SUBMITTED".equals(status) ? null : staff[i % staff.length];
            rows[i] = new Object[]{
                String.format("REQ%03d", i + 1),
                types[i % types.length],
                students[i % students.length],
                status,
                assignedStaff
            };
        }
        return rows;
    }

    private static boolean isAssignableRequestStatus(Object status) {
        return "SUBMITTED".equals(StatusBadgeLabel.formatStatus(String.valueOf(status)));
    }

    public static final class ManagerAssignStaffTableModel extends AbstractTableModel {

        private final java.util.List<Object[]> allRows = new java.util.ArrayList<>();
        private final String[] columnNames;
        private int currentPage;
        private boolean staffColumnEditable;

        public ManagerAssignStaffTableModel(Object[][] data, Object[] columns) {
            this.columnNames = new String[columns.length];
            for (int i = 0; i < columns.length; i++) {
                this.columnNames[i] = String.valueOf(columns[i]);
            }
            for (Object[] row : data) {
                if (isAssignableRequestStatus(row[ASSIGN_STAFF_COL_STATUS])) {
                    allRows.add(row.clone());
                }
            }
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        public void setStaffColumnEditable(boolean editable) {
            this.staffColumnEditable = editable;
        }

        public int getTotalRowCount() {
            return allRows.size();
        }

        public int getPageSize() {
            return MANAGE_REQUEST_PAGE_SIZE;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getShowingFrom() {
            return currentPage * MANAGE_REQUEST_PAGE_SIZE + 1;
        }

        public int getShowingTo() {
            return Math.min((currentPage + 1) * MANAGE_REQUEST_PAGE_SIZE, allRows.size());
        }

        public boolean canGoPrevious() {
            return currentPage > 0;
        }

        public boolean canGoNext() {
            return (currentPage + 1) * MANAGE_REQUEST_PAGE_SIZE < allRows.size();
        }

        public void previousPage() {
            if (!canGoPrevious()) {
                return;
            }
            currentPage--;
            fireTableDataChanged();
        }

        public void nextPage() {
            if (!canGoNext()) {
                return;
            }
            currentPage++;
            fireTableDataChanged();
        }

        private int toDataIndex(int viewRow) {
            return currentPage * MANAGE_REQUEST_PAGE_SIZE + viewRow;
        }

        @Override
        public int getRowCount() {
            int remaining = allRows.size() - (currentPage * MANAGE_REQUEST_PAGE_SIZE);
            return Math.min(MANAGE_REQUEST_PAGE_SIZE, Math.max(remaining, 0));
        }

        @Override
        public Object getValueAt(int row, int column) {
            return allRows.get(toDataIndex(row))[column];
        }

        @Override
        public void setValueAt(Object value, int row, int column) {
            if (column == ASSIGN_STAFF_COL_ASSIGNED_STAFF && value != null) {
                value = StaffAssignmentComboBox.displayName(String.valueOf(value));
            }
            allRows.get(toDataIndex(row))[column] = value;
            fireTableCellUpdated(row, column);
            if (column == ASSIGN_STAFF_COL_ASSIGNED_STAFF) {
                fireTableCellUpdated(row, ASSIGN_STAFF_COL_STATUS);
            }
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return staffColumnEditable && column == ASSIGN_STAFF_COL_ASSIGNED_STAFF;
        }

        public void replaceRows(Object[][] data) {
            allRows.clear();
            for (Object[] row : data) {
                if (isAssignableRequestStatus(row[ASSIGN_STAFF_COL_STATUS])) {
                    allRows.add(row.clone());
                }
            }
            currentPage = 0;
            fireTableDataChanged();
        }

        public List<Object[]> getAllRows() {
            List<Object[]> copy = new java.util.ArrayList<>();
            for (Object[] row : allRows) {
                copy.add(row.clone());
            }
            return copy;
        }
    }

    public static void applyManagerAssignStaffTableRenderers(
            JTable table, java.util.function.BooleanSupplier staffEditMode) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setFont(AppFonts.body());
        centerRenderer.setForeground(AppColors.LABEL);
        centerRenderer.setBackground(AppColors.SURFACE);

        for (int column = 0; column < ASSIGN_STAFF_COL_STATUS; column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(ASSIGN_STAFF_COL_STATUS).setCellRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
            wrapper.setBackground(AppColors.SURFACE);
            wrapper.add(new StatusBadgeLabel(String.valueOf(value)));
            return wrapper;
        });

        table.getColumnModel().getColumn(ASSIGN_STAFF_COL_ASSIGNED_STAFF).setCellRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            boolean notAssigned = value == null || String.valueOf(value).isBlank();
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
            wrapper.setBackground(AppColors.SURFACE);

            if (staffEditMode.getAsBoolean()) {
                String staffName = value == null ? "" : StaffAssignmentComboBox.displayName(String.valueOf(value));
                wrapper.add(new StaffDropdownPanel(staffName, notAssigned));
            } else if (notAssigned) {
                wrapper.add(new NotAssignedBadgeLabel());
            } else {
                String staffName = StaffAssignmentComboBox.displayName(String.valueOf(value));
                JLabel staffLabel = new JLabel(staffName, JLabel.CENTER);
                staffLabel.setFont(AppFonts.bodyBold());
                staffLabel.setForeground(AppColors.LABEL);
                wrapper.add(staffLabel);
            }
            return wrapper;
        });
    }

    public static TableCellEditor createManagerAssignStaffEditor(JTable table, String[] staffOptions) {
        return new DefaultCellEditor(new StaffAssignmentComboBox(staffOptions)) {
            @Override
            public Component getTableCellEditorComponent(
                    JTable tbl, Object value, boolean isSelected, int row, int column) {
                StaffAssignmentComboBox combo = (StaffAssignmentComboBox) getComponent();
                combo.setSelectedStaff(value == null ? null : String.valueOf(value));
                return combo;
            }

            @Override
            public Object getCellEditorValue() {
                return ((StaffAssignmentComboBox) getComponent()).getSelectedStaffName();
            }
        };
    }

    public static void sizeManagerAssignStaffTable(JTable table, JScrollPane scroll) {
        sizeManagerManageRequestTable(table, scroll);
    }

    public static void updateManagerPaginationFooter(
            JLabel lblShowing, JButton btnPrevious, JButton btnNext,
            ManagerAssignStaffTableModel model) {

        setManagerPaginationFooter(lblShowing, btnPrevious, btnNext,
                model.getShowingFrom(), model.getShowingTo(), model.getTotalRowCount(),
                model.canGoPrevious(), model.canGoNext());
    }

    private static void setManagerPaginationFooter(
            JLabel lblShowing, JButton btnPrevious, JButton btnNext,
            int from, int to, int total, boolean canPrevious, boolean canNext) {

        if (lblShowing == null || btnPrevious == null || btnNext == null) {
            return;
        }
        lblShowing.setText(String.format("Showing %d to %d of %d results", from, to, total));
        btnPrevious.setEnabled(canPrevious);
        btnNext.setEnabled(canNext);
    }

    public static void layoutManagerViewRoomToolbar(JPanel toolbar, JPanel searchField) {
        toolbar.removeAll();
        toolbar.setLayout(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.add(searchField, BorderLayout.CENTER);

        Dimension size = new Dimension(Integer.MAX_VALUE, MANAGER_SEARCH_HEIGHT);
        toolbar.setPreferredSize(new Dimension(0, MANAGER_SEARCH_HEIGHT));
        toolbar.setMinimumSize(new Dimension(0, MANAGER_SEARCH_HEIGHT));
        toolbar.setMaximumSize(size);
    }

    public static JPanel createManagerViewRoomHint() {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icon = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.MUTED);
                g2.drawOval(2, 2, getWidth() - 4, getHeight() - 4);
                g2.setFont(AppFonts.body().deriveFont(10f));
                int x = (getWidth() - g2.getFontMetrics().stringWidth("i")) / 2;
                int y = ((getHeight() - g2.getFontMetrics().getHeight()) / 2) + g2.getFontMetrics().getAscent();
                g2.drawString("i", x, y);
                g2.dispose();
            }
        };
        icon.setPreferredSize(new Dimension(16, 16));

        JLabel text = new JLabel(
                "Enter a valid alphanumeric Request ID to fetch associated room and maintenance details.");
        text.setFont(AppFonts.body());
        text.setForeground(AppColors.MUTED);

        row.add(icon, BorderLayout.WEST);
        row.add(text, BorderLayout.CENTER);
        return row;
    }

    public static void layoutManagerViewRoomContent(
            JPanel pnlMain, JPanel toolbar, JPanel hint, ManagerRoomInformationPanel roomCard) {

        pnlMain.removeAll();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.setOpaque(true);
        pnlMain.setBackground(AppColors.BACKGROUND);
        pnlMain.setBorder(new EmptyBorder(28, 32, 32, 32));

        JPanel searchSection = new JPanel();
        searchSection.setLayout(new javax.swing.BoxLayout(searchSection, javax.swing.BoxLayout.Y_AXIS));
        searchSection.setOpaque(false);

        constrainManagerViewRoomWidth(toolbar, MANAGER_SEARCH_HEIGHT);
        constrainManagerViewRoomWidth(hint);

        searchSection.add(toolbar);
        searchSection.add(javax.swing.Box.createVerticalStrut(10));
        searchSection.add(hint);

        JPanel resultsSection = new JPanel(new BorderLayout());
        resultsSection.setOpaque(true);
        resultsSection.setBackground(AppColors.BACKGROUND);
        resultsSection.setBorder(new EmptyBorder(24, 0, 0, 0));
        resultsSection.add(roomCard, BorderLayout.NORTH);

        pnlMain.add(searchSection, BorderLayout.NORTH);
        pnlMain.add(resultsSection, BorderLayout.CENTER);
    }

    private static void constrainManagerViewRoomWidth(JComponent component, int height) {
        Dimension size = new Dimension(Integer.MAX_VALUE, height);
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        component.setMaximumSize(size);
    }

    private static void constrainManagerViewRoomWidth(JComponent component) {
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    public static final class ManagerRoomDetails {

        public final String requestId;
        public final String roomNumber;
        public final String block;
        public final String studentName;
        public final String requestType;

        public ManagerRoomDetails(String requestId, String roomNumber, String block,
                String studentName, String requestType) {
            this.requestId = requestId;
            this.roomNumber = roomNumber;
            this.block = block;
            this.studentName = studentName;
            this.requestType = requestType;
        }
    }

    public static ManagerRoomDetails lookupManagerRoomDetails(String requestId) {
        if (requestId == null || requestId.isBlank()) {
            return null;
        }
        return MANAGER_ROOM_DETAILS.get(requestId.trim().toUpperCase());
    }

    private static final java.util.Map<String, ManagerRoomDetails> MANAGER_ROOM_DETAILS =
            buildManagerRoomDetailsLookup();

    private static java.util.Map<String, ManagerRoomDetails> buildManagerRoomDetailsLookup() {
        String[] types = {"Electrical", "Plumbing", "Furniture"};
        String[] students = {"Alex Johnson", "Maria Chen", "Sam Patel"};
        String[] roomNumbers = {"402-B", "305-A", "118-C"};
        String[] blocks = {"Tower B, DHUAM", "Tower A, DHUAM", "Tower C, DHUAM"};

        java.util.Map<String, ManagerRoomDetails> lookup = new java.util.HashMap<>();
        for (int i = 0; i < 21; i++) {
            int index = i % 3;
            String requestId = String.format("REQ%03d", i + 1);
            lookup.put(requestId, new ManagerRoomDetails(
                    requestId,
                    roomNumbers[index],
                    blocks[index],
                    students[index],
                    types[index]));
        }
        return lookup;
    }

    public static void layoutManagerViewHistoryToolbar(
            JPanel toolbar, JPanel searchField, JButton btnFilter) {

        toolbar.removeAll();
        toolbar.setLayout(new BorderLayout(12, 0));
        toolbar.setOpaque(false);
        toolbar.add(searchField, BorderLayout.CENTER);
        toolbar.add(btnFilter, BorderLayout.EAST);

        Dimension size = new Dimension(Integer.MAX_VALUE, MANAGER_SEARCH_HEIGHT);
        toolbar.setPreferredSize(new Dimension(0, MANAGER_SEARCH_HEIGHT));
        toolbar.setMinimumSize(new Dimension(0, MANAGER_SEARCH_HEIGHT));
        toolbar.setMaximumSize(size);
    }

    public static final int VIEW_HISTORY_PAGE_SIZE = 7;
    public static final int VIEW_HISTORY_COL_ASSIGNED_STAFF = 3;
    public static final int VIEW_HISTORY_COL_DESCRIPTION = 4;
    public static final int VIEW_HISTORY_COL_PRIORITY = 5;
    public static final int VIEW_HISTORY_COL_STATUS = 6;

    public static ManagerViewHistoryTableModel createManagerViewHistoryTableModel() {
        return createManagerViewHistoryTableModel(new Object[0][7]);
    }

    public static ManagerViewHistoryTableModel createManagerViewHistoryTableModel(Object[][] data) {
        return new ManagerViewHistoryTableModel(data, new String[]{
            "Request ID", "Request Type", "Student Name",
            "Assigned Staff", "Description", "Priority", "Status"});
    }

    private static Object[][] buildViewHistorySampleData() {
        Object[][] rows = new Object[21][7];
        rows[0] = new Object[]{"REQ001", "Electrical", "Alex Johnson", "John Doe", "8 June 2026", "High", "IN PROGRESS"};
        rows[1] = new Object[]{"REQ001", "Plumbing", "Alex Johnson", "John Doe", "8 June 2026", "Medium", "SUBMITTED"};
        rows[2] = new Object[]{"REQ001", "Furniture", "Alex Johnson", "John Doe", "8 June 2026", "Low", "COMPLETED"};

        String[] types = {"Electrical", "Plumbing", "Furniture"};
        String[] students = {"Alex Johnson", "Maria Chen", "Sam Patel"};
        String[] staff = {"John Doe", "Jane Smith"};
        String[] dates = {"8 June 2026", "9 June 2026", "10 June 2026", "11 June 2026",
            "12 June 2026", "13 June 2026", "14 June 2026"};
        String[] priorities = {"High", "Medium", "Low"};
        String[] statuses = {"IN PROGRESS", "SUBMITTED", "COMPLETED"};

        for (int i = 3; i < rows.length; i++) {
            rows[i] = new Object[]{
                String.format("REQ%03d", i + 1),
                types[i % types.length],
                students[i % students.length],
                staff[i % staff.length],
                dates[i % dates.length],
                priorities[i % priorities.length],
                statuses[i % statuses.length]
            };
        }
        return rows;
    }

    public static final class ManagerViewHistoryTableModel extends AbstractTableModel {

        private final java.util.List<Object[]> allRows = new java.util.ArrayList<>();
        private final String[] columnNames;
        private int currentPage;

        public ManagerViewHistoryTableModel(Object[][] data, Object[] columns) {
            this.columnNames = new String[columns.length];
            for (int i = 0; i < columns.length; i++) {
                this.columnNames[i] = String.valueOf(columns[i]);
            }
            for (Object[] row : data) {
                allRows.add(row.clone());
            }
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        public int getTotalRowCount() {
            return allRows.size();
        }

        public int getPageSize() {
            return VIEW_HISTORY_PAGE_SIZE;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getShowingFrom() {
            if (allRows.isEmpty()) {
                return 0;
            }
            return currentPage * VIEW_HISTORY_PAGE_SIZE + 1;
        }

        public int getShowingTo() {
            return Math.min((currentPage + 1) * VIEW_HISTORY_PAGE_SIZE, allRows.size());
        }

        public boolean canGoPrevious() {
            return currentPage > 0;
        }

        public boolean canGoNext() {
            return (currentPage + 1) * VIEW_HISTORY_PAGE_SIZE < allRows.size();
        }

        public void previousPage() {
            if (!canGoPrevious()) {
                return;
            }
            currentPage--;
            fireTableDataChanged();
        }

        public void nextPage() {
            if (!canGoNext()) {
                return;
            }
            currentPage++;
            fireTableDataChanged();
        }

        private int toDataIndex(int viewRow) {
            return currentPage * VIEW_HISTORY_PAGE_SIZE + viewRow;
        }

        @Override
        public int getRowCount() {
            int remaining = allRows.size() - (currentPage * VIEW_HISTORY_PAGE_SIZE);
            return Math.min(VIEW_HISTORY_PAGE_SIZE, Math.max(remaining, 0));
        }

        @Override
        public Object getValueAt(int row, int column) {
            return allRows.get(toDataIndex(row))[column];
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public void replaceRows(Object[][] data) {
            allRows.clear();
            for (Object[] row : data) {
                allRows.add(row.clone());
            }
            currentPage = 0;
            fireTableDataChanged();
        }
    }

    public static void applyManagerViewHistoryTableRenderers(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setFont(AppFonts.body());
        centerRenderer.setForeground(AppColors.LABEL);
        centerRenderer.setBackground(AppColors.SURFACE);

        for (int column = 0; column < VIEW_HISTORY_COL_PRIORITY; column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(VIEW_HISTORY_COL_PRIORITY).setCellRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
            wrapper.setBackground(AppColors.SURFACE);
            wrapper.add(new PriorityBadgeLabel(String.valueOf(value)));
            return wrapper;
        });

        table.getColumnModel().getColumn(VIEW_HISTORY_COL_STATUS).setCellRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
            wrapper.setBackground(AppColors.SURFACE);
            wrapper.add(new StatusBadgeLabel(String.valueOf(value)));
            return wrapper;
        });
    }

    public static void sizeManagerViewHistoryTable(JTable table, JScrollPane scroll) {
        sizeManagerPaginatedTable(table, scroll);
    }

    public static void updateManagerPaginationFooter(
            JLabel lblShowing, JButton btnPrevious, JButton btnNext,
            ManagerViewHistoryTableModel model) {

        setManagerPaginationFooter(lblShowing, btnPrevious, btnNext,
                model.getShowingFrom(), model.getShowingTo(), model.getTotalRowCount(),
                model.canGoPrevious(), model.canGoNext());
    }

    public static final int STUDENT_SIDEBAR_WIDTH = 285;
    public static final int STUDENT_TABLE_PAGE_SIZE = VIEW_HISTORY_PAGE_SIZE;
    public static final int STUDENT_PROFILE_LABEL_COL_WIDTH = 140;
    public static final int STUDENT_ACTIVE_COL_STATUS = 3;
    public static final int STUDENT_HISTORY_COL_PRIORITY = 3;
    public static final int STUDENT_HISTORY_COL_STATUS = 4;

    public enum StudentNavPage {
        DASHBOARD, SUBMIT, HISTORY
    }

    public static void configureStudentShell(
            javax.swing.JFrame frame,
            JPanel pnlHeader,
            JPanel pnlSidebar,
            JPanel pnlMain,
            JPanel pnlPageHeader,
            JLabel lblAppTitle,
            JLabel lblPageTitle,
            LogoPanel pnlHeaderLogo,
            JPanel pnlUserProfile,
            JLabel lblUserName,
            JLabel lblUserRole,
            JButton btnUserMenu,
            ManagerNavButton btnNavDashboard,
            ManagerNavButton btnNavSubmit,
            ManagerNavButton btnNavHistory,
            StudentNavPage activePage) {

        styleManagerShell(pnlHeader, pnlSidebar, pnlMain, lblAppTitle, pnlHeaderLogo);
        styleManagerPageHeader(pnlPageHeader, lblPageTitle);
        installStudentSession(frame, pnlUserProfile, lblUserName, lblUserRole, btnUserMenu);
        pnlSidebar.setPreferredSize(new Dimension(STUDENT_SIDEBAR_WIDTH, 0));
        layoutStudentSidebar(pnlSidebar, btnNavDashboard, btnNavSubmit, btnNavHistory);
        styleManagerNavButton(btnNavDashboard, activePage == StudentNavPage.DASHBOARD);
        styleManagerNavButton(btnNavSubmit, activePage == StudentNavPage.SUBMIT);
        styleManagerNavButton(btnNavHistory, activePage == StudentNavPage.HISTORY);
    }

    public static void installStudentSession(
            javax.swing.JFrame frame,
            javax.swing.JPanel pnlUserProfile,
            javax.swing.JLabel lblUserName,
            javax.swing.JLabel lblUserRole,
            javax.swing.JButton btnUserMenu) {
        ManagerUserMenu.install(pnlUserProfile, lblUserName, lblUserRole, btnUserMenu,
                () -> {
                    SessionManager.clear();
                    navigateTo(frame, new LoginFrame());
                });
        if (SessionManager.isLoggedIn()) {
            lblUserName.setText(SessionManager.getFullName());
            lblUserRole.setText("STUDENT");
        } else {
            lblUserName.setText("Student");
            lblUserRole.setText("STUDENT");
        }
    }

    public static void layoutStudentSidebar(JPanel pnlSidebar, ManagerNavButton... buttons) {
        pnlSidebar.setPreferredSize(new Dimension(STUDENT_SIDEBAR_WIDTH, 0));
        layoutManagerSidebar(pnlSidebar, buttons);
    }

    public static void styleStudentMainContent(JPanel main) {
        main.setBackground(AppColors.BACKGROUND);
        main.setBorder(new EmptyBorder(
                MANAGER_MAIN_PADDING, MANAGER_MAIN_PADDING,
                MANAGER_MAIN_PADDING, MANAGER_MAIN_PADDING));
    }

    public static void styleStudentPageTitle(JLabel lblPageTitle) {
        lblPageTitle.setFont(AppFonts.pageTitle());
        lblPageTitle.setForeground(AppColors.LABEL);
    }

    public static void styleStudentFieldLabel(JLabel... labels) {
        for (JLabel label : labels) {
            label.setFont(AppFonts.label());
            label.setForeground(AppColors.LABEL);
        }
    }

    public static void styleStudentHintLabel(JLabel label) {
        label.setFont(AppFonts.body());
        label.setForeground(AppColors.MUTED);
    }

    public static void styleOutlineButton(JButton button, int width) {
        button.setFont(AppFonts.bodyBold());
        button.setForeground(AppColors.PRIMARY);
        button.setBackground(AppColors.SURFACE);
        button.setBorder(new CompoundBorder(
                new LineBorder(AppColors.PRIMARY, 1, true),
                new EmptyBorder(10, 20, 10, 20)));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        fixSize(button, width, BUTTON_HEIGHT);
    }

    public static void styleStudentFormCard(JPanel card) {
        card.setBackground(AppColors.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppColors.GRID_LINE, 1, true),
                new EmptyBorder(24, 32, 24, 32)));
    }

    @SuppressWarnings("unchecked")
    public static void styleStudentFormField(javax.swing.JTextField field, int width) {
        field.setFont(AppFonts.body());
        field.setBackground(AppColors.INPUT_FILL);
        field.setForeground(AppColors.LABEL);
        field.setBorder(inputBorder());
        fixSize((JComponent) field, width, FIELD_HEIGHT);
    }

    /** Form row field that grows horizontally; height matches other form inputs. */
    public static void styleStudentFormFieldFlexible(javax.swing.JTextField field) {
        field.setFont(AppFonts.body());
        field.setBackground(AppColors.INPUT_FILL);
        field.setForeground(AppColors.LABEL);
        field.setBorder(inputBorder());
        Dimension heightOnly = new Dimension(0, FIELD_HEIGHT);
        field.setPreferredSize(heightOnly);
        field.setMinimumSize(heightOnly);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, FIELD_HEIGHT));
    }

    @SuppressWarnings("unchecked")
    public static void styleStudentFormCombo(JComboBox<?> combo, int width) {
        combo.setFont(AppFonts.body());
        combo.setBackground(Color.WHITE);
        combo.setForeground(AppColors.LABEL);
        combo.setBorder(comboBorder());
        fixSize((JComponent) combo, width, FIELD_HEIGHT);
    }

    public static void styleStudentFormTextArea(javax.swing.JTextArea area, JScrollPane scroll, int width, int height) {
        area.setFont(AppFonts.body());
        area.setBackground(AppColors.INPUT_FILL);
        area.setForeground(AppColors.LABEL);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(10, 14, 10, 14));
        scroll.setBorder(inputBorder());
        Dimension size = new Dimension(width, height);
        scroll.setPreferredSize(size);
        scroll.setMinimumSize(size);
        scroll.setMaximumSize(new Dimension(width, height));
    }

    public static void setupStudentHeader(
            JPanel pnlHeader,
            JLabel lblAppTitle,
            JPanel pnlUserHost,
            LogoPanel pnlHeaderLogo) {
        pnlHeader.setLayout(new BorderLayout());
        pnlHeader.setPreferredSize(new Dimension(0, MANAGER_HEADER_HEIGHT));
        pnlHeader.removeAll();

        JPanel pnlHeaderLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 14));
        pnlHeaderLeft.setOpaque(false);
        pnlHeaderLeft.add(pnlHeaderLogo);
        pnlHeaderLeft.add(lblAppTitle);

        pnlHeader.add(pnlHeaderLeft, BorderLayout.WEST);
        pnlHeader.add(pnlUserHost, BorderLayout.EAST);
    }

    public static JPanel createStudentUserProfileHost() {
        JPanel host = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 12));
        host.setOpaque(false);
        host.setBorder(new EmptyBorder(0, 0, 0, 24));
        return host;
    }

    public static JPanel createStudentUserProfile(
            JLabel lblUserName, JLabel lblUserRole, JButton btnUserMenu) {
        JPanel pnlUserProfile = new JPanel(new BorderLayout(8, 0));
        JPanel pnlUserText = new JPanel();
        pnlUserText.setLayout(new javax.swing.BoxLayout(pnlUserText, javax.swing.BoxLayout.Y_AXIS));
        pnlUserText.setOpaque(false);
        pnlUserText.add(lblUserName);
        pnlUserText.add(lblUserRole);
        pnlUserProfile.add(pnlUserText, BorderLayout.CENTER);
        pnlUserProfile.add(btnUserMenu, BorderLayout.EAST);
        return pnlUserProfile;
    }

    public static void styleStudentProfileTable(JLabel sectionTitle, JTable table, JScrollPane scroll) {
        sectionTitle.setFont(AppFonts.tableSection());
        sectionTitle.setForeground(AppColors.LABEL);
        sectionTitle.setHorizontalAlignment(JLabel.LEFT);
        sectionTitle.setOpaque(true);
        sectionTitle.setBackground(AppColors.STAT_TOTAL_HEADER);
        sectionTitle.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(AppColors.GRID_LINE, 1),
                new EmptyBorder(10, 14, 10, 14)));

        table.setTableHeader(null);
        table.setFont(AppFonts.body());
        table.setRowHeight(44);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(false);
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        applyStudentProfileTableColumns(table);
        applyStudentProfileTableRenderers(table);

        scroll.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, AppColors.GRID_LINE));
        scroll.getViewport().setBackground(AppColors.SURFACE);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    }

    public static void layoutStudentProfileSection(JPanel section, JLabel title, JScrollPane scroll) {
        section.removeAll();
        section.setLayout(new BorderLayout());
        section.setOpaque(false);
        section.add(title, BorderLayout.NORTH);
        section.add(scroll, BorderLayout.CENTER);
    }

    private static void applyStudentProfileTableColumns(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table.getColumnModel().getColumn(0).setPreferredWidth(STUDENT_PROFILE_LABEL_COL_WIDTH);
        table.getColumnModel().getColumn(0).setMinWidth(STUDENT_PROFILE_LABEL_COL_WIDTH);
        table.getColumnModel().getColumn(0).setMaxWidth(STUDENT_PROFILE_LABEL_COL_WIDTH);
    }

    public static void setStudentProfileTableRows(JTable table, JScrollPane scroll, Object[][] rows) {
        table.setModel(createStudentProfileTableModel(rows));
        table.setTableHeader(null);
        scroll.setColumnHeaderView(null);
        applyStudentProfileTableColumns(table);
        applyStudentProfileTableRenderers(table);
    }

    private static void applyStudentProfileTableRenderers(JTable table) {
        table.getColumnModel().getColumn(0).setCellRenderer(createStudentProfileCellRenderer(true));
        table.getColumnModel().getColumn(1).setCellRenderer(createStudentProfileCellRenderer(false));
    }

    private static DefaultTableCellRenderer createStudentProfileCellRenderer(boolean labelColumn) {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        tbl, value, isSelected, hasFocus, row, column);
                label.setFont(labelColumn ? AppFonts.body() : AppFonts.bodyBold());
                label.setForeground(AppColors.LABEL);
                label.setBackground(labelColumn ? AppColors.STAT_TOTAL_HEADER : AppColors.SURFACE);
                label.setOpaque(true);
                label.setHorizontalAlignment(JLabel.LEFT);
                label.setVerticalAlignment(JLabel.CENTER);
                label.setBorder(createStudentProfileCellBorder(row, column, tbl.getColumnCount(), tbl.getRowCount()));
                return label;
            }
        };
    }

    private static Border createStudentProfileCellBorder(int row, int column, int columnCount, int rowCount) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 1, AppColors.GRID_LINE),
                new EmptyBorder(0, 12, 0, 12));
    }

    private static Border createStudentTableCellBorder(int row, int column, int columnCount, int rowCount) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 1, AppColors.GRID_LINE),
                new EmptyBorder(0, 12, 0, 12));
    }

    public static void sizeStudentProfileTable(JTable table, JScrollPane scroll) {
        int bodyHeight = table.getRowHeight() * Math.max(table.getRowCount(), 0);
        table.setPreferredScrollableViewportSize(new Dimension(0, bodyHeight));
        scroll.setPreferredSize(new Dimension(0, bodyHeight));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, bodyHeight));
    }

    private static void disableStudentTableGrid(JTable table) {
        table.setShowGrid(false);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
    }

    private static final class StudentBadgeTableCellRenderer extends JPanel implements javax.swing.table.TableCellRenderer {

        private final java.util.function.Function<String, JComponent> badgeFactory;

        private StudentBadgeTableCellRenderer(java.util.function.Function<String, JComponent> badgeFactory) {
            super(new FlowLayout(FlowLayout.CENTER, 0, 8));
            this.badgeFactory = badgeFactory;
            setOpaque(true);
            setBackground(AppColors.SURFACE);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            removeAll();
            add(badgeFactory.apply(String.valueOf(value)));
            setBorder(createStudentTableCellBorder(row, column, table.getColumnCount(), table.getRowCount()));
            return this;
        }
    }

    private static DefaultTableCellRenderer createStudentGridCellRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable tbl, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        tbl, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setFont(AppFonts.body());
                label.setForeground(AppColors.LABEL);
                label.setBackground(AppColors.SURFACE);
                label.setOpaque(true);
                label.setBorder(createStudentTableCellBorder(row, column, tbl.getColumnCount(), tbl.getRowCount()));
                return label;
            }
        };
    }

    public static DefaultTableModel createStudentProfileTableModel(Object[][] rows) {
        return new DefaultTableModel(rows, new String[]{"", ""}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public String getColumnName(int column) {
                return "";
            }
        };
    }

    public static void applyStudentActiveRequestTableRenderers(JTable table) {
        disableStudentTableGrid(table);
        DefaultTableCellRenderer centerRenderer = createStudentGridCellRenderer();

        for (int column = 0; column < STUDENT_ACTIVE_COL_STATUS; column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(STUDENT_ACTIVE_COL_STATUS).setCellRenderer(
                new StudentBadgeTableCellRenderer(StatusBadgeLabel::new));
    }

    public static void applyStudentHistoryTableRenderers(JTable table) {
        disableStudentTableGrid(table);
        DefaultTableCellRenderer centerRenderer = createStudentGridCellRenderer();

        for (int column = 0; column < STUDENT_HISTORY_COL_PRIORITY; column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(STUDENT_HISTORY_COL_PRIORITY).setCellRenderer(
                new StudentBadgeTableCellRenderer(PriorityBadgeLabel::new));

        table.getColumnModel().getColumn(STUDENT_HISTORY_COL_STATUS).setCellRenderer(
                new StudentBadgeTableCellRenderer(StatusBadgeLabel::new));
    }

    public static StudentActiveRequestTableModel createStudentActiveRequestTableModel() {
        return createStudentActiveRequestTableModel(new Object[0][4]);
    }

    public static StudentActiveRequestTableModel createStudentActiveRequestTableModel(Object[][] data) {
        return new StudentActiveRequestTableModel(data, new String[]{
            "Request ID", "Request Type", "Date Raised", "Status"});
    }

    public static StudentHistoryTableModel createStudentHistoryTableModel() {
        return createStudentHistoryTableModel(new Object[0][5]);
    }

    public static StudentHistoryTableModel createStudentHistoryTableModel(Object[][] data) {
        return new StudentHistoryTableModel(data, new String[]{
            "Request ID", "Request Type", "Date Raised", "Priority", "Status"});
    }

    public static void updateStudentPaginationFooter(
            JLabel lblShowing, JButton btnPrevious, JButton btnNext,
            StudentHistoryTableModel model) {
        updateStudentPaginationFooter(lblShowing, btnPrevious, btnNext,
                model.getShowingFrom(), model.getShowingTo(), model.getTotalRowCount(),
                model.canGoPrevious(), model.canGoNext());
    }

    public static void updateStudentPaginationFooter(
            JLabel lblShowing, JButton btnPrevious, JButton btnNext,
            StudentActiveRequestTableModel model) {
        updateStudentPaginationFooter(lblShowing, btnPrevious, btnNext,
                model.getShowingFrom(), model.getShowingTo(), model.getTotalRowCount(),
                model.canGoPrevious(), model.canGoNext());
    }

    private static void updateStudentPaginationFooter(
            JLabel lblShowing, JButton btnPrevious, JButton btnNext,
            int from, int to, int total, boolean canPrevious, boolean canNext) {
        if (total == 0) {
            lblShowing.setText("No results found");
        } else {
            lblShowing.setText(String.format("Showing %d to %d of %d results", from, to, total));
        }
        btnPrevious.setEnabled(canPrevious);
        btnNext.setEnabled(canNext);
    }

    public static void sizeStudentPaginatedTable(JTable table, JScrollPane scroll) {
        sizeManagerPaginatedTable(table, scroll);
    }

    public static void sizeStudentHistoryTable(JTable table, JScrollPane scroll) {
        sizeStudentPaginatedTable(table, scroll);
    }

    public static final class StudentActiveRequestTableModel extends AbstractTableModel {

        private final java.util.List<Object[]> allRows = new java.util.ArrayList<>();
        private final String[] columnNames;
        private int currentPage;

        public StudentActiveRequestTableModel(Object[][] data, Object[] columns) {
            this.columnNames = new String[columns.length];
            for (int i = 0; i < columns.length; i++) {
                this.columnNames[i] = String.valueOf(columns[i]);
            }
            replaceRows(data);
        }

        public void replaceRows(Object[][] data) {
            allRows.clear();
            currentPage = 0;
            if (data != null) {
                for (Object[] row : data) {
                    allRows.add(row.clone());
                }
            }
            fireTableDataChanged();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public int getRowCount() {
            if (allRows.isEmpty()) {
                return 0;
            }
            int start = currentPage * STUDENT_TABLE_PAGE_SIZE;
            if (start >= allRows.size()) {
                return 0;
            }
            return Math.min(STUDENT_TABLE_PAGE_SIZE, allRows.size() - start);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            int dataIndex = currentPage * STUDENT_TABLE_PAGE_SIZE + rowIndex;
            return allRows.get(dataIndex)[columnIndex];
        }

        public int getTotalRowCount() {
            return allRows.size();
        }

        public int getShowingFrom() {
            if (allRows.isEmpty()) {
                return 0;
            }
            return currentPage * STUDENT_TABLE_PAGE_SIZE + 1;
        }

        public int getShowingTo() {
            return Math.min((currentPage + 1) * STUDENT_TABLE_PAGE_SIZE, allRows.size());
        }

        public boolean canGoPrevious() {
            return currentPage > 0;
        }

        public boolean canGoNext() {
            return (currentPage + 1) * STUDENT_TABLE_PAGE_SIZE < allRows.size();
        }

        public void previousPage() {
            if (canGoPrevious()) {
                currentPage--;
                fireTableDataChanged();
            }
        }

        public void nextPage() {
            if (canGoNext()) {
                currentPage++;
                fireTableDataChanged();
            }
        }
    }

    public static final class StudentHistoryTableModel extends AbstractTableModel {

        private final java.util.List<Object[]> allRows = new java.util.ArrayList<>();
        private final String[] columnNames;
        private int currentPage;

        public StudentHistoryTableModel(Object[][] data, Object[] columns) {
            this.columnNames = new String[columns.length];
            for (int i = 0; i < columns.length; i++) {
                this.columnNames[i] = String.valueOf(columns[i]);
            }
            replaceRows(data);
        }

        public void replaceRows(Object[][] data) {
            allRows.clear();
            currentPage = 0;
            if (data != null) {
                for (Object[] row : data) {
                    allRows.add(row.clone());
                }
            }
            fireTableDataChanged();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public int getRowCount() {
            if (allRows.isEmpty()) {
                return 0;
            }
            int start = currentPage * STUDENT_TABLE_PAGE_SIZE;
            if (start >= allRows.size()) {
                return 0;
            }
            return Math.min(STUDENT_TABLE_PAGE_SIZE, allRows.size() - start);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            int dataIndex = currentPage * STUDENT_TABLE_PAGE_SIZE + rowIndex;
            return allRows.get(dataIndex)[columnIndex];
        }

        public int getTotalRowCount() {
            return allRows.size();
        }

        public int getShowingFrom() {
            if (allRows.isEmpty()) {
                return 0;
            }
            return currentPage * STUDENT_TABLE_PAGE_SIZE + 1;
        }

        public int getShowingTo() {
            return Math.min((currentPage + 1) * STUDENT_TABLE_PAGE_SIZE, allRows.size());
        }

        public boolean canGoPrevious() {
            return currentPage > 0;
        }

        public boolean canGoNext() {
            return (currentPage + 1) * STUDENT_TABLE_PAGE_SIZE < allRows.size();
        }

        public void previousPage() {
            if (canGoPrevious()) {
                currentPage--;
                fireTableDataChanged();
            }
        }

        public void nextPage() {
            if (canGoNext()) {
                currentPage++;
                fireTableDataChanged();
            }
        }

        public void resetPage() {
            currentPage = 0;
            fireTableDataChanged();
        }
    }

    public static final int STAFF_MANAGE_COL_STATUS = 3;
    public static final int STAFF_TABLE_PAGE_SIZE = STUDENT_TABLE_PAGE_SIZE;

    public enum StaffNavPage {
        DASHBOARD, MANAGE_REQUESTS, VIEW_ROOM, HISTORY
    }

    public static void configureStaffShell(
            javax.swing.JFrame frame,
            JPanel pnlHeader,
            JPanel pnlSidebar,
            JPanel pnlMain,
            JPanel pnlPageHeader,
            JLabel lblAppTitle,
            JLabel lblPageTitle,
            LogoPanel pnlHeaderLogo,
            JPanel pnlUserProfile,
            JLabel lblUserName,
            JLabel lblUserRole,
            JButton btnUserMenu,
            ManagerNavButton btnNavDashboard,
            ManagerNavButton btnNavManageRequests,
            ManagerNavButton btnNavViewRoom,
            ManagerNavButton btnNavHistory,
            StaffNavPage activePage) {

        styleManagerShell(pnlHeader, pnlSidebar, pnlMain, lblAppTitle, pnlHeaderLogo);
        styleManagerPageHeader(pnlPageHeader, lblPageTitle);
        installStaffSession(frame, pnlUserProfile, lblUserName, lblUserRole, btnUserMenu);
        pnlSidebar.setPreferredSize(new Dimension(STUDENT_SIDEBAR_WIDTH, 0));
        layoutStaffSidebar(pnlSidebar, btnNavDashboard, btnNavManageRequests, btnNavViewRoom, btnNavHistory);
        styleManagerNavButton(btnNavDashboard, activePage == StaffNavPage.DASHBOARD);
        styleManagerNavButton(btnNavManageRequests, activePage == StaffNavPage.MANAGE_REQUESTS);
        styleManagerNavButton(btnNavViewRoom, activePage == StaffNavPage.VIEW_ROOM);
        styleManagerNavButton(btnNavHistory, activePage == StaffNavPage.HISTORY);
    }

    public static void installStaffSession(
            javax.swing.JFrame frame,
            javax.swing.JPanel pnlUserProfile,
            javax.swing.JLabel lblUserName,
            javax.swing.JLabel lblUserRole,
            javax.swing.JButton btnUserMenu) {
        ManagerUserMenu.install(pnlUserProfile, lblUserName, lblUserRole, btnUserMenu,
                () -> {
                    SessionManager.clear();
                    navigateTo(frame, new LoginFrame());
                });
        if (SessionManager.isLoggedIn()) {
            lblUserName.setText(SessionManager.getFullName());
            lblUserRole.setText("STAFF");
        } else {
            lblUserName.setText("John Doe");
            lblUserRole.setText("STAFF");
        }
    }

    public static void layoutStaffSidebar(JPanel pnlSidebar, ManagerNavButton... buttons) {
        pnlSidebar.setPreferredSize(new Dimension(STUDENT_SIDEBAR_WIDTH, 0));
        layoutManagerSidebar(pnlSidebar, buttons);
    }

    public static Object[][] buildStaffProfileMockRows() {
        return new Object[][]{
            {"Name", "John Doe"},
            {"Staff ID", "ST12345"}
        };
    }

    public static Object[][] buildStaffDashboardActiveMockRows() {
        return new Object[][]{
            {"REQ001", "Electrical", "8 June 2026", "IN PROGRESS"},
            {"REQ002", "Electrical", "8 June 2026", "SUBMITTED"},
            {"REQ003", "Plumbing", "8 June 2026", "SUBMITTED"}
        };
    }

    public static Object[][] buildStaffManageRequestMockRows() {
        Object[][] rows = new Object[12][4];
        String[] types = {"Electrical", "Plumbing", "Furniture"};
        String[] statuses = {"IN PROGRESS", "SUBMITTED", "SUBMITTED"};
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Object[]{
                String.format("REQ%03d", i + 1),
                types[i % types.length],
                "8 June 2026",
                statuses[i % statuses.length]
            };
        }
        return rows;
    }

    public static Object[][] buildStaffHistoryMockRows() {
        Object[][] rows = new Object[12][5];
        String[] types = {"Electrical", "Plumbing", "Furniture"};
        String[] priorities = {"High", "Medium", "Low"};
        String[] statuses = {"IN PROGRESS", "SUBMITTED", "COMPLETED"};
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Object[]{
                String.format("REQ%03d", i + 1),
                types[i % types.length],
                "8 June 2026",
                priorities[i % priorities.length],
                statuses[i % statuses.length]
            };
        }
        return rows;
    }

    public static StaffManageRequestTableModel createStaffManageRequestTableModel() {
        return createStaffManageRequestTableModel(new Object[0][4]);
    }

    public static StaffManageRequestTableModel createStaffManageRequestTableModel(Object[][] data) {
        return new StaffManageRequestTableModel(data, new String[]{
            "Request ID", "Request Type", "Date Raised", "Status"});
    }

    public static void applyStaffManageRequestTableRenderers(
            JTable table, java.util.function.BooleanSupplier statusEditMode) {
        disableStudentTableGrid(table);
        DefaultTableCellRenderer centerRenderer = createStudentGridCellRenderer();

        for (int column = 0; column < STAFF_MANAGE_COL_STATUS; column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(centerRenderer);
        }

        table.getColumnModel().getColumn(STAFF_MANAGE_COL_STATUS).setCellRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
            wrapper.setOpaque(true);
            wrapper.setBackground(AppColors.SURFACE);
            if (statusEditMode.getAsBoolean()) {
                wrapper.add(new StatusDropdownPanel(String.valueOf(value)));
            } else {
                wrapper.add(new StatusBadgeLabel(String.valueOf(value)));
            }
            wrapper.setBorder(createStudentTableCellBorder(row, column, tbl.getColumnCount(), tbl.getRowCount()));
            return wrapper;
        });
    }

    public static TableCellEditor createStaffManageRequestStatusEditor(JTable table) {
        return createManagerManageRequestStatusEditor(table);
    }

    public static void sizeStaffManageRequestTable(JTable table, JScrollPane scroll) {
        sizeManagerPaginatedTable(table, scroll);
    }

    public static void updateStaffPaginationFooter(
            JLabel lblShowing, JButton btnPrevious, JButton btnNext,
            StaffManageRequestTableModel model) {
        updateStudentPaginationFooter(lblShowing, btnPrevious, btnNext,
                model.getShowingFrom(), model.getShowingTo(), model.getTotalRowCount(),
                model.canGoPrevious(), model.canGoNext());
    }

    public static final class StaffManageRequestTableModel extends AbstractTableModel {

        private final java.util.List<Object[]> allRows = new java.util.ArrayList<>();
        private final String[] columnNames;
        private int currentPage;
        private boolean statusColumnEditable;

        public StaffManageRequestTableModel(Object[][] data, Object[] columns) {
            this.columnNames = new String[columns.length];
            for (int i = 0; i < columns.length; i++) {
                this.columnNames[i] = String.valueOf(columns[i]);
            }
            for (Object[] row : data) {
                if (isStaffActiveStatus(row[STAFF_MANAGE_COL_STATUS])) {
                    allRows.add(row.clone());
                }
            }
        }

        private static boolean isStaffActiveStatus(Object status) {
            return "IN PROGRESS".equals(StatusBadgeLabel.formatStatus(String.valueOf(status)));
        }

        @Override
        public int getRowCount() {
            int start = currentPage * STAFF_TABLE_PAGE_SIZE;
            return Math.min(STAFF_TABLE_PAGE_SIZE, Math.max(allRows.size() - start, 0));
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int row, int column) {
            int index = currentPage * STAFF_TABLE_PAGE_SIZE + row;
            return index < allRows.size() ? allRows.get(index)[column] : null;
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return statusColumnEditable && column == STAFF_MANAGE_COL_STATUS;
        }

        @Override
        public void setValueAt(Object value, int row, int column) {
            if (!isCellEditable(row, column)) {
                return;
            }
            int index = currentPage * STAFF_TABLE_PAGE_SIZE + row;
            if (index >= allRows.size()) {
                return;
            }
            allRows.get(index)[column] = value;
            fireTableCellUpdated(row, column);
        }

        public void setStatusColumnEditable(boolean editable) {
            this.statusColumnEditable = editable;
        }

        public int getTotalRowCount() {
            return allRows.size();
        }

        public int getShowingFrom() {
            return allRows.isEmpty() ? 0 : currentPage * STAFF_TABLE_PAGE_SIZE + 1;
        }

        public int getShowingTo() {
            return Math.min((currentPage + 1) * STAFF_TABLE_PAGE_SIZE, allRows.size());
        }

        public boolean canGoPrevious() {
            return currentPage > 0;
        }

        public boolean canGoNext() {
            return (currentPage + 1) * STAFF_TABLE_PAGE_SIZE < allRows.size();
        }

        public void previousPage() {
            if (!canGoPrevious()) {
                return;
            }
            currentPage--;
            fireTableDataChanged();
        }

        public void nextPage() {
            if (!canGoNext()) {
                return;
            }
            currentPage++;
            fireTableDataChanged();
        }

        public void replaceRows(Object[][] rows) {
            allRows.clear();
            for (Object[] row : rows) {
                if (isStaffActiveStatus(row[STAFF_MANAGE_COL_STATUS])) {
                    allRows.add(row.clone());
                }
            }
            currentPage = 0;
            fireTableDataChanged();
        }

        public void resetPage() {
            currentPage = 0;
            fireTableDataChanged();
        }

        public java.util.List<Object[]> getAllRows() {
            java.util.List<Object[]> copy = new java.util.ArrayList<>();
            for (Object[] row : allRows) {
                copy.add(row.clone());
            }
            return copy;
        }
    }

    private static JPanel createRequirementItem(String text) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icon = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppColors.LABEL);
                g2.fillOval(2, 2, 18, 18);
                g2.setColor(Color.WHITE);
                g2.setStroke(new java.awt.BasicStroke(2f));
                g2.drawLine(6, 11, 9, 14);
                g2.drawLine(9, 14, 15, 7);
                g2.dispose();
            }
        };
        icon.setPreferredSize(new Dimension(22, 22));

        JLabel label = new JLabel(text);
        label.setFont(AppFonts.reqItem());
        label.setForeground(AppColors.LABEL);

        row.add(icon);
        row.add(label);
        return row;
    }
}
