package studenthostelmaintenancerequest.trackingsystem.gui.staff;

import studenthostelmaintenancerequest.trackingsystem.DatabaseException;
import studenthostelmaintenancerequest.trackingsystem.ManagerRoomDetails;
import studenthostelmaintenancerequest.trackingsystem.ManagerService;
import studenthostelmaintenancerequest.trackingsystem.Staff;
import studenthostelmaintenancerequest.trackingsystem.StaffService;
import studenthostelmaintenancerequest.trackingsystem.gui.common.LogoPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.ManagerNavButton;
import studenthostelmaintenancerequest.trackingsystem.gui.common.ManagerRoomInformationPanel;
import studenthostelmaintenancerequest.trackingsystem.gui.common.PlaceholderTextField;
import studenthostelmaintenancerequest.trackingsystem.gui.common.UIHelper;

/**
 * Staff view room details by request ID.
 */
public class ViewRoomDetails extends javax.swing.JFrame {

    private Staff staff;
    private PlaceholderTextField txtSearch;
    private ManagerRoomInformationPanel pnlRoomInformation;

    public ViewRoomDetails() {
        initComponents();
        customizeForm();
    }

    private void customizeForm() {
        staff = StaffService.requireStaff(this);
        if (staff == null) {
            return;
        }

        UIHelper.configureStaffShell(this,
                pnlHeader, pnlSidebar, pnlMain, pnlPageHeader,
                lblAppTitle, lblPageTitle, pnlHeaderLogo,
                pnlUserProfile, lblUserName, lblUserRole, btnUserMenu,
                btnNavDashboard, btnNavManageRequests, btnNavViewRoom, btnNavHistory,
                UIHelper.StaffNavPage.VIEW_ROOM);
        wireNavigation();

        txtSearch = new PlaceholderTextField("Search Request by ID");
        javax.swing.JPanel pnlSearchField = UIHelper.createSearchField(txtSearch);
        UIHelper.layoutManagerViewRoomToolbar(pnlToolbar, pnlSearchField);

        javax.swing.JPanel pnlHint = UIHelper.createManagerViewRoomHint();
        pnlRoomInformation = new ManagerRoomInformationPanel();
        UIHelper.layoutManagerViewRoomContent(pnlMain, pnlToolbar, pnlHint, pnlRoomInformation);

        txtSearch.addActionListener(e -> performSearch());

        UIHelper.showManagerFrame(this);
        javax.swing.SwingUtilities.invokeLater(() -> {
            pnlMain.revalidate();
            pnlMain.repaint();
        });
    }

    private void performSearch() {
        String requestId = txtSearch.getInputText().trim();
        if (requestId.isEmpty()) {
            pnlRoomInformation.clearDetails();
            return;
        }
        try {
            ManagerRoomDetails details = StaffService.lookupRoomDetails(staff, requestId);
            if (details == null) {
                pnlRoomInformation.clearDetails();
                return;
            }
            pnlRoomInformation.setDetails(
                    details.requestId,
                    details.roomNumber,
                    ManagerService.formatBlockDisplay(details.roomNumber, details.placeName),
                    details.studentName,
                    details.requestType);
        } catch (DatabaseException ex) {
            UIHelper.showDatabaseError(this, ex);
            pnlRoomInformation.clearDetails();
        }
        pnlMain.revalidate();
        pnlMain.repaint();
    }

    private void wireNavigation() {
        btnNavDashboard.addActionListener(e -> UIHelper.navigateTo(this, new staffDashboard()));
        btnNavManageRequests.addActionListener(e -> UIHelper.navigateTo(this, new ManageRequestPage()));
        btnNavViewRoom.addActionListener(e -> { /* current page */ });
        btnNavHistory.addActionListener(e -> UIHelper.navigateTo(this, new viewRequestHistoryPage()));
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
        btnNavManageRequests = new ManagerNavButton("Manage Request");
        btnNavViewRoom = new ManagerNavButton("View Room Details");
        btnNavHistory = new ManagerNavButton("View Request History");
        pnlContent = new javax.swing.JPanel();
        pnlPageHeader = new javax.swing.JPanel();
        lblPageTitle = new javax.swing.JLabel();
        pnlMain = new javax.swing.JPanel();
        pnlToolbar = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("View Room Details");

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
        lblUserName.setText("Staff");

        lblUserRole.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblUserRole.setText("STAFF");

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
        pnlSidebar.add(btnNavViewRoom);
        pnlSidebar.add(btnNavHistory);

        pnlBody.add(pnlSidebar, java.awt.BorderLayout.WEST);

        pnlContent.setLayout(new java.awt.BorderLayout());

        lblPageTitle.setText("View Room Details");

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

        javax.swing.GroupLayout pnlMainLayout = new javax.swing.GroupLayout(pnlMain);
        pnlMain.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(pnlToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(32, 32, 32))
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMainLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(pnlToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        java.awt.EventQueue.invokeLater(() -> new ViewRoomDetails());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUserMenu;
    private ManagerNavButton btnNavDashboard;
    private ManagerNavButton btnNavHistory;
    private ManagerNavButton btnNavManageRequests;
    private ManagerNavButton btnNavViewRoom;
    private javax.swing.JLabel lblAppTitle;
    private javax.swing.JLabel lblPageTitle;
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
    private javax.swing.JPanel pnlToolbar;
    private javax.swing.JPanel pnlUserProfile;
    private javax.swing.JPanel pnlUserText;
    // End of variables declaration//GEN-END:variables
}
