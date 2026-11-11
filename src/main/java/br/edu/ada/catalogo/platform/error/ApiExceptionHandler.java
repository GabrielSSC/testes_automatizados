package br.edu.ada.catalogo.platform.error;

import br.edu.ada.catalogo.produtos.domain.EstoqueInsuficienteException;
import br.edu.ada.catalogo.produtos.domain.ProdutoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@RestControllerAdvice(basePackages = "br.edu.ada.catalogo.produtos.boundary")
public class ApiExceptionHandler {
    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> naoEncontrado(ProdutoNaoEncontradoException ex, HttpServletRequest req) {
        return erro(HttpStatus.NOT_FOUND, "PRODUTO_NAO_ENCONTRADO", ex.getMessage(), req, List.of());
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<ErroResponse> estoqueInsuficiente(EstoqueInsuficienteException ex, HttpServletRequest req) {
        return erro(HttpStatus.CONFLICT, "ESTOQUE_INSUFICIENTE", ex.getMessage(), req, List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> validacao(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<DetalheErro> detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> new DetalheErro(f.getField(), f.getDefaultMessage())).toList();
        return erro(HttpStatus.BAD_REQUEST, "VALIDACAO", "Dados de entrada inválidos.", req, detalhes);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> jsonMalformado(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return erro(HttpStatus.BAD_REQUEST, "JSON_INVALIDO", "JSON inválido ou incompleto.", req, List.of());
    }

    private ResponseEntity<ErroResponse> erro(HttpStatus status, String codigo, String mensagem, HttpServletRequest req,
            List<DetalheErro> detalhes) {
        return ResponseEntity.status(status).body(ErroResponse.of(codigo, mensagem, req.getRequestURI(), detalhes));
    }
}
