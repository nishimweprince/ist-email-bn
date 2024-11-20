package istemail.istemail.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String resourceName;
    private String fieldName;
    private Object fieldValue;


    public ResourceNotFoundException(String message) {
        super(message);
    }
    
}
