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

    /**
     * 컨트롤러 단에서 정상 응답 보낼 때 호출
     * @param response data 필드에 넣어 반환할 객체
     */
    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(ResultCd.S, response, null, HttpStatus.OK.value());
    }
    /**
     * 컨트롤러 단에서 에러 응답 보낼 때 호출
     * 직접 호출하지 않고, 예외 발생 시 @ExceptionHandler에서 처리
     */
    public static ApiResponse<?> error(String resultMessage, HttpStatus status) {
        return new ApiResponse<>(ResultCd.BE, null, resultMessage, status.value());
    }

}
