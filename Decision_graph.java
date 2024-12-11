package blackjack;
import java.util.*;

public class Decision_graph 
{
    private Map<String, List<String>> adjacency_list;
    private String current_state = "Start";

    public Decision_graph()
    {
        adjacency_list = new LinkedHashMap<>();
        setup_game();
    }

    public void add_node(String node)
    {
        adjacency_list.putIfAbsent(node, new ArrayList<>());
    }

    public void add_edge(String node1, String node2)
    {
        adjacency_list.get(node1).add(node2);
    }

    public void print_graph()
    {
        for (String node : adjacency_list.keySet())
        {
            System.out.println(node + " -> " + adjacency_list.get(node));
        }
    }

    public boolean is_valid_transition(String next_state)
    {
        return adjacency_list.containsKey(current_state) && adjacency_list.get(current_state).contains(next_state);
    }

    public void transitionTo(String next_state) 
    {
        if (adjacency_list.containsKey(current_state) && adjacency_list.get(current_state).contains(next_state)) {
            current_state = next_state;
            System.out.println("Transitioned to: " + current_state);
        } 
        else 
        {
            System.out.println("Invalid transition from " + current_state + " to " + next_state);
        }
    }

    public String getCurrentState() 
    {
        return this.current_state;
    }

    public void setup_game()
    {
        this.add_node("Start");
        this.add_node("Player hits");
        this.add_node("Player stands");
        this.add_node("Player splits");
        this.add_node("Player doubles");
        this.add_node("Dealer hits");
        this.add_node("Dealer busts");
        this.add_node("Player wins");
        this.add_node("Player loses");
        this.add_node("Push");
        this.add_node("Blackjack");
        this.add_node("Natural 21");
        this.add_node("Player busts");

        this.add_edge("Start", "Player hits");
        this.add_edge("Start", "Player stands");
        this.add_edge("Start", "Player splits");
        this.add_edge("Start", "Player doubles");
        this.add_edge("Start", "Natural 21");

        this.add_edge("Player hits", "Player busts");
        this.add_edge("Player hits", "Player stands");
        this.add_edge("Player hits", "Player hits");
        this.add_edge("Player hits", "Blackjack");

        this.add_edge("Player stands", "Dealer hits");

        this.add_edge("Dealer hits", "Dealer busts");
        this.add_edge("Dealer hits", "Dealer hits");
        this.add_edge("Dealer hits", "Player wins");
        this.add_edge("Dealer hits", "Player loses");
        this.add_edge("Dealer hits", "Push");

        this.add_edge("Player busts", "Player loses");
        this.add_edge("Player loses", "Start");

        this.add_edge("Player splits", "Player hits");
        this.add_edge("Player splits", "Player stands");

        this.add_edge("Push", "Start");

        this.add_edge("Dealer busts", "Player wins");
        this.add_edge("Player wins", "Start");

        this.add_edge("Blackjack", "Player wins");
        this.add_edge("Blackjack", "Dealer hits");
        this.add_edge("Natural 21", "Player wins");
    }

    public static void main(String[] args)
    {
        System.out.println("working");
    }
}
