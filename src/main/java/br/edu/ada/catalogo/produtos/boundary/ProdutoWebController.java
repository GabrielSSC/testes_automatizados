package br.edu.ada.catalogo.produtos.boundary;

import br.edu.ada.catalogo.produtos.domain.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProdutoWebController {
    private final ProdutoService service;

    @GetMapping("/")
    public String inicio() {
        return "redirect:/produtos";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/produtos")
    public String listar(@RequestParam(required = false) String nome, Model model, Authentication authentication) {
        model.addAttribute("novoProduto", new ProdutoForm());
        preencher(model, nome, authentication);
        return "produtos";
    }

    @PostMapping("/produtos")
    public String criar(@Valid @ModelAttribute("novoProduto") ProdutoForm produto, BindingResult erros, Model model,
            Authentication authentication) {
        if (erros.hasErrors()) {
            preencher(model, null, authentication);
            return "produtos";
        }
        service.criar(produto.getNome(), produto.getPreco(), produto.getQuantidade());
        return "redirect:/produtos";
    }

    private void preencher(Model model, String nome, Authentication authentication) {
        model.addAttribute("produtos", service.listar(nome));
        model.addAttribute("filtro", nome == null ? "" : nome);
        boolean logado = authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("logado", logado);
        model.addAttribute("usuario", logado ? authentication.getName() : "");
        model.addAttribute("admin", logado
                && authentication.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority())));
    }
}
