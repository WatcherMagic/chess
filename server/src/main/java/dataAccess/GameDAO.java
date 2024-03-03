package dataAccess;

import dataAccess.objects.GameData;
import chess.ChessGame.TeamColor;

import java.util.List;

public interface GameDAO {

    int createGame(String gameName);

    GameData getGameData(int gameID);

    List<GameData> getGameList();

    void addParticipant(int gameID, String username, TeamColor color);

    void clearData();
}
