package services;

import enums.ApiResponse;
import enums.FollowingStatus;
import exceptions.ApiException;
import models.FollowingsModel;
import repositories.FollowingsRepository;

import java.util.List;

public class FollowerService {
    public List<Integer> getCurrentFollowerIds(int premiumId) {
        return FollowingsRepository.getCurrentFollowers(premiumId);
    }

    public List<Integer> getPendingFollowerIds(int premiumId) {
        return FollowingsRepository.getPendingFollowers(premiumId);
    }

    public List<Integer> getFollowedIds(int followerId) {
        return FollowingsRepository.getFollowedUsers(followerId);
    }

    public void unfollow(int premiumId, int followerId) {
        FollowingsModel fm = FollowingsRepository.getFollowing(premiumId, followerId);

        if (fm == null || fm.getStatus() == FollowingStatus.pending) {
            throw new ApiException(ApiResponse.BAD_REQUEST);
        }

        FollowingsRepository.deleteFollowing(premiumId, followerId);
    }

    public void follow(int premiumId, int followerId) {
        FollowingsModel fm = FollowingsRepository.getFollowing(premiumId, followerId);

        if (fm != null) {
            throw new ApiException(ApiResponse.BAD_REQUEST);
        }

        fm = new FollowingsModel();
        fm.setPremiumId(premiumId);
        fm.setFollowerId(followerId);
        fm.setStatus(FollowingStatus.pending);
        FollowingsRepository.createFollowing(fm);
    }

    public void accept(int premiumId, int followerId) {
        FollowingsModel fm = FollowingsRepository.getFollowing(premiumId, followerId);

        if (fm == null || fm.getStatus() == FollowingStatus.accepted) {
            throw new ApiException(ApiResponse.BAD_REQUEST);
        }

        fm.setStatus(FollowingStatus.accepted);
        FollowingsRepository.updateFollowing(fm);
    }

    public void reject(int premiumId, int followerId) {
        FollowingsModel fm = FollowingsRepository.getFollowing(premiumId, followerId);

        if (fm == null || fm.getStatus() == FollowingStatus.accepted) {
            throw new ApiException(ApiResponse.BAD_REQUEST);
        }

        FollowingsRepository.deleteFollowing(premiumId, followerId);
    }
}
