package model;

import java.sql.*;

public class Credencial {
	/*
	 * Declaração dos atributos
	 */
	private String username;
	private String site;
	private String valor;
	private Date   dataCriacao;
	private String observacao;
	private String fk_usuario_username;
	private String fk_categoria_sigla;
	
	public Credencial() {
		username = null;
		site = null;
		valor = null;
		dataCriacao = null;
		observacao = null;
		fk_categoria_sigla = null;
	}
	
	public Credencial(String username, String site, String valor, String observacao, String fk_usuario_username, String fk_categoria_sigla) {
		long millis =  System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		this.username = username;
		this.site = site;
		this.valor = valor;
		this.dataCriacao = date;
		this.observacao = observacao;
		this.fk_usuario_username = fk_usuario_username;
		this.fk_categoria_sigla = fk_categoria_sigla;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao() {
		long millis =  System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		this.dataCriacao = date;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public String getFkUsername() {
		return this.fk_usuario_username;
	}
	
	public void setFkUsername(String fk_usuario_username) {
		this.fk_usuario_username = fk_usuario_username;
	}
	
	public String getCategoria() {
		return this.fk_categoria_sigla;
	}
	
	public void setCategoria(String fk_categoria_sigla) {
		this.fk_categoria_sigla = fk_categoria_sigla;
	}
	
	@Override
	public String toString() {
		return "Credencial [username=" + username + ", site=" + site + ", valor=" + valor + ", dataCriacao="
				+ dataCriacao + ", observacao=" + observacao + "]";
	}
}
