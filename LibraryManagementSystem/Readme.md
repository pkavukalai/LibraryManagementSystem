**Instructions to run the application :**
1. Unzip and Open the folder in intellij.
2. Build and Run Main.
3. Welcome message will be displayed in the console.
4. Follow the instructions displayed in the console.
5. To stop the application, press the stop button.

**Package structure :**<br>
_com.library.managementsystem :_ base package <br>
_controller :_ has controller class. This reads the user operations and perform actions.<br>
_operations :_ This has OperationsFactory class to create the Operation object. 
             This also has operations grouped as admin, display, user, authentication based on functionalities.<br>
_session :_ has SessionManagement class to save the active user in the session.<br>
_authorization :_ has AuthorizationUtil class to check whether the user is authorised to perform specific operation.<br>
_entities :_ has Book and User entity classes.<br>
_repositories :_ has Book, User and inventory repository classes with data access functions.<br>

**Database Info :**
_Note :_ 
This application uses static variables for data storage. 
Data will be stored in static variables. This will remain in storage till the application is running.
The Entities and repositories are modelled to mimic the RDBMS storage system.
The static storage can be replaced later with any RDBMS database.

_Entities used :_
Book
User 

_DB Repositories used :_
BookRepository - To access Book DB data
UserRepository - To access User DB data
InventoryRepository - To access inventory DB data

**Application Design Pattern :**
This application is designed as per MVC design pattern. 

**Other Design Patterns used in Application :**
1. Factory Design pattern
2. Builder Design pattern
3. Strategy Design pattern
4. Singleton Design pattern
5. Flyweight Design pattern
