package service;

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

	public Object add(Request request, Response response) {
		//Usuario[] usuarios = UsuarioDAO.getUsuarios();
		String username = request.queryParams("username");
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");

		Usuario usuario = new Usuario(username, senha, email);
		//System.out.println(usuario.getUsername() + " " + usuario.getEmail() + " " + usuario.getSenha());
		
		UsuarioDAO.inserirUsuario(usuario);

		response.redirect("login.html");
		response.status(201); // 201 Created
		return username;
	}

	public Object get(Request request, Response response) {
		//System.out.println(request.body());
		Gson gson = new Gson();
		Usuario user = gson.fromJson(request.body(), Usuario.class);
		
		Usuario usuario = UsuarioDAO.getUsuario(user.getUsername(), user.getSenha());
		
		if (usuario != null) {
            response.status(200);
            return "ok";
        } else {
            response.status(404);
            return "Usuário " + usuario + " não encontrado.";
        }
	}

	/*public Object update(Request request, Response response) {
        String username = request.params(":username");
        
        //Usuario usuario = UsuarioDAO.getUsuario(username);

        if (usuario != null) {
        	usuario.setUsername(request.queryParams("username"));
        	usuario.setEmail(request.queryParams("email"));
        	usuario.setSenha(request.queryParams("senha"));

        	UsuarioDAO.atualizarUsuario(usuario);
        	
            return username;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }

	}*/

	public Object remove(Request request, Response response) {
        String username = request.params(":username");

            UsuarioDAO.excluirUsuario(username);

            response.status(200); // success
        	return username;
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<usuarios type=\"array\">");
		for (Usuario usuario : UsuarioDAO.getUsuarios()) {
			returnValue.append("\n<usuario>\n" + 
            		"\t<username>" + usuario.getUsername() + "</username>\n" +
            		"\t<email>" + usuario.getEmail() + "</email>\n" +
            		"\t<senha>" + usuario.getSenha() + "</senha>\n" +
            		"</usuario>\n");
		}
		returnValue.append("</usuarios>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}