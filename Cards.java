package blackjack;
import java.util.*;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Cards 
{
    private Deque<String> deck;
    private ArrayList<String> dealer_hand = new ArrayList<>();
    private ArrayList<String> player_hand = new ArrayList<>();
    private Card_values card_values = new Card_values();
    private Hashtable<String, BufferedImage> image_map = new Hashtable<>();
    private int num_dealer_aces = 0;
    private int num_player_aces = 0;
    private int player_score = 0;
    private int dealer_score = 0;
    private boolean natural = false;

    public Cards()
    {
        deck = new ArrayDeque<>();
        initialize_deck();
        shuffle_deck();
        initialize_images(this.card_values.get_card_values());
    }

    public ArrayList<String> get_dealer_hand()
    {
        return this.dealer_hand;
    }

    public ArrayList<String> get_player_hand()
    {
        return this.player_hand;
    }

    public Hashtable<String, BufferedImage> get_image_map()
    {
        return this.image_map;
    }

    public void print_hand(ArrayList<String> hand)
    {
        for (String cards : hand)
        {
            System.out.println(cards);
        }
    }

    public void player_total()
    {
        int val = 0;

        for (String card : player_hand)
        {
            List<Integer> card_vals = this.card_values.get_card_values().get(card);
    
            if (card_vals.size() == 2)
            {
                val += card_vals.get(1);
                num_player_aces++;
            }
            else 
            {
                val += card_vals.get(0);
            }
        }

        player_score = val;

        while (player_score > 21 && num_player_aces > 0)
        {
            player_score -= 10;
            num_player_aces --;
        }

    }

    public void dealer_total()
    {
        int val = 0;

        for (String card : dealer_hand)
        {
            List<Integer> card_vals = this.card_values.get_card_values().get(card);
    
            if (card_vals.size() == 2)
            {
                val += card_vals.get(1);
                num_dealer_aces++;
            }
            else 
            {
                val += card_vals.get(0);
            }
        }

        dealer_score = val;

        while (dealer_score > 21 && num_dealer_aces > 0)
        {
            dealer_score -= 10;
            num_dealer_aces--;
        }
    }
    
    
    public void player_draw()
    {
        if (this.deck.size() == 0)
        {
            initialize_deck();
            shuffle_deck();
        }
        else
        {
            player_hand.add(deck.removeFirst());
        }
        player_total();
    }

    public void dealer_draw()
    {
        if (this.deck.size() == 0)
        {
            initialize_deck();
            shuffle_deck();
        }
        else
        {
            dealer_hand.add(deck.removeFirst());
        }
        dealer_total();
    }

    public void initialize_deck()
    {
        String[] suits = {"Hearts", "Spades", "Clubs", "Diamonds"};
        String[] value = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
         
        for (String suit : suits)
        {
            for (String val : value)
            {
                this.deck.addFirst(val + " of " + suit);
            }
        }
    }

    public String determine_outcome_hit(Decision_graph graph)
    {
        if (player_score > 21)
        {
            graph.transitionTo("Player busts");
            return "Player busts";
        }
        else if (player_score == 21)
        {
            graph.transitionTo("Blackjack");
            return "Blackjack";
        }
        return "";
    }



    public String determine_outcome_stand(Decision_graph graph)
    {
        if (player_score == 21 && player_score != dealer_score)
        {
            graph.transitionTo("Player wins");
            return "Player wins";
        }

        if (dealer_score > 21)
        {
            graph.transitionTo("Dealer busts");
            return "Dealer busts";
        }

        if (player_score > dealer_score)
        {
            graph.transitionTo("Player wins");
            return "Player wins";
        }

        if (dealer_score > player_score && dealer_score <= 21)
        {
            graph.transitionTo("Player loses");
            return "Player loses";
        }

        if (player_score == dealer_score)
        {
            graph.transitionTo("Push");
            return "Push";
        }

        return "";
    }

    public void initialize_images(Hashtable<String, List<Integer>> table)
    {
        BufferedImage image = null;
        String file_name = "CS-210-Data-Structures/blackjack/images/";

        for (String key : table.keySet())
        {
            file_name = file_name + key + ".png";
            try
            {
                image = ImageIO.read(new File(file_name));
                image_map.put(key, image);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            file_name = "CS-210-Data-Structures/blackjack/images/";
        }
    }

    public void shuffle_deck()
    {
        List<String> temp_list = new ArrayList<>(deck);
        Collections.shuffle(temp_list);
        this.deck.clear();
        this.deck.addAll(temp_list);
    }

    public void deal_cards()
    {
        player_draw();
        dealer_draw();
        player_draw();
        dealer_draw();
        if (player_score == 21)
        {
            natural = true;
        }
    }

    public void reset_hands()
    {
        dealer_hand.clear();
        player_hand.clear();
        dealer_score = 0;
        player_score = 0;
        num_player_aces = 0;
        num_dealer_aces = 0;
    }

    public int get_player_score()
    {
        return this.player_score;
    }

    public int get_dealer_score()
    {
        return this.dealer_score;
    }
}
