package studenthostelmaintenancerequest.trackingsystem;

/**
 * Room and request details shown on the manager View Room Details screen.
 */
public final class ManagerRoomDetails {

    // instance fields for class state
    public final String requestId;
    public final String roomNumber;
    public final String placeName;
    public final String studentName;
    public final String requestType;

    // construct object with initial state
    public ManagerRoomDetails(String requestId, String roomNumber, String placeName,
            String studentName, String requestType) {
        this.requestId = requestId;
        this.roomNumber = roomNumber;
        this.placeName = placeName;
        this.studentName = studentName;
        this.requestType = requestType;
    }
}
