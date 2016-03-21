package ca.momoperes.client;


import ca.momoperes.client.display.DisplayThread;
import ca.momoperes.client.display.GameFrame;
import ca.momoperes.client.io.DynamicSession;
import ca.momoperes.client.io.GameProtocol;
import ca.momoperes.client.io.message.serverbound.S01InitialMessage;
import ca.momoperes.client.multiplayer.MultiplayerManager;
import ca.momoperes.client.multiplayer.sound.MicrophoneRecordingThread;
import ca.momoperes.client.multiplayer.sound.VoiceChatThread;
import com.flowpowered.network.NetworkClient;
import com.flowpowered.network.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;

public class GameClient extends NetworkClient {
    public static String USERNAME = "Client";
    public static final byte VERSION = 0x01;
    public static GameClient instance;


    public DynamicSession session;
    public Runnable runnable;
    public MultiplayerManager multiplayer;

    public GameClient() {
        //connectToServer("localhost");
        new DisplayThread().start();
        new MicrophoneRecordingThread().start();
        new VoiceChatThread().start();
        //new GameFrame();
    }

    public void connectToServer(String address) {
        connectToServer(address, 7499);
    }

    public void connectToServer(String address, int port) {
        ChannelFuture connect = connect(new InetSocketAddress(address, port));
    }

    public Session newSession(Channel channel) {
        System.out.println("Connecting to server.");
        session = new DynamicSession(channel, new GameProtocol()) {
            @Override
            public void onReady() {
                if (runnable != null) runnable.run();
                multiplayer = new MultiplayerManager();
                session.send(new S01InitialMessage(USERNAME, VERSION));
            }
        };
        return session;
    }

    public void sessionInactivated(Session session) {
        System.out.println("Disconnected from server: Rejected connection.");
    }

    public static void main(String[] args) {
        instance = new GameClient();
    }
}
