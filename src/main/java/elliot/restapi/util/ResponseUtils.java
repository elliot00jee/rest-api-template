package elliot.restapi.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    @Getter
    @Setter
    @ToString
    @RequiredArgsConstructor
    public static class ApiResponse<T> {
        private final boolean success;
        private final T data;
        private final String message;
    }

    /**
     * 컨트롤러 단에서 정상 응답 보낼 때 호출
     */
    public static ResponseEntity<?> success() {
        return success(null);
    }

    public static ResponseEntity<?> success(String key, String value) {
        Map<String, String> response = new HashMap<>();
        response.put(key, value);

        return success(response);
    }

    public static <T> ResponseEntity<?> success(T response) {
        return success(response, null);
    }

    public static <T> ResponseEntity<?> success(T response, String message) {
        return new ResponseEntity<>(
                new ApiResponse<>(true, response, message),
                HttpStatus.OK);
    }

    /**
     * 컨트롤러 단에서 에러 응답 보낼 때 호출
     * 직접 호출하지 않고, 예외 발생 시 @ExceptionHandler에서 처리
     */
    public static ResponseEntity<?> error(String message, HttpStatus errorStatus) {
        return new ResponseEntity<>(
                new ApiResponse<>(false, null, message),
                errorStatus);
    }

}
