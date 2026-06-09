package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JPasswordField;
import javax.swing.text.AbstractDocument;

public class PlaceholderPasswordField extends JPasswordField {

    private final String placeholder;
    private boolean passwordVisible;

    public PlaceholderPasswordField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(true);
        setBackground(AppColors.INPUT_FILL);
        setForeground(AppColors.LABEL);
        setCaretColor(AppColors.LABEL);
        setFont(AppFonts.body());
        setBorder(UIHelper.inputBorder());
        setPreferredSize(new Dimension(10, 40));
        setMinimumSize(new Dimension(10, 40));
        setEchoChar('\u2022');

        ((AbstractDocument) getDocument()).setDocumentFilter(new PlaceholderDocumentFilter());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getPassword().length == 0) {
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
        return new String(getPassword()).trim();
    }

    public void setPasswordVisible(boolean visible) {
        passwordVisible = visible;
        if (getPassword().length == 0) {
            setEchoChar((char) 0);
            return;
        }
        setEchoChar(visible ? (char) 0 : '\u2022');
    }

    public void setInputFont(Font font) {
        setFont(font);
    }
}
