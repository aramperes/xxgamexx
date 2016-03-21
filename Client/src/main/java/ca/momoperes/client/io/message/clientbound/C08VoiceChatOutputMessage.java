package ca.momoperes.client.io.message.clientbound;

import com.flowpowered.network.Message;

public class C08VoiceChatOutputMessage implements Message {
    private final String username;
    private final byte[] data;

    public C08VoiceChatOutputMessage(String username, byte[] data) {
        this.username = username;
        this.data = data;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getData() {
        return data;
    }
}
