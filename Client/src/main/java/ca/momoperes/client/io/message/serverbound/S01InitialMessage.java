package ca.momoperes.client.io.message.serverbound;

import com.flowpowered.network.Message;

public class S01InitialMessage implements Message {
    private final String username;
    private final byte version;

    public S01InitialMessage(String username, byte version) {
        this.username = username;
        this.version = version;
    }

    public String getUsername() {
        return username;
    }

    public byte getVersion() {
        return version;
    }
}
