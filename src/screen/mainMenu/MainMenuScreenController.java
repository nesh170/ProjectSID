package screen.mainMenu;

public interface MainMenuScreenController {
	
	/*
	 * Creates a new game and loads the game to the game editing screen
	 */
	public void createNewGame();
	
	void loadGame();
	
	void loadGameEditScreen(String recentGameName);
	
	/*
	 * Takes the file path to a directory containing a game and loads it to
	 * the game editing screen
	 */
	public void loadGame(String gameFile);
	
}
