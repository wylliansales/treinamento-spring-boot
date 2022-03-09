package io.github.wyllian.domain.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.wyllian.domain.entity.Cliente;
import io.github.wyllian.domain.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{    
    Set<Pedido> findByCliente(Cliente cliente);

    @Query(" select p from Pedido p left join fetch p.itens where p.id = :id")
    Optional<Pedido> findByIdFetchItens(Integer id);
}
