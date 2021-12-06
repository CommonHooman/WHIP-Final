package service;

import com.google.gson.Gson;
import java.util.*;

import dao.CredencialDAO;
import model.Credencial;

import spark.Request;
import spark.Response;


public class CredencialService {

	private CredencialDAO CredencialDAO;

	public CredencialService() {
		CredencialDAO = new CredencialDAO();
		CredencialDAO.conectar();
	} 

	public Object add(Request request, Response response) {
		String username = request.queryParams("username");
		String site = request.queryParams("site");
		String valor = request.queryParams("valor");
		String observacao = request.queryParams("observacao");
		String fk_username = request.queryParams("fk_username");
		String categoria = request.queryParams("categoria");

		Credencial credencial = new Credencial(username, site, crypt(valor), observacao, fk_username, categoria);

		CredencialDAO.inserirCredencial(credencial);
		//response.redirect("userPage.html");

		response.status(201); // 201 Created
		return username;
	}

	public Object get(Request request, Response response) {
		String username = request.params(":username");
		
		Credencial[] credencial = CredencialDAO.getCredenciais(username);
		Gson gson = new Gson();
		
		if  (credencial != null) {
		    response.status(200);
		    response.header("Content-Type", "application/json");
		} else response.status(400);
		 return gson.toJson(credencial);
	}
	
	public static String crypt(String pValor) {
	    return new String(Base64.getEncoder().encode(pValor.getBytes()));
	}
}