package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Font;

public final class AppFonts {

    public static final int HEADER_SIZE = 28;
    public static final int LOGIN_HEADER_SIZE = 24;
    public static final int LABEL_SIZE = 14;
    public static final int BODY_SIZE = 14;
    public static final int LINK_SIZE = 13;
    public static final int REQ_TITLE_SIZE = 14;
    public static final int REQ_ITEM_SIZE = 15;

    private AppFonts() {
    }

    public static String family() {
        return FontLoader.family();
    }

    public static String cssFamily() {
        return FontLoader.cssFamily();
    }

    public static Font header() {
        return FontLoader.derive(Font.BOLD, HEADER_SIZE);
    }

    public static Font loginHeader() {
        return FontLoader.derive(Font.BOLD, LOGIN_HEADER_SIZE);
    }

    public static Font label() {
        return FontLoader.derive(Font.BOLD, LABEL_SIZE);
    }

    public static Font body() {
        return FontLoader.derive(Font.PLAIN, BODY_SIZE);
    }

    public static Font bodyBold() {
        return FontLoader.derive(Font.BOLD, BODY_SIZE);
    }

    public static Font link() {
        return FontLoader.derive(Font.PLAIN, LINK_SIZE);
    }

    public static Font reqTitle() {
        return FontLoader.derive(Font.PLAIN, REQ_TITLE_SIZE);
    }

    public static Font reqItem() {
        return FontLoader.derive(Font.PLAIN, REQ_ITEM_SIZE);
    }
}
