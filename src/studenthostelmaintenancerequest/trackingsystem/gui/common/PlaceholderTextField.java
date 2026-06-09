package studenthostelmaintenancerequest.trackingsystem.gui.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;

public class PlaceholderTextField extends JTextField {

    private final String placeholder;
    private boolean showingPlaceholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(true);
        setBackground(AppColors.INPUT_FILL);
        setForeground(AppColors.PLACEHOLDER);
        setCaretColor(AppColors.LABEL);
        setFont(AppFonts.body());
        setBorder(UIHelper.inputBorder());
        setPreferredSize(new Dimension(10, 52));
        setMinimumSize(new Dimension(10, 52));

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
                    showingPlaceholder = false;
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (getText().isEmpty()) {
                    showPlaceholder();
                }
            }
        });
    }

    private void onTextChanged() {
        if (!showingPlaceholder && !getText().isEmpty()) {
            setForeground(AppColors.LABEL);
        }
    }

    private void showPlaceholder() {
        showingPlaceholder = true;
        setForeground(AppColors.PLACEHOLDER);
        setText(placeholder);
    }

    public String getInputText() {
        return showingPlaceholder ? "" : getText().trim();
    }

    public void setInputFont(Font font) {
        setFont(font);
    }
}
