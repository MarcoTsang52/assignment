public interface Factory {
    Player createPlayer();
}

class PlayerFactory implements Factory{
    private String playId;
    private String playName;

    private int position;

    public PlayerFactory(String playId, String playName, int position) {
        this.playId = playId;
        this.playName = playName;
        this.position = position;
    }

    @Override
    public Player createPlayer() {
        Player player = new Player(this.playId, this.playName);
        player.setPosition(this.position);
        return player;
    }
}
