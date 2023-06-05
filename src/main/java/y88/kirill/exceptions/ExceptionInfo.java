package y88.kirill.exceptions;

import lombok.*;


@AllArgsConstructor
@Setter
@Getter
public class ExceptionInfo extends RuntimeException{
    private String msg;
    private String stackTraceException;

    @Override
    public String toString() {
        return "ExceptionInfo{" +
                "msg='" + msg + '\'' +
                ", stackTraceException='" + stackTraceException + '\'' +
                '}';
    }
}
