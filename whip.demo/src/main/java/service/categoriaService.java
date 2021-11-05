package service;

import dao.CategoriaDAO;
import model.Categoria;

import spark.Request;
import spark.Response;


public class categoriaService {

	private CategoriaDAO CategoriaDAO;

	public categoriaService() {
		CategoriaDAO = new CategoriaDAO();
		CategoriaDAO.conectar();
	} 

	public Object add(Request request, Response response) {
		//Categoria[] categorias = CategoriaDAO.getCategorias(); //é necessário ter essa linha?
		String sigla = request.queryParams("sigla");
		String nome = request.queryParams("nome");
		String cor = request.queryParams("cor");
		String observacao = request.queryParams("observacao");
		String fk_usuario_username = request.queryParams("username");

		Categoria categoria = new Categoria(sigla, nome, cor, observacao, fk_usuario_username);

		CategoriaDAO.inserirCategoria(categoria);

		response.status(201); // 201 Created
		return sigla;
	}

	public Object get(Request request, Response response) {
		String sigla = request.params(":sigla");
		
		Categoria categoria = CategoriaDAO.getCategoria(sigla);
		
		if (categoria != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<categoria>\n" + 
            		"\t<sigla>" + categoria.getSigla() + "</sigla>\n" +
            		"\t<nome>" + categoria.getNome() + "</nome>\n" +
            		"\t<cor>" + categoria.getCor() + "</cor>\n" +
                    "\t<observacao>" + categoria.getObservacao() + "</observacao>\n" +
            		"\t<username>" + categoria.getUsername() + "</username>\n" + 
            		"</categoria>\n";
        } else {
            response.status(404); // 404 Not found
            return "Categoria " + categoria + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        String sigla = request.params(":sigla");
        
        Categoria categoria = CategoriaDAO.getCategoria(sigla);

        if (categoria != null) {
        	categoria.setSigla(request.queryParams("sigla"));
        	categoria.setNome(request.queryParams("nome"));
        	categoria.setCor(request.queryParams("cor"));
        	categoria.setObservacao(request.queryParams("observacao"));
        	categoria.setUsername(request.queryParams("username"));

        	CategoriaDAO.atualizarCategoria(categoria);
        	
            return categoria;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        String sigla = request.params(":sigla");

            CategoriaDAO.excluirCategoria(sigla);

            response.status(200); // success
        	return sigla;
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<categorias type=\"array\">");
		for (Categoria categoria : CategoriaDAO.getCategorias()) {
			returnValue.append("\n<categoria>\n" + 
            		"\t<sigla>" + categoria.getSigla() + "</sigla>\n" +
            		"\t<nome>" + categoria.getNome() + "</nome>\n" +
            		"\t<cor>" + categoria.getCor() + "</cor>\n" +
                    "\t<observacao>" + categoria.getObservacao() + "</observacao>\n" +
            		"\t<username>" + categoria.getUsername() + "</username>\n" + 
            		"</categoria>\n");
		}
		returnValue.append("</categorias>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}