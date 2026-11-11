package br.edu.ada.catalogo.produtos.domain;

import br.edu.ada.catalogo.produtos.repository.ProdutoEntity;
import br.edu.ada.catalogo.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repository;

    @Transactional(readOnly = true)
    public List<Produto> listar(String nome) {
        List<ProdutoEntity> encontrados = nome == null || nome.isBlank() ? repository.findAll(Sort.by("id"))
                : repository.findByNomeContainingIgnoreCaseOrderByIdAsc(nome.trim());
        return encontrados.stream().map(ProdutoService::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Produto buscar(Long id) {
        return toDomain(obter(id));
    }

    @Transactional
    public Produto criar(String nome, BigDecimal preco, int quantidade) {
        return toDomain(repository.save(new ProdutoEntity(nome.trim(), preco, quantidade)));
    }

    @Transactional
    public Produto atualizar(Long id, String nome, BigDecimal preco, int quantidade) {
        ProdutoEntity entity = obter(id);
        entity.setNome(nome.trim());
        entity.setPreco(preco);
        entity.setQuantidade(quantidade);
        return toDomain(entity); // Hibernate dirty checking no commit
    }

    @Transactional
    public void excluir(Long id) {
        ProdutoEntity entity = obter(id);
        repository.delete(entity);
    }

    @Transactional
    public Produto retirarEstoque(Long id, int quantidade) {
        ProdutoEntity entity = obter(id);
        if (entity.getQuantidade() < quantidade) {
            throw new EstoqueInsuficienteException(entity.getQuantidade(), quantidade);
        }
        entity.setQuantidade(entity.getQuantidade() - quantidade);
        return toDomain(entity);
    }

    private ProdutoEntity obter(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    private static Produto toDomain(ProdutoEntity e) {
        return new Produto(e.getId(), e.getNome(), e.getPreco(), e.getQuantidade());
    }
}
