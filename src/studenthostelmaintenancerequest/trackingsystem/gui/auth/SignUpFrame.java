/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem.gui.auth;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.CardPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.PlaceholderPasswordField;
import studenthostelmaintenancerequest.trackingsystem.gui.common.PlaceholderTextField;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper;

/**
 *
 * @author vian
 */
public class SignUpFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SignUpFrame.class.getName());

    private JPanel pnlStudentExtra;
    private JPanel pnlStaffExtra;
    private JComboBox<String> cmbRole;

    public SignUpFrame() {
        initComponents();
        buildUi();
    }

    private void buildUi() {
        UIHelper.setupAuthFrame(this, "Create Account", false);

        CardPanel card = new CardPanel(UIHelper.SIGNUP_CONTENT_WIDTH);
        JPanel body = card.getBody();

        JLabel lblTitle = UIHelper.createHeaderLabel("Create Account", UIHelper.SIGNUP_CONTENT_WIDTH);
        body.add(lblTitle);
        body.add(Box.createVerticalStrut(20));

        PlaceholderTextField txtFirstName = UIHelper.createSignupHalfTextField("First Name");
        PlaceholderTextField txtLastName = UIHelper.createSignupHalfTextField("Last Name");
        JPanel nameRow = UIHelper.createTwoColumnRow(
                UIHelper.createFieldGroup(UIHelper.createFieldLabel("First Name"), txtFirstName),
                UIHelper.createFieldGroup(UIHelper.createFieldLabel("Last Name"), txtLastName));
        body.add(nameRow);
        body.add(Box.createVerticalStrut(12));

        PlaceholderTextField txtUsername = UIHelper.createSignupTextField("Username");
        body.add(UIHelper.createFieldGroup(UIHelper.createFieldLabel("Username"), txtUsername));
        body.add(Box.createVerticalStrut(12));

        PlaceholderTextField txtUserId = UIHelper.createSignupTextField("User ID");
        body.add(UIHelper.createFieldGroup(UIHelper.createFieldLabel("User ID"), txtUserId));
        body.add(Box.createVerticalStrut(12));

        PlaceholderTextField txtEmail = UIHelper.createSignupTextField("Email");
        body.add(UIHelper.createFieldGroup(UIHelper.createFieldLabel("Email"), txtEmail));
        body.add(Box.createVerticalStrut(12));

        PlaceholderPasswordField txtPassword = UIHelper.createSignupHalfPasswordField("Password");
        PlaceholderPasswordField txtConfirmPassword = UIHelper.createSignupHalfPasswordField("Confirm Password");
        JPanel passwordRow = UIHelper.createTwoColumnRow(
                UIHelper.createFieldGroup(UIHelper.createFieldLabel("Password"), txtPassword),
                UIHelper.createFieldGroup(UIHelper.createFieldLabel("Confirm Password"), txtConfirmPassword));
        body.add(passwordRow);
        body.add(Box.createVerticalStrut(12));

        cmbRole = UIHelper.createComboBox(new String[]{"Select your role", "Student", "Staff"});
        body.add(UIHelper.createFieldGroup(UIHelper.createFieldLabel("Select Role"), cmbRole));
        body.add(Box.createVerticalStrut(12));

        pnlStudentExtra = UIHelper.createFieldGroup(
                UIHelper.createFieldLabel("Room Number"),
                UIHelper.createSignupTextField("Room Number"));
        pnlStudentExtra.setVisible(false);
        body.add(pnlStudentExtra);

        JComboBox<String> cmbExpertise = UIHelper.createComboBox(new String[]{
            "Select your expertise", "Electrician", "Plumber", "Furniture Tech", "Other"
        });
        pnlStaffExtra = UIHelper.createFieldGroup(
                UIHelper.createFieldLabel("Select Expertise"), cmbExpertise);
        pnlStaffExtra.setVisible(false);
        body.add(pnlStaffExtra);

        body.add(Box.createVerticalStrut(16));

        javax.swing.JButton btnSignUp = UIHelper.createSignupPrimaryButton("Sign Up");
        btnSignUp.addActionListener(e -> {
            // Backend sign-up logic will be added later.
        });
        body.add(btnSignUp);

        cmbRole.addActionListener(e -> updateRoleFields());

        UIHelper.mountScrollableCard(this, card);
        UIHelper.showFrame(this);
    }

    private void updateRoleFields() {
        String selected = (String) cmbRole.getSelectedItem();
        pnlStudentExtra.setVisible("Student".equals(selected));
        pnlStaffExtra.setVisible("Staff".equals(selected));
        revalidate();
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create Account");
        setResizable(true);

    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new SignUpFrame());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
