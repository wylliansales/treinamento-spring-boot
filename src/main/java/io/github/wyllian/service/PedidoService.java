package io.github.wyllian.service;

import java.util.Optional;

import io.github.wyllian.domain.entity.Pedido;
import io.github.wyllian.domain.enums.StatusPedido;
import io.github.wyllian.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer idPedido);
    void atualizarStatus(Integer id, StatusPedido statusPedido);
}
