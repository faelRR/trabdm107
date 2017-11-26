package com.jersey;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {
	
	public void insert(Connection conn, EntregaModel entrega) throws SQLException{
		String sql = "insert into entregas (id, numero_pedido, id_cliente, nome_recebedor, cpf_recebedor, data_hora_entrega) values (?, ?, ?, ?, ?, ?);";
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, entrega.getId());
		pstm.setInt(2, entrega.getNumPedido());
		pstm.setInt(3, entrega.getIdCliente());
		pstm.setString(4, entrega.getNomeRecebedor());
		pstm.setString(5, entrega.getCpfRecebedor());
		pstm.setDate(6, new java.sql.Date(entrega.getDataHoraEntrega().getTime()));
		pstm.execute();
	}
	
	public void update(Connection conn, EntregaModel entrega , int id) throws SQLException{
		String sql = "update entregas set numero_pedido = ? , id_cliente = ? , nome_recebedor = ? , cpf_recebedor = ? , data_hora_entrega = ?  where id = ? ";
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);		
		pstm.setInt(1, entrega.getId());
		pstm.setInt(2, entrega.getNumPedido());
		pstm.setInt(3, entrega.getIdCliente());
		pstm.setString(4, entrega.getNomeRecebedor());
		pstm.setString(5, entrega.getCpfRecebedor());
		pstm.setDate(6, new java.sql.Date(entrega.getDataHoraEntrega().getTime()));
		pstm.setInt(7, id);
		pstm.execute();
	}
	
	public void delete(Connection conn, int idEntrega) throws SQLException{
		String sql = "delete entregas where id = ? ";
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);		
		pstm.setInt(1, idEntrega);
		pstm.execute();
	}
	
	public List<EntregaModel> list(Connection conn) throws SQLException{
		String sql = "select * from entregas";
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);
		ResultSet rs = pstm.executeQuery();

		List<EntregaModel> entrega = new ArrayList<EntregaModel>();
		while (rs.next())
		{
			int id = rs.getInt("id");
			int numero_pedido = rs.getInt("numero_pedido");
			int id_cliente = rs.getInt("id_cliente");
			String nome_recebedor = rs.getString("nome_recebedor");
			String cpf_recebedor = rs.getString("cpf_recebedor");
			Date data_hora_entrega = rs.getDate("data_hora_entrega");
			entrega.add(new EntregaModel(id, numero_pedido, id_cliente, nome_recebedor, cpf_recebedor, data_hora_entrega));
		}
		return entrega;	
	}	
	
	public EntregaModel listByNumeroPedido(Connection conn, int numeroPedido) throws SQLException{
		String sql = "select * from entregas where numero_pedido = ?";
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, numeroPedido);
		ResultSet rs = pstm.executeQuery();

		EntregaModel entrega = new EntregaModel();
		while (rs.next())
		{
			int id = rs.getInt("id");
			int numero_pedido = rs.getInt("numero_pedido");
			int id_cliente = rs.getInt("id_cliente");
			String nome_recebedor = rs.getString("nome_recebedor");
			String cpf_recebedor = rs.getString("cpf_recebedor");
			Date data_hora_entrega = rs.getDate("data_hora_entrega");
			entrega = new EntregaModel(id, numero_pedido, id_cliente, nome_recebedor, cpf_recebedor, data_hora_entrega);
		}
		return entrega;
		
	}
}