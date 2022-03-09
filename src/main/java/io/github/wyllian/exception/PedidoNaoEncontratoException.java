package io.github.wyllian.exception;


public class PedidoNaoEncontratoException extends RuntimeException{

    public PedidoNaoEncontratoException() {
        super("Pedido não encontrato");
    }
    
}
