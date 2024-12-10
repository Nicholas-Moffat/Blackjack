package blackjack;

public class Game_controller 
{
    private Decision_graph decision_graph;
    private Cards cards;
    private UI ui;

    private Audio audio;

    public void start_game()
    {
        this.decision_graph = new Decision_graph();
        this.cards = new Cards();
        deal_cards(cards);
        this.audio = new Audio();
        this.ui = new UI(cards, decision_graph, audio);
        boolean is_running = true;
        audio.play_background();

        while (is_running)
        {
            if (!decision_graph.getCurrentState().equals("Player loses") || !decision_graph.getCurrentState().equals("Player wins"))
            {
                is_running = false;
            }
            try 
            {
                Thread.sleep(16);
            } 
            catch (InterruptedException e) 
            {
                e.printStackTrace();
            }
        }
    }

    public void deal_cards(Cards cards)
    {
        cards.deal_cards();
    }

}
