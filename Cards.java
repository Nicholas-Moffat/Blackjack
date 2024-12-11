package blackjack;
import java.util.*;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.TreeSet;

public class Cards 
{
    private Deque<String> deck;
    private ArrayList<String> dealer_hand = new ArrayList<>();
    private ArrayList<String> player_hand = new ArrayList<>();
    private ArrayList<String> player_hand_1 = new ArrayList<>();
    private ArrayList<String> player_hand_2 = new ArrayList<>();
    private Card_values card_values = new Card_values();
    private Hashtable<String, BufferedImage> image_map = new Hashtable<>();
    private int num_dealer_aces = 0;
    private int num_player_aces = 0;
    private int player_score = 0;
    private int player_score_1 = 0;
    private int player_score_2 = 0;
    private int num_player_aces_1 = 0;
    private int num_player_aces_2 = 0;
    private int dealer_score = 0;
    private boolean natural = false;
    private Tree_node tree;

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

    public boolean can_split_double()
    {
        int space_1_index = player_hand.get(0).indexOf(' ');
        int space_2_index = player_hand.get(1).indexOf(' ');
        String val_1 = player_hand.get(0).substring(0, space_1_index);
        String val_2 = player_hand.get(1).substring(0, space_2_index);
        if (val_1.equals(val_2))
        {
            return true;
        }
        return false;
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

    public int get_player_score_1()
    {
        return this.player_score_1;
    }

    public int get_player_score_2()
    {
        return this.player_score_2;
    }

    public void player_draw_1()
    {
        if (this.deck.size() == 0)
        {
            initialize_deck();
            shuffle_deck();
        }
        else
        {
            player_hand_1.add(deck.removeFirst());
        }
        player_total_1();
    }

    public void player_draw_2()
    {
        if (this.deck.size() == 0)
        {
            initialize_deck();
            shuffle_deck();
        }
        else
        {
            player_hand_2.add(deck.removeFirst());
        }
        player_total_2();
    }

    public ArrayList<String> get_player_hand_1()
    {
        return player_hand_1;
    }

    public ArrayList<String> get_player_hand_2()
    {
        return player_hand_2;
    }

    public void player_total_1()
    {
        int val = 0;

        for (String card : player_hand_1)
        {
            List<Integer> card_vals = this.card_values.get_card_values().get(card);
    
            if (card_vals.size() == 2)
            {
                val += card_vals.get(1);
                num_player_aces_1++;
            }
            else 
            {
                val += card_vals.get(0);
            }
        }

        player_score_1 = val;

        while (player_score_1 > 21 && num_player_aces_1 > 0)
        {
            player_score_1 -= 10;
            num_player_aces_1 --;
        }
        System.out.println(player_score_1);
    }

    public void player_total_2()
    {
        int val = 0;

        for (String card : player_hand_2)
        {
            List<Integer> card_vals = this.card_values.get_card_values().get(card);
    
            if (card_vals.size() == 2)
            {
                val += card_vals.get(1);
                num_player_aces_2++;
            }
            else 
            {
                val += card_vals.get(0);
            }
        }

        player_score_2 = val;

        while (player_score_2 > 21 && num_player_aces_2 > 0)
        {
            player_score_2 -= 10;
            num_player_aces_2 --;
        }
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
        player_hand.set(0, "Queen of Hearts");
        player_hand.set(1, "Queen of Hearts");
        player_score = 20;
        if (player_score == 21)
        {
            natural = true;
        }
        tree = new Tree_node(player_hand);
    }

    public void split_hand()
    {
        player_hand_1 = new ArrayList<>();
        player_hand_2 = new ArrayList<>();
        player_hand_1.add(player_hand.get(0));
        player_hand_2.add(player_hand.get(1));
        tree.left_child = new Tree_node(player_hand_1);
        tree.right_child = new Tree_node(player_hand_2);
        tree.print();
        if (player_hand_1.get(0).contains("Ace"))
        {
            num_player_aces_1++;
        }
        if (player_hand_2.get(0).contains("Ace"))
        {
            num_player_aces_2++;
        }
        player_total_1();
        player_total_2();
    }

    public void reset_hands()
    {
        dealer_hand.clear();
        player_hand.clear();
        dealer_score = 0;
        player_score = 0;
        num_player_aces = 0;
        num_dealer_aces = 0;
        player_hand_1.clear();
        player_hand_2.clear();
        num_player_aces_1 = 0;
        num_player_aces_2 = 0;
        player_score_1 = 0;
        player_score_2 = 0;
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
