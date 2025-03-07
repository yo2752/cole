package org.icogam.sanciones.actions;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.icogam.sanciones.DTO.SancionDto;
import org.icogam.sanciones.ModeloImpl.ModeloSancionImpl;
import org.icogam.sanciones.Utiles.EstadoSancion;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloPersona;
import general.acciones.ActionBase;
import hibernate.entities.general.Contrato;
import oegam.constantes.ConstantesPQ;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.DescontarCreditosExcepcion;

public class AltaSancionAction extends ActionBase implements SessionAware {
	
	private static final long serialVersionUID = -1883144374608563310L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaSancionAction.class);
	
	private String TipoPermisos;
	private SancionDto sancionDto;
	private String idSancion;
	
	private ModeloSancionImpl modeloSancion= new ModeloSancionImpl();
	private ModeloCreditosTrafico modeloCreditosTrafico;

	@Autowired
	private ServicioCreditoFacturado servicioCreditoFacturado;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private Map <String,Object> session;
	
	private final String COBRAR_CREDITOS="si"; 
	
	public String inicio() throws Exception {
		if(utilesColegiado.tienePermisoAdmin()){
			TipoPermisos = UtilesColegiado.PERMISO_ADMINISTRACION;
		}
		else{
			TipoPermisos = "GESTOR";
		}
		return SUCCESS;
	}
	
	public String limpiarCampos(){
		sancionDto = new SancionDto();
		return SUCCESS;
	}
	
	private boolean validar(){
		
		if(sancionDto.getEstadoSancion() != null && EstadoSancion.FINALIZADO.getValorEnum().equals(sancionDto.getEstadoSancion().toString())){
			addActionError("No se puede modificar una sanción en estado FINALIZADA");
			return false;
		}
		
		if (sancionDto.getFechaPresentacion().getAnio().equals("") || sancionDto.getFechaPresentacion().getMes().equals("") ||
			sancionDto.getFechaPresentacion().getDia().equals("")){
				addActionError("Se debe introducir una fecha completa");
				return false;
		}
		Fecha hoy = utilesFecha.getFechaActual();
		try {
			if( !utilesFecha.esFechaLaborableConsiderandoFestivos(sancionDto.getFechaPresentacion()) ||
					utilesFecha.compararFechaMayor(hoy, sancionDto.getFechaPresentacion())==1) {
				addActionError("Debe seleccionar una Fecha de Presentación laborable igual o posterior a la fecha actual");
				return false;
			}
		} catch (ParseException e) {
			log.error("Error al parsear la fecha",e);
			addActionError("Debe seleccionar una Fecha de Presentación laborable igual o posterior a la fecha actual");
			return false;
		} catch (OegamExcepcion e) {
			log.error("Error al parsear la fecha",e);
			addActionError("Debe seleccionar una Fecha de Presentación laborable igual o posterior a la fecha actual");
			return false;
		}
		
		if(sancionDto.getApellidos()==null || sancionDto.getApellidos().equals("")){
			addActionError("No se ha rellenado los apellidos o razón social.");
			return false;
		}
		
		if(sancionDto.getDni()==null || sancionDto.getDni().equals("")){
			addActionError("No se ha rellenado el DNI.");
			return false;
		} else {
			BigDecimal resultValida = NIFValidator.validarNif(sancionDto.getDni());
			if (resultValida == null || resultValida.intValue() <= 0) {
				addActionError("DNI no valido.");
				return false;
			}
		}
		
		if(sancionDto.getMotivo() == null || sancionDto.getMotivo().equals(-1)){
			addActionError("Se debe de elegir un motivo");
			return false;
		}
		
		return true;
	}
	
	public String guardar(){
		
		if (!utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_SANCION_DOCUMENTOS_ALTA)) {
			addActionError("No tiene permiso para realizar una peticion.");
			return SUCCESS;
		}
		
		if (!validar()){
			return SUCCESS;
		}
		//Si es un alta nuevo se descuentan creditos.
		if(!utilesColegiado.tienePermisoAdmin() && sancionDto.getIdSancion()==null && gestorPropiedades.valorPropertie("cobrar.sanciones").equals(COBRAR_CREDITOS)){
			try{
				descontarCreditos(utilesColegiado.getIdContratoSessionBigDecimal().toString(), utilesColegiado.getIdUsuarioSessionBigDecimal());
			}catch(DescontarCreditosExcepcion e){
				addActionError("No dispone de suficientes créditos para poder dar de alta la sanción.");
				log.error("Se ha producido un error al descontar creditos: ", e);
				return SUCCESS;
			}
		}
		
		if(hasErrors()){
			return ERROR;
		}
		
		if(sancionDto.getIdSancion()!=null){
			try {
				sancionDto= modeloSancion.actualizar(sancionDto, null);
				addActionMessage("Sanción actualizada");
			} catch (ParseException e) {
				log.error(e);
				addActionError("Sanción no actualizada");
			}
		}
		else{
			
			sancionDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			
			try {
				sancionDto= modeloSancion.guardar(sancionDto);
				addActionMessage("Sanción guardada");
			} catch (ParseException e) {
				log.error("Error de parseado");
				Contrato contrato = new Contrato();
				contrato.setIdContrato(utilesColegiado.getIdContratoSession());
				try{
					if (sancionDto != null && sancionDto.getIdSancion() != null) {
						servicioCreditoFacturado.anotarGasto(1, ConceptoCreditoFacturado.DSAN, contrato.getIdContrato(), TipoTramiteTrafico.Sancion.getValorEnum(), sancionDto.getIdSancion().toString());
					} else {
						servicioCreditoFacturado.anotarGasto(1, ConceptoCreditoFacturado.DSAN, contrato.getIdContrato(), TipoTramiteTrafico.Sancion.getValorEnum());
					}
				}catch(Exception eg){
					log.error("No se pudo trazar la devolucion de creditos");
				}
				addActionError("Sanción no guardada");
			} catch (OegamExcepcion e) {
				log.error("Error número maximo de sanciones");
				Contrato contrato = new Contrato();
				contrato.setIdContrato(utilesColegiado.getIdContratoSession());
				try{
					if (sancionDto != null && sancionDto.getIdSancion() != null) {
						servicioCreditoFacturado.anotarGasto(1, ConceptoCreditoFacturado.DSAN, contrato.getIdContrato(), TipoTramiteTrafico.Sancion.getValorEnum(), sancionDto.getIdSancion().toString());
					} else {
						servicioCreditoFacturado.anotarGasto(1, ConceptoCreditoFacturado.DSAN, contrato.getIdContrato(), TipoTramiteTrafico.Sancion.getValorEnum());
					}
				}catch(Exception eg){
					log.error("No se pudo trazar la devolucion de creditos");
				}
				addActionError(e.getMessage());
			}
		}
		
		return SUCCESS;
	}
	
	public void descontarCreditos(String contrato, BigDecimal idUsuario) throws DescontarCreditosExcepcion{
		HashMap<String, Object> resultado = getModeloCreditosTrafico().descontarCreditos(
				contrato,
				utiles.convertirIntegerABigDecimal(1),
				TipoTramiteTrafico.Sancion.getValorEnum(),idUsuario);

		ResultBean resultadoProcedimiento =(ResultBean) resultado.get(ConstantesPQ.RESULTBEAN);

		if(resultadoProcedimiento.getError()){
			String mensajeError = "Error al descontar créditos de la operación";
			resultadoProcedimiento.setError(true);
			log.error(mensajeError);
			throw new DescontarCreditosExcepcion(resultadoProcedimiento.getMensaje());
		}

		try{
			if (sancionDto != null && sancionDto.getIdSancion() != null) {
				servicioCreditoFacturado.anotarGasto(1, ConceptoCreditoFacturado.SAN, Long.valueOf(contrato), TipoTramiteTrafico.Sancion.getValorEnum(), sancionDto.getIdSancion().toString());
			} else {
				servicioCreditoFacturado.anotarGasto(1, ConceptoCreditoFacturado.SAN, Long.valueOf(contrato), TipoTramiteTrafico.Sancion.getValorEnum());
			}
		}catch(Exception e){
			log.error("Se ha producido un error al guardar historico de creditos para la sanción dada de alta el: " + utilesFecha.getFechaHoy() + " para el contrato: " +  utilesColegiado.getIdContratoSession() , e);
		}
	}

	public String modificar(){
		
		if(idSancion!=null && !idSancion.equals("")){
			sancionDto = new SancionDto();
			try {
				sancionDto.setIdSancion(Integer.parseInt(idSancion));
				sancionDto = modeloSancion.getSancion(sancionDto, null);
			} catch (NumberFormatException e) {
				log.error("Error en el id de Sancion");
				addActionError("Error al recuperar la Sancion");
			} catch (Exception e) {
				log.error("Error al recuperar la Sancion");
				addActionError("Error al recuperar la Sancion");
			}
			
			
		}
		
		
		return SUCCESS;
	}
	
	public String getDetallePersona(){
		Persona persona = ModeloPersona.obtenerDetallePersonaCompleto(sancionDto.getDni(), utilesColegiado.getNumColegiadoSession());
		sancionDto.setNombre(persona.getNombre());
		StringBuffer apellidos = persona.getApellido1RazonSocial()!=null?new StringBuffer(persona.getApellido1RazonSocial()+" "):new StringBuffer("");
		apellidos.append(persona.getApellido2()!=null?new StringBuffer(persona.getApellido2()):new StringBuffer(""));
		sancionDto.setApellidos(apellidos.toString());
		return SUCCESS;
	}
	
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public String getTipoPermisos() {
		return TipoPermisos;
	}

	public void setTipoPermisos(String tipoPermisos) {
		TipoPermisos = tipoPermisos;
	}

	public SancionDto getSancionDto() {
		return sancionDto;
	}

	public void setSancionDto(SancionDto sancionDto) {
		this.sancionDto = sancionDto;
	}

	public String getIdSancion() {
		return idSancion;
	}

	public void setIdSancion(String idSancion) {
		this.idSancion = idSancion;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */
	// FIXME Si se implementa inyeccion por Spring quitar los ifs de los getters
	/* *********************************************************************** */
	
	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}
	
	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}

}
