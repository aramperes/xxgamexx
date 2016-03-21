package ca.momoperes.client.io.message.clientbound;

import ca.momoperes.client.GameClient;
import ca.momoperes.client.io.DynamicSession;
import ca.momoperes.client.io.message.PlayerAction;
import ca.momoperes.client.multiplayer.GamePlayerMP;
import com.flowpowered.network.Codec;
import com.flowpowered.network.MessageHandler;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class C06PlayerActionMessageCodec implements Codec<C06PlayerActionMessage>, MessageHandler<DynamicSession, C06PlayerActionMessage> {
    public C06PlayerActionMessage decode(ByteBuf byteBuf) throws IOException {
        int usernameLength = byteBuf.readInt();
        String username = new String(byteBuf.readBytes(usernameLength).array());
        int actionId = byteBuf.readInt();
        PlayerAction action = PlayerAction.get(actionId);
        return new C06PlayerActionMessage(username, action);
    }

    public ByteBuf encode(ByteBuf byteBuf, C06PlayerActionMessage message) throws IOException {
        byteBuf.writeInt(message.getUsername().length());
        byteBuf.writeBytes(message.getUsername().getBytes());
        byteBuf.writeInt(message.getAction().getId());
        return byteBuf;
    }

    public void handle(DynamicSession session, C06PlayerActionMessage message) {
        // Client handle MP action
        //System.out.println("Received MP Player action " + message.getAction() + " from '" + message.getUsername() + "'.");
        GamePlayerMP player = GameClient.instance.multiplayer.getPlayer(message.getUsername());

        if (message.getAction() == PlayerAction.CROUCH) {
            player.setCrouched(!player.isCrouched());
        }
        if (message.getAction() == PlayerAction.LOOK_LEFT) {
            player.setFacing(GamePlayerMP.FACE_L);
        }
        if (message.getAction() == PlayerAction.LOOK_RIGHT) {
            player.setFacing(GamePlayerMP.FACE_R);
        }
    }
}
