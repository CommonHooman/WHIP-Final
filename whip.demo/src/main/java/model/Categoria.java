package model;

public class Categoria {
	/*
	 * Declaração dos atributos 
	 */
	private String sigla;
	private String nome;
	private String cor;
	private String observacao;
	private String fk_usuario_username;
	
	/*
	 * Construtor primário
	 */
	public Categoria() {
		sigla = null;
		nome = null;
		cor = null;
		observacao = null;
		fk_usuario_username = null;
	}
	
	/*
	 * Construtor secundário
	 * @param allCategoria
	 */
	public Categoria(String sigla, String nome, String cor, String observacao, String fk_usuario_username) {
		this.sigla = sigla;
		this.nome = nome;
		this.cor = cor;
		this.observacao = observacao;
		this.fk_usuario_username = fk_usuario_username;
	}
	
	/*
	 * Função para retornar a sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/*
	 * Método para setar o atributo sigla
	 * @param String sigla
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/*
	 * Função para retornar o nome
	 */
	public String getNome() {
		return nome;
	}

	/*
	 * Método para setar o atributo nome
	 * @param String nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/*
	 * Função para retornar a cor
	 */
	public String getCor() {
		return cor;
	}

	/*
	 * Método para setar a cor
	 * @param String cor
	 */
	public void setCor(String cor) {
		this.cor = cor;
	}

	/*
	 * Função para retornar a observacao
	 */
	public String getObservacao() {
		return observacao;
	}

	/*
	 * Método para setar a observacao
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public String getUsername() {
		return this.fk_usuario_username;
	}
	
	public void setUsername(String fk_usuario_username) {
		this.fk_usuario_username = fk_usuario_username;
	}

	/*
	 * Função para retornar uma string com todos os atributos
	 */
	@Override
	public String toString() {
		return "Categoria [sigla=" + sigla + ", nome=" + nome + ", cor=" + cor + ", observacao=" + observacao + "]";
	}
}