package ca.momoperes.server.io.message.clientbound;

import ca.momoperes.server.io.DynamicSession;
import com.flowpowered.network.Codec;
import com.flowpowered.network.MessageHandler;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class C08VoiceChatOutputMessageCodec implements Codec<C08VoiceChatOutputMessage>, MessageHandler<DynamicSession, C08VoiceChatOutputMessage> {
    public C08VoiceChatOutputMessage decode(ByteBuf byteBuf) throws IOException {
        int usernameLength = byteBuf.readInt();
        String username = new String(byteBuf.readBytes(usernameLength).array());
        int dataLength = byteBuf.readInt();
        byte[] data = byteBuf.readBytes(dataLength).array();
        return new C08VoiceChatOutputMessage(username, data);
    }

    public ByteBuf encode(ByteBuf byteBuf, C08VoiceChatOutputMessage message) throws IOException {
        byteBuf.writeInt(message.getUsername().length());
        byteBuf.writeBytes(message.getUsername().getBytes());
        byteBuf.writeInt(message.getData().length);
        byteBuf.writeBytes(message.getData());
        return byteBuf;
    }

    public void handle(DynamicSession session, C08VoiceChatOutputMessage message) {
        // Clientbound
    }
}
