package studenthostelmaintenancerequest.trackingsystem.gui.student;

import javax.swing.JLabel;
import studenthostelmaintenancerequest.trackingsystem.DatabaseException;
import studenthostelmaintenancerequest.trackingsystem.Student;
import studenthostelmaintenancerequest.trackingsystem.StudentService;
import studenthostelmaintenancerequest.trackingsystem.gui.common.LogoPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.ManagerNavButton;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper.StudentActiveRequestTableModel;

/**
 * Student dashboard — profile summary and active maintenance requests.
 */
public class stdDashboard extends javax.swing.JFrame {

    private StudentActiveRequestTableModel activeTableModel;
    private JLabel lblActivePagination;
    private javax.swing.JButton btnActivePrevious;
    private javax.swing.JButton btnActiveNext;

    public stdDashboard() {
        initComponents();
        customizeForm();
    }

    private void customizeForm() {
        Student student = StudentService.requireStudent(this);
        if (student == null) {
            return;
        }

        UIHelper.configureStudentShell(this,
                pnlHeader, pnlSidebar, pnlMain, pnlPageHeader,
                lblAppTitle, lblPageTitle, pnlHeaderLogo,
                pnlUserProfile, lblUserName, lblUserRole, btnUserMenu,
                btnNavDashboard, btnNavSubmit, btnNavHistory,
                UIHelper.StudentNavPage.DASHBOARD);
        wireNavigation();

        tblProfile.setModel(UIHelper.createStudentProfileTableModel(new Object[0][0]));
        UIHelper.styleStudentProfileTable(lblProfileSection, tblProfile, scrProfile);
        UIHelper.layoutStudentProfileSection(pnlProfileSection, lblProfileSection, scrProfile);
        UIHelper.setStudentProfileTableRows(tblProfile, scrProfile, new Object[0][0]);

        activeTableModel = UIHelper.createStudentActiveRequestTableModel(new Object[0][0]);
        tblActive.setModel(activeTableModel);
        UIHelper.styleManagerTableSection(lblActiveSection, tblActive, scrActive);
        UIHelper.applyStudentActiveRequestTableRenderers(tblActive);

        lblActivePagination = new JLabel();
        btnActivePrevious = new javax.swing.JButton("<");
        btnActiveNext = new javax.swing.JButton(">");
        UIHelper.layoutManagerTableSectionWithPagination(
                pnlActiveSection, lblActiveSection, scrActive,
                lblActivePagination, btnActivePrevious, btnActiveNext);
        refreshActivePagination();

        btnActivePrevious.addActionListener(e -> changeActivePage(-1));
        btnActiveNext.addActionListener(e -> changeActivePage(1));

        loadDashboardData(student);

        UIHelper.showManagerFrame(this);
        javax.swing.SwingUtilities.invokeLater(() -> {
            UIHelper.sizeStudentProfileTable(tblProfile, scrProfile);
            resizeActiveTableSection();
            pnlProfileSection.revalidate();
        });
    }

    private void loadDashboardData(Student student) {
        try {
            UIHelper.setStudentProfileTableRows(tblProfile, scrProfile,
                    StudentService.getProfileRows(student));
            activeTableModel.replaceRows(StudentService.getActiveRequestRows(student));
            refreshActivePagination();
            resizeActiveTableSection();
            UIHelper.sizeStudentProfileTable(tblProfile, scrProfile);
            pnlProfileSection.revalidate();
        } catch (DatabaseException ex) {
            UIHelper.showDatabaseError(this, ex);
        }
    }

    private void changeActivePage(int direction) {
        if (direction < 0) {
            activeTableModel.previousPage();
        } else {
            activeTableModel.nextPage();
        }
        refreshActivePagination();
        resizeActiveTableSection();
    }

    private void refreshActivePagination() {
        UIHelper.updateStudentPaginationFooter(
                lblActivePagination, btnActivePrevious, btnActiveNext, activeTableModel);
    }

    private void resizeActiveTableSection() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            UIHelper.sizeStudentPaginatedTable(tblActive, scrActive);
            pnlActiveSection.revalidate();
        });
    }

    private void wireNavigation() {
        btnNavDashboard.addActionListener(e -> { /* current page */ });
        btnNavSubmit.addActionListener(e -> UIHelper.navigateTo(this, new SubmitMRequest()));
        btnNavHistory.addActionListener(e -> UIHelper.navigateTo(this, new studRHistory()));
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
        pnlProfileSection = new javax.swing.JPanel();
        lblProfileSection = new javax.swing.JLabel();
        scrProfile = new javax.swing.JScrollPane();
        tblProfile = new javax.swing.JTable();
        pnlActiveSection = new javax.swing.JPanel();
        lblActiveSection = new javax.swing.JLabel();
        scrActive = new javax.swing.JScrollPane();
        tblActive = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Dashboard");

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
        lblUserName.setText("Student");

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

        lblPageTitle.setText("Dashboard");

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

        lblProfileSection.setText("STUDENT PROFILE");

        tblProfile.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Name", ""},
                {"Student ID", ""},
                {"Room No.", ""}
            },
            new String [] {
                " ", " "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrProfile.setViewportView(tblProfile);

        javax.swing.GroupLayout pnlProfileSectionLayout = new javax.swing.GroupLayout(pnlProfileSection);
        pnlProfileSection.setLayout(pnlProfileSectionLayout);
        pnlProfileSection.setOpaque(false);
        pnlProfileSectionLayout.setHorizontalGroup(
            pnlProfileSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProfileSection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlProfileSectionLayout.setVerticalGroup(
            pnlProfileSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProfileSectionLayout.createSequentialGroup()
                .addComponent(lblProfileSection)
                .addGap(0, 0, 0)
                .addComponent(scrProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblActiveSection.setText("Active Requests");

        tblActive.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Request ID", "Request Type", "Date Raised", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrActive.setViewportView(tblActive);

        javax.swing.GroupLayout pnlActiveSectionLayout = new javax.swing.GroupLayout(pnlActiveSection);
        pnlActiveSection.setLayout(pnlActiveSectionLayout);
        pnlActiveSection.setOpaque(false);
        pnlActiveSectionLayout.setHorizontalGroup(
            pnlActiveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblActiveSection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrActive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlActiveSectionLayout.setVerticalGroup(
            pnlActiveSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActiveSectionLayout.createSequentialGroup()
                .addComponent(lblActiveSection)
                .addGap(0, 0, 0)
                .addComponent(scrActive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlProfileSection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlActiveSection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(pnlProfileSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(pnlActiveSection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        java.awt.EventQueue.invokeLater(() -> new stdDashboard());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUserMenu;
    private ManagerNavButton btnNavDashboard;
    private ManagerNavButton btnNavHistory;
    private ManagerNavButton btnNavSubmit;
    private javax.swing.JLabel lblActiveSection;
    private javax.swing.JLabel lblAppTitle;
    private javax.swing.JLabel lblPageTitle;
    private javax.swing.JLabel lblProfileSection;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblUserRole;
    private javax.swing.JPanel pnlActiveSection;
    private javax.swing.JPanel pnlBody;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlHeader;
    private LogoPanel pnlHeaderLogo;
    private javax.swing.JPanel pnlHeaderLeft;
    private javax.swing.JPanel pnlMain;
    private javax.swing.JPanel pnlPageHeader;
    private javax.swing.JPanel pnlProfileSection;
    private javax.swing.JPanel pnlRoot;
    private javax.swing.JPanel pnlSidebar;
    private javax.swing.JPanel pnlUserProfile;
    private javax.swing.JPanel pnlUserText;
    private javax.swing.JScrollPane scrActive;
    private javax.swing.JScrollPane scrProfile;
    private javax.swing.JTable tblActive;
    private javax.swing.JTable tblProfile;
    // End of variables declaration//GEN-END:variables
}
