package com.fornecedor.dao;

import com.fornecedor.domain.EntidadeDominio;
import com.fornecedor.domain.Telefone;

import java.sql.*;
import java.util.List;

public class TelefoneDao extends AbstractDao {
    public TelefoneDao(Connection connection) {
        super(connection, "telefones", "id");
    }

    public TelefoneDao() {
        super("telefones", "id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws Exception {
        if (connection == null) {
            openConnection();
        }

        PreparedStatement pst = null;
        Telefone telefone = (Telefone) entidade;
        StringBuilder sql = new StringBuilder();

        sql.append("insert into telefones (ddd, numero, tipo) values (?, ?, ?)");

        try {
            //connection.setAutoCommit(false);

            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, telefone.getDdd());
            pst.setString(2, telefone.getNumero());
            pst.setString(3, telefone.getTipoTelefone().getDescricao());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            telefone.setId(id);
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
