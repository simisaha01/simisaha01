package model;

// File: PlayerAI.java
import java.util.Random;

/**
 * This is the dealer class that extends the player class
 */
public class PlayerAI extends Player
{

	private Random random;

	/**
	 * This is the constructor for this class
	 */
	public PlayerAI()
	{
		super();
		this.random = new Random();
	}

	/**
	 * This is second constructor for this class
	 * @param givenChips which is an int
	 */
	public PlayerAI(int givenChips)
	{
		super(givenChips);
		this.random = new Random();
	}


	/**
	 * This method gets the next move of the dealer
	 * @return a String
	 */
	public String getNextMove()
	{
		// Logic for determining the next move
		int sumOfHand = getSumOfHand();
		if (sumOfHand < 17)
		{
			return "hit";
		} else
		{
			return "stand";
		}
	}

	/**
	 * This is the abstract method which is a part of the Player class
	 * @param dealerVisibleCard, theDeck
	 * @return a boolean
	 */
	@Override
	public boolean makeDecision(Card dealerVisibleCard, CardDeck theDeck)
	{
		return false;
		// TODO Auto-generated method stub

	}

}
