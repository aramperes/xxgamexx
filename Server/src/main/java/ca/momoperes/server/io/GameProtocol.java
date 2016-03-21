package ca.momoperes.server.io;
import ca.momoperes.server.io.message.clientbound.*;
import ca.momoperes.server.io.message.serverbound.*;
import com.flowpowered.network.Codec;
import com.flowpowered.network.Message;
import com.flowpowered.network.MessageHandler;
import com.flowpowered.network.exception.IllegalOpcodeException;
import com.flowpowered.network.exception.UnknownPacketException;
import com.flowpowered.network.protocol.simple.SimpleProtocol;
import io.netty.buffer.ByteBuf;

public class GameProtocol extends SimpleProtocol {
    public GameProtocol() {
        super("GameProtocol", 9);
        registerMessage(S01InitialMessage.class, S01InitialMessageCodec.class, S01InitialMessageCodec.class, null);
        registerMessage(C01ResponseMessage.class, C01ResponseMessageCodec.class, C01ResponseMessageCodec.class, null);
        registerMessage(S02ConfirmMessage.class, S02ConfirmMessageCodec.class, S02ConfirmMessageCodec.class, null);
        registerMessage(C04PlayerPositionMessage.class, C04PlayerPositionMessageCodec.class, C04PlayerPositionMessageCodec.class, null);
        registerMessage(S06PlayerActionMessage.class, S06PlayerActionMessageCodec.class, S06PlayerActionMessageCodec.class, null);
        registerMessage(C06PlayerActionMessage.class, C06PlayerActionMessageCodec.class, C06PlayerActionMessageCodec.class, null);
        registerMessage(C07PlayerQuitMessage.class, C07PlayerQuitMessageCodec.class, C07PlayerQuitMessageCodec.class, null);
        registerMessage(S08VoiceChatInputMessage.class, S08VoiceChatInputMessageCodec.class, S08VoiceChatInputMessageCodec.class, null);
        registerMessage(C08VoiceChatOutputMessage.class, C08VoiceChatOutputMessageCodec.class, C08VoiceChatOutputMessageCodec.class, null);
    }

    public Codec<?> readHeader(ByteBuf byteBuf) throws UnknownPacketException {
        int id = byteBuf.readUnsignedShort();
        int length = byteBuf.readInt();
        try {
            return getCodecLookupService().find(id);
        } catch (IllegalOpcodeException e) {
            throw new UnknownPacketException("Packet not found!", id, length);
        }
    }

    public ByteBuf writeHeader(ByteBuf header, Codec.CodecRegistration codec, ByteBuf data) {
        header.writeShort(codec.getOpcode());
        header.writeInt(data.writerIndex());
        return header;
    }

    public <M extends Message> MessageHandler<?, M> getMessageHandle(Class<M> message) {
        MessageHandler<?, M> handle = super.getMessageHandle(message);
        if (handle == null) {
            System.out.println("Null handle");
            System.out.println(message);
            System.out.println(getHandlerLookupService());
        }
        return handle;
    }
}
