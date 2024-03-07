package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.GameResponse;
import service.GameService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameDataAccessTests {
    GameDAO gameDAO = new SQLGameDAO();

    @BeforeEach
    void setUp() throws DataAccessException {
        gameDAO.clearData();
    }

    @Test
    void addAndGetGameSuccess() throws DataAccessException {
        String gameName = "CoolNewGame";
        int id = gameDAO.addNewGame(gameName);
        assertEquals(1, id);

        GameData confirm = gameDAO.getGameData(id);
        assertEquals(null, confirm.whiteUsername());
        assertEquals(null, confirm.blackUsername());
        assertEquals(gameName, confirm.gameName());
        assertNotNull(confirm.chessGame());
    }

    @Test
    void getGameFail() throws DataAccessException {
        GameData confirm = gameDAO.getGameData(1);
        assertNull(confirm);
    }

    @Test
    void addMultipleGamesAndGetListSuccess() throws DataAccessException {
        String[] gameNames = {"CoolNewGame", "I'm bad at chess", "yoooooooo", "just you wait Joshua",
                "LongHaveITrainedForThisDay", "TrembleBeforeMyPawn", "theNightShallConsumeAll"};

        for (String name : gameNames) {
            gameDAO.addNewGame(name);
        }

        List<GameData> games = gameDAO.getGameList();
        assertEquals(7, games.size());
    }

    @Test
    void getEmptyGameList() throws DataAccessException {
        List<GameData> games = gameDAO.getGameList();
        assertEquals(0, games.size());
    }

    @Test
    void addChessPlayersSuccess() throws DataAccessException {
        String player1 = "Survivor";
        String player2 = "Spearmaster";
        int id = gameDAO.addNewGame("RainWorld");
        gameDAO.addParticipant(id, player1, ChessGame.TeamColor.WHITE);
        gameDAO.addParticipant(id, player2, ChessGame.TeamColor.BLACK);

        GameData updatedGame = gameDAO.getGameData(id);
        assertEquals(player1, updatedGame.whiteUsername());
        assertEquals(player2, updatedGame.blackUsername());
    }

    @Test
    void addChessPlayerFail() throws DataAccessException {
        String player1 = "Hunter";
        String player2 = "Artificer";
        int id = gameDAO.addNewGame("DownPour");
        gameDAO.addParticipant(id, player1, ChessGame.TeamColor.WHITE);
        gameDAO.addParticipant(id, player2, ChessGame.TeamColor.BLACK);

        AuthDAO authDAO = new SQLAuthDAO();
        AuthData user = new AuthData("Saint", "token");
        authDAO.addAuth(user);
        GameService service = new GameService(authDAO, gameDAO);
        service.joinGame(user, id, "WHITE");

        GameData updatedGame = gameDAO.getGameData(id);
        assertNotEquals(user.username(), updatedGame.whiteUsername());
        assertEquals(player1, updatedGame.whiteUsername());
    }
}
