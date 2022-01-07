package meetingschedulingsystem;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;

/**
 * @author ASA5286
 */
public class addPer extends JFrame {
    private JList<Integer> roomOptions;
    private JList<String> meetings;
    private JTextField firstName, lastName, phone;
    private final JButton confirm, back;

    public addPer() {
        super("Add a Person");

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
        
        JPanel pInfo = new JPanel();
        firstName = new JTextField(15);
        lastName = new JTextField(15);
        phone = new JTextField(10);
        pInfo.add(firstName, BorderLayout.WEST);
        pInfo.add(lastName, BorderLayout.CENTER);
        pInfo.add(phone, BorderLayout.EAST);
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout());
        buttons.add(pInfo, BorderLayout.NORTH);
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
            if(event.getSource() == confirm) {
                long phoneL;
                try {
                    phoneL = Long.parseLong(phone.getText());
                } catch (NumberFormatException e) {
                    System.err.println(e);
                    JOptionPane.showMessageDialog(null, "You entered an incorrect phone number\n", "ERROR", JOptionPane.ERROR_MESSAGE);
                    MeetingSchedulingSystem.mainMenu();
                    dispose();
                    return;
                }
                if(phoneL < 1000000000L || phoneL > 9999999999L) {
                    System.err.println("Not a valid phone number");
                    JOptionPane.showMessageDialog(null, "You entered an incorrect phone number\n", "ERROR", JOptionPane.ERROR_MESSAGE);
                    MeetingSchedulingSystem.mainMenu();
                    dispose();
                    return;
                }
                
                person personToAdd = MeetingSchedulingSystem.addParticipant(firstName.getText(), lastName.getText(), phoneL);
                
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
                
                if(targetMeeting == null || !MeetingSchedulingSystem.addToMeeting(personToAdd, targetMeeting))
                        JOptionPane.showMessageDialog(null, "Could not add person to meeting", "ERROR", JOptionPane.ERROR_MESSAGE);
                MeetingSchedulingSystem.mainMenu();
                dispose();
            }
            
            if(event.getSource() == back) {
                MeetingSchedulingSystem.mainMenu();
                dispose();
            }
        }
    }
    
    // Listener class for updating the button grid
   /* private class listener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent event) {
            times.remove(length);

            meeting[] iterMeets = MeetingSchedulingSystem.rooms.get(roomOptions.getSelectedIndex()).getMeetings();
            
            int startTime = start.getSelectedValue();
            if(startTime >= 1 && startTime <= 5)
                startTime += 12;
            startTime -= 8; // Would be -9 but we need the hour after the selected meeting
            int possibleLengths = 0;
            while(startTime < iterMeets.length && iterMeets[startTime] == null) {
                startTime++;
                possibleLengths++;
            }
            Integer[] lengthList = new Integer[possibleLengths + 1]; // possibleLengths + 1 because the above loop doesn't count the last hour
            for(int i = 0; i < lengthList.length; i++)
                lengthList[i] = i + 1;
            
            length = new JList<>(lengthList);
            length.setVisibleRowCount(3);
            length.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            length.setSelectedIndex(0);
            
            times.add(length, BorderLayout.EAST);
            
            times.revalidate(); // update the JPanel
        }
    }*/
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


                add(meetings, BorderLayout.EAST);
                revalidate();
            }
        }
}
