package service;

import dataAccess.*;
import model.AuthData;
import model.GameData;
import chess.ChessGame.TeamColor;

import java.util.List;

public class GameService extends Service {

    AuthDAO authDAO;
    GameDAO gameDAO;

    public GameService(AuthDAO auth, GameDAO game) {
        super();
        this.authDAO = auth;
        this.gameDAO = game;
    }

    public GameResponse newGame(GameRequest request, AuthData auth) throws DataAccessException {
        resetErrorCode();
        if (auth == null || !authDAO.validateAuth(auth)) {
            errorCode = 401;
            return new GameResponse("Error: unauthorized", null, null);
        }
        else if (authDAO.validateAuth(auth)) {
            if (request.gameName() != null) {
                int gameID = gameDAO.addNewGame(request.gameName());
                return new GameResponse(null, gameID, null);
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

    public GameListResponse listGames(AuthData auth) throws DataAccessException {
        resetErrorCode();
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

    public GameResponse joinGame(AuthData auth, Integer gameID, String color) throws DataAccessException {
        resetErrorCode();
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
            if (c != null) {
                gameDAO.addParticipant(gameID, auth.username(), c);
            }
            //success
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
