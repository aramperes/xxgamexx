package ca.momoperes.server.io;

import com.flowpowered.network.Message;
import com.flowpowered.network.MessageHandler;
import com.flowpowered.network.exception.ChannelClosedException;
import com.flowpowered.network.protocol.AbstractProtocol;
import com.flowpowered.network.session.BasicSession;
import io.netty.channel.Channel;

public class DynamicSession extends BasicSession {
    public DynamicSession(Channel channel, AbstractProtocol bootstrapProtocol) {
        super(channel, bootstrapProtocol);
    }

    @Override
    public void setProtocol(AbstractProtocol protocol) {
        super.setProtocol(protocol);
    }

    @Override
    public void onHandlerThrowable(Message message, MessageHandler<?, ?> handle, Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onOutboundThrowable(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void send(Message message) throws ChannelClosedException {
        super.send(message);
        System.out.println("Sent message '" + message.getClass() + "'.");
    }

    @Override
    public void onInboundThrowable(Throwable throwable) {
        throwable.printStackTrace();
    }
}
