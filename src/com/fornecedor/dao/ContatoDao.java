package com.fornecedor.dao;

import com.fornecedor.domain.Contato;
import com.fornecedor.domain.EntidadeDominio;

import java.sql.*;
import java.util.List;

public class ContatoDao extends AbstractDao{


    public ContatoDao(Connection cx) {
        super(cx, "contatos", "id");
    }

    public ContatoDao() {
        super("contatos", "id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws Exception {
        if (connection == null) {
            openConnection();
        }

        PreparedStatement pst = null;
        Contato contato = (Contato) entidade;
        StringBuilder sql = new StringBuilder();

        TelefoneDao telefoneDao = new TelefoneDao(connection);
        telefoneDao.ctrlTransaction = false;
        telefoneDao.salvar(contato.getTelefone());

        sql.append("insert into contatos (tel_id, email, departamento, nome, idade) values (?, ?, ?, ?, ?)");

        try {
            connection.setAutoCommit(false);

            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, contato.getTelefone().getId());
            pst.setString(2, contato.getEmail());
            pst.setString(3, contato.getDepartamento().getDescricao());
            pst.setString(4, contato.getNome());
            pst.setInt(5, contato.getIdade());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            contato.setId(id);
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
