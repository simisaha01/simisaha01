package model;

/**
 * This class holds the image and the number of every card in a deck
 */
public class Card
{
	private String imagePath;
	private String suit;

	private int score;

	/**
	 * this is the constructor of this class
	 * @param score which is an int
	 * @param suit which is a String
	 */
	public Card(int score, String suit)
	{
		this.score = score;
		this.suit = suit;
		findImage();
	}

	/**
	 * this method finds the image of the card
	 */
	public void findImage()
	{
		String strScore;
		if (score > 10)
		{
			if (score == 11)
			{
				strScore = "jack";
				this.score = 10;
			} else if (score == 12)
			{
				strScore = "queen";
				this.score = 10;
			} else
			{
				strScore = "king";
				this.score = 10;
			}
		} else
		{
			strScore = Integer.toString(score);
		}

		this.imagePath = "File:images/" + strScore + "_of_" + suit + ".png";

	}

	/**
	 * this method is the getter method for the score
	 * @return an int
	 */
	public int getScore()
	{
		return score;
	}

	/**
	 * This method is the getter method for the Image
	 * @return a String
	 */
	public String getImage()
	{
		return imagePath;
	}

}
