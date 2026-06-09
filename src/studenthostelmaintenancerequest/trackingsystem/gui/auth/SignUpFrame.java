/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem.gui.auth;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
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
    private JComboBox<String> cmbExpertise;

    public SignUpFrame() {
        initComponents();
        buildUi();
    }

    private void buildUi() {
        UIHelper.setupAuthFrame(this, "Create Account", false);

        CardPanel card = new CardPanel();
        card.setLayout(new javax.swing.BoxLayout(card, javax.swing.BoxLayout.Y_AXIS));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = UIHelper.createHeaderLabel("Create Account");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(32));

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new javax.swing.BoxLayout(form, javax.swing.BoxLayout.Y_AXIS));
        form.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.setMaximumSize(new Dimension(UIHelper.SIGNUP_FIELD_WIDTH, 1200));

        PlaceholderTextField txtFirstName = UIHelper.createSignupHalfTextField("First Name");
        PlaceholderTextField txtLastName = UIHelper.createSignupHalfTextField("Last Name");
        JPanel nameRow = UIHelper.createTwoColumnRow(
                UIHelper.createFieldGroup(UIHelper.createFieldLabel("First Name"), txtFirstName),
                UIHelper.createFieldGroup(UIHelper.createFieldLabel("Last Name"), txtLastName));
        form.add(nameRow);
        form.add(Box.createVerticalStrut(20));

        PlaceholderTextField txtUsername = UIHelper.createSignupTextField("Username");
        form.add(UIHelper.createFieldGroup(UIHelper.createFieldLabel("Username"), txtUsername));
        form.add(Box.createVerticalStrut(20));

        PlaceholderTextField txtUserId = UIHelper.createSignupTextField("User ID");
        form.add(UIHelper.createFieldGroup(UIHelper.createFieldLabel("User ID"), txtUserId));
        form.add(Box.createVerticalStrut(20));

        PlaceholderTextField txtEmail = UIHelper.createSignupTextField("Email");
        form.add(UIHelper.createFieldGroup(UIHelper.createFieldLabel("Email"), txtEmail));
        form.add(Box.createVerticalStrut(20));

        PlaceholderPasswordField txtPassword = UIHelper.createSignupHalfPasswordField("Password");
        PlaceholderPasswordField txtConfirmPassword = UIHelper.createSignupHalfPasswordField("Confirm Password");
        JPanel passwordRow = UIHelper.createTwoColumnRow(
                UIHelper.createFieldGroup(UIHelper.createFieldLabel("Password"), txtPassword),
                UIHelper.createFieldGroup(UIHelper.createFieldLabel("Confirm Password"), txtConfirmPassword));
        form.add(passwordRow);
        form.add(Box.createVerticalStrut(20));

        cmbRole = UIHelper.createComboBox(new String[]{"Select your role", "Student", "Staff"});
        form.add(UIHelper.createFieldGroup(UIHelper.createFieldLabel("Select Role"), cmbRole));
        form.add(Box.createVerticalStrut(20));

        pnlStudentExtra = UIHelper.createFieldGroup(
                UIHelper.createFieldLabel("Room Number"),
                UIHelper.createSignupTextField("Room Number"));
        pnlStudentExtra.setVisible(false);
        pnlStudentExtra.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(pnlStudentExtra);

        cmbExpertise = UIHelper.createComboBox(new String[]{
            "Select your expertise", "Electrician", "Plumber", "Furniture Tech", "Other"
        });
        pnlStaffExtra = UIHelper.createFieldGroup(
                UIHelper.createFieldLabel("Select Expertise"), cmbExpertise);
        pnlStaffExtra.setVisible(false);
        pnlStaffExtra.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(pnlStaffExtra);

        form.add(Box.createVerticalStrut(28));

        javax.swing.JButton btnSignUp = UIHelper.createSignupPrimaryButton("Sign Up");
        btnSignUp.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSignUp.addActionListener(e -> {
            // Backend sign-up logic will be added later.
        });
        form.add(btnSignUp);

        card.add(form);

        cmbRole.addActionListener(e -> updateRoleFields());

        card.setMaximumSize(new Dimension(860, Integer.MAX_VALUE));
        card.setPreferredSize(new Dimension(860, card.getPreferredSize().height));

        JPanel centered = new JPanel(new java.awt.GridBagLayout());
        centered.setOpaque(false);
        centered.add(UIHelper.wrapSignupCard(card));
        getContentPane().add(centered, new GridBagConstraints());
        pack();
    }

    private void updateRoleFields() {
        String selected = (String) cmbRole.getSelectedItem();
        boolean isStudent = "Student".equals(selected);
        boolean isStaff = "Staff".equals(selected);
        pnlStudentExtra.setVisible(isStudent);
        pnlStaffExtra.setVisible(isStaff);
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
        setPreferredSize(new java.awt.Dimension(1280, 800));
        setResizable(false);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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

        java.awt.EventQueue.invokeLater(() -> UIHelper.showFrame(new SignUpFrame()));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
