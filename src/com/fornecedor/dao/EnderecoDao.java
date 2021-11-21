package com.fornecedor.dao;

import com.fornecedor.domain.Endereco;
import com.fornecedor.domain.EntidadeDominio;

import java.sql.*;
import java.util.List;

public class EnderecoDao extends AbstractDao {
    public EnderecoDao(Connection connection) {
        super(connection, "enderecos", "id");
    }

    public EnderecoDao() {
        super("enderecos", "id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws Exception {
        if (connection == null) {
            openConnection();
        }

        PreparedStatement pst = null;
        Endereco endereco = (Endereco) entidade;
        StringBuilder sql = new StringBuilder();

        sql.append("insert into enderecos ( ");
        sql.append("logradouro, ");
        sql.append("numero, ");
        sql.append("cep, ");
        sql.append("bairro, ");
        sql.append("complemento, ");
        sql.append("cidade, ");
        sql.append("estado, ");
        sql.append("pais ");
        sql.append(") ");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?)");

        try {
            connection.setAutoCommit(false);

            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, endereco.getLogradouro());
            pst.setString(2, endereco.getNumero());
            pst.setString(3, endereco.getCep());
            pst.setString(4, endereco.getBairro());
            pst.setString(5, endereco.getComplemento());
            pst.setString(6, endereco.getCidade().getDescricao());
            pst.setString(7, endereco.getCidade().getEstado().getDescricao());
            pst.setString(8, endereco.getCidade().getEstado().getPais().getDescricao());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            endereco.setId(id);

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
