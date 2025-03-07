package org.gestoresmadrid.oegam2.personas.controller.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.Estado;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.personas.model.enumerados.EstadoPersonaDireccion;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.view.beans.SeleccionarDireccionBean;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.view.dto.EvolucionPersonaDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import escrituras.utiles.UtilesVista;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class DetallePersonaAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = 7985208940834131180L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(DetallePersonaAction.class);

	private static final String EXPEDIENTE_DIRECCION = "expedienteDireccion";

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioEvolucionPersona servicioEvolucionPersona;

	@Resource
	private ModelPagination modeloPersonaDireccionPaginated;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	private String nif;
	private String numColegiado;
	private Long idDireccion;

	private boolean baja;

	private Long principal;
	private ArrayList<Long> fusion;

	private PersonaDto personaModificar;

	private SeleccionarDireccionBean seleccionarDireccionBean;

	private List<IntervinienteTraficoDto> listaExpedientesDireccion;

	private DireccionDto direccionExpediente;

	public String detalle() throws Throwable {
		if (nif != null && numColegiado != null) {
			ResultBean result = servicioPersona.buscarPersona(nif, numColegiado);
			if (!result.getError()) {
				personaModificar = (PersonaDto) result.getAttachment(ServicioPersona.PERSONA);
				DireccionDto direccionDto = servicioPersona.getDireccionActiva(personaModificar.getNif(), personaModificar.getNumColegiado());
				if (direccionDto != null) {
					personaModificar.setDireccionDto(direccionDto);
				}
			}
		}
		inicio();
		return SUCCESS;
	}

	public String modificacion() throws Throwable {
		if (!utilesColegiado.tienePermisoMantenimientoPersonas()) {
			addActionError("No tiene permiso para realizar esta acción.");
			return SUCCESS;
		}

		if (isBaja()) {
			personaModificar.setEstado(Estado.Deshabilitado.getValorEnum().toString());
		} else {
			personaModificar.setEstado(Estado.Habilitado.getValorEnum().toString());
		}

		nifCaducado();

		ResultBean result = servicioPersona.guardarMantenimientoPersona(personaModificar, null, ServicioPersona.TIPO_TRAMITE_MANTENIMIENTO, utilesColegiado.getIdUsuarioSessionBigDecimal(),
				ServicioPersona.CONVERSION_PERSONA_MANTENIMIENTO);

		if (result.getError()) {
			String mensajeError = "Se ha producido un error al guardar la persona: ";
			if (result.getListaMensajes() != null && result.getListaMensajes().size() > 0) {
				for (String mensaje : result.getListaMensajes()) {
					mensajeError += " - " + mensaje;
				}
			} else {
				mensajeError += result.getMensaje();
			}
			addActionError(mensajeError);
		} else {
			addActionMessage("La persona se ha guardado correctamente. " + (null != result.getMensaje() ? result.getMensaje() : ""));
			detalle();
		}
		return SUCCESS;
	}

	private void nifCaducado() throws ParseException {
		String resultadoCaducidadNIF = "";
		Fecha fechaActual = utilesFecha.getFechaActual();
		java.util.Date fechaAdvertencia = utilesFecha.getDate((utilesFecha.sumaDias(fechaActual, "-30")));
		if (personaModificar != null && personaModificar.getFechaCaducidadNif() != null && personaModificar.getFechaCaducidadNif().getDate() != null
				&& personaModificar.getFechaCaducidadNif().getDate().after(fechaAdvertencia) && personaModificar.getFechaCaducidadNif().getDate().before(fechaActual.getDate())) {
			resultadoCaducidadNIF = "NIF de la persona próximo a caducar.";
			if (personaModificar.getCodigoMandato() != null) {
				resultadoCaducidadNIF += " Recuerde que esta persona tiene asociado el código de mandato: " + personaModificar.getCodigoMandato();
			}
			addActionMessage(resultadoCaducidadNIF);
		}
	}

	public String listarExpedientes() {
		listaExpedientesDireccion = servicioIntervinienteTrafico.getExpedientesDireccion(nif, numColegiado, idDireccion);

		if (listaExpedientesDireccion != null && listaExpedientesDireccion.size() > 0) {
			direccionExpediente = listaExpedientesDireccion.get(0).getDireccion();
		} else {
			direccionExpediente = servicioDireccion.getDireccionDto(idDireccion);
		}

		direccionExpediente.setNombreProvincia(servicioDireccion.obtenerNombreProvincia(direccionExpediente.getIdProvincia()));
		direccionExpediente.setNombreMunicipio(servicioDireccion.obtenerNombreMunicipio(direccionExpediente.getIdMunicipio(), direccionExpediente.getIdProvincia()));

		return EXPEDIENTE_DIRECCION;
	}

	public String fusionarDireccion() throws Throwable {
		ResultBean result = new ResultBean();
		String idFusionados = "";
		for (Long idDireccionBorrar : fusion) {
			if (idDireccionBorrar.equals(principal)) {
				continue;
			} else {
				if ("".equals(idFusionados)) {
					idFusionados = idDireccionBorrar + "";
				} else {
					idFusionados = idDireccionBorrar + ",";
				}
				result = servicioPersona.eliminarFusionarDireccion(numColegiado, nif, principal, idDireccionBorrar, EstadoPersonaDireccion.Fusionado.getValorEnum());
				if (result.getError()) {
					break;
				}
			}
		}

		if (result.getError()) {
			addActionError("Error al realizar la fusión de las direcciones");
		} else {
			idFusionados = "Fusión direcciones: " + idFusionados + " con " + principal;
			guardarEvolucion(idFusionados);
			addActionMessage("La fusión se ha realizado correctamente. " + idFusionados);
		}

		setFusion(null);
		return detalle();
	}

	public String eliminarDireccion() throws Throwable {
		ResultBean result = new ResultBean();
		String idEliminados = "";
		String mensajeNoEliminados = "";
		for (Long idDireccionEliminar : fusion) {
			result = servicioPersona.eliminarFusionarDireccion(numColegiado, nif, null, idDireccionEliminar, EstadoPersonaDireccion.Eliminado.getValorEnum());
			if (result.getError()) {
				break;
			} else if (result.getMensaje() != null && !"".equals(result.getError())) {
				mensajeNoEliminados = result.getMensaje();
			} else {
				if ("".equals(idEliminados)) {
					idEliminados = idDireccionEliminar + "";
				} else {
					idEliminados = idDireccionEliminar + ",";
				}
			}
		}

		if (result.getError()) {
			addActionError("Error al realizar la eliminación de las direcciones");
		} else if (mensajeNoEliminados != null && !"".equals(mensajeNoEliminados)) {
			if (!"".equals(idEliminados)) {
				idEliminados = "Eliminación de las direcciones: " + idEliminados;
				guardarEvolucion(idEliminados);
				addActionMessage("La eliminación se ha realizado correctamente con errores: " + mensajeNoEliminados);
			} else {
				addActionError(mensajeNoEliminados);
			}
		} else {
			idEliminados = "Eliminación de las direcciones: " + idEliminados;
			guardarEvolucion(idEliminados);
			addActionMessage("La eliminación se ha realizado correctamente. " + idEliminados);
		}

		setFusion(null);
		return detalle();
	}

	public String asignarPrincipal() throws Throwable {
		ResultBean result = new ResultBean();

		servicioPersona.asignarPrincipal(numColegiado, nif, principal);

		if (result.getError()) {
			addActionError("Error al asignar la dirección " + principal + " como principal: " + result.getMensaje());
		} else {
			guardarEvolucion("Dirección " + principal + " asignada como principal");
			addActionMessage("Se ha asignado como principal la dirección: " + principal);
		}

		setFusion(null);
		return detalle();
	}

	private void guardarEvolucion(String textoEvolucion) {
		EvolucionPersonaDto evolucionDto = new EvolucionPersonaDto();
		evolucionDto.setFechaHora(utilesFecha.getFechaHoraActualLEG());
		evolucionDto.setNumColegiado(numColegiado);
		evolucionDto.setTipoTramite(ServicioPersona.TIPO_TRAMITE_MANTENIMIENTO);
		evolucionDto.setNif(nif);
		evolucionDto.setOtros(textoEvolucion);
		UsuarioDto usuario = new UsuarioDto();
		usuario.setIdUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal());
		evolucionDto.setUsuario(usuario);
		evolucionDto.setTipoActuacion(TipoActualizacion.MOD.getNombreEnum());
		servicioEvolucionPersona.guardarEvolucion(evolucionDto);
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected void cargaRestricciones() {
		if (nif != null && !"".equals(nif)) {
			seleccionarDireccionBean.setNif(nif);
		}
		if (numColegiado != null && !"".equals(numColegiado)) {
			seleccionarDireccionBean.setNumColegiado(numColegiado);
		}
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloPersonaDireccionPaginated;
	}

	@Override
	protected Object getBeanCriterios() {
		return seleccionarDireccionBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.seleccionarDireccionBean = (SeleccionarDireccionBean) object;
	}

	@Override
	protected void cargarFiltroInicial() {
		seleccionarDireccionBean = new SeleccionarDireccionBean();
		seleccionarDireccionBean.setNif(nif);
		seleccionarDireccionBean.setNumColegiado(numColegiado);

		ArrayList<Short> estadosDirecciones = new ArrayList<Short>();
		estadosDirecciones.add(EstadoPersonaDireccion.Activo.getValorEnum());
		seleccionarDireccionBean.setEstadosDirecciones(estadosDirecciones);

		setSort("id.idDireccion");
		setDir(GenericDaoImplHibernate.ordenDes);

		setResultadosPorPagina(UtilesVista.RESULTADOS_POR_PAGINA_POR_DEFECTO_DIRECCIONES);
	}

	public ServicioPersona getServicioPersona() {
		return servicioPersona;
	}

	public void setServicioPersona(ServicioPersona servicioPersona) {
		this.servicioPersona = servicioPersona;
	}

	public ServicioDireccion getServicioDireccion() {
		return servicioDireccion;
	}

	public void setServicioDireccion(ServicioDireccion servicioDireccion) {
		this.servicioDireccion = servicioDireccion;
	}

	public ServicioIntervinienteTrafico getServicioIntervinienteTrafico() {
		return servicioIntervinienteTrafico;
	}

	public void setServicioIntervinienteTrafico(ServicioIntervinienteTrafico servicioIntervinienteTrafico) {
		this.servicioIntervinienteTrafico = servicioIntervinienteTrafico;
	}

	public ServicioEvolucionPersona getServicioEvolucionPersona() {
		return servicioEvolucionPersona;
	}

	public void setServicioEvolucionPersona(ServicioEvolucionPersona servicioEvolucionPersona) {
		this.servicioEvolucionPersona = servicioEvolucionPersona;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public PersonaDto getPersonaModificar() {
		return personaModificar;
	}

	public void setPersonaModificar(PersonaDto personaModificar) {
		this.personaModificar = personaModificar;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}

	public SeleccionarDireccionBean getSeleccionarDireccionBean() {
		return seleccionarDireccionBean;
	}

	public void setSeleccionarDireccionBean(SeleccionarDireccionBean seleccionarDireccionBean) {
		this.seleccionarDireccionBean = seleccionarDireccionBean;
	}

	public List<IntervinienteTraficoDto> getListaExpedientesDireccion() {
		return listaExpedientesDireccion;
	}

	public void setListaExpedientesDireccion(List<IntervinienteTraficoDto> listaExpedientesDireccion) {
		this.listaExpedientesDireccion = listaExpedientesDireccion;
	}

	public DireccionDto getDireccionExpediente() {
		return direccionExpediente;
	}

	public void setDireccionExpediente(DireccionDto direccionExpediente) {
		this.direccionExpediente = direccionExpediente;
	}

	public Long getPrincipal() {
		return principal;
	}

	public void setPrincipal(Long principal) {
		this.principal = principal;
	}

	public ArrayList<Long> getFusion() {
		return fusion;
	}

	public void setFusion(ArrayList<Long> fusion) {
		this.fusion = fusion;
	}
}
