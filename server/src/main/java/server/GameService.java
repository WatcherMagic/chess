package server;

import dataAccess.*;
import dataAccess.objects.AuthToken;
import dataAccess.objects.GameData;
import dataAccess.objects.User;

import java.util.List;

public class GameService {

        AuthDAO authDAO;
        GameDAO gameDAO;

        public GameService(AuthDAO auth, GameDAO game) {
            this.authDAO = auth;
            this.gameDAO = game;
        }

        public void newGame(AuthToken auth, String gameName) {
            if (authDAO.getAuth(auth.username()) == auth) {
                //
            }
        }

        public void listGames(AuthToken auth) {
            if (authDAO.getAuth(auth.username()) == auth) {
                List<GameData> games = gameDAO.getGameList();
                //return
            }
            //return
        }

}
