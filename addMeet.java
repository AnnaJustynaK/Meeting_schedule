package meetingschedulingsystem;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;

/**
 * @author ASA5286
 */
public class addMeet extends JFrame {
    private JList<Integer> roomOptions, start, length;
    private JPanel times;
    private final JTextField name;
    private final JButton confirm, back;

    public addMeet() {
        super("Add a meeting");

        int numRooms = MeetingSchedulingSystem.rooms.size();
        Integer[] roomNumbers = new Integer[numRooms];
        for(int i = 0; i < numRooms; i++)
            roomNumbers[i] = MeetingSchedulingSystem.rooms.get(i).getNumber();

        setLayout(new BorderLayout());
        
        roomOptions = new JList<>(roomNumbers);
        roomOptions.setVisibleRowCount(3);
        roomOptions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomOptions.setSelectedIndex(0);
        
        name = new JTextField(20);
        confirm = new JButton("Confirm");
        back = new JButton("Back");
        
        JPanel buttons = new JPanel();
        JPanel buttonsLow = new JPanel();
        JPanel buttonsHigh = new JPanel();
        buttonsLow.setLayout(new BorderLayout());
        buttonsLow.add(back, BorderLayout.WEST);
        buttonsLow.add(confirm, BorderLayout.EAST);
        buttonsLow.add(name, BorderLayout.NORTH);
        buttonsHigh.add(new JLabel("Meeting name"), BorderLayout.WEST);
        buttons.add(buttonsLow, BorderLayout.CENTER);
        buttons.add(buttonsHigh, BorderLayout.NORTH);
        
        meeting[] iterMeets = MeetingSchedulingSystem.rooms.get(roomOptions.getSelectedIndex()).getMeetings();
        
        int openSlots = 0;
        for(int i = 0; i < iterMeets.length; i ++) {
            if (iterMeets[i] == null) {
                openSlots++;
            }   
        }
        Integer[] startTimes = new Integer[openSlots];
        int k = 0;
        for(int i = 0; i < startTimes.length; i++) {
            int j = i + k;
            while(j < iterMeets.length && iterMeets[j] != null) {
                k++;
                j++;
            }
            j += 9;
            if(j >= 13 && j <= 17)
                j -= 12;
            startTimes[i] = j;
        }
        
        start = new JList<>(startTimes);
        start.setVisibleRowCount(3);
        start.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        start.setSelectedIndex(0);
        
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
        
        times = new JPanel();
        times.setLayout(new GridLayout(1, 2));
        times.add(start);
        times.add(length);
        
        add(roomOptions, BorderLayout.WEST);
        add(times, BorderLayout.EAST);
        
        add(roomOptions, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        
        handler buttonHandler = new handler();
        confirm.addActionListener(buttonHandler);
        back.addActionListener(buttonHandler);
        
        //listener listListener = new listener();
        start.addListSelectionListener(new listener());
        roomOptions.addListSelectionListener(new roomListener());
        roomOptions.addListSelectionListener(new listener());
    }
    
    // Listener class for updating the button grid
    private class handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            int roomSel = roomOptions.getSelectedIndex();
            if(event.getSource() == confirm) {
                MeetingSchedulingSystem.addMeeting(name.getText(), roomSel, start.getSelectedValue(), length.getSelectedValue());
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
    private class listener implements ListSelectionListener {
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
    }
        private class roomListener implements ListSelectionListener {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                times.remove(start);
                times.remove(length);

                meeting[] iterMeets = MeetingSchedulingSystem.rooms.get(roomOptions.getSelectedIndex()).getMeetings();

                int openSlots = 0;
                for(int i = 0; i < iterMeets.length; i ++) {
                    if (iterMeets[i] == null) {
                        openSlots++;
                    }   
                }
                Integer[] startTimes = new Integer[openSlots];
                int k = 0;
                for(int i = 0; i < startTimes.length; i++) {
                    int j = i + k;
                    while(j < iterMeets.length && iterMeets[j] != null) {
                        k++;
                        j++;
                    }
                    j += 9;
                    if(j >= 13 && j <= 17)
                        j -= 12;
                    startTimes[i] = j;
                }

                start = new JList<>(startTimes);
                start.setVisibleRowCount(3);
                start.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                start.setSelectedIndex(0);
                start.addListSelectionListener(new listener());


                times.add(start);
                times.add(length);
                times.revalidate();
            }
        }
}
