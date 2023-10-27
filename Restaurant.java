import java.util.Scanner;

public class Restaurant {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String username = null;
        String password = null;
        doublyLinkedList dllcust = new doublyLinkedList("Customer");
        doublyLinkedList dllemp = new doublyLinkedList("Employee");
        boolean flag = true;
        while (flag) {
            System.out.println("Enter 1 to login as Restaurant Manager : ");
            System.out.println("Enter 2 to login as Customer : ");
            System.out.println("Enter 3 to EXIT SYSTEM : ");
            int userChoice = sc.nextInt();
            switch (userChoice) {
                case 1:
                    System.out.print("Enter username : ");
                    username = sc.next();
                    System.out.print("Enter password : ");
                    password = sc.next();
                    if (password.equalsIgnoreCase("1234")) {
                        System.out.println("Entry Successfull");
                    } else {
                        System.out.println("Entry failed");
                        break;
                    }
                    int managerChoice = 0;
                    while (managerChoice != 3) {
                        System.out.println("Enter 1 to FILL ALL EMPLOYEES DETAILS : ");
                        System.out.println("Enter 2 to SEE EMPLOYEE SCHEDULING : ");
                        System.out.println("Enter 3 TO EXIT : ");
                        managerChoice = sc.nextInt();
                        switch (managerChoice) {
                            case 1:
                                boolean f = true;
                                System.out.println("ENTER ALL THE EMPLOYEES DETAILS HERE...");
                                while (f) {
                                    dllemp.insertAtLast(username);
                                    System.out.print("DO YOU WANT TO ADD MORE? Y/N : ");
                                    String yn = sc.next();
                                    if (yn.charAt(0) == 'Y' || yn.charAt(0) == 'y') {
                                        f = true;
                                    } else {
                                        f = false;
                                    }
                                }
                                break;

                            case 2:
                                System.out.println("Scheduling employees based on their shifts...");
                                dllemp.displayemployee();
                                break;

                            case 3:
                                break;

                            default:
                                System.out.println("ENTER VALID CHOICE : ");
                                break;
                        }
                    }
                    break;

                case 2:
                    int orderChoice = 0;
                    while (orderChoice != 6) {
                        System.out.println("1. BOOK AN ORDER");
                        System.out.println("2. CANCEL AN ORDER");
                        System.out.println("3. PROCESS THE NEXT CUSTOMER'S ORDER");
                        System.out.println("4. DISPLAY ALL ORDERS");
                        System.out.println("5. TRACK(SEARCH) ORDER");
                        System.out.println("6. EXIT");
                        orderChoice = sc.nextInt();
                        switch (orderChoice) {
                            case 1:
                                sc.nextLine();
                                System.out.print("Enter customer Name : ");
                                dllcust.insertAtLast(sc.nextLine());
                                break;

                            case 2:
                                System.out.print("Enter username of the customer whose order you want to delete : ");
                                String deluser = sc.next();
                                dllcust.deleteValue(deluser);
                                break;

                            case 3:
                                System.out.println("\nProcessing the next customer's order...\n");
                                dllcust.getFirst();
                                dllcust.deleteAtFirst();
                                break;

                            case 4:
                                System.out.println("DISPLAYING ALL THE ORDERS..\n");
                                dllcust.display();
                                break;

                            case 5:
                                System.out.print("Enter username to track your order : ");
                                String searchuser = sc.next();
                                dllcust.search(searchuser);
                                break;

                            case 6:
                                break;

                            default:
                                System.out.println("ENTER VALID CHOICE!");
                                break;
                        }
                    }
                    break;

                case 3:
                    flag = false;
                    System.out.println("---Exiting The System---");
                    break;

                default:
                    System.out.println("Enter valid choice!");
                    break;
            }
        }
    }
}

class doublyLinkedList {
    String oe;

    doublyLinkedList(String oe) {
        this.oe = oe;
    }

    class Node {
        OrderHistory oh;
        EmployeeScheduling es;
        Node next;
        Node prev;

        Node(String username) {
            if (oe.equals("Customer")) {
                oh = new OrderHistory(username);
            } else {
                es = new EmployeeScheduling();
            }
            this.next = null;
            this.prev = null;
        }
    }

    Node first = null;

    void insertAtLast(String username) {
        Node n = new Node(username);
        if (first == null) {
            first = n;
        } else {
            Node temp = first;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = n;
            n.prev = temp;
        }
        display();
    }

    void getFirst() {
        System.out.println("Current customer's order details :");
        if (first == null) {
            System.out.println("No orders");
        } else {
            System.out.println(first.oh);
        }
        System.out.println();
    }

    Node deleteAtFirst() {
        if (first == null) {
            System.out.println("NO ORDERS HAVE BEEN TAKEN !");
            return null;
        } else if (first.next == null) {
            first = null;
            return null;
        } else {
            Node del = first;
            first = first.next;
            first.prev = null;
            del.next = null;
            return del;
        }
    }

    void deleteValue(String key) {
        if (first == null) {
            System.out.println("Empty");
        } else if (first.oh.getUsername().equalsIgnoreCase(key)) {
            System.out.println("Order Deleted Successfully of : " + key);
            first = first.next;
            if (first != null)
                first.prev = null;
            System.out.println("THE NEW ORDER LIST...");
            display();
        } else {
            Node temp = first;
            while (temp != null && !temp.oh.getUsername().equalsIgnoreCase(key)) {
                temp = temp.next;
            }
            if (temp != null) {
                System.out.println("Order Successfully deleted of : " + key);
                temp.prev.next = temp.next;
                if (temp.next != null)
                    temp.next.prev = temp.prev;
                else {
                    temp.prev = null;
                }
                System.out.println("THE NEW ORDER LIST...");
                display();
            } else {
                System.out.println("No order of : " + key + " has been found!");
            }
        }
        System.out.println();
    }

    void search(String sname) {
        int flag = 0;
        Node dummy = first;
        Node temp = first;
        while (dummy != null) {
            if (dummy.oh.getUsername().equalsIgnoreCase(sname)) {
                flag = 1;
            }
            dummy = dummy.next;
        }
        if (flag == 0) {
            System.out.println("No order found!");
        } else {
            while (!temp.oh.getUsername().equalsIgnoreCase(sname)) {
                temp = temp.next;
            }
            System.out.println(temp.oh);
        }
        System.out.println();
    }

    void display() {
        if (first == null) {
            System.out.println("EMPTY LINKED LIST");
        } else {
            Node temp = first;
            do {
                System.out.println(temp.oh + " ");
                temp = temp.next;
            } while (temp != null);
        }
        System.out.println();
    }

    private void display7am() {
        if (first == null) {
            System.out.println("EMPTY LINKED LIST");
        } else {
            Node temp = first;
            do {
                if (temp.es.start_time.equalsIgnoreCase("7 AM"))
                    System.out.println(temp.es);
                temp = temp.next;
            } while (temp != null);
        }
        System.out.println();
    }

    private void display7pm() {
        if (first == null) {
            System.out.println("EMPTY LINKED LIST");
        } else {
            Node temp = first;
            do {
                if (temp.es.start_time.equalsIgnoreCase("7 PM"))
                    System.out.println(temp.es);
                temp = temp.next;
            } while (temp != null);
        }
        System.out.println();
    }

    void displayemployee() {
        System.out.println("----EMPLOYEES OF 7 AM to 7 PM (Day Shift)----\n");
        display7am();
        System.out.println("----EMPLOYEES OF 7 PM to 7 AM (Night Shift)----\n");
        display7pm();
    }
}

class OrderHistory {
    public String toString() {
        return "CustName : " + username + ", Items Ordered : " + ItemsOrdered
                + ", numOfItemsOrdered : "
                + numOfItems
                + ", totalAmount : " + totalAmount;

    }

    public String getUsername() {
        return username;
    }

    Scanner sc = new Scanner(System.in);
    static int orderID = 0;
    String username;
    int numOfItems;
    double totalAmount;
    String ItemsOrdered = "";

    public OrderHistory(String username) {
        this.username = username;
        int pastaCount = 0;
        int pizzaCount = 0;
        int burgerCount = 0;
        int friesCount = 0;
        int shakesCount = 0;
        int choice = 0;
        orderID++;
        do {
            System.out.println("ENTER 1 for Pasta(300 $) : ");
            System.out.println("ENTER 2 for Pizza(280 $) : ");
            System.out.println("ENTER 3 for Burger(180 $) : ");
            System.out.println("ENTER 4 for Fries(150 $) : ");
            System.out.println("ENTER 5 for Shakes(250 $) : ");
            System.out.println("ENTER 6 to finalize the order : ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    pastaCount++;
                    break;
                case 2:
                    pizzaCount++;
                    break;
                case 3:
                    burgerCount++;
                    break;
                case 4:
                    friesCount++;
                    break;
                case 5:
                    shakesCount++;
                    break;
                case 6:
                    break;
                default:
                    System.out.println("ENTER VALID CHOICE");
                    break;
            }
        } while (choice != 6);
        totalAmount = pastaCount * 300 + pizzaCount * 280 + burgerCount * 180 + friesCount * 150
                + shakesCount * 250;
        numOfItems = pastaCount + pizzaCount + burgerCount + friesCount + shakesCount;
        if (pastaCount != 0) {
            ItemsOrdered += " PASTA : " + pastaCount;
        }
        if (pizzaCount != 0) {
            ItemsOrdered += " PIZZA : " + pizzaCount;
        }
        if (burgerCount != 0) {
            ItemsOrdered += " BURGER : " + burgerCount;
        }
        if (friesCount != 0) {
            ItemsOrdered += " FRIES : " + friesCount;
        }
        if (shakesCount != 0) {
            ItemsOrdered += " SHAKES : " + shakesCount;
        }
    }
}

class EmployeeScheduling {

    @Override
    public String toString() {
        return "EmployeeScheduling [emp_Name=" + emp_Name + ", start_time=" + start_time + ", end_time=" + end_time
                + ", role=" + role + "]";
    }

    Scanner sc = new Scanner(System.in);
    String emp_Name;
    String start_time;
    String end_time;
    String role;

    public EmployeeScheduling() {
        System.out.print("ENTER EMPLOYEE NAME : ");
        emp_Name = sc.next();
        System.out.println("SHIFTS : ");
        System.out.println("1. 7 AM - 7 PM (Day Shift)");
        System.out.println("2. 7 PM - 7 AM (Night Shift)");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                start_time = "7 AM";
                end_time = "7 PM (Day Shift)";
                break;
            case 2:
                start_time = "7 PM";
                end_time = "7 AM (Night Shift)";
                break;
        }
        System.out.print("ENTER ROLE OF THE EMPLOYEE : ");
        role = sc.next();
    }
}