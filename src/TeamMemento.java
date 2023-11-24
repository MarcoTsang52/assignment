import java.util.Vector;

public class TeamMemento {
    private String TeamName;
    private Vector<Player> players;
    private String TeamId;

    public TeamMemento(String name, String TeamId, Vector<Player> players) {
        this.TeamName = name;
        this.players = players;
        this.TeamId = TeamId;
    }

    public String getTeamName() {
        return TeamName;
    }

    public Vector<Player> getPlayers() {
        return players;
    }

    public String getTeamId() {
        return TeamId;
    }
}
