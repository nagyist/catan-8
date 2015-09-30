package shared.communication.moves;

import com.google.gson.Gson;

import shared.models.ResourceCards;

public class DiscardCards_Input 
{
	private final String type = "discardCards";
	private int playerIndex;
	private ResourceCards discardedCards;
	
	public DiscardCards_Input(int playerIndex, ResourceCards discardedCards)
	{
		super();
		this.playerIndex = playerIndex;
		this.discardedCards = discardedCards;
	}

	/**
	 * @return the playerIndex
	 */
	public int getPlayerIndex()
	{
		return playerIndex;
	}

	/**
	 * @param playerIndex the playerIndex to set
	 */
	public void setPlayerIndex(int playerIndex)
	{
		this.playerIndex = playerIndex;
	}

	/**
	 * @return the discardedCards
	 */
	public ResourceCards getDiscardedCards()
	{
		return discardedCards;
	}

	/**
	 * @param discardedCards the discardedCards to set
	 */
	public void setDiscardedCards(ResourceCards discardedCards)
	{
		this.discardedCards = discardedCards;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}
	
	public String toJSON()
	{
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}