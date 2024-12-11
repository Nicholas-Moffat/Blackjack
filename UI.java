package blackjack;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.ArrayList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;
import java.lang.Runnable;


public class UI extends JPanel
{
    private JFrame frame;
    private Color background = new Color(25, 56, 30);
    private JPanel main_panel;
    private JPanel start_panel;
    private JPanel game_panel = new JPanel(null);
    private JButton start_button;
    private JButton hit_button;
    private JButton hit_hand_1;
    private JButton stand_hand_1;
    private JButton stand_hand_2;
    private JButton hit_hand_2;
    private JButton stand_button;
    private JButton restart_button;
    private JButton split_button;
    private Decision_graph decision_graph;
    private Cards cards;
    private JLabel player_score_label;
    private JLabel player_score_1_label;
    private JLabel player_score_2_label;
    private JLabel dealer_score_label;
    private JLabel hidden_card_label;
    private JLabel dealer_card_label;
    private JLabel player_card_label_1;
    private JLabel player_card_label_2;
    private JLabel game_over_label;
    private JLabel outcome_label;
    private String player_score;
    private String player_score_1;
    private String player_score_2;
    private String dealer_score;
    private Audio audio;
    private String game_outcome = null;
    private Handler handler;


    public UI(Cards _cards, Decision_graph _decision_graph, Audio _audio)
    {
        this.cards = _cards;
        this.decision_graph = _decision_graph;
        this.audio = _audio;

        CardLayout card_layout = new CardLayout();
        main_panel= new JPanel(card_layout);
        handler = new Handler(card_layout, main_panel, cards, decision_graph, _audio, this);

        get_hidden_card();
        set_up_start_screen(handler);
        set_up_game_screen(handler);

        frame.add(main_panel);
        card_layout.show(main_panel, "Start Screen");

        frame.setVisible(true);
    }

    public void get_hidden_card()
    {
        try
        {
            BufferedImage back_of_card = ImageIO.read(new File("CS-210-Data-Structures/blackjack/images/card_back_red.png"));
            Image scaled_back_image = back_of_card.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            hidden_card_label = new JLabel(new ImageIcon(scaled_back_image));
            hidden_card_label.setBounds(440, 150, 200, 300);
        }
        catch(IOException e)
        {
            System.out.println("File not found");
        }
    }

    public void set_up_start_screen(Handler handler)
    {
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);

        start_panel = new JPanel();
        start_panel.setBackground(background);
        start_panel.setLayout(null);

        JLabel title_label = new JLabel("Welcome to Blackjack!", SwingConstants.CENTER);
        title_label.setFont(new Font("Serif", Font.BOLD, 32));
        title_label.setBounds(760, 100, 400, 80);
        title_label.setForeground(Color.WHITE);
        start_panel.add(title_label, BorderLayout.NORTH);

        start_button = new JButton("Start Game");
        start_button.setFont(new Font("Serif", Font.BOLD, 32));
        start_button.setBounds(860, 460, 200, 80);
        start_button.setForeground(Color.WHITE);
        start_button.setBorder(null);
        start_button.setFocusPainted(false);
        start_button.setContentAreaFilled(false);
        start_button.setActionCommand("START_GAME");
        start_button.addActionListener(handler);

        start_panel.add(start_button, BorderLayout.CENTER);
        main_panel.add(start_panel, "Start Screen");
    }

    public void set_up_game_screen(Handler handler)
    {
        game_panel.setBackground(background);

        outcome_label = new JLabel();
        outcome_label.setFont(new Font("Serif", Font.BOLD, 24));
        outcome_label.setForeground(Color.WHITE);
        outcome_label.setBounds(960, 80, 400, 80);
        outcome_label.setVisible(false);
        game_panel.add(outcome_label);

        JLabel playerLabel = new JLabel("Player's Hand");
        playerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setBounds(100, 500, 200, 50);
        game_panel.add(playerLabel);

        JLabel dealerLabel = new JLabel("Dealer's Hand");
        dealerLabel.setFont(new Font("Serif", Font.BOLD, 24));
        dealerLabel.setForeground(Color.WHITE);
        dealerLabel.setBounds(100, 100, 200, 50);
        game_panel.add(dealerLabel);

        hit_button = new JButton("Hit");
        hit_button.setFont(new Font("Serif", Font.BOLD, 24));
        hit_button.setBounds(1700, 440, 100, 50);
        hit_button.setForeground(Color.WHITE);
        hit_button.setBorder(null);
        hit_button.setFocusPainted(false);
        hit_button.setContentAreaFilled(false);
        hit_button.setActionCommand("HIT");
        hit_button.addActionListener(handler);
        game_panel.add(hit_button);

        hit_hand_1 = new JButton("Hit Hand 1");
        hit_hand_1.setFont(new Font("Serif", Font.BOLD, 24));
        hit_hand_1.setBounds(220, 850, 200, 50);
        hit_hand_1.setForeground(Color.WHITE);
        hit_hand_1.setBorder(null);
        hit_hand_1.setFocusPainted(false);
        hit_hand_1.setContentAreaFilled(false);
        hit_hand_1.setActionCommand("HIT 1");
        hit_hand_1.addActionListener(handler);
        hit_hand_1.setVisible(false);
        game_panel.add(hit_hand_1);

        stand_hand_1 = new JButton("Stand Hand 1");
        stand_hand_1.setFont(new Font("Serif", Font.BOLD, 24));
        stand_hand_1.setBounds(220, 900, 200, 50);
        stand_hand_1.setForeground(Color.WHITE);
        stand_hand_1.setBorder(null);
        stand_hand_1.setFocusPainted(false);
        stand_hand_1.setContentAreaFilled(false);
        stand_hand_1.setActionCommand("STAND 1");
        stand_hand_1.addActionListener(handler);
        stand_hand_1.setVisible(false);
        game_panel.add(stand_hand_1);

        hit_hand_2 = new JButton("Hit Hand 2");
        hit_hand_2.setFont(new Font("Serif", Font.BOLD, 24));
        hit_hand_2.setBounds(880, 850, 200, 50);
        hit_hand_2.setForeground(Color.WHITE);
        hit_hand_2.setBorder(null);
        hit_hand_2.setFocusPainted(false);
        hit_hand_2.setContentAreaFilled(false);
        hit_hand_2.setActionCommand("HIT 2");
        hit_hand_2.addActionListener(handler);
        hit_hand_2.setVisible(false);
        game_panel.add(hit_hand_2);

        stand_hand_2 = new JButton("Stand Hand 2");
        stand_hand_2.setFont(new Font("Serif", Font.BOLD, 24));
        stand_hand_2.setBounds(880, 900, 200, 50);
        stand_hand_2.setForeground(Color.WHITE);
        stand_hand_2.setBorder(null);
        stand_hand_2.setFocusPainted(false);
        stand_hand_2.setContentAreaFilled(false);
        stand_hand_2.setActionCommand("STAND 2");
        stand_hand_2.addActionListener(handler);
        stand_hand_2.setVisible(false);
        game_panel.add(stand_hand_2);

        dealer_score = String.valueOf(cards.get_dealer_score());
        dealer_score_label = new JLabel("Dealer Score: " + dealer_score);
        dealer_score_label.setBounds(1060, 900, 200, 100);
        dealer_score_label.setFont(new Font("Serif", Font.BOLD, 24));
        dealer_score_label.setForeground(Color.WHITE);
        game_panel.add(dealer_score_label);
        dealer_score_label.setVisible(false);

        restart_button = new JButton("Play Again?");
        restart_button.setFont(new Font("Serif", Font.BOLD, 24));
        restart_button.setBounds(1700, 740, 200, 50);
        restart_button.setForeground(Color.WHITE);
        restart_button.setBorder(null);
        restart_button.setFocusPainted(false);
        restart_button.setContentAreaFilled(false);
        restart_button.setActionCommand("REPLAY");
        restart_button.addActionListener(handler);
        restart_button.setVisible(false);
        game_panel.add(restart_button);

        split_button = new JButton("Split");
        split_button.setFont(new Font("Serif", Font.BOLD, 24));
        split_button.setBounds(1700, 640, 100, 50);
        split_button.setForeground(Color.WHITE);
        split_button.setBorder(null);
        split_button.setFocusPainted(false);
        split_button.setContentAreaFilled(false);
        split_button.setActionCommand("SPLIT");
        split_button.addActionListener(handler);
        split_button.setVisible(false);
        game_panel.add(split_button);

        stand_button = new JButton("Stand");
        stand_button.setFont(new Font("Serif", Font.BOLD, 24));
        stand_button.setBounds(1700, 540, 100, 50);
        stand_button.setForeground(Color.WHITE);
        stand_button.setBorder(null);
        stand_button.setFocusPainted(false);
        stand_button.setContentAreaFilled(false);
        stand_button.setActionCommand("STAND");
        stand_button.addActionListener(handler);
        game_panel.add(stand_button);

        player_score = String.valueOf(cards.get_player_score());
        player_score_label = new JLabel("Player Score: " + player_score);
        player_score_label.setBounds(660, 900, 200, 100);
        player_score_label.setFont(new Font("Serif", Font.BOLD, 24));
        player_score_label.setForeground(Color.WHITE);
        game_panel.add(player_score_label);

        player_score_1 = String.valueOf(cards.get_player_score_1());
        player_score_1_label = new JLabel("Player Score: " + player_score_1);
        player_score_1_label.setBounds(220, 950, 200, 100);
        player_score_1_label.setFont(new Font("Serif", Font.BOLD, 24));
        player_score_1_label.setForeground(Color.WHITE);
        player_score_1_label.setVisible(false);
        game_panel.add(player_score_1_label);

        player_score_2 = String.valueOf(cards.get_player_score_2());
        player_score_2_label = new JLabel("Player Score: " + player_score_2);
        player_score_2_label.setBounds(880, 950, 200, 100);
        player_score_2_label.setFont(new Font("Serif", Font.BOLD, 24));
        player_score_2_label.setForeground(Color.WHITE);
        player_score_2_label.setVisible(false);
        game_panel.add(player_score_2_label);

        main_panel.add(game_panel, "Game Screen");
    }

    public void dealer_hits(Runnable onComplete)
    {
        decision_graph.transitionTo("Dealer hits");
        int[] counter = {1};

        Timer timer = new Timer(1000, e -> 
        {
            BufferedImage card_image;
            Image scaled_image;
            switch (counter[0])
            {
                case(1):
                    reveal_hidden_card();
                    audio.card_flip();
                    counter[0]++;
                    break;

                default:
                    if (cards.get_dealer_score() < 17)
                    {
                        System.out.println("First" + cards.get_dealer_score());
                        cards.dealer_draw();
                        card_image = cards.get_image_map().get(cards.get_dealer_hand().get(counter[0]));
                        scaled_image = card_image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
                        dealer_card_label = new JLabel(new ImageIcon(scaled_image));
                        dealer_card_label.setBounds((counter[0]+1)*220, 150, 200, 300);
                        game_panel.add(dealer_card_label);
                        audio.card_flip();
                        counter[0]++; 
                        System.out.println("Second" + cards.get_dealer_score());
                    }
                    else
                    {
                        outcome_label.setVisible(true);
                        restart_button.setVisible(true);
                        ((Timer) e.getSource()).stop();
                        if (onComplete != null) 
                        {
                            onComplete.run();
                        }
                    }
                    break;
            }
            dealer_score_label.setText("Dealer Score: " + String.valueOf(cards.get_dealer_score()));
            game_panel.repaint();
        });

        timer.start();
    }

    public void clear_screen()
    {
        game_panel.removeAll();
        set_up_game_screen(handler);
        game_panel.revalidate();
        game_panel.repaint();
    }

    public void player_bust()
    {
        dealer_score_label.setText("Dealer Score: " + String.valueOf(cards.get_dealer_score()));
        decision_graph.transitionTo("Player loses");
        reveal_hidden_card();
        you_lose();
        restart_button.setVisible(true);
        game_panel.repaint();
    }

    public void reveal_hidden_card()
    {
        game_panel.remove(hidden_card_label);
        dealer_score_label.setVisible(true);
        BufferedImage card_image = cards.get_image_map().get(cards.get_dealer_hand().get(1));
        Image scaled_image = card_image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        JLabel dealer_card_label = new JLabel(new ImageIcon(scaled_image));
        dealer_card_label.setBounds(440, 150, 200, 300);
        game_panel.add(dealer_card_label);
        game_panel.repaint();
    }

    public void player_splits()
    {
        game_panel.remove(player_card_label_2);
        BufferedImage card_image = cards.get_image_map().get(cards.get_player_hand().get(1));
        Image scaled_image = card_image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        JLabel card_label = new JLabel(new ImageIcon(scaled_image));
        card_label.setBounds(880, 550, 200, 300);
        game_panel.add(card_label);
        hit_hand_1.setVisible(true);
        stand_hand_1.setVisible(true);
        hit_hand_2.setVisible(true);
        stand_hand_2.setVisible(true);
        game_panel.remove(hit_button);
        game_panel.remove(stand_button);
        game_panel.remove(split_button);
        player_score_label.setVisible(false);
        player_score_1_label.setVisible(true);
        player_score_1_label.setText("Player Score 1: " + cards.get_player_score_1());
        player_score_2_label.setVisible(true);
        player_score_2_label.setText("Player Score 2: " + cards.get_player_score_2());
        game_panel.repaint();
    }

    public void update_player_score_and_cards_1()
    {
        List<String> player_hand_1_copy = new ArrayList<>(cards.get_player_hand_1());
        for (int i = 1 ; i < player_hand_1_copy.size() ; i++)
        {
            BufferedImage card_image = cards.get_image_map().get(player_hand_1_copy.get(i));
            Image scaled_image = card_image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            JLabel card_label = new JLabel(new ImageIcon(scaled_image));
            card_label.setBounds(220 + (i*50), 550, 200, 300);
            game_panel.add(card_label);
            game_panel.setComponentZOrder(card_label, 0);
        }
        player_score_1 = String.valueOf(cards.get_player_score_1());
        player_score_1_label.setText("Player Score 1: " + player_score_1);
        game_panel.repaint();
    }

    public void update_player_score_and_cards_2()
    {
        List<String> player_hand_2_copy = new ArrayList<>(cards.get_player_hand_2());
        for (int i = 0 ; i < player_hand_2_copy.size() ; i++)
        {
            BufferedImage card_image = cards.get_image_map().get(player_hand_2_copy.get(i));
            Image scaled_image = card_image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            JLabel card_label = new JLabel(new ImageIcon(scaled_image));
            card_label.setBounds(880 + (i*50), 550, 200, 300);
            game_panel.add(card_label);
            game_panel.setComponentZOrder(card_label, 0);
        }
        player_score_2 = String.valueOf(cards.get_player_score_2());
        player_score_2_label.setText("Player Score 2: " + player_score_2);
        game_panel.repaint();
    }

    public void update_player_score_and_cards()
    {
        List<String> player_hand_copy = new ArrayList<>(cards.get_player_hand());
        int count = 0;
        for (String key : player_hand_copy)
        {
            count++;
            BufferedImage card_image = cards.get_image_map().get(key);
            Image scaled_image = card_image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            JLabel card_label = new JLabel(new ImageIcon(scaled_image));
            card_label.setBounds((count*220), 550, 200, 300);
            game_panel.add(card_label);
        }
        player_score = String.valueOf(cards.get_player_score());
        player_score_label.setText("Player Score: " + player_score);
        game_panel.repaint();
    }

    public void set_visibility(boolean visibility)
    {
        hit_button.setVisible(visibility);
        stand_button.setVisible(visibility);
        player_score_label.setVisible(visibility);
    }

    public void you_win()
    {
        outcome_label.setVisible(true);
        outcome_label.setText("YOU WIN");
        game_panel.repaint();
    }

    public void blackjack()
    {
        dealer_hits(() -> {
            if (cards.get_dealer_score() != 21)
            {
                outcome_label.setVisible(true);
                outcome_label.setText("BLACKJACK!");
                restart_button.setVisible(true);
            }

        });
    }

    public void dealer_hits()
    {
        dealer_hits(null);
    }

    public void you_lose()
    {
        outcome_label.setVisible(true);
        outcome_label.setText("YOU LOSE");
        game_panel.repaint();
    }

    public void push()
    {
        outcome_label.setVisible(true);
        outcome_label.setText("PUSH");
        game_panel.repaint();
    }

    public void natural()
    {
        dealer_hits(() -> {
            if (cards.get_dealer_score() != 21)
            {
                outcome_label.setVisible(true);
                outcome_label.setText("Natural 21!");
                restart_button.setVisible(true);
            }

        });
    }

    public void start_game()
    {
        player_score = String.valueOf(cards.get_player_score());
        player_score_label.setText("Player Score: " + player_score);
        List<String> dealer_hand_copy = new ArrayList<>(cards.get_dealer_hand());
        List<String> player_hand_copy = new ArrayList<>(cards.get_player_hand());
        List<String> both_hands = new ArrayList<>();
        
        both_hands.add(player_hand_copy.get(0));
        both_hands.add(dealer_hand_copy.get(0));
        both_hands.add(player_hand_copy.get(1));
        int[] counter = {1};

        Timer timer = new Timer(1000, e -> 
        {
            switch (counter[0])
            {
                
                case 1:
                    BufferedImage card_image = cards.get_image_map().get(both_hands.get(0));
                    Image scaled_image = card_image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
                    player_card_label_1 = new JLabel(new ImageIcon(scaled_image));
                    player_card_label_1.setBounds(220, 550, 200, 300);
                    game_panel.add(player_card_label_1);
                    break;

                case 2:
                    card_image = cards.get_image_map().get(both_hands.get(1));
                    scaled_image = card_image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
                    JLabel card_label = new JLabel(new ImageIcon(scaled_image));
                    card_label.setBounds(220, 150, 200, 300);
                    game_panel.add(card_label);
                    break;
                
                case 3:
                    card_image = cards.get_image_map().get(both_hands.get(2));
                    scaled_image = card_image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
                    player_card_label_2 = new JLabel(new ImageIcon(scaled_image));
                    player_card_label_2.setBounds(440, 550, 200, 300);
                    game_panel.add(player_card_label_2);
                    break;

                case 4:
                    game_panel.add(hidden_card_label);
                    set_visibility(true);
                    if (cards.get_player_score() == 21)
                    {
                        natural();
                    }
                    else if (cards.can_split_double())
                    {
                        split_button.setVisible(true);
                    }
                    ((Timer) e.getSource()).stop();
                    break;
            }

            audio.card_flip();
            counter[0]++;
            game_panel.repaint();
        });

        set_visibility(false);
        timer.start();
    }
}
