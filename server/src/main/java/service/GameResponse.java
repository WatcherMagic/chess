package service;

public class GameResponse extends Response {

    Integer gameID;
    String playerColor;

    public GameResponse(String message, Integer gameID, String playerColor) {
        super(message);
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    public Integer getGameID() {
        return gameID;
    }

    public String getPlayerColor() {
        return playerColor;
    }
}
