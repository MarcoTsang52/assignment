import java.util.*;

public class Main {

    public static void main(String[] args) {
        TeamFactory factory;
        Team CurrentTeam = null;
        Vector<Team> teams = new Vector<Team>();
        Scanner scanner = new Scanner(System.in);
        Stack<TeamMemento> UndoStack = new Stack<>();
        Stack<TeamMemento> RedoStack = new Stack<>();
        List<String> UndoList = new ArrayList<>();
        List<String> RedoList = new ArrayList<>();
        Stack<Vector<Team>> teamsUndoStack = new Stack<>();
        Stack<Vector<Team>> teamsRedoStack = new Stack<>();
        boolean is_continue = true;
        String choice;
        Command command;
        while (is_continue){

            System.out.println("Sport Teams Management System (STMS)");
            System.out.println("c = create team, g = set current team, a = add player, m = modify player’s\n" +
                    "position, d = delete player, s = show team, p = display all teams, t = change\n" +
                    "team’s name, u = undo, r = redo, l = list undo/redo, x = exit system");
            if(CurrentTeam != null){
                System.out.printf("The current team is %s %s.\n", CurrentTeam.getTeamID(), CurrentTeam.getName());

            }
            System.out.print("Please enter command [ c | g | a | m | d | s | p | t | u | r | l | x ] :-");
            choice = scanner.nextLine();
            switch (choice){
                case "x": { is_continue = false; break;}
                case "c": {

                    if(CurrentTeam == null){
                        UndoStack.push(null);
                    }else{
                    UndoStack.push(CurrentTeam.createNameMemento());}
                    teamsUndoStack.push(teams);
                    System.out.print("Enter sport type (v = volleyball | f = football) :-");
                    String team_type = scanner.nextLine();
                    System.out.print("Team ID:");
                    String team_id = scanner.nextLine();
                    System.out.print("Team Name:");
                    String team_name = scanner.nextLine();
                    CreateCommand createCommand = new CreateTeam(team_id, team_name, team_type);
                    CurrentTeam = createCommand.execute();
                    System.out.println("Current team is changed to " + CurrentTeam.getTeamID() + ".");
                    teams.add(CurrentTeam);
                    if(team_type.equals("v")) {
                        UndoList.add("(Create volleyball team, %s, %s".formatted(CurrentTeam.getTeamID(), CurrentTeam.getName()));
                    }else{
                        UndoList.add("(Create football team, %s, %s".formatted(CurrentTeam.getTeamID(), CurrentTeam.getName()));
                    }
                    break;

                }
                case "a": {
                    scanner.useDelimiter(",\\s*");
                    assert CurrentTeam != null;
                    UndoStack.push(CurrentTeam.createNameMemento());
                    teamsUndoStack.push(teams);
                    System.out.print("Please input player information (id, name):");
                    String userInput = scanner.nextLine();
                    String[] inputArray = userInput.split(",");

                    if (inputArray.length == 2) {
                        String firstString = inputArray[0].trim();
                        String secondString = inputArray[1].trim();
                        assert CurrentTeam != null;
                        if(CurrentTeam instanceof VolleyballTeam){
                            System.out.print("Position (1 = attacker | 2 = defender ):");
                        }else{
                            System.out.print("Position (1 = attacker | 2 = defender | 3 = midfielder | 4 = forward ):");
                        }
                        scanner.reset();
                        int position = scanner.nextInt();

                        CreateCommand addPlayCommand = new AddPlayer(CurrentTeam, firstString, secondString, position);
                        CurrentTeam = addPlayCommand.execute();

                        System.out.println("Player is added.");
                        UndoList.add("Add player, %s, %s".formatted(firstString, secondString));

                    }
                    break;
                }
                case "s": {
                    command = new ShowCurrentTeam(CurrentTeam);
                    command.execute();
                    break;
                }
                case "p": {
                    command = new DisplayAllTeam(teams);
                    command.execute();
                    break;
                }
                case "m":
                {
                    assert CurrentTeam != null;
                    UndoStack.push(CurrentTeam.createNameMemento());
                    teamsUndoStack.push(teams);
                    System.out.print("Please input player ID:");
                    String playId = scanner.nextLine();
                    int position;
                    assert CurrentTeam != null;
                    if(CurrentTeam.getClass().toString().equals("VolleyballTeam")){
                        System.out.print("Position (1 = attacker | 2 = defender ):");

                    }else{
                        System.out.print("Position (1 = attacker | 2 = defender | 3 = midfielder | 4 = forward ):");
                    }
                    position = scanner.nextInt();
                    CreateCommand command1  = new ModifyPlayerPosition(playId, position, CurrentTeam);
                    CurrentTeam = command1.execute();
                    System.out.print("Position is updated.");
                    UndoList.add("Modify player’s position, %s, defender".formatted(playId));
                    break;
                }
                case "d": {
                    assert CurrentTeam != null;
                    UndoStack.push(CurrentTeam.createNameMemento());
                    teamsUndoStack.push(teams);
                    System.out.print("Please input player ID:");
                    String playId = scanner.nextLine();
                    CreateCommand command1  = new DeletePlayer(CurrentTeam, playId);
                    CurrentTeam = command1.execute();
                    System.out.println("Player is deleted.");
                    UndoList.add("Delete player, %s".formatted(playId));
                    break;
                }
                case "t":{
                    assert CurrentTeam != null;
                    UndoStack.push(CurrentTeam.createNameMemento());
                    teamsUndoStack.push(teams);

                    System.out.print("Please input new name of the current team:");
                    String newName = scanner.nextLine();
                    CreateCommand command1 = new ChangeTeamName(CurrentTeam, newName);
                    CurrentTeam = command1.execute();
                    UndoList.add("Change team’s name, %s, %s".formatted(CurrentTeam.getTeamID(), newName));
                    System.out.println("Team’s name is updated.");
                    break;
                }
                case "g":{
                    System.out.print("Please input team ID:");
                    String TeamId = scanner.nextLine();
                    CreateCommand createCommand = new SetCurrentTeam(teams, TeamId);
                    CurrentTeam = createCommand.execute();
                    System.out.printf("Changed current team to %s.\n", TeamId);
                    break;
                }
                case "l":{
                    command = new ShowURdoList(UndoList, RedoList);
                    command.execute();
                    break;
                }
                case "u":{
                    assert CurrentTeam != null;
                    teamsRedoStack.push(teams);
                    RedoStack.push(CurrentTeam.createNameMemento());
                    TeamMemento teamMemento = UndoStack.pop();
                    CurrentTeam.restoreNameFromMemento(teamMemento);
                    teams = teamsUndoStack.pop();

                    String str = UndoList.remove(UndoList.size() - 1);
                    System.out.printf("Command (%s) is undone\n", str);
                    RedoList.add(str);
                    break;
                }
                case "r":{
                    assert CurrentTeam != null;
                    teamsUndoStack.push(teams);
                    UndoStack.push(CurrentTeam.createNameMemento());
                    TeamMemento teamMemento = RedoStack.pop();
                    CurrentTeam.restoreNameFromMemento(teamMemento);
                    teams = teamsUndoStack.pop();

                    String str = RedoList.remove(0);
                    System.out.printf("Command (%s) is redone\n", str);
                    UndoList.add(str);
                    break;
                }
            }
        }
    }

}