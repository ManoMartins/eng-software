package com.fornecedor.dao;

import com.fornecedor.domain.EntidadeDominio;

import java.util.List;

public interface IDAO {
    void salvar(EntidadeDominio entidade) throws Exception;

    void alterar(EntidadeDominio entidade) throws Exception;

    void excluir(EntidadeDominio entidade) throws Exception;

    EntidadeDominio consultar(EntidadeDominio entidade) throws Exception;

    List<EntidadeDominio> consultarTodos(EntidadeDominio entidade) throws Exception;
}
