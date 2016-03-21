package ca.momoperes.server.io.message.serverbound;

import ca.momoperes.server.GameServer;
import ca.momoperes.server.io.DynamicSession;
import ca.momoperes.server.io.GamePlayer;
import ca.momoperes.server.io.message.PlayerAction;
import ca.momoperes.server.io.message.clientbound.C04PlayerPositionMessage;
import ca.momoperes.server.io.message.clientbound.C06PlayerActionMessage;
import ca.momoperes.server.world.Location;
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
        GamePlayer player = GameServer.instance.getPlayer(session);
        PlayerAction action = message.getAction();
        System.out.println("Received Player action " + message.getAction() + " from '" + player.getUsername() + "'.");

        if (message.getAction() == PlayerAction.WALK_LEFT) {
            Location location = player.getLocation();
            location.setX(location.getX() - 6);
            player.setLocation(location);
        }
        if (message.getAction() == PlayerAction.WALK_RIGHT) {
            Location location = player.getLocation();
            location.setX(location.getX() + 6);
            player.setLocation(location);
        }

        for (GamePlayer mp : GameServer.instance.getPlayers()) {
            mp.getSession().send(new C06PlayerActionMessage(player.getUsername(), action));
            mp.getSession().send(new C04PlayerPositionMessage(player.getUsername(), player.getLocation()));
        }
    }
}
