/**
 * @author TP
 * @date 09/17-10/17
 * 
 * GUI class for a user profile display<br>
 * 
 * Only accessible if the user is logged in
 */


package p1;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class ProfilePage
{
	/* ---------------------- */
	/* ----- ATTRIBUTES ----- */
	/* ---------------------- */
	
	/** Container VBox for profile page */	
	private VBox vbProfile = new VBox(50);
	
	/** Navigation bar instance */
	private NavBar nav = new NavBar();
	
	// All nodes necessary
	private Label lblUsername = new Label("Username:");
	private Label lblInfo = new Label();
	private ImageView imgProfile = new ImageView();
	private Image img;

	/** Timeline for gameloop method */
	private Timeline timeline;
	
	/* -------------------------------- */
	/* ----- METHODS/CONSTRUCTORS ----- */
	/* -------------------------------- */

	/**
	 * Default constructor for Profile page
	 */
	public ProfilePage()
	{	
		// Display the logged-in user's info
		lblUsername.setStyle("-fx-font-family: \"Arial\"; -fx-font-size: 3em; -fx-font-weight: bold");
		lblUsername.setText("Welcome, " + MainPage.sqlm.user.getName() + ".");
		lblInfo.setText("Email:" + MainPage.sqlm.user.getLoggedInEmail() + " \nPassword: " + MainPage.sqlm.user.getLoggedInPassword()
		                + "\nGood ratings:" + MainPage.sqlm.user.getRatingGood() + "\nBad ratings:" + MainPage.sqlm.user.getRatingBad()
		                + "\nItems sold: " + MainPage.sqlm.user.getItemsSold() + "\nRegistration date: " + MainPage.sqlm.user.getRegDate());
		lblInfo.setStyle("-fx-font-family: \"Arial\"; -fx-font-size: 1.2em; -fx-font-weight: bold");
		
		// Grab the user's profile image URI from the User class and set an ImageView object to contain this Image
		String imgURI = MainPage.sqlm.user.getPictureURI().toString();
		System.out.println(imgURI);
		img = new Image(imgURI, 350, 350, true, true);
		imgProfile.setImage(img);
		
		// Set VBox for use
		vbProfile.setAlignment(Pos.TOP_CENTER);
		vbProfile.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, null, null)));
		vbProfile.getChildren().addAll(nav.getNavBar(), lblUsername, lblInfo, imgProfile);

		// Start gameloop to update the navbar
		gameLoop();
	}

	
	/**
	 * Simple JavaFX timeline to update the navbar
	 */
	private void gameLoop()
	{
		timeline = new Timeline();
		// Set the game's timeline to be indefinite
		timeline.setCycleCount(Timeline.INDEFINITE);
		// Repeat the timeline cycle every 16ms, which equates to 1000ms / 16ms =~ 60fps
		// Every time it's enabled, update the navbar
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(16), e -> nav.setDisables(false, MainPage.sqlm.user.isLoggedIn(), false)));
		timeline.play(); 
	}
	
	
	/**
	 * Helper function- takes in a button and sets its text style
	 * 
	 * @param bt
	 */
	private void setTextStyle(Button bt)
	{
		bt.setStyle("-fx-font-family: \"Arial\"; -fx-font-size: 1.15em");
	}
	
	
	/**
	 * Getter
	 *
	 * @return vbProfile
	 */
	public VBox getRootPane()
	{
		return vbProfile;
	}
}
