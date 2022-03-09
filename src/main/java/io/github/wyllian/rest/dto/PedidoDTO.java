package io.github.wyllian.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.github.wyllian.domain.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  {
 *   "cliente" : 1,
 *   "total": 100,
 *   "items": [
 *       {
 *           "produto": 1,
 *           "quantidade": 10
 *       }
 *   ]
 *   }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
    private Integer cliente;

    @NotNull(message = "{campo.total-pedido.obrigatorio}")
    private BigDecimal total;

    @NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
    private List<ItemPedidoDTO> items;
}
