/**
 * 
 * @author TP
 * @date 09/27/17
 * 
 * Main page for the Eaglelistings desktop application<br>
 * 
 * All attributes are listed in top down order, in the order
 * they will appear in the JavaFX GUI<br>
 * 
 */


package p1;
import java.sql.SQLException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainPage extends Application
{
	/* ---------------------- */
	/* ----- ATTRIBUTES ----- */
	/* ---------------------- */
	
	/** Stage width constant */
	public static final int STAGE_WIDTH = 800;
	/** Stage height constant */
	public static final int STAGE_HEIGHT = 800;
	
	/** Stage reference for other classes */
	private Stage stage;
	/** Instance of this class for other classes to reference */
	public static MainPage instance = null;
	
	/** Scene for the main page */ 
	private Scene sceneMainPage;
	/** Overall VBox for the main page */
	private VBox vbMainPage = new VBox();

	/** Title label */
	private Label lblTitle = new Label("EagleListings");
	
	/** NavBar instance, for navigating to other pages */
	private NavBar nav = new NavBar();
	
	// Nodes to put into vbMainPage 
	private GridPane gpBooks = new GridPane();
	private GridPane gpVehicles = new GridPane();
	private GridPane gpFurniture = new GridPane();
	private GridPane gpRooms = new GridPane();
	private Button btBooks = new Button("Books");
	private Button btVehicles = new Button("Vehicles");
	private Button btFurniture = new Button("Furniture");
	private Button btRooms = new Button("Rooms");
	private DropShadow dropShadowButton = new DropShadow(5.0, 3.0, 3.0, Color.CADETBLUE);
	
	/** Timeline for gameloop method */
	private Timeline timeline;
	
	/** 
	 * Public instance of the SQLManager class<br>
	 * Created when the program starts MainPage.java, run only once
	 */
	public static SQLManager sqlm = new SQLManager();
	
	
	/* -------------------------------- */
	/* ----- METHODS/CONSTRUCTORS ----- */
	/* -------------------------------- */
	
	/**
	 * Start method where all JavaFX begins
	 */
	@Override
	public void start(Stage stagePrimary)
	{
		// Set for use (so we can return to MainPage from other pages)
		stage = stagePrimary;
		instance = this;
		
		// Scene to contain all nodes
		sceneMainPage = new Scene(vbMainPage, STAGE_WIDTH, STAGE_HEIGHT);
		
		lblTitle.setStyle("-fx-font-family: \"Arial\"; -fx-font-size: 3em; -fx-font-weight: bold");
		
		// Set up the gridpanes
		// TODO
		// This is where the most recent item images would be displayed
		gpBooks.add(new Circle(50, Color.DARKCYAN), 0, 0);
		gpBooks.add(new Circle(50, Color.BURLYWOOD), 1, 0);
		gpBooks.add(new Circle(50, Color.DARKGOLDENROD), 2, 0);
		gpBooks.add(new Circle(50, Color.DARKORCHID), 3, 0);
		gpBooks.setHgap(10);
		gpBooks.setPadding(new Insets(10, 0, 10, 0));
		gpBooks.setMinWidth(500);
		gpBooks.setAlignment(Pos.CENTER);
		gpBooks.setGridLinesVisible(true);
		
		gpVehicles.add(new Circle(50, Color.FUCHSIA), 0, 0);
		gpVehicles.add(new Circle(50, Color.GAINSBORO), 1, 0);
		gpVehicles.add(new Circle(50, Color.GOLD), 2, 0);
		gpVehicles.add(new Circle(50, Color.LIGHTSALMON), 3, 0);
		gpVehicles.setHgap(10);
		gpVehicles.setPadding(new Insets(10, 0, 10, 0));
		gpVehicles.setMinWidth(500);
		gpVehicles.setAlignment(Pos.CENTER);
		gpVehicles.setGridLinesVisible(true);
		
		gpFurniture.add(new Circle(50, Color.HOTPINK), 0, 0);
		gpFurniture.add(new Circle(50, Color.KHAKI), 1, 0);
		gpFurniture.add(new Circle(50, Color.MEDIUMSLATEBLUE), 2, 0);
		gpFurniture.add(new Circle(50, Color.MAGENTA), 3, 0);
		gpFurniture.setHgap(10);
		gpFurniture.setPadding(new Insets(10, 0, 10, 0));
		gpFurniture.setMinWidth(500);
		gpFurniture.setAlignment(Pos.CENTER);
		gpFurniture.setGridLinesVisible(true);
		
		gpRooms.add(new Circle(50, Color.SADDLEBROWN), 0, 0);
		gpRooms.add(new Circle(50, Color.OLIVEDRAB), 1, 0);
		gpRooms.add(new Circle(50, Color.ROSYBROWN), 2, 0);
		gpRooms.add(new Circle(50, Color.SEAGREEN), 3, 0);
		gpRooms.setHgap(10);
		gpRooms.setPadding(new Insets(10, 0, 10, 0));
		gpRooms.setMinWidth(500);
		gpRooms.setAlignment(Pos.CENTER);
		gpRooms.setGridLinesVisible(true);
		
		// Make buttons pretty
		setTextStyle(btBooks);
		btBooks.setEffect(dropShadowButton);
		setTextStyle(btVehicles);
		btVehicles.setEffect(dropShadowButton);
		setTextStyle(btFurniture);
		btFurniture.setEffect(dropShadowButton);
		setTextStyle(btRooms);
		btRooms.setEffect(dropShadowButton);
		
		// Set up the all-encompassing VBox on the Main page and add all nodes inside it, top to bottom
		vbMainPage.setSpacing(5);
		vbMainPage.setAlignment(Pos.TOP_CENTER);
		vbMainPage.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, null, null)));
		vbMainPage.getChildren().addAll(lblTitle, nav.getNavBar(), gpBooks, btBooks, gpVehicles, btVehicles, gpFurniture, btFurniture, gpRooms, btRooms);
		
		// Display everything in the stage, editing its properties
		stagePrimary.setResizable(true);
		stagePrimary.setMinWidth(STAGE_WIDTH);
		stagePrimary.setMinHeight(STAGE_HEIGHT);
		stagePrimary.setTitle("EagleListings Test");
		stagePrimary.setScene(sceneMainPage);
		stagePrimary.show();
		
		// Button functionality
		btBooks.setOnAction(e -> booksButtonClick());
		btVehicles.setOnAction(e -> vehiclesButtonClick());
		btFurniture.setOnAction(e -> furnitureButtonClick());
		btRooms.setOnAction(e -> roomsButtonClick());
		
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
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(16), e -> nav.setDisables(true, sqlm.user.isLoggedIn(), true)));
		timeline.play();
	}
	
	
	/**
	 * Functionality for Books button click
	 */
	private void booksButtonClick()
	{
		ItemPage obj = new ItemPage("Books");
		stage.getScene().setRoot(obj.getRootPane());
	}
	
	
	/**
	 * Functionality for Vehicles button click
	 */
	private void vehiclesButtonClick()
	{
		ItemPage obj = new ItemPage("Vehicles");
		stage.getScene().setRoot(obj.getRootPane());
	}
	
	
	/**
	 * Functionality for Furniture button click
	 */
	private void furnitureButtonClick()
	{
		ItemPage obj = new ItemPage("Furniture");
		stage.getScene().setRoot(obj.getRootPane());
	}
	
	
	/**
	 * Functionality for Rooms button click
	 */
	private void roomsButtonClick()
	{
		ItemPage obj = new ItemPage("Rooms");
		stage.getScene().setRoot(obj.getRootPane());
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
	 * @return stage
	 */
	public Stage getStage()
	{
		return stage;
	}
	
	
	/**
	 * Getter
	 * 
	 * @return vbMainPage
	 */
	public VBox getMainVBox()
	{
		return vbMainPage;
	}
	
	
	/**
	 * Needed to run JavaFX without command line
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		// Launch the start method
	    MainPage.launch(args);
	    // Or just launch(args);

	    // Close the SQL database connection if the application is closing
	    if(Platform.isImplicitExit())
	    {
	    	// Such try						
			try 
	    	{
				sqlm.connection.close();
				System.out.println("Closed database connection!");
			} 
	    	
			// Very catch
	    	catch (SQLException e) 
	    	{
				e.printStackTrace();
				System.err.println("Could not close connection to database!");
			}
	    }
	    
	}
}
