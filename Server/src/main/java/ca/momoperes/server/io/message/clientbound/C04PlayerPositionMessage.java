package ca.momoperes.server.io.message.clientbound;

import ca.momoperes.server.world.Location;
import com.flowpowered.network.Message;

public class C04PlayerPositionMessage implements Message {
    private final String username;
    private final Location location;

    public C04PlayerPositionMessage(String username, Location location) {
        this.username = username;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public Location getLocation() {
        return location;
    }
}
