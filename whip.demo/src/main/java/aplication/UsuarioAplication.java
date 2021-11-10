package aplication;

import static spark.Spark.*;
import service.UsuarioService;

public class UsuarioAplication{
	
	private static UsuarioService usuarioService = new UsuarioService();
	
	public static void main(String[] args) throws Exception{
		
		staticFiles.location("/public");
		
		post("/usuario", (request, response) -> usuarioService.add(request, response));
		
		post("/usuario/:username",  "application/json", (request, response) -> usuarioService.get(request, response));

		//get("/usuario/update/:username", (request, response) -> usuarioService.update(request, response));
		
		get("/usuario/remove/:username", (request, response) -> usuarioService.remove(request, response));

		get("/usuario", (request, response) -> usuarioService.getAll(request, response));
	}
}