package org.gestoresmadrid.oegam2.pegatinas.controller.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasPeticionEvolucionVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockPeticionesVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockVO;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioPegatinasTransactional;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.ConstantesPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.EstadoPeticiones;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.TipoPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.view.beans.PegatinasStockBean;
import org.gestoresmadrid.oegam2comun.pegatinas.view.beans.PegatinasStockPeticionesBean;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.model.service.ServicioEstadisticasJPT;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import escrituras.utiles.UtilesVista;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class GestionPegatinasAction extends AbstractPaginatedListAction implements SessionAware {

	private static final long serialVersionUID = 7068221511089304431L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(GestionPegatinasAction.class);

	private static final String NOMBRE_ACTION ="GestionPegatinas";

	@Autowired
	private ServicioPegatinasTransactional servicioPegatinasTransactional;

	@Autowired
	private ServicioPegatinas servicioPegatinas;

	@Autowired
	private ServicioEstadisticasJPT servicioEstadisticasJPT;

	@Resource
	private ModelPagination modeloGestionPegatinasPaginatedImpl;

	@Autowired
	UtilesColegiado utilesColegiado;

	private PegatinasStockBean pegatinasStockBean;

	private PegatinasStockPeticionesBean pegatinasStockPeticionesBean;

	private String idStock;

	private String jefaturaJpt;

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloGestionPegatinasPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	public String getAction() {
		return NOMBRE_ACTION;
	}

	@Override
	public String buscar() {
		if (pegatinasStockBean != null) {
			if (jefaturaJpt != null){
				pegatinasStockBean.setJefatura(jefaturaJpt);
			}
			if (TipoPegatinas.TODOS.getNombreEnum().equals(pegatinasStockBean.getTipo())) {
				pegatinasStockBean.setTipo(null);
			}
		}
		return super.buscar();
	}

	@Override
	protected void cargaRestricciones() {
		if (!utilesColegiado.getTipoUsuarioAdminJefaturaJpt()) {
			jefaturaJpt = servicioEstadisticasJPT.getJefaturaProvincialPorUsuario(utilesColegiado.getIdUsuarioSession());
		}
	}

	public String popupStock(){
		return "popupStock";
	}

	@Override
	public String inicio() {
		if (!utilesColegiado.getTipoUsuarioAdminJefaturaJpt()) {
			jefaturaJpt = servicioEstadisticasJPT.getJefaturaProvincialPorUsuario(utilesColegiado.getIdUsuarioSession());
		}
		if (pegatinasStockBean == null) {
			pegatinasStockBean = new PegatinasStockBean();
			if (jefaturaJpt != null){
				pegatinasStockBean.setJefatura(jefaturaJpt);
			}
		}
		return super.buscar();
	}

	@Override
	protected void cargarFiltroInicial() {
		setResultadosPorPagina(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_STOCK_PEGATINAS);
		if (pegatinasStockBean == null) {
			pegatinasStockBean = new PegatinasStockBean();
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return pegatinasStockBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.pegatinasStockBean = (PegatinasStockBean) object;
	}

	public PegatinasStockBean getPegatinasStockBean() {
		return pegatinasStockBean;
	}

	public void setPegatinasStockBean(PegatinasStockBean pegatinasStockBean) {
		this.pegatinasStockBean = pegatinasStockBean;
	}

	public PegatinasStockPeticionesBean getPegatinasStockPeticionesBean() {
		return pegatinasStockPeticionesBean;
	}

	public void setPegatinasStockPeticionesBean(PegatinasStockPeticionesBean pegatinasStockPeticionesBean) {
		this.pegatinasStockPeticionesBean = pegatinasStockPeticionesBean;
	}

	public String getIdStock() {
		return idStock;
	}

	public void setIdStock(String idStock) {
		this.idStock = idStock;
	}

	public String getJefaturaJpt() {
		return jefaturaJpt;
	}

	public void setJefaturaJpt(String jefaturaJpt) {
		this.jefaturaJpt = jefaturaJpt;
	}

	public String abrirPopupPedirStock(){
		return "pedirStockPegatina";
	}

	public String pedirStock() {
		pegatinasStockBean = (PegatinasStockBean) getSession().get(getKeyCriteriosSession());
		Date fecha = new Date();
		PegatinasStockVO pegatinasStock  = servicioPegatinasTransactional.getStockByTipoPegatinaJefatura(pegatinasStockPeticionesBean.getTipo(), jefaturaJpt);
		if (pegatinasStock == null) {
			pegatinasStock = new PegatinasStockVO();
			pegatinasStock.setJefatura(jefaturaJpt);
			pegatinasStock.setStock(0);
			pegatinasStock.setTipo(pegatinasStockPeticionesBean.getTipo());
			pegatinasStock.setIdStock(servicioPegatinasTransactional.insertarPegatinasStock(pegatinasStock));
		}
		PegatinasStockPeticionesVO pegatinasStockPeticionVO = new PegatinasStockPeticionesVO();
		pegatinasStockPeticionVO.setIdStock(pegatinasStock.getIdStock());
		pegatinasStockPeticionVO.setNumPegatinas(pegatinasStockPeticionesBean.getNumPegatinas());
		pegatinasStockPeticionVO.setTipo(pegatinasStockPeticionesBean.getTipo());
		pegatinasStockPeticionVO.setEstado(EstadoPeticiones.SOLICITUD_CREADA.getValorEnum());
		pegatinasStockPeticionVO.setDescrEstado(EstadoPeticiones.SOLICITUD_CREADA.getNombreEnum());
		pegatinasStockPeticionVO.setFecha(fecha);
		int idPeticionStock = servicioPegatinasTransactional.guardarPeticionStock(pegatinasStockPeticionVO);
		PegatinasPeticionEvolucionVO pegatinaPeticionEvo = new PegatinasPeticionEvolucionVO();
		pegatinaPeticionEvo.setIdStockPeti(idPeticionStock);
		pegatinaPeticionEvo.setEstado(EstadoPeticiones.SOLICITUD_CREADA.getNombreEnum());
		pegatinaPeticionEvo.setFecha(fecha);
		pegatinaPeticionEvo.setObservaciones(ConstantesPegatinas.CAMBIO_ESTADO);
		servicioPegatinasTransactional.guardarPeticionStockEvo(pegatinaPeticionEvo);
		
		ResultBean result = null;
		result = servicioPegatinas.creaSolicitudPeticionStock(pegatinasStockPeticionesBean.getNumPegatinas(), pegatinasStockPeticionesBean.getTipo(), jefaturaJpt, idPeticionStock);
		if (!result.getError()) {
			addActionMessage("Se ha creado la solicitud de petición de stock");
			log.info("Se ha realizado una petición de stock del tipo:" +pegatinasStockPeticionesBean.getTipo());
		} else {
			addActionError(result.getMensaje());
			log.error("Se ha producido un error al crear una solicitud de stock de tipo:" +pegatinasStockPeticionesBean.getTipo());
		}

		return buscar();
	}

}