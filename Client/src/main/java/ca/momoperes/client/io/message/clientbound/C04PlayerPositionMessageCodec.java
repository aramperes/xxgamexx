package ca.momoperes.client.io.message.clientbound;

import ca.momoperes.client.GameClient;
import ca.momoperes.client.io.DynamicSession;
import ca.momoperes.client.io.message.PlayerAction;
import ca.momoperes.client.io.message.serverbound.S06PlayerActionMessage;
import ca.momoperes.client.multiplayer.GamePlayerMP;
import ca.momoperes.client.world.Location;
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

    public void handle(DynamicSession session, C04PlayerPositionMessage message) {
        //session.send(new S06PlayerActionMessage(PlayerAction.ATTACK));
        if (GameClient.instance.multiplayer.getPlayer(message.getUsername()) == null) {
            GamePlayerMP mp = new GamePlayerMP(message.getUsername());
            mp.setLocation(message.getLocation());
            GameClient.instance.multiplayer.addPlayer(mp);
        } else {
            GameClient.instance.multiplayer.getPlayer(message.getUsername()).setLocation(message.getLocation());
        }
        System.out.println("Location of " + message.getUsername() + " = " + message.getLocation().getX() + ", " + message.getLocation().getY());
    }
}
