package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Font;

public final class AppFonts {

    public static final String FAMILY = "Segoe UI";

    public static final int HEADER_SIZE = 50;
    public static final int LABEL_SIZE = 25;
    public static final int BODY_SIZE = 25;
    public static final int LINK_SIZE = 20;
    public static final int REQ_TITLE_SIZE = 25;
    public static final int REQ_ITEM_SIZE = 27;

    private AppFonts() {
    }

    public static Font header() {
        return new Font(FAMILY, Font.BOLD, HEADER_SIZE);
    }

    public static Font label() {
        return new Font(FAMILY, Font.BOLD, LABEL_SIZE);
    }

    public static Font body() {
        return new Font(FAMILY, Font.PLAIN, BODY_SIZE);
    }

    public static Font bodyBold() {
        return new Font(FAMILY, Font.BOLD, BODY_SIZE);
    }

    public static Font link() {
        return new Font(FAMILY, Font.PLAIN, LINK_SIZE);
    }

    public static Font reqTitle() {
        return new Font(FAMILY, Font.PLAIN, REQ_TITLE_SIZE);
    }

    public static Font reqItem() {
        return new Font(FAMILY, Font.PLAIN, REQ_ITEM_SIZE);
    }
}
