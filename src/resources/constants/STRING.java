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
	
	public class IMAGE {

		// (private) prefix
		private static final String ImagePrefix = "resources/images/";
		// Image Names
		public static final String SPRITEIMAGE = "sprite.jpg";
		public static final String SPLASH_TMP = "sprite.jpg";
		
		
		public class GAME_EDIT_IMAGES {

			// GameEdit: image path
			// (private) prefix
			private static final String GEIPrefix = "resources/images/GameEdit/";
			// Image Names
			public static final String ADD_IMG = GEIPrefix + "addsplash.png";
			public static final String BACK_IMG = GEIPrefix + "back.png";
			public static final String LEVEL1IMAGE = GEIPrefix + "level1_tmp.PNG";
			public static final String LEVEL2IMAGE = GEIPrefix + "level2_tmp.PNG";
			public static final String PLAY_IMG = GEIPrefix + "play.png";
			public static final String PLUS_IMG = GEIPrefix + "plus_sign.png";
			public static final String TRASH_IMG = GEIPrefix + "trash_sign.png";
			public static final String TRASH_ICON = GEIPrefix + "trashicon.png";

		}

	}
	
	public class GAME_EDIT {
		
		//GameEdit Screen
		public static final String GAME_EDIT = "Game Edit";
		public static final String SPLASH_SCREEN = "Splash Screen";
		public static final String NOTE = "* Note: Double Left Click to Edit Level/Splash, Right click to remove/edit";

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
