package io.github.wyllian.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.wyllian.domain.entity.Produto;

/**
 * ProdutoRepository
 */
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    
}