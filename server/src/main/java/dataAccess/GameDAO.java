package dataAccess;

import dataAccess.objects.GameData;

import java.util.List;

public interface GameDAO {

    void createGame(String gameName);

    List<GameData> getGameList();

}
