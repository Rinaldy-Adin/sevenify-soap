package repositories;

import db.DB;
import enums.ApiResponse;
import exceptions.ApiException;
import models.LoggingModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoggingRepository {
    private static final Connection connection = DB.getConnection();

    public static void createLog(LoggingModel log) {
        String sql = "INSERT INTO logging (description, ip, endpoint, timestamp) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, log.getDescription());
            preparedStatement.setString(2, log.getIp());
            preparedStatement.setString(3, log.getEndpoint());
            preparedStatement.setTimestamp(4, new Timestamp(log.getTimestamp().getTime()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }
    }

    public static LoggingModel getLogById(int id) {
        String sql = "SELECT * FROM logging WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    LoggingModel log = new LoggingModel();
                    log.setId(resultSet.getInt("id"));
                    log.setDescription(resultSet.getString("description"));
                    log.setIp(resultSet.getString("ip"));
                    log.setEndpoint(resultSet.getString("endpoint"));
                    log.setTimestamp(resultSet.getTimestamp("timestamp"));

                    return log;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<LoggingModel> getAllLogs() {
        String sql = "SELECT * FROM logging";
        List<LoggingModel> logsList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                LoggingModel log = new LoggingModel();
                log.setId(resultSet.getInt("id"));
                log.setDescription(resultSet.getString("description"));
                log.setIp(resultSet.getString("ip"));
                log.setEndpoint(resultSet.getString("endpoint"));
                log.setTimestamp(resultSet.getTimestamp("timestamp"));

                logsList.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logsList;
    }

    public static void updateLog(LoggingModel log) {
        String sql = "UPDATE logging SET description = ?, ip = ?, endpoint = ?, timestamp = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, log.getDescription());
            preparedStatement.setString(2, log.getIp());
            preparedStatement.setString(3, log.getEndpoint());
            preparedStatement.setTimestamp(4, new Timestamp(log.getTimestamp().getTime()));
            preparedStatement.setInt(5, log.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }
    }

    public static void deleteLog(int id) {
        String sql = "DELETE FROM logging WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }
    }
}
