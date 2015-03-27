Introduction
============

/* This section describes the problem your team is trying to solve by writing this program, the primary design goals of the project (i.e., where is it most flexible), and the primary architecture of the design (i.e., what is closed and what is open). It should also describe your chosen game genre and what qualities make it unique that your design will need to support. It should be approximately 300-600 words long and discuss the program at a high-level (i.e., without referencing specific classes, data structures, or code). */

In this project, we are developing a game engine and a game authoring environment. An example of this would be something like [GameSalad](http://gamesalad.com/). It allows programmers at any level (even with zero programming experience) to be able to develop a game which could be published on multiple platform. This is accomplished using a game engine which is a software framework designed for the creation and development of video games. The game authoring environment represents the user's workspace for developing the games to work on the game engine. Aside from that, the primary goal of the project is to create a game engine that is easily extensible. An example would be where if a certain sprite class is added to the game engine code, it's change is automatically reflected in the authoring environment with minimal to no code change. Aside from that, the parts of the program which is closed to extension would be the core game loop and specific implementations such as having a player automatically created on every level.

The game genre which we chose for this project are side-scrolling platformers. Side-scrolling platformers is a famous game genre which revolutionized the industry with games like Super Mario Bros and Prince Of Persia. The game mechanics are pretty simple where most of the times it would be to get the player to the end of the level by jumping on platforms without falling down to your doom. Aside from that, there are enemies which can attack the player. This creates new challenges for the player to keep their health up before reaching the end of the level. Lastly, platformers also have boss battles towards the end of specific level. Bosses normally have more health and also require a specific way to kill such as jumping on obstacles to drop on the boss.
 

Overview
=======

/* This section serves as a map of your design for other programmers to gain a general understanding of how and why the program was divided up, and how the individual parts work together to provide the desired functionality. As such, it should describe specific modules you intend to create, their purpose with regards to the program's functionality, and how they collaborate with each other, focusing specifically on each one's behavior. It should also include a picture of how the modules are related (these pictures can be hand drawn and scanned in, created with a standard drawing program, or screen shots from a UML design program). This section should be approximately 700-1000 words long and discuss specific classes, methods, and data 
structures, but not individual lines of code. */

<img src=”http://people.duke.edu/~sl290/vooga/design/ClassDiagram-VoogaSalad-overall.png”>
<img src=”http://people.duke.edu/~sl290/vooga/design/ClassDiagram-VoogaSalad-partGamePlayer.png”>
<img src=”http://people.duke.edu/~sl290/vooga/design/ClassDiagram-VoogaSalad-partSID.png”>

During our team design meeting, we boiled down the regular side-scrolling platformer to it's key elements, the player(jumping and left/right), the concept of an end goal, static platforms and enemies.

##Authoring Environment
For the Authoring Environment, GameAuthoringEnvironment class will initialize an object of a central control class that implements all the scene controller interfaces. Each of the interface  has all the methods that is specific to each screen such as, GameEdit, LevelEdit, SpriteEdit, as well as the stage to show all the scenes. There will also be a list of different scenes, to switch among those, this ScreenController will be casted into specific interface and adjust the scene to show on the stage accordingly. 

There will also be screen abstract classes specific to each screens. Those will be specific to setting up Buttons,  ObservableLists, bindings and etc. The general flow of the authoring environment would be as follows: authoring environment will start with a MainMenuScreen, which can create a new game, edit a game and load a previously created game. User will be able to move to GameEditScreen when edit game button is pressed. They could switch to either SplashEditScreen or LevelEditScreen. Both the two EditScreens can switch back to GameEditScreen. From LevelEditScreen, user could go to SpriteEditScreen. From SpriteEditScreen, users could go back to the SelectedLevelEdit().

The LevelEditScreen can contain a level object and the SpriteEditScreen can contain a Sprite object, as well as images it can attach to the Sprite object.  

##Game Engine
For the game engine, there is a lot of delegation to other classes to create representations of the game mechanics within the game. The GameEngine class takes in the file path of where the game properties are all locate. It then initializes the camera and the controls class. It also gets the Level from the XML. The game player then runs the game loop and calls the update() method and render method respectively. The update method goes through each of the sprites to update the it and it also checks for collision. The render creates the nodes from the render method in each sprite and put them into the group and returns it. The camera class defines the boundaries where the sprite could be seen by the user. Anything outside the camera, will not be seen by the player. However, the camera is centered to the player so when the player moves, it updates the boundaries to let the player see the new sprite.

The sprite class is the superclass of a sprite hierarchy which contains subclasses like blockSprite which contains the position and size of the boundary and the SpriteImage which contains a grid of integer which represents the RGB number at each pixel. Each sprite also contains a behavior. The behaviour is superclass which is part of a hierarchy where each behavior corresponds to a particular attribute on how the sprite moves or in more general how it reacts in the game. For example, the healthBehavior decrements in health when a collision occurs.

The collision class uses the strategy pattern to use an algorithm to detect the collision between 2 sprites. It takes in two sprites and uses a lookup table which is defined by the game designer in the game authoring environment to determine outcome of the collisions if a collision has occurred.

##Game Player
The GamePlayer is responsible for locating the folder of files needed for creating a game and parsing the name of the game. The GamePlayer also initializes the GameEngine and GamePlayerScene. In addition, the GamePlayer creates a Timeline that tells the engine to update and render the game.

##Game Data
The game data represents all of the information required to play the game. XStream will be used to convert the specific objects generated during the game into XML representations, and vice versa. Other files contain configuration data for the game, as well as data representing user preferences, such as image files and media files. Initially, the game will start out by loading all of this information when the user decides to start a new game.
 

User Interface
=============

/* This section describes how the user will interact with your program (keep it simple to start). It should describe the overall appearance of program's user interface components and how users interact with these components (especially those specific to your program, i.e., means of input other than menus or toolbars). It should also include one or more pictures of the user interface (these pictures can be hand drawn and scanned in, created with a standard drawing program, or screen shots from a dummy program that serves as a exemplar). Describe how a game is represented to the designer and what support is provided to make it easy to create a game. Finally, it should describe any erroneous situations that are reported to the user (i.e., bad input data, empty data, etc.). This section may be as long as it needs to be and go into as much detail as necessary to cover all your team wants to say. */

<img src=”http://people.duke.edu/~sl290/vooga/design/1.png”>
<img src=”http://people.duke.edu/~sl290/vooga/design/2.png”>
<img src=”http://people.duke.edu/~sl290/vooga/design/3.png”>
<img src=”http://people.duke.edu/~sl290/vooga/design/4.png”>
The user, upon starting our Game Authoring Environment, is presented with our Main Menu (class is MainMenu). The catchy, 8-bit version of Adele’s “Rolling in the Deep” plays in the background as the user contemplates his/her next move. Clicking “Blank Project” loads an empty GameEditScreen. “Open Recent” loads a Game from an XML for a given String.


Design Details
==============

/* This section describes each module introduced in the Overview in detail (as well as any other sub-modules that may be needed but are not significant to include in a high-level description of the program). It should describe how each module handles specific features given in the assignment specification, what resources it might use, how it collaborates with other modules, and how each could be extended to include additional requirements (from the assignment specification or discussed by your team). Note, each sub-team should have its own API for others in the overall team or for new team members to write extensions. Finally, justify the decision to create each module with respect to the design's key goals, principles, and abstractions. This section may be as long as it needs to be and go into as much detail as necessary to cover all your team wants to say. */

###Game Authoring Environment

The Game Authoring Environment class starts when the GameAuthoringEnvironment main class is initialized.  A ScreenController object that implements several interfaces will be initialized within that class.  Each of these interfaces contains methods that are specific to each screen, such as the Main Menu Screen, the Game Edit Screen, Level Edit Screen, Sprite Edit Screen, etc.  This ScreenController is a singleton design, but the methods are not publically accessible.  When we need to transition screens, the ScreenController will be cast to one of the interfaces that it implements, and methods will be called for that interface.  The first ScreenController method called when switching screens will grab the appropriate screen from a list of Screens in ScreenController, and switch the stage to the specified scene contained in the retrieved Screen object. 

####MainMenuScreenController:
```java
void createNewGame();

void loadGame();

void loadGameEditScreen();
```
####GameEditScreenController:
```java

void returnToMainMenuScreen();

void loadSplashEditScreen();

void loadLevelEditScreen();
```
####SplashEditScreenController:
```java
void returnToGameEditScreen();
```
####LevelEditScreenController:
```java
void returnToGameEditScreen();

void loadSpriteEditScreen();
```
####SpriteEditScreenController:

```java
void returnToSelectedLevel();
```

The Screen abstract class will be extended into the different types of Screen, and each Screen object will have a scene instance variable that allows for the scene to be set to the stage.  Screens will be in charge of setting up any Buttons, ObservableLists, bindings, and any general activity that happens in each screen. 

The Main Menu Screen is in charge of the screen that is displayed to the user upon first opening the authoring environment application.  From here, the user will be prompted to either create a new game or load an existing game.  Then, the user can move on to the Game Edit Screen.  This next screen is in charge of displaying to the user the current state of the game.  It displayed all the levels created, and allows a user to create splash screens or new levels, as well as to delete them.  From this Game Edit Screen, the user will be able to go to the screen of a specific level or a specific splash, or return back to the main menu.  Both the Splash Edit Screen and the Level Edit Screen will be able to return to the Game Edit Screen.  The Splash Edit Screen will allow a user to create a splash screen and save it to XML.  The Level Edit Screen allows the user to visually see the level that he/she has created and add sprites to it, and possibly behaviors.  The Sprite Edit Screen will definitely allow for behaviors to be attached, as well as images being attached to the sprites.  This screen will allow the user to return to the level screen that the user was currently working in.



###Game Engine
The Game Engine starts off in the GameEngine class where the initializations and the game loop is stored. In the constructor of this class, folder of the various XML files is passed through to be converted into the appropriate objects. Within the construction, the controls are initialized. There are 4 public methods in the game engine. Render() which uses functional programming to call the render method within each sprite to create a node which is put inside the view. Update() calls the update method within each sprite and it then also checks for collision for each of the player. The play and pause recouples and decouples the event handlers from the keys to make sure they don’t affect the game when it is paused.

The Controls class sets up the bindings for the keys in the game. The game folder would contain  a controls.xml file which defines a map<KeyCode, String> where the KeyCode would be the keys and the string would be the method it needs to be execute when the key is pressed. However implementations of the common controls of the platformer such as the movement key and weapon fire key is stored in the controls class.

The camera class is delegated by the game engine to handle the current view of the object. The camera object defines which JavaFX objects should be rendered within the screen. The camera object will have the dimensions of the game window to determine how much of the object will be within the view.

Objects within the game will be stored as instances of Sprite or any of its subclasses, containing information about the location, orientation, and functionality of the sprite.

In general, any logic/functionality/reactions pertaining to a Sprite will be held in a list of Behavior classes made for specific facets of  a sprite’s function within the game; examples may include health systems, capabilities, etc.

###Game Player
The game player starts up and initializes the GameLoader, GameEngine, and GamePlayerScreen. The GameLoader is able to load a folder that contains files necessary to play the game, and it is also able to show the available files present. The GameEngine instance runs the main bulk of the game. It takes in a folder representation in order to load the original game. The player then sets up the scene and take in the group from the game engine and renders it on screen. There is a menu bar on the top which would allow for various interaction with the game such as pausing and also loading another game file. The GamePlayer will also display the current score on the screen. The game player also controls the game loop using a Timeline and Animation. It is able to update the sprites in the game and render the view at different frequencies by calling methods belonging to the GameEngine. The GamePlayer itself is also responsible for pausing and playing the game loop.

###Game Data
Game data will be represented as a folder containing XML files each representing various objects, such as a Player, Enemy, Level, etc. All of the game data that corresponds to the specific objects created in the game should be represented by these XML files. The conversions made inside of the GameEngine, and will be performed by methods called in a utility GameLoader class. Additional data in a folder will include images (possibly corresponding to the Player and Enemy sprites) as well as media files corresponding to the music to be played during the game. A help page contains information that will contain instructions to help play the specific game. A properties file holds the properties for the configuration of the game itself, such as a screen layout, frame rate, as well as other details about the game that needed to be loaded in prior to playing.

Example Games
==============

/* Describe three example games from your genre in detail that differ significantly. Clearly identify how the functional differences in these games is supported by your design and enabled by your authoring environment. Use these examples to help make concrete the abstractions in your design. This section may be as long as it needs to be and go into as much detail as necessary to cover all your team wants to say. */

###Super Mario Bros
Nintendo’s beloved Super Mario Bros games are the classic example of side-scrolling games. Our game authoring environment will allow users to easily create a Super Mario Bros game. Players can place platforms on which Mario can run and jump, blocks which break if Mario collides with them from below, and enemies which die if Mario collides with them from above. Enemies in Mario games generally follow simple, back and forth motion patterns, or else keep walking in one particular direction until they fall off their platform. Powerups like mushrooms would increase Mario’s health (to simulate the game, mario could have a max healths of 1, 2, or 3 for little mario, big mario, or fire mario) Powerups like fire flowers could give Mario a weapon that fires projectiles. To support Pipes in Mario as well as any other teleportation, we need to implement a Teleporter sprite. 

###Mega Man
Capcom’s groundbreaking platforming series has offered players challenging platforming experiences for over 25 years. The games in the main Mega Man series have been formatted basically the same since its inception. At the very beginning, the player has the option to choose from 8 different levels to try and tackle. The player then traverses through the level playing as Mega Man, a player character whose main method of attack is shooting projectiles from his arm-turned-blaster. Mega Man encounters various enemies throughout his adventure, who often drop power-ups that increase his health or ability to use additional powers. At the end of each level, Mega Man encounters a boss. If he defeats the boss, Mega Man receives a new ability and the level is cleared. After all 8 levels are cleared, a 9th level is revealed and the game is beaten when the level is cleared. Our engine will be able to support projectile methods of attack, as well as a nonlinear progression through the game. In addition, Mega Man often employs vertical moving cameras, which our game engine will be able to implement.

###Metroid (NES Version)
Metroid is an action-adventure video game developed by Nintendo  that is based mostly on side-scrolling elements with a little twist. Unlike regular side scrolling, where the camera scrolls horizontally across the screen, Metroid implements camera’s that move vertically too. This opens the player to also traverse up and down through the level. In our game, we would have camera object that could move vertically and horizontally that would support this unique extension in our game.

Design Considerations
================

/* This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution. It should include any design decisions that each sub-team discussed at length (include pros and cons from all sides of the discussion) as well as any ambiguities, assumptions, or dependencies regarding the program that impact the overall design. This section may be as long as it needs to be and go into as much detail as necessary to cover all your team wants to say. */

One part of our design on which opinions differed was how many files to save for a level when it is created in our Authoring environment. The simplest idea was to have one file per level, and all of the sprites would be contained within the level. However, we wanted to allow users to create a sprite in one game and reuse it in another. Because of this, the idea was proposed to save not only the level file but also an additional file for each sprite. The problems with this, though, are first that it may create a lot of unnecessary files and second that it would allow a sprite to be changed in its own file but not in the level file. Our solution is to separate the ideas of saving a game and saving a sprite, so that when the user saves a game they save one file per level, like our original simple plan. However, in the game authoring environment there will also be save/load Sprite options to save a particular sprite and load it into another game. 
One limitation of the XStream is that JavaFX objects cannot be stored in the data files that it creates. Because of this, we have decided to forego the traditional Model-View design. Instead, Sprites are objects that are created in the Game Authoring Environment, stored in the data file, and used in the back-end. Where do the javaFX objects come in? Each Sprite has a makeView method that returns a Node, and each frame the makeView method is called on each Sprite and all the Nodes are added to the Group which is displayed on the screen. The downside to this solution was that it goes against the general rule most of us have been following, that the back-end Classes should not use javaFX objects. 
Given that JavaFX objects are created through the makeView method, a tricky design decision was how to display images. While the game is being played, every sprite is destroyed and recreated in it’s new location (or with its new state) each frame. This is done by calling each Sprite’s makeView method and adding the Node returned to a group. If, each time makeView was called, every Sprite with an image had to load this image into the program, our games would all be extremely slow. However, simply having each Sprite hold it’s image as a javaFX Image is also not an option, as this would make each Sprite too large and we would be unable to write the Sprites into a file with XStream. After discussing this problem, our solution was to have an ImageHolder class which has a String-Image map. In the Sprite’s makeView, a method of ImageHolder is called to get an Image for a particular String. The first time this method is called for a particular string, the image file is loaded and the String, Image pair is placed in the map. 
One part of our project that still requires a good bit of thought is how we are detecting and handling collisions. If we design our collision-detection well, it could make many aspects of the game much easier to implement. For instance, the way players land on platforms, whether or not a player can jump onto a platform from below, and how the player attacks/is attacked by enemies is all dependent on how we handle our collisions. Much of our design on the specifics of this issue has yet to be decided. We will need a way to not only detect if there is a collision but also detect the spatial relationship of the two nodes colliding (for example, in Mario, if mario jumps on top of an enemy. the enemy dies, but if he collides from the side, Mario is hurt). We will also need a design that is extensible to many shapes, not just rectangles. Right now the only really concrete idea we have about collisions is that we only need to check whether the movable objects have collided with (any) other object, as two stationary objects cannot collide. 


This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution. It should include any design decisions that each sub-team discussed at length (include pros and cons from all sides of the discussion) as well as any ambiguities, assumptions, or dependencies regarding the program that impact the overall design. This section may be as long as it needs to be and go into as much detail as necessary to cover all your team wants to say.


Team Responsibilities
================

/* This section describes the program modules each team member plans to take primary and secondary responsibility for and a high-level plan of how the team will complete the program. */

Our team is very responsible. 

Please see [this excel sheet](https://docs.google.com/spreadsheets/d/1BjqZdk9ETorizzsTm_wtmxBzu4f95y0C4S7KossI7rQ/edit#gid=0).
