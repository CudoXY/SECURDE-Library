package library.domain;

import java.sql.Timestamp;

/**
 * Created by Brandon on 7/31/2017.
 */

public class Timeslot {
    private int roomId;
    private int time;
    private User reservedBy;

    public User getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(User reservedBy) {
        this.reservedBy = reservedBy;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString()
    {
        return reservedBy == null ? "null" : reservedBy.getId() + "";
    }
}
