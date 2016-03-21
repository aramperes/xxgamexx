package ca.momoperes.client.game;

import ca.momoperes.client.GameClient;
import ca.momoperes.client.io.message.PlayerAction;
import ca.momoperes.client.io.message.serverbound.S06PlayerActionMessage;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MovementThread extends Thread {

    public static boolean KEY_A, KEY_D;
    public static final long TICK = 50l;

    @Override
    public void run() {
        while (true) {
            long start = System.currentTimeMillis();
            if (KEY_A) {
                GameClient.instance.session.send(new S06PlayerActionMessage(PlayerAction.LOOK_LEFT));
                GameClient.instance.session.send(new S06PlayerActionMessage(PlayerAction.WALK_LEFT));
            }
            if (KEY_D) {
                GameClient.instance.session.send(new S06PlayerActionMessage(PlayerAction.LOOK_RIGHT));
                GameClient.instance.session.send(new S06PlayerActionMessage(PlayerAction.WALK_RIGHT));
            }

            long length = System.currentTimeMillis() - start;
            if (length < TICK) {
                try {
                    sleep(TICK - length);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
