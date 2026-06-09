package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Color;

public final class AppColors {

    public static final Color PRIMARY = color("0D5C63");
    public static final Color LABEL = color("1A1C1C");
    public static final Color PLACEHOLDER = color("707979");
    public static final Color BACKGROUND = color("F9F9F6");
    public static final Color INPUT_FILL = color("F9F9F6");
    public static final Color BORDER = color("DADAD7");
    public static final Color CARD = Color.WHITE;
    public static final Color BUTTON_TEXT = Color.WHITE;
    public static final Color REQUIREMENTS_FILL = color("F4F4F0");
    public static final Color GRADIENT_CYAN = color("06F5FF");
    public static final Color GRADIENT_LIGHT_CYAN = color("C2F2F4");
    public static final Color GRADIENT_EDGE = color("A8DFE3");
    public static final Color SHADOW = new Color(0, 0, 0, 64);
    public static final Color EYE_ICON = color("707979");

    private AppColors() {
    }

    private static Color color(String hex) {
        return new Color(Integer.parseInt(hex, 16));
    }
}
