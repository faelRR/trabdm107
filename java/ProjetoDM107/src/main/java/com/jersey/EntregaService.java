package com.jersey;

import java.sql.Connection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/apiEntrega")
public class EntregaService {

		@GET
		@Path("/numeroPedido/{numeroPedido}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response listEntregaById(@PathParam("numeroPedido") int numeroPedido) {
			ConnectionFactory connectionFactory = new ConnectionFactory();
			EntregaModel entrega = null;
			try {
				Connection conn = connectionFactory.getConnection();
				EntregaDAO entregaDAO = new EntregaDAO();
				entrega = entregaDAO.listByNumeroPedido(conn, numeroPedido);
				return Response.status(Response.Status.OK).entity(entrega).build();
			} catch (Exception e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		}


		@POST
		@Path("/pedido/")
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response create( EntregaModel entrega){
			ConnectionFactory connectionFactory = new ConnectionFactory();
			try {
				Connection conn = connectionFactory.getConnection();
				EntregaDAO entregaDAO = new EntregaDAO();
				entregaDAO.insert(conn, entrega);
				return Response.status(Response.Status.OK).build();
			} catch (Exception e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		
		
		/*
		 * 
		 * Métodos abaixo não requisitados no trabalho
		 * 
		 *
		  
		@PUT
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		@Path("/{id}")
		public Response update( EntregaModel entrega, @PathParam("id") int id){
			ConnectionFactory connectionFactory = new ConnectionFactory();
			try {
				Connection conn = connectionFactory.getConnection();
				EntregaDAO entregaDAO = new EntregaDAO();
				entregaDAO.update(conn, entrega, id);
				return Response.status(Response.Status.OK).build();
			} catch (Exception e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		} 
		
	
		@DELETE
		@Path("/{id}")
		public Response delete( @PathParam("id") int id){
			ConnectionFactory connectionFactory = new ConnectionFactory();
			try {
				Connection conn = connectionFactory.getConnection();
				EntregaDAO entregaDAO = new EntregaDAO();
				entregaDAO.delete(conn, id);
				return Response.status(Response.Status.OK).build();
			} catch (Exception e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		}

		@GET
		@Path("/entregas")
		@Produces(MediaType.APPLICATION_JSON)
		public Response listAllEntregas() {
			ConnectionFactory connectionFactory = new ConnectionFactory();
			List<EntregaModel> entregas = null;
			try {
				Connection conn = connectionFactory.getConnection();
				EntregaDAO entregaDAO = new EntregaDAO();
				entregas = entregaDAO.list(conn);
				return Response.status(Response.Status.OK).entity(entregas).build();
			} catch (Exception e) {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		*/
	
}