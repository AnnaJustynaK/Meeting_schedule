/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meetingschedulingsystem;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;

/**
 * @author ASA5286
 */
public class delMeet extends JFrame {
    private JList<Integer> roomOptions;
    private JList<String> meetings;
    private final JButton confirm, back;

    public delMeet() {
        super("Delete a meeting");

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
                
        add(roomOptions, BorderLayout.CENTER);
        add(meetings, BorderLayout.EAST);
        add(buttons, BorderLayout.SOUTH);
        
        handler buttonHandler = new handler();
        confirm.addActionListener(buttonHandler);
        back.addActionListener(buttonHandler);
        
        //listener listListener = new listener();
        //meetings.addListSelectionListener(new listener());
        roomOptions.addListSelectionListener(new roomListener());
        //roomOptions.addListSelectionListener(new listener());
    }
    
    // Listener class for updating the button grid
    private class handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            int roomSel = roomOptions.getSelectedIndex();
            if(event.getSource() == confirm) {
                meeting[] iterMeetings = MeetingSchedulingSystem.rooms.get(roomOptions.getSelectedIndex()).getMeetings();
                int delTime = -1;
                for(meeting m : iterMeetings){
                    if(m != null) {
                        int startTime = m.getTime();
                        int endTime = m.getTime() + m.getDuration();
                        startTime = (startTime >= 13 ? startTime - 12 : startTime);
                        if(meetings.getSelectedValue().equals(String.format("%d - %d : %s", startTime, (endTime >= 13 ? endTime - 12 : endTime), m.getName()))) {
                            delTime = (startTime - 9 < 0 ? startTime + 3 : startTime - 9);
                            System.out.printf("%d - %d : %s ", startTime, (endTime >= 13 ? endTime - 12 : endTime), m.getName());
                            System.out.printf("equal to %s\n", meetings.getSelectedValue());
                        } else {
                            System.out.printf("%d - %d : %s ", startTime, (endTime >= 13 ? endTime - 12 : endTime), m.getName());
                            System.out.printf("not equal to %s\n", meetings.getSelectedValue());
                        }
                    }
                }
                System.out.printf("passing delMeetin.(%d, %d)\n", roomSel, delTime);
                if(MeetingSchedulingSystem.rooms.get(roomSel).getMeetings()[delTime].getPeople().isEmpty())
                    MeetingSchedulingSystem.delMeeting(roomSel, delTime);
                else
                    JOptionPane.showMessageDialog(null, "Meeting is non-emtpy", "ERROR", JOptionPane.ERROR_MESSAGE);
                MeetingSchedulingSystem.mainMenu();
                dispose();
            }
            
            if(event.getSource() == back) {
                MeetingSchedulingSystem.mainMenu();
                dispose();
            }
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
                    while(iterMeets[j] == null || (j + 1 < meetingStrings.length && iterMeets[j+1] == iterMeets[j])) {
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


                add(meetings, BorderLayout.EAST);
                revalidate();
            }
        }
}
