package model;

/**
 * the only reason i can think of for having HumanPlayer be its own class is 
 * so we can distinguish it from PlayerAI
 */

public class HumanPlayer extends Player
{
	/**
	 * This is the constructor for this class
	 * @param amountOfChips
	 */
	HumanPlayer(int amountOfChips)
	{
		super(amountOfChips);
	}

	/**
	 * This is the second constructor for this class
	 */
	HumanPlayer()
	{
		super();
	}

	/**
	 * This method is the abstract method implementation from the Player abstract class which
	 * doens't do anything
	 * @return a  boolean
	 */
	@Override
	public boolean makeDecision(Card dealerVisibleCard, CardDeck theDeck)
	{
		return false;
		// TODO Auto-generated method stub

	}
}
