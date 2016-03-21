package ca.momoperes.client.io.message.serverbound;

import ca.momoperes.client.io.DynamicSession;
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

    public void handle(DynamicSession dynamicSession, S01InitialMessage s01InitialMessage) {
        System.out.println("Received incomming 0x01 message: username=" + s01InitialMessage.getUsername() + ", version=" + s01InitialMessage.getVersion());
    }
}
