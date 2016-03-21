package ca.momoperes.client.display;

import ca.momoperes.client.GameClient;
import ca.momoperes.client.game.MovementThread;
import ca.momoperes.client.io.message.PlayerAction;
import ca.momoperes.client.io.message.serverbound.S06PlayerActionMessage;
import ca.momoperes.client.multiplayer.GamePlayerMP;
import ca.momoperes.client.multiplayer.sound.MicrophoneRecordingThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameFrame extends JFrame implements KeyListener {

    public GamePanel panel;
    private boolean connected = false;

    public GameFrame() throws HeadlessException {
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(this);
        setLayout(null);
        setVisible(true);

        {
            JLabel label = new JLabel("Host/Port:");
            label.setLocation(30, 20);
            label.setVisible(true);
            label.setSize(70, 20);
            label.setFocusable(false);
            add(label);
        }
        {
            JTextField field = new JTextField();
            field.setName("field_host");
            field.setLocation(100, 21);
            field.setSize(150, 21);
            field.setVisible(true);
            field.addKeyListener(this);
            add(field);
        }
        {
            JTextField field = new JTextField("7499");
            field.setName("field_port");
            field.setHorizontalAlignment(SwingConstants.CENTER);
            field.setLocation(255, 21);
            field.setSize(60, 21);
            field.setVisible(true);
            field.addKeyListener(this);
            add(field);
        }

        {
            JLabel label = new JLabel("Username:");
            label.setLocation(30, 50);
            label.setVisible(true);
            label.setSize(70, 20);
            label.setFocusable(false);
            add(label);
        }
        {
            JTextField field = new JTextField();
            field.setName("field_name");
            field.setLocation(100, 51);
            field.setSize(215, 21);
            field.setVisible(true);
            field.addKeyListener(this);
            add(field);
        }
        {
            JButton button = new JButton("Connect");
            button.setLocation(340, 32);
            button.setSize(120, 25);
            button.setVisible(true);
            button.setFocusable(false);
            button.addActionListener(new ConnectDisconnectButtonClicked());
            add(button);
        }

        panel = new GamePanel(this);

        repaint();
    }

    public String getFieldValue(String name) {
        for (Component component : getContentPane().getComponents()) {
            if (component.getName() == null)
                continue;
            if (component.getName().equals(name))
                return ((JTextField) component).getText();
        }
        return null;
    }

    public void toggleFields() {
        for (Component c : getContentPane().getComponents()) {
            if (c instanceof JTextField) {
                c.setFocusable(false);
                c.setEnabled(!c.isEnabled());
            }
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (connected) {
            if (e.getKeyCode() == KeyEvent.VK_S) {
                GamePlayerMP local = GameClient.instance.multiplayer.getPlayer(GameClient.USERNAME);
                if (!local.isCrouched())
                    GameClient.instance.session.send(new S06PlayerActionMessage(PlayerAction.CROUCH));
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                GameClient.instance.session.send(new S06PlayerActionMessage(PlayerAction.JUMP));
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                MovementThread.KEY_A = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                MovementThread.KEY_D = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                MicrophoneRecordingThread.recording = true;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (connected) {
            if (e.getKeyCode() == KeyEvent.VK_S) {
                GamePlayerMP local = GameClient.instance.multiplayer.getPlayer(GameClient.USERNAME);
                if (local.isCrouched())
                    GameClient.instance.session.send(new S06PlayerActionMessage(PlayerAction.CROUCH));
            }
            if (e.getKeyCode() == KeyEvent.VK_A) {
                MovementThread.KEY_A = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                MovementThread.KEY_D = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_Q) {
                MicrophoneRecordingThread.recording = false;
            }
        }
    }

    class ConnectDisconnectButtonClicked implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (!connected) {
                String host = getFieldValue("field_host");
                String port = getFieldValue("field_port");
                String username = getFieldValue("field_name");

                GameClient.USERNAME = username;
                toggleFields();
                GameClient.instance.connectToServer(host, Integer.valueOf(port));
                connected = true;
                panel.requestFocusInWindow();
                panel.requestFocus();
                requestFocusInWindow();
                requestFocus();
                System.out.println(getFocusOwner());
            }
        }
    }
}
