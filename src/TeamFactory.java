import java.util.Map;
import java.util.Vector;

public interface TeamFactory {
    Team createTeam();
}

class FootballFactory implements TeamFactory {
    private String TeamId;
    private String TeamName;
    FootballFactory(String team_id, String name){
        this.TeamId = team_id;
        this.TeamName = name;
    }
    @Override
    public Team createTeam() {
        Team team = new VolleyballTeam(this.TeamId);
        team.setName(this.TeamName);return team;
    }

}

class VolleyballFactory implements TeamFactory{
    private String TeamId;
    private String TeamName;
    VolleyballFactory(String team_id, String name){
        this.TeamId = team_id;
        this.TeamName = name;
    }

    @Override
    public Team createTeam() {
        Team team = new VolleyballTeam(this.TeamId);
        team.setName(this.TeamName);
        return team;
    }
}