package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PlaceholderTextArea extends JTextArea {

    // instance fields for class state
    private final String placeholder;

    // construct frame and initialize UI
    public PlaceholderTextArea() {
        this("Placeholder");
    }

    // construct frame and initialize UI
    public PlaceholderTextArea(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(true);
        setBackground(AppColors.INPUT_FILL);
        setForeground(AppColors.LABEL);
        setCaretColor(AppColors.LABEL);
        setFont(AppFonts.body());
        setText("");

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                repaint();
            }
        });
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
            int y = insets.top + fm.getAscent();
            g2.drawString(placeholder, x, y);
            g2.dispose();
        }
    }

    public String getInputText() {
        return getText().trim();
    }
}
