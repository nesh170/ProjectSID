package resources.constants;

import java.util.HashMap;
import java.util.Map;

public class STRING {

	// Colors
	public static final String DEFAULT_FX_BACKGROUND_COLOR = "CORNFLOWERBLUE;";
	public static final String FX_BACKGROUND_COLOR_PREDICATE = "-fx-background-color: ";
	public static final String FX_GAME_EDIT_BACKGROUND = "-fx-background-color: rgba(241, 241, 241, 1);";
	public static final String FX_RED_BACKGROUND = "-fx-background-color: red;";
	
	// Screen Controller
	public static final String CLICK_TO_DISMISS = " (Click to dismiss)";
	
	// Music Playback
	public static final String MUSIC_OPTIONS = "Music Options";
	public static final String PLAY = "Play";
	public static final String PAUSE = "Pause";
	public static final String STOP = "Stop";
	
	// Main Menu Screen
	public static final String FILE = "File";
	public static final String NEW = "New";
	public static final String OPEN = "Open";
	public static final String CLOSE = "Close";
	public static final String NEWGAME = "New Game";
	public static final String LOADGAME = "Load Game";
	
	// Splash Edit Screen Buttons
	public static final String BACK = "Back";
	public static final String TRASH = "Trash";
	public static final String SAVE = "Save";
	public static final String ADD_START_BUTTON = "Add Start Button";
	public static final String ADD_IMAGE = "Add Background Image";
	public static final String ADD_TEXT = "Add Text";
	public static final String ADD_ANIMATION = "Add Animation";
	
	//GameEdit Screen
	public static final String ADD_SIGN ="images/addsplash.png";
	public static final String TRASH_ICON = "images/trashicon.png";
	public static final String LEVEL1IMAGE = "images/level2_tmp.PNG";
	public static final String LEVEL2IMAGE = "images/level2_tmp.PNG";
	public static final String SPRITEIMAGE = "images/sprite.jpg";
	public static final String SPLASH_SCREEN = "Splash Screen";
	
	// Splash Edit Screen Default Images
	public static final String DEFAULT_START_BUTTON_IMAGE = "/Users/kam237/Documents/workspace308/voogasalad_ScrollingDeep/src/images/sprite.jpg";
	
	//Level Edit Screen Buttons, Menus, miscellaneous strings
	public static final String ADD_SPRITE = "Add New Sprite";
	public static final String PLATFORMS = "Platforms";
	public static final String ENEMIES = "Enemies";
	public static final String PLAYERS = "Players";
	public static final String LEVEL_EDIT = "Level Edit Screen";
	
	
	//Sprite Edit Strings
	public static final String SPRITE_EDIT = "Sprite Edit Screen";
	
	
	
	// Buttons
	public static final String BUTTON_STYLE = 
			"-fx-font: 14 georgia; -fx-text-fill: black;  "
			+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); "
			+ "-fx-border-width: 2 2 2 2; -fx-border-color: #006652; -fx-background-color: white;";
	public static final String PRESSED_BUTTON_CSS = "-fx-font: 14 georgia; -fx-text-fill: #CC0000;  -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) ,.5, 0.0 , 0 , 1 ); -fx-border-width: 2 2 2 2; -fx-border-color: white; -fx-background-color: black;";
	public static final String RELEASED_BUTTON_CSS = "-fx-font: 14 georgia; -fx-text-fill: black;  -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-border-width: 2 2 2 2; -fx-border-color: #006652; -fx-background-color: white;";
	
	
	//new String[]{"Add Level", "Edit Level", "Add Splash", "Edit Splash", "Remove Level"};
	public static final Map<String, String> LEVELS_SPLASH_MAP;
    static
    {
    	LEVELS_SPLASH_MAP = new HashMap<String, String>();
    	LEVELS_SPLASH_MAP.put("ADD_LEVEL", "Add Level");
    	LEVELS_SPLASH_MAP.put("EDIT_LEVEL", "Edit Level");
    	LEVELS_SPLASH_MAP.put("ADD_SPLASH", "Add Splash");
    	LEVELS_SPLASH_MAP.put("EDIT_SPLASH", "Edit Splash");
    	LEVELS_SPLASH_MAP.put("REMOVE_LEVEL", "Remove Level");
    }
	
	//Game Engine Constants
	public static final String PLAYER_SPRITE = "playersprite";
}
