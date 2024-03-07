package dataAccess;

import chess.ChessGame.TeamColor;
import model.GameData;

import java.util.List;

public interface GameDAO {

    int addNewGame(String gameName) throws DataAccessException;

    GameData getGameData(int gameID) throws DataAccessException;

    List<GameData> getGameList() throws DataAccessException;

    void addParticipant(int gameID, String username, TeamColor color) throws DataAccessException;

    boolean clearData() throws DataAccessException;
}
