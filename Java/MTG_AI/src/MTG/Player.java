package MTG;

import MTG.Cards.IMagicCard;

import java.util.Vector;


public class Player {

    private final Vector<IMagicCard> cards;


    public Player() {

        this.cards = new Vector<>(1000);
    }


}
