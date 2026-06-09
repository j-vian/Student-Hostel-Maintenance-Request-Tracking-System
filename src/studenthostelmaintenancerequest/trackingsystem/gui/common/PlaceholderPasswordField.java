package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

public class PlaceholderPasswordField extends JPasswordField {

    private final String placeholder;
    private boolean showingPlaceholder;

    public PlaceholderPasswordField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(true);
        setBackground(AppColors.INPUT_FILL);
        setForeground(AppColors.PLACEHOLDER);
        setCaretColor(AppColors.LABEL);
        setFont(AppFonts.body());
        setBorder(UIHelper.inputBorder());
        setPreferredSize(new Dimension(10, 52));
        setMinimumSize(new Dimension(10, 52));
        setEchoChar((char) 0);

        ((AbstractDocument) getDocument()).setDocumentFilter(new PlaceholderDocumentFilter());
        showPlaceholder();

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onTextChanged();
            }
        });

        addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (showingPlaceholder) {
                    setText("");
                    setForeground(AppColors.LABEL);
                    setEchoChar('\u2022');
                    showingPlaceholder = false;
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (getPassword().length == 0) {
                    showPlaceholder();
                }
            }
        });
    }

    private void onTextChanged() {
        if (!showingPlaceholder && getPassword().length > 0) {
            setForeground(AppColors.LABEL);
        }
    }

    private void showPlaceholder() {
        showingPlaceholder = true;
        setEchoChar((char) 0);
        setForeground(AppColors.PLACEHOLDER);
        setText(placeholder);
    }

    public String getInputText() {
        return showingPlaceholder ? "" : new String(getPassword()).trim();
    }

    public void setPasswordVisible(boolean visible) {
        if (showingPlaceholder) {
            setEchoChar((char) 0);
            return;
        }
        setEchoChar(visible ? (char) 0 : '\u2022');
    }

    public void setInputFont(Font font) {
        setFont(font);
    }
}
