# Robot Worlds App

Multi client web interactive game and server where robots battle each other. 

## Instructions:
1. Start server.
2. Start mobile or web app.
3. Connect to server
4. Load world
5. Launch a robot and have fun!

## Technologies:

* Java 11.
* Maven.
* Flutter
* Dart
* Docker
* SQLite

## Developer setup:

1. Ensure all relevant technologies are installed.
2. In your terminal run: `git clone https://gitlab.wethinkco.de/sgerber/shenzou-robot.git`
3. Navigate to the program root folder and run `make package-notest` 
4. Once complete, then run `make run-server` in the same folder.
5. In a new terminal window in the same folder, run `make run-app`.
6. To clean your Docker container run `make stop-server` in the project root.

## Architecture:

The MVC (model-view-controller) pattern & Observer pattern where relevant were followed when developing our web app.

# Roadmap:
1. Model layer
    - Consists of program models for robots, obstacles & world, as well as relevant functions.

2. View layer

    - Consists of various app screens, which are interacted with by the user.
    - Prompts the user for input.

3. Controller layer
    - App controller controls the interaction between the screens and the program data.
    - API controller controls the interaction between the server and app.


4. Observer Pattern:
    - The app controller is the subject, which notifies its listeners to reload when data is updated.
    - Screens are the listeners, reloading when notified.

## Testing:

### Outline:

1. Start server.
2. Start web app.
3. Connect to server
4. Load world
5. Launch a robot.
6. Save + Load world
7. Add + delete obstacles
8. Get + delete robots

### Expected outcomes:

1. Server starts successfully
2. App starts successfully
3. Able to connect to server
4. World loads successfully
5. Robot is launched
6. Able to save/load Worlds
7. Able to add/delete obstacles
8. Able to get/delete obstacles

## Built With

* [JAVA 11](https://www.oracle.com/za/java/technologies/javase-jdk11-downloads.html) - The language used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Flutter](https://flutter.dev/) - App 
* [SQLite](https://www.sqlite.org/) - Database

## Authors

* **Sarah Gerber**
* **Andreas Moller**
* **Adam Becker**
* **Zachary Glanville**

## Acknowledgments

2020 Team (Socket server, original functionality of world and robot):

* **Issa Kalonji** 
* **Thonifo Muhali**
* **Sarah Matsena**
* **Tsepo Moshole** 
