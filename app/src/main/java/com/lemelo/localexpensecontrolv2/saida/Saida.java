package com.lemelo.localexpensecontrolv2.saida;

/*
 * Created by leoci on 13/06/2017.
 */

class Saida {
    private Integer id;
    private String data;
    private String descricao;
    private String valor;

    public Saida() {
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
        int aux;
        int tam = descricao.length();
        int espaco = 25;
        StringBuilder espacos = new StringBuilder();
        if(tam < espaco){
            aux = espaco - tam;
            int i;
            for(i=0; i<aux; i++){
                espacos.append("-");
            }
        }

        String saida = "" + data + "\n" + descricao + espacos.toString() + valor;

        return saida;
        //return data + "\n" + descricao + espacos.toString() + valor;
    }
}
