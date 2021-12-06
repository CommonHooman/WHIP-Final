package service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.gson.Gson;

import dao.UsuarioDAO;
import model.Usuario;

import spark.Request;
import spark.Response;

public class UsuarioService {

	private UsuarioDAO UsuarioDAO;

	public UsuarioService() {
		UsuarioDAO = new UsuarioDAO();
		UsuarioDAO.conectar();
	} 

	public Object add(Request request, Response response) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String username = request.queryParams("username");
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");

		Usuario usuario = new Usuario(username, crypt(senha), email);
		
		UsuarioDAO.inserirUsuario(usuario);

		response.redirect("login.html");
		response.status(201); // 201 Created
		return username;
	}

	public Object get(Request request, Response response) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Gson gson = new Gson();
		Usuario user = gson.fromJson(request.body(), Usuario.class);          
		
		Usuario usuario = UsuarioDAO.getUsuario(user.getUsername(), crypt(user.getSenha()));
		
		if (usuario != null) {
            response.status(200);
            return "ok";
        } else {
            response.status(404);
            return "Usuário " + usuario + " não encontrado.";
        }
	}

	public String crypt(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte messageDigest[] = md5.digest(senha.getBytes("UTF-8"));
		
		String crypted = new String(messageDigest, "UTF-8");
		return crypted;
	}
}