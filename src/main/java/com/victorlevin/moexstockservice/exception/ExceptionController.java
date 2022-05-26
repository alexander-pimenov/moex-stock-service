package com.victorlevin.moexstockservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Для того чтобы не просто выкидывать сообщения об ошибках и остнавливать приложение,
 * эти выброшенные ошибки мы должны отлавливать, оборачивать в некую ErrorDto и потом
 * передавать пользователю.
 * Отлавливаем ошибки с помощью ExceptionHandler, далее в ResponseEntity выводим ошибку и
 * соответствующий статус.
 */
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BondNotFoundException.class})
    public ResponseEntity<ErrorDto> handleNotFound(Exception ex) {
        return new ResponseEntity<ErrorDto>(new ErrorDto(ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BondParsingException.class})
    public ResponseEntity<ErrorDto> handleExceptionFromPriceService(Exception ex) {
        return new ResponseEntity<ErrorDto>(new ErrorDto(ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({LimitRequestsException.class})
    public ResponseEntity<ErrorDto> handleExceptionFromMoex(Exception ex) {
        return new ResponseEntity<ErrorDto>(new ErrorDto(ex.getLocalizedMessage()), HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler({Exception.class, ThreadInterruptedException.class})
    protected ResponseEntity<Object> handleGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error. Exception message: " + ex.getMessage());
    }
}
