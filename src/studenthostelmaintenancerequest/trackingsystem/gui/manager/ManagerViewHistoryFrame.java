/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem.gui.manager;

import java.util.ArrayList;
import java.util.List;
import studenthostelmaintenancerequest.trackingsystem.DatabaseException;
import studenthostelmaintenancerequest.trackingsystem.ManagerService;
import studenthostelmaintenancerequest.trackingsystem.gui.common.LogoPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.ManagerNavButton;
import studenthostelmaintenancerequest.trackingsystem.gui.common.PlaceholderTextField;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper.FilterOption;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper.ManagerViewHistoryTableModel;

/**
 *
 * @author vian
 */
public class ManagerViewHistoryFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManagerViewHistoryFrame.class.getName());

    private ManagerViewHistoryTableModel requestTableModel;
    private PlaceholderTextField txtSearch;
    private javax.swing.JLabel lblPagination;
    private javax.swing.JButton btnPagePrevious;
    private javax.swing.JButton btnPageNext;

    private Object[][] allHistoryRows = new Object[0][0];
    private String filterStatus = "All";
    private String filterPriority = "All";
    private String filterType = "All";

    public ManagerViewHistoryFrame() {
        initComponents();
        customizeForm();
    }

    private void customizeForm() {
        UIHelper.styleManagerShell(pnlHeader, pnlSidebar, pnlMain, lblAppTitle, pnlHeaderLogo);
        UIHelper.styleManagerPageHeader(pnlPageHeader, lblPageTitle);
        UIHelper.installManagerSession(this, pnlUserProfile, lblUserName, lblUserRole, btnUserMenu);
        UIHelper.layoutManagerSidebar(pnlSidebar,
                btnNavDashboard, btnNavManageRequests, btnNavAssignStaff,
                btnNavRoomDetails, btnNavRequestHistory);
        UIHelper.styleManagerNavButton(btnNavDashboard, false);
        UIHelper.styleManagerNavButton(btnNavManageRequests, false);
        UIHelper.styleManagerNavButton(btnNavAssignStaff, false);
        UIHelper.styleManagerNavButton(btnNavRoomDetails, false);
        UIHelper.styleManagerNavButton(btnNavRequestHistory, true);

        wireNavigation();

        PlaceholderTextField txtSearch = new PlaceholderTextField("Search Request by ID");
        this.txtSearch = txtSearch;
        javax.swing.JPanel pnlSearchField = UIHelper.createSearchField(txtSearch);
        UIHelper.layoutManagerViewHistoryToolbar(pnlToolbar, pnlSearchField, btnFilter);
        UIHelper.styleManagerFilterButton(btnFilter);

        lblTableSection.setText("All Requests");

        requestTableModel = UIHelper.createManagerViewHistoryTableModel();
        tblRequests.setModel(requestTableModel);
        UIHelper.styleManagerTableSection(lblTableSection, tblRequests, scrTable);
        UIHelper.applyManagerViewHistoryTableRenderers(tblRequests);
        UIHelper.installDescriptionRowClickHandler(
                tblRequests, UIHelper.VIEW_HISTORY_COL_DESCRIPTION, 0, this);

        lblPagination = new javax.swing.JLabel();
        btnPagePrevious = new javax.swing.JButton("<");
        btnPageNext = new javax.swing.JButton(">");
        UIHelper.layoutManagerTableSectionWithPagination(
                pnlTableSection, lblTableSection, scrTable,
                lblPagination, btnPagePrevious, btnPageNext);

        loadHistoryData();

        btnPagePrevious.addActionListener(e -> changePage(-1));
        btnPageNext.addActionListener(e -> changePage(1));

        btnFilter.addActionListener(e -> showFilterDialog());
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { applySearchAndFilter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { applySearchAndFilter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { applySearchAndFilter(); }
        });

        UIHelper.showManagerFrame(this);
        javax.swing.SwingUtilities.invokeLater(() -> {
            UIHelper.sizeManagerViewHistoryTable(tblRequests, scrTable);
            pnlTableSection.revalidate();
        });
    }

    private void loadHistoryData() {
        try {
            allHistoryRows = ManagerService.getHistoryRows(null);
            applySearchAndFilter();
        } catch (DatabaseException ex) {
            UIHelper.showDatabaseError(this, ex);
        }
    }

    private void applySearchAndFilter() {
        String keyword = txtSearch.getInputText().trim().toLowerCase();
        List<Object[]> filtered = new ArrayList<>();

        for (Object[] row : allHistoryRows) {
            boolean keywordMatch = keyword.isEmpty()
                    || row[0].toString().toLowerCase().contains(keyword);
            boolean statusMatch = filterStatus.equals("All")
                    || row[UIHelper.VIEW_HISTORY_COL_STATUS].toString().equalsIgnoreCase(filterStatus);
            boolean priorityMatch = filterPriority.equals("All")
                    || row[UIHelper.VIEW_HISTORY_COL_PRIORITY].toString().equalsIgnoreCase(filterPriority);
            boolean typeMatch = filterType.equals("All")
                    || row[1].toString().equalsIgnoreCase(filterType);

            if (keywordMatch && statusMatch && priorityMatch && typeMatch) {
                filtered.add(row);
            }
        }

        requestTableModel.replaceRows(filtered.toArray(Object[][]::new));
        refreshPaginationFooter();
        resizeTableSection();
    }

    private void showFilterDialog() {
        FilterOption statusOption = new FilterOption("Status",
                new String[]{"All", "SUBMITTED", "IN PROGRESS", "COMPLETED", "CANCELLED"}, filterStatus);
        FilterOption priorityOption = new FilterOption("Priority",
                new String[]{"All", "Low", "Medium", "High"}, filterPriority);
        FilterOption typeOption = new FilterOption("Request Type",
                new String[]{"All", "Electrical", "Plumbing", "Furniture"}, filterType);

        if (UIHelper.showRequestFilterDialog(this, statusOption, priorityOption, typeOption)) {
            filterStatus = statusOption.value;
            filterPriority = priorityOption.value;
            filterType = typeOption.value;
            UIHelper.updateFilterButtonState(btnFilter, statusOption, priorityOption, typeOption);
            applySearchAndFilter();
        }
    }

    private void changePage(int direction) {
        if (direction < 0) {
            requestTableModel.previousPage();
        } else {
            requestTableModel.nextPage();
        }
        refreshPaginationFooter();
        tblRequests.repaint();
        resizeTableSection();
    }

    private void resizeTableSection() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            UIHelper.sizeManagerViewHistoryTable(tblRequests, scrTable);
            pnlTableSection.revalidate();
        });
    }

    private void refreshPaginationFooter() {
        UIHelper.updateManagerPaginationFooter(
                lblPagination, btnPagePrevious, btnPageNext, requestTableModel);
    }

    private void wireNavigation() {
        btnNavDashboard.addActionListener(e -> UIHelper.navigateTo(this, new ManagerDashboardFrame()));
        btnNavManageRequests.addActionListener(e -> UIHelper.navigateTo(this, new ManagerManageRequestFrame()));
        btnNavAssignStaff.addActionListener(e -> UIHelper.navigateTo(this, new ManagerAssignStaffFrame()));
        btnNavRoomDetails.addActionListener(e -> UIHelper.navigateTo(this, new ManagerViewRoomFrame()));
        btnNavRequestHistory.addActionListener(e -> { /* already on this page */ });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRoot = new javax.swing.JPanel();
        pnlHeader = new javax.swing.JPanel();
        pnlHeaderLeft = new javax.swing.JPanel();
        pnlHeaderLogo = new LogoPanel();
        lblAppTitle = new javax.swing.JLabel();
        pnlUserProfile = new javax.swing.JPanel();
        pnlUserText = new javax.swing.JPanel();
        lblUserName = new javax.swing.JLabel();
        lblUserRole = new javax.swing.JLabel();
        btnUserMenu = new javax.swing.JButton();
        pnlBody = new javax.swing.JPanel();
        pnlSidebar = new javax.swing.JPanel();
        pnlContent = new javax.swing.JPanel();
        pnlPageHeader = new javax.swing.JPanel();
        lblPageTitle = new javax.swing.JLabel();
        btnNavDashboard = new ManagerNavButton("Dashboard");
        btnNavManageRequests = new ManagerNavButton("Manage Active Requests");
        btnNavAssignStaff = new ManagerNavButton("Assign Staff");
        btnNavRoomDetails = new ManagerNavButton("View Room Details");
        btnNavRequestHistory = new ManagerNavButton("View Request History");
        pnlMain = new javax.swing.JPanel();
        pnlToolbar = new javax.swing.JPanel();
        btnFilter = new javax.swing.JButton();
        pnlTableSection = new javax.swing.JPanel();
        lblTableSection = new javax.swing.JLabel();
        scrTable = new javax.swing.JScrollPane();
        tblRequests = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("View Request History");

        pnlRoot.setLayout(new java.awt.BorderLayout());

        pnlHeader.setLayout(new java.awt.BorderLayout());

        pnlHeaderLeft.setOpaque(false);

        lblAppTitle.setText("Student Hostel Maintenance Request & Tracking System");

        javax.swing.GroupLayout pnlHeaderLeftLayout = new javax.swing.GroupLayout(pnlHeaderLeft);
        pnlHeaderLeft.setLayout(pnlHeaderLeftLayout);
        pnlHeaderLeftLayout.setHorizontalGroup(
            pnlHeaderLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLeftLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pnlHeaderLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblAppTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHeaderLeftLayout.setVerticalGroup(
            pnlHeaderLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLeftLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(pnlHeaderLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlHeaderLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAppTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pnlHeader.add(pnlHeaderLeft, java.awt.BorderLayout.WEST);

        pnlUserProfile.setOpaque(false);

        lblUserName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserName.setText("Admin");

        lblUserRole.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserRole.setText("ADMIN");

        javax.swing.GroupLayout pnlUserTextLayout = new javax.swing.GroupLayout(pnlUserText);
        pnlUserText.setLayout(pnlUserTextLayout);
        pnlUserText.setOpaque(false);
        pnlUserTextLayout.setHorizontalGroup(
            pnlUserTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUserName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblUserRole, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlUserTextLayout.setVerticalGroup(
            pnlUserTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserTextLayout.createSequentialGroup()
                .addComponent(lblUserName)
                .addGap(2, 2, 2)
                .addComponent(lblUserRole))
        );

        btnUserMenu.setText("v");

        javax.swing.GroupLayout pnlUserProfileLayout = new javax.swing.GroupLayout(pnlUserProfile);
        pnlUserProfile.setLayout(pnlUserProfileLayout);
        pnlUserProfileLayout.setHorizontalGroup(
            pnlUserProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUserProfileLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlUserText, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(btnUserMenu)
                .addGap(20, 20, 20))
        );
        pnlUserProfileLayout.setVerticalGroup(
            pnlUserProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserProfileLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlUserProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pnlUserText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUserMenu))
                .addGap(10, 10, 10))
        );

        pnlHeader.add(pnlUserProfile, java.awt.BorderLayout.EAST);

        pnlRoot.add(pnlHeader, java.awt.BorderLayout.NORTH);

        pnlBody.setLayout(new java.awt.BorderLayout());

        pnlSidebar.setLayout(new javax.swing.BoxLayout(pnlSidebar, javax.swing.BoxLayout.Y_AXIS));
        pnlSidebar.add(btnNavDashboard);
        pnlSidebar.add(btnNavManageRequests);
        pnlSidebar.add(btnNavAssignStaff);
        pnlSidebar.add(btnNavRoomDetails);
        pnlSidebar.add(btnNavRequestHistory);

        pnlBody.add(pnlSidebar, java.awt.BorderLayout.WEST);

        pnlContent.setLayout(new java.awt.BorderLayout());

        lblPageTitle.setText("View Request History");

        javax.swing.GroupLayout pnlPageHeaderLayout = new javax.swing.GroupLayout(pnlPageHeader);
        pnlPageHeader.setLayout(pnlPageHeaderLayout);
        pnlPageHeaderLayout.setHorizontalGroup(
            pnlPageHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPageHeaderLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(lblPageTitle)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPageHeaderLayout.setVerticalGroup(
            pnlPageHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPageHeaderLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblPageTitle)
                .addGap(20, 20, 20))
        );

        pnlContent.add(pnlPageHeader, java.awt.BorderLayout.NORTH);

        pnlToolbar.setOpaque(false);

        btnFilter.setText("Filter");

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTableSection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(pnlToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(pnlTableSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTableSection.setText("All Requests");

        tblRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Request ID", "Request Type", "Student Name", "Assigned Staff", "Description", "Priority", "Status"
            }
        ));
        scrTable.setViewportView(tblRequests);

        javax.swing.GroupLayout pnlTableSectionLayout = new javax.swing.GroupLayout(pnlTableSection);
        pnlTableSection.setLayout(pnlTableSectionLayout);
        pnlTableSection.setOpaque(false);
        pnlTableSectionLayout.setHorizontalGroup(
            pnlTableSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTableSection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTableSectionLayout.setVerticalGroup(
            pnlTableSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableSectionLayout.createSequentialGroup()
                .addComponent(lblTableSection)
                .addGap(0, 0, 0)
                .addComponent(scrTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlContent.add(pnlMain, java.awt.BorderLayout.CENTER);

        pnlBody.add(pnlContent, java.awt.BorderLayout.CENTER);

        pnlRoot.add(pnlBody, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlRoot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlSidebar.setPreferredSize(new java.awt.Dimension(220, 0));
        pnlPageHeader.setPreferredSize(new java.awt.Dimension(0, 72));
        pnlHeader.setPreferredSize(new java.awt.Dimension(0, 64));

        pack();
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

        UIHelper.initApplicationLook();
        java.awt.EventQueue.invokeLater(() -> new ManagerViewHistoryFrame());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnUserMenu;
    private ManagerNavButton btnNavAssignStaff;
    private ManagerNavButton btnNavDashboard;
    private ManagerNavButton btnNavManageRequests;
    private ManagerNavButton btnNavRequestHistory;
    private ManagerNavButton btnNavRoomDetails;
    private javax.swing.JLabel lblAppTitle;
    private javax.swing.JLabel lblPageTitle;
    private javax.swing.JLabel lblTableSection;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserRole;
    private javax.swing.JPanel pnlBody;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    private LogoPanel pnlHeaderLogo;
    private javax.swing.JPanel pnlHeaderLeft;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlPageHeader;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlTableSection;
    private javax.swing.JPanel pnlToolbar;
    private javax.swing.JPanel pnlUserProfile;
    private javax.swing.JPanel pnlUserText;
    private javax.swing.JScrollPane scrTable;
    private javax.swing.JTable tblRequests;
    // End of variables declaration//GEN-END:variables
}
