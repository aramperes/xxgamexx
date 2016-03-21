package ca.momoperes.server;

import ca.momoperes.server.io.DynamicSession;
import ca.momoperes.server.io.GamePlayer;
import ca.momoperes.server.io.GameProtocol;
import ca.momoperes.server.io.message.clientbound.C04PlayerPositionMessage;
import ca.momoperes.server.io.message.clientbound.C07PlayerQuitMessage;
import ca.momoperes.server.world.Location;
import com.flowpowered.network.NetworkServer;
import com.flowpowered.network.protocol.AbstractProtocol;
import com.flowpowered.network.protocol.ProtocolRegistry;
import com.flowpowered.network.session.Session;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameServer extends NetworkServer {

    public static final byte VERSION = 0x01;
    public static final String NAME = "My Server";
    public static GameServer instance;

    private final List<GamePlayer> players;
    private final int port;

    private ProtocolRegistry<AbstractProtocol> pr = new ProtocolRegistry<AbstractProtocol>();

    public GameServer() {
        this(7499);
    }

    public GameServer(int port) {
        this.port = port;
        this.players = new ArrayList<GamePlayer>();
        bind();
    }

    private void bind() {
        bind(new InetSocketAddress(port));
        pr.registerProtocol(port, new GameProtocol());
        System.out.println("Opened for connections on port " + port);
    }

    public static void main(String[] args) {
        instance = new GameServer();
    }

    public Session newSession(Channel channel) {
        DynamicSession session = new DynamicSession(channel, pr.getProtocol(channel.localAddress()));
        return session;
    }

    public void sessionInactivated(Session session) {
        GamePlayer rip = getPlayer((DynamicSession) session);
        players.remove(rip);
        for (GamePlayer pl : players) {
            pl.getSession().send(new C07PlayerQuitMessage(rip.getUsername()));
        }
    }

    public void onPlayerJoin(GamePlayer player) {
        Random random = new Random();
        double x = random.nextDouble() * 600 + 70;
        double y = random.nextDouble() * 400 + 110;
        player.setLocation(new Location(x, y));
        for (GamePlayer p : players) {
            for (GamePlayer mp : players) {
                p.getSession().send(new C04PlayerPositionMessage(mp.getUsername(), mp.getLocation()));
            }
        }
    }

    public GamePlayer getPlayer(String username) {
        for (GamePlayer gamePlayer : getPlayers()) {
            if (gamePlayer.getUsername().equalsIgnoreCase(username))
                return gamePlayer;
        }

        return null;
    }

    public GamePlayer getPlayer(DynamicSession session) {
        for (GamePlayer player : players) {
            if (player.getSession().getSessionId().equals(session.getSessionId()))
                return player;
        }
        return null;
    }

    public GamePlayer createPlayer(DynamicSession session, byte version) {
        if (VERSION != version) {
            return null;
        }
        if (getPlayers().size() == 2) {
            return null;
        }
        if (getPlayer(session) != null) {
            return null;
        }
        GamePlayer player = new GamePlayer(null, session);
        players.add(player);
        return player;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }
}
