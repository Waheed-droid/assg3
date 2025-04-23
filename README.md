

Overview
A comprehensive Java-based hospital management system that handles:
- Patient and doctor registration 
- Appointment scheduling
- Vital signs tracking
- Emergency alerts
- Doctor feedback system
- Notifications (email/SMS)

Features
- User Management: Register doctors, patients, and administrators
- Appointments: Schedule and view doctor-patient appointments
- Vital Signs: Record and view patient health metrics (BP, Oxygen, Temp)
- Emergency System: Panic button for immediate alerts
- Feedback: Doctors can provide feedback for patients
- Notifications: Email and SMS notification system

How to Use
1. Compile and run `HospitalManagementSystem.java`
2. Use the menu system to:
   - Register staff and patients
   - Schedule appointments
   - View vital signs (Option 5)
   - Submit doctor feedback
   - Trigger emergency alerts

Technical Details
- Written in Java (JDK 8+)
- Uses LocalDateTime for appointment scheduling
- Implements notification interface (Email/SMS)
- Console-based interface with Scanner input

Example Commands
- Register patient with vitals: `2 → Enter details → Add vitals`
- View vitals: `5 → Enter patient ID`
- Schedule appointment: `3 → Enter doctor/patient IDs and time`
- Emergency alert: `6 → Enter patient ID`
