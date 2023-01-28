import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.*;

public class GameBoard extends JPanel implements ActionListener {

    int height=400;
    int width=400;
    int x[]=new int[height*width];
    int y[]=new int[height*width];
    int dots=3;
    int apple_x=100;
    int apple_y=100;
    int dotsize=10;
    Image apple;
    Image head;
    Image body;
    int eaten=2;
    // marking directions to move

    boolean leftD=true;
    boolean rightD=false;
    boolean upD=false;
    boolean downD=false;

    Timer time;
    int Delay=200;
    boolean inGame=true;

    public GameBoard(){
        addKeyListener(new TAdapter());
        setFocusable(true);

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);

        setBorder(new LineBorder(Color.RED,2));
        loadImages();
        initGame();

    }

    public void initGame(){
        dots=3;
        for(int i=0;i<dots;i++){
            x[i]=150+i*dotsize;
            y[i]=150;
        }
        time =new Timer(Delay,this);
        time.start();
    }
    private void loadImages(){
        ImageIcon image_apple=new ImageIcon("src/resources/apple.png");
        apple=image_apple.getImage();

        ImageIcon image_head=new ImageIcon("src/resources/head.png");
        head=image_head.getImage();

        ImageIcon image_body=new ImageIcon("src/resources/dot.png");
        body=image_body.getImage();

    }
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (inGame) {
            graphics.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0) {
                    graphics.drawImage(head, x[0], y[0], this);
                } else {
                    graphics.drawImage(body, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(graphics);
        }
    }
    private void move(){
        for(int i=dots-1;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftD){
            x[0]-=dotsize;
        }
        if(rightD){
            x[0]+=dotsize;
        }
        if(upD){
            y[0]-=dotsize;
        }
        if(downD){
            y[0]+=dotsize;
        }
    }

    private void locateApple(){
        int r= (int) (Math.random()*(39));
        apple_x=r*dotsize;
        r= (int) (Math.random()*(39));
        apple_y=r*dotsize;
    }
    public void checkApple(){
        if(x[0]==apple_x && y[0]==apple_y){
            dots++;
            eaten++;
            locateApple();
        }
    }
    public void checkCollision(){
        if(x[0]<0){
            inGame=false;
        }
        if(x[0]>=width){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(y[0]>=height){
            inGame=false;
        }
        for(int i=dots-1;i>=3;i--){
            if(x[0]==x[i] && y[0]==y[i]){
                inGame=false;
                break;
            }
        }
    }
    private void gameOver(Graphics graphics){
        String msg="Game Over\n";
        String msgs="Score: "+eaten;
        Font small=new Font("Helvetica", Font.BOLD, 16);
        FontMetrics metrics=getFontMetrics(small);
        graphics.setColor(Color.WHITE);
        graphics.setFont(small);
        graphics.drawString(msg, width-metrics.stringWidth(msg)/2, height/3);
        graphics.drawString(msgs, width-metrics.stringWidth(msgs)/2, height/2);
    }
    @Override
    public  void actionPerformed(ActionEvent actionEvent){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    public class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key = keyEvent.getKeyCode();
            if((key == KeyEvent.VK_LEFT)&&(!rightD)){
                leftD = true;
                upD = false;
                downD = false;
            }
            if((key == KeyEvent.VK_RIGHT)&&(!leftD)){
                rightD = true;
                upD = false;
                downD = false;
            }
            if((key == KeyEvent.VK_UP)&&(!downD)){
                leftD = false;
                upD = true;
                rightD = false;
            }
            if((key == KeyEvent.VK_DOWN)&&(!upD)){
                leftD = false;
                rightD = false;
                downD = true;
            }
        }
    }
}