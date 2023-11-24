import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;

public interface CreateCommand {
    Team execute();
}

class CreateTeam implements CreateCommand{
    private TeamFactory factory;
    CreateTeam(String TeamId, String TeamName, String choice) {
        if(choice.equals("f")){
            this.factory = new FootballFactory(TeamId, TeamName);
        }else{
            this.factory = new VolleyballFactory(TeamId, TeamName);
        }
    }
    @Override
    public Team execute() {
        return this.factory.createTeam();
    }
}
class SetCurrentTeam implements CreateCommand{
    private Vector<Team> teams;
    private String TeamId;

    SetCurrentTeam(Vector<Team> teams, String TeamId){
        this.teams = teams;
        this.TeamId = TeamId;
    }
    @Override
    public Team execute() {
        for(Team team: teams){
            if(team.getTeamID().equals(this.TeamId)){
                return team;
            }
        }
        return null;
    }
}
class AddPlayer implements CreateCommand{
    private PlayerFactory playerFactory;
    private Team team;
    AddPlayer(Team team, String playId, String playName, int position){
        this.playerFactory = new PlayerFactory(playId, playName, position);
        this.team = team;
    }
    @Override
    public Team execute() {
        this.team.addPlayer(this.playerFactory.createPlayer());
        return this.team;
    }

}

class ModifyPlayerPosition implements CreateCommand{
    private String playerId;
    private int position;

    private Team team;

    ModifyPlayerPosition(String playerId, int position, Team team){
        this.playerId = playerId;
        this.position = position;
        this.team = team;
    }
    @Override
    public Team execute() {
        Enumeration<Player> playerEnumeration = this.team.getAllPlayers();
        while (playerEnumeration.hasMoreElements()){
            Player player = playerEnumeration.nextElement();
            if(player.getPlayerID().equals(this.playerId)){
                player.setPosition(this.position);
                break;
            }
        }
        return this.team;
    }
}

class DeletePlayer implements CreateCommand{
    private Team team;
    private String playerId;

    public DeletePlayer(Team team, String playerId) {
        this.team = team;
        this.playerId = playerId;
    }

    @Override
    public Team execute() {
        Player removePlayer = null;
        Enumeration<Player> playerEnumeration = this.team.getAllPlayers();
        while (playerEnumeration.hasMoreElements()) {
            Player player = playerEnumeration.nextElement();
            if(player.getPlayerID().equals(this.playerId)) {
                removePlayer = player;break;
            }
        }
        this.team.remove(removePlayer);
        return this.team;
    }


}
class ChangeTeamName implements CreateCommand{
    private Team team;
    private String name;
    public ChangeTeamName(Team team, String name) {
        this.team = team;
        this.name = name;
    }

    @Override
    public Team execute() {
        this.team.setName(this.name);
        return this.team;
    }


}

class Undo implements CreateCommand{
    private Stack<TeamMemento> UndoStack;
    private Stack<TeamMemento> RedoStack;
    private Team CurrentTeam;

    public Undo(Stack<TeamMemento> Undostack, Stack<TeamMemento> RedoStack, Team currentTeam) {
        this.UndoStack = Undostack;
        this.RedoStack = RedoStack;
        CurrentTeam = currentTeam;
    }

    @Override
    public Team execute() {

        this.RedoStack.push(this.CurrentTeam.createNameMemento());
        TeamMemento teamMemento = this.UndoStack.pop();
        this.CurrentTeam.restoreNameFromMemento(teamMemento);
        return this.CurrentTeam;
    }
}

class Redo implements CreateCommand{

    private Stack<TeamMemento> UndoStack;

    private Stack<TeamMemento> RedoStack;

    private Team CurrentTeam;

    public Redo(Stack<TeamMemento> Undostack, Stack<TeamMemento> RedoStack, Team currentTeam) {
        this.UndoStack = Undostack;
        this.RedoStack = RedoStack;
        CurrentTeam = currentTeam;
    }

    @Override
    public Team execute() {
        this.UndoStack.push(this.CurrentTeam.createNameMemento());
        TeamMemento teamMemento = this.RedoStack.pop();
        this.CurrentTeam.restoreNameFromMemento(teamMemento);
        return this.CurrentTeam;
    }
}