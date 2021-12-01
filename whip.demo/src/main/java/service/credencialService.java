package service;

import com.google.gson.Gson;

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
		//Credencial[] credenciais = CredencialDAO.getCredenciais(); //é necessário ter essa linha?
		String username = request.queryParams("username");
		String site = request.queryParams("site");
		String valor = request.queryParams("valor");
		String observacao = request.queryParams("observacao");
		String fk_username = request.queryParams("fkusername");
		String categoria = request.queryParams("categoria");

		Credencial credencial = new Credencial(username, site, valor, observacao, fk_username, categoria);
		//System.out.println(credencial.getSite() + " " + credencial.getValor());

		CredencialDAO.inserirCredencial(credencial);
		response.redirect("userPage.html");

		response.status(201); // 201 Created
		return username;
	}

	public Object get(Request request, Response response) {
		String username = request.params(":username");
		
		Credencial[] credencial = CredencialDAO.getCredenciais(username);
		Gson gson = new Gson();
		
		/*if (credencial != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<credencial>\n" + 
            		"\t<username>" + credencial.getUsername() + "</username>\n" +
            		"\t<site>" + credencial.getSite() + "</site>\n" +
            		"\t<valor>" + credencial.getValor() + "</valor>\n" +
                    "\t<dataCriacao>" + credencial.getDataCriacao() + "</dataCriacao>\n" +
                    "\t<observacao>" + credencial.getObservacao() + "</observacao>\n" +
                    "\t<fkusername>" + credencial.getFkUsername() + "</fkusername>\n" +
                    "\t<categoria>" + credencial.getCategoria() + "</categoria>\n" +
            		"</credencial>\n";
        } else {
            response.status(404); // 404 Not found
            return "Credencial " + credencial + " não encontrado.";
        }*/
		if  (credencial != null) {
		    response.status(200);
		} else response.status(400);
		 return gson.toJson(credencial);

	}

	public Object update(Request request, Response response) {
        String site = request.params(":site");
        
        Credencial credencial = CredencialDAO.getCredencial(site);

        if (credencial != null) {
        	credencial.setUsername(request.queryParams("username"));
        	credencial.setSite(request.queryParams("site"));
        	credencial.setValor(request.queryParams("valor"));
        	credencial.setObservacao(request.queryParams("observacao"));
        	credencial.setFkUsername(request.queryParams("fkusername"));
        	credencial.setCategoria(request.queryParams("categoria"));

        	CredencialDAO.atualizarCredencial(credencial);
        	
            return credencial;
        } else {
            response.status(404); // 404 Not found
            return "Produto não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        String username = request.params(":username");

            CredencialDAO.excluirCredencial(username);

            response.status(200); // success
        	return username;
	}

	/*public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<credenciais type=\"array\">");
		for (Credencial credencial : CredencialDAO.getCredenciais()) {
			returnValue.append("\n<credencial>\n" + 
            		"\t<username>" + credencial.getUsername() + "</username>\n" +
            		"\t<site>" + credencial.getSite() + "</site>\n" +
            		"\t<valor>" + credencial.getValor() + "</valor>\n" +
                    "\t<dataCriacao>" + credencial.getDataCriacao() + "</dataCriacao>\n" +
                    "\t<observacao>" + credencial.getObservacao() + "</observacao>\n" +
                    "\t<fkusername>" + credencial.getFkUsername() + "</fkusername>\n" +
                    "\t<categoria>" + credencial.getCategoria() + "</categoria>\n" +
            		"</credencial>\n");
		}
		returnValue.append("</credenciais>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}*/
}