package blackjack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.*;

import javax.swing.*;
import java.awt.*;

public class Handler extends MouseAdapter implements ActionListener
{
    private CardLayout card_layout;
    private JPanel main_panel;
    private Cards cards;
    private Decision_graph decision_graph;
    private JButton start_button;
    private Audio audio;
    private UI ui;

    public Handler(CardLayout _card_layout, JPanel _main_panel, Cards _cards, Decision_graph _decision_graph, Audio _audio, UI _ui)
    {
        this.card_layout = _card_layout;
        this.main_panel = _main_panel;
        this.cards = _cards;
        this.decision_graph = _decision_graph;
        this.audio = _audio;
        this.ui = _ui;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
       String action_command = e.getActionCommand();

       switch (action_command)
       {
            case "START_GAME":
                card_layout.show(main_panel, "Game Screen");
                ui.start_game();
                break;

            case "REPLAY":
                card_layout.show(main_panel, "Game Screen");
                decision_graph.transitionTo("Start");
                ui.clear_screen();
                cards.reset_hands();
                cards.deal_cards();
                break;

            case "HIT":
                if (decision_graph.getCurrentState().equals("Player stands") || decision_graph.getCurrentState().equals("Player loses") || decision_graph.getCurrentState().equals("Player wins")
                || decision_graph.getCurrentState().equals("Dealer hits"))
                {
                    break;
                }
                decision_graph.transitionTo("Player hits");
                cards.player_draw();
                ui.update_player_score_and_cards();
                audio.card_flip();
                switch (cards.determine_outcome_hit(decision_graph))
                {
                    case "Player busts":
                        ui.player_bust();
                        break;

                    case "Blackjack":
                        ui.blackjack();
                        break;

                    default:
                        break;
                }
                break;
            
            case "STAND":
                if (decision_graph.getCurrentState().equals("Player stands") || decision_graph.getCurrentState().equals("Player loses") || decision_graph.getCurrentState().equals("Player wins")
                || decision_graph.getCurrentState().equals("Dealer hits"))
                {
                    break;
                }
                decision_graph.transitionTo("Player stands");
                ui.dealer_hits(() -> {
                    switch (cards.determine_outcome_stand(decision_graph)) {
                        case "Player wins":
                            ui.you_win();
                            break;
                
                        case "Dealer busts":
                            ui.you_win();
                            decision_graph.transitionTo("Player wins");
                            break;
                
                        case "Push":
                            ui.push();
                            break;
                
                        case "Player loses":
                            ui.you_lose();
                            break;
                
                        default:
                            break;
                    }
                });
                break;

            case "HIT 1":
                System.out.println("Hit hand 1");
                cards.player_draw_1();
                ui.update_player_score_and_cards_1();
                break;

            case "HIT 2":
                System.out.println("Hit hand 2");
                cards.player_draw_2();
                ui.update_player_score_and_cards_2();
                break;

            case "STAND 1":
                System.out.println("Stand hand 1");
                break;
            
            case "STAND 2":
                System.out.println("Stand hand 2");
                break;

            case "SPLIT":
                System.out.println("Splitting...");
                cards.split_hand();
                decision_graph.transitionTo("Player splits");
                ui.player_splits();
                break;

            case "DOUBLE":
                break;
       }
    }
}
