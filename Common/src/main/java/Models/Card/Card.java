package Models.Card;

import Services.Strategies.Cards.PlayCardStrategy;

/**
 * Clase que define el modelo de una carta
 */
public class Card implements PlayCard
{
    private final int cardNumber;
    
    private PlayCardStrategy cardStrategy;
    
    private String imagePath;
    
    private String typeOfCard;
    
    public Card(int cardNumber, String typeOfCard)
    {
        this.cardNumber = cardNumber;
        this.imagePath = imagePath;
        this.typeOfCard = typeOfCard;
    }
    
    public Card(int cardNumber, String typeOfCard, PlayCardStrategy cardStrategy)
    {
        this.cardNumber = cardNumber;
        this.imagePath = typeOfCard+"/"+cardNumber+".jpeg";
        this.typeOfCard = typeOfCard;
        this.cardStrategy = cardStrategy;
    }
    
    @Override
    public String toString()
    {
        return this.cardNumber+" of "+this.typeOfCard;
    }

    @Override
    public String getTypeOfCard()
    {
        return typeOfCard;
    }
    
    public String getImagePath()
    {
        return imagePath;
    }
    
    @Override
    public int getNumber()
    {
        return this.cardNumber;
    }
    
    @Override
    public PlayCardStrategy getStrategy()
    {
        return cardStrategy;
    }
    
    @Override
    public void setStrategy(PlayCardStrategy strategy)
    {
        this.cardStrategy=strategy;
    }
    
    @Override
    public String getColor()
    {
        return this.typeOfCard;
    }
    
    @Override
    public int getPoints()
    {
        return this.cardStrategy.getValue();
    }
    
    @Override
    public boolean isSameColor(PlayCard card)
    {
        return card.getColor().equals(getColor());
    }
    
}
