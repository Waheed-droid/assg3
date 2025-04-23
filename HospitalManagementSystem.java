/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospitalmanagementsystem;

/**
 *
 * @author lenovo
 */
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Scanner;

public class HospitalManagementSystem {
    public static void main(String[] args) {
        Main.main(args);
    }

    // Represents the hospital entity and core services it offers
    static class Hospital {
        private String hospitalName;
        private List<User> users;
        private AppointmentManager appointmentManager;
        private NotificationService notificationService;

        public Hospital(String name) {
            this.hospitalName = name;
            this.users = new ArrayList<>();
            this.appointmentManager = new AppointmentManager();
            this.notificationService = new NotificationService();
        }

        public void registerUser(User user) {
            users.add(user);
        }

        public List<User> getAllUsers() {
            return users;
        }

        public AppointmentManager getAppointmentManager() {
            return appointmentManager;
        }

        public NotificationService getNotificationService() {
            return notificationService;
        }

        public String getHospitalName() {
            return hospitalName;
        }
    }

    // Abstract base class for any user (Doctor, Patient, Administrator)
    static abstract class User {
        private int id;
        private String name;
        private String email;
        private String role;

        public User(int id, String name, String email, String role) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
    }

    // A patient has vitals, may allow remote checkups and has a panic button
    static class Patient extends User {
        private List<VitalSign> vitals;
        private boolean remoteCheckAllowed;
        private PanicButton panicButton;

        public Patient(int id, String name, String email) {
            super(id, name, email, "Patient");
            this.vitals = new ArrayList<>();
            this.panicButton = new PanicButton(this);
            this.remoteCheckAllowed = false;
        }

        public void allowRemoteCheck() {
            this.remoteCheckAllowed = true;
        }

        public void recordVital(VitalSign vital) {
            vitals.add(vital);
        }
public void displayVitals() {
    if (vitals.isEmpty()) {
        System.out.println("No vital signs recorded yet.");
    } else {
        for (VitalSign v : vitals) {
            System.out.println(v);
        }
    }
}

        public PanicButton getPanicButton() {
            return panicButton;
        }

        public boolean isRemoteCheckAllowed() {
            return remoteCheckAllowed;
        }
    }

    // Doctors can review vitals of patients
    static class Doctor extends User {
        public Doctor(int id, String name, String email) {
            super(id, name, email, "Doctor");
        }

        public void reviewPatientVitals(Patient patient) {
            System.out.println("Reviewing vitals for: " + patient.getName());
            patient.displayVitals();
        }
        private List<Feedback> feedbackList = new ArrayList<>();

public void giveFeedback(Patient patient, String message) {
    Feedback feedback = new Feedback(this, patient, message);
    feedbackList.add(feedback);
    System.out.println("Feedback submitted successfully.");
}

public void showAllFeedback() {
    if (feedbackList.isEmpty()) {
        System.out.println("No feedback submitted yet.");
    } else {
        for (Feedback fb : feedbackList) {
            fb.displayFeedback();
        }
    }
}


    }

    // Admins can manage users
    static class Administrator extends User {
        private List<User> registeredUsers;

        public Administrator(int id, String name, String email) {
            super(id, name, email, "Administrator");
            this.registeredUsers = new ArrayList<>();
        }

        public void addUser(User user) {
            registeredUsers.add(user);
        }

        public List<User> getRegisteredUsers() {
            return registeredUsers;
        }

        public void showAllUsers() {
            for (User user : registeredUsers) {
                System.out.println(user.getRole() + ": " + user.getName() + " (" + user.getEmail() + ")");
            }
        }
    }

    // Represents an appointment between a doctor and a patient
    static class Appointment {
        private LocalDateTime dateTime;
        private Doctor doctor;
        private Patient patient;

        public Appointment(LocalDateTime dateTime, Doctor doctor, Patient patient) {
            this.dateTime = dateTime;
            this.doctor = doctor;
            this.patient = patient;
        }

        public LocalDateTime getDateTime() { return dateTime; }
        public Doctor getDoctor() { return doctor; }
        public Patient getPatient() { return patient; }

        @Override
        public String toString() {
            return "Appointment: " + dateTime + " with Dr. " + doctor.getName() + " and patient " + patient.getName();
        }
    }

    // Handles all appointments in the hospital
    static class AppointmentManager {
        private List<Appointment> appointments;

        public AppointmentManager() {
            appointments = new ArrayList<>();
        }

        public void bookAppointment(Appointment appointment) {
            appointments.add(appointment);
            System.out.println("Appointment has been scheduled successfully!");
        }

        public List<Appointment> getAllAppointments() {
            return appointments;
        }

        public void showAppointments() {
            if (appointments.isEmpty()) {
                System.out.println("No appointments have been scheduled.");
            } else {
                for (Appointment appt : appointments) {
                    System.out.println(appt);
                }
            }
        }
    }
static class Feedback {
    private Doctor doctor;
    private Patient patient;
    private String message;
    private LocalDateTime timestamp;

    public Feedback(Doctor doctor, Patient patient, String message) {
        this.doctor = doctor;
        this.patient = patient;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public void displayFeedback() {
        System.out.println("Feedback from Dr. " + doctor.getName() + " to " + patient.getName() + " on " + timestamp);
        System.out.println("Message: " + message);
    }
}

    // Emergency alert class used when panic button is triggered
    static class EmergencyAlert {
        private Patient patient;
        private String reason;
        private LocalDateTime alertTime;

        public EmergencyAlert(Patient patient, String reason) {
            this.patient = patient;
            this.reason = reason;
            this.alertTime = LocalDateTime.now();
        }

        public void displayAlert() {
            System.out.println("EMERGENCY ALERT: " + patient.getName() + " - " + reason + " at " + alertTime);
        }

        @Override
        public String toString() {
            return "Patient: " + patient.getName() + " | Reason: " + reason + " | Time: " + alertTime;
        }
    }

    // Represents the panic button available to patients
    static class PanicButton {
        private Patient patient;

        public PanicButton(Patient patient) {
            this.patient = patient;
        }

        public EmergencyAlert press() {
            System.out.println("Panic button pressed by " + patient.getName() + "!");
            return new EmergencyAlert(patient, "Manual panic trigger");
        }
    }

    // Service to notify users (can switch between SMS and Email)
    static class NotificationService {
        private NotificationSender sender;

        public NotificationService() {
            this.sender = new EmailNotification(); // default sender
        }

        public void setSender(NotificationSender sender) {
            this.sender = sender;
        }

        public void notifyUser(User user, String message) {
            sender.sendNotification(user.getEmail(), message);
        }

        public void sendEmergencyNotification(Patient patient, String reason) {
            String alertMsg = "Emergency for " + patient.getName() + ": " + reason;
            sender.sendNotification(patient.getEmail(), alertMsg);
        }

        public void sendReminder(User user, String content) {
            sender.sendNotification(user.getEmail(), content);
        }
    }

    // Notification sending interface
    interface NotificationSender {
        void sendNotification(String recipient, String message);
    }

    // Email implementation of notifications
    static class EmailNotification implements NotificationSender {
        @Override
        public void sendNotification(String recipient, String message) {
            System.out.println("\n[Email Notification]");
            System.out.println("To: " + recipient);
            System.out.println("Message: " + message);
        }
    }

    // SMS implementation of notifications
    static class SMSNotification implements NotificationSender {
        @Override
        public void sendNotification(String recipient, String message) {
            System.out.println("\n[SMS Notification]");
            System.out.println("To: " + recipient);
            System.out.println("Message: " + message);
        }
    }

    // Represents a video call session between doctor and patient
    static class VideoCall {
        private Doctor doctor;
        private Patient patient;

        public VideoCall(Doctor doctor, Patient patient) {
            this.doctor = doctor;
            this.patient = patient;
        }

        public void startSession() {
            System.out.println("Starting video consultation between Dr. " + doctor.getName() + " and " + patient.getName());
            System.out.println("Connecting to video session...");
        }
    }

    // Basic chat server that logs and displays messages
    static class ChatServer {
        private List<String> messageLog;

        public ChatServer() {
            this.messageLog = new ArrayList<>();
        }

        public void sendMessage(String sender, String recipient, String content) {
            String formattedMessage = "[" + sender + " to " + recipient + "]: " + content;
            messageLog.add(formattedMessage);
            System.out.println("Message sent: " + formattedMessage);
        }

        public void displayMessages() {
            System.out.println("\n--- Chat History ---");
            if (messageLog.isEmpty()) {
                System.out.println("No chat history available.");
            } else {
                for (String msg : messageLog) {
                    System.out.println(msg);
                }
            }
        }
    }

    // Each client can send and view chat history
    static class ChatClient {
        private String username;
        private ChatServer server;

        public ChatClient(String username, ChatServer server) {
            this.username = username;
            this.server = server;
        }

        public void sendMessage(String recipient, String message) {
            server.sendMessage(username, recipient, message);
        }

        public void viewChatHistory() {
            server.displayMessages();
        }
    }

    // Handles reminders for appointments and medication
    static class ReminderService {
        private NotificationService notificationService;

        public ReminderService(NotificationService notificationService) {
            this.notificationService = notificationService;
        }

        public void sendAppointmentReminder(Patient patient, String dateTime) {
            String message = "Reminder: You have an appointment on " + dateTime;
            notificationService.sendReminder(patient, message);
        }

        public void sendMedicationReminder(Patient patient, String medication) {
            String message = "Reminder: Time to take your medication - " + medication;
            notificationService.sendReminder(patient, message);
        }
    }

    // Vital signs recorded by patients
    static class VitalSign {
        private String type;
        private String value;
        private LocalDateTime timestamp;

        public VitalSign(String type, String value) {
            this.type = type;
            this.value = value;
            this.timestamp = LocalDateTime.now();
        }

        public String getType() { return type; }
        public String getValue() { return value; }
        public LocalDateTime getTimestamp() { return timestamp; }

        @Override
        public String toString() {
            return type + ": " + value + " (Recorded: " + timestamp + ")";
        }
    }

    // CLI Main class that allows interaction with the system
    static class Main {
        private static final Scanner scanner = new Scanner(System.in);
        private static final Hospital hospital = new Hospital("NUST Health Center");

        public static void main(String[] args) {
            System.out.println("Welcome to " + hospital.getHospitalName() + " Management System");

            while (true) {
System.out.println("\n--- Main Menu ---");
System.out.println("1. Register Doctor");
System.out.println("2. Register Patient");
System.out.println("3. Schedule Appointment");
System.out.println("4. View Appointments");
System.out.println("5. View Patient Vitals");  
System.out.println("6. Trigger Panic Button");
System.out.println("7. Submit Doctor Feedback");
System.out.println("8. Exit"); 
System.out.print("Choose option: ");
                int option = scanner.nextInt();
                scanner.nextLine(); 

                switch (option) {
    case 1 -> registerDoctor();
    case 2 -> registerPatient();
    case 3 -> scheduleAppointment();
    case 4 -> hospital.getAppointmentManager().showAppointments();
    case 5 -> viewPatientVitals();  // New case
    case 6 -> triggerPanic();
    case 7 -> submitDoctorFeedback();
    case 8 -> {  // Changed from 7 to 8
        System.out.println("Exiting system. Goodbye!");
        return;
    }
    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        }

        private static void registerDoctor() {
            System.out.print("Doctor ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            Doctor doctor = new Doctor(id, name, email);
            hospital.registerUser(doctor);
            System.out.println("Doctor registered successfully.");
        }

        private static void registerPatient() {
            System.out.print("Patient ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            Patient patient = new Patient(id, name, email);
            System.out.print("Allow remote checkup (yes/no)? ");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                patient.allowRemoteCheck();
            }

            System.out.print("Add vital sign (yes/no)? ");
            while (scanner.nextLine().equalsIgnoreCase("yes")) {
                System.out.print("Type (BP/Oxygen/Temp): ");
                String type = scanner.nextLine();
                System.out.print("Value: ");
               String value = scanner.nextLine();
                scanner.nextLine();
                patient.recordVital(new VitalSign(type, value));
                System.out.print("Add another vital? (yes/no): ");
            }

            hospital.registerUser(patient);
            System.out.println("Patient registered successfully.");
        }

        private static void scheduleAppointment() {
            System.out.print("Doctor ID: ");
            int docId = scanner.nextInt();
            System.out.print("Patient ID: ");
            int patId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter appointment date/time (YYYY-MM-DDTHH-MM): ");
            String datetime = scanner.nextLine();

            Doctor doctor = null;
            Patient patient = null;

            for (User user : hospital.getAllUsers()) {
                if (user instanceof Doctor && user.getId() == docId) {
                    doctor = (Doctor) user;
                }
                if (user instanceof Patient && user.getId() == patId) {
                    patient = (Patient) user;
                }
            }

            if (doctor != null && patient != null) {
                Appointment appointment = new Appointment(LocalDateTime.parse(datetime), doctor, patient);
                hospital.getAppointmentManager().bookAppointment(appointment);
                hospital.getNotificationService().notifyUser(doctor, "New appointment with " + patient.getName());
                hospital.getNotificationService().notifyUser(patient, "Your appointment with Dr. " + doctor.getName());
            } else {
                System.out.println("Doctor or Patient not found.");
            }
        }
        private static void submitDoctorFeedback() {
    System.out.print("Doctor ID: ");
    int docId = scanner.nextInt();
    System.out.print("Patient ID: ");
    int patId = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Feedback message: ");
    String msg = scanner.nextLine();

    Doctor doctor = null;
    Patient patient = null;

    for (User user : hospital.getAllUsers()) {
        if (user instanceof Doctor && user.getId() == docId) {
            doctor = (Doctor) user;
        }
        if (user instanceof Patient && user.getId() == patId) {
            patient = (Patient) user;
        }
    }

    if (doctor != null && patient != null) {
        doctor.giveFeedback(patient, msg);
    } else {
        System.out.println("Doctor or Patient not found.");
    }
}

private static void viewPatientVitals() {
    System.out.print("Enter patient ID to view vitals: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    for (User user : hospital.getAllUsers()) {
        if (user instanceof Patient && user.getId() == id) {
            Patient patient = (Patient) user;
            System.out.println("\nVital signs for " + patient.getName() + ":");
            patient.displayVitals();
            return;
        }
    }
    System.out.println("Patient not found.");
}


        private static void triggerPanic() {
            System.out.print("Enter patient ID to trigger emergency: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            for (User user : hospital.getAllUsers()) {
                if (user instanceof Patient && user.getId() == id) {
                    Patient patient = (Patient) user;
                    EmergencyAlert alert = patient.getPanicButton().press();
                    alert.displayAlert();
                    hospital.getNotificationService().sendEmergencyNotification(patient, "Manual panic alert triggered.");
                    return;
                }
            }
            System.out.println("Patient not found.");
        }
    }
}