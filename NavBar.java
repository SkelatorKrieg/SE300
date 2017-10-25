/**
 * 
* @author TP
 * @date 09/27/17
 * 
 * Custom navbar class...
 * 
 */


package p1;
import java.util.Objects;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class NavBar 
{
	/* ---------------------- */
	/* ----- ATTRIBUTES ----- */
	/* ---------------------- */
	
	// Nodes necessary for the NavBar
	private VBox vbNav = new VBox(15);
	private GridPane gpNav = new GridPane();
	private Button btHome = new Button("Home");
	private ComboBox<String> cbBuying = new ComboBox<>();
	private Button btSelling = new Button("Selling");
	private Button btLogin = new Button("Login");
	private Button btLogout = new Button("Logout");
	private Button btProfile = new Button("Profile");
	private TextField tfSearchBar = new TextField();
	private DropShadow dropShadowButton = new DropShadow(5.0, 3.0, 3.0, Color.CADETBLUE);
	
	
	/* -------------------------------- */
	/* ----- METHODS/CONSTRUCTORS ----- */
	/* -------------------------------- */
	
	// TODO
	// Consolidate GameLoop method here
	
	/**
	 * Default constructor for NavBar
	 */
	public NavBar()
	{
		// Make text pretty
		setTextStyle(btHome);
		btHome.setEffect(dropShadowButton);
		setTextStyle(btSelling);
		btSelling.setEffect(dropShadowButton);
		setTextStyle(btLogin);
		btLogin.setEffect(dropShadowButton);
		setTextStyle(btProfile);
		btProfile.setEffect(dropShadowButton);
		setTextStyle(btLogout);
		btLogout.setEffect(dropShadowButton);
	
		// Add items to combobox
		cbBuying.setStyle("-fx-font-family: \"Arial\"; -fx-font-size: 1.15em");
		cbBuying.setEffect(dropShadowButton);
		cbBuying.setPromptText("Buying");
		cbBuying.getItems().addAll("Books", "Vehicles", "Furniture", "Rooms");
		
		// Search bar setup
		tfSearchBar.setMaxWidth(400);
		tfSearchBar.setPromptText("What are you looking for...");
		tfSearchBar.setEffect(dropShadowButton);
		
		// Set up the gridpane's content
		gpNav.add(btHome, 0, 0);
		gpNav.add(cbBuying, 1, 0);
		gpNav.add(btSelling, 2, 0);
		gpNav.add(btLogin, 3, 0);
		gpNav.add(btProfile, 4, 0);
		gpNav.add(btLogout, 5, 0);
		gpNav.setHgap(10);
		gpNav.setVgap(15);
		gpNav.setAlignment(Pos.CENTER);
		
		// Set up the VBox to be displayed
		VBox.setMargin(vbNav, new Insets(0, 0, 15, 0));
		vbNav.getChildren().addAll(gpNav, tfSearchBar);
		vbNav.setAlignment(Pos.CENTER);
		
		// Return home functionality!
		btHome.setOnAction(e -> 
		{
			MainPage.instance.getStage().getScene().setRoot(MainPage.instance.getMainVBox());
			
			// FIXME
			// When going to home page, the prompt text in the combo box does not say "Buying"
			cbBuying.setPromptText("Buying");
		});
			

		// Functionality for combobox drop down items
		// Incredibly ugly, but it works as intended
		cbBuying.setOnAction(e -> 
		{
			String value = cbBuying.getValue();
			
			if(Objects.equals(value, "Books"))
			{
				ItemPage obj = new ItemPage("Books");
				MainPage.instance.getStage().getScene().setRoot(obj.getRootPane());
			}
			if(Objects.equals(value, "Vehicles"))
			{
				ItemPage obj = new ItemPage("Vehicles");
				MainPage.instance.getStage().getScene().setRoot(obj.getRootPane());
			}
			if(Objects.equals(value, "Furniture"))
			{
				ItemPage obj = new ItemPage("Furniture");
				MainPage.instance.getStage().getScene().setRoot(obj.getRootPane());
			}
			if(Objects.equals(value, "Rooms"))
			{
				ItemPage obj = new ItemPage("Rooms");
				MainPage.instance.getStage().getScene().setRoot(obj.getRootPane());
			}
		});
		
		
		// Login and profile page functionality
		btLogin.setOnAction(e -> loginButtonClick());
		btProfile.setOnAction(e-> profileButtonClick());
		btLogout.setOnAction(e -> logoutButtonClick());
		// Selling button functionality
		btSelling.setOnAction(e -> sellingButtonClick());
	}
	
	
	/**
	 * Method used to dynamically set the disabled values of each button in the NavBar<br>
	 * Called in the GameLoop methods of each Page class
	 * 
	 * @param isHome
	 * @param isLoggedIn
	 * @param isSearchable
	 */
	public void setDisables(boolean isHome, boolean isLoggedIn, boolean isSearchable)
	{
		// Are we home? Disable home button
		if (isHome)
		{
			btHome.setDisable(true);
		}
		
		else
		{
			btHome.setDisable(false);
		}
		
		// Are we logged in?
		if (!isLoggedIn)
		{
			btSelling.setDisable(true);
			btProfile.setDisable(true);
			btLogin.setDisable(false);
			btLogout.setDisable(true);
		}
		
		else
		{
			btSelling.setDisable(false);
			btProfile.setDisable(false);
			btLogin.setDisable(true);
			btLogout.setDisable(false);
		}
		
		// If we are in the profile/login/specific item pages, we don't need a search bar
		if (!isSearchable)
		{
			tfSearchBar.setDisable(true);
		}
		
		else
		{
			tfSearchBar.setDisable(false);
		}
	}
	
	
	public VBox getNavBar()
	{
		return vbNav;
	}
	
	
	private void sellingButtonClick()
	{
		SellingPage obj = new SellingPage();
		MainPage.instance.getStage().getScene().setRoot(obj.getRootPane());
	}
	
	
	private void loginButtonClick()
	{
		LoginPage obj = new LoginPage();
		MainPage.instance.getStage().getScene().setRoot(obj.getRootPane());
	}
	
	
	private void profileButtonClick()
	{
		ProfilePage obj = new ProfilePage();
		MainPage.instance.getStage().getScene().setRoot(obj.getRootPane());
	}
	
	
	/**
	 * Functionality for logout button click<br>
	 * Displays an alert and logs the user out by setting the isLoggedIn variable in the User class to false
	 */
	public void logoutButtonClick() 
	{
		// Create a confirmation alert
		Alert alertLogout = new Alert(AlertType.CONFIRMATION, "Are you sure?");
		alertLogout.setTitle("Logging out...");
		alertLogout.setHeaderText(null);		
	
		// Getting the proper functionality out of the alert
		// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
		Optional<ButtonType> result = alertLogout.showAndWait();
		
		if (result.isPresent() && result.get() == ButtonType.OK)
		{
			MainPage.sqlm.user.logout();
			// Return to main page
			MainPage.instance.getStage().getScene().setRoot(MainPage.instance.getMainVBox());
			System.out.println("Logout successful!");
		}
		
		else
		{
			alertLogout.close();
		}
	}
	
	
	private void setTextStyle(Button bt)
	{
		bt.setStyle("-fx-font-family: \"Arial\"; -fx-font-size: 1.15em");
	}
}
