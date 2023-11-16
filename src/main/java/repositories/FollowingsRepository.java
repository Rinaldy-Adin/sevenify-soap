package repositories;

import db.DB;
import enums.ApiResponse;
import enums.FollowingStatus;
import exceptions.ApiException;
import models.FollowingsModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class FollowingsRepository {
    private static final Connection connection = DB.getConnection();

    public static void createFollowing(@NotNull FollowingsModel following) {
        String sql = "INSERT INTO followings (premium_id, follower_id, status) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, following.getPremiumId());
            preparedStatement.setInt(2, following.getFollowerId());
            preparedStatement.setString(3, following.getStatus().name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }
    }

    @Nullable
    public static FollowingsModel getFollowing(int premiumId, int followerId) {
        String sql = "SELECT * FROM followings WHERE premium_id = ? AND follower_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, premiumId);
            preparedStatement.setInt(2, followerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    FollowingsModel following = new FollowingsModel();
                    following.setPremiumId(resultSet.getInt("premium_id"));
                    following.setFollowerId(resultSet.getInt("follower_id"));
                    following.setStatus(FollowingStatus.valueOf(resultSet.getString("status")));

                    return following;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }
        return null;
    }

    @NotNull
    public static List<Integer> getCurrentFollowers(int premiumId) {
        String sql = "SELECT * FROM followings WHERE premium_id = ? AND status = 'accepted'";
        List<Integer> results = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, premiumId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    FollowingsModel following = new FollowingsModel();
                    following.setPremiumId(resultSet.getInt("premium_id"));
                    following.setFollowerId(resultSet.getInt("follower_id"));
                    following.setStatus(FollowingStatus.valueOf(resultSet.getString("status")));

                    results.add(following.getFollowerId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }

        return results;
    }

    @NotNull
    public static List<Integer> getPendingFollowers(int premiumId) {
        String sql = "SELECT * FROM followings WHERE premium_id = ? AND status = 'pending'";
        List<Integer> results = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, premiumId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    FollowingsModel following = new FollowingsModel();
                    following.setPremiumId(resultSet.getInt("premium_id"));
                    following.setFollowerId(resultSet.getInt("follower_id"));
                    following.setStatus(FollowingStatus.valueOf(resultSet.getString("status")));

                    results.add(following.getFollowerId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }

        return results;
    }

    @NotNull
    public static List<Integer> getFollowedUsers(int followerId) {
        String sql = "SELECT * FROM followings WHERE follower_id = ? AND status = 'accepted'";
        List<Integer> results = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, followerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    FollowingsModel following = new FollowingsModel();
                    following.setPremiumId(resultSet.getInt("premium_id"));
                    following.setFollowerId(resultSet.getInt("follower_id"));
                    following.setStatus(FollowingStatus.valueOf(resultSet.getString("status")));

                    results.add(following.getPremiumId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }

        return results;
    }

    @NotNull
    public static List<FollowingsModel> getAllFollowings() {
        String sql = "SELECT * FROM followings";
        List<FollowingsModel> followingsList = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                FollowingsModel following = new FollowingsModel();
                following.setPremiumId(resultSet.getInt("premium_id"));
                following.setFollowerId(resultSet.getInt("follower_id"));
                following.setStatus(FollowingStatus.valueOf(resultSet.getString("status")));

                followingsList.add(following);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }

        return followingsList;
    }

    public static void updateFollowing(FollowingsModel following) {
        String sql = "UPDATE followings SET status = ? WHERE premium_id = ? AND follower_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, following.getStatus().name());
            preparedStatement.setInt(2, following.getPremiumId());
            preparedStatement.setInt(3, following.getFollowerId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFollowing(int premiumId, int followerId) {
        String sql = "DELETE FROM followings WHERE premium_id = ? AND follower_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, premiumId);
            preparedStatement.setInt(2, followerId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApiException(ApiResponse.ERROR);
        }
    }
}
