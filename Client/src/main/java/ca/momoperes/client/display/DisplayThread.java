package ca.momoperes.client.display;

public class DisplayThread extends Thread {

    private GameFrame frame;

    public void showGame() {

    }

    public void hideGame() {

    }

    @Override
    public void run() {
        setup();
        while (true) {
            if (frame.panel != null)
                frame.panel.repaint();
        }
    }

    public void setup() {
        frame = new GameFrame();
    }
}
