package ca.momoperes.server.io;

import ca.momoperes.server.world.Location;

import java.net.InetSocketAddress;

public class GamePlayer {

    private String username;
    private final DynamicSession session;
    private boolean confirmed = false;
    private Location location;

    public GamePlayer(String username, DynamicSession session) {
        this.username = username;
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DynamicSession getSession() {
        return session;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
