package meetingschedulingsystem;

/**
 * @author ASA5286
 */
public class person {
    private String first;
    private String last;
    private long phone;
    private meeting meetings[];
    
    public person(String first, String last, long phone) {
        this.first = first;
        this.last = last;
        this.phone = phone;
        meetings = new meeting[8];
    }
    
    public person() {
        this("null", "null", 0);
    }
    
    public meeting[] getMeetings() {
        return meetings;
    }
    
    public boolean meetingFits(meeting Meeting) {
        boolean fits = true;
        int start = Meeting.getTime() - 9;
        int end = start + Meeting.getDuration();
        for(int i = start; i < end; i++) {
            if(this.meetings[i] != null) {
                System.err.println("Meeting conflicts with person's schedule.");
                fits = false;
            }
        }
        return fits;
    }
    
    public void addMeeting(meeting Meeting) {
        int start = Meeting.getTime() - 9;
        int end = start + Meeting.getDuration();        
        for(int i = start; i < end; i++)
            this.meetings[i] = Meeting;
    }
    
    public void delMeeting(int time) {
        int start;
        time -= 9;
        if(meetings[time] != null)
            start = meetings[time].getTime() - 9;
        else {
            System.err.println("Person does not have a meeting at this time.");
            return;
        }
        int end = start + meetings[start].getDuration();
        for(int i = start; i < end; i++)
            meetings[i] = null;
    }
    
    public boolean equals(person Person) {
        if(!this.getFirst().equals(Person.getFirst()))
            return false;
        if(!this.getLast().equals(Person.getLast()))
            return false;
        return true;
    }
    
    public boolean noMeetings() {
        for(meeting Meeting : meetings) {
            if(Meeting != null)
                return false;
        }
        return true;
    }
    
    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }
}
