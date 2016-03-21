package ca.momoperes.server.io.message.serverbound;

import ca.momoperes.server.GameServer;
import ca.momoperes.server.io.DynamicSession;
import ca.momoperes.server.io.GamePlayer;
import ca.momoperes.server.io.message.clientbound.C08VoiceChatOutputMessage;
import com.flowpowered.network.Codec;
import com.flowpowered.network.MessageHandler;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class S08VoiceChatInputMessageCodec implements Codec<S08VoiceChatInputMessage>, MessageHandler<DynamicSession, S08VoiceChatInputMessage> {

    public S08VoiceChatInputMessage decode(ByteBuf byteBuf) throws IOException {
        int dataLength = byteBuf.readInt();
        byte[] data = byteBuf.readBytes(dataLength).array();
        return new S08VoiceChatInputMessage(data);
    }

    public ByteBuf encode(ByteBuf byteBuf, S08VoiceChatInputMessage message) throws IOException {
        byteBuf.writeInt(message.getData().length);
        byteBuf.writeBytes(message.getData());
        return byteBuf;
    }

    public void handle(DynamicSession dynamicSession, S08VoiceChatInputMessage message) {
        //Serverbound
        GamePlayer player = GameServer.instance.getPlayer(dynamicSession);

        for (GamePlayer gamePlayer : GameServer.instance.getPlayers()) {
            if (player.getUsername().equals(gamePlayer.getUsername()))
                continue;
            gamePlayer.getSession().send(new C08VoiceChatOutputMessage(player.getUsername(), message.getData()));
        }
    }
}
