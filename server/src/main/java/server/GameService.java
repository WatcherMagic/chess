package server;

import dataAccess.*;
import dataAccess.objects.AuthToken;
import dataAccess.objects.GameData;
import chess.ChessGame.TeamColor;
import server.resreq.GameListResponse;
import server.resreq.GameRequest;
import server.resreq.GameResponse;

import java.util.List;

public class GameService {

    AuthDAO authDAO;
    GameDAO gameDAO;
    int errorCode = 0;

    public GameService(AuthDAO auth, GameDAO game) {
        this.authDAO = auth;
        this.gameDAO = game;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public GameResponse newGame(GameRequest request, AuthToken auth) {
        errorCode = 0;
        if (auth == null || !authDAO.validateAuth(auth)) {
            errorCode = 401;
            return new GameResponse("Error: unauthorized", null, null);
        }
        else if (authDAO.validateAuth(auth)) {
            if (request.gameName() != null) {
                int newGameID = gameDAO.createGame(request.gameName());
                return new GameResponse(null, newGameID, null);
            }
            else {
                //bad request
                errorCode = 400;
                return new GameResponse("Error: bad request",null, null);
            }
        }
        errorCode = 500;
        return new GameResponse("Error: description", null, null);
    }

    public GameListResponse listGames(AuthToken auth) {
        errorCode = 0;
        if (auth == null || !authDAO.validateAuth(auth)) {
            errorCode = 401;
            return new GameListResponse("Error: unauthorized", null);
        }
        else if (authDAO.validateAuth(auth)) {
            List<GameData> games = gameDAO.getGameList();
            return new GameListResponse(null, games);
        }
        errorCode = 500;
        return new GameListResponse("Error: description", null);
    }

    public GameResponse joinGame(AuthToken auth, Integer gameID, String color) {
        errorCode = 0;
        GameData g = gameDAO.getGameData(gameID);
        if (g == null) {
            errorCode = 400;
            return new GameResponse("Error: bad request", null, null);
        }
        if (authDAO.validateAuth(auth)) {
            TeamColor c = null;
            if (color != null) {
                if (color.equalsIgnoreCase("WHITE")) {
                    c = TeamColor.WHITE;
                }
                else if (color.equalsIgnoreCase("BLACK")) {
                    c = TeamColor.BLACK;
                }
            }

            if ((c == TeamColor.WHITE && g.whiteUsername() != null && !g.whiteUsername().isEmpty())
                    || (c == TeamColor.BLACK && g.blackUsername() != null && !g.blackUsername().isEmpty())) {
                //spot taken
                errorCode = 403;
                return new GameResponse("Error: already taken", null, null);
            }
            //success
            gameDAO.addParticipant(gameID, auth.username(), c);
            return new GameResponse(null, null, null);
        }
        else if (!authDAO.validateAuth(auth)) {
            errorCode = 401;
            return new GameResponse("Error: unauthorized", null, null);
        }
        errorCode = 500;
        return new GameResponse("Error: description", null, null);
    }
}
