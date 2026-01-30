package kb.moto.exceptions.handler

import kb.moto.exceptions.PartNotFound
import kb.moto.exceptions.SameUserName
import kb.moto.exceptions.UserNotFound
import kb.moto.exceptions.WrongPassword
import kb.moto.model.exception.MotoExceptionResponse
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono

@Suppress("SameParameterValue")
@RestControllerAdvice
class GlobalExceptionHandler {

    private fun exceptionResponse(httpStatus: HttpStatus, message: String?) = Mono
        .just(ResponseEntity.status(httpStatus).body(MotoExceptionResponse(httpStatus.value(), message)))

    @ExceptionHandler(PartNotFound::class)
    fun handleException(e: PartNotFound): Mono<ResponseEntity<MotoExceptionResponse>> {
        return exceptionResponse(NOT_FOUND, e.message)
    }

    @ExceptionHandler(UserNotFound::class)
    fun handleException(e: UserNotFound): Mono<ResponseEntity<MotoExceptionResponse>> {
        return exceptionResponse(NOT_FOUND, e.message)
    }

    @ExceptionHandler(SameUserName::class)
    fun handleException(e: SameUserName): Mono<ResponseEntity<MotoExceptionResponse>> {
        return exceptionResponse(NOT_FOUND, e.message)
    }

    @ExceptionHandler(DuplicateKeyException::class)
    fun handleException(e: DuplicateKeyException): Mono<ResponseEntity<MotoExceptionResponse>> {
        return exceptionResponse(CONFLICT, e.message)
    }
    @ExceptionHandler(WrongPassword::class)
    fun handleException(e: WrongPassword): Mono<ResponseEntity<MotoExceptionResponse>> {
        return exceptionResponse(PRECONDITION_FAILED, e.message)
    }
}