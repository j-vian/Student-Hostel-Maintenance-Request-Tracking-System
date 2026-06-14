package studenthostelmaintenancerequest.trackingsystem.gui.student;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.AppColors;
import studenthostelmaintenancerequest.trackingsystem.gui.common.LogoPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.ManagerNavButton;
import studenthostelmaintenancerequest.trackingsystem.gui.common.PlaceholderTextField;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper.StudentHistoryTableModel;

/**
 * Student request history with search, filter, and pagination.
 */
public class studRHistory extends javax.swing.JFrame {

    private static final Object[][] MOCK_HISTORY = {
        {"REQ001", "Electrical", "8 June 2026", "High", "IN PROGRESS"},
        {"REQ002", "Furniture", "8 June 2026", "Medium", "SUBMITTED"},
        {"REQ003", "Plumbing", "8 June 2026", "Low", "COMPLETED"},
        {"REQ004", "Electrical", "7 June 2026", "High", "IN PROGRESS"},
        {"REQ005", "Plumbing", "6 June 2026", "Medium", "COMPLETED"},
        {"REQ006", "Furniture", "5 June 2026", "Low", "SUBMITTED"},
        {"REQ007", "Electrical", "4 June 2026", "High", "COMPLETED"},
        {"REQ008", "Furniture", "3 June 2026", "Medium", "IN PROGRESS"},
        {"REQ009", "Plumbing", "2 June 2026", "Low", "SUBMITTED"},
        {"REQ010", "Electrical", "1 June 2026", "High", "COMPLETED"},
        {"REQ011", "Plumbing", "31 May 2026", "Medium", "IN PROGRESS"},
        {"REQ012", "Furniture", "30 May 2026", "Low", "SUBMITTED"}
    };

    private LogoPanel pnlHeaderLogo;
    private JPanel pnlUserProfile;
    private JLabel lblUserName;
    private JLabel lblUserRole;
    private javax.swing.JButton btnUserMenu;
    private ManagerNavButton btnNavDashboard;
    private ManagerNavButton btnNavSubmit;
    private ManagerNavButton btnNavHistory;

    private PlaceholderTextField txtSearch;
    private StudentHistoryTableModel historyTableModel;

    private String filterStatus = "All";
    private String filterType = "All";

    public studRHistory() {
        initComponents();
        customizeForm();
    }

    private void customizeForm() {
        setTitle("View Request History");

        pnlHeaderLogo = new LogoPanel();
        lblUserName = new JLabel();
        lblUserRole = new JLabel();
        btnUserMenu = new javax.swing.JButton();
        JPanel pnlUserHost = UIHelper.createStudentUserProfileHost();
        pnlUserProfile = UIHelper.createStudentUserProfile(lblUserName, lblUserRole, btnUserMenu);
        pnlUserHost.add(pnlUserProfile);

        UIHelper.setupStudentHeader(jPanel1, jLabel6, pnlUserHost, pnlHeaderLogo);
        UIHelper.styleManagerShell(jPanel1, jPanel2, jPanel4, jLabel6, pnlHeaderLogo);
        UIHelper.styleStudentMainContent(jPanel4);
        UIHelper.installStudentSession(this, pnlUserProfile, lblUserName, lblUserRole, btnUserMenu);

        btnNavDashboard = new ManagerNavButton("Dashboard");
        btnNavSubmit = new ManagerNavButton("Submit Maintenance Request");
        btnNavHistory = new ManagerNavButton("View Request History");
        jPanel2.removeAll();
        UIHelper.layoutStudentSidebar(jPanel2, btnNavDashboard, btnNavSubmit, btnNavHistory);
        UIHelper.styleManagerNavButton(btnNavDashboard, false);
        UIHelper.styleManagerNavButton(btnNavSubmit, false);
        UIHelper.styleManagerNavButton(btnNavHistory, true);
        wireNavigation();

        UIHelper.styleStudentPageTitle(jLabel1);

        txtSearch = new PlaceholderTextField("Search Request by ID");
        JPanel pnlSearchField = UIHelper.createSearchField(txtSearch);
        UIHelper.styleManagerFilterButton(jButton7);
        rebuildContentArea(pnlSearchField);

        jLabel2.setText("Maintenance Request History");
        historyTableModel = UIHelper.createStudentHistoryTableModel(MOCK_HISTORY);
        jTable1.setModel(historyTableModel);
        UIHelper.styleManagerTableSection(jLabel2, jTable1, jScrollPane1);
        UIHelper.applyStudentHistoryTableRenderers(jTable1);

        UIHelper.styleManagerPaginationButton(jButton5);
        UIHelper.styleManagerPaginationButton(jButton6);
        refreshPaginationFooter();

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { applySearchAndFilter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { applySearchAndFilter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { applySearchAndFilter(); }
        });
        jButton7.addActionListener(e -> showFilterDialog());
        jButton5.addActionListener(e -> changePage(-1));
        jButton6.addActionListener(e -> changePage(1));

        UIHelper.showManagerFrame(this);
        resizeTableSection();
    }

    private void rebuildContentArea(JPanel pnlSearchField) {
        jPanel4.removeAll();
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));
        jPanel4.setOpaque(false);

        jLabel1.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        jPanel4.add(jLabel1);
        jPanel4.add(javax.swing.Box.createVerticalStrut(12));

        JPanel searchRow = new JPanel(new BorderLayout(12, 0));
        searchRow.setOpaque(false);
        searchRow.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        searchRow.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, UIHelper.MANAGER_SEARCH_HEIGHT));
        searchRow.add(pnlSearchField, BorderLayout.CENTER);
        searchRow.add(jButton7, BorderLayout.EAST);
        jPanel4.add(searchRow);
        jPanel4.add(javax.swing.Box.createVerticalStrut(18));

        JPanel tableSection = new JPanel(new BorderLayout());
        tableSection.setOpaque(false);
        tableSection.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        tableSection.setMaximumSize(new java.awt.Dimension(640, Integer.MAX_VALUE));
        tableSection.add(jLabel2, BorderLayout.NORTH);
        tableSection.add(jScrollPane1, BorderLayout.CENTER);
        jPanel4.add(tableSection);
        jPanel4.add(javax.swing.Box.createVerticalStrut(12));

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        footer.setMaximumSize(new java.awt.Dimension(640, 40));
        footer.add(jLabel3, BorderLayout.WEST);
        JPanel nav = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 8, 0));
        nav.setOpaque(false);
        nav.add(jButton5);
        nav.add(jButton6);
        footer.add(nav, BorderLayout.EAST);
        jPanel4.add(footer);
        jPanel4.add(javax.swing.Box.createVerticalGlue());
    }

    private void wireNavigation() {
        btnNavDashboard.addActionListener(e -> UIHelper.navigateTo(this, new stdDarshboard()));
        btnNavSubmit.addActionListener(e -> UIHelper.navigateTo(this, new SubmitMRequest()));
        btnNavHistory.addActionListener(e -> { /* current page */ });
    }

    private void applySearchAndFilter() {
        String keyword = txtSearch.getInputText().trim().toLowerCase();
        List<Object[]> filtered = new ArrayList<>();

        for (Object[] row : MOCK_HISTORY) {
            boolean keywordMatch = keyword.isEmpty();
            if (!keywordMatch) {
                for (Object cell : row) {
                    if (cell != null && cell.toString().toLowerCase().contains(keyword)) {
                        keywordMatch = true;
                        break;
                    }
                }
            }
            boolean statusMatch = filterStatus.equals("All")
                    || row[4].toString().equalsIgnoreCase(filterStatus);
            boolean typeMatch = filterType.equals("All")
                    || row[1].toString().equalsIgnoreCase(filterType);

            if (keywordMatch && statusMatch && typeMatch) {
                filtered.add(row);
            }
        }

        historyTableModel.replaceRows(filtered.toArray(Object[][]::new));
        refreshPaginationFooter();
        resizeTableSection();
    }

    private void showFilterDialog() {
        String[] statusOptions = {"All", "IN PROGRESS", "SUBMITTED", "COMPLETED", "CANCELLED"};
        String[] typeOptions = {"All", "Electrical", "Plumbing", "Furniture"};

        JPanel panel = new JPanel(new java.awt.GridLayout(2, 2, 8, 8));
        javax.swing.JComboBox<String> statusCombo = new javax.swing.JComboBox<>(statusOptions);
        javax.swing.JComboBox<String> typeCombo = new javax.swing.JComboBox<>(typeOptions);
        statusCombo.setSelectedItem(filterStatus);
        typeCombo.setSelectedItem(filterType);
        panel.add(new JLabel("Status:"));
        panel.add(statusCombo);
        panel.add(new JLabel("Request Type:"));
        panel.add(typeCombo);

        int result = javax.swing.JOptionPane.showConfirmDialog(
                this, panel, "Filter Requests",
                javax.swing.JOptionPane.OK_CANCEL_OPTION,
                javax.swing.JOptionPane.PLAIN_MESSAGE);

        if (result == javax.swing.JOptionPane.OK_OPTION) {
            filterStatus = (String) statusCombo.getSelectedItem();
            filterType = (String) typeCombo.getSelectedItem();
            boolean active = !filterStatus.equals("All") || !filterType.equals("All");
            jButton7.setText(active ? "Filter ✓" : "Filter");
            jButton7.setForeground(active ? AppColors.PRIMARY : AppColors.LABEL);
            applySearchAndFilter();
        }
    }

    private void changePage(int direction) {
        if (direction < 0) {
            historyTableModel.previousPage();
        } else {
            historyTableModel.nextPage();
        }
        refreshPaginationFooter();
        resizeTableSection();
    }

    private void refreshPaginationFooter() {
        UIHelper.updateStudentPaginationFooter(jLabel3, jButton5, jButton6, historyTableModel);
    }

    private void resizeTableSection() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            UIHelper.sizeStudentHistoryTable(jTable1, jScrollPane1);
            jPanel4.revalidate();
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("View Request History");

        jLabel2.setBackground(new java.awt.Color(210, 210, 210));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Maintenance Request History");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel2.setOpaque(true);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Request ID", "Request Type", "Date Raised", "Priority", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton5.setText("<");
        jButton6.setText(">");
        jLabel3.setText("Showing 1 to 3 of 12 results");

        jButton7.setBackground(new java.awt.Color(245, 245, 245));
        jButton7.setText("Filter");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.setFocusPainted(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addGap(32, 32, 32))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jLabel3))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jButton3.setText("View Request History");
        jButton2.setLabel("Submit Maintenance Request");
        jButton1.setText("Darshboard");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addComponent(jButton1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(32, 32, 32)
                .addComponent(jButton3)
                .addGap(37, 37, 37))
        );

        jPanel1.setForeground(new java.awt.Color(102, 102, 102));

        jLabel6.setText("Student Hostel Maintenance Request & Tracking System");

        jButton4.setText("Alex Johnson ▾ STUDENT");
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButton4)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> UIHelper.showManagerFrame(new studRHistory()));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
