package ca.momoperes.client.io.message.serverbound;

import com.flowpowered.network.Message;

public class S02ConfirmMessage implements Message {
    private final String username;
    private final String server;

    public S02ConfirmMessage(String username, String server) {
        this.username = username;
        this.server = server;
    }

    public String getUsername() {
        return username;
    }

    public String getServer() {
        return server;
    }
}
