package ca.momoperes.server.io.message.serverbound;

import ca.momoperes.server.GameServer;
import ca.momoperes.server.io.DynamicSession;
import ca.momoperes.server.io.GamePlayer;
import ca.momoperes.server.io.message.clientbound.C01ResponseMessage;
import com.flowpowered.network.Codec;
import com.flowpowered.network.MessageHandler;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class S01InitialMessageCodec implements Codec<S01InitialMessage>, MessageHandler<DynamicSession, S01InitialMessage> {

    public S01InitialMessageCodec() {

    }

    public S01InitialMessage decode(ByteBuf byteBuf) throws IOException {
        int nameLength = byteBuf.readInt();
        byte[] usernameB = byteBuf.readBytes(nameLength).array();
        String username = new String(usernameB);
        byte version = byteBuf.readByte();

        return new S01InitialMessage(username, version);
    }

    public ByteBuf encode(ByteBuf byteBuf, S01InitialMessage s01InitialMessage) throws IOException {
        byteBuf.writeInt(s01InitialMessage.getUsername().length());
        byteBuf.writeBytes(s01InitialMessage.getUsername().getBytes());
        byteBuf.writeByte(s01InitialMessage.getVersion());
        return byteBuf;
    }

    public void handle(DynamicSession session, S01InitialMessage message) {
        if (!session.isActive()) {
            session.disconnect();
            return;
        }
        System.out.println("Received incomming 0x01 message: username=" + message.getUsername() + ", version=" + message.getVersion());
        if (GameServer.instance.getPlayer(session) != null) {
            System.out.println("Session exists.");
            return;
        }
        GamePlayer player = GameServer.instance.createPlayer(session, message.getVersion());
        if (player == null) {
            System.out.println("Failed creation");
            session.disconnect();
        }
        session.send(new C01ResponseMessage(GameServer.instance.getPlayers().size(), GameServer.NAME, GameServer.VERSION));
    }
}
