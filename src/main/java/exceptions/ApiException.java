package exceptions;

import enums.ApiResponse;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class ApiException extends RuntimeException {
    private final ApiResponse apiResponse;

    public ApiException(@NotNull ApiResponse apiResponse) {
        super(apiResponse.getMessage());
        this.apiResponse = apiResponse;
    }

}
