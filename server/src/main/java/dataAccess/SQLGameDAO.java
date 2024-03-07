package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SQLGameDAO implements GameDAO {

    @Override
    public int addNewGame(String gameName) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO game_data (game_name, game) VALUES (?, ?)", RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, gameName);
                Gson serializer = new Gson();
                String gameJSON = serializer.toJson(new ChessGame());
                preparedStatement.setString(2, gameJSON);
                preparedStatement.executeUpdate();

                var resultSet = preparedStatement.getGeneratedKeys();
                var ID = 0;
                if (resultSet.next()) {
                    ID = resultSet.getInt(1);
                }
                return ID;
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public GameData getGameData(int gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM game_data WHERE id=?")) {
                Integer id = null;
                String whiteUsername = null;
                String blackUsername = null;
                String gameName = null;
                ChessGame game = null;

                preparedStatement.setString(1, String.valueOf(gameID));
                var rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    id = rs.getInt("id");
                    whiteUsername = rs.getString("white_username");
                    blackUsername = rs.getString("black_username");
                    gameName = rs.getString("game_name");

                    Gson serializer = new Gson();
                    game = serializer.fromJson(rs.getString("game"), ChessGame.class);

                    return new GameData(id, whiteUsername, blackUsername, gameName, game);
                }
                return null;
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public List<GameData> getGameList() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM game_data")) {
                var rs = preparedStatement.executeQuery();
                Gson serializer = new Gson();
                List<GameData> gameList = new ArrayList<>();

                while (rs.next()) {
                    var id = rs.getInt("id");
                    var whiteUsername = rs.getString("white_username");
                    var blackUsername = rs.getString("black_username");
                    var gameName = rs.getString("game_name");
                    ChessGame game = serializer.fromJson(rs.getString("game"), ChessGame.class);

                    gameList.add(new GameData(id, whiteUsername, blackUsername, gameName, game));
                }

                return gameList;
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public void addParticipant(int gameID, String username, ChessGame.TeamColor color) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String sql = "UPDATE game_data SET ";
            if (color == ChessGame.TeamColor.WHITE) {
                sql = sql + "white_username";
            }
            else if (color == ChessGame.TeamColor.BLACK) {
                sql = sql + "black_username";
            }
            sql = sql + "=? WHERE id=?";

            try (var preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, String.valueOf(gameID));

                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    @Override
    public boolean clearData() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE game_data")) {
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
}
