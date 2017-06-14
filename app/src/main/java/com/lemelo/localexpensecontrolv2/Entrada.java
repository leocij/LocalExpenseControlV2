package com.lemelo.localexpensecontrolv2;

/*
 * Created by leoci on 13/06/2017.
 */

import java.math.BigDecimal;
import java.sql.Date;

class Entrada {
    private Integer id;
    private Date data;
    private String descricao;
    private BigDecimal valor;

    public Entrada() {
        setId(id);
        setData(data);
        setDescricao(descricao);
        setValor(valor);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Data: " + data +
                "\nDescrição: " + descricao +
                "\nValor: " + valor;
    }
}
