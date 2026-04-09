Overview
Mero Vote is a semi-automatic electronic voting system designed as an alternative to traditional paper-based voting in Nepal. The system replaces manual ballot marking and counting with a hardware-software solution that captures votes via capacitive touch sensors, stores them securely in a SQL database over cellular network, and displays live results through an Android application.
This project was developed as a B.E. Minor Project at the Department of Computer Engineering, Pokhara Engineering College (Pokhara University), in 2022.
Problem
Traditional paper ballot systems suffer from high rates of invalid votes (improper marking), slow manual tallying, significant printing and labor costs, and vulnerability to human error during counting.
Solution
Mero Vote addresses these issues with a three-part architecture:
ComponentDescriptionEVM (Electronic Voting Machine)Arduino Uno with TTP223 touch sensors for vote inputDatabase ServerMS SQL Server for persistent, secure vote storageAndroid AppReal-time vote count display and admin election controls

System Architecture
┌─────────────────────┐       Cellular (GSM)       ┌──────────────────┐
│   VOTING MACHINE    │ ──────────────────────────► │   SQL DATABASE   │
│                     │    SIM800L GSM Module       │  MS SQL Server   │
│  Arduino Uno R3     │                             └────────┬─────────┘
│  TTP223 x4 Sensors  │                                      │
│  Key Switch (Reset) │                                      │  SQL Query
│  Ballot Button      │                                      ▼
│  Piezo Buzzer       │                             ┌──────────────────┐
│  Status LED         │                             │   ANDROID APP    │
│  EEPROM Backup      │                             │  Live Vote Count │
└─────────────────────┘                             │  Admin Controls  │
                                                    │  Election Reset  │
                                                    └──────────────────┘

Hardware Components
ComponentModelPurposeMicrocontrollerArduino Uno R3Central controller, vote processing, EEPROM storageTouch SensorsTTP223 × 4Capacitive touch input — one per candidateGSM ModuleSIM800LTransmit vote data to SQL database over cellularKey Switch2-positionMaster reset / election officer controlPush ButtonMomentaryBallot enable — authorizes a single vote sessionBuzzerPiezoAudio confirmation on successful voteLEDGreenVisual vote confirmation indicatorPower5V (Arduino), 3.9V 2A (SIM800L)Separate regulated supplies
Pin Mapping
Arduino PinConnected ToD2TTP223 Sensor #1 (Candidate A)D3TTP223 Sensor #2 (Candidate B)D4TTP223 Sensor #3 (Candidate C)D5TTP223 Sensor #4 (Candidate D)D7Piezo BuzzerD8Key Switch (Reset)D10Ballot Push ButtonD11Status LEDD0 (RX)SIM800L TXD1 (TX)SIM800L RX

Software Stack
LayerTechnologyFirmwareC/C++ (Arduino IDE)DatabaseMicrosoft SQL Server Management StudioAndroid AppJava (Android Studio)CommunicationSerial (Arduino ↔ SIM800L), Cellular (GSM ↔ SQL Server)

How It Works
Voting Flow

Election officer turns the key switch to enable the system.
Officer presses the ballot button to authorize one vote session.
Voter touches one of the four TTP223 sensors to cast their vote.
Arduino registers the touch, increments the count in EEPROM, triggers the buzzer and LED for confirmation.
SIM800L transmits the updated vote data to the SQL database over cellular network.
System locks until the officer authorizes the next voter with the ballot button.

Admin Flow

Admin logs into the Android app with credentials.
App queries the SQL database and displays live vote counts per candidate.
Admin can refresh results or reset the database for a new election.


App Screenshots
<p align="center">
  <img src="screenshots/splash.png" alt="Splash Screen" width="200"/>
  &nbsp;&nbsp;
  <img src="screenshots/login.png" alt="Login" width="200"/>
  &nbsp;&nbsp;
  <img src="screenshots/home.png" alt="Live Vote Count" width="200"/>
</p>

Note: If screenshots are not yet in the repo, add them to a screenshots/ directory.


Getting Started
Prerequisites

Arduino IDE (1.8+)
Android Studio (Arctic Fox or later)
Microsoft SQL Server Management Studio
SIM card with active data plan (for SIM800L)

Hardware Setup

Wire components according to the circuit diagram and pin mapping table above.
Ensure separate power supplies: 5V for Arduino, 3.9V / 2A for SIM800L.
Insert an active SIM card into the SIM800L module.

Firmware Upload
bash# Open the Arduino sketch in Arduino IDE
# Select Board: Arduino Uno
# Select the correct COM port
# Upload the sketch
Database Setup

Open SQL Server Management Studio.
Create a new database and the vote table:

sql   CREATE TABLE dbo.vote (
       sensor1 INT DEFAULT 0,
       sensor2 INT DEFAULT 0,
       sensor3 INT DEFAULT 0,
       sensor4 INT DEFAULT 0 -- extend for more candidates
   );

Configure the server to accept remote connections from the GSM module.

Android App

Open the Android project in Android Studio.
Update the SQL Server connection string in the source code with your server's IP and credentials.
Build and install on an Android device or emulator.


Circuit Diagram
The full circuit diagram is included in this repository as an SVG file:
mero_vote_circuit_diagram.svg

Project Structure
MeroVote/
├── arduino/                  # Arduino firmware (C/C++)
│   └── mero_vote.ino
├── android/                  # Android Studio project
│   └── app/
│       └── src/
├── database/                 # SQL scripts
│   └── schema.sql
├── screenshots/              # App screenshots
├── mero_vote_circuit_diagram.svg
├── LICENSE
└── README.md

Adjust the structure above to match your actual repository layout.


Testing
#Test CaseResult1Vote registered on touch sensor press✅ Passed2Data transmitted to SQL database✅ Passed3App executes SQL query correctly✅ Passed4Multiple votes by same voter invalidated✅ Passed5Reset function truncates database table✅ Passed6Login authentication works✅ Passed7EEPROM and server database store consistent data✅ Passed

Limitations

Android only — no iOS support.
Once reset is completed, vote data cannot be recovered locally.
Reset requires clearing both EEPROM and database separately.
Requires cellular network coverage at the polling station.
