package io.github.wyllian.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.wyllian.domain.entity.ItemPedido;
import io.github.wyllian.domain.entity.Pedido;
import io.github.wyllian.domain.enums.StatusPedido;
import io.github.wyllian.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.wyllian.rest.dto.InformacaoItemPedidoDTO;
import io.github.wyllian.rest.dto.InformacoesPedidoDTO;
import io.github.wyllian.rest.dto.PedidoDTO;
import io.github.wyllian.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    
  private PedidoService pedidoService;

  public PedidoController(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Integer salvarPedido(@RequestBody PedidoDTO dto) {
    Pedido pedido = this.pedidoService.salvar(dto);
    return pedido.getId();
  }

  @GetMapping("/{id}")
  public InformacoesPedidoDTO getById(@PathVariable Integer id) {
    return this.pedidoService
                    .obterPedidoCompleto(id)
                    .map(p -> converter(p))
                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.CREATED, "Pedido não encontrado."));
  }

  private InformacoesPedidoDTO converter(Pedido pedido) {
    return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .items(converter(pedido.getItens()))
                .status(pedido.getStatus().name())
                .build();
  }

  private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
      if(CollectionUtils.isEmpty(itens)) {
          return List.of();
      }
      return itens.stream().map(
                        item -> InformacaoItemPedidoDTO
                                    .builder()
                                    .descricaoProduto(item.getProduto().getDecricao())
                                    .precoUnitario(item.getProduto().getPreco())
                                    .quantidade(item.getQuatidade())
                                    .build()
                        ).collect(Collectors.toList());
   
  }

  @PatchMapping("{id}")
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void updateStatus( @PathVariable Integer id, 
                            @RequestBody AtualizacaoStatusPedidoDTO statusDTO ) {
        String novStatus = statusDTO.getNovoStatus();
        this.pedidoService.atualizarStatus(id, StatusPedido.valueOf(novStatus));                                 
  }
}
