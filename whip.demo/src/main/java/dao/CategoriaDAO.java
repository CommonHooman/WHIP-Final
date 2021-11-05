package dao;

import java.sql.*;
import model.Categoria;

public class CategoriaDAO {
	private Connection conexao;
	
	public CategoriaDAO() {
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
	
	public boolean inserirCategoria(Categoria categoria) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO Categoria (sigla, nome, cor, observacao, fk_usuario_username) "
					       + "VALUES ('" + categoria.getSigla() + "', '" + categoria.getNome() + "', '"  
					       + categoria.getCor() + "', '" + categoria.getObservacao() + "', '" + categoria.getUsername() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarCategoria(Categoria categoria) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE Categoria SET sigla = '" + categoria.getSigla() + "', nome = '" + categoria.getNome() 
					   + "', cor = '" + categoria.getCor() + "', observacao ='" + categoria.getObservacao() + "', username ='" + categoria.getUsername();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirCategoria(String sigla) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM Categoria WHERE sigla = " + sigla);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Categoria[] getCategorias() {
		Categoria[] categorias = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM Categoria");		
	         if(rs.next()){
	             rs.last();
	             categorias = new Categoria[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                categorias[i] = new Categoria(rs.getString("sigla"), rs.getString("nome"), rs.getString("cor"), rs.getString("observacao"), rs.getString("username"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return categorias;
	}

	
	public Categoria getCategoria(String sigla) {
		Categoria[] categorias = null;
		Categoria categoria = new Categoria();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM Categoria WHERE Categoria.sigla LIKE " + sigla);		
	         if(rs.next()){
	             rs.last();
	             categorias = new Categoria[rs.getRow()];
	             rs.beforeFirst();

		         categorias[0] = new Categoria(rs.getString("sigla"), rs.getString("nome"), rs.getString("cor"), rs.getString("observacao"), rs.getString("username"));
		         categoria = categorias[0];
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return categoria;
	}
}