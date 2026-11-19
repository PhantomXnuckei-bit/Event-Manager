
import java.util.*;

class Event {
    int id;
    String name;
    String venue;
    int priority;
    int time;

    Event(int id, String name, String venue, int priority, int time) {
        this.id = id;
        this.name = name;
        this.venue = venue;
        this.priority = priority;
        this.time = time;
    }
}

public class EventScheduler {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Min Heap: Priority first, then Time
        PriorityQueue<Event> minHeap = new PriorityQueue<>(
            (a, b) -> {
                if (a.priority != b.priority)
                    return Integer.compare(a.priority, b.priority);

                return Integer.compare(a.time, b.time);
            }
        );

        int idCounter = 1;
        int choice;

        do {
            System.out.println("\n===== Event Management Scheduler =====");
            System.out.println("1. Add Event");
            System.out.println("2. Display All Events");
            System.out.println("3. Search Event by ID");
            System.out.println("4. Search Event by Name");
            System.out.println("5. Execute Next Event");
            System.out.println("6. Remove Event by ID");
            System.out.println("7. Display Total Events");
            System.out.println("8. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // Add Event
                case 1:
                    System.out.print("Enter Event Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Venue: ");
                    String venue = sc.nextLine();

                    System.out.println("Priority: 1-High, 2-Medium, 3-Low");
                    System.out.print("Enter Event Priority: ");
                    int priority = sc.nextInt();
                    sc.nextLine();

                    if (priority < 1 || priority > 3) {
                        System.out.println("Invalid Priority!");
                        break;
                    }

                    System.out.print("Enter Event Time (HH:MM): ");
                    String inputTime = sc.nextLine();

                    int hours, minutes;

                    try {
                        if (!inputTime.matches("\\d{2}:\\d{2}"))
                            throw new Exception();

                        hours = Integer.parseInt(inputTime.substring(0, 2));
                        minutes = Integer.parseInt(inputTime.substring(3, 5));

                        if (hours > 23 || minutes > 59)
                            throw new Exception();

                    } catch (Exception e) {
                        System.out.println("Invalid Time! Enter between 00:00 and 23:59.");
                        break;
                    }

                    int time = hours * 60 + minutes;

                    minHeap.add(new Event(idCounter++, name, venue, priority, time));

                    System.out.println("Event added successfully.");
                    break;

                // Display All Events
                case 2:
                    if (minHeap.isEmpty()) {
                        System.out.println("No events scheduled.");
                        break;
                    }

                    PriorityQueue<Event> temp = new PriorityQueue<>(minHeap);

                    System.out.println("\n--- Scheduled Events ---");

                    while (!temp.isEmpty()) {
                        Event e = temp.poll();

                        System.out.println("ID       : " + e.id);
                        System.out.println("Name     : " + e.name);
                        System.out.println("Venue    : " + e.venue);
                        System.out.println("Priority : " +
                            (e.priority == 1 ? "High" :
                             e.priority == 2 ? "Medium" : "Low"));

                        System.out.printf("Time     : %02d:%02d%n",
                            e.time / 60, e.time % 60);

                        System.out.println("-------------------------");
                    }
                    break;

                // Search Event by ID
                case 3:
                    System.out.print("Enter Event ID: ");
                    int searchId = sc.nextInt();

                    boolean found = false;

                    for (Event e : minHeap) {
                        if (e.id == searchId) {
                            System.out.println("ID       : " + e.id);
                            System.out.println("Name     : " + e.name);
                            System.out.println("Venue    : " + e.venue);
                            System.out.println("Priority : " +
                                (e.priority == 1 ? "High" :
                                 e.priority == 2 ? "Medium" : "Low"));

                            System.out.printf("Time     : %02d:%02d%n",
                                e.time / 60, e.time % 60);

                            found = true;
                            break;
                        }
                    }

                    if (!found)
                        System.out.println("Event not found.");
                    break;

                // Search Event by Name
                case 4:
                    System.out.print("Enter Event Name: ");
                    String searchName = sc.nextLine();

                    found = false;

                    for (Event e : minHeap) {
                        if (e.name.equalsIgnoreCase(searchName)) {
                            System.out.println("ID       : " + e.id);
                            System.out.println("Name     : " + e.name);
                            System.out.println("Venue    : " + e.venue);
                            System.out.println("Priority : " +
                                (e.priority == 1 ? "High" :
                                 e.priority == 2 ? "Medium" : "Low"));

                            System.out.printf("Time     : %02d:%02d%n",
                                e.time / 60, e.time % 60);

                            found = true;
                        }
                    }

                    if (!found)
                        System.out.println("Event not found.");
                    break;

                // Execute Next Event
                case 5:
                    if (minHeap.isEmpty()) {
                        System.out.println("No events available.");
                    } else {
                        Event e = minHeap.poll();

                        System.out.println("\nExecuting Next Event:");
                        System.out.println("Event ID   : " + e.id);
                        System.out.println("Event Name : " + e.name);
                        System.out.println("Venue      : " + e.venue);
                        System.out.println("Priority   : " +
                            (e.priority == 1 ? "High" :
                             e.priority == 2 ? "Medium" : "Low"));

                        System.out.printf("Time       : %02d:%02d%n",
                            e.time / 60, e.time % 60);

                        System.out.println("Event completed successfully.");
                    }
                    break;

                // Remove Event by ID
                case 6:
                    System.out.print("Enter Event ID to Remove: ");
                    int removeId = sc.nextInt();

                    found = minHeap.removeIf(e -> e.id == removeId);

                    if (found)
                        System.out.println("Event removed successfully.");
                    else
                        System.out.println("Event not found.");
                    break;

                // Display Total Events
                case 7:
                    System.out.println("Total Scheduled Events: " + minHeap.size());
                    break;

                case 8:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice.");
            }

        } while (choice != 8);

        sc.close();
    }
}