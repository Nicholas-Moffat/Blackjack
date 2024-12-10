package blackjack;
import java.util.*;

public class Card_values 
{
    private Hashtable<String, List<Integer>> card_values = new Hashtable<>();

    public Card_values()
    {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits)
        {
            for (String val : values)
            {
                String card = val + " of " + suit;
                switch (val) 
                {
                    case "Ace":
                        card_values.put(card, Arrays.asList(1, 11));
                        break;
                    case "Two":
                        card_values.put(card, Collections.singletonList(2));
                        break;
                    case "Three":
                        card_values.put(card, Collections.singletonList(3));
                        break;
                    case "Four":
                        card_values.put(card, Collections.singletonList(4));
                        break;
                    case "Five":
                        card_values.put(card, Collections.singletonList(5));
                        break;
                    case "Six":
                        card_values.put(card, Collections.singletonList(6));
                        break;
                    case "Seven":
                        card_values.put(card, Collections.singletonList(7));
                        break;
                    case "Eight":
                        card_values.put(card, Collections.singletonList(8));
                        break;
                    case "Nine":
                        card_values.put(card, Collections.singletonList(9));
                        break;
                    case "Ten":
                    case "Jack":
                    case "Queen":
                    case "King":
                        card_values.put(card, Collections.singletonList(10));
                        break;
                }
            }
        }
    }


    public Hashtable<String, List<Integer>> get_card_values()
    {
        return this.card_values;
    }
}
