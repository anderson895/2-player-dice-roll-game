import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RollingDiceGui extends JFrame {
    private int currentPlayer = 1; // Variable to track the current player

    public RollingDiceGui(){
        super("Rolling Random Dice");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 700));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        addGuiComponents();
    }

    private void addGuiComponents(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);

        // Adding label above the title
        JLabel titleLabel = new JLabel("Player " + currentPlayer, SwingConstants.CENTER); // Display current player
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setBounds(0, 20, 700, 60);
        jPanel.add(titleLabel);

        //2. Dices
        JLabel diceOneImg = ImgService.loadImage("resources/dice1.png");
        diceOneImg.setBounds(100, 200, 200, 200);
        jPanel.add(diceOneImg);

        JLabel diceTwoImg = ImgService.loadImage("resources/dice1.png");
        diceTwoImg.setBounds(390, 200, 200, 200);
        jPanel.add(diceTwoImg);

        //3. Roll Button
        JButton rollButton = new JButton("Let`s Roll!");
        rollButton.setBounds(250, 550, 200, 50);
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollButton.setEnabled(false);

                // roll for 3 seconds
                long startTime = System.currentTimeMillis();
                Thread rollThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long endTime = System.currentTimeMillis();
                        try{
                            while((endTime - startTime)/1000F < 3){
                                // roll dice
                                int diceOne = (int)(Math.random() * 6) + 1; // Generating random numbers between 1 and 6
                                int diceTwo = (int)(Math.random() * 6) + 1;

                                // update dice images
                                ImgService.updateImage(diceOneImg, "resources/dice" + diceOne + ".png");
                                ImgService.updateImage(diceTwoImg, "resources/dice" + diceTwo + ".png");

                                repaint();
                                revalidate();

                                // sleep thread
                                Thread.sleep(60);

                                endTime = System.currentTimeMillis();
                            }

                            rollButton.setEnabled(true);
                            
                            // Switch player
                            currentPlayer = (currentPlayer == 1) ? 2 : 1;
                            titleLabel.setText("Player " + currentPlayer); // Update player label
                        } catch(InterruptedException e){
                            System.out.println("Threading Error: " + e);
                        }
                    }
                });
                rollThread.start();
            }
        });
        jPanel.add(rollButton);

        this.getContentPane().add(jPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RollingDiceGui().setVisible(true);
            }
        });
    }
}
