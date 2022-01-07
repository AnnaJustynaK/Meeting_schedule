package meetingschedulingsystem;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author ASA5286
 */
public class display extends JFrame {
    private JButton select, back, all;
    private JList<String> people, times;
    private JList<Integer> rooms;
    private JPanel lists;
    
    public display() {
        super("Scheduler");
        
        setLayout(new BorderLayout());
        
        int numRooms = MeetingSchedulingSystem.rooms.size();
        Integer[] roomNumbers = new Integer[numRooms];
        for(int i = 0; i < numRooms; i++)
            roomNumbers[i] = MeetingSchedulingSystem.rooms.get(i).getNumber();
        
        rooms = new JList<>(roomNumbers);
        rooms.setVisibleRowCount(3);
        rooms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rooms.setSelectedIndex(0);
        
        ArrayList<person> iterPeople = MeetingSchedulingSystem.people;
        
        String[] peopleStrings = new String[iterPeople.size()];
        for(int i = 0; i < peopleStrings.length; i++) {
            peopleStrings[i] = String.format("%s %s : %d", iterPeople.get(i).getFirst(), iterPeople.get(i).getLast(), iterPeople.get(i).getPhone());
        }
        
        people = new JList<>(peopleStrings);
        people.setVisibleRowCount(3);
        people.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        String[] timeArray = {"9 - 10", "10 - 11", "11 - 12", "12 - 1", "1 - 2", "2 - 3", "3 - 4", "4 - 5"};
        times = new JList<>(timeArray);
        times.setVisibleRowCount(3);
        times.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        select = new JButton("Confirm");
        back = new JButton("Back");
        all = new JButton("Everything");
        
        JPanel buttons = new JPanel();
        buttons.add(back, BorderLayout.WEST);
        buttons.add(select, BorderLayout.CENTER);
        buttons.add(all, BorderLayout.EAST);
        
        lists = new JPanel();
        lists.setLayout(new GridLayout(1, 3));
        lists.add(rooms);
        lists.add(people);
        lists.add(times);
        
        add(lists, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        
        selectionHandler handler = new selectionHandler();
        select.addActionListener(handler);
        all.addActionListener(handler);
        back.addActionListener(handler);
        
        listener Listener = new listener();
        people.addListSelectionListener(Listener);
        rooms.addListSelectionListener(Listener);
        times.addListSelectionListener(Listener);
    }
    
    private class selectionHandler implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent event) {
            if(event.getSource() == select) {
                if(rooms.getSelectedIndex() != -1) {
                    String output = "";
                    room r = MeetingSchedulingSystem.rooms.get(rooms.getSelectedIndex());
                    meeting[] meetings = r.getMeetings();
                    for (int i = 0; i < meetings.length; i++) {
                        if (meetings[i] != null && (0 == i || meetings[i] != meetings[i-1])) {
                            int tempEnd = meetings[i].getTime() + meetings[i].getDuration();
                            if(tempEnd > 12)
                                tempEnd -= 12;
                            int tempTime = meetings[i].getTime();
                            if(tempTime > 12)
                                tempTime -= 12;
                            output += String.format("    %d-%d Meeting: %s\n", tempTime, tempEnd, meetings[i].getName());
                            for(person p : meetings[i].getPeople()) {
                                output += String.format("        * %s %s Phone: %d\n", p.getFirst(), p.getLast(), p.getPhone());
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, output, Integer.toString(r.getNumber()), JOptionPane.PLAIN_MESSAGE);
                }
                if(people.getSelectedIndex() != -1) {
                    String output = "";
                    person p = MeetingSchedulingSystem.people.get(people.getSelectedIndex());
                    meeting[] meetings = p.getMeetings();
                    for (int i = 0; i < meetings.length; i++) {
                        if (meetings[i] != null && (0 == i || meetings[i] != meetings[i-1])) {
                            int tempEnd = meetings[i].getTime() + meetings[i].getDuration();
                            if(tempEnd > 12)
                                tempEnd -= 12;
                            int tempTime = meetings[i].getTime();
                            if(tempTime > 12)
                                tempTime -= 12;
                            output += String.format("    %d-%d Meeting: %s\n", tempTime, tempEnd, meetings[i].getName());
                        }
                    }
                    JOptionPane.showMessageDialog(null, output, p.getFirst() + " " + p.getLast(), JOptionPane.PLAIN_MESSAGE);
                }
                if(times.getSelectedIndex() != -1) {
                    int time = (times.getSelectedIndex() >= 4 ? times.getSelectedIndex() - 3 : times.getSelectedIndex() + 9);
                    String output = String.format("Meetings in the %s block\n", times.getSelectedValue());
                    for(room r : MeetingSchedulingSystem.rooms) {
                        meeting[] meetings = r.getMeetings();
                        int i = times.getSelectedIndex();
                        if (meetings[i] != null) {
                            int tempEnd = meetings[i].getTime() + meetings[i].getDuration();
                            if(tempEnd > 12)
                                tempEnd -= 12;
                            int tempTime = meetings[i].getTime();
                            if(tempTime > 12)
                                tempTime -= 12;
                            output += String.format("Room %d: %d-%d Meeting: %s\n", r.getNumber(), tempTime, tempEnd, meetings[i].getName());
                            for(person p : meetings[i].getPeople()) {
                                output += String.format("        * %s %s Phone: %d\n", p.getFirst(), p.getLast(), p.getPhone());
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, output, times.getSelectedValue(), JOptionPane.PLAIN_MESSAGE);
                }
            }
            
            if(event.getSource() == all) {
                String output = "";
                for(room r : MeetingSchedulingSystem.rooms) {
                    output = output + r.getNumber() + ":\n";
                    meeting[] meetings = r.getMeetings();
                    for (int i = 0; i < meetings.length; i++) {
                        if (meetings[i] != null && (0 == i || meetings[i] != meetings[i-1])) {
                            int tempEnd = meetings[i].getTime() + meetings[i].getDuration();
                            if(tempEnd > 12)
                                tempEnd -= 12;
                            int tempTime = meetings[i].getTime();
                            if(tempTime > 12)
                                tempTime -= 12;
                            output += String.format("    %d-%d Meeting: %s\n", tempTime, tempEnd, meetings[i].getName());
                            for(person p : meetings[i].getPeople()) {
                                output += String.format("        * %s %s Phone: %d\n", p.getFirst(), p.getLast(), p.getPhone());
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, output, "Schedule", JOptionPane.PLAIN_MESSAGE);
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
            if(event.getSource() != people)
                people.clearSelection();
                
            if(event.getSource() != rooms)
                rooms.clearSelection();
                
            if(event.getSource() != times)
                times.clearSelection();
            
            revalidate(); // update the JPanel
        }
    }
}
