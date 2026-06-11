package studenthostelmaintenancerequest.trackingsystem.gui.manager;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import studenthostelmaintenancerequest.trackingsystem.gui.common.AppFonts;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper;

/**
 * Placeholder frame for the View Request History screen.
 * Replace this layout with the full manager shell when the page is implemented.
 */
public class RequestHistoryFrame extends javax.swing.JFrame {

    public RequestHistoryFrame() {
        initComponents();
        UIHelper.showManagerFrame(this);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("View Request History");

        JLabel lblPlaceholder = new JLabel("View Request History", SwingConstants.CENTER);
        lblPlaceholder.setFont(AppFonts.pageTitle());
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(lblPlaceholder, BorderLayout.CENTER);
    }
}
