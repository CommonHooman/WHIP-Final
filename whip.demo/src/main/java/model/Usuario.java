package model;

public class Usuario {
	
	/*
	 * Declaração das colunas correspondentes ao BD
	 */
	private String username;
	private String senha;
	private String email;
	
	/*
	 * Construtor primário
	 */
	public Usuario() {
		username = null;
		senha = null;
		email = null;
	}
	
	/*
	 * Construtor secundário
	 * @param allUsuario
	 */
	public Usuario(String username, String senha, String email) {
		this.username = username;
		this.senha = senha;
		this.email = email;
	}
	
	/*
	 * Método para setar o atributo username
	 * @param String username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/*
	 * Função para retornar o atributo username
	 */
	public String getUsername() {
		return this.username;
	}
	
	/*
	 * Método para setar o atributo senha
	 * @param String senha
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	/*
	 * Função para retornar o atributo senha
	 */
	public String getSenha() {
		return this.senha;
	}
	
	/*
	 * Método para setar o atributo email
	 * @param String email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/*
	 * Função para retornar o atributo email
	 */
	public String getEmail() {
		return this.email;
	}
	
	/*
	 * Função que retorna todos os atributos em uma única String
	 */
	public String toString() {
		return "Usuario [username=" + this.username + ", email=" + this.email + ", senha=" + this.senha + "]";
	}
}
