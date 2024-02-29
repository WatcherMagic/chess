package dataAccess;

import dataAccess.objects.GameData;

import java.util.ArrayList;
import java.util.List;

public class MemGameDAO implements GameDAO {

    List<GameData> games;

    public MemGameDAO() {
        this.games = new ArrayList<>();
    }

    @Override
    public GameData createGame(String gameName) {
        //return new GameData()
    }

    @Override
    public List<GameData> getGameList() {
        return games;
        //modify later to return copy so list is protected (do for user & auth too)
    }

}
