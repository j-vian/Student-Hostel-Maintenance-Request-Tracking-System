/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

/**
 *
 * @author vian
 */
public class RequestHistory {
    //data variables
    private String historyId;
    private String statusChanges;
    private MaintenanceRequest request;
    private int[] statusCounters; //primitive array
    private String[] historyLog;
    private int logCount;
    
    //constructor
    public RequestHistory(String historyId, String statusChanges, MaintenanceRequest request) {
        this.historyId = historyId;
        this.statusChanges = statusChanges.trim();
        this.request = request;
        this.statusCounters = new int[4];
        this.historyLog = new String[50];
        this.logCount = 0;
    }
    
    //addHistory method
    public void addHistory(Status newStatus) {
        String statusLabel = newStatus.toString().toUpperCase();
        String logEntry = "Request [" + request.getRequestId() + "] status changed to: " + statusLabel;
        
        //to check if historyLog array has space before adding
        if (logCount < historyLog.length) {
            historyLog[logCount] = logEntry;
            logCount++;
        }
        
        //Update the statusCounters array based on which status was applied
        switch (newStatus) {
            case SUBMITTED:
                statusCounters[0]++;
                break;
            case IN_PROGRESS:
                statusCounters[1]++;
                break;
            case COMPLETED:
                statusCounters[2]++;
                break;
            case CANCELLED:
                statusCounters[3]++;
                break;
        }
        
        //Update the actual request status
        request.setStatus(newStatus);
        
        if (!statusChanges.contains(statusLabel)) {
            statusChanges = statusChanges + " -> " + statusLabel;
         }
        
        System.out.println("[History] " + logEntry);
    }
    
    //viewHistory method
    public void viewHistory() {
        //String method: isEmpty()
        if (historyId.isEmpty() || logCount == 0) {
            System.out.println("No history recorded for this entry.");
            return;
        }
        
        System.out.println("==========================================");
        System.out.println("History ID     : " + historyId);

        // String method: toUpperCase() on requestId for consistent display
        System.out.println("Request ID     : " + request.getRequestId().toUpperCase());
        System.out.println("Status Summary : " + statusChanges);
        System.out.println("------------------------------------------");
        System.out.println("Full Change Log:");

        // Repetition statement: loop through all recorded log entries
        for (int i = 0; i < logCount; i++) {
            System.out.println("  " + (i + 1) + ". " + historyLog[i]);
        }

        System.out.println("------------------------------------------");
        System.out.println("Status Change Counts:");

        // Repetition statement: loop through primitive array to display counts
        // Rubric criterion: Array of Primitive traversal
        String[] statusNames = {"SUBMITTED", "IN_PROGRESS", "COMPLETED", "CANCELLED"};
        for (int i = 0; i < statusCounters.length; i++) {
            System.out.println("  " + statusNames[i] + ": " + statusCounters[i] + " time(s)");
        }

        System.out.println("==========================================");
    }
    
    //setters and getters
    public void setHistoryId(String historyId) { this.historyId = historyId; }
    public String getHistoryId() { return historyId; }
    public void setStatusChanges(String statusChanges) { this.statusChanges = statusChanges; }
    public String getStatusChanges() { return statusChanges; }
    public MaintenanceRequest getRequest() { return request; }
    public int[] getStatusCounters() { return statusCounters; }
    public int getLogCount() { return logCount; }
}