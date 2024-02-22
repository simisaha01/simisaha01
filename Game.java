package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This Game class is the backbone of the blackjack game which has all the functionality for it
 */
public class Game
{
	private HashMap<String, Player> players;
	// private Player currentPlayer;
	private CardDeck theDeck;
	private PlayerAI dealer;
	private int numDecks;
	private int bank;
	private String playerStatus;
	private String status;
	private boolean insuranced = false;

	/**
	 * This is the constructor for this class 
	 * @param decks which is an int
	 * @param bank which is an int
	 */
	public Game(int decks, int bank)
	{
		players = new HashMap<>();
		theDeck = new CardDeck(decks);
		dealer = new PlayerAI();
		numDecks = decks;
		this.bank = bank;
		playerStatus = "unknown";
		status = "unknown";
		// Hits for dealer
		hit(true, "human");
		hit(true, "human");

		addPlayer("AI1", "AI");
		hit(false, "AI1");
		hit(false, "AI1");

		addPlayer("human", "human");
		hit(false, "human");
		hit(false, "human");

		Player player = players.get("human");
		player.addHand(player.getHand());

		addPlayer("AI2", "AI");
		hit(false, "AI2");
		hit(false, "AI2");
	}

	/**
	 * this is the getter method for the dealer
	 * @return the PlayerAI object
	 */
	public PlayerAI getDealer()
	{
		return dealer;
	}

	/**
	 * This is the getter method for the number of deck
	 * @return an int
	 */
	public int getNumDecks()
	{
		return numDecks;
	}

	/**
	 * this method adds either a human player or AI player 
	 * @param name which is a String 
	 * @param type which is a String
	 */
	public void addPlayer(String name, String type)
	{
		Player newPlayer;
		if (type.equals("human"))
			newPlayer = new HumanPlayer();
		else
			newPlayer = new AIStrategy();
		players.put(name, newPlayer);
	}

	/**
	 * This method allows the PlayerAi to make the next move
	 * @param name which is a String
	 * @return a boolean
	 */
	public boolean nextMove(String name)
	{
		Player playerAI = players.get(name);
		return playerAI.makeDecision(dealer.getCard(0), theDeck);

	}
	
	/**
	 * check can insurance in gamescene when button is pressed.
	 * @param name which is a String
	 * @param insurance which is an int
	 * @return a boolean
	 */
	public boolean insurance(String name, int insurance)
	{
		insuranced = true;
		Player player = players.get(name);
		player.setInsurance(insurance);
		boolean insuranceStatus = false;
		if (dealer.getHand().get(0).getScore() == 10)
		{
			insuranceStatus = true;
			setStatus("Insurance");
		}
		else if(dealer.getHand().get(0).getScore() != 10)
		{
			bank -= insurance;
		}
		return insuranceStatus;
	}

	/**
	 * This method takes care of the double bet and it's functionality
	 * @param name which is a String
	 * @return a Card object
	 */
	public Card doubleBet(String name)
	{
		Player player = players.get(name);
		if (!player.isStanding())
		{
			Card newCard = theDeck.getRandomCard();
			int subtract = player.getBet();
			bank -= subtract;
			player.doubleBet();
			player.hit(newCard);
			player.stand();
			return newCard;
		}
		return null;
	}

	/**
	 * This method takes care of the split case where there are identical cards
	 * @param name which is a String
	 */
	public void split(String name)
	{
		Player player = players.get(name);
		ArrayList<Card> currHand = player.getHand();
		if (currHand.size() == 2)
		{
			if (currHand.get(0).getScore() == currHand.get(1).getScore())
			{
				player.split();
				ArrayList<Card> firstHand = new ArrayList<>();
				ArrayList<Card> secondHand = new ArrayList<>();
				player.replaceHand(firstHand);
				player.hit(currHand.get(0));
				Card newCard = theDeck.getRandomCard();
				player.hit(newCard);
				secondHand.add(currHand.get(1));
				newCard = theDeck.getRandomCard();
				secondHand.add(newCard);
				player.addHand(secondHand);
			}
		}
	}

	/**
	 * This method takes care of the functionality of the case where we hit
	 * @param dealer which is a boolean
	 * @param name which is a String
	 * @return a Card object
	 */
	public Card hit(boolean dealer, String name)
	{
		Player player = players.get(name);
		if (dealer)
		{
			player = this.dealer;
		}

		Card newCard = theDeck.getRandomCard();
		player.hit(newCard);
		if (!dealer)
		{
			int sum = player.getSumOfHand();
			if (sum >= 21)
			{
				player.stand();
			}
		}
		return newCard;

	}

	/**
	 * This method takes care of the functionality of when the player decides to stand
	 * @param name which is a String
	 */
	public void stand(String name)
	{
		Player player = players.get(name);
		player.stand();
	}

	/**
	 * This method gets the dealers move 
	 * @return a Card object
	 */
	public Card getDealerMove()
	{
		Card newCard;
		if (dealer.getNextMove() == "hit")
		{
			newCard = theDeck.getRandomCard();
			dealer.hit(newCard);
			return newCard;
		} else
		{
			return null;
		}
	}

	/**
	 * This method calculates the amount of money in the bank after every round of game
	 */
	public void updateBank()
	{
		Player player = players.get("human");

		if (playerStatus.equals("Insurance"))
		{
			return;
		} else if (playerStatus.equals("won"))
		{
			bank += (2 * player.getBet());
			return;
		} else if (playerStatus.equals("lost") || playerStatus.equals("bust"))
		{
			bank -= player.getBet();
			return;
		}
	}

	/**
	 * This method is a getter method for the Bank
	 * @return an int
	 */
	public int getBank()
	{
		return bank;
	}

	/**
	 * This is the setter method for the Bank
	 * @param amount which is an int
	 */
	public void setBank(int amount)
	{
		bank = amount;
	}

	/**
	 * This is the setter method for the Status
	 * @param status which is a String
	 */
	private void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * This is a getter method for the Status
	 * @param name which is a STring
	 * @return a String
	 */
	public String getStatus(String name)
	{
		if (status.equals("Insurance"))
		{
			return status;
		}
		int dealerHand = dealer.getSumOfHand();
		Player player = players.get(name);
		ArrayList<ArrayList<Card>> hands = player.getHands();
		if (hands.size() == 1)
		{
			int playerHand = player.getSumOfHand();
			if (status.equals("Insurance"))
			{
				return status;
			}
			if (dealerHand > 21)
			{
				playerStatus = "won";
				return "Dealer Busted, You Win!";
			} else if (playerHand > 21)
			{
				playerStatus = "bust";
				return "Bust!";
			} else if (dealerHand >= 17 && dealerHand > playerHand)
			{
				playerStatus = "lost";
				return "Dealer Wins!";
			} else if (dealerHand >= 17 && dealerHand < playerHand)
			{
				playerStatus = "won";
				return "You Win!";
			} else if (dealerHand >= 17 && dealerHand == playerHand)
			{
				playerStatus = "tie";
				return "Push!";
			} else
			{
				return "shouldn't print this!";
			}
		}

		else
		{
			String retString = "Split:";
			for (ArrayList<Card> hand : hands)
			{
				player.setHand(hand);
				if (wins(player, dealer) == 1)
				{
					// player wins
					bank += 2 * player.getOGBet();
					retString += " Win :) ";
				} else if (wins(player, dealer) == 0)
				{
					// push
					bank += player.getOGBet();
					retString += " Push :/ ";
				}
				else
				{
					// dealer wins
					retString += " Lost :( ";
				}
			}
			return retString;

		}

	}

	/**
	 * This method decides whether a player wins or not
	 * @param player1 which is a Player object
	 * @param player2 which is a player object
	 * @return an int
	 */
	private int wins(Player player1, Player player2)
	{
		int sum1 = player1.getSumOfHand();
		int sum2 = player2.getSumOfHand();
		if (sum1 == 21)
		{
			return 1;
		}
		if (sum1 > 21 && sum2 > 21)
		{
			return -1;
		}
		if (sum1 > 21 && sum2 <= 21)
		{
			return -1;
		}
		if (sum1 <= 21 && sum2 > 21)
		{
			return 1;
		}
		if (sum1 <= 21 && sum1 > sum2)
		{
			return 1;
		} else if (sum1 == sum2)
		{
			return 0;
		} else
		{
			return -1;
		}
	}

	/**
	 * This is a getter method for the Players
	 * @return an hashmap element
	 */
	public HashMap<String, Player> getPlayers()
	{
		return players;
	}

	/**
	 * This is a getter method for the deck
	 * @return a CardDeck Object
	 */
	public CardDeck getDeck()
	{
		return theDeck;
	}

	/**
	 * This method checks whether the player is standing or not
	 * @param name which is a String
	 * @return a boolean
	 */
	public boolean isStanding(String name)
	{
		Player player = players.get(name);
		return player.isStanding();
	}

	/**
	 * This method checks whether the player gets busted or not
	 * @param name which is a String
	 * @return a boolean
	 */
	public boolean busted(String name)
	{
		Player player = players.get(name);
		int score = player.getSumOfHand();
		if (score > 21)
		{
			playerStatus = "bust";
			return true;
		}
		return false;
	}

	/**
	 * This method decides if the player can split or not
	 * @param name which is a String
	 * @return a boolean
	 */
	public boolean canSplit(String name)
	{
		Player player = players.get(name);
		ArrayList<Card> hand = player.getHand();
		int card1 = hand.get(0).getScore();
		int card2 = hand.get(1).getScore();

		if (card1 == card2)
		{
			return true;
		}

		return false;
	}

	/**
	 * This method decides when a player can double
	 * @param name which is a String
	 * @return a boolean
	 */
	public boolean canDouble(String name)
	{
		Player player = players.get(name);
		ArrayList<Card> hand = player.getHand();

		if (hand.size() == 2 && bank >= player.getBet())
		{
			return true;
		}

		return false;
	}

	/**
	 * This method decides when the player get an insurances
	 * @return a boolean
	 */
	public boolean canInsurance()
	{
		boolean insuranceStatus = false;
		if (insuranced == false)
		{
			
			ArrayList<Card> hand = dealer.getHand();
			int card2 = hand.get(1).getScore();

			if (card2 == 1)
			{
				insuranceStatus = true;
			}
			
			if (insuranceStatus)
			{
				return true;
			}
		}

		return false;
	}
	
	
}
