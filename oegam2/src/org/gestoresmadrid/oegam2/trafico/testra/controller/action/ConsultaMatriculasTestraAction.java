package org.gestoresmadrid.oegam2.trafico.testra.controller.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.trafico.testra.model.vo.ConsultaTestraVO;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.trafico.testra.service.ServicioConsultasTestra;
import org.gestoresmadrid.oegam2comun.trafico.testra.service.impl.ServicioConsultasTestraImpl;
import org.gestoresmadrid.oegam2comun.trafico.testra.view.beans.ConsultaTestraViewBean;
import org.gestoresmadrid.oegam2comun.trafico.testra.view.dto.ConsultaTestraDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaMatriculasTestraAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 9148160786207248346L;
	private static final String TOKEN_SEPARATOR = ",";
	private static final String IMPORTAR_TESTRA = "importarTestra";

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ConsultaMatriculasTestraAction.class);

	@Resource
	private ModelPagination modeloConsultaTestraPaginatedImpl;

	@Autowired
	private ServicioConsultasTestra servicioConsultasTestra;

	@Autowired
	UtilesFecha utilesFecha;
	
	@Autowired
	private Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private ConsultaTestraViewBean testraViewBean;

	// Upload para importacion de compras
	private File ficheroTestra;

	List<ConsultaTestraDto> listaConsulta;

	private Short activo;
	private String idConsultas;
	
	

	public String resultado() {
		return SUCCESS;
		/*if(utiles.esUsuarioOegam3(utilesColegiado.getIdUsuarioSessionBigDecimal().toString())) {
			return "redireccionOegam3";
		}else {
			return SUCCESS;
		}*/
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
		/*if(utiles.esUsuarioOegam3(utilesColegiado.getNumColegiadoSession())) {
			return "redireccionOegam3";
		}else {
			return SUCCESS;
		}*/
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloConsultaTestraPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return LOG;
	}

	/**
	 * Realiza el cambio de estado de las compras
	 * @return
	 * @throws Throwable
	 */
	public String activacion() throws Throwable {
		ConsultaTestraVO consulta =null;
		for (String idConsulta : idConsultas.split(TOKEN_SEPARATOR)) {
			boolean resultado;
			try {
				consulta = servicioConsultasTestra.getConsultaByID(Integer.parseInt(idConsulta));
				resultado = servicioConsultasTestra.actualizarActivacion(activo, new Long(idConsulta));
			}catch(RuntimeException r) {
				LOG.error("Error actualizando la consulta con dato: " + consulta.getDato(), r);
				resultado = false;
			}
			if (resultado) {
				addActionMessage("El dato " + consulta.getDato() + "  ha sido actualizado a " + EstadoVehiculo.convertirTexto(activo));
			} else {
				addActionError("No se pudo cambiar el estado de " + consulta.getDato() + ".");
			}
		}

		return navegar();
	}

	public String inicioImportar() {
		return IMPORTAR_TESTRA;
	}

	public String lecturaImportacion() {
		if (!utiles.esNombreFicheroValido(ficheroTestra.getName())) {
			addActionError("El fichero añadido debe tener un nombre correcto");
		} else {
			listaConsulta = servicioConsultasTestra.lecturaFichero(ficheroTestra);
			if (listaConsulta == null || listaConsulta.isEmpty()) {
				addActionError("No se ha podido recuperar ningún dato del fichero");
			}
		}
		return IMPORTAR_TESTRA;
	}

	public String altaImportacion() {
		boolean alguno = false;
		if (listaConsulta != null) {
			if (!utilesColegiado.tienePermisoAdmin()) {
				testraViewBean = new ConsultaTestraViewBean();
				testraViewBean.setNumColegiado(utilesColegiado.getIdContratoSessionBigDecimal().toString());
			}
			if (testraViewBean == null || testraViewBean.getNumColegiado() == null || testraViewBean.getNumColegiado().isEmpty()) {
				addActionError("Debe seleccionar un número de colegiado para la importación");
			} else {
				for (ConsultaTestraDto consulta : listaConsulta) {
					if (consulta.getId() == null && EstadoVehiculo.Activo.getValorEnum().equals(consulta.getActivo())) {
						/*consulta =*/ guardar(consulta, utilesColegiado.getNumColegiadoByIdContrato(new BigDecimal(Integer.parseInt(testraViewBean.getNumColegiado()))));
						alguno = true;
					}
				}	
			}
		}
		if (!alguno) {
			addActionError("No se ha importado ningun dato para consultar en el servicio TESTRA");
		}
		return IMPORTAR_TESTRA;
	}

	private  void guardar(ConsultaTestraDto consulta, String numColegiado) {
		ResultBean result = null;
		try {
			consulta.setNumColegiado(numColegiado);
			result = servicioConsultasTestra.guardarConsulta(consulta);
		} catch (RuntimeException r) {
			LOG.error("Error al guardar consulta testra", r);
		}
		if (result == null) {
			addActionError("Ocurrió un error al guardar los datos");

		} else if (result.getError()) {
			if (result.getListaMensajes() != null) {
				for (String mensaje : result.getListaMensajes()) {
					addActionError(consulta.getDato() + ": " + mensaje);
				}
			}
		} else {
			consulta = (ConsultaTestraDto) result.getAttachment(ServicioConsultasTestraImpl.KEY_RESULT);
			addActionMessage(consulta.getDato() + ": Guardado correctamente");
		}
		
	}

	@Override
	protected void cargaRestricciones() {
		if (testraViewBean == null) {
			testraViewBean = new ConsultaTestraViewBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			testraViewBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (testraViewBean == null) {
			testraViewBean = new ConsultaTestraViewBean();
		}
		testraViewBean.setActivo(EstadoVehiculo.Activo.getValorEnum());
		testraViewBean.setFechaAlta(utilesFecha.getFechaFracionadaActual());
	}

	@Override
	protected Object getBeanCriterios() {
		return testraViewBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.testraViewBean = (ConsultaTestraViewBean) object;
	}

	public ConsultaTestraViewBean getTestraViewBean() {
		return testraViewBean;
	}

	public void setTestraViewBean(ConsultaTestraViewBean testraViewBean) {
		this.testraViewBean = testraViewBean;
	}

	public Short getActivo() {
		return activo;
	}

	public void setActivo(Short activo) {
		this.activo = activo;
	}

	public String getIdConsultas() {
		return idConsultas;
	}

	public void setIdConsultas(String idConsultas) {
		this.idConsultas = idConsultas;
	}

	public File getFicheroTestra() {
		return ficheroTestra;
	}

	public void setFicheroTestra(File ficheroTestra) {
		this.ficheroTestra = ficheroTestra;
	}

	public List<ConsultaTestraDto> getListaConsulta() {
		return listaConsulta;
	}

	public void setListaConsulta(List<ConsultaTestraDto> listaConsulta) {
		this.listaConsulta = listaConsulta;
	}

	
}
