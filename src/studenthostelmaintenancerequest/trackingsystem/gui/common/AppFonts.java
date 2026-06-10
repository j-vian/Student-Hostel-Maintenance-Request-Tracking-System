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
    public static final int PAGE_TITLE_SIZE = 28;
    public static final int STAT_COUNT_SIZE = 40;
    public static final int STAT_TITLE_SIZE = 14;
    public static final int APP_BAR_TITLE_SIZE = 15;
    public static final int USER_NAME_SIZE = 14;
    public static final int USER_ROLE_SIZE = 12;
    public static final int TABLE_SECTION_SIZE = 16;
    public static final int STATUS_BADGE_SIZE = 12;

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

    public static Font pageTitle() {
        return FontLoader.derive(Font.BOLD, PAGE_TITLE_SIZE);
    }

    public static Font statCount() {
        return FontLoader.derive(Font.BOLD, STAT_COUNT_SIZE);
    }

    public static Font statTitle() {
        return FontLoader.derive(Font.BOLD, STAT_TITLE_SIZE);
    }

    public static Font appBarTitle() {
        return FontLoader.derive(Font.BOLD, APP_BAR_TITLE_SIZE);
    }

    public static Font userName() {
        return FontLoader.derive(Font.BOLD, USER_NAME_SIZE);
    }

    public static Font userRole() {
        return FontLoader.derive(Font.PLAIN, USER_ROLE_SIZE);
    }

    public static Font tableSection() {
        return FontLoader.derive(Font.BOLD, TABLE_SECTION_SIZE);
    }

    public static Font statusBadge() {
        return FontLoader.derive(Font.BOLD, STATUS_BADGE_SIZE);
    }
}
