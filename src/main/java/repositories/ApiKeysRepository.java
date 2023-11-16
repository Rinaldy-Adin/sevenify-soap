package repositories;

import db.DB;
import enums.ApiResponse;
import exceptions.ApiException;
import models.ApiKeysModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApiKeysRepository {
    private static final Connection connection = DB.getConnection();

    public static void createApiKey(@NotNull ApiKeysModel apiKey) {
        String sql = "INSERT INTO api_keys (`key`, client) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, apiKey.getKey());
            preparedStatement.setString(2, apiKey.getClient());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }
    }

    @Nullable
    public static ApiKeysModel getApiKeyByKey(String key) {
        String sql = "SELECT * FROM api_keys WHERE `key` = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, key);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ApiKeysModel apiKey = new ApiKeysModel();
                    apiKey.setKey(resultSet.getString("key"));
                    apiKey.setClient(resultSet.getString("client"));

                    return apiKey;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @NotNull
    public static List<ApiKeysModel> getAllApiKeys() {
        String sql = "SELECT * FROM api_keys";
        List<ApiKeysModel> apiKeysList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                ApiKeysModel apiKey = new ApiKeysModel();
                apiKey.setKey(resultSet.getString("key"));
                apiKey.setClient(resultSet.getString("client"));

                apiKeysList.add(apiKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return apiKeysList;
    }

    public static void updateApiKey(ApiKeysModel apiKey) {
        String sql = "UPDATE api_keys SET client = ? WHERE `key` = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, apiKey.getClient());
            preparedStatement.setString(2, apiKey.getKey());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }
    }

    public static void deleteApiKey(String key) {
        String sql = "DELETE FROM api_keys WHERE `key` = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, key);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }
    }
}
