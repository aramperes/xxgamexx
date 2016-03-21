package ca.momoperes.client.io.message.clientbound;

import ca.momoperes.client.GameClient;
import ca.momoperes.client.io.DynamicSession;
import com.flowpowered.network.Codec;
import com.flowpowered.network.MessageHandler;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class C07PlayerQuitMessageCodec implements Codec<C07PlayerQuitMessage>, MessageHandler<DynamicSession, C07PlayerQuitMessage> {
    public C07PlayerQuitMessage decode(ByteBuf byteBuf) throws IOException {
        int usernameLength = byteBuf.readInt();
        String username = new String(byteBuf.readBytes(usernameLength).array());
        return new C07PlayerQuitMessage(username);
    }

    public ByteBuf encode(ByteBuf byteBuf, C07PlayerQuitMessage message) throws IOException {
        byteBuf.writeInt(message.getUsername().length());
        byteBuf.writeBytes(message.getUsername().getBytes());
        return null;
    }

    public void handle(DynamicSession dynamicSession, C07PlayerQuitMessage message) {
        GameClient.instance.multiplayer.removePlayer(GameClient.instance.multiplayer.getPlayer(message.getUsername()));
    }
}
