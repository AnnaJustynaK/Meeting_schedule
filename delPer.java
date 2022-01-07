package meetingschedulingsystem;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * @author ASA5286
 */
public class delPer extends JFrame {
    private JList<Integer> roomOptions;
    private JList<String> meetings, people;
    private final JButton confirm, back;

    public delPer() {
        super("Delete a Person");

        int numRooms = MeetingSchedulingSystem.rooms.size();
        Integer[] roomNumbers = new Integer[numRooms];
        for(int i = 0; i < numRooms; i++)
            roomNumbers[i] = MeetingSchedulingSystem.rooms.get(i).getNumber();

        setLayout(new BorderLayout());
        
        roomOptions = new JList<>(roomNumbers);
        roomOptions.setVisibleRowCount(3);
        roomOptions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomOptions.setSelectedIndex(0);
        
        confirm = new JButton("Confirm");
        back = new JButton("Back");
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout());
        buttons.add(back, BorderLayout.WEST);
        buttons.add(confirm, BorderLayout.EAST);
        
        meeting[] iterMeets = MeetingSchedulingSystem.rooms.get(roomOptions.getSelectedIndex()).getMeetings();
        
        int numMeetings = 0;
        for(int i = 0; i < iterMeets.length; i ++) {
            if (iterMeets[i] != null) {
                numMeetings++;
                while(i + 1 < iterMeets.length && iterMeets[i+1] == iterMeets[i])
                    i++;
            }   
        }
        String[] meetingStrings = new String[numMeetings];
        int k = 0;
        for(int i = 0; i < meetingStrings.length; i++) {
            int j = i + k;
            while(iterMeets[j] == null || (j + 1 < iterMeets.length && iterMeets[j+1] == iterMeets[j])) {
                j++;
                k++;
            }
            int startTime = iterMeets[j].getTime();
            int endTime = iterMeets[j].getDuration() + startTime;
            startTime = (startTime >= 13 ? startTime - 12 : startTime);
            meetingStrings[i] = String.format("%d - %d : %s", startTime, (endTime >= 13 ? endTime - 12 : endTime), iterMeets[j].getName());
        }
        
        meetings = new JList<>(meetingStrings);
        meetings.setVisibleRowCount(3);
        meetings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        meetings.setSelectedIndex(0);
        
        for(meeting m : iterMeets)
            System.out.printf("%s\n", m);
        System.out.printf("ArrayList<person> iterPeople = iterMeets[%d].getPeople();\n", meetings.getSelectedIndex());
        int index = meetings.getSelectedIndex();
        ArrayList<person> iterPeople;
        if(index != -1) {
            while(index < iterMeets.length && iterMeets[index] == null)
                index++;
            if(index < iterMeets.length)
                iterPeople = iterMeets[index].getPeople();
            else
                iterPeople = null;
        } else {
            iterPeople = null;
        }
        String[] peopleStrings;
        if(iterPeople != null) {
            peopleStrings = new String[iterPeople.size()];
            for(int i = 0; i < peopleStrings.length; i++) {
                peopleStrings[i] = String.format("%s %s : %d", iterPeople.get(i).getFirst(), iterPeople.get(i).getLast(), iterPeople.get(i).getPhone());
            }
        } else {
            peopleStrings = new String[0];
        }
        
        people = new JList<>(peopleStrings);
        people.setVisibleRowCount(3);
        people.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        people.setSelectedIndex(0);
        
        add(roomOptions, BorderLayout.WEST);
        add(meetings, BorderLayout.CENTER);
        add(people, BorderLayout.EAST);
        add(buttons, BorderLayout.SOUTH);
        
        handler buttonHandler = new handler();
        confirm.addActionListener(buttonHandler);
        back.addActionListener(buttonHandler);
        
        //listener listListener = new listener();
        meetings.addListSelectionListener(new listener());
        roomOptions.addListSelectionListener(new roomListener());
        roomOptions.addListSelectionListener(new listener());
    }
    
    // Listener class for updating the button grid
    private class handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(event.getSource() == confirm) {
                meeting[] iterMeetings = MeetingSchedulingSystem.rooms.get(roomOptions.getSelectedIndex()).getMeetings();
                meeting targetMeeting = null;
                for(meeting m : iterMeetings){
                    if(m != null) {
                        int startTime = m.getTime();
                        int endTime = m.getTime() + m.getDuration();
                        startTime = (startTime >= 13 ? startTime - 12 : startTime);
                        if(meetings.getSelectedValue().equals(String.format("%d - %d : %s", startTime, (endTime >= 13 ? endTime - 12 : endTime), m.getName()))) {
                            targetMeeting = m;
                            //delTime = (startTime - 9 < 0 ? startTime + 3 : startTime - 9);
                            System.out.printf("%d - %d : %s ", startTime, (endTime >= 13 ? endTime - 12 : endTime), m.getName());
                            System.out.printf("equal to %s\n", meetings.getSelectedValue());
                        } else {
                            System.out.printf("%d - %d : %s ", startTime, (endTime >= 13 ? endTime - 12 : endTime), m.getName());
                            System.out.printf("not equal to %s\n", meetings.getSelectedValue());
                        }
                    }
                }
                person targetPerson;
                if(targetMeeting != null) {
                    ArrayList<person> iterPeople = targetMeeting.getPeople();
                    targetPerson = null;
                    for(person p : iterPeople) {
                        if(people.getSelectedValue().equals(String.format("%s %s : %d", p.getFirst(), p.getLast(), p.getPhone())))
                            targetPerson = p;
                    }
                } else {
                    targetPerson = null;
                }
                if(targetPerson != null)
                    MeetingSchedulingSystem.delFromMeeting(MeetingSchedulingSystem.rooms.get(roomOptions.getSelectedIndex()), targetMeeting, targetPerson);
                if(targetPerson != null && targetPerson.noMeetings())
                    MeetingSchedulingSystem.delParticipant(targetPerson);
                else
                    JOptionPane.showMessageDialog(null, "Could not delete person", "ERROR", JOptionPane.ERROR_MESSAGE);
                MeetingSchedulingSystem.mainMenu();
                dispose();
            }
            
            if(event.getSource() == back) {
                MeetingSchedulingSystem.mainMenu();
                dispose();
            }
        }
    }
    
    private class listener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent event) {
            remove(people);

            meeting[] iterMeetings = MeetingSchedulingSystem.rooms.get(roomOptions.getSelectedIndex()).getMeetings();
            for(meeting m : iterMeetings)
                System.out.printf("%s\n", m);
            System.out.printf("ArrayList<person> iterPeople = iterMeetings[%d].getPeople()\n", meetings.getSelectedIndex());
            int index = meetings.getSelectedIndex();
            ArrayList<person> iterPeople;
            if(index != -1) {
                while(index < iterMeetings.length && iterMeetings[index] == null)
                    index++;
                if(index < iterMeetings.length)
                    iterPeople = iterMeetings[index].getPeople();
                else
                    iterPeople = null;
            } else {
                iterPeople = null;
            }
            String[] peopleStrings;
            if(iterPeople != null) {
                peopleStrings = new String[iterPeople.size()];
                for(int i = 0; i < peopleStrings.length; i++) {
                    peopleStrings[i] = String.format("%s %s : %d", iterPeople.get(i).getFirst(), iterPeople.get(i).getLast(), iterPeople.get(i).getPhone());
            }
            } else {
                peopleStrings = new String[0];
            }

            people = new JList<>(peopleStrings);
            people.setVisibleRowCount(3);
            people.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            people.setSelectedIndex(0);
            
            add(people, BorderLayout.EAST);
            
            revalidate(); // update the JPanel
        }
    }
    
        private class roomListener implements ListSelectionListener {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                remove(meetings);

                meeting[] iterMeets = MeetingSchedulingSystem.rooms.get(roomOptions.getSelectedIndex()).getMeetings();
        
                int numMeetings = 0;
                for(int i = 0; i < iterMeets.length; i ++) {
                    if (iterMeets[i] != null) {
                        numMeetings++;
                        while(i + 1 < iterMeets.length && iterMeets[i+1] == iterMeets[i])
                            i++;
                    }   
                }
                String[] meetingStrings = new String[numMeetings];
                int k = 0;
                for(int i = 0; i < meetingStrings.length; i++) {
                    int j = i + k;
                    while(iterMeets[j] == null || (j + 1 < iterMeets.length && iterMeets[j+1] == iterMeets[j])) {
                        j++;
                        k++;
                    }
                    int startTime = iterMeets[j].getTime();
                    int endTime = iterMeets[j].getDuration() + startTime;
                    startTime = (startTime >= 13 ? startTime - 12 : startTime);
                    meetingStrings[i] = String.format("%d - %d : %s", startTime, (endTime >= 13 ? endTime - 12 : endTime), iterMeets[j].getName());
                }

                meetings = new JList<>(meetingStrings);
                meetings.setVisibleRowCount(3);
                meetings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                meetings.setSelectedIndex(0);
                meetings.addListSelectionListener(new listener());


                add(meetings, BorderLayout.CENTER);
                revalidate();
            }
        }
}
