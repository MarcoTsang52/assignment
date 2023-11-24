import java.util.Enumeration;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

public interface Command {
    void execute();
}


class ShowURdoList implements Command{
    private List<String> UndoList;


    private List<String> RedoList;


    public ShowURdoList(List<String> undoList, List<String> redoList) {
        this.UndoList = undoList;
        this.RedoList = redoList;
    }

    @Override
    public void execute() {
        System.out.println("Undo List");
        for(String str: this.UndoList){
            System.out.println(str);
        }
        System.out.println("-- End of undo list --");

        System.out.println("Redo List");
        for(String str: this.RedoList){
            System.out.println(str);
        }
        System.out.println("-- End of redo list –-");
    }
}



class ShowCurrentTeam implements Command{
    private Team team;

    public ShowCurrentTeam(Team team) {
        this.team = team;
    }

    @Override
    public void execute() {
        if(this.team.getClass().toString().equals("VolleyballTeam")){
            System.out.printf("Volleyball Team %s (%s)", this.team.getName(), this.team.getTeamID());
        }
        Enumeration<Player> playerEnumeration = this.team.getAllPlayers();
        while (playerEnumeration.hasMoreElements()){
            Player player = playerEnumeration.nextElement();
            switch (player.getPosition()){
                case 1: {
                    System.out.println("Attacker:");
                    break;
                }
                case 2: {
                    System.out.println("Defender:");
                    break;
                }
                case 3: {
                    System.out.println("Midfielder:");
                    break;
                }case 4: {
                    System.out.println("Forwarder:");
                    break;
                }
            }
            System.out.printf("%s, %s\n", player.getPlayerID(), player.getName());

        }

    }


}

class DisplayAllTeam implements Command{
    private Vector<Team> teams;

    public DisplayAllTeam(Vector<Team> teams) {
        this.teams = teams;
    }

    @Override
    public void execute() {
        for(Team team: teams){
            if(team.getClass().toString().equals("VolleyballTeam")){
                System.out.printf("Volleyball Team %s (%s)", team.getName(), team.getTeamID());
            }else{
                System.out.printf("Football Team %s (%s)", team.getName(), team.getTeamID());
            }
        }
    }


}



