package meetingschedulingsystem;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 * @author ASA5286
 */
public class menu extends JFrame {
    private JButton select;
    private ButtonGroup buttonGroup;
    private JRadioButton addRoom, delRoom, addMeet, delMeet, addPer, delPart, display;
    
    private static <T> String[] toStringArray(ArrayList<T> list) {
        String[] returnArray = new String[list.size()];
        ArrayList<String> returnList = new ArrayList<>();
        
        for(T item : list)
            returnList.add(item.toString());
        
        returnArray = returnList.toArray(returnArray);
        
        return returnArray;
    }
    
    private static void addRoom() {
        int num;
        String snum;
        boolean roomExists = false;
        do {
            snum = JOptionPane.showInputDialog("Enter room number");
            num = Integer.parseInt(snum);
        }while(num < 0);
        for(room Room : MeetingSchedulingSystem.rooms) {
            if(Room.getNumber() == num)
                roomExists = true;
        }
        if(!roomExists)
            MeetingSchedulingSystem.rooms.add(new room(num));
        else
            System.err.printf("Room %d already exists.\n", num);
    }
    
    public menu() {
        super("Scheduler");
        
        setLayout(new GridLayout(8, 1));
        
        buttonGroup = new ButtonGroup();
        addRoom = new JRadioButton("Add room", true);
        delRoom = new JRadioButton("Delete room", false);
        addMeet = new JRadioButton("Add meeting to room", false);
        delMeet = new JRadioButton("Delete meeting from room", false);
        addPer = new JRadioButton("Add person to meeting", true);
        delPart = new JRadioButton("Delete person from meeting", false);
        //addPToM = new JRadioButton("Add participant to meeting", false);
        //delPFrM = new JRadioButton("Delete participant from meeting", false);
        display = new JRadioButton("Display Schedule", false);
        buttonGroup.add(addRoom);
        buttonGroup.add(delRoom);
        buttonGroup.add(addMeet);
        buttonGroup.add(delMeet);
        buttonGroup.add(addPer);
        buttonGroup.add(delPart);
        //buttonGroup.add(addPToM);
        //buttonGroup.add(delPFrM);
        buttonGroup.add(display);
                
        select = new JButton("Confirm");

        add(addRoom);
        add(delRoom);
        add(addMeet);
        add(delMeet);
        add(addPer);
        add(delPart);
        //add(addPToM);
        //add(delPFrM);
        add(display);
        add(select);
        
        selectionHandler handler = new selectionHandler();
        select.addActionListener(handler);
    }
    
    private class selectionHandler implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent event) {
            if(event.getSource() == select) {
                if(addRoom.isSelected())
                    addRoom();
                if(delRoom.isSelected()) {
                    delRoom delRoomP = new delRoom();
                    delRoomP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    delRoomP.setSize(250, 300);
                    delRoomP.setVisible(true);
                    dispose();
                }
                if(addMeet.isSelected()) {
                    addMeet addMeetP = new addMeet();
                    addMeetP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    addMeetP.setSize(250, 300);
                    addMeetP.setVisible(true);
                    dispose();
                }
                if(delMeet.isSelected()) {
                    delMeet delMeetP = new delMeet();
                    delMeetP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    delMeetP.setSize(250, 300);
                    delMeetP.setVisible(true);
                    dispose();
                }
                if(addPer.isSelected()) {
                    if(!MeetingSchedulingSystem.rooms.isEmpty()) {
                        addPer addPerP = new addPer();
                        addPerP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        addPerP.setSize(500, 300);
                        addPerP.setVisible(true);
                        dispose();
                    } else
                        JOptionPane.showMessageDialog(null, "There are no rooms", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                if(delPart.isSelected()) {
                    if(!MeetingSchedulingSystem.rooms.isEmpty()) {
                        delPer delPerP = new delPer();
                        delPerP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        delPerP.setSize(500, 300);
                        delPerP.setVisible(true);
                        dispose();
                    } else
                        JOptionPane.showMessageDialog(null, "There are no rooms", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                if(display.isSelected()) {
                    System.out.println("rooms is not empty");
                    display displayP = new display();
                    displayP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    displayP.setSize(500, 300);
                    displayP.setVisible(true);
                    dispose();
                }
            }
        }
    }
}
