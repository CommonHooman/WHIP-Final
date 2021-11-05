package dao;

import java.sql.*;
import model.Credencial;

public class CredencialDAO {
	private Connection conexao;
	
	public CredencialDAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "whip.postgres.database.azure.com";
		String mydatabase = "whip";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
		String username = "adm@whip";
		String password = "@Pucminas";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public boolean inserirCredencial(Credencial credencial) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO Credencial (username, site, valor, dataCriacao, observacao, fk_usuario_username, fk_categoria_sigla) "
					       + "VALUES ('" + credencial.getUsername() + "', '" + credencial.getSite() + "', '"  
					       + credencial.getValor() + "', '" + credencial.getDataCriacao() + "', '" + credencial.getObservacao()
					       + "', " + credencial.getFkUsername() + "', " + credencial.getCategoria() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarCredencial(Credencial credencial) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE Credencial SET username = '" + credencial.getUsername() + "', site = '" + credencial.getSite() 
					   + "', valor = '" + credencial.getValor() + "', dataCriacao = '" + credencial.getDataCriacao() + "', observacao ='" 
					   + credencial.getObservacao() + "', username = '" + credencial.getFkUsername() + "', categoria = '" + credencial.getCategoria();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirCredencial(String site) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM Credencial WHERE site = " + site);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Credencial[] getCredenciais() {
		Credencial[] credenciais = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM Credencial");		
	         if(rs.next()){
	             rs.last();
	             credenciais = new Credencial[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                credenciais[i] = new Credencial(rs.getString("username"), rs.getString("site"), rs.getString("valor"), 
	                		                        rs.getDate("dataCriacao").toLocalDate(), rs.getString("observacao"), rs.getString("fkusername"),
	                		                        rs.getString("categoria"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return credenciais;
	}

	
	public Credencial getCredencial(String site) {
		Credencial[] credenciais = null;
		Credencial credencial = new Credencial();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM Credencial WHERE Credencial.site LIKE " + site);		
	         if(rs.next()){
	             rs.last();
	             credenciais = new Credencial[rs.getRow()];
	             rs.beforeFirst();

		         credenciais[0] = new Credencial(rs.getString("username"), rs.getString("site"), rs.getString("valor"), 
	                        					 rs.getDate("dataCriacao").toLocalDate(), rs.getString("observacao"), rs.getString("fkusername"),
	                        					 rs.getString("categoria"));
		         credencial = credenciais[0];
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return credencial;
	}
}