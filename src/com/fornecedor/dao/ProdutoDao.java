package com.fornecedor.dao;

import com.fornecedor.domain.EntidadeDominio;
import com.fornecedor.domain.Produto;
import com.fornecedor.domain.Telefone;

import java.sql.*;
import java.util.List;

public class ProdutoDao extends AbstractDao {
    public ProdutoDao(Connection connection) {
        super(connection, "produtos", "id");
    }

    public ProdutoDao() {
        super("produtos", "id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws Exception {
        if (connection == null) {
            openConnection();
        }

        PreparedStatement pst = null;
        Produto produto = (Produto) entidade;
        StringBuilder sql = new StringBuilder();

        sql.append("insert into produtos (nome, for_id, descricao) values (?, ?, ?)");

        try {
            connection.setAutoCommit(false);

            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, produto.getNome());
            pst.setInt(2, produto.getFornecedor().getId());
            pst.setString(3, produto.getDescricao());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            produto.setId(id);
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (ctrlTransaction) {
                try {
                    pst.close();
                    if (ctrlTransaction) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void alterar(EntidadeDominio entidade) throws Exception {

    }

    @Override
    public EntidadeDominio consultar(EntidadeDominio entidade) throws Exception {
        return null;
    }

    @Override
    public List<EntidadeDominio> consultarTodos(EntidadeDominio entidade) throws Exception {
        return null;
    }
}
