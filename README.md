# Synchronization Repository

This repository contains a Java program that simulates a limited number of devices connected to a router's Wi-Fi using Java threading and semaphores. The program implements a Router class, a Semaphore class, and a Device class to demonstrate the synchronization of concurrent connections.

## Contributors

- Badr Mohamed
- Omar Mohamed
- Mohamed Ahmed
- Ahmed Gehad

## Program Overview

### Router Class

The `Router` class maintains a list of connections and provides methods to occupy and release a connection. It uses semaphores to control the maximum number of concurrent connections.

### Semaphore Class

The `Semaphore` class is used for synchronization purposes, as outlined in the synchronization lab. It helps control access to the router's connections.

### Device Class

The `Device` class represents different devices (threads) that can connect to the router. Each device has a name and type (e.g., mobile, pc, tablet) and can perform three activities: connect, perform online activity, and disconnect/logout.

### Network Class

The `Network` class contains the main method where the user is prompted to input the maximum number of connections a router can accept (`N`) and the total number of devices that wish to connect (`TC`). The program then simulates the connection and disconnection of devices, printing logs to a file to represent the execution order of device threads.

## Sample Input and Output

Sample input is provided to showcase the program's functionality, demonstrating the order of device connections and disconnections.

```plaintext
What is the number of WI-FI Connections?
2
What is the number of devices Clients want to connect?
4
C1 mobile
C2 tablet
C3 pc
C4 pc
```

Sample Output:

```plaintext
- (C1)(mobile) arrived
- (C2)(tablet) arrived
- Connection 1: C1 Occupied
- Connection 2: C2 Occupied
- C4(pc) arrived and waiting
- C3(pc) arrived and waiting
- Connection 1: C1 login
- Connection 1: C1 performs online activity
- Connection 2: C2 login
- Connection 2: C2 performs online activity
- Connection 1: C1 Logged out
- Connection 1: C4 Occupied
- Connection 1: C4 log in
- Connection 1: C4 performs online activity
- Connection 2: C2 Logged out
- Connection 2: C3 Occupied
```

## How to Run

To run the program, compile the Java files and execute the `Network` class.

```bash
javac *.java
java Network
```

The program will prompt you for input and generate the simulation logs in an output file.

Feel free to explore and modify the program as needed. Happy coding!
