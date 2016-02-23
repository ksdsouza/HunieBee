/**
 * Created by Kevin on 2015-08-26.
 */

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
// Notes
// URL to find profile images for each girl
// http://steamtradingcards.wikia.com/wiki/HuniePop
// URL For Girl's profiles
// http://huniepot.deviantart.com/art/Girl-Profile-Kyanna-486777230

class Gui implements ActionListener {
    private final GirlsInfo girlsInfo = new GirlsInfo();
    private final JFrame mainFrame = new JFrame();
    private final JPanel contentPanel = new JPanel();
    public MediaPlayer player;
    public boolean isPlaying = false;
    private JPanel infoPanel = new JPanel();

    public void playMusic() {
        new Thread(new Runnable() {

            public void run() {
                try {
                    new JFXPanel();
                    String path = this.getClass().getResource("res/music/bgmusic.mp3").toString();
                    Media media = new Media(path);
                    player = new MediaPlayer(media);
                    player.setVolume(0.25);
                    isPlaying = true;
                    player.setOnEndOfMedia(new Runnable() {
                        public void run() {
                            player.seek(Duration.ZERO);
                        }
                    });
                    player.play();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Music could not be found");
                    System.out.println("Music file could not be found");
                }
            }
        }).start();


    }

    public void createProfileGui() {

        mainFrame.setTitle("Hunie Bee");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        contentPanel.setLayout(new GridLayout(4, 4, 10, 10));
        mainFrame.setSize(650, 850);
        mainFrame.setResizable(false);
        mainFrame.setMaximizedBounds(new Rectangle(0, 0, 700, 900));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        setBackground();

        createGirlsButton();

        final JButton musicButton = new JButton();
        if (!isPlaying || player.getVolume() == 0) {
            musicButton.setText("play");
        } else {
            musicButton.setText("mute");
        }

        musicButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (musicButton.getText().equals("mute")) {
                    player.setVolume(0);
                    musicButton.setText("play");
                } else {
                    if (!isPlaying) {
                        playMusic();
                        isPlaying = true;
                        musicButton.setText("mute");
                    }
                    player.setVolume(0.25);
                    musicButton.setText("mute");
                }

            }
        });

        JPanel panel = new JPanel();
        panel.add(musicButton);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);
        contentPanel.add(panel);
        mainFrame.add(contentPanel);
        mainFrame.setVisible(true);

    }

    private void createInfoGui(final String name) {


        JLabel userImage;
        final GridBagConstraints c = new GridBagConstraints();
        contentPanel.setLayout(new GridBagLayout());
        mainFrame.setTitle(name + "'s Profile");

        /*
         * Create User Image
         */
        userImage = new JLabel(new ImageIcon(girlsInfo.getGirlProfile(name)));
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        contentPanel.add(userImage, c);

        JLabel userName = new JLabel("<html>" + name + "<br/>" + girlsInfo.getLastName(name));
        userName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 42));
        c.insets = new Insets(5, 8, 80, 0);
        c.gridx = 1;
        c.gridy = 0;
        contentPanel.add(userName, c);

        /*
         * Create button to switch to go back to choose girl
         */
        JButton back = new JButton("Go Back");
        back.setPreferredSize(new Dimension(81, 25));
        back.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        mainFrame.remove(contentPanel);
                        contentPanel.removeAll();
                        mainFrame.repaint();
                        createProfileGui();
                    }
                }
        );
        c.insets = new Insets(0, 0, 5, 0);
        c.gridx = 0;
        c.gridy = 1;
        contentPanel.add(back, c);

        /**
         * Create button to switch to detail pane
         */
        final JButton switchButton = new JButton("Go To Details");
        switchButton.setPreferredSize(new Dimension(300, 25));
        switchButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (switchButton.getText().equals("Go To Details")) {
                    switchButton.setText("Go to Preferences");
                    mainFrame.setTitle(name + "'s Details");
                    contentPanel.remove(infoPanel);
                    infoPanel = addDetail(name);
                    c.gridx = 0;
                    c.gridy = 3;
                    contentPanel.add(infoPanel, c);
                    mainFrame.add(contentPanel);
                    mainFrame.setVisible(true);
                } else {
                    switchButton.setText("Go To Details");
                    mainFrame.setTitle(name + "'s Profile");
                    contentPanel.remove(infoPanel);
                    infoPanel = addInfo();
                    c.gridx = 0;
                    c.gridy = 3;
                    contentPanel.add(infoPanel, c);
                    mainFrame.add(contentPanel);
                    mainFrame.setVisible(true);
                }


            }
        });
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        contentPanel.add(switchButton, c);

        /**
         * Populate body with chosen girl's preferences
         */
        infoPanel = addInfo();

        c.gridx = 0;
        c.gridy = 3;
        contentPanel.add(infoPanel, c);
        /**
         * Add everything to the main frame
         */
        mainFrame.add(contentPanel);
        mainFrame.setVisible(true);
    }

    private JPanel addDetail(String name) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 3, 25, 25));
        panel.setOpaque(false);
        String[] prefFileHeaders = {"Last Name", "Age", "Education", "Height", "Weight", "Occupation", "Cup Size",
                "Birthday", "Hobby", "Fav. Color", "Fav. Season", "Hangout"};


        String[] prefHeaders = {
                "<html>Last Name<br/>" + girlsInfo.getInfo(prefFileHeaders[0]) + "</html>",
                "<html>Age<br/>" + girlsInfo.getInfo(prefFileHeaders[1]) + "</html>",
                "<html>Education<br/>" + girlsInfo.getInfo(prefFileHeaders[2]) + "</html>",
                "<html>Height<br/>" + girlsInfo.getInfo(prefFileHeaders[3]) + "</html>",
                "<html>Weight<br/>" + girlsInfo.getInfo(prefFileHeaders[4]) + "</html>",
                "<html>Occupation<br/>" + girlsInfo.getInfo(prefFileHeaders[5]) + "</html>",
                "<html>Cup Size<br/>" + girlsInfo.getInfo(prefFileHeaders[6]) + "</html>",
                "<html>Birthday<br/>" + girlsInfo.getInfo(prefFileHeaders[7]) + "</html>",
                "<html>Hobby<br/>" + girlsInfo.getInfo(prefFileHeaders[8]) + "</html>",
                "<html>Favourite Colour<br/>" + girlsInfo.getInfo(prefFileHeaders[9]) + "</html>",
                "<html>Favourite Season<br/>" + girlsInfo.getInfo(prefFileHeaders[10]) + "</html>",
                "<html>Favourite Hangout<br/>" + girlsInfo.getInfo(prefFileHeaders[11]) + "</html>"};
        if(name.equalsIgnoreCase("Kyu")){
            prefFileHeaders[2]="Homeworld";
            prefHeaders[2]= "<html>Homeworld<br/>" + girlsInfo.getInfo(prefFileHeaders[2]) + "</html>";
        }

        for (int i = 0; i < 11; i++) {
            JLabel preferenceLabel = new JLabel(prefHeaders[i]);
            preferenceLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
            panel.add(preferenceLabel);
        }
        return panel;
    }

    private JPanel addInfo() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 25, 25));
        panel.setOpaque(false);
        String[] prefFileHeaders = {"MDT", "LDT", "LovesGifts", "UniqueGifts", "LikesGifts", "LikesFood", "FavDrink", "AlcoholTolerance"};
        String[] prefHeaders = {
                "<html>Most Desired Trait<br/>" + girlsInfo.getPref(prefFileHeaders[0]) + "</html>",
                "<html>Least Desired Trait<br/>" + girlsInfo.getPref(prefFileHeaders[1]) + "</html>",
                "<html>Loves Gift Type<br/>" + girlsInfo.getPref(prefFileHeaders[2]) + "</html>",
                "<html>Unique Gift Type<br/>" + girlsInfo.getPref(prefFileHeaders[3]) + "</html>",
                "<html>Likes Gift Types<br/>" + girlsInfo.getPref(prefFileHeaders[4]) + "</html>",
                "<html>Likes Food Types<br/>" + girlsInfo.getPref(prefFileHeaders[5]) + "</html>",
                "<html>Favorite Drink<br/>" + girlsInfo.getPref(prefFileHeaders[6]) + "</html>",
                "<html>Alcohol Tolerance<br/>" + girlsInfo.getPref(prefFileHeaders[7]) + "</html>"};

        for (int i = 0; i < 7; i++) {
            JLabel preferenceLabel = new JLabel(prefHeaders[i]);
            preferenceLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
            panel.add(preferenceLabel);
        }
        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        for (String name : girlsInfo.girlList) {
            if (e.getActionCommand().equals(name)) {
                mainFrame.remove(contentPanel);
                contentPanel.removeAll();
                mainFrame.repaint();
                girlsInfo.setGirlName(name);
                createInfoGui(name);

            }
        }
    }

    private void setBackground() {
        try {
            mainFrame.setContentPane(new JPanel() {
                final BufferedImage image = ImageIO.read(getClass().getResource("res/bgimg.jpg"));

                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    //g.drawImage(image, 0, 0, 1600, 1600, this);
                    g.drawImage(image, 0, 0, 650, 970, this);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createGirlsButton() {
        int i = 0;
        JButton imageButton;
        try {
            for (i = 0; i < girlsInfo.girlList.length; i++) {
                imageButton = new JButton(new ImageIcon(girlsInfo.getGirlProfile(i)));
                imageButton.setBorder(BorderFactory.createEmptyBorder());
                imageButton.setContentAreaFilled(false);
                imageButton.setActionCommand(girlsInfo.girlList[i]);
                imageButton.addActionListener(this);
                contentPanel.add(imageButton);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(contentPanel, "Cannot find " + girlsInfo.girlList[i] + ".png\nPlease Check location and try again");
            System.exit(0);
        }
    }


}