package ca.momoperes.client.io.message.clientbound;

import com.flowpowered.network.Message;

public class C07PlayerQuitMessage implements Message {
    private final String username;

    public C07PlayerQuitMessage(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
