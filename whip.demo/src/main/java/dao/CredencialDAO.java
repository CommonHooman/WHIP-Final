package dao;

import java.sql.*;
import java.util.Base64;

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
			PreparedStatement st = conexao.prepareStatement("INSERT INTO credencial (username, site, valor, dataCriacao, fk_usuario_username, fk_categoria_sigla) VALUES (?,?,?,?,?,?)");
			st.setString(1, credencial.getUsername());
			st.setString(2, credencial.getSite());
			st.setString(3, credencial.getValor());
			st.setDate(4, credencial.getDataCriacao());
			st.setString(5, credencial.getFkUsername()); 
			st.setString(6, "padrão"); 
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Credencial[] getCredenciais(String username) {
		Credencial[] credenciais = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM Credencial WHERE Credencial.fk_usuario_username = '" + username + "'");		
	         if(rs.next()){
	             rs.last();
	             credenciais = new Credencial[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                credenciais[i] = new Credencial(rs.getString("username"), rs.getString("site"), decrypt(rs.getString("valor")),
	                								rs.getString("observacao"), rs.getString("fk_usuario_username"), 
	                								rs.getString("fk_categoria_sigla"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return credenciais;
	}

	
	public Credencial getCredencial(String username) {
		Credencial[] credenciais = null;
		Credencial credencial = new Credencial();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM Credencial WHERE Credencial.username = '" + username + "'");		
	         while(rs.next()){
	             rs.last();
	             credenciais = new Credencial[rs.getRow()];
	             rs.beforeFirst();

		         credenciais[0] = new Credencial(rs.getString("username"), rs.getString("site"), decrypt(rs.getString("valor")), 
	                        					 rs.getString("observacao"), rs.getString("fkusername"), rs.getString("categoria"));
		         credencial = credenciais[0];
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return credencial;
	}
	
	public static String decrypt(String pValor) {
	    return new String(Base64.getDecoder().decode(pValor.getBytes()));
	}
}