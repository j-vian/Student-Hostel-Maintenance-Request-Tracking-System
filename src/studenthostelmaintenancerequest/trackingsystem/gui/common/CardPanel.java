package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CardPanel extends JPanel {

    private static final int SHADOW_OFFSET = 4;
    private static final int SHADOW_BLUR = 14;
    private static final int CORNER_RADIUS = 10;
    private static final int H_PAD = 40;
    private static final int V_PAD = 40;

    private final JPanel body;
    private final int contentWidth;

    public CardPanel(int contentWidth) {
        this.contentWidth = contentWidth;
        setOpaque(false);
        setBackground(AppColors.CARD);
        setBorder(new EmptyBorder(V_PAD + SHADOW_OFFSET, H_PAD + SHADOW_OFFSET, V_PAD + SHADOW_OFFSET, H_PAD + SHADOW_OFFSET));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new javax.swing.BoxLayout(body, javax.swing.BoxLayout.Y_AXIS));
        body.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        UIHelper.fixWidth(body, contentWidth);
        add(body);
    }

    public JPanel getBody() {
        return body;
    }

    public int getContentWidth() {
        return contentWidth;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension bodySize = body.getPreferredSize();
        Insets insets = getInsets();
        return new Dimension(
                contentWidth + insets.left + insets.right,
                bodySize.height + insets.top + insets.bottom);
    }

    @Override
    public Dimension getMaximumSize() {
        Dimension pref = getPreferredSize();
        return new Dimension(pref.width, Integer.MAX_VALUE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Insets insets = getInsets();
        int x = insets.left;
        int y = insets.top;
        int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;

        for (int i = SHADOW_BLUR; i >= 1; i--) {
            float alpha = 10f / 255f / (i * 0.55f + 1f);
            g2.setColor(new Color(0f, 0f, 0f, alpha));
            int spread = SHADOW_OFFSET + i / 2;
            g2.fillRoundRect(
                    x + SHADOW_OFFSET - spread / 2,
                    y + SHADOW_OFFSET - spread / 2,
                    w + spread,
                    h + spread,
                    CORNER_RADIUS + 2,
                    CORNER_RADIUS + 2);
        }

        g2.setColor(AppColors.CARD);
        g2.fillRoundRect(x, y, w, h, CORNER_RADIUS, CORNER_RADIUS);

        g2.dispose();
        super.paintComponent(g);
    }
}
