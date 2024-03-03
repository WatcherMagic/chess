package server.resreq;

import dataAccess.objects.GameData;

import java.util.List;

public class GameListResponse extends Response {

    List<GameData> games;

    public GameListResponse(String message, List<GameData> games) {
        super(message);
        this.games = games;
    }
}
