package model;

import java.util.ArrayList;

/**
 * This is the abstract class which has all the necessary methods for the Player
 */
public abstract class Player
{
	private int bet;
	private int ogBet;
	private int sumOfHand;
	private int insurance;
	private int split;

	private boolean standing;

	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
	private boolean ace;

	/**
	 * This is the constructor for this class
	 * @param givenChips which is an int
	 */
	public Player(int givenChips)
	{
		split = 0;
		standing = false;
		bet = 0;
		ogBet = 0;
		sumOfHand = 0;
		ace = false;
	}

	/**
	 * This is the second constructor for his class
	 */
	public Player()
	{
		split = 0;
		standing = false;
		bet = 0;
		ogBet = 0;
		sumOfHand = 0;
	}

	/**
	 * This method does the functionality of hit 
	 * @param card which is a Card object
	 */
	public void hit(Card card)
	{
		hand.add(card);
		int score = card.getScore();
		sumOfHand += score;
		if (score == 1)
			ace = true;

	}

	/**
	 * This method does the functionality of stand
	 */
	public void stand()
	{
		if (hands.indexOf(hand) != hands.size() - 1)
		{
			setHand(hands.get(hands.indexOf(hand) + 1));
		} else
		{
			standing = true;
		}
	}

	/**
	 * This method does the functionality of double bet
	 */
	public void doubleBet()
	{
		bet += bet;
	}

	/**
	 * This method is the setter for bet
	 * @param amount which is an int
	 */
	public void setBet(int amount)
	{
		bet = amount;
		ogBet = amount;
	}

	/**
	 * This method adds money to the bet
	 * @param amount which is an int
	 */
	public void addToBet(int amount)
	{
		bet += amount;
	}

	/**
	 * This is the getter method for the bet
	 * @return an int
	 */
	public int getBet()
	{
		return bet;
	}

	/**
	 * This method sets the insurance money
	 * @param amount which is an int
	 */
	public void setInsurance(int amount)
	{
		insurance = amount;
	}

	/**
	 * This method is the getter method for the insurance
	 * @param amount which is an int
	 * @return an int
	 */
	public int getInsurance(int amount)
	{
		return insurance;
	}

	/**
	 * This method checks if a player is standing or not
	 * @return a boolean
	 */
	public boolean isStanding()
	{
		return standing;
	}

	/**
	 * This method calculates the sum of each hand
	 * @return an int
	 */
	public int getSumOfHand()
	{
		if (ace)
		{
			if (sumOfHand + 10 > 21)
			{
				return sumOfHand;
			} else
			{
				return sumOfHand + 10;
			}
		}
		return sumOfHand;
	}

	/**
	 * This method is the getter method for an Ace
	 * @return a boolean
	 */
	public boolean getAce()
	{
		return ace;
	}

	/**
	 * Getter method for the Card
	 * @param i which is an int
	 * @return a Card object
	 */
	public Card getCard(int i)
	{
		return hand.get(i);
	}

	/**
	 * Getter method for the cards in hand
	 * @return an arraylist of card objects
	 */
	public ArrayList<Card> getHand()
	{
		return hand;
	}

	/**
	 * This method clears each hand and updates it
	 */
	public void clearHand()
	{
		hand = new ArrayList<Card>();
	}

	/**
	 * This method sets hand for each hand
	 * @param givenHand which is an ArrayList of Card objects
	 */
	public void setHand(ArrayList<Card> givenHand)
	{
		hand = givenHand;
		sumOfHand = 0;
		for (int i = 0; i < givenHand.size(); i++)
		{
			sumOfHand += givenHand.get(i).getScore();
		}
	}

	/**
	 * This method replaces hand for every list
	 * @param givenHand which is an ArrayList of Card objects
	 */
	public void replaceHand(ArrayList<Card> givenHand)
	{
		hands.set(hands.indexOf(hand), givenHand);
		hand = givenHand;
		sumOfHand = 0;
		for (int i = 0; i < givenHand.size(); i++)
		{
			sumOfHand += givenHand.get(i).getScore();
		}
	}

	/**
	 * This method adds hand for every method
	 * @param givenHand which is an ArrayList of Card objects
	 */
	public void addHand(ArrayList<Card> givenHand)
	{
		hands.add(givenHand);
	}

	/**
	 * This method does the functionality of a split
	 */
	public void split()
	{
		split += 1;
		bet += ogBet;
	}

	/**
	 * This method is a getter method for split
	 * @return an int
	 */
	public int getSplit()
	{
		return split;
	}

	/**
	 * This method is the getter method for the cards in each hand
	 * @return an arraylist of an arraylist of Card objects
	 */
	public ArrayList<ArrayList<Card>> getHands()
	{
		return hands;
	}

	/**
	 * This method is the getter method for the original bet
	 * @return an int
	 */
	public int getOGBet()
	{
		return ogBet;
	}

	/**
	 * This is an abstract method which is implemented in AIStrategy class
	 * @param dealerVisibleCard which is a Card object
	 * @param theDeck which is a CardDeck object
	 * @return a boolean
	 */
	public abstract boolean makeDecision(Card dealerVisibleCard, CardDeck theDeck);
}