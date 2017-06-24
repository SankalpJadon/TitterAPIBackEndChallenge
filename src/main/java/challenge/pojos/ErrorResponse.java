package challenge.pojos;

public class ErrorResponse extends Response {
    public static final String UNAUTHORIZED = "You are not authorized to make the request. " +
            "Make sure your API token is correct.";
    public static final String BAD_REQ_SAME_IDS = "The ids provided cannot be the same.";
    public static final String USERS_NOT_VALID = "One of both users are not valid";


    public ErrorResponse(int errorCode, String message) {
        super(errorCode, message);
    }
}
