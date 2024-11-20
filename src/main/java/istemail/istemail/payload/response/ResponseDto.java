package istemail.istemail.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private Boolean status;
    private Object data;
    private String message;

    public ResponseDto(Boolean status, String message, Object data) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public static ResponseDto success(String message, Object data) {
        return new ResponseDto(true, message, data);
    }

    public static ResponseDto success(String message) {
        return new ResponseDto(true, message, Optional.ofNullable(null));
    }

    public static ResponseDto error(String message, Object data) {
        return new ResponseDto(false, message, data);
    }

    public static ResponseDto error(String message) {
        return new ResponseDto(false, message, Optional.ofNullable(null));
    }

}