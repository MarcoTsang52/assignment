import java.util.Enumeration;
import java.util.Vector;

public class Team {
    private String teamID;
    private String name;
    private Vector<Player> players = new Vector<>();

    public Team(String teamID) {
        this.teamID = teamID;
    }

    public String getTeamID() {
        return teamID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void remove(Player player){
        this.players.remove(player);
    }

    public Enumeration<Player> getAllPlayers(){
        return this.players.elements();
    }



    public void displayTeam(){

    }
    public void updatePlayerPosition(){

    }
    public TeamMemento createNameMemento() {
        return new TeamMemento(name, teamID, players);
    }

    public void restoreNameFromMemento(TeamMemento memento) {
        this.name = memento.getTeamName();
        this.teamID = memento.getTeamId();
        this.players = memento.getPlayers();
    }
}
