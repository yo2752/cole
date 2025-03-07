package org.gestoresmadrid.oegam2.permisoDistintivoItv.controller.action;

import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDetalleDocPrmDstvFichaDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class DetalleDocPrmDstvFichaAction extends ActionBase{

	private static final long serialVersionUID = -779999420348051025L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(DetalleDocPrmDstvFichaAction.class);
	
	private String docId;
	private String numExpediente;
	private String resultadosPorPagina;
	private PaginatedList lista;
	private int page = 0;
	private String dir;

	@Autowired
	ServicioDetalleDocPrmDstvFichaDgt servicioDetalleDocPrmDstvFichaDgt;
	
	public String buscarTramites(){
		log.info("DatosSensiblesAction consultar Inicio");
		try {
			if(resultadosPorPagina == null || resultadosPorPagina.isEmpty()){
				if(getSession().get(getKeyResultadosPorPaginaSession()) != null){
					resultadosPorPagina = (String) getSession().get(getKeyResultadosPorPaginaSession());
				} else {
					resultadosPorPagina = "5";
				}
			}
			if(docId == null || docId.isEmpty()){
				if(getSession().get(getKeyCriteriosSession()) != null){
					docId = (String) getSession().get(getKeyCriteriosSession());
				}
			}
			if(page == 0){
				page = 1;
			}
			ResultadoPermisoDistintivoItvBean resultado = servicioDetalleDocPrmDstvFichaDgt.getTramitesDocPrmDstvFicha(docId, page,resultadosPorPagina);
			if(resultado.getError()){
				addActionError(resultado.getMensaje());
			} else{
				lista = resultado.getListaDetallePaginacion();
				getSession().put(getKeyCriteriosSession(), docId);
				getSession().put(getKeyResultadosPorPaginaSession(), resultadosPorPagina);
			}
		} catch (Exception e) {
			addActionError("Se ha producido un error a la hora de cobtener el detalle del documento");
			log.error("Se ha producido un error a la hora de cobtener el detalle del documento, Error ", e);
		}
		return SUCCESS;
	}
	
	public String navegar(){
		return buscarTramites();
	}

	protected String getKeyCriteriosSession(){
		return "keyCriteriosSession"+this.getClass().getName();
	}
	
	protected String getKeyResultadosPorPaginaSession(){
		return "keyResultadosPorPaginaSession"+this.getClass().getName();
	}
	
	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getResultadosPorPagina() {
		return resultadosPorPagina;
	}

	public void setResultadosPorPagina(String resultadosPorPagina) {
		this.resultadosPorPagina = resultadosPorPagina;
	}

	public PaginatedList getLista() {
		return lista;
	}

	public void setLista(PaginatedList lista) {
		this.lista = lista;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public ServicioDetalleDocPrmDstvFichaDgt getServicioDetalleDocPrmDstvFichaDgt() {
		return servicioDetalleDocPrmDstvFichaDgt;
	}

	public void setServicioDetalleDocPrmDstvFichaDgt(ServicioDetalleDocPrmDstvFichaDgt servicioDetalleDocPrmDstvFichaDgt) {
		this.servicioDetalleDocPrmDstvFichaDgt = servicioDetalleDocPrmDstvFichaDgt;
	}
	
	
}
