package p1;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class ItemPage 
{
	/* ---------------------- */
	/* ----- ATTRIBUTES ----- */
	/* ---------------------- */
	
	/** Container VBox for item page */	
	protected VBox vbItemPage = new VBox(10);
	
	/** Navigation bar instance */
	private NavBar nav = new NavBar();
	
	/** Title label */
	protected Label lblItem = new Label();
	
	//\All the necessary nodes
	protected GridPane gpItems = new GridPane();
	protected ScrollPane spItems = new ScrollPane();
	protected VBox vbSort = new VBox();
	protected Label lblSortBy = new Label("Sort by:");
	protected ComboBox<String> cbSortBy = new ComboBox<>();
	protected Label lblPostTime = new Label("Posted within:");
	protected ComboBox<String> cbPostTime = new ComboBox<>();
	private DropShadow dropShadowButton = new DropShadow(5.0, 3.0, 3.0, Color.CADETBLUE);
	
	/** Timeline for gameloop method */
	private Timeline timeline;
	
	/* -------------------------------- */
	/* ----- METHODS/CONSTRUCTORS ----- */
	/* -------------------------------- */
	
	/**
	 * Default constructor for Item page - takes in a String of the Item type:
	 * i.e. Books, Vehicles, Furniture or Rooms
	 * 
	 * @param itemType
	 */
	protected ItemPage(String itemType)
	{
		// Set up the title label
		lblItem.setText(itemType);
		lblItem.setStyle("-fx-font-family: \"Arial\"; -fx-font-size: 3em; -fx-font-weight: bold");
		
		// Container node for the sorting nodes
		vbSort.getChildren().addAll(lblSortBy, cbSortBy, lblPostTime, cbPostTime);
		
		// Combo box for sorting by price
		cbSortBy.setValue("Most recent");
		cbSortBy.getItems().addAll("Most recent", "Price: low to high", "Price: high to low");
		cbSortBy.setEffect(dropShadowButton);
		
		// Combo box for sorting by time posted
		cbPostTime.setValue("All time");
		cbPostTime.getItems().addAll("12 hours", "24 hours", "1 week", "1 month", "All time");
		cbPostTime.setEffect(dropShadowButton);
		
		// Set scrollpane for item browsing
		spItems.setMinSize(620, 620);
		
		// Add all nodes to the gridpane
		gpItems.add(vbSort, 0, 0);
		gpItems.add(spItems, 1, 0);
		gpItems.setAlignment(Pos.CENTER);
		gpItems.setMinSize(650, 650);
		
		// Set VBox for use
		vbItemPage.setAlignment(Pos.TOP_CENTER);
		vbItemPage.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, null, null)));
		vbItemPage.getChildren().addAll(nav.getNavBar(), lblItem, gpItems);
		
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
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(16), e -> nav.setDisables(false, MainPage.sqlm.user.isLoggedIn(), true)));
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
	 * @return vbItemPage
	 */
	protected VBox getRootPane()
	{
		return vbItemPage;
	}
}
