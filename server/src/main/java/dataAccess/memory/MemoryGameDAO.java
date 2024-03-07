package dataAccess.memory;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.GameData;

import java.util.ArrayList;
import java.util.List;

public class MemoryGameDAO implements GameDAO {

    List<GameData> games;
    int gameIDIncrement;

    public MemoryGameDAO() {
        this.games = new ArrayList<>();
        this.gameIDIncrement = 0;
    }

    @Override
    public int addNewGame(String gameName) throws DataAccessException {
        gameIDIncrement += 1;
        GameData game = new GameData(gameIDIncrement, null, null, gameName, new ChessGame());
        games.add(game);
        return game.gameID();
    }

    @Override
    public GameData getGameData(int gameID) throws DataAccessException {
        for (GameData gamedata : games) {
            if (gamedata.gameID() == gameID) {
                return gamedata;
            }
        }
        return null;
    }

    @Override
    public List<GameData> getGameList() throws DataAccessException {
        List<GameData> list = new ArrayList<>(games);
        return list;
    }

    public void addParticipant(int gameID, String username, ChessGame.TeamColor color) throws DataAccessException {
        GameData game = getGameData(gameID);
        int index = games.indexOf(game);
        if (color == ChessGame.TeamColor.WHITE) {
            games.set(index, new GameData(gameID, username, game.blackUsername(),
                    game.gameName(), game.chessGame()));
        }
        else if (color == ChessGame.TeamColor.BLACK) {
            games.set(index, new GameData(gameID, game.whiteUsername(), username,
                    game.gameName(), game.chessGame()));
        }
    }

    @Override
    public boolean clearData() throws DataAccessException {
        games.clear();
        if (games.size() == 0) {
            return true;
        }
        return false;
    }
}
