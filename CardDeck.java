package model;

import java.util.HashMap;
import java.util.Random;

/**
 * This CardDeck class has the mapping of all the cards in the deck
 */
public class CardDeck
{

	private HashMap<Card, Integer> map;
	private int decks;
	private String[] suits =
	{ "clubs", "diamonds", "hearts", "spades" };

	/**
	 * This is the constructor for this class
	 * @param decks which is an int
	 */
	public CardDeck(int decks)
	{
		map = new HashMap<>();
		this.decks = decks;
	}

	/**
	 * This is a getter method for a random card selector
	 * @return a Card object
	 */
	public Card getRandomCard()
	{
		Random rand = new Random();
		int n = rand.nextInt(13) + 1;
		int s = rand.nextInt(4);
		Card card;

		while (true)
		{
			String suit = suits[s];
			card = new Card(n, suit);
			if (!map.containsKey(card))
			{
				map.put(card, 1);
				break;
			} else
			{
				if (map.get(card) == decks)
				{
					n = rand.nextInt(12) + 1;
					s = rand.nextInt(4);
				} else
				{
					map.put(card, map.get(card) + 1);
					break;
				}
			}
		}
		return card;
	}

}
