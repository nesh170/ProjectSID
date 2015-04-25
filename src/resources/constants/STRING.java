package resources.constants;

import java.util.ArrayList;
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
		public static final String FX_MAIN_MENU_BUTTON = "-fx-background-color: rgba(241, 241, 241, 0.8); -fx-background-radius: 10;";
		public static final String FX_GAME_EDIT_BUTTON_RADIUS = " -fx-background-radius: 15;";
		
	}
	
	public class ERROR {
		
		// Errors
		public static final String CLICK_TO_DISMISS = " (Click to dismiss)";
		public static final String ILLEGAL_FILE_PATH = "Illegal File Path Error";
		public static final String EMPTY_GAME_NAME = "Please Enter Game Name";
		
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
	
	public class CSS {
		public static final String ERROR = "text-field-error";
		public static final String PANE = "pane";
	}
	
	public class REGEX {
		public static final String ANY_CHAR = ".+";
	}
	
	public class EXT {
		public static final String XML = ".xml";
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
		public static final String ENTER_IMAGE_INDEX = "Enter Image Index:";
		public static final String ENTER_TEXT_CONTENT = "Enter Text Content:";
		public static final String TIMES = "Times";
		public static final String ARIAL = "Arial";
		public static final String VERDANA = "Verdana";
		public static final String CENTURY_GOTHIC = "Century Gothic";
		public static final String MARKER_FELT = "Marker Felt";
		public static final String MONOTYPE_CORSIVA = "Monotype Corsiva";
		public static final String APPLE_CHANCERY = "Apple Chancery";
		public static final String JPG_LONG = "JPG files (*.jpg)";
		public static final String JPG_SHORT = "*.JPG";
		public static final String PNG_LONG = "PNG files (*.png)";
		public static final String PNG_SHORT = "*.PNG";
		public static final String TAG_START = "Start";
		public static final String TAG_IMAGE = "Image";
		public static final String TAG_TEXT = "Text";
		public static final String TAG_BACKGROUND_IMAGE = "Background Image";
		public static final String TRASH_START_BUTTON = "Start Button";
		public static final String TRASH_IMAGE = "Image";
		public static final String TRASH_TEXT = "Text";
		
		
		
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
		public static final String SPLASH_TMP = "images/GameEdit_Images/Splash_tmp.PNG";
		public static final String PLUS_IMG = "images/GameEdit_Images/plus_sign.png";
		public static final String PLUSDOWN_IMG = "images/GameEdit_Images/plus_sign_down.png";
		public static final String PLAY_IMG = "images/GameEdit_Images/play.png";
		public static final String PLAYDOWN_IMG = "images/GameEdit_Images/play_down.png";
		public static final String BACK_IMG = "images/GameEdit_Images/back.png";
		public static final String BACKDOWN_IMG = "images/GameEdit_Images/back_down.png";
		public static final String TRASH_IMG = "images/GameEdit_Images/trash_sign.png";
		public static final String POPUP_SAVE = "Do you want to save the game before returning to the mainmenu ? ";
		public static final String IMAGE_FOLDER ="-images";
			
	}
	
	public class LEVEL_EDIT {
		
		//Level Edit Screen Buttons, Menus, miscellaneous strings
		public static final String ADD_SPRITE = "Add New Sprite";
		public static final String EDIT_SELECTED_SPRITE = "Edit Selected Sprite";
		public static final String PLATFORMS = "Platforms";
		public static final String ENEMIES = "Enemies";
		public static final String PLAYERS = "Players";
		public static final String LEVEL_EDIT = "Level Edit Screen";
		
	}
	
	public class SPRITE_EDIT {
		
		//Sprite Edit Strings
		public static final String SPRITE_EDIT = "Sprite Edit Screen";
		public static final String NAME = "Name";
		public static final String SPRITE_PROMPT = "SpritePrompt";
		public static final String IMAGE_PROMPT = "ImagePrompt";
		
	}
	
	public class COLLISION_EDIT {
		
		public static final String COLLISION_TABLE_EDIT = "Collision Table Edit Screen";
		public static final String COLLISION_SCREEN_TITLE ="images/CollisionTableImages/collisionTableTitle.png";
		public static final String FONT_STYLE = "-fx-font: 15px \"arial\";";
		public static final String SAVE_BUTTON_IMG ="images/CollisionTableImages/savedCheckMark.png";
		public static final String SAVE_BUTTON_PRESSED_IMG ="images/CollisionTableImages/savedCheckMarkPressed.png";
		public static final String ADD_BUTTON_IMG ="images/CollisionTableImages/addGreen.png";
		public static final String ADD_BUTTON_PRESSED_IMG ="images/CollisionTableImages/addCopper.png";
		public static final String TRASH_BUTTON_IMG ="images/CollisionTableImages/trashRed.png";
		public static final String TRASH_BUTTON_PRESSED_IMG ="images/CollisionTableImages/trashBlack.png";
		public static final String BOTTOM_ROW_STYLE = "-fx-background-color: radial-gradient(focus-angle 135deg, focus-distance 20%, center 25% 25%, radius 50%, reflect, purple, gold 75%, salmon);";
		public static final String TEXT_BOX_STYLE = " -fx-background-color: transparent; -fx-border-color: #000;";
		public static final String BACKGROUND_STYLE = "-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #e6c9ff, #fffac9);";
		
		public static final String COMBO_SPRITE1_NAME = "Sprite Active";
		public static final String COMBO_SPRITE1_TAG = "SpriteActive";
		public static final String COMBO_SPRITE2_NAME = "Sprite Inactive";
		public static final String COMBO_SPRITE2_TAG = "SpriteInactive";
		public static final String COMBO_DIRECTION_NAME_AND_TAG = "Direction";
		public static final String COMBO_ACTION_NAME_AND_TAG = "Action";
		public static final String TEXT_PROMPT = "Value";
		public static final String SAVE_BUTTON_TEXT = "Save";
		
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
    
    public static final Map<String, Integer> DIRECTION_TO_INTEGER_MAP;

    static
    {
    	DIRECTION_TO_INTEGER_MAP = new HashMap<String, Integer>();
    	DIRECTION_TO_INTEGER_MAP.put("Above", INT.COLLISION_UP);
    	DIRECTION_TO_INTEGER_MAP.put("Below", INT.COLLISION_DOWN);
    	DIRECTION_TO_INTEGER_MAP.put("Left", INT.COLLISION_LEFT);
    	DIRECTION_TO_INTEGER_MAP.put("Right", INT.COLLISION_RIGHT);
    }
}
