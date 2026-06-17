package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

final class FontLoader {

    // logger for runtime diagnostics
    private static final Logger LOGGER = Logger.getLogger(FontLoader.class.getName());
    // constants used by this class
    private static final String FAMILY = "Hanken Grotesk";
    private static Font regular;
    private static Font bold;

    static {
        regular = loadFont("/resources/fonts/HankenGrotesk-Regular.ttf", Font.PLAIN);
        bold = loadFont("/resources/fonts/HankenGrotesk-Bold.ttf", Font.BOLD);

        if (regular == null) {
            regular = new Font(FAMILY, Font.PLAIN, 14);
        }
        if (bold == null) {
            bold = new Font(FAMILY, Font.BOLD, 14);
        }
    }

    // construct frame and initialize UI
    private FontLoader() {
    }

    private static Font loadFont(String resourcePath, int style) {
        try (InputStream stream = FontLoader.class.getResourceAsStream(resourcePath)) {
            if (stream == null) {
                return null;
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            return font.deriveFont(style, 14f);
        } catch (FontFormatException | IOException ex) {
            LOGGER.log(Level.WARNING, "Could not load font from " + resourcePath, ex);
            return null;
        }
    }

    static Font derive(int style, float size) {
        Font base = (style == Font.BOLD) ? bold : regular;
        return base.deriveFont(style, size);
    }

    static String cssFamily() {
        return "'Hanken Grotesk', sans-serif";
    }

    static String family() {
        return FAMILY;
    }
}
