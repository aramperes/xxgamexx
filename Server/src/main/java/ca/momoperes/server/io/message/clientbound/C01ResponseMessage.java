package ca.momoperes.server.io.message.clientbound;

import com.flowpowered.network.Message;

public class C01ResponseMessage implements Message {
    private final int players;
    private final String server;
    private final byte version;

    public C01ResponseMessage(int players, String server, byte version) {
        this.players = players;
        this.server = server;
        this.version = version;
    }

    public int getPlayers() {
        return players;
    }

    public String getServer() {
        return server;
    }

    public byte getVersion() {
        return version;
    }
}
