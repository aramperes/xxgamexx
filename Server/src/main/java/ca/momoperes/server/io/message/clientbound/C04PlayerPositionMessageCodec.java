package ca.momoperes.server.io.message.clientbound;

import ca.momoperes.server.io.DynamicSession;
import ca.momoperes.server.world.Location;
import com.flowpowered.network.Codec;
import com.flowpowered.network.MessageHandler;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class C04PlayerPositionMessageCodec implements Codec<C04PlayerPositionMessage>, MessageHandler<DynamicSession, C04PlayerPositionMessage> {

    public C04PlayerPositionMessage decode(ByteBuf byteBuf) throws IOException {
        int usernameLength = byteBuf.readInt();
        byte[] usernameB = byteBuf.readBytes(usernameLength).array();
        String username = new String(usernameB);

        double x = byteBuf.readDouble();
        double y = byteBuf.readDouble();
        Location location = new Location(x, y);

        return new C04PlayerPositionMessage(username, location);
    }

    public ByteBuf encode(ByteBuf byteBuf, C04PlayerPositionMessage message) throws IOException {
        byteBuf.writeInt(message.getUsername().length());
        byteBuf.writeBytes(message.getUsername().getBytes());
        byteBuf.writeDouble(message.getLocation().getX());
        byteBuf.writeDouble(message.getLocation().getY());
        return byteBuf;
    }

    public void handle(DynamicSession dynamicSession, C04PlayerPositionMessage c04PlayerPositionMessage) {

    }
}
