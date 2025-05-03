package com.copilot.assignment.exception.handler;

import com.copilot.assignment.exception.ResourceNotFoundException;
import com.copilot.assignment.exception.TypeNotFoundException;
import com.copilot.assignment.exception.ValidationException;
import com.copilot.assignment.utils.ResultUtil;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.copilot.assignment.constants.ResultInfoConstants.BAD_REQUEST;
import static com.copilot.assignment.constants.ResultInfoConstants.NOT_FOUND;
import static com.copilot.assignment.constants.ResultInfoConstants.REQ_FIELD_VALIDATION_ERROR;
import static com.copilot.assignment.constants.ResultInfoConstants.SYSTEM_ERROR;


@ControllerAdvice
@Log4j2
@SuppressWarnings("unused")
public class GlobalExceptionHandler {

    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentTypeMismatchException.class,
            BindException.class
    })
    @ResponseBody
    protected ResponseEntity<Object> handleIllegalArgumentException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResultUtil.buildResult(BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseBody
    protected ResponseEntity<Object> handleConflict(Throwable ex) {
        log.error("GlobalExceptionHandling:{}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResultUtil.buildResult(SYSTEM_ERROR, null));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        log.info("Caught resource not found exception : {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResultUtil.buildResult(NOT_FOUND, null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<Object> onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        val resultInfo = REQ_FIELD_VALIDATION_ERROR;
        val message = new StringBuilder();
        for (val fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!message.isEmpty()) {
                message.append(", ");
            }
            message.append(fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage()
                    : fieldError.getField() + " is required");
        }
        resultInfo.setMessage(message.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResultUtil.buildResult(resultInfo, null));
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<Object> validationException(ValidationException ex) {
        log.info("ValidationException :{}", ex.getResultInfo());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResultUtil.buildResult(ex.getResultInfo(), null));
    }

    @ExceptionHandler(value = {ValidationCompletionException.class})
    @ResponseBody
    protected ResponseEntity<Object> handleConflict(ValidationCompletionException ex) {
        log.error("GlobalExceptionHandler to handle CompletionException:{}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResultUtil.buildResult(ex.getResultInfo(), null));
    }

    @ExceptionHandler(TypeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<Object> typeNotFoundException(TypeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ResultUtil.buildResult(ex.getResultInfo(), null));
    }

}