package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

public class PlaceholderTextField extends JTextField {

    // instance fields for class state
    private final String placeholder;

    /** No-arg constructor required by the NetBeans Form Editor at design time. */
    // construct frame and initialize UI
    public PlaceholderTextField() {
        this("Placeholder");
    }

    // construct frame and initialize UI
    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(true);
        setBackground(AppColors.INPUT_FILL);
        setForeground(AppColors.LABEL);
        setCaretColor(AppColors.LABEL);
        setFont(AppFonts.body());
        setBorder(UIHelper.inputBorder());
        setPreferredSize(new Dimension(10, 40));
        setMinimumSize(new Dimension(10, 40));
        setText("");

        ((AbstractDocument) getDocument()).setDocumentFilter(new PlaceholderDocumentFilter());
    }

    @Override
    // process business logic
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(AppColors.PLACEHOLDER);
            g2.setFont(getFont());
            Insets insets = getInsets();
            FontMetrics fm = g2.getFontMetrics();
            int x = insets.left;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(placeholder, x, y);
            g2.dispose();
        }
    }

    public String getInputText() {
        return getText().trim();
    }

    public void setInputFont(Font font) {
        setFont(font);
    }
}
