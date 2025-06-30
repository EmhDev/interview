package com.bci.interview.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice // Indica que esta clase provee manejo global de excepciones a traves de la aplicacion
public class GlobalExceptionHandler {


@ExceptionHandler(UserAlreadyExistsException.class)
@ResponseStatus(HttpStatus.CONFLICT) // Define el codigo de estado HTTP para esta excepción
public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
     return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.CONFLICT);
}

     /**
      * Maneja la excepción {@link InvalidEmailFormatException}.
      * Retorna un código de estado HTTP 400 Bad Request y un mensaje de error estandarizado.
      * @param ex La excepción {@link InvalidEmailFormatException} capturada.
      * @return Una {@link ResponseEntity} que contiene el {@link ErrorResponse} y el {@link HttpStatus}.
      */
     @ExceptionHandler(InvalidEmailFormatException.class)
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     public ResponseEntity<ErrorResponse> handleInvalidEmailFormatException(InvalidEmailFormatException ex) {
          return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
     }

     /**
      * Maneja la excepción {@link InvalidPasswordFormatException}.
      * Retorna un código de estado HTTP 400 Bad Request y un mensaje de error estandarizado.
      * @param ex La excepción {@link InvalidPasswordFormatException} capturada.
      * @return Una {@link ResponseEntity} que contiene el {@link ErrorResponse} y el {@link HttpStatus}.
      */
     @ExceptionHandler(InvalidPasswordFormatException.class)
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     public ResponseEntity<ErrorResponse> handleInvalidPasswordFormatException(InvalidPasswordFormatException ex) {
          return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
     }

     /**
      * Maneja las excepciones de validacion de argumentos (@Valid) generadas por Spring.
      * Cuando los DTO de solicitud no cumplen las restricciones de validacion, esta excepcion se lanza.
      * Retorna un código de estado HTTP 400 Bad Request con un mensaje que detalla los errores de validacion.
      * @param ex La excepcion {@link MethodArgumentNotValidException} capturada.
      * @return Una {@link ResponseEntity} que contiene el {@link ErrorResponse} y el {@link HttpStatus}.
      */
     @ExceptionHandler(MethodArgumentNotValidException.class)
     @ResponseStatus(HttpStatus.BAD_REQUEST)
     public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
          // Recoge todos los errores de campo y los une en un solo mensaje
          String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                  .map(error -> error.getField() + ": " + error.getDefaultMessage())
                  .collect(Collectors.joining(", "));
          return new ResponseEntity<>(new ErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
     }

     /**
      * Manejador generico para cualquier otra excepcion no capturada por manejadores mas específicos
      * Retorna un código de estado HTTP 500 Internal Server Error
      * En un entorno de producción, es recomendable loggear la excepción completa para depuracion
      * y retornar un mensaje mas generico al cliente por razones de seguridad (evitar exponer detalles internos)
      * @param ex La {@link Exception} genérica capturada
      * @return Una {@link ResponseEntity} que contiene el {@link ErrorResponse} y el {@link HttpStatus}.
      */
     @ExceptionHandler(Exception.class)
     @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
     public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception ex) {
          // Log de la excepcion para depuracion (en un entorno real se usaría un logger)
          // System.err.println("Ocurrio un error inesperado: " + ex.getMessage());
          // ex.printStackTrace();
          return new ResponseEntity<>(new ErrorResponse("Ocurrió un error inesperado: " +
                                        ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
     }


}
