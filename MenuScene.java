package view_controller;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * This class takes care of the Menu in the GUI and all it's user interface functionality
 */
public class MenuScene
{
	private MainGUI caller;

	private Scene scene;

	private BorderPane menuPane;

	private GridPane menuButtonsPane;
	private ImageView titleButtonImage = new ImageView(new Image("File:images/title.png", 720, 258, true, false));
	private ImageView playButtonImage = new ImageView(new Image("File:images/playButton.png", 435, 60, true, false));
	private ImageView creditsButtonImage = new ImageView(
			new Image("File:images/creditsButton.png", 435, 60, true, false));
	private ImageView closeButtonImage = new ImageView(new Image("File:images/closeButton.png", 435, 60, true, false));

	private GridPane playPane;
	private ImageView backButtonImage = new ImageView(new Image("File:images/backButton.png", 435, 60, true, false));
	private Label decksLabel = new Label("CARD DECKS:");
	private Button decksButton = new Button("3");
	private Label startingBankLabel = new Label("STARTING BANK:");
	private Button startingBankButton = new Button("$100");
	private ImageView startGameButtonImage = new ImageView(
			new Image("File:images/startGameButton.png", 435, 60, true, false));
	
	private BorderPane creditsPane;
	private ImageView creditsImage = new ImageView(new Image("File:images/credits.jpg", 372.6, 279.6, true, false));
	private Label creditsLabel = new Label("Team 1: Stephen Ceja, Simi Saha, Mahshad Parizi, & Maxwell Delaney");
	private ImageView backButtonImage2 = new ImageView(new Image("File:images/backButton.png", 435, 60, true, false));

	/**
	 * This method is a getter method for the Scene
	 * @param caller is the object MenuGUI
	 * @param width is a double
	 * @param height is a double
	 * @return a Scene object
	 */
	public Scene getScene(MainGUI caller, double width, double height)
	{
		this.caller = caller;

		menuPane = new BorderPane();
		scene = new Scene(menuPane, width, height);
		scene.getStylesheets().add("file:stylesheet.css");
		createMenuScene();
		createMenuPane();
		createPlayPane();
		createCreditsPane();
		registerHandlers();

		menuPane.setCenter(menuButtonsPane);

		return scene;
	}

	/**
	 * This method creates a MenuScene for the GUI pane
	 */
	private void createMenuScene()
	{
		menuPane.setPadding(new Insets(32, 16, 16, 16));

		titleButtonImage.setId("button");

		menuPane.setTop(titleButtonImage);

		BorderPane.setAlignment(titleButtonImage, Pos.CENTER);
		menuPane.setId("pane");
	}

	/**
	 * This method creates a MenuPane in the Menu Scene in the GUI
	 */
	private void createMenuPane()
	{
		menuButtonsPane = new GridPane();
		menuButtonsPane.setPadding(new Insets(16, 16, 16, 16));
		menuButtonsPane.setHgap(24);
		menuButtonsPane.setVgap(24);

		menuButtonsPane.setAlignment(Pos.CENTER);

		playButtonImage.setId("button");
		creditsButtonImage.setId("button");
		closeButtonImage.setId("button");

		menuButtonsPane.add(playButtonImage, 0, 0);
		menuButtonsPane.add(creditsButtonImage, 0, 1);
		menuButtonsPane.add(closeButtonImage, 0, 2);

		BorderPane.setAlignment(menuPane, Pos.CENTER);
	}

	/**
	 * This method creates a Pane which has different buttons with different functionality
	 */
	private void createPlayPane()
	{
		playPane = new GridPane();
		playPane.setPadding(new Insets(16, 16, 16, 16));
		playPane.setHgap(16);
		playPane.setVgap(16);

		playPane.setAlignment(Pos.CENTER);

		backButtonImage.setId("button");
		startGameButtonImage.setId("button");

		decksLabel.setMinWidth(135);
		decksButton.setMinWidth(135);
		startingBankLabel.setMinWidth(135);
		startingBankButton.setMinWidth(135);

		playPane.add(backButtonImage, 0, 0, 2, 1);
		playPane.add(decksLabel, 0, 1);
		playPane.add(decksButton, 1, 1);
		playPane.add(startingBankLabel, 0, 2);
		playPane.add(startingBankButton, 1, 2);
		playPane.add(startGameButtonImage, 0, 3, 2, 1);
	}
	
	/**
	 * This method creates a Pane which holds the credits!
	 */
	private void createCreditsPane()
	{
		creditsPane = new BorderPane();
		creditsPane.setPadding(new Insets(16, 16, 16, 16));
		
		BorderPane.setAlignment(backButtonImage2, Pos.CENTER);
		BorderPane.setAlignment(creditsImage, Pos.CENTER);
		BorderPane.setAlignment(creditsLabel, Pos.CENTER);

		creditsImage.setId("image");
		backButtonImage2.setId("button");

		creditsPane.setTop(backButtonImage2);
		creditsPane.setCenter(creditsImage);
		creditsPane.setBottom(creditsLabel);
	}

	/**
	 * This is the registerHandler which actually takes care of the button actions
	 */
	private void registerHandlers()
	{
		playButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			menuPane.setCenter(playPane);
		});

		backButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			menuPane.setCenter(menuButtonsPane);
		});

		decksButton.setOnAction((event) ->
		{
			int numCardDecks = Integer.parseInt(decksButton.getText());

			numCardDecks++;

			if (numCardDecks > 5)
			{
				numCardDecks = 3;
			}

			decksButton.setText(String.valueOf(numCardDecks));
		});

		startingBankButton.setOnAction((event) ->
		{
			int bankTotal = Integer.parseInt(startingBankButton.getText().substring(1));

			bankTotal += 100;

			if (bankTotal == 1100)
			{
				bankTotal = 100;
			}

			startingBankButton.setText("$" + String.valueOf(bankTotal));
		});

		startGameButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			caller.startGame(Integer.parseInt(decksButton.getText()),
					Integer.parseInt(startingBankButton.getText().substring(1)), scene.getWidth(), scene.getHeight());
		});
		
		creditsButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			menuPane.setCenter(creditsPane);
		});
		
		backButtonImage2.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			menuPane.setCenter(menuButtonsPane);
		});

		closeButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			Platform.exit();
		});
	}
}
