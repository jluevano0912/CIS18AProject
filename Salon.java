package salonpackage;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import salonpackage.Order;
import salonpackage.Service;
import java.time.LocalDateTime; // import the LocalDateTime class
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.io.*;
import java.nio.file.FileSystems;
import java.util.Scanner;

// Salon order implements (interface) Order 
class SalonOrder implements Order {

    // array of services
    SalonService services[] = new SalonService[100];
    int serviceIndex = 0;

    // array of extraservies (add-on's)
    SalonService extraservice[] = new SalonService[100];
    int serviceIn = 0;

    // variables to store appointment info
    String date;
    String customerName;
    double totalCost;
    String month;
    int day;
    int year;
    LocalTime midnight = LocalTime.MIDNIGHT;
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a"); // date format
    int days = 2; // num of days to generate time slots for
    int start = 9; // start at 9am
    int hours = 5; // num of hour slots ex 9 + 5 = 14 so hours 9-14 24hr time
    int timeArrayIndex = 0;

    // array for available times
    String timeArray[] = new String[days * hours];

    SalonOrder() {
    }

    void change(SalonOrder ob) {

    }

    public String[] generateDates() { // creates timeslots for the next 2 days & 5 hours from 9 AM
        for (int i = 1; i <= days; i++) {

            LocalDate today = LocalDate.now(ZoneId.of("America/Los_Angeles"));
            LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
            LocalDateTime tomorrowMidnight = todayMidnight.plusDays(i);

            for (int h = start; h <= start + hours - 1; h++) {
                String formattedDate = tomorrowMidnight.plusHours(h).format(dateFormat);
                timeArray[timeArrayIndex] = formattedDate; //
                timeArrayIndex++;

            }
        }
        return this.timeArray; // returns time array to later ask user for appointment
    }

    public void printDates() {

    }

    public void addService(SalonService service) {
        this.services[serviceIndex] = service;
        serviceIndex++;

    }

    public void addExtraService(SalonService service) {
        this.extraservice[serviceIn] = service;
        serviceIn++;
    }

    // adding methods and returning the services

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalCost() {
        double total = 0;

        // loop all services
        for (int i = 0; i <= this.serviceIndex - 1; i++) {
            total += this.services[i].getCost();
        }

        for (int i = 0; i <= this.serviceIn - 1; i++) {
            total += this.extraservice[i].getCost();
        }

        // save the cost of each service to the total var +=
        return total;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    // method that prints the receipt
    public void printServices() {
        final DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("\r\nHere is your receipt.");

        System.out.println("-------------------------------------------");
        System.out.println("    Zippy's Nail Salon Appointment\r\n");
        System.out.println("        Appointment for: " + this.customerName);
        System.out.println("        Appointment Date: " + this.getDate());
        for (int i = 0; i <= serviceIndex - 1; i++) {
            System.out.println(
                    "        Service: " + this.services[i].getName());
        }
        for (int i = 0; i <= serviceIn - 1; i++) {
            System.out.println("        Add-on selected: " + this.extraservice[i].getName());
        }
        System.out.println("        Total: $" + df.format(this.getTotalCost()) + "\r\n");
        System.out.println("     Thank you for booking with Zippy's.");
        System.out.println("            See you soon!");
        System.out.println("-------------------------------------------\r\n");
        String data = "\r\n------------------------------------------- \r\n";
        data += "Zippy's Nail Salon Appointment\r\n";
        data += "Appointment for: " + this.customerName + "\r\n" + "Appointment Date: " + this.getDate() + "\r\n";
        for (int i = 0; i <= serviceIndex - 1; i++) {
            data += "Service: " + this.services[i].getName() + "\r\n";
        }
        for (int i = 0; i <= serviceIn - 1; i++) {
            data += "Add-on selected: " + this.extraservice[i].getName() + "\r\n";
        }
        data += "Total: $" + df.format(this.getTotalCost());
        data += "\r\nThank you for booking with Zippy's.";
        data += "\r\n------------------------------------------- \r\n";
        writeCustomerReciept writer = new writeCustomerReciept(data, "receipt.txt");
        writer.writeToFile(writer.getTextToWrite(), writer.getFileName());

    }

}

class SalonService implements Service {
    double cost;
    String name;

    SalonService(String name, double cost) {
        this.setCost(cost);
        this.setName(name);
    }

    public double getCost() {
        return this.cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class test1 {
    public void print1() {
        System.out.println("print 1");
    }
}

class test2 extends test1 {
    public void print2() {
        System.out.println("print 2");
    }
}

class receiptPrinter {

    public void writeToFile(String textToWrite, String fileName) {

        FileOutputStream fop = null;
        File file;
        try {
            String data = textToWrite;
            String currentDirectory = System.getProperty("user.dir");
            System.out.println("CURRENT dir: " + currentDirectory);

            file = new File(currentDirectory + "/" + fileName);
            fop = new FileOutputStream(file, true);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = data.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();
            System.out.println("Done");

        } catch (IOException c) {
            c.printStackTrace();
            System.out.println(c.getMessage());
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException c) {
                c.printStackTrace();
            }
        }

    }

}

class writeCustomerReciept extends receiptPrinter {
    String fileName;
    String textToWrite;

    public writeCustomerReciept(String textToWrite, String fileName) {
        this.textToWrite = textToWrite;
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getTextToWrite() {
        return this.textToWrite;
    }

}

class Salon {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    SalonService services[] = new SalonService[6];

    // for the add-on's
    SalonService extraservice[] = new SalonService[2];

    // constrcutor for Salon class - runs on instance creation
    Salon() {
        this.services[0] = new SalonService("Basic Manicure", 30.00);
        this.services[1] = new SalonService("Gel Manicure", 40.00);
        this.services[2] = new SalonService("Basic Pedicure", 25.00);
        this.services[3] = new SalonService("Gel Pedicure", 35.00);
        this.services[4] = new SalonService("Basic Mani Pedi Combo", 50.00);
        this.services[5] = new SalonService("Gel Mani Pedi Combo", 70.00);

        this.extraservice[0] = new SalonService("Nail Design", 5.00);
        this.extraservice[1] = new SalonService("Extra 10 minutes massage", 7.00);

        this.startOrder();

    }

    public void showServices() {
        for (int i = 0; i < this.services.length; i++) {
            // print option
            System.out.println("Option " + i + " " + services[i].getName() + "  $" + df.format(services[i].getCost()));
        }
        System.out.println("\r\nAvailable add-on's:");
        for (int i = 0; i < this.extraservice.length; i++) {
            // print option
            System.out.println(
                    "Option " + i + " " + extraservice[i].getName() + "    $" + df.format(extraservice[i].getCost()));
        }

        // get input
        System.out.println("\r\nPlease select an option: ");
    }

    public void showExtraServices() {
        System.out.println("\r\nAvailable add-on's:");
        for (int i = 0; i < this.extraservice.length; i++) {
            // print option
            System.out.println(
                    "Option " + i + " " + extraservice[i].getName() + "    $" + df.format(extraservice[i].getCost()));
        }
        System.out.println("\r\nPlease select an option: ");
    }

    public void startOrder() {

        SalonOrder order = new SalonOrder();
        Scanner sc = new Scanner(System.in);

        System.out.println("\r\nWelcome to Zippy's Nail Salon! ");
        System.out.println("We are open everyday from 9:00 AM to 1:00 PM\r\n");

        // Construct Scanner object from System.in

        String addExtraService;
        do {
            int option = -1;

            boolean chooseInvalidService = false;

            do {
                System.out.println("Services we offer: ");

                if (chooseInvalidService)
                    System.out.println("\r\n" + option + " is not a valid option. Please select from the following: ");

                showServices();
                option = Integer.parseInt(sc.nextLine());

                chooseInvalidService = true;
            } while (option < 0 || option > 5); // check if valid

            System.out.println("You selected option: " + option);

            // this is adding a valid service to the order
            order.addService(services[option]); // add service
            System.out.println("Would you like to add another service to your appointment? (Yes or No)");
            addExtraService = sc.nextLine();
        } while ((addExtraService.equals("yes") || addExtraService.equals("Yes")));

        System.out.println("\r\nWould you like an add-on? (Yes or No)");
        String addOn = sc.nextLine();
        if (addOn.equals("yes") || addOn.equals("Yes")) {
            showExtraServices();

            int addOption = Integer.parseInt(sc.nextLine());

            // check if add-on option is valid
            if (addOption == 0 || addOption == 1) {
                switch (addOption) {

                    // if users chooses 0
                    case 0:
                        System.out.println();
                        System.out.println("Add-on selected: Nail design");

                        break;

                    // if user chooses 1
                    case 1:
                        System.out.println();
                        System.out.println("Add-on selected: Extra 10 minute massage.");

                }

                // any other input is invalid
            } else {

                // print out add-on options until valid input
                do {
                    System.out.println(
                            "\r\n" + addOption + " is not a valid option. Please select from the following: ");
                    showExtraServices();
                    addOption = Integer.parseInt(sc.nextLine());

                    // print options
                } while (addOption < 0 || addOption > 1);

            }
            order.addExtraService(extraservice[addOption]);

            // if user does not want an add-on
        } else if (addOn.equals("No") || addOn.equals("no")) {
            System.out.println("No add on selected.");

            // if user does not enter yes or no
        } else {
            System.out.println("Invalid answer, please type yes to select an add-on or no for no add-on.");
            addOn = sc.nextLine();
        }

        // Instance of class SalonOrder

        sc.reset();
        // Get name
        System.out.println("\r\nName for appointment: ");
        String customerName = sc.nextLine();
        order.setCustomerName(customerName);
        // ----- new date code
        String[] dateOptions = order.generateDates(); // generates dates and fills in local string array of dates
        System.out.println("Select a date option below: ");
        for (int dateIndex = 0; dateIndex < dateOptions.length; dateIndex++) {
            System.out.println(dateIndex + ") " + dateOptions[dateIndex]); // prints out options
        }
        int datePicked;
        do {
            datePicked = Integer.parseInt(sc.nextLine());
        } while (datePicked > dateOptions.length || datePicked < 0);
        // store selected date in order
        order.setDate(dateOptions[datePicked]);
        // end of new date code

        order.printServices();

    }// end of startOrder

}// end of Salon class

class Main {
    public static void main(String[] args) {
        Salon testService = new Salon();

    }
}
