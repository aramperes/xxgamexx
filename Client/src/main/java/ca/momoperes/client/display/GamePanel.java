package ca.momoperes.client.display;

import ca.momoperes.client.GameClient;
import ca.momoperes.client.multiplayer.GamePlayerMP;
import ca.momoperes.client.world.Location;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameFrame frame;

    public GamePanel(GameFrame frame) {
        this.frame = frame;
        setSize(frame.getWidth(), frame.getHeight() - 90);
        setLocation(0, 90);
        setBackground(Color.WHITE);
        frame.add(this);
        setFocusable(true);
        setRequestFocusEnabled(true);
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D)g1;

        if (GameClient.instance.multiplayer != null) {
            int players = GameClient.instance.multiplayer.getPlayers().size();
            g.setStroke(new BasicStroke(2f));
            g.drawRect(getWidth() - 250, 20, 225, 25 + (players * 16));
            g.setFont(g.getFont().deriveFont(14f).deriveFont(Font.BOLD));
            g.setStroke(new BasicStroke(1f));
            g.drawString("Players: " + players + "/2", getWidth() - 235, 35);
            g.setFont(g.getFont().deriveFont(14f).deriveFont(Font.PLAIN));
            for (int i = 0; i < GameClient.instance.multiplayer.getPlayers().size(); i++) {
                GamePlayerMP player = GameClient.instance.multiplayer.getPlayers().get(i);
                g.setColor(Color.BLACK);
                g.drawString(player.getUsername(), getWidth() - 235, 55 + (i * 15));
                Location location = player.getLocation();
                { // Hit box
                    int width = 50;
                    int height = player.isCrouched() ? 70 : 110;
                    int x = (int) (player.getLocation().getX() - width / 2);
                    int y = (int) (player.getLocation().getY() - height);
                    g.setStroke(new BasicStroke(2f));
                    g.setColor(Color.RED);
                    g.drawRect(x, y, width, height);
                    g.drawString(player.getUsername(), x + width / 2 - g.getFontMetrics().stringWidth(player.getUsername()) / 2, y + 20);
                }
                { // Direction
                    int width = 70;
                    int x = (int) location.getX() + 20;
                    if (player.getFacing() == GamePlayerMP.FACE_L) {
                        x -= (40 + width);
                    }
                    int y = (int) location.getY() - 70;
                    g.setColor(Color.BLUE);
                    g.setStroke(new BasicStroke(2f));
                    g.drawLine(x, y, x + width, y);
                }
            }
        }
    }
}
