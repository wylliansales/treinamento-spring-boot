package io.github.wyllian.domain.entity;


import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "descricao")
    @NotEmpty(message = "Campo descrição é obrigatório")
    private String decricao;

    @Column(name = "preco_unitario")
    @NotNull(message = "Campo preço é obrigatório")
    private BigDecimal preco;    
}
