package studenthostelmaintenancerequest.trackingsystem.gui.student;

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

    private static final Object[][] MOCK_HISTORY = buildMockHistory();

    private static Object[][] buildMockHistory() {
        String[] types = {"Electrical", "Plumbing", "Furniture"};
        String[] dates = {"8 June 2026", "9 June 2026", "10 June 2026", "11 June 2026",
            "12 June 2026", "13 June 2026", "14 June 2026"};
        String[] priorities = {"High", "Medium", "Low"};
        String[] statuses = {"IN PROGRESS", "SUBMITTED", "COMPLETED"};
        Object[][] rows = new Object[21][5];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Object[]{
                String.format("REQ%03d", i + 1),
                types[i % types.length],
                dates[i % dates.length],
                priorities[i % priorities.length],
                statuses[i % statuses.length]
            };
        }
        return rows;
    }

    private PlaceholderTextField txtSearch;
    private StudentHistoryTableModel historyTableModel;
    private JLabel lblPagination;
    private javax.swing.JButton btnPagePrevious;
    private javax.swing.JButton btnPageNext;

    private String filterStatus = "All";
    private String filterType = "All";

    public studRHistory() {
        initComponents();
        customizeForm();
    }

    private void customizeForm() {
        UIHelper.configureStudentShell(this,
                pnlHeader, pnlSidebar, pnlMain, pnlPageHeader,
                lblAppTitle, lblPageTitle, pnlHeaderLogo,
                pnlUserProfile, lblUserName, lblUserRole, btnUserMenu,
                btnNavDashboard, btnNavSubmit, btnNavHistory,
                UIHelper.StudentNavPage.HISTORY);
        wireNavigation();

        txtSearch = new PlaceholderTextField("Search Request by ID");
        JPanel pnlSearchField = UIHelper.createSearchField(txtSearch);
        UIHelper.layoutManagerViewHistoryToolbar(pnlToolbar, pnlSearchField, btnFilter);
        UIHelper.styleManagerFilterButton(btnFilter);

        lblTableSection.setText("Maintenance Request History");
        historyTableModel = UIHelper.createStudentHistoryTableModel(MOCK_HISTORY);
        tblHistory.setModel(historyTableModel);
        UIHelper.styleManagerTableSection(lblTableSection, tblHistory, scrHistory);
        UIHelper.applyStudentHistoryTableRenderers(tblHistory);

        lblPagination = new JLabel();
        btnPagePrevious = new javax.swing.JButton("<");
        btnPageNext = new javax.swing.JButton(">");
        UIHelper.layoutManagerTableSectionWithPagination(
                pnlTableSection, lblTableSection, scrHistory,
                lblPagination, btnPagePrevious, btnPageNext);
        refreshPaginationFooter();

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { applySearchAndFilter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { applySearchAndFilter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { applySearchAndFilter(); }
        });
        btnFilter.addActionListener(e -> showFilterDialog());
        btnPagePrevious.addActionListener(e -> changePage(-1));
        btnPageNext.addActionListener(e -> changePage(1));

        UIHelper.showManagerFrame(this);
        resizeTableSection();
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
            btnFilter.setText(active ? "Filter ✓" : "Filter");
            btnFilter.setForeground(active ? AppColors.PRIMARY : AppColors.LABEL);
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
        UIHelper.updateStudentPaginationFooter(lblPagination, btnPagePrevious, btnPageNext, historyTableModel);
    }

    private void resizeTableSection() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            UIHelper.sizeStudentPaginatedTable(tblHistory, scrHistory);
            pnlTableSection.revalidate();
        });
    }

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
        btnNavDashboard = new ManagerNavButton("Dashboard");
        btnNavSubmit = new ManagerNavButton("Submit Maintenance Request");
        btnNavHistory = new ManagerNavButton("View Request History");
        pnlContent = new javax.swing.JPanel();
        pnlPageHeader = new javax.swing.JPanel();
        lblPageTitle = new javax.swing.JLabel();
        pnlMain = new javax.swing.JPanel();
        pnlToolbar = new javax.swing.JPanel();
        btnFilter = new javax.swing.JButton();
        pnlTableSection = new javax.swing.JPanel();
        lblTableSection = new javax.swing.JLabel();
        scrHistory = new javax.swing.JScrollPane();
        tblHistory = new javax.swing.JTable();

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
        lblUserName.setText("Alex Johnson");

        lblUserRole.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserRole.setText("STUDENT");

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
        pnlSidebar.add(btnNavSubmit);
        pnlSidebar.add(btnNavHistory);

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

        lblTableSection.setText("Maintenance Request History");

        tblHistory.setModel(new javax.swing.table.DefaultTableModel(
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
        scrHistory.setViewportView(tblHistory);

        javax.swing.GroupLayout pnlTableSectionLayout = new javax.swing.GroupLayout(pnlTableSection);
        pnlTableSection.setLayout(pnlTableSectionLayout);
        pnlTableSection.setOpaque(false);
        pnlTableSectionLayout.setHorizontalGroup(
            pnlTableSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTableSection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTableSectionLayout.setVerticalGroup(
            pnlTableSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableSectionLayout.createSequentialGroup()
                .addComponent(lblTableSection)
                .addGap(0, 0, 0)
                .addComponent(scrHistory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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

        pnlSidebar.setPreferredSize(new java.awt.Dimension(UIHelper.STUDENT_SIDEBAR_WIDTH, 0));
        pnlPageHeader.setPreferredSize(new java.awt.Dimension(0, 72));
        pnlHeader.setPreferredSize(new java.awt.Dimension(0, 64));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String[] args) {
        UIHelper.initApplicationLook();
        java.awt.EventQueue.invokeLater(() -> new studRHistory());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnUserMenu;
    private ManagerNavButton btnNavDashboard;
    private ManagerNavButton btnNavHistory;
    private ManagerNavButton btnNavSubmit;
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
    private javax.swing.JScrollPane scrHistory;
    private javax.swing.JTable tblHistory;
    // End of variables declaration//GEN-END:variables
}
