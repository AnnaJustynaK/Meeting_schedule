# Meeting Scheduling System

## Problem Description

In this project you are required to design and implement an application that keeps track of a
meeting scheduling system (MSS). Your MSS keeps track of meetings schedule and which
people are in what meeting in which room. The program is not required to perform scheduling,
rather is is responsible for maintaining the schedule. For simplicity, assume your application is
maintaining the schedule for only one day and a meeting can only fall in traditional 9-5 business
hours.

Following are general guidelines:

1. Each room has a number. A room should keep track of all meetings held in it.
2. Each meeting has a name, time and room. A meeting should keep track of all people attending the meeting.
3. A person has a first name, a last name and a phone number.
4. A person cannot be attending more than one meeting in any one-hour slot.
5. A room cannot hold more than one meeting at any one-hour slot.

Your application should be able to provide the following functionaries:

1. When the application starts, the user can specify the number of rooms available for meetings.
2. Rooms can be added and deleted by the user. (To delete a room, no meetings should be scheduled in this room).
3. Participants can be added and deleted by the user. (To delete a participant, he should not be assigned to any meeting).
4. A meeting can be added and deleted by the user. (To delete a meeting, no participant should be assigned to any meeting).
5. The user can create meetings by specifying a room number and the participants.
6. Upon request the system should display all meetings in the day.
7. Upon request the system should display all meetings in a room.
8. Upon request the system should display all meetings being attended by a single user.
9. Upon request the system should display all meetings at a specific time slot.

## 
General Implementation Guidelines:

1. You are required to use Java.
2. You are required to use classes to represent your objects.

The application implementation is defined into the following two phases:

## Phase 1 (10%): Due: 8:00 AM-March 29, 2017

1. Your application should not have a GUI. All interaction should be made through the command line.
2. Your application does not need to save the information onto a file.
3. Zip your files into one zip file. Upload your zip file into Angel under drop box ProjectPhase1.
4. You will have to present a 5-minute demo of your application. This is performed in your lab sessions scheduled on (March 29th).
5. Grading Policy:
* Correct functional behavior. (70%)
* Good and clear documentation. (10%)
* Input validity check and fault tolerance. (20%)

## Phase 2 (10%): Due 8:00 AM-April 26, 2017

1. Your application should have a GUI. All interaction should be through the GUI. The command line should never be used.
2. At any time, the user should be able to save all the information on a file.
3. When a program start the user can upload all the information from a file.
4. Zip your files into one zip file. Upload your zip file into Angel under drop box ProjectPhase2.
5. You will have to prepare a short report about your application. Submit the printed report during your demo. It should contain the following sections:
* **Introduction**: give an overview of the application and the main functionalities.
* **Requirements**: give a detailed discussion about the application required functionalities.
* **Design**: List all the classes that you have in your implementation. Each class should be discussed by introducing the class goal and its main functionality.
* **Challenges**: discuss and challenges that you have faced and how you dealt with them.
* **Shortcomings**: discuss the functionaries that you were not able to implement and the reason behind that.
* **Conclusion and Future work**: give some concluding remarks and state how you can improve your application in the future.
6. You will have to present a 5-minute demo of your final application. This is performed in your last lab session scheduled on (April 26th).
7. Grading Policy:
* Correct functional behavior. (50%)
* Correct and Usable GUI. (25%)
* Input validity check and fault tolerance. (15%)
* Report (10%)