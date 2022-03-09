package io.github.wyllian.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.wyllian.domain.entity.ItemPedido;


public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer>{
    
}
