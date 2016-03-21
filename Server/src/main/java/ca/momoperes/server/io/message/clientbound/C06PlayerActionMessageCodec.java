package ca.momoperes.server.io.message.clientbound;

import ca.momoperes.server.io.DynamicSession;
import ca.momoperes.server.io.message.PlayerAction;
import com.flowpowered.network.Codec;
import com.flowpowered.network.MessageHandler;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class C06PlayerActionMessageCodec implements Codec<C06PlayerActionMessage>, MessageHandler<DynamicSession, C06PlayerActionMessage> {
    public C06PlayerActionMessage decode(ByteBuf byteBuf) throws IOException {
        int usernameLength = byteBuf.readInt();
        String username = new String(byteBuf.readBytes(usernameLength).array());
        int actionId = byteBuf.readInt();
        PlayerAction action = PlayerAction.get(actionId);
        return new C06PlayerActionMessage(username, action);
    }

    public ByteBuf encode(ByteBuf byteBuf, C06PlayerActionMessage message) throws IOException {
        byteBuf.writeInt(message.getUsername().length());
        byteBuf.writeBytes(message.getUsername().getBytes());
        byteBuf.writeInt(message.getAction().getId());
        return byteBuf;
    }

    public void handle(DynamicSession session, C06PlayerActionMessage message) {
        // Client handle MP action
    }
}
