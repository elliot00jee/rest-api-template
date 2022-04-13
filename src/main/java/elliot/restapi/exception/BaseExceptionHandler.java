package elliot.restapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

import static elliot.restapi.util.ResponseUtils.ApiResponse;
import static elliot.restapi.util.ResponseUtils.error;

@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValid(Exception e) {
        String message = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return getResponse(message, HttpStatus.OK);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(Exception e) {
        return getResponse(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<?> handleUnauthenticatedException(Exception e) {
        return getResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            ConstraintViolationException.class,
            GeneralBusinessException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        return getResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Internal server error occurred: {}", e.getMessage(), e);
        return getResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<?> getResponse(String message, HttpStatus status) {
        return new ResponseEntity<ApiResponse<?>>(error(message, status), status);
    }
}
