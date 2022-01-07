package meetingschedulingsystem;

/**
 * @author ASA5286
 */
public class room {
    private int number;
    private meeting[] meetings;

    public room(int number) {
        this.number = number;
        meetings = new meeting[8];
    }
    
    public meeting[] getMeetings() {
        return meetings;
    }
    
    public meeting findMeeting(long time) {
        for(int i = 0; i < meetings.length; i++) {
            if(meetings[i] != null && meetings[i].getTime() == time)
                return meetings[i];
        }
        return null;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    public void addMeeting(String name, int time, int duration) {
        boolean fit = true;
        if(time >= 1 && time <= 5)
            time += 12;
        if(time >= 9 && time <= 17 && time + duration <=17) {
            for(int i = time - 9; i < time + duration - 9; i++) {
                if(meetings[i] != null)
                    fit = false;
            }
            if(fit) {
            meeting temp = new meeting(name, time, duration);
            for(int i = time - 9; i < time + duration - 9; i++)
                meetings[i] = temp;
            }else {
                System.err.printf("Meeting conflicts with another meeting.\n");
            }
        }else {
            System.err.printf("Meetings must begin and end on the hour between 9 and 5.\n");
        }
    }
    
    public void delMeeting(meeting delMeeting) {
        int endIndex = delMeeting.getTime() - 9 + delMeeting.getDuration();
        for(int i = delMeeting.getTime() - 9; i < endIndex; i++) {
            System.out.printf("checking meetings[%d]\n", i);
            if(meetings[i] != null && !meetings[i].getPeople().isEmpty()) {
                System.err.println("Meeting is non-empty and cannot be deleted.");
                return;
            }
        }
        System.out.printf("deleting from meetings[%d] to meetings[%d]\n", delMeeting.getTime() - 9, endIndex);
        for(int i = delMeeting.getTime() - 9; i < endIndex; i++) {
            System.out.printf("meetings[%d] = null;\n", i);
            meetings[i] = null;
        }
    }
    
    public void printMeetings() {
            for (int i = 0; i < meetings.length; i++) {
                if (meetings[i] != null && (0 == i || meetings[i] != meetings[i-1])) {
                    int tempEnd = meetings[i].getTime() + meetings[i].getDuration();
                    if(tempEnd > 12)
                        tempEnd -= 12;
                    int tempTime = meetings[i].getTime();
                    if(tempTime > 12)
                        tempTime -= 12;
                    System.out.printf("\t%d-%d Meeting: %s\n", tempTime, tempEnd, meetings[i].getName());
                    meetings[i].printPeople();
                }
            }
    }
    
    public boolean isEmpty() {
        for (meeting Meeting : meetings) {
            if (Meeting != null)
                return false;
        }
        return true;
    }
}
