package models;

import enums.FollowingStatus;
import lombok.Data;

@Data
public class FollowingsModel {
    private int premiumId;
    private int followerId;
    private FollowingStatus status;
}
