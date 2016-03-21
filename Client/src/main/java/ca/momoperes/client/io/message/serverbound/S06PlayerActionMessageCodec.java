package ca.momoperes.client.io.message.serverbound;

import ca.momoperes.client.io.DynamicSession;
import ca.momoperes.client.io.message.PlayerAction;
import com.flowpowered.network.Codec;
import com.flowpowered.network.MessageHandler;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class S06PlayerActionMessageCodec implements Codec<S06PlayerActionMessage>, MessageHandler<DynamicSession, S06PlayerActionMessage> {

    public S06PlayerActionMessage decode(ByteBuf byteBuf) throws IOException {
        int actionId = byteBuf.readInt();
        PlayerAction action = PlayerAction.get(actionId);
        return new S06PlayerActionMessage(action);
    }

    public ByteBuf encode(ByteBuf byteBuf, S06PlayerActionMessage message) throws IOException {
        byteBuf.writeInt(message.getAction().getId());
        return byteBuf;
    }

    public void handle(DynamicSession session, S06PlayerActionMessage message) {

    }
}
