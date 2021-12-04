package aplication;

import static spark.Spark.*;

import service.CredencialService;
import service.UsuarioService;

public class Application{
	
	private static UsuarioService usuarioService = new UsuarioService();
	private static CredencialService credencialService = new CredencialService();
	
	public static void main(String[] args) throws Exception{
		
		staticFiles.location("/public");
		
		post("/usuario", (request, response) -> usuarioService.add(request, response));
		
		post("/usuario/:username",  "application/json", (request, response) -> usuarioService.get(request, response));
		
		post("/credencial", (request, response) -> credencialService.add(request, response));
		
		get("/credencial/:username", (request, response) -> credencialService.get(request, response));
	}
}