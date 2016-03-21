package ca.momoperes.client.io.message.clientbound;

import ca.momoperes.client.io.message.PlayerAction;
import com.flowpowered.network.Message;

public class C06PlayerActionMessage implements Message {
    private final String username;
    private final PlayerAction action;

    public C06PlayerActionMessage(String username, PlayerAction action) {
        this.username = username;
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public PlayerAction getAction() {
        return action;
    }

}
