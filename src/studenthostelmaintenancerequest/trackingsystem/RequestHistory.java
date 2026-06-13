/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studenthostelmaintenancerequest.trackingsystem;

import java.util.Arrays;

/**
 * Tracks status changes for a maintenance request (composition with request).
 *
 * @author vian
 */
public class RequestHistory {

    private String historyId;
    private String statusChanges;
    private MaintenanceRequest request;
    private int[] statusCounters;
    private String[] historyLog;
    private int logCount;

    public RequestHistory(String historyId, String statusChanges, MaintenanceRequest request) {
        this.historyId = historyId;
        this.statusChanges = statusChanges.trim();
        this.request = request;
        this.statusCounters = new int[4];
        this.historyLog = new String[50];
        this.logCount = 0;
    }

    public void recordStatus(Status newStatus) {
        String statusLabel = newStatus.toString().toUpperCase();
        String logEntry = "Request [" + request.getRequestId() + "] status changed to: " + statusLabel;

        if (logCount < historyLog.length) {
            historyLog[logCount] = logEntry;
            logCount++;
        }

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

        if (!statusChanges.contains(statusLabel)) {
            statusChanges = statusChanges + " -> " + statusLabel;
        }

        System.out.println("[History] " + logEntry);
    }

    public void viewHistory() {
        if (historyId.isEmpty() || logCount == 0) {
            System.out.println("No history recorded for this entry.");
            return;
        }

        System.out.println("==========================================");
        System.out.println("History ID     : " + historyId);
        System.out.println("Request ID     : " + request.getRequestId().toUpperCase());
        System.out.println("Status Summary : " + statusChanges);
        System.out.println("------------------------------------------");
        System.out.println("Full Change Log:");

        for (int i = 0; i < logCount; i++) {
            System.out.println("  " + (i + 1) + ". " + historyLog[i]);
        }

        System.out.println("------------------------------------------");
        System.out.println("Status Change Counts:");

        String[] statusNames = {"SUBMITTED", "IN_PROGRESS", "COMPLETED", "CANCELLED"};
        for (int i = 0; i < statusCounters.length; i++) {
            System.out.println("  " + statusNames[i] + ": " + statusCounters[i] + " time(s)");
        }

        System.out.println("==========================================");
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setStatusChanges(String statusChanges) {
        this.statusChanges = statusChanges;
    }

    public String getStatusChanges() {
        return statusChanges;
    }

    public MaintenanceRequest getRequest() {
        return request;
    }

    public int getStatusCountAt(int index) {
        if (index < 0 || index >= statusCounters.length) {
            return 0;
        }
        return statusCounters[index];
    }

    public int[] getStatusCountersCopy() {
        return Arrays.copyOf(statusCounters, statusCounters.length);
    }

    public int getLogCount() {
        return logCount;
    }
}
