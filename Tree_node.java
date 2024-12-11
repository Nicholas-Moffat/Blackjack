package blackjack;

import java.util.ArrayList;

public class Tree_node
{
    ArrayList<String> hand;
    Tree_node left_child;
    Tree_node right_child;

    public Tree_node(ArrayList<String> _hand)
    {
        this.hand = _hand;
        this.left_child = null;
        this.right_child = null;
    }

    public void print()
    {
        String str = "";
        if (left_child != null)
        {
            str = "left hand: " + left_child.hand.get(0) + " ";
        }
        if (right_child != null)
        {
            str = str + "right hand: " + right_child.hand.get(0);
        }
        System.out.println(str);
    }
}
