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
    public static final Color SURFACE = Color.WHITE;
    public static final Color MUTED = color("707979");
    public static final Color SUCCESS = color("008545");
    public static final Color DANGER = color("B3261E");
    public static final Color WARNING = color("B35900");

    public static final Color STAT_TOTAL_HEADER = color("DADAD7");
    public static final Color STAT_TOTAL_BODY = color("F4F4F0");
    public static final Color STAT_ACTIVE_HEADER = color("0D5C63");
    public static final Color STAT_ACTIVE_BODY = color("A5E1E1");
    public static final Color STAT_COMPLETED_HEADER = color("008545");
    public static final Color STAT_COMPLETED_BODY = color("D1FADF");
    public static final Color STAT_CANCELLED_HEADER = color("B3261E");
    public static final Color STAT_CANCELLED_BODY = color("FDE2E1");

    public static final Color NAV_ACTIVE_BG = color("D1EAEB");
    public static final Color STATUS_SUBMITTED_BG = color("D1EAEB");
    public static final Color STATUS_IN_PROGRESS_BG = color("FFE7D1");
    public static final Color STATUS_COMPLETED_BG = color("D1FADF");

    private AppColors() {
    }

    private static Color color(String hex) {
        return new Color(Integer.parseInt(hex, 16));
    }
}
