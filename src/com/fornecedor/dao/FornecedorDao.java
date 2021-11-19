package com.fornecedor.dao;

import com.fornecedor.domain.Contato;
import com.fornecedor.domain.EntidadeDominio;
import com.fornecedor.domain.Fornecedor;

import java.sql.*;
import java.util.List;

public class FornecedorDao extends AbstractDao {

    public FornecedorDao(Connection cx) {
        super(cx, "fornecedores", "id");
    }

    public FornecedorDao() {
        super("fornecedores", "id");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws Exception {
        if (connection == null) {
            openConnection();
        }

        PreparedStatement pst = null;
        Fornecedor fornecedor = (Fornecedor) entidade;
        StringBuilder sql = new StringBuilder();

        TelefoneDao telefoneDao = new TelefoneDao(connection);
        telefoneDao.ctrlTransaction = false;
        telefoneDao.salvar(fornecedor.getTelefone());

        ContatoDao contatoDao = new ContatoDao(connection);
        contatoDao.ctrlTransaction = false;
        contatoDao.salvar(fornecedor.getContato());

        sql.append("insert into fornecedores ( ");
        sql.append("tel_id, ");
        sql.append("con_id, ");
        sql.append("email, ");
        sql.append("cnpj, ");
        sql.append("inscricao_municipal, ");
        sql.append("inscricao_estadual, ");
        sql.append("razao_social, ");
        sql.append("nome_fantasia, ");
        sql.append("tipo_fornecedor, ");
        sql.append("status");
        sql.append(") ");
        sql.append("values (?, ?, ?, ?, ?, ?, ?, ?, ?::fornecedor_type_enum, ?::status_type_enum)");

        try {
            connection.setAutoCommit(false);

            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, fornecedor.getTelefone().getId());
            pst.setInt(2, fornecedor.getContato().getId());
            pst.setString(3, fornecedor.getEmail());
            pst.setString(4, fornecedor.getCnpj());
            pst.setString(5, fornecedor.getIncricaoMunicipal());
            pst.setString(6, fornecedor.getIncricaoEstadual());
            pst.setString(7, fornecedor.getRazaoSocial());
            pst.setString(8, fornecedor.getNomeFantasia());
            pst.setString(9, fornecedor.getTipoFornecedor().name());
            pst.setString(10, fornecedor.getStatus().name());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            fornecedor.setId(id);
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
