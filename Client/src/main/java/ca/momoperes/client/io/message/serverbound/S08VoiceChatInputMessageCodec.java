package ca.momoperes.client.io.message.serverbound;

import ca.momoperes.client.io.DynamicSession;
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
    }
}
