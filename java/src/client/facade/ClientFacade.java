package client.facade;

import client.session.SessionManager;
import shared.communication.game.GameModel_Input;
import shared.communication.game.GameModel_Output;
import shared.communication.moves.*;
import shared.models.ClientModel;

public class ClientFacade {
	
	private ClientModel clientModel;
	
	/**
	 * Constructs a moves facade
	 * @param clientModel
	 */
	public ClientFacade(ClientModel clientModel) {
		this.clientModel = clientModel;
	}
	
	public void handleResponse(String response)
	{
		if(response.equals("\"true\"")) //model has not changed
		{
			return;
		}
		try
		{
			ClientModel newClientModel = SessionManager.instance().getInterpreter().deserialize(response);
			SessionManager.instance().updateClientModels(newClientModel);
		}
		catch(Exception e)
		{
			//oops
		}
	}
	
	
	/**
	 * 
	 * @param playerIndex
	 * @return true if it is this players turn, else false
	 */
	public boolean isPlayersTurn(int playerIndex)
	{
		return (playerIndex == clientModel.getTurnTracker().getCurrentTurn());
	}
	
	/**
	 * @param params
	 * @return true if client can make this move, else false
	 */
	public boolean canSendChat(SendChat_Input params)
	{
		 return(params.getContent() != null && !params.getContent().isEmpty());
	}
	
	public void sendChat(SendChat_Input params)
	{
		if(canSendChat(params))
		{
			SendChat_Output output = SessionManager.instance().getServer().sendChat(params);
			handleResponse(output.getResponse());
		}
	}

	/**
	 * @param params
	 * @return true if client can make this move, else false
	 */
	public boolean canRollNumber(RollNumber_Input params)
	{
		if(isPlayersTurn(params.getPlayerIndex()))
		{
			if(params.getNumber() <= 12 && params.getNumber() >= 2)
			{
				return true;
			}
		}
		return false;
	}
	
	public void rollNumber(RollNumber_Input params)
	{
		if(canRollNumber(params))
		{
			RollNumber_Output output = SessionManager.instance().getServer().rollNumber(params);
			handleResponse(output.getResponse());
		}
	}
	
	/**
	 * @param params
	 * @return true if client can make this move, else false
	 */
	public boolean canRobPlayer(RobPlayer_Input params)
	{
		//if player is not victim, and it is the players turn
		if(params.getPlayerIndex()!=params.getVictimIndex() && isPlayersTurn(params.getPlayerIndex()))
		{
			return clientModel.canRobPlayer(params);
		}
		return false;
	}
	
	public void robPlayer(RobPlayer_Input params)
	{
		if(canRobPlayer(params))
		{
			RobPlayer_Output output = SessionManager.instance().getServer().robPlayer(params);
			handleResponse(output.getResponse());
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can end turn with these params, else false
	 */
	public boolean canFinishTurn(FinishTurn_Input params)
	{
		return isPlayersTurn(params.getPlayerIndex());
	}
	
	public void finishTurn(FinishTurn_Input params)
	{
		if(canFinishTurn(params))
		{
			FinishTurn_Output output = SessionManager.instance().getServer().finishTurn(params);
			handleResponse(output.getResponse());
		}
	}

	/**
	 * 
	 * @param params
	 * @return true if we can buyDevCard with these params, else false
	 */
	public boolean canBuyDevCard(BuyDevCard_Input params)
	{
		return (clientModel.canBuyDevCard(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	public void buyDevCard(BuyDevCard_Input params)
	{
		if(canBuyDevCard(params))
		{
			BuyDevCard_Output output = SessionManager.instance().getServer().buyDevCard(params);
			handleResponse(output.getResponse());
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can yearOfPlenty with these params, else false
	 */
	public boolean canYearOfPlenty(YearOfPlenty_Input params)
	{
		return( clientModel.canYearOfPlenty(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	public void yearOfPlenty(YearOfPlenty_Input params)
	{
		if(canYearOfPlenty(params))
		{
			YearOfPlenty_Output output = SessionManager.instance().getServer().yearOfPlenty(params);
			handleResponse(output.getResponse());
		}
	}

	/**
	 * 
	 * @param params
	 * @return true if we can roadBuilding with these params, else false
	 */
	public boolean canRoadBuilding(RoadBuilding_Input params) //todo
	{
		return( clientModel.canRoadBuilding(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	public void roadBuilding(RoadBuilding_Input params)
	{
		if(canRoadBuilding(params))
		{
			RoadBuilding_Output output = SessionManager.instance().getServer().roadBuilding(params);
			handleResponse(output.getResponse());
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can soldier with these params, else false
	 */
	public boolean canSoldier(Soldier_Input params)
	{
		return( clientModel.canSoldier(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	public void soldier(Soldier_Input params)
	{
		if(canSoldier(params))
		{
			Soldier_Output output = SessionManager.instance().getServer().soldier(params);
			handleResponse(output.getResponse());
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can monopoly with these params, else false
	 */
	public boolean canMonopoly(Monopoly_Input params)
	{
		return( clientModel.canMonopoly(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	public void monopoly(Monopoly_Input params)
	{
		if(canMonopoly(params))
		{
			Monopoly_Output output = SessionManager.instance().getServer().monopoly(params);
			handleResponse(output.getResponse());
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can monument with these params, else false
	 */
	public boolean canMonument(Monument_Input params)
	{
		return( clientModel.canMonument(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	public void monument(Monument_Input params)
	{
		if(canMonument(params))
		{
			Monument_Output output = SessionManager.instance().getServer().monument(params);
			handleResponse(output.getResponse());
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buildRoad with these params, else false
	 */
	public boolean canBuildRoad(BuildRoad_Input params)
	{
		return (clientModel.canBuildRoad(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	public void buildRoad(BuildRoad_Input params)
	{
		if(canBuildRoad(params))
		{
			BuildRoad_Output output = SessionManager.instance().getServer().buildRoad(params);
			handleResponse(output.getResponse());
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buildSettlement with these params, else false
	 */
	public boolean canBuildSettlement(BuildSettlement_Input params)
	{
		return (clientModel.canBuildSettlement(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	public void buildSettlement(BuildSettlement_Input params)
	{
		if(canBuildSettlement(params))
		{
			BuildSettlement_Output output = SessionManager.instance().getServer().buildSettlement(params);
			handleResponse(output.getResponse());
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can buildCity with these params, else false
	 */
	public boolean canBuildCity(BuildCity_Input params)
	{
		return (clientModel.canBuildCity(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	public void buildCity(BuildCity_Input params)
	{
		if(canBuildCity(params))
		{
			BuildCity_Output output = SessionManager.instance().getServer().buildCity(params);
			handleResponse(output.getResponse());
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can offerTrade with these params, else false
	 */
	public boolean canOfferTrade(OfferTrade_Input params)
	{
		if(clientModel.getTradeOffer() != null) //can't already have trade going
		{
			return false;
		}
		return(clientModel.canOfferTrade(params) && isPlayersTurn(params.getPlayerIndex()) && params.getPlayerIndex()!=params.getReceiver());
	}
	
	public void offerTrade(OfferTrade_Input params)
	{
		if(canOfferTrade(params))
		{
			OfferTrade_Output output = SessionManager.instance().getServer().offerTrade(params);
			handleResponse(output.getResponse());
		}
	}

	/**
	 * 
	 * @param params
	 * @return true if we can acceptTrade with these params, else false
	 */
	public boolean canAcceptTrade(AcceptTrade_Input params)
	{
		if(clientModel.getTradeOffer() == null) //must have trade going on
		{
			return false;
		}
		return( clientModel.canAcceptTrade(params));
	}
	
	public void acceptTrade(AcceptTrade_Input params)
	{
		if(canAcceptTrade(params))
		{
			AcceptTrade_Output output = SessionManager.instance().getServer().acceptTrade(params);
			handleResponse(output.getResponse());
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can maritimeTrade with these params, else false
	 */
	public boolean canMaritimeTrade(MaritimeTrade_Input params)
	{
		return( clientModel.canMaritimeTrade(params) && isPlayersTurn(params.getPlayerIndex()));
	}
	
	public void maritimeTrade(MaritimeTrade_Input params)
	{
		if(canMaritimeTrade(params))
		{
			MaritimeTrade_Output output = SessionManager.instance().getServer().maritimeTrade(params);
			handleResponse(output.getResponse());
		}
	}
	
	/**
	 * 
	 * @param params
	 * @return true if we can discardCards with these params, else false
	 */
	public boolean canDiscardCards(DiscardCards_Input params)
	{
		return (clientModel.canDiscardCards(params));
	}
	
	public void discardCards(DiscardCards_Input params)
	{
		if(canDiscardCards(params))
		{
			DiscardCards_Output output = SessionManager.instance().getServer().discardCards(params);
			handleResponse(output.getResponse());
		}
	}

	public ClientModel getClientModel() {
		return clientModel;
	}

	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Extra Methods
	
	public int getPoints(int playerIndex)
	{
		return clientModel.getPlayerByIndex(playerIndex).getVictoryPoints();
	}
	
	public ClientModel getInitialModel()
	{
		GameModel_Output output = SessionManager.instance().getServer().getModel(new GameModel_Input());
		
		ClientModel initialClientModel = SessionManager.instance().getInterpreter().deserialize(output.getResponse());
		
		return initialClientModel;
	}
	
	public boolean canRoll(int index)
	{
		if(index != clientModel.getTurnTracker().getCurrentTurn())
		{
			return false;
		}
		else if(!clientModel.getTurnTracker().getStatus().equalsIgnoreCase("rolling"))
		{
			return false;
		}
		return true;
	}
	
}
