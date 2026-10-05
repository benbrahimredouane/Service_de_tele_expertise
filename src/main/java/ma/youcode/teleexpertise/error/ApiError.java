package ma.youcode.teleexpertise.error;

public record ApiError(int status, String error, String message) {
}