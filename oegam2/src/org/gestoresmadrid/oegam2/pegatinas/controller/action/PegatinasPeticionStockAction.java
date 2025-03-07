package org.gestoresmadrid.oegam2.pegatinas.controller.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasPeticionEvolucionVO;
import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasStockVO;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioPegatinasTransactional;
import org.gestoresmadrid.oegam2comun.pegatinas.model.service.ServicioPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.ConstantesPegatinas;
import org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados.EstadoPeticiones;
import org.gestoresmadrid.oegam2comun.pegatinas.view.beans.PegatinasStockPeticionesBean;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class PegatinasPeticionStockAction extends AbstractPaginatedListAction implements SessionAware {

	private static final long serialVersionUID = 7068221511089304431L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(PegatinasPeticionStockAction.class);

	@Resource
	private ModelPagination modeloPegatinasPeticionStockPaginatedImpl;

	@Autowired
	private ServicioPegatinasTransactional servicioPegatinasTransactional;

	@Autowired
	private ServicioPegatinas servicioPegatinas;

	private PegatinasStockPeticionesBean pegatinasStockPeticionesBean;

	private String idStock;

	private String datos;

	private String idPeticion;

	private List<PegatinasPeticionEvolucionVO> listaRespuestas;

	@Override
	public String inicio() {
		getSession().remove(getKeyCriteriosSession());
		if (pegatinasStockPeticionesBean == null) {
			pegatinasStockPeticionesBean = new PegatinasStockPeticionesBean();
			pegatinasStockPeticionesBean.setIdStock(Integer.parseInt(idStock));
		}
		return super.inicio();
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloPegatinasPeticionStockPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	public String buscar() {
		if (pegatinasStockPeticionesBean == null) {
			pegatinasStockPeticionesBean = new PegatinasStockPeticionesBean();
		}
		pegatinasStockPeticionesBean
				.setIdStock(((PegatinasStockPeticionesBean) getSession().get(getKeyCriteriosSession())).getIdStock());
		if (pegatinasStockPeticionesBean != null && EstadoPeticiones.TODOS.getValorEnum().equals(pegatinasStockPeticionesBean.getEstado())) {
			pegatinasStockPeticionesBean.setEstado(null);
		}
		return super.buscar();
	}

	@Override
	protected void cargaRestricciones() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void cargarFiltroInicial() {
		if (pegatinasStockPeticionesBean == null) {
			pegatinasStockPeticionesBean = new PegatinasStockPeticionesBean();
			pegatinasStockPeticionesBean.setIdStock(Integer.parseInt(idStock));
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return pegatinasStockPeticionesBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.pegatinasStockPeticionesBean = (PegatinasStockPeticionesBean) object;
	}

	public String getDatos() {
		return datos;
	}

	public void setDatos(String datos) {
		this.datos = datos;
	}

	public String getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(String idPeticion) {
		this.idPeticion = idPeticion;
	}

	public List<PegatinasPeticionEvolucionVO> getListaRespuestas() {
		return listaRespuestas;
	}

	public void setListaRespuestas(List<PegatinasPeticionEvolucionVO> listaRespuestas) {
		this.listaRespuestas = listaRespuestas;
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

	public String confirmarRecepcion() {
		String[] datosPeticion = datos.split("-");
		int size = datosPeticion.length;
		String[] identificadores = new String[size];
		String[] idsPeticion = new String[size];
		String[] idStocks = new String[size];
		boolean estadoNotificacion = true;
		for (int i = 0; i < size; i++) {
			String[] valores = datosPeticion[i].split(",");
			String identificador = valores[0].trim();
			String id = valores[2].trim();
			String idStock = valores[3].trim();
			identificadores[i] = identificador;
			idsPeticion[i] = id;
			idStocks[i] = idStock;
			if (!valores[1].trim().equals("1")) {
				estadoNotificacion = false;
				break;
			}
		}
		if (!estadoNotificacion) {
			addActionError("Sólo es posible confirmar la recepción de peticiones en estado 'Pendiente de Recepción'");
		} else {
			for (int i = 0; i < identificadores.length; i++) {
				servicioPegatinasTransactional.cambiarEstadoPeticionById(idsPeticion[i],
						EstadoPeticiones.FINALIZADO_PENDIENTE.getNombreEnum(),
						EstadoPeticiones.FINALIZADO_PENDIENTE.getValorEnum());
				servicioPegatinasTransactional.guardarPegatinasPeticionEvolucion(idsPeticion[i],
						EstadoPeticiones.FINALIZADO_PENDIENTE.getNombreEnum(), ConstantesPegatinas.CAMBIO_ESTADO);

				ResultBean result = null;
				PegatinasStockVO pegStock = servicioPegatinasTransactional
						.getStockByIdStock(Integer.valueOf(idStocks[i]));
				result = servicioPegatinas.creaSolicitudRecepcionStock(identificadores[i], pegStock.getJefatura());
				if (!result.getError()) {
					addActionMessage(
							"Se ha creado la solicitud de confirmación de stock para la petición con el identificador: "
									+ identificadores[i]);
					log.info("Se ha creado la solicitud de confirmación de stock para la petición con identificador: "
							+ identificadores[i]);
				} else {
					addActionError(result.getMensaje());
					log.error("Se ha producido un error a la hora de confirmar el stock para la petición: "
							+ identificadores[i]);
				}
			}
		}

		return buscar();
	}

	public String anularPeticion() {
		String[] datosPeticion = datos.split("-");
		int size = datosPeticion.length;
		String[] identificadores = new String[size];
		String[] idsPeticion = new String[size];
		String[] idStocks = new String[size];
		boolean estadoNotificacion = true;
		for (int i = 0; i < size; i++) {
			String[] valores = datosPeticion[i].split(",");
			String identificador = valores[0].trim();
			String id = valores[2].trim();
			String idStock = valores[3].trim();
			identificadores[i] = identificador;
			idsPeticion[i] = id;
			idStocks[i] = idStock;
			if (!(valores[1].trim().equals("0") || valores[1].trim().equals("1"))) {
				estadoNotificacion = false;
				break;
			}
		}
		if (!estadoNotificacion) {
			addActionError(
					"Sólo es posible anular las peticiones en estado 'Pendiente de Recepción' o 'Solicitud Creada'");
		} else {
			for (int i = 0; i < datosPeticion.length; i++) {
				servicioPegatinasTransactional.cambiarEstadoPeticionById(idsPeticion[i],
						EstadoPeticiones.ANULADO_PENDIENTE.getNombreEnum(),
						EstadoPeticiones.ANULADO_PENDIENTE.getValorEnum());
				servicioPegatinasTransactional.guardarPegatinasPeticionEvolucion(idsPeticion[i],
						EstadoPeticiones.ANULADO_PENDIENTE.getNombreEnum(), ConstantesPegatinas.CAMBIO_ESTADO);

				ResultBean result = null;
				PegatinasStockVO pegStock = servicioPegatinasTransactional
						.getStockByIdStock(Integer.valueOf(idStocks[i]));
				result = servicioPegatinas.creaSolicitudBajaPeticionStock(identificadores[i], pegStock.getJefatura());
				if (!result.getError()) {
					addActionMessage("Se ha creado la solicitud de anulación de stock para la petición con id: "
							+ identificadores[i]);
					log.info("Se ha creado la solicitud de anulación de stock para la petición con id: "
							+ identificadores[i]);
				} else {
					addActionError(result.getMensaje());
					log.error("Se ha producido un error a la hora de anular el stock para la petición: "
							+ identificadores[i]);
				}
			}
		}
		return buscar();
	}

	public String verEvolucion() {
		listaRespuestas = servicioPegatinasTransactional
				.getPegatinasPeticionEvolucionByIdPeticion(Integer.parseInt(idPeticion));
		int size = listaRespuestas.size();
		if (size == 0) {
			addActionError("No se ha realizado ninguna acción con esta petición");
			return buscar();
		}
		return "evolucion";
	}

}