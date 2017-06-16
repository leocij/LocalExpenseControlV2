package com.lemelo.localexpensecontrolv2;

/*
 * Created by leoci on 13/06/2017.
 */

import java.sql.Date;

class Entrada {
    private Integer id;
    private String data;
    private String descricao;
    private String valor;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Data: " + data +
                "\nDescrição: " + descricao +
                "\nValor: " + valor;
    }
}
