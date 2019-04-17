package Command;

import Models.Deck.Deck;
import Models.Deck.PlayDeck;
import Models.Game.PlayGame;
import Models.Player.PlayPlayer;
import Models.Player.Player;

import java.util.ArrayList;

public class FactoryCommand
{
    
    public static FillDeskCommand createFillDeskCommand(PlayDeck playDeck)
    {
        return new FillDeskCommand(playDeck);
    }
    
    public static DistributeCardsCommand createDistributeCardsCommand(PlayDeck deck, ArrayList<PlayPlayer> players)
    {
        return new DistributeCardsCommand(deck, players);
    }
    
    public static InitGameRoomCommand createInitGameRoomCommand(PlayGame playGame){
        return new InitGameRoomCommand(playGame);
    }
    
}
