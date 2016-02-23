/**
 * Created by Kevin on 2015-08-26.
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Scanner;
class GirlsInfo {
    public final String[] girlList = {"Tiffany", "Aiko", "Kyanna", "Audrey", "Lola", "Nikki", "Jessie", "Beli", "Kyu"};
    private final String[] girlSurnames = {"Maye", "Yumi", "Delrio", "Belrose", "Rembrite", "Ann-Marie", "Maye", "Lapran", "Sugardust"};
    private String girlName = "";

    public void setGirlName(String g) {
        girlName = g;
    }

    public BufferedImage getGirlProfile(int a) {
        try {
            return ImageIO.read(getClass().getResource("res/icons/" + girlList[a] + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage getGirlProfile(String name) {
        try {
            for (String aGirlList : girlList) {
                if (name.equals(aGirlList)) {
                    return ImageIO.read(getClass().getResource("res/icons/" + aGirlList + ".png"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not find " + name + "'s profile picture");
            JOptionPane.showMessageDialog(null,"Could not find " + name + "'s profile picture");
        }
        return null;
    }

    public String getInfo(String infoType) {
        try {
            Scanner fileScanner = new Scanner(getClass().getResourceAsStream("res/Information.txt"));
            String currentLine;
            String info;
            while (fileScanner.hasNextLine()) {
                currentLine = fileScanner.nextLine();
                if (currentLine.equals(girlName + ":")) {
                    while (fileScanner.hasNextLine()) {
                        currentLine = fileScanner.nextLine();
                        if (currentLine.contains(infoType + ":")) {
                            info = currentLine.substring(infoType.length() + 2);
                            return info;
                        }
                        if (currentLine.equals("Hangout:")) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPref(String prefType) {
        try {
            Scanner fileScanner = new Scanner(getClass().getResourceAsStream("res/Preferences.txt"));
            String currentLine;
            String info;
            while (fileScanner.hasNextLine()) {
                currentLine = fileScanner.nextLine();
                if (currentLine.equals(girlName + ":")) {
                    while (fileScanner.hasNextLine()) {
                        currentLine = (fileScanner.nextLine());
                        if (currentLine.contains(prefType + ":")) {
                            info = currentLine.substring(prefType.length() + 2);
                            return info;
                        }
                        if (currentLine.equals("AlcoholTolerance:")) {
                            break;
                        }
                    }

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getLastName(String fname) {
        int i = 0;
        for (String name : girlList) {
            if (fname.equals(name)) {
                return girlSurnames[i];
            }
            i++;
        }
        System.out.println("Could not find girl " + fname);
        JOptionPane.showMessageDialog(null,"Could not find girl " + fname);
        return null;
    }

}
