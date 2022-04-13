package elliot.restapi.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

public class ResponseUtils {

    @Getter
    @Setter
    @ToString
    @RequiredArgsConstructor
    public static class ApiResponse<T> {
        private final ResultCd resultCd;
        private final T data;
        private final String resultMessage;
        private final int status;
    }

    public enum ResultCd {S, BE}

    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(ResultCd.S, response, null, HttpStatus.OK.value());
    }

    public static ApiResponse<?> error(String resultMessage, HttpStatus status) {
        return new ApiResponse<>(ResultCd.BE, null, resultMessage, status.value());
    }
}
