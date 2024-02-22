package model;

/**
 * AIStrategy class extends the player class and plays the part of the AI
 */
public class AIStrategy extends Player
{
	private CardDeck cardDeck;

	/**
	 * AIStrategy is the constructor 
	 * @param chips which is an int
	 * @param cardDeck which is a CardDeck object
	 */
	public AIStrategy(int chips, CardDeck cardDeck)
	{
		super(chips);
		this.cardDeck = cardDeck;
	}

	/**
	 * This is the constructor for the AIStrategy class
	 */
	public AIStrategy()
	{
		super();
	}

	/**
	 * Makes a decision for the AI player based on the blackjack strategy.
	 * 
	 * @param dealerVisibleCard The dealer's visible card.
	 */
	public boolean makeDecision(Card dealerVisibleCard, CardDeck theDeck)
	{
		if (getSumOfHand() == 21) {
			stand();
			return false;
		}
		if (!isStanding())
		{
			cardDeck = theDeck;
			int handValue = getSumOfHand();
			boolean hasAce = getAce(); // Checks if the hand contains an Ace
			boolean isPair = getHand().size() == 2 && getHand().get(0).getScore() == getHand().get(1).getScore();
			int dealerValue = dealerVisibleCard.getScore();
			int dealerIndex = dealerValue == 1 ? 9 : dealerValue - 2;

			if (hasAce && !isPair)
			{
				return makeSoftHandDecision(handValue, dealerIndex);
			} else if (isPair)
			{
				return makePairHandDecision(handValue, dealerIndex);
			} else
			{
				return makeHardHandDecision(handValue, dealerIndex);
			}
		}
		return true;
	}

	/**
	 * makeSoftHandDecision makes some decision based on the black jack strategy
	 * @param handValue, dealerIndex
	 * @return a boolean
	 */
	private boolean makeSoftHandDecision(int handValue, int dealerIndex)
	{
		// Soft hand decisions based on the cheat sheet
		Card card = cardDeck.getRandomCard();
		if (handValue <= 17)
		{ // Assuming Ace is counted as 11 here
			hit(card);
			return true;
		} else if (handValue == 18)
		{
			if (dealerIndex >= 2 && dealerIndex <= 6)
			{
				stand();
				return false;
			} else
			{
				hit(card);
				return true;
			}
		} else
		{ // handValue between 19 and 21
			stand();
			return false;
		}
	}

	/**
	 * this methods makes the decision for an AI in case of a pair
	 * @param handValue an int
	 * @param dealerIndex an int
	 * @return a boolean
	 */
	private boolean makePairHandDecision(int handValue, int dealerIndex)
	{
		// Pair hand decisions based on the cheat sheet
		Card card = cardDeck.getRandomCard();
		int cardValue = handValue / 2; // Since it's a pair, the hand value is twice the card value
		switch (cardValue)
		{
		case 2:
		case 3:
			if (dealerIndex < 7)
			{
				split();
				return false;
			} else
			{
				hit(card);
				return true;
			}
		case 4:
			// If the game rules allow to split on a dealer 5 or 6, insert logic here
			hit(card);
			return true;
		case 5:
			if (dealerIndex < 9)
			{
				// If the game allows doubling, then double, otherwise hit
				// doubleDown();
			} else
			{
				hit(card);
				return true;
			}
			break;
		case 6:
		case 7:
			if (dealerIndex < 7)
			{
				split();
				return false;
			} else
			{
				hit(card);
				return true;
			}
		case 8:
			split();
			return false;
		case 9:
			if (dealerIndex != 7 && dealerIndex < 9)
			{
				split();
				return false;
			} else
			{
				stand();
				return false;
			}
		case 10:
			stand();
			return false;
		case 11:
			split();
			return false;
		default:
			// Handle unexpected cases
			hit(card);
			return true;
		}
		return false;
	}

	/**
	 * this method makes the hardhand decision based on the black jack strategy sheet
	 * @param handValue an int
	 * @param dealerIndex an int
	 * @return a boolean
	 */
	private boolean makeHardHandDecision(int handValue, int dealerIndex)
	{
		// Hard hand decisions based on the cheat sheet
		Card card = cardDeck.getRandomCard();
		if (handValue <= 8)
		{
			hit(card);
			return true;
		} else if (handValue >= 17)
		{
			stand();
			return false;
		} else
		{
			// This is a simplification. For a complete implementation, you would have a
			// lookup table or complex conditionals.
			// The following is a basic strategy:
			if (handValue == 9 && dealerIndex >= 2 && dealerIndex <= 5)
			{
				// If the game allows doubling, then double, otherwise hit
				// doubleDown();
				hit(card);
				return true;
			} else if (handValue >= 9 && handValue <= 16)
			{
				if (dealerIndex >= 2 && dealerIndex <= 6)
				{
					// If the game allows doubling, then double, otherwise hit
					hit(card);
					return true;
				}else
				{
					hit(card);
					return true;
				}
			} else
			{
				hit(card);
				return true;

			}
		}
	}

}
