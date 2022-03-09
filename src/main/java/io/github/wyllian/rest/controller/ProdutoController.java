package io.github.wyllian.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.wyllian.domain.entity.Produto;
import io.github.wyllian.domain.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody @Valid Produto produto) {
        return this.produtoRepository.save(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Produto produto) {
        this.produtoRepository.findById(id).map( p -> {
            produto.setId(p.getId());
            this.produtoRepository.save(produto);
            return produto;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        this.produtoRepository.findById(id).map( p -> {       
            this.produtoRepository.delete(p);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto getProdutoById(@PathVariable Integer id) {
            return this.produtoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping("/buscar")
    @ResponseStatus()
    public List<Produto> filtro(@RequestBody Produto filtro) {
        ExampleMatcher matche = ExampleMatcher.matching()
                                                .withIgnoreCase()
                                                .withStringMatcher(StringMatcher.CONTAINING);
        Example<Produto> exp = Example.of(filtro, matche);
        return this.produtoRepository.findAll(exp);
    }
}
