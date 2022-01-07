package meetingschedulingsystem;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;

/**
 * @author ASA5286
 */
public class delRoom extends JFrame{
    private final JList<Integer> options;
    private final JButton confirm, back;

    public delRoom() {
        super("Which room should be deleted?");

        int numRooms = MeetingSchedulingSystem.rooms.size();
        Integer[] roomNumbers = new Integer[numRooms];
        for(int i = 0; i < numRooms; i++)
            roomNumbers[i] = MeetingSchedulingSystem.rooms.get(i).getNumber();

        setLayout(new BorderLayout());
        
        options = new JList<>(roomNumbers);
        options.setVisibleRowCount(3);
        options.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        options.setSelectedIndex(0);
        
        confirm = new JButton("Confirm");
        back = new JButton("Back");
        
        JPanel buttons = new JPanel();
        buttons.add(back);
        buttons.add(confirm);
        
        add(options, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        handler buttonHandler = new handler();
        confirm.addActionListener(buttonHandler);
        back.addActionListener(buttonHandler);
    }
    
    // Listener class for updating the button grid
    private class handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            int roomSel = options.getSelectedIndex();
            if(event.getSource() == confirm) {
                if(MeetingSchedulingSystem.rooms.get(roomSel).isEmpty()) {
                    MeetingSchedulingSystem.delRoom(roomSel);
                } else
                    JOptionPane.showMessageDialog(null, "Room is non-empty", "ERROR", JOptionPane.ERROR_MESSAGE);
                MeetingSchedulingSystem.mainMenu();
                dispose();
        }
            if(event.getSource() == back) {
                MeetingSchedulingSystem.mainMenu();
                dispose();
            }
        }
    }
}
