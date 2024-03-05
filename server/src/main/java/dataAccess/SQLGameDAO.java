package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.List;

public class SQLGameDAO implements GameDAO {

    @Override
    public int createGame(String gameName) {
        return 0;
    }

    @Override
    public GameData getGameData(int gameID) {
        return null;
    }

    @Override
    public List<GameData> getGameList() {
        return null;
    }

    @Override
    public void addParticipant(int gameID, String username, ChessGame.TeamColor color) {

    }

    @Override
    public boolean clearData() {
        return false;
    }
}
