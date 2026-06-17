/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem.gui.auth;

import studenthostelmaintenancerequest.trackingsystem.AuthService;
import studenthostelmaintenancerequest.trackingsystem.gui.common.GradientBackgroundPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.PasswordFieldPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.PasswordRequirementsPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper;

/**
 *
 * @author vian
 */
public class SetNewPasswordFrame extends javax.swing.JFrame {

    // logger for runtime diagnostics
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SetNewPasswordFrame.class.getName());
    // instance fields for class state
    private final String resetEmail;

    // construct frame and initialize UI
    public SetNewPasswordFrame() {
        this(null);
    }

    // construct frame and initialize UI
    public SetNewPasswordFrame(String resetEmail) {
        this.resetEmail = resetEmail;
        initComponents();
        customizeForm();
    }

    private void customizeForm() {
        UIHelper.styleAuthCard(pnlCard);
        UIHelper.styleAuthTitle(lblTitle);
        UIHelper.styleAuthInstructionLine(lblInstruction1, lblInstruction2);
        UIHelper.styleAuthFieldLabel(lblNewPassword, lblConfirmPassword);
        UIHelper.stylePrimaryButton(btnReset);
        UIHelper.styleReturnLink(lblReturn);

        UIHelper.addHyperlinkAction(lblReturn, () -> UIHelper.navigateTo(this, new LoginFrame()));

        UIHelper.centerAuthCard(this, pnlBackground);
        pnlCard.revalidate();
        getRootPane().setDefaultButton(btnReset);
    }

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {
        if (resetEmail == null || resetEmail.isBlank()) {
            UIHelper.navigateTo(this, new ForgotPasswordFrame());
            return;
        }
        String newPassword = new String(pnlNewPassword.getPasswordField().getPassword());
        String confirmPassword = new String(pnlConfirmPassword.getPasswordField().getPassword());
        AuthService.resetPassword(this, resetEmail, newPassword, confirmPassword);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBackground = new GradientBackgroundPanel();
        pnlCard = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblInstruction1 = new javax.swing.JLabel();
        lblInstruction2 = new javax.swing.JLabel();
        lblNewPassword = new javax.swing.JLabel();
        pnlNewPassword = new PasswordFieldPanel("Enter new password");
        lblConfirmPassword = new javax.swing.JLabel();
        pnlConfirmPassword = new PasswordFieldPanel("Re-enter new password");
        pnlRequirements = new PasswordRequirementsPanel();
        btnReset = new javax.swing.JButton();
        lblReturn = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Set New Password");
        setResizable(true);

        pnlBackground.setOpaque(true);

        pnlCard.setOpaque(true);

        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Set New Password");

        lblInstruction1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInstruction1.setText("Please create a new password that you don't use");

        lblInstruction2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInstruction2.setText("on any other site.");

        lblNewPassword.setText("New Password");

        lblConfirmPassword.setText("Confirm Password");

        btnReset.setText("Reset Password");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        lblReturn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReturn.setText("\u2190 Return to Login");

        javax.swing.GroupLayout pnlCardLayout = new javax.swing.GroupLayout(pnlCard);
        pnlCard.setLayout(pnlCardLayout);
        pnlCardLayout.setHorizontalGroup(
            pnlCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pnlCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblInstruction1, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblInstruction2, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNewPassword)
                    .addComponent(pnlNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblConfirmPassword)
                    .addComponent(pnlConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlRequirements, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReturn, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40))
        );
        pnlCardLayout.setVerticalGroup(
            pnlCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblTitle)
                .addGap(12, 12, 12)
                .addComponent(lblInstruction1)
                .addGap(2, 2, 2)
                .addComponent(lblInstruction2)
                .addGap(20, 20, 20)
                .addComponent(lblNewPassword)
                .addGap(6, 6, 6)
                .addComponent(pnlNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(lblConfirmPassword)
                .addGap(6, 6, 6)
                .addComponent(pnlConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(pnlRequirements, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(lblReturn)
                .addGap(40, 40, 40))
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String[] args) {
        UIHelper.initApplicationLook();
        java.awt.EventQueue.invokeLater(() -> new SetNewPasswordFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JLabel lblConfirmPassword;
    private javax.swing.JLabel lblInstruction1;
    private javax.swing.JLabel lblInstruction2;
    private javax.swing.JLabel lblNewPassword;
    private javax.swing.JLabel lblReturn;
    private javax.swing.JLabel lblTitle;
    private GradientBackgroundPanel pnlBackground;
    private javax.swing.JPanel pnlCard;
    private PasswordFieldPanel pnlConfirmPassword;
    private PasswordFieldPanel pnlNewPassword;
    private PasswordRequirementsPanel pnlRequirements;
    // End of variables declaration//GEN-END:variables
}
