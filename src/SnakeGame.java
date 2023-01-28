import javax.swing.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGame extends JFrame  {
    private GameBoard board;
    public SnakeGame(){
        setTitle("->SnakeGame");
        board=new GameBoard();
        add(board);
        setResizable(false);
        pack();
        setBounds(100, 100, 800, 500);
        setVisible(true);

    }

    public static void main(String[] args) {

        JFrame sk=new SnakeGame();
    }
}