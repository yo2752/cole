package trafico.acciones;

import general.acciones.ActionBase;
import hibernate.dao.trafico.TramiteAnuntisDAO;
import hibernate.dao.trafico.TramiteTrafSolInfoDAO;
import hibernate.dao.trafico.VehiculoDAO;
import hibernate.entities.trafico.TramiteAnuntis;
import hibernate.entities.trafico.TramiteTrafSolInfo;
import hibernate.entities.trafico.filters.TramiteAnuntisFilter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;

import trafico.beans.TramiteTraficoBean;
import trafico.beans.VehiculoBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class MatriculasSolInfoAction extends ActionBase {

	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(MatriculasSolInfoAction.class);

	private String numExpediente;
	private boolean solInfo;
	private boolean anuntis;
	ArrayList<TramiteTraficoBean> listaBeansPantalla;

	private static final String JSP = "jspMatrSolInfo";
	private static final String LISTA_TRAMITE_ANUNTIS = "listaTramiteAnuntis";
	private static final String LISTA_TRAMITE_SOL_INFO = "listaTramiteTrafSolInfo";
	private static final String NUM_EXPEDIENTE = "numExpediente";

	private String bastidorNew;
	private String matriculaNew;
	private String identificador;

	public String inicio() {
		return JSP;
	}

	public String buscar() throws Exception {
		try{
			solInfo = false;
			anuntis = false;
			List<TramiteAnuntis> listaTramiteAnuntis = null;
			List<TramiteTrafSolInfo> listaTramiteTrafSolInfo = null;
			if (numExpediente == null || numExpediente.equals("")) {
				addActionError("El campo número de expediente es requerido");
				return JSP;
			}
			super.getSession().put(NUM_EXPEDIENTE, numExpediente);
			Long longNumExpediente = null;
			try {
				longNumExpediente = new Long(numExpediente);
			} catch (NumberFormatException nfe) {
				addActionError("Introduzca un número de expediente válido");
				return JSP;
			}
			TramiteTrafSolInfoDAO tramiteTrafSolInfoDAO = new TramiteTrafSolInfoDAO();
			listaTramiteTrafSolInfo = tramiteTrafSolInfoDAO.getList(longNumExpediente);
			if (listaTramiteTrafSolInfo == null || listaTramiteTrafSolInfo.isEmpty()) {
				solInfo = false;
			} else {
				solInfo = true;
				super.getSession().put(LISTA_TRAMITE_SOL_INFO, listaTramiteTrafSolInfo);
			}

			if (!solInfo) {
				TramiteAnuntisFilter tramiteAnuntisFilter = new TramiteAnuntisFilter();
				tramiteAnuntisFilter.setNumExpediente(longNumExpediente);
				TramiteAnuntisDAO tramiteAnuntisDAO = new TramiteAnuntisDAO();
				listaTramiteAnuntis = tramiteAnuntisDAO.list(tramiteAnuntisFilter, new String[]{"tasa"});
				if (listaTramiteAnuntis == null || listaTramiteAnuntis.isEmpty()) {
					anuntis = false;
				} else {
					anuntis = true;
					super.getSession().put(LISTA_TRAMITE_ANUNTIS, listaTramiteAnuntis);
				}
			}
			if (!solInfo && !anuntis) {
				addActionError("No existe un trámite de solicitud de información con ese número de expediente");
				return JSP;
			}
			actualizarListaBeansPantalla();
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
		}
		return JSP;
	}

	public String modificar() {
		try {
			if (matriculaNew != null) {
				matriculaNew = matriculaNew.toUpperCase();
			}
			if (bastidorNew != null) {
				bastidorNew = bastidorNew.toUpperCase();
			}
			List<TramiteAnuntis> listaTramiteAnuntis = null;
			List<TramiteTrafSolInfo> listaTramiteTrafSolInfo = null;
			if (anuntis) {
				listaTramiteAnuntis = (List<TramiteAnuntis>) super.getSession().get(LISTA_TRAMITE_ANUNTIS);
				for (TramiteAnuntis tramiteAnuntis : listaTramiteAnuntis) {
					if (tramiteAnuntis.getMatricula().equals(identificador)) {
						TramiteAnuntisDAO tramiteAnuntisDao = new TramiteAnuntisDAO();
						tramiteAnuntis.setMatricula(matriculaNew);
						tramiteAnuntisDao.saveOrUpdate(tramiteAnuntis);
						addActionMessage("Modificado");
						actualizarListaBeansPantalla();
						break;
					}
				}
			} else if (solInfo) {
				listaTramiteTrafSolInfo = (List<TramiteTrafSolInfo>) super.getSession().get(LISTA_TRAMITE_SOL_INFO);
				boolean modificar = false;
				for (TramiteTrafSolInfo tramiteTrafSolInfo : listaTramiteTrafSolInfo) {
					modificar = false;
					if ((tramiteTrafSolInfo.getVehiculo().getMatricula() != null && tramiteTrafSolInfo.getVehiculo().getMatricula().equalsIgnoreCase(identificador))
							|| (tramiteTrafSolInfo.getVehiculo().getBastidor() != null && tramiteTrafSolInfo.getVehiculo().getBastidor().equalsIgnoreCase(identificador))) {
						tramiteTrafSolInfo.getVehiculo().setMatricula(matriculaNew);
						tramiteTrafSolInfo.getVehiculo().setBastidor(bastidorNew);
						modificar = true;
					}
					if (modificar) {
						VehiculoDAO vehiculoDao = new VehiculoDAO();
						vehiculoDao.actualizar(tramiteTrafSolInfo.getVehiculo());
						addActionMessage("Modificado");
						actualizarListaBeansPantalla();
						break;
					}
				}
			}
		} catch (Exception ex) {
			log.error(ex);
			addActionError(ex.toString());
		}
		return JSP;
	}

	private void actualizarListaBeansPantalla() throws Exception {
		List<TramiteAnuntis> listaTramiteAnuntis = null;
		List<TramiteTrafSolInfo> listaTramiteTrafSolInfo = null;
		listaBeansPantalla = new ArrayList<>();
		String numExpedienteSession = (String) super.getSession().get(NUM_EXPEDIENTE);
		if (solInfo) {
			listaTramiteTrafSolInfo = (List<TramiteTrafSolInfo>) super.getSession().get(LISTA_TRAMITE_SOL_INFO);
			for (TramiteTrafSolInfo tramiteTrafSolInfo : listaTramiteTrafSolInfo) {
				TramiteTraficoBean tramiteTraficoBean = new TramiteTraficoBean();
				tramiteTraficoBean.setNumExpediente(new BigDecimal(numExpedienteSession));
				if (tramiteTrafSolInfo.getTasa() != null) {
					Tasa tasa = new Tasa();
					tasa.setCodigoTasa(tramiteTrafSolInfo.getTasa().getCodigoTasa());
					tramiteTraficoBean.setTasa(tasa);
				}
				tramiteTraficoBean.setVehiculo(new VehiculoBean());
				tramiteTraficoBean.getVehiculo().setMatricula(tramiteTrafSolInfo.getVehiculo().getMatricula());
				tramiteTraficoBean.getVehiculo().setBastidor(tramiteTrafSolInfo.getVehiculo().getBastidor());
				tramiteTraficoBean.setReferenciaPropia("solInfo");
				listaBeansPantalla.add(tramiteTraficoBean);
			}
		}
		if (anuntis) {
			listaTramiteAnuntis = (List<TramiteAnuntis>) super.getSession().get(LISTA_TRAMITE_ANUNTIS);
			for (TramiteAnuntis tramiteAnuntis : listaTramiteAnuntis) {
				TramiteTraficoBean tramiteTraficoBean = new TramiteTraficoBean();
				tramiteTraficoBean.setNumExpediente(new BigDecimal(numExpedienteSession));
				if (tramiteAnuntis.getTasa() != null) {
					Tasa tasa = new Tasa();
					tasa.setCodigoTasa(tramiteAnuntis.getTasa().getCodigoTasa());
					tramiteTraficoBean.setTasa(tasa);
				}
				tramiteTraficoBean.setVehiculo(new VehiculoBean());
				tramiteTraficoBean.getVehiculo().setMatricula(tramiteAnuntis.getMatricula());
				tramiteTraficoBean.setReferenciaPropia("anuntis");
				listaBeansPantalla.add(tramiteTraficoBean);
			}
		}
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public boolean isSolInfo() {
		return solInfo;
	}

	public void setSolInfo(boolean solInfo) {
		this.solInfo = solInfo;
	}

	public boolean isAnuntis() {
		return anuntis;
	}

	public void setAnuntis(boolean anuntis) {
		this.anuntis = anuntis;
	}

	public ArrayList<TramiteTraficoBean> getListaBeansPantalla() {
		return listaBeansPantalla;
	}

	public void setListaBeansPantalla(ArrayList<TramiteTraficoBean> listaBeansPantalla) {
		this.listaBeansPantalla = listaBeansPantalla;
	}

	public String getBastidorNew() {
		return bastidorNew;
	}

	public void setBastidorNew(String bastidorNew) {
		this.bastidorNew = bastidorNew;
	}

	public String getMatriculaNew() {
		return matriculaNew;
	}

	public void setMatriculaNew(String matriculaNew) {
		this.matriculaNew = matriculaNew;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

}