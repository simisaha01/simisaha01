package view_controller;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Game;
import model.Player;
/**
* This class creates the GameScene for the user interface
*/
public class GameScene
{
	private MainGUI caller;
	private Scene scene;
	private Game game;
	private StackPane root;
	private BorderPane mainPane;
	private GridPane gamePane;
	private GridPane menuPane;
	private GridPane betsPane;
	private Label betsTitleLabel = new Label("PLACE YOUR BETS!");
	private Label bankTotalLabel = new Label("Bank:  ");
	private ImageView tenButtonImage = new ImageView(new Image("File:images/tenButton.png", 135, 135, true, false));
	private ImageView fiftyButtonImage = new ImageView(new Image("File:images/fiftyButton.png", 135, 135, true, false));
	private ImageView oneHundredButtonImage = new ImageView(
			new Image("File:images/oneHundredButton.png", 135, 135, true, false));
	private ImageView fiveHundredButtonImage = new ImageView(
			new Image("File:images/fiveHundredButton.png", 135, 135, true, false));
	private ImageView allInButtonImage = new ImageView(new Image("File:images/allInButton.png", 135, 135, true, false));
	private ImageView resetButtonImage = new ImageView(new Image("File:images/resetButton.png", 135, 135, true, false));
	private Label betTotalLabel = new Label("Bet:  $10");
	private ImageView submitButtonImage = new ImageView(
			new Image("File:images/startRoundButton.png", 435, 60, true, false));
	private GridPane moneyPane;
	private Label bankLabel = new Label("Bank:  ");
	private Label betLabel = new Label("Bet:  ");
	private Label insuranceLabel = new Label(" ");
	private BorderPane dealerOuterPane;
	private HBox dealerInnerPane;
	private Label dealerScoreLabel = new Label(" ");
	private ImageView menuButtonImage = new ImageView(new Image("File:images/menuButton.png", 195, 60, true, false));
	private HBox AI2Pane;
	private BorderPane playerOuterPane;
	private HBox playerInnerPane;
	private Label playerScoreLabel = new Label(" ");
	private HBox AI1Pane;
	private ImageView hitButtonImage = new ImageView(new Image("File:images/hitButton.png", 195, 60, true, false));
	private ImageView standButtonImage = new ImageView(new Image("File:images/standButton.png", 195, 60, true, false));
	private ImageView doubleButtonImage = new ImageView(
			new Image("File:images/doubleButton.png", 195, 60, true, false));
	private ImageView splitButtonImage = new ImageView(new Image("File:images/splitButton.png", 195, 60, true, false));
	private ImageView insuranceButtonImage = new ImageView(
			new Image("File:images/insuranceButton.png", 195, 60, true, false));
	private ImageView exitGameButtonImage0 = new ImageView(
			new Image("File:images/exitGameButton.png", 195, 60, true, false));
	private ImageView newRoundButtonImage = new ImageView(
			new Image("File:images/newRoundButton.png", 435, 60, true, false));
	private ImageView exitToTitleButtonImage0 = new ImageView(
			new Image("File:images/exitToTitleButton.png", 435, 60, true, false));
	private ImageView backButtonImage = new ImageView(new Image("File:images/backButton.png", 435, 60, true, false));
	private ImageView exitToTitleButtonImage1 = new ImageView(
			new Image("File:images/exitToTitleButton.png", 435, 60, true, false));
	private ImageView exitGameButtonImage1 = new ImageView(
			new Image("File:images/closeButton.png", 435, 60, true, false));
	private boolean firstRender = true;
	private boolean gameDone = false;

	private Image spritesheet = new Image("file:images/winScreenSheet-export.png", 1200, 9600, true, false);;
	private GraphicsContext gc;
	private Timeline timeline;
	double sx, sy, sw, sh, dx, dy, dw, dh;

	private Canvas canvas = new Canvas(1200, 600);
	
	private boolean pressedButton;
	
	/**
	 * This method is the getter for the Scene in the user interface
	 *
	 * @param caller which is the MainGUI
	 * @param game   which is a Game object
	 * @param width  which is a double
	 * @param height which is a double
	 * @return a Scene object
	 */
	public Scene getScene(MainGUI caller, Game game, double width, double height)
	{
		this.caller = caller;
		this.game = game;
		
		root = new StackPane();
		mainPane = new BorderPane();
		betsPane = new GridPane();
		gamePane = new GridPane();
		menuPane = new GridPane();
		
		layoutMenuPane();
		layoutBetsPane();
		
		mainPane.setId("game-pane");
		mainPane.setCenter(betsPane);
		root.getChildren().add(mainPane);
		scene = new Scene(root, width, height);
		scene.getStylesheets().add("file:stylesheet.css");
		
		registerHandlers();
		return scene;
	}
	
	/**
	 * This method creates the GamePane for the GameScene
	 */
	private void createGamePane()
	{
		gamePane.setPadding(new Insets(16, 16, 16, 16));
		gamePane.setHgap(16);
		gamePane.setVgap(16);
		gamePane.setGridLinesVisible(false);
		
		layoutMoneyPane();
		layoutDealerPane(false);
		layoutAIPane("AI1", 1);
		layoutHumanPane();
		layoutAIPane("AI2", 2);
		
		if (!gameDone)
		{
			layoutButtons(false, false);
		}
		
		GridPane.setHalignment(menuButtonImage, HPos.CENTER);
		GridPane.setValignment(menuButtonImage, VPos.TOP);
		
		ColumnConstraints colAvailable = new ColumnConstraints();
		RowConstraints rowOne = new RowConstraints();
		RowConstraints rowTwo = new RowConstraints();
		RowConstraints rowThree = new RowConstraints();
		colAvailable.setHgrow(Priority.ALWAYS);
		rowOne.setPercentHeight(42);
		rowTwo.setPercentHeight(42);
		rowThree.setPercentHeight(16);
		gamePane.getColumnConstraints().addAll(colAvailable, colAvailable, colAvailable, colAvailable, colAvailable,
				colAvailable);
		gamePane.getRowConstraints().addAll(rowOne, rowTwo, rowThree);
		
		menuButtonImage.setId("button");
		gamePane.add(menuButtonImage, 5, 0);
		
		boolean flag = true;
		while (flag)
		{
			flag = game.nextMove("AI1");
			layoutAIPane("AI1", 1);
		}
		
		boolean flag2 = true;
		while (flag2)
		{
			flag2 = game.nextMove("AI2");
			layoutAIPane("AI2", 2);
		}
	}
	
	/**
	 * This method is the getter method for the CardImageView
	 *
	 * @param player which is a Player object
	 * @return an ArrayList of ImageView
	 */
	private ArrayList<ImageView> getCardsImageView(Player player)
	{
		ArrayList<ImageView> array = new ArrayList<ImageView>();
		for (int i = 0; i < player.getHand().size(); i++)
		{
			array.add(new ImageView(new Image(player.getCard(i).getImage(), 123, 171, true, false)));
			array.get(i).setId("card");
		}
		return array;
	}
	
	/**
	 * This method creates the layout for the BetsPane
	 */
	private void layoutBetsPane()
	{
		pressedButton = false;
		betsPane.setAlignment(Pos.CENTER);
		betsPane.setPadding(new Insets(16, 16, 16, 16));
		betsPane.setHgap(16);
		betsPane.setVgap(16);
		bankTotalLabel.setText("Bank:  $" + game.getBank());
		tenButtonImage.setId("button");
		fiftyButtonImage.setId("button");
		oneHundredButtonImage.setId("button");
		fiveHundredButtonImage.setId("button");
		allInButtonImage.setId("button");
		resetButtonImage.setId("button");
		submitButtonImage.setId("button");
		betsPane.add(betsTitleLabel, 0, 0, 3, 1);
		betsPane.add(bankTotalLabel, 0, 1, 3, 1);
		betsPane.add(tenButtonImage, 0, 2);
		betsPane.add(fiftyButtonImage, 1, 2);
		betsPane.add(oneHundredButtonImage, 2, 2);
		betsPane.add(fiveHundredButtonImage, 0, 3);
		betsPane.add(allInButtonImage, 1, 3);
		betsPane.add(resetButtonImage, 2, 3);
		betsPane.add(betTotalLabel, 0, 4, 3, 1);
		betsPane.add(submitButtonImage, 0, 5, 3, 1);
	}
	
	/**
	 * This method creates the layout for the MenuPane
	 */
	private void layoutMenuPane()
	{
		menuPane.setAlignment(Pos.CENTER);
		menuPane.setPadding(new Insets(16, 16, 16, 16));
		menuPane.setHgap(16);
		menuPane.setVgap(16);
		backButtonImage.setId("button");
		exitToTitleButtonImage1.setId("button");
		exitGameButtonImage1.setId("button");
		menuPane.add(backButtonImage, 0, 0);
		menuPane.add(exitToTitleButtonImage1, 0, 1);
		menuPane.add(exitGameButtonImage1, 0, 2);
	}
	
	/**
	 * This method creates the layout for the MoneyPane
	 */
	private void layoutMoneyPane()
	{
		moneyPane = new GridPane();
		moneyPane.add(bankLabel, 0, 0);
		moneyPane.add(betLabel, 0, 1);
		gamePane.add(moneyPane, 0, 0, 2, 1);
	}
	
	/**
	 * This method creates the layout for the DealerPane
	 *
	 * @param gameOver which is a boolean
	 */
	private void layoutDealerPane(boolean gameOver)
	{
		dealerOuterPane = new BorderPane();
		if (gameOver)
		{
			dealerInnerPane.getChildren().clear();
			ArrayList<ImageView> array = getCardsImageView(game.getDealer());
			dealerInnerPane = new HBox(-61.0);
			dealerInnerPane.getChildren().addAll(array);
			dealerScoreLabel.setText(String.valueOf(game.getDealer().getSumOfHand()));
		} else
		{
			ImageView card0 = new ImageView(new Image("File:images/cardback.png", 123, 171, false, false));
			ImageView card1 = new ImageView(new Image(game.getDealer().getCard(1).getImage(), 123, 171, true, false));
			card0.setId("card");
			card1.setId("card");
			dealerInnerPane = new HBox(-61.0, card0, card1);
			if (game.getDealer().getCard(1).getScore() == 1)
			{
				dealerScoreLabel.setText("11 + ?");
			} else
			{
				dealerScoreLabel.setText(String.valueOf(game.getDealer().getCard(1).getScore()) + " + ?");
			}
		}
		dealerInnerPane.setAlignment(Pos.TOP_CENTER);
		dealerOuterPane.setTop(dealerInnerPane);
		dealerOuterPane.setCenter(dealerScoreLabel);
		gamePane.add(dealerOuterPane, 2, 0, 2, 1);
	}
	
	/**
	 * This method creates the layout for HumanPane
	 */
	private void layoutHumanPane()
	{
		if (!firstRender)
		{
			playerInnerPane.getChildren().clear();
		} else
		{
			firstRender = false;
		}
		
		bankLabel.setText("Bank:  $" + String.valueOf(game.getBank()));
		betLabel.setText("Bet:  $" + String.valueOf(game.getPlayers().get("human").getBet()));
		
		playerOuterPane = new BorderPane();
		Player human = game.getPlayers().get("human");
		ArrayList<ImageView> array = getCardsImageView(human);
		
		playerInnerPane = new HBox(-61.0);
		playerInnerPane.getChildren().addAll(array);
		playerInnerPane.setAlignment(Pos.TOP_CENTER);
		playerScoreLabel.setText(String.valueOf(human.getSumOfHand()));
		playerOuterPane.setTop(playerInnerPane);
		playerOuterPane.setCenter(playerScoreLabel);
		gamePane.add(playerOuterPane, 2, 1, 2, 1);
		
		if (game.isStanding("human"))
		{
			gameDone = true;
			while (!(game.getDealer().getNextMove().equals("stand")) && !(game.busted("human")))
			{
				game.hit(true, "human");
				layoutDealerPane(true);
			}
			layoutDealerPane(true);
			layoutGameEndPane();
		}
	}
	
	/**
	 * This method creates the layout for the GameEndPane
	 */
	private void layoutGameEndPane()
	{
		game.getStatus("human");
		game.updateBank();
		int bank = game.getBank();
		
		bankLabel.setText("Bank:  $" + String.valueOf(bank));
		betLabel.setText("Bet:  $" + String.valueOf(game.getPlayers().get("human").getBet()));
		
		String gameStatus = game.getStatus("human");
		playerScoreLabel.setText(gameStatus);
		boolean enoughMoney = true;
		
		if (game.getBank() < 10)
		{
			enoughMoney = false;
		}
		
		if (enoughMoney)
		{
			layoutButtons(true, true);
		} else
		{
			layoutButtons(true, false);
		}
		
		if (gameStatus.equals("Dealer Busted, You Win!") || gameStatus.equals("You Win!")
				|| gameStatus.equals("Insurance"))
		{
			spritesheet = new Image("file:images/winScreenSheet-export.png", 1200, 9600, true, false);
		} else if (gameStatus.equals("Bust!") || gameStatus.equals("Dealer Wins!"))
		{
			spritesheet = new Image("file:images/loseScreenSheet-export.png", 1200, 9600, true, false);
		} else if (gameStatus.equals("Push!"))
		{
			spritesheet = new Image("file:images/pushScreenSheet-export.png", 1200, 9600, true, false);
		} else
		{
			spritesheet = new Image("file:images/splitScreenSheet-export.png", 1200, 9600, true, false);
		}
		
		canvas = new Canvas(1200, 600);
		gc = canvas.getGraphicsContext2D();
		root.getChildren().addAll(canvas);
		
		timeline = new Timeline(new KeyFrame(Duration.millis(100), new spriteAnimator()));
		timeline.setCycleCount(80);
		timeline.play();
		timeline.setOnFinished(e ->
		{
			root.getChildren().remove(canvas);
		});
	}
	
	/**
	 * This private class helps in the animation
	 */
	private class spriteAnimator implements EventHandler<ActionEvent>
	{
		int tic;
		
		public spriteAnimator()
		{
			tic = 0;
			sx = 0;
			sy = 0;
			sw = 1200;
			sh = 600;
			dx = 0;
			dy = 0;
			dw = 1200; // was 70
			dh = 600; // was 125
		}
		
		@Override
		/**
		 * This handle method gets called every 100 ms to draw the spritesheet
		 *
		 * @param event which is an ActionEvent
		 */
		public void handle(ActionEvent event)
		{
			// erase the previous image of the walker to draw the next spritesheet image
			gc.clearRect(0, 0, 1200, 600);
			tic++;
			gc.drawImage(spritesheet, sx, sy, sw, sh, dx, dy, dw, dh);
			sy += 600;
			if (tic % 16 == 0)
			{
				sy = 0;
			}
		}
	}
	
	/**
	 * This method creates the layout for the AIPane
	 *
	 * @param player which is a STring
	 * @param AINum  which is an int
	 */
	private void layoutAIPane(String player, int AINum)
	{
		Player AI = game.getPlayers().get(player);
		ArrayList<ImageView> arrayAI = getCardsImageView(AI);
		ColorAdjust dim = new ColorAdjust();
		dim.setBrightness(-0.5);
		if (AINum == 1)
		{
			if (AI1Pane != null)
			{
				AI1Pane.getChildren().clear();
			}
			
			AI1Pane = new HBox(-61.0);
			AI1Pane.getChildren().addAll(arrayAI);
			AI1Pane.setAlignment(Pos.TOP_LEFT);
			AI1Pane.setEffect(dim);
			
			Random rand = new Random();
			int shouldRender = rand.nextInt(4);
			
			if (shouldRender == 0)
			{
				AI1Pane.setVisible(false);
			}
			
			gamePane.add(AI1Pane, 4, 1, 2, 1);
		} else
		{
			if (AI2Pane != null)
			{
				AI2Pane.getChildren().clear();
			}
			
			AI2Pane = new HBox(-61.0);
			AI2Pane.getChildren().addAll(arrayAI);
			AI2Pane.setAlignment(Pos.TOP_RIGHT);
			AI2Pane.setEffect(dim);
			
			Random rand = new Random();
			int shouldRender = rand.nextInt(4);
			
			if (shouldRender == 0)
			{
				AI2Pane.setVisible(false);
			}
			
			gamePane.add(AI2Pane, 0, 1, 2, 1);
		}
	}
	
	/**
	 * This method creates the layout for the Button
	 *
	 * @param gameOver    which is a boolean
	 * @param enoughMoney which is a boolean
	 */
	private void layoutButtons(boolean gameOver, boolean enoughMoney)
	{
		// 210 x 63
		if (gameOver)
		{
			gamePane.getChildren().remove(hitButtonImage);
			gamePane.getChildren().remove(standButtonImage);
			gamePane.getChildren().remove(doubleButtonImage);
			gamePane.getChildren().remove(splitButtonImage);
			gamePane.getChildren().remove(insuranceButtonImage);
			gamePane.getChildren().remove(exitGameButtonImage0);
			if (enoughMoney)
			{
				newRoundButtonImage.setId("button");
				GridPane.setHalignment(newRoundButtonImage, HPos.CENTER);
				gamePane.add(newRoundButtonImage, 2, 2, 2, 1);
			} else
			{
				exitToTitleButtonImage0.setId("button");
				GridPane.setHalignment(exitToTitleButtonImage0, HPos.CENTER);
				gamePane.add(exitToTitleButtonImage0, 2, 2, 2, 1);
			}
		} else
		{
			hitButtonImage.setId("button");
			standButtonImage.setId("button");
			doubleButtonImage.setId("button");
			splitButtonImage.setId("button");
			insuranceButtonImage.setId("button");
			exitGameButtonImage0.setId("button");
			GridPane.setHalignment(hitButtonImage, HPos.CENTER);
			GridPane.setHalignment(standButtonImage, HPos.CENTER);
			GridPane.setHalignment(doubleButtonImage, HPos.CENTER);
			GridPane.setHalignment(splitButtonImage, HPos.CENTER);
			GridPane.setHalignment(insuranceButtonImage, HPos.CENTER);
			GridPane.setHalignment(exitGameButtonImage0, HPos.CENTER);
			ColorAdjust dim = new ColorAdjust();
			dim.setBrightness(-0.5);
			
			if (!(game.canDouble("human")))
			{
				doubleButtonImage = new ImageView(new Image("File:images/doubleButton.png", 195, 60, true, false));
				doubleButtonImage.setEffect(dim);
				GridPane.setHalignment(doubleButtonImage, HPos.CENTER);
			}
			if (!(game.canSplit("human")))
			{
				splitButtonImage = new ImageView(new Image("File:images/splitButton.png", 195, 60, true, false));
				splitButtonImage.setEffect(dim);
				GridPane.setHalignment(splitButtonImage, HPos.CENTER);
			}
			if (!(game.canInsurance()))
			{
				insuranceButtonImage = new ImageView(
						new Image("File:images/insuranceButton.png", 195, 60, true, false));
				insuranceButtonImage.setEffect(dim);
				GridPane.setHalignment(insuranceButtonImage, HPos.CENTER);
			}
			
			gamePane.add(hitButtonImage, 0, 2);
			gamePane.add(standButtonImage, 1, 2);
			gamePane.add(doubleButtonImage, 2, 2);
			gamePane.add(splitButtonImage, 3, 2);
			gamePane.add(insuranceButtonImage, 4, 2);
			gamePane.add(exitGameButtonImage0, 5, 2);
		}
	}
	
	/**
	 * This method calculates the money in the bet and the bank and updates it
	 * accordingly
	 *
	 * @param amount which is an int
	 */
	private void updateBankAndBet(int amount)
	{
		int betTotal = Integer.parseInt(betTotalLabel.getText().substring(7));
		
		if (betTotal + amount <= game.getBank())
		{
			betTotal += amount;
			betTotalLabel.setText("Bet:  $" + String.valueOf(betTotal));
		}
	}
	
	/**
	 * This method helps with eventhandling for all the actionevents
	 */
	private void registerHandlers()
	{
		tenButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			updateBankAndBet(10);
		});
		
		fiftyButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			updateBankAndBet(50);
		});
		
		oneHundredButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			updateBankAndBet(100);
		});
		
		fiveHundredButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			updateBankAndBet(500);
		});
		
		allInButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			betTotalLabel.setText("Bet:  $" + String.valueOf(game.getBank()));
		});
		
		resetButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			betTotalLabel.setText("Bet:  $10");
		});
		
		submitButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			if (!pressedButton)
			{
				pressedButton = true;
				
				game.getPlayers().get("human").setBet(Integer.parseInt(betTotalLabel.getText().substring(7)));
				game.setBank(Integer.parseInt(bankTotalLabel.getText().substring(8)));
				
				bankLabel.setText("Bank:  $" + String.valueOf(game.getBank()));
				betLabel.setText("Bet:  $" + String.valueOf(game.getPlayers().get("human").getBet()));
				
				ImageView card = new ImageView(new Image("File:images/jack_of_clubs.png", 123, 171, true, false));
				root.getChildren().add(card);
				
				FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), card);
				fadeTransition.setFromValue(0.3f);
				fadeTransition.setToValue(1f);
				fadeTransition.setCycleCount(1);
				fadeTransition.setAutoReverse(false);
				
				TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), card);
				translateTransition.setFromX(0);
				translateTransition.setToX(0);
				translateTransition.setCycleCount(1);
				translateTransition.setAutoReverse(false);
				
				RotateTransition rotateTransition = new RotateTransition(Duration.millis(3000), card);
				rotateTransition.setByAngle(1000f);
				rotateTransition.setCycleCount(1);
				rotateTransition.setAutoReverse(false);
				
				ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(3000), card);
				scaleTransition.setToX(9f);
				scaleTransition.setToY(9f);
				scaleTransition.setCycleCount(1);
				scaleTransition.setAutoReverse(false);
				
				ParallelTransition parallelTransition = new ParallelTransition();
				parallelTransition.getChildren().addAll(fadeTransition, translateTransition, rotateTransition,
						scaleTransition);
				parallelTransition.setCycleCount(1);
				parallelTransition.play();
				parallelTransition.setOnFinished(e ->
				{
					createGamePane();
					mainPane.setCenter(gamePane);
					
					FadeTransition fadeTransitionOut = new FadeTransition(Duration.millis(3000), card);
					fadeTransitionOut.setFromValue(1f);
					fadeTransitionOut.setToValue(0f);
					fadeTransitionOut.setCycleCount(1);
					fadeTransitionOut.setAutoReverse(false);
					
					TranslateTransition translateTransitionOut = new TranslateTransition(Duration.millis(2000), card);
					translateTransitionOut.setFromX(0);
					translateTransitionOut.setToX(0);
					translateTransitionOut.setCycleCount(1);
					translateTransitionOut.setAutoReverse(false);
					
					RotateTransition rotateTransitionOut = new RotateTransition(Duration.millis(3000), card);
					rotateTransitionOut.setByAngle(1000f);
					rotateTransitionOut.setCycleCount(1);
					rotateTransitionOut.setAutoReverse(false);
					
					ScaleTransition scaleTransitionOut = new ScaleTransition(Duration.millis(3000), card);
					scaleTransitionOut.setToX(0f);
					scaleTransitionOut.setToY(0f);
					scaleTransitionOut.setCycleCount(1);
					scaleTransitionOut.setAutoReverse(false);
					
					ParallelTransition parallelTransitionOut = new ParallelTransition();
					parallelTransitionOut.getChildren().addAll(fadeTransitionOut, translateTransitionOut,
							rotateTransitionOut, scaleTransitionOut);
					parallelTransitionOut.setCycleCount(1);
					parallelTransitionOut.play();
					
					parallelTransitionOut.setOnFinished(c ->
					{
						root.getChildren().remove(card);
					});
				});
			}
		});
		
		menuButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			root.getChildren().remove(canvas);
			mainPane.setCenter(menuPane);
		});
		
		hitButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			if (!game.isStanding("human"))
			{
				if (!(game.busted("human")))
				{
					game.hit(false, "human");
					layoutHumanPane();
				}
			}
		});
		
		standButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			game.stand("human");
			layoutHumanPane();
		});
		
		doubleButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			if (game.canDouble("human"))
			{
				game.doubleBet("human");
				layoutHumanPane();
			}
		});
		
		splitButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			game.split("human");
			layoutHumanPane();
		});
		
		insuranceButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			if (game.canInsurance())
			{
				if (game.getDealer().getCard(1).getScore() == 1)
				{
					if (game.getBank() < 10)
					{
						// throw an error, can't do insurance
					} else
					{
						int amount = 5;
						if (!(game.insurance("human", amount)))
						{
							insuranceLabel.setText("Insurance Lost!");
						} else
						{
							insuranceLabel.setText("Insurance !");
							layoutHumanPane();
							layoutDealerPane(true);
							layoutGameEndPane();
						}
						layoutHumanPane();
					}
				}
			}
		});
		
		exitGameButtonImage0.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			MenuScene menuScene = new MenuScene();
			caller.getStage().setScene(menuScene.getScene(caller, scene.getWidth(), scene.getHeight()));
		});
		
		newRoundButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			caller.startGame(game.getNumDecks(), game.getBank(), scene.getWidth(), scene.getHeight());
		});
		
		exitToTitleButtonImage0.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			MenuScene menuScene = new MenuScene();
			caller.getStage().setScene(menuScene.getScene(caller, scene.getWidth(), scene.getHeight()));
		});
		
		backButtonImage.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			mainPane.setCenter(gamePane);
		});
		
		exitToTitleButtonImage1.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			MenuScene menuScene = new MenuScene();
			caller.getStage().setScene(menuScene.getScene(caller, scene.getWidth(), scene.getHeight()));
		});
		
		exitGameButtonImage1.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) ->
		{
			Platform.exit();
		});
	}
}
