/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem.gui.auth;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.CardPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.PlaceholderPasswordField;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper;

/**
 *
 * @author vian
 */
public class setNewPasswordFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(setNewPasswordFrame.class.getName());

    public setNewPasswordFrame() {
        initComponents();
        buildUi();
    }

    private void buildUi() {
        UIHelper.setupAuthFrame(this, "Set New Password", false);

        CardPanel card = new CardPanel();
        card.setLayout(new javax.swing.BoxLayout(card, javax.swing.BoxLayout.Y_AXIS));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = UIHelper.createHeaderLabel("Set New Password");
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(20));

        JLabel lblInstruction = UIHelper.createInstructionLabel(
                "Please create a new password that you don't use on any other site.");
        lblInstruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblInstruction);
        card.add(Box.createVerticalStrut(32));

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new javax.swing.BoxLayout(form, javax.swing.BoxLayout.Y_AXIS));
        form.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.setMaximumSize(new Dimension(UIHelper.FIELD_WIDTH, 700));

        PlaceholderPasswordField txtNewPassword = UIHelper.createPasswordField("Enter new password");
        PlaceholderPasswordField txtConfirmPassword = UIHelper.createPasswordField("Re-enter new password");

        JPanel newPasswordGroup = new JPanel();
        newPasswordGroup.setOpaque(false);
        newPasswordGroup.setLayout(new javax.swing.BoxLayout(newPasswordGroup, javax.swing.BoxLayout.Y_AXIS));
        newPasswordGroup.setAlignmentX(Component.LEFT_ALIGNMENT);
        newPasswordGroup.setMaximumSize(new Dimension(UIHelper.FIELD_WIDTH, 90));
        JLabel lblNewPassword = UIHelper.createFieldLabel("New Password");
        lblNewPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        newPasswordGroup.add(lblNewPassword);
        newPasswordGroup.add(Box.createVerticalStrut(8));
        JPanel newPasswordField = UIHelper.createPasswordWithToggle(txtNewPassword);
        newPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        newPasswordGroup.add(newPasswordField);

        JPanel confirmPasswordGroup = new JPanel();
        confirmPasswordGroup.setOpaque(false);
        confirmPasswordGroup.setLayout(new javax.swing.BoxLayout(confirmPasswordGroup, javax.swing.BoxLayout.Y_AXIS));
        confirmPasswordGroup.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmPasswordGroup.setMaximumSize(new Dimension(UIHelper.FIELD_WIDTH, 90));
        JLabel lblConfirmPassword = UIHelper.createFieldLabel("Confirm Password");
        lblConfirmPassword.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmPasswordGroup.add(lblConfirmPassword);
        confirmPasswordGroup.add(Box.createVerticalStrut(8));
        JPanel confirmPasswordField = UIHelper.createPasswordWithToggle(txtConfirmPassword);
        confirmPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmPasswordGroup.add(confirmPasswordField);

        form.add(newPasswordGroup);
        form.add(Box.createVerticalStrut(24));
        form.add(confirmPasswordGroup);
        form.add(Box.createVerticalStrut(24));

        JPanel requirements = UIHelper.createRequirementsPanel();
        requirements.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(requirements);
        form.add(Box.createVerticalStrut(28));

        javax.swing.JButton btnReset = UIHelper.createPrimaryButton("Reset Password");
        btnReset.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnReset.addActionListener(e -> {
            // Backend reset logic will be added later.
        });
        form.add(btnReset);
        form.add(Box.createVerticalStrut(24));

        JLabel lblReturn = UIHelper.createHyperlink("\u2190 Return to Login");
        lblReturn.setAlignmentX(Component.CENTER_ALIGNMENT);
        UIHelper.addHyperlinkAction(lblReturn, () -> UIHelper.navigateTo(this, new LoginFrame()));
        form.add(lblReturn);

        card.add(form);

        JPanel centered = UIHelper.centerCard(card, 680);
        getContentPane().add(centered, new GridBagConstraints());
        pack();
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
        setTitle("Set New Password");
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

        java.awt.EventQueue.invokeLater(() -> UIHelper.showFrame(new setNewPasswordFrame()));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
