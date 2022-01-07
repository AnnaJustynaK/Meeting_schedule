package meetingschedulingsystem;

import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * @author ASA5286
 */
public class MeetingSchedulingSystem {

    public static ArrayList<room> rooms = new ArrayList<>();
    public static ArrayList<person> people = new ArrayList<>();
    
    private static long readLong(String prompt) {
        long temp;
        Scanner i = new Scanner(System.in);
        System.out.printf(prompt);
        while(true) {
            try {
                temp = i.nextLong();
                return temp;
            }catch(RuntimeException e){
                System.err.println(e);
                i.nextLine();
                System.out.printf(prompt);
            }
        }
    }
    
    private static String readString(String prompt) {
        String temp;
        Scanner i = new Scanner(System.in);
        System.out.printf(prompt);
        while(true) {
            try {
                temp = i.nextLine();
                return temp;
            }catch(RuntimeException e){
                System.err.println(e);
                i.nextLine();
                System.out.printf(prompt);
            }
        }
    }
    
    private static void printSchedule() {
        for(room Room : rooms) {
            System.out.printf("Room: %d\n", Room.getNumber());
            Room.printMeetings();
        }
        System.out.printf("Participants:\n");
        for(person Person: people) {
            System.out.printf("\t%s %s Phone: %d\n", Person.getFirst(), Person.getLast(), Person.getPhone());
        }
    }
    
    public static void delRoom(int roomSel) {
        if(rooms.isEmpty()) {
            System.err.println("No rooms to delete.");
            return;
        }
        room delRoom = rooms.get(roomSel);
        if(delRoom.isEmpty())
            rooms.remove(delRoom);
        else{
            System.err.printf("Room %d is non-empty and cannot be deleted.\n", roomSel);
        }
    }
    
    public static void addMeeting(String name, int room, int time, int duration) {
        if(rooms.isEmpty()) {
            System.err.println("No rooms to add meetings to");
            return;
        }        
        room addRoom = rooms.get(room);
        System.out.printf("addRoom.addMeeting(%s, %d, %d", name, time, duration);
        addRoom.addMeeting(name, time, duration);
    }
    
    public static void delMeeting(int roomIndex, int timeIndex) {
        if(rooms.isEmpty()) {
            System.err.println("No rooms to delete meetings from.");
            return;
        }
        
        room delRoom = rooms.get(roomIndex);
        System.out.printf("Room %d selected\n", delRoom.getNumber());
        meeting meetings[] = delRoom.getMeetings();
        System.out.printf(".delMeeting(meetings[%d])\n", timeIndex);
        if(timeIndex != -1)
            delRoom.delMeeting(meetings[timeIndex]);
    }
     
    public static person addParticipant(String first, String last, long phone) {
        person personToAdd = new person(first, last, phone);
        for(person Person : people) {
            if(Person.equals(personToAdd)) {
                System.out.printf("%s %s already exists in participants.\n", personToAdd.getFirst(), personToAdd.getLast());
                return Person;
            }
        }
        people.add(personToAdd);
        return personToAdd;
    }
    
    public static void delParticipant(person personToDel) {
        if(people.isEmpty()) {
            System.err.println("No people to delete.");
            return;
        }
        people.remove(personToDel);
    }
    
    public static boolean addToMeeting(person personToAdd, meeting meetingAddedTo) {
        /*if(people.isEmpty()) {
            System.err.println("No people to add to meetings.");
            return;
        }*/

        if(rooms.isEmpty()) {
            System.err.println("No rooms to add person to.");
            return false;
        }
        if(personToAdd.meetingFits(meetingAddedTo) && meetingAddedTo.personFits(personToAdd)){
            System.out.printf("Adding %s to %s %s\n", meetingAddedTo.getName(), personToAdd.getFirst(), personToAdd.getLast());
            personToAdd.addMeeting(meetingAddedTo);
            System.out.printf("Adding %s %s to %s\n", personToAdd.getFirst(), personToAdd.getLast(), meetingAddedTo.getName());
            meetingAddedTo.addPerson(personToAdd);
            return true;
        } else
            return false;
    }
    
    public static void delFromMeeting(room roomDelFrom, meeting meetingDelFrom, person personToDel) {
        
        if(rooms.isEmpty()) {
            System.err.println("No rooms to delete people from.");
            return;
        }
        
        if(roomDelFrom.isEmpty()) {
            System.err.printf("No meetings in room %d\n", roomDelFrom.getNumber());
            return;
        }
        
        ArrayList<person> meetingPeople = meetingDelFrom.getPeople();
        if(meetingPeople.isEmpty()) {
            System.err.println("No people to remove from meeting.");
            return;
        }
        personToDel.delMeeting(meetingDelFrom.getTime());
        meetingDelFrom.delPerson(personToDel);
    }
    
    public static void mainMenu() {
        menu main = new menu();
        
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(250, 300);
        main.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int temp;
        
        mainMenu();

        /*
        do {           
            temp = (int)readLong("\n1: Add room\n2: Delete room\n3: Add meeting to room\n4: Delete meeting from room\n"
                + "5: Add person to participants.\n6: Delete person from participants.\n7: Add participant to meeting\n8: Delete participant from meeting\n"
                    + "9: Print schedule\n10: Quit\n\nOption (1, 2, 3 ...): ");
            System.out.printf("\n");
            switch(temp){
                case 1: // Add room
                    addRoom();
                    break;
                case 2: // Delete room
                    delRoom();
                    break;
                case 3: // Add meeting to room
                    addMeeting();
                    break;
                case 4: // Delete meeting from room
                    delMeeting();
                    break;
                case 5: // Add person to participants
                    addParticipant();
                    break;
                case 6: // Delete person from participants
                    delParticipant();
                    break;
                case 7: // Add participant to meeting
                    addToMeeting();
                    break;
                case 8: // Delete participant from meeting
                    delFromMeeting();
                    break;
                case 9: // Print schedule
                    printSchedule();
                    break;
                case 10: // Quit
                    break;
                default:
                    System.err.println("Invalid menu selection.");
            }
        }while(temp != 10);
        */
    }
}
