package screen.tab;

import screen.Screen;
import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/***
 * Lets 
 * @author Michael, Ruslan 
 */
public class TabManager {
	
	private TabPane tabPane;
	private SingleSelectionModel<Tab> singleSelectionModel;
	
	private Tab defaultTab;
	
	public TabManager(TabPane tabPane) {
		this.tabPane = tabPane;
		singleSelectionModel = tabPane.getSelectionModel();
	}
	
	/**
	 * Method for adding new Tab items
	 * 
	 * @param Screen (to add)
	 * @param String (title)
	 */
	public Tab addTabWithScreenWithStringIdentifier(Screen screen, String string) {

		Tab returnTab = new Tab();

		returnTab.setText(string);
		returnTab.setId(string);
		
		returnTab.setContent(screen);
		returnTab.onClosedProperty().set(e -> setCorrectTabModifiabilityAndViewability());

		tabPane.getTabs().add(returnTab);
		
		setCorrectTabModifiabilityAndViewability();
		
		return returnTab;

	}
	
	public void removeTabAndChangeSelected(Tab oldTab) {
		
		Tab tab = singleSelectionModel.getSelectedItem();
		tabPane.getTabs().remove(tab);
		
		setCorrectTabModifiabilityAndViewability();		
		singleSelectionModel.select(oldTab);
		
	}
	
	public SingleSelectionModel<Tab> getTabSelectionModel() {
		return singleSelectionModel;
	}
	
	public void removeTab(Tab tab) {
		tabPane.getTabs().remove(tab);
	}
	
	public void changeToDefault() {
		if (defaultTab != null) {
			removeTabAndChangeSelected(defaultTab);
			defaultTab.setClosable(false);
		}
	}
	
	
	public void setDefaultTab(Tab tab) {
		defaultTab = tab;
		defaultTab.setClosable(false);
	}
	
	// Private
	/**
	 * Take all tabs except the current/last one and make them unmodifiable. Make the current tab modifiable
	 * 
	 * @author Ruslan
	 */
	private void setCorrectTabModifiabilityAndViewability() {
		
		int tabPaneLastIndex = tabPane.getTabs().size() - 1;
		
		ObservableList<Tab> tabs = tabPane.getTabs();
		
		// All Tab(s) except the last one
		for (int i=0; i < tabPaneLastIndex; i++) {
			disableTab(tabs.get(i));
		}
		
		Tab nextTab = tabs.get(tabPaneLastIndex);
		enableTab(nextTab);
	
	}
	
	private void disableTab(Tab tab) {
	
		tab.setClosable(false);
		tab.setDisable(true);
		
	}
	
	private void enableTab(Tab tab) {
		
		tab.setClosable(true);
		tab.setDisable(false);
		
		// Selects current, enabled tab
		// http://stackoverflow.com/questions/6902377/javafx-tabpane-how-to-set-the-selected-tab
		singleSelectionModel.select(tab);
		if (tab.getContent() instanceof Screen) {
			Screen screen = (Screen) tab.getContent();
			System.out.println("Rerender");
			screen.rerender();
		}
		
	}

	
}
