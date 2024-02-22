package view_controller;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Game;

/**
 * This class is the Main GUI which actually calls and collaborate all the functionality of different class
 * and creates the User Interface
 */
public class MainGUI extends Application
{
	Stage currStage;
	
	/**
	 * This is the main method
	 * @param args which is String[]
	 */
	public static void main(String[] args)
	{
		launch(args);
	}

	/**
	 * This is the start method which creates the scene and the stage
	 */
	@Override
	public void start(Stage stage) throws Exception
	{
		MenuScene scene = new MenuScene();

		currStage = stage;

		currStage.setMaximized(true);
		currStage.setTitle("Blackjack!");
		currStage.setScene(scene.getScene(this, currStage.getWidth(), currStage.getHeight()));
		currStage.getIcons().addAll(new Image("File:images/cardbackicon.png", 123, 171, false, false));
		currStage.show();
	}

	/**
	 * This method initiates the game
	 * @param numCardDecks an int
	 * @param bankTotal an int
	 * @param width a double
	 * @param height a double
	 */
	public void startGame(int numCardDecks, int bankTotal, double width, double height)
	{
		GameScene scene = new GameScene();
		Game game = new Game(numCardDecks, bankTotal);

		currStage.setScene(scene.getScene(this, game, width, height));
		currStage.show();
	}

	/**
	 * This method is the getter for the stage
	 * @return a Stage Object
	 */
	public Stage getStage()
	{
		return currStage;
	}
}