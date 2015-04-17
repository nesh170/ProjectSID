package resources.constants;

import java.util.HashMap;
import java.util.Map;

public class STRING {

	// Generally Reused Strings / To Sort
	public static final String DEFAULT_GAME_NAME = "(untitled)";

	public class COLORS {
		
		// Colors
		public static final String DEFAULT_FX_BACKGROUND_COLOR = "CORNFLOWERBLUE;";
		public static final String FX_BACKGROUND_COLOR_PREDICATE = "-fx-background-color: ";
		public static final String FX_GAME_EDIT_BACKGROUND = "-fx-background-color: rgba(241, 241, 241, 1);";
		public static final String FX_RED_BACKGROUND = "-fx-background-color: red;";
		
	}
	
	public class ERROR {
		
		// Errors
		public static final String CLICK_TO_DISMISS = " (Click to dismiss)";
		public static final String ILLEGAL_FILE_PATH = "Illegal File Path Error";
		
	}
	
	public class MUSIC {
	
		// Music Playback
		public static final String MUSIC_OPTIONS = "Music Options";
		public static final String PLAY = "Play";
		public static final String PAUSE = "Pause";
		public static final String STOP = "Stop";
		
	}
	
	public class BUTTONS {
		
		// Buttons
		public static final String BUTTON_STYLE = 
				"-fx-font: 14 georgia; -fx-text-fill: black;  "
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); "
				+ "-fx-border-width: 2 2 2 2; -fx-border-color: #006652; -fx-background-color: white;";
		public static final String PRESSED_BUTTON_CSS = "-fx-font: 14 georgia; -fx-text-fill: #CC0000;  -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) ,.5, 0.0 , 0 , 1 ); -fx-border-width: 2 2 2 2; -fx-border-color: white; -fx-background-color: black;";
		public static final String RELEASED_BUTTON_CSS = "-fx-font: 14 georgia; -fx-text-fill: black;  -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-border-width: 2 2 2 2; -fx-border-color: #006652; -fx-background-color: white;";
		
		
	}
	
	public class MAIN_MENU_SCREEN {
		
		// Main Menu Screen
		public static final String MAIN_MENU = "Main Menu";
		public static final String FILE = "File";
		public static final String NEW = "New";
		public static final String OPEN = "Open";
		public static final String CLOSE = "Close";
		public static final String NEWGAME = "New Game";
		public static final String LOADGAME = "Load Game";
		public static final String ENTERGNAME = "Enter your game name.";
		public static final String ENTERGDESCRIPTION = "Enter your game description";
		public static final String NAMELABEL = "              Game Name: ";
		public static final String DESCRIPTIONLABEL = "       Game Description: ";
		//image path
		public static final String POPUP ="images/GameEdit_Images/popup.png";
	}

	public class SPLASH_EDIT_SCREEN {
		
		// Splash Edit Screen Buttons
		public static final String BACK = "Back";
		public static final String TRASH = "Trash";
		public static final String SAVE = "Save";
		public static final String ADD_START_BUTTON = "Add Start Button";
		public static final String ADD_IMAGE = "Add Image";
		public static final String ADD_BACKGROUND_IMAGE = "Add Background Image";
		public static final String ADD_TEXT = "Add Text";
		public static final String ADD_ANIMATION = "Add Animation";
		
		public static final String ADD_LEVEL = "Add Level";
		public static final String EDIT_LEVEL = "Edit Level";
		public static final String ADD_SPLASH = "Add Splash";
		public static final String EDIT_SPLASH = "Edit Splash";
		
		// Splash Edit Screen Default Images
		// TODO: Fix this
		public static final String DEFAULT_START_BUTTON_IMAGE = "/Users/kam237/Documents/workspace308/voogasalad_ScrollingDeep/src/images/sprite.jpg";
		
		
	}
	
	public class GAME_EDIT {
		
		//GameEdit Screen
		public static final String GAME_EDIT = "Game Edit";
		public static final String SPLASH_SCREEN = "Splash Screen";
		public static final String NOTE = "* Note: Double Left Click to Edit Level/Splash, Right click to remove/edit";
		public static final String FONT_POPUP = "-fx-font: 18 serif;";
		//GameEdit: image path
		public static final String ADD_IMG ="images/GameEdit_Images/addsplash.png";
		public static final String TRASH_ICON = "images/GameEdit_Images/trashicon.png";
		public static final String LEVEL1IMAGE = "images/GameEdit_Images/level1_tmp.PNG";
		public static final String LEVEL2IMAGE = "images/GameEdit_Images/level2_tmp.PNG";
		public static final String SPRITEIMAGE = "images/sprite.jpg";
		public static final String BACK_IMG = "images/GameEdit_Images/back.png";
		public static final String SPLASH_TMP = "images/sprite.jpg";
		public static final String PLUS_IMG = "images/GameEdit_Images/plus_sign.png";
		public static final String PLUS_DOWN_IMG = "images/GameEdit_Images/plus_sign_down.png";
		public static final String PLAY_IMG = "images/GameEdit_Images/play.png";
		public static final String TRASH_IMG = "images/GameEdit_Images/trash_sign.png";
		public static final String POPUP_SAVE = "Do you want to save the game before returning to the mainmenu ? ";
		
	}
	
	public class LEVEL_EDIT {
		
		//Level Edit Screen Buttons, Menus, miscellaneous strings
		public static final String ADD_SPRITE = "Add New Sprite";
		public static final String PLATFORMS = "Platforms";
		public static final String ENEMIES = "Enemies";
		public static final String PLAYERS = "Players";
		public static final String LEVEL_EDIT = "Level Edit Screen";
		
	}
	
	public class SPRITE_EDIT {
		
		//Sprite Edit Strings
		public static final String SPRITE_EDIT = "Sprite Edit Screen";
		
	}
	
	public class COLLISION_EDIT {
		
		public static final String COLLISION_TABLE_EDIT = "Collision Table Edit Screen";
		
	}
	
	public class GAME_PLAY {
		
		// GamePlayScren
	    public static final String GAME_PLAY = "Game Play";
		
	}
	
	public class GAME_ENGINE {
		
		//Game Engine Constants
		public static final String PLAYER_SPRITE = "playersprite";
		
	}
	
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
    
}
