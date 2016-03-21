package ca.momoperes.client.multiplayer;

import ca.momoperes.client.world.Location;

public class GamePlayerMP {

    public static final int FACE_R = 0;
    public static final int FACE_L = 1;

    private final String username;
    private Location location;
    private boolean crouched;
    private int facing = FACE_R;

    public GamePlayerMP(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isCrouched() {
        return crouched;
    }

    public void setCrouched(boolean crouched) {
        this.crouched = crouched;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }
}
