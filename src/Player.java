public class Player {
    private String playerID;

    private String name;
    private int position;

    public Player(String playerID, String name) {
        this.playerID = playerID;
        this.name = name;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }


}
