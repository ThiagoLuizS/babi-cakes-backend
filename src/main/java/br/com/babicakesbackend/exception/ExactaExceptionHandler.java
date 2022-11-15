package br.com.babicakesbackend.exception;

import br.com.babicakesbackend.models.dto.ErrorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExactaExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorView handleNotFoundException(
            NotFoundException notFoundException,
            HttpServletRequest request) {
        return ErrorView.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getServletPath())
                .error(HttpStatus.NOT_FOUND.name())
                .message(Arrays.asList(notFoundException.getMessage())).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorView error = criarListaDeErros(ex.getBindingResult());
        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    private ErrorView criarListaDeErros(BindingResult bindingResult){
        return ErrorView.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .path(bindingResult.getNestedPath())
                .message(bindingResult.getFieldErrors().stream().map(error -> messageSource.getMessage(error, LocaleContextHolder.getLocale())).collect(Collectors.toList()))
                .error(HttpStatus.BAD_REQUEST.name()).build();
    }

    public static ErrorView handleErrorBadCrendentialUnauthorizadTokenException(){
        return ErrorView.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .path("/api/auth")
                .message(Arrays.asList("Não foi possivel autenticar a requisição, verifique se o token está correto!"))
                .error(HttpStatus.UNAUTHORIZED.name()).build();
    }

}
