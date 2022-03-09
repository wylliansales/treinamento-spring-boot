package io.github.wyllian.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.wyllian.domain.entity.Cliente;
import io.github.wyllian.domain.entity.ItemPedido;
import io.github.wyllian.domain.entity.Pedido;
import io.github.wyllian.domain.entity.Produto;
import io.github.wyllian.domain.enums.StatusPedido;
import io.github.wyllian.domain.repository.ClienteRepository;
import io.github.wyllian.domain.repository.ItemPedidoRepository;
import io.github.wyllian.domain.repository.PedidoRepository;
import io.github.wyllian.domain.repository.ProdutoRepository;
import io.github.wyllian.exception.PedidoNaoEncontratoException;
import io.github.wyllian.exception.RegraNegocioException;
import io.github.wyllian.rest.dto.ItemPedidoDTO;
import io.github.wyllian.rest.dto.PedidoDTO;
import io.github.wyllian.service.PedidoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService{
    
    private final PedidoRepository      pedidoRepository;
    private final ClienteRepository     clienteRepository;
    private final ProdutoRepository     produtoRepository;
    private final ItemPedidoRepository  itemPedidoRepository;

    @Override
    @Transactional // Garante que todas as operaçães no banco sejam realizadas, caso contrário realizar rollback
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = this.clienteRepository.findById(idCliente)
                              .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido =  converterItems(pedido, dto.getItems());
        this.pedidoRepository.save(pedido);
        this.itemPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if (items.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem items");
        }
        return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = this.produtoRepository.findById(idProduto)
                                     .orElseThrow( () -> new RegraNegocioException("Código de produto inválido: " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuatidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer idPedido) {        
        return this.pedidoRepository.findByIdFetchItens(idPedido);
    }

    @Override
    @Transactional
    public void atualizarStatus(Integer id, StatusPedido statusPedido) {
        this.pedidoRepository.findById(id).map(pedido ->  {
            pedido.setStatus(statusPedido);
            return this.pedidoRepository.save(pedido);
        }).orElseThrow( () -> new PedidoNaoEncontratoException());  
    } 
}
