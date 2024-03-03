package dataAccess;

import chess.ChessGame;
import dataAccess.objects.GameData;

import java.util.ArrayList;
import java.util.List;

public class MemGameDAO implements GameDAO {

    List<GameData> games;
    int gameIDIncrement;

    public MemGameDAO() {
        this.games = new ArrayList<>();
        this.gameIDIncrement = 0;
    }

    @Override
    public int createGame(String gameName) {
        gameIDIncrement += 1;
        GameData game = new GameData(gameIDIncrement, null, null, gameName, new ChessGame());
        games.add(game);
        return game.gameID();
    }

    @Override
    public GameData getGameData(int gameID) {
        for (GameData gamedata : games) {
            if (gamedata.gameID() == gameID) {
                return gamedata;
            }
        }
        return null;
    }

    @Override
    public List<GameData> getGameList() {
        List<GameData> list = new ArrayList<>(games);
        return list;
    }

    public void addParticipant(int gameID, String username, ChessGame.TeamColor color) {
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
    public void clearData() {
        games.clear();
    }
}
