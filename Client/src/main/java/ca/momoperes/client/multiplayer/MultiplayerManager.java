package ca.momoperes.client.multiplayer;

import ca.momoperes.client.game.MovementThread;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerManager {
    private List<GamePlayerMP> players;

    public MultiplayerManager() {
        players = new ArrayList<GamePlayerMP>();
        new MovementThread().start();
    }

    public void removePlayer(GamePlayerMP player) {
        players.remove(player);
    }

    public void addPlayer(GamePlayerMP player) {
        players.add(player);
    }

    public GamePlayerMP getPlayer(String username) {
        for (GamePlayerMP gamePlayerMP : getPlayers()) {
            if (gamePlayerMP.getUsername().equalsIgnoreCase(username))
                return gamePlayerMP;
        }
        return null;
    }

    public List<GamePlayerMP> getPlayers() {
        return players;
    }
}
