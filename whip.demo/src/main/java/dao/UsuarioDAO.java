package dao;

import java.sql.*;
import model.Usuario;

public class UsuarioDAO {
	private Connection conexao;
	
	public UsuarioDAO() {
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
	
	public boolean inserirUsuario(Usuario user) {
		boolean status = false;
		try {  
			PreparedStatement st = conexao.prepareStatement("INSERT INTO usuario (username, email, senha) VALUES (?,?,?)");
			st.setString(1, user.getUsername());
			st.setString(2, user.getEmail());
			st.setString(3, user.getSenha());
			st.executeUpdate();
			st.close();

			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Usuario getUsuario(String username, String senha) {
		Usuario usuario = null;
		try {
			PreparedStatement st = conexao.prepareStatement("SELECT * FROM usuario WHERE usuario.username = ? AND usuario.senha = ?");

			st.setString(1, username);
			st.setString(2, senha);
			ResultSet rs = st.executeQuery();
			
	         if(rs.next()){
		         usuario = new Usuario(rs.getString("username"), rs.getString("email"), rs.getString("senha"));
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuario;
	}
}