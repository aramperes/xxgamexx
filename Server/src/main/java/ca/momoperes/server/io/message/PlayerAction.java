package ca.momoperes.server.io.message;

public enum PlayerAction {
    CROUCH(1),
    JUMP(2),
    INTERACT(3),
    ATTACK(4),
    WALK_LEFT(5),
    WALK_RIGHT(6),
    LOOK_LEFT(7),
    LOOK_RIGHT(8);

    private final int id;

    PlayerAction(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PlayerAction get(int id) {
        for (PlayerAction playerAction : values()) {
            if (playerAction.getId() == id)
                return playerAction;
        }

        return null;
    }
}
