package ca.momoperes.client.io.message.clientbound;

import ca.momoperes.client.GameClient;
import ca.momoperes.client.io.DynamicSession;
import ca.momoperes.client.io.message.serverbound.S01InitialMessage;
import ca.momoperes.client.io.message.serverbound.S02ConfirmMessage;
import com.flowpowered.network.Codec;
import com.flowpowered.network.MessageHandler;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class C01ResponseMessageCodec implements Codec<C01ResponseMessage>, MessageHandler<DynamicSession, C01ResponseMessage> {

    public C01ResponseMessageCodec() {

    }

    public C01ResponseMessage decode(ByteBuf byteBuf) throws IOException {
        int players = byteBuf.readInt();

        int serverLength = byteBuf.readInt();
        byte[] serverB = byteBuf.readBytes(serverLength).array();
        String server = new String(serverB);
        byte version = byteBuf.readByte();

        return new C01ResponseMessage(players, server, version);
    }

    public ByteBuf encode(ByteBuf byteBuf, C01ResponseMessage message) throws IOException {
        byteBuf.writeInt(message.getPlayers());
        byteBuf.writeInt(message.getServer().length());
        byteBuf.writeBytes(message.getServer().getBytes());
        byteBuf.writeByte(message.getVersion());
        return byteBuf;
    }

    public void handle(DynamicSession dynamicSession, C01ResponseMessage message) {
        //System.out.println("Received incomming 0x01 message: username=" + s01InitialMessage.getUsername() + ", version=" + s01InitialMessage.getVersion());
        System.out.println("Received response from server '" + message.getServer() + "'.");
        dynamicSession.send(new S02ConfirmMessage(GameClient.USERNAME, message.getServer()));
    }
}
