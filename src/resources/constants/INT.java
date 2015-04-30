package resources.constants;

public class INT {

	public static final int FRAMERATE = 30;
	
	// SpriteImage / Rendering
	public static final int DEFAULT_IMAGE_FRAMERATE = 5;
	//SpriteEdit
	public static final int DEFAULT_IMAGE_SIZE = 50;
	
	//GameEdit
	public static final int GAMEEDITSCREEN_LEVEL_DISPLAY_SPACE = 160;
	public static final int DEFAULT_LEVEL_DISPLAY_WIDTH = 500;
	public static final int DEFAULT_LEVEL_DISPLAY_HEIGHT = 400;		
	public static final int GAMEEDIT_ADD_SIGN_DIM = 150;
	public static final int INITIAL_SETUP = 3;
	public static final int LEVEL = 1;
	public static final int SPLASH = 0;
	
	// SplashEdit
	public static final int SPLASH_EDIT_SCREEN_VERTICAL_SPACING = 40;
	public static final int SPLASH_EDIT_SCREEN_HORIZONTAL_SPACING = 40;
	public static final int SPLASH_EDIT_SCREEN_DISPLAY_WIDTH = 1300;
	public static final int SPLASH_EDIT_SCREEN_DISPLAY_HEIGHT = 637;
	public static final int SPLASH_EDIT_SCREEN_LARGE_BUTTON_WIDTH = 150;
	public static final int SPLASH_EDIT_SCREEN_LARGE_BUTTON_HEIGHT = 100;
	public static final int SPLASH_EDIT_SCREEN_SMALL_BUTTON_WIDTH = 150;
	public static final int SPLASH_EDIT_SCREEN_SMALL_BUTTON_HEIGHT = 50;
	public static final int SPLASH_EDIT_ADD_TEXT_VBOX_HEIGHT = 5;
	public static final int SPLASH_EDIT_COLOR_PICKER_HEIGHT = 25;
	public static final int SPLASH_EDIT_SCREEN_COMBO_BOX_WIDTH_X = 157;
	public static final int SPLASH_EDIT_SCREEN_COMBO_BOX_WIDTH_2X = 330;
	public static final int SPLASH_EDIT_SCREEN_COMBO_BOX_WIDTH = 150;
	public static final int SPLASH_EDIT_SCREEN_COMBO_BOX_HEIGHT = 50;
	public static final int SPLASH_EDIT_SCREEN_COMBO_BOX_GRID_LOCATION = 0;
	public static final int SPLASH_EDIT_BACK_BUTTON_HBOX_SPACING = 30;
	public static final int SPLASH_EDIT_TEXTFIELD_WIDTH = 150;
	public static final int SPLASH_EDIT_TEXTFIELD_HEIGHT = 30;
	public static final int SPLASH_EDIT_ROTATE_FACTOR = 15;
	public static final int SPLASH_EDIT_OFFSCREEN = 2000;
	
	
	// Collision table
	// [Sprite 1 (active)]		[Sprite 2]		[Direction   v]		[Action  v]		[Value]
	public static final int ACTIVE_SPRITE = 0;
	public static final int INACTIVE_SPRITE = 1;
	public static final int DIRECTION = 2;
	public static final int ACTION = 3;
	public static final int VALUE = 4;
	public static final int ACTION_STRING_LENGTH = STRING.COLLISION_EDIT.ACTION_NAME.length();
	public static final int ADD_ROW_BUTTON_OFFSET = 30;
	
	public static final int SAMPLE_DISPLAY_NUMBER = 3;
	
	public static final int EACH_ROW_VBOX_SIZE = 800;
	public static final int EACH_ROW_VBOX_OFFSET_Y = 50;
	public static final int EACH_ROW_VBOX_OFFSET_X = 100;
	public static final int EACH_ROW_GRIDPANE_HGAP = 30;
	public static final int EACH_ROW_GRIDPANE_VGAP = 30;
	public static final int EACH_ROW_GRIDPANE_MAX_HEIGHT = 50;
	public static final int EACH_ROW_GRIDPANE_INSETS_TOPBOTTOM = 10;
	public static final int EACH_ROW_GRIDPANE_INSETS_LEFTRIGHT = 50;
	
	public static final int TOP_ROW = 0;
	public static final int SPRITELIST_ROW = 1;
	public static final int ACTIVESPRITE_COLUMN = 1;
	public static final int INACTIVESPRITE_COLUMN = 2;
	public static final int DIRECTION_COLUMN = 3;
	public static final int ACTION_COLUMN = 4;
	public static final int TEXTVAL_COLUMN = 5;
	public static final int SAVE_BUTTON_COLUMN = 6;
	public static final int TRASH_BUTTON_COLUMN = 7;
	
	public static final int ADD_ROW_BUTTON_SIZE = 50;
	
	public static final int SAVE_BUTTON_SIZE = 100;
	public static final int TRASH_BUTTON_SIZE = 70;
	
	public static final int SCROLLPANE_VBOX_SIZE = 50;
	public static final int SCROLLPANE_TITLEBOX_SIZE = 800;
	
	// Collision Map: Map<String, Map<String, List<String>>>
	
	public static final int DIRECTION_INDEX = 0;
	public static final int ACTION_INDEX = 1;
	public static final int VALUE_INDEX = 2;
	public static final int SWITCH_OPTION_INDEX = 3;
	
	


	
	
	//MainMenu
	public static final int DEFAULT_BUTTON_WIDTH = 100;
	public static final int DEFAULT_BUTTON_HEIGHT = 50;
	public static final double DEFAULT_BUTTON_SPREAD = 30;
	
	// Level
	public static final int DEFAULT_LEVEL_WIDTH = 400;
	public static final int DEFAULT_LEVEL_HEIGHT = 150;
	public static final int DEFAULT_INCREASE_LEVEL_SIZE = 700;

	//Buttons
	public static final int PREF_BUTTON_WIDTH = 150;

	//Multiplayer
	public static final int LOCAL_PLAYER = 0;
	public static final int SECOND_PLAYER = 1;
	
	//Directions
    public static final int COLLISION_LEFT=0;
    public static final int COLLISION_RIGHT=1;
    public static final int COLLISION_UP=2;
    public static final int COLLISION_DOWN=3;

    //Coordinates
    public static final int X = 1;
    public static final int Y = 1;

    
    

	

}
