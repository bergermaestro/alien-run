/* The main class */

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Gui extends JFrame implements ActionListener, KeyListener{

    static Board board;
    static final int BOARD_WIDTH = 1150;
    static final int BOARD_HEIGHT = 400;

    JButton startButton;
    JTextField input1;
    ImageIcon[] icons;
    BoxLayout card;
    Container content;
    JLabel heading, programInfo, background;
    static CustomFont customFont;

    public Gui() {

        setTitle("Alien Game");
        setSize(BOARD_WIDTH, BOARD_HEIGHT);

        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("alien-1.png"));   //set a custom window icon
        setIconImage(icon);   
        
        content = getContentPane();
        //card = new BoxLayout(null, BoxLayout.Y_AXIS);
        content.setLayout(null);
        content.setBackground(Color.decode("#151826"));
        
        CustomFont customFont = new CustomFont(14f);
        
        background = new JLabel(); //adds the image on the right hand side - used you could set an icon in a JLabel and not just a JButton, which looks nicer and won't confuse the user
        background.setIcon(new ImageIcon(getClass().getResource("blur-background.png")));
        background.setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        // /Color c = new Color();



        heading = new JLabel(); //the text... learned that in order to do a multi-line Label you need to specify as HTML and then use <br>, and then got caught up in some basic HTML/CSS to make things look nice
        heading.setText("<html><div style='color: white; padding: 25px; background-color: #151826; border-radius: 25px; min-width=1150px; min-height = 400px'><h1 style='text-align: center;'>Welcome to Alien Chase</h1></div></html>");
        heading.setBounds(250, 10, 800, 100);
        heading.setFont(customFont.getFont());

        programInfo = new JLabel(); //the text... learned that in order to do a multi-line Label you need to specify as HTML and then use <br>, and then got caught up in some basic HTML/CSS to make things look nice
        programInfo.setText("<html><div style='color: white; padding: 25px; background-color: #151826; border-radius: 25px; min-width=1150px; min-height = 400px'><h1> </h1><h3>The idea is you are an alien running through a forest in order to get back to your ship. <br> Press the spacebar in order to jump over the tree trunks, and see how far you can get.</h3></div></html>");
        programInfo.setBounds(250, 25, 800, 150);


        
        startButton = new JButton("Start Game");   
        startButton.addActionListener(this); //the meaty code for switching is inside this 
        startButton.setBounds(BOARD_WIDTH /2 - 50, 250, 150, 50);
        startButton.setBackground(Color.white);
        startButton.setBorder(null);
        startButton.setFont(customFont.getFont());

        content.add(heading);
        content.add(programInfo);
        content.add(startButton);
        content.add(background);

        addKeyListener(this);

        setFocusable(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void keyTyped(KeyEvent e) {}

    
    @Override
    public void keyReleased(KeyEvent e) {
        Board.alien.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Board.alien.keyPressed(e);

    }

    public static void main(String[] args) {
        new Gui();

        // Beyond The Known Space by MusicLFiles
        // Link: https://filmmusic.io/song/6835-beyond-the-known-space
        // License: https://filmmusic.io/standard-license
    }

    @Override
    public void actionPerformed(ActionEvent e) { //deals with what happens when the button is clicked
        Object obj = e.getSource();

        if(obj == startButton) {

            remove(startButton);
            remove(programInfo);

            board = new Board();
            newBoard();
        }

    }

    public void newBoard()
    {
        board.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT)); //default size of a jframe is 1*1, so need to set size custom
        board.setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        add(board);

        revalidate();
        repaint();
        Board.timer.start(); //timer is responsible for the 'game loop'
    }

}
