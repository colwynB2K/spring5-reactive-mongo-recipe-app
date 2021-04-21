package guru.springframework.spring5recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    public static final String VIEWS_400 = "400";

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNumberFormatException(Exception exception, Model model) {
        log.warn("Handling NumberFormatException");
        log.warn(exception.getMessage());

        model.addAttribute("exception", exception);

        return VIEWS_400;
    }
}
