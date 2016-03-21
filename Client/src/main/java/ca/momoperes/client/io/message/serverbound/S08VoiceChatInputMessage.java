package ca.momoperes.client.io.message.serverbound;

import com.flowpowered.network.Message;

public class S08VoiceChatInputMessage implements Message {
    private byte[] data;

    public S08VoiceChatInputMessage(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }
}
