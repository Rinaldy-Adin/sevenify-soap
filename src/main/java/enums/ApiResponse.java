package enums;

public enum ApiResponse {
    UP("Server Up"),
    FOLLOW_SUCCESS("Follow action successful"),
    UNFOLLOW_SUCCESS("Unfollow action successful"),
    ACCEPT_SUCCESS("Accept action successful"),
    REJECT_SUCCESS("Reject action successful"),
    ERROR("An error occurred"),
    BAD_REQUEST("Bad request"),
    UNAUTHORIZED("Unauthorized"),
    NOT_FOUND("Resource not found"),
    FORBIDDEN("Forbidden");

    private final String message;

    ApiResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
