package istemail.istemail.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import istemail.istemail.payload.response.ResponseDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    // Resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.error(ex.getMessage()));
    }

    // Resource already exists exception
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseDto> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseDto.error(ex.getMessage()));
    }

    // Resource conflict exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = extractConstraintViolationMessage(ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseDto.error(message));
    }

    // Resource bad request exception
    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<ResponseDto> handleResourceBadRequestException(ResourceBadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        @SuppressWarnings("null")
        String message = String.format("The parameter '%s' must be of type '%s'", ex.getName(), ex.getRequiredType().getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.error(message));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.error(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDto> handleAnyError(RuntimeException exception) {
        return ResponseEntity.badRequest().body(ResponseDto.error(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleGenericException(Exception ex) {
        // Check if the exception is AccessDeniedException
        if (ex instanceof AccessDeniedException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ResponseDto.error("Access denied for this service"));
        }
        // For all other exceptions, return 500 Internal Server Error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.error(ex.getMessage()));
    }


    private String extractConstraintViolationMessage(DataIntegrityViolationException ex) {
        String specificMessage = ex.getMostSpecificCause().getMessage();
        String patternString = "Key \\((.*?)\\)=\\((.*?)\\)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(specificMessage);

        if (matcher.find()) {
            String key = formatKey(matcher.group(1));
            String value = matcher.group(2);
            return String.format("The %s '%s' already exists. Please use a different value.", key, value);
        }

        return ex.getMessage();
    }

    private String formatKey(String key) {
        return key.replace("_", " ");
    }
}
