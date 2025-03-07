package org.gestoresmadrid.oegam2.notario.controller.action;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.notario.model.service.ServicioNotario;
import org.gestoresmadrid.oegam2comun.notario.view.beans.NotarioFilterBean;
import org.gestoresmadrid.oegam2comun.notario.view.dto.NotarioDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaNotarioAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -2365431863444212812L;
	
	public static final String SUCCESS_PROPIEDAD = "success_propiedad";
	public static final String ALTA_NOTARIO = "altaNotario";	

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaNotarioAction.class);
	private NotarioFilterBean notario;
	private String codigo;
	private boolean esSeleccionadoNotario = false;
	private String codNotario;
	
	private NotarioDto notarioDto;
	
	@Autowired
	private ServicioNotario servicioNotario;

	@Resource
	private ModelPagination modeloNotarioPaginatedImpl;

	@Autowired
	private Utiles utiles;
	
	public String inicio() {
		return actualizarPaginatedList();
	}

	public String cargar() {
		if (codigo != null && !codigo.isEmpty()) {
			esSeleccionadoNotario = true;
		}
		return SUCCESS;
	}
	
	public String alta(){
		return ALTA_NOTARIO;
	}
		
	public String editar(){
				
		NotarioDto notario = servicioNotario.getNotarioPorId(getCodNotario());
		
		if(notario != null){
			notarioDto = new NotarioDto();
			notarioDto.setNombre(notario.getNombre());
			notarioDto.setCodigoNotaria(notario.getCodigoNotaria());
			notarioDto.setCodigoNotario(notario.getCodigoNotario());
			notarioDto.setApellidos(notario.getApellidos());
		}
		else{
			addActionError("Error al editar notario.");
			inicio();
		}
		
		return ALTA_NOTARIO;
	}
			
	public String guardar(){
				
		if (notarioDto !=null){		
			String accion="guardado";
			if (servicioNotario.getNotarioPorId(notarioDto.getCodigoNotario()) != null) {
				accion="actualizado";
			}
			NotarioDto result = servicioNotario.guardarNotario(notarioDto);				
		
			if (result!= null){
				addActionMessage("Notario " +  accion +  " correctamente.");	
			}	
			else{
				addActionError("Error al dar de alta notario.");
			}
		}
		
		return ALTA_NOTARIO;
	}

	@Override
	protected String getResultadoPorDefecto() {
		if(notario != null){
			if (notario.getCodigoNotario()!=null && !notario.getCodigoNotario().isEmpty()){
				notario.setCodigoNotario(notario.getCodigoNotario());
			}
			if (notario.getCodigoNotaria()!=null && !notario.getCodigoNotaria().isEmpty()){
				notario.setCodigoNotaria(utiles.quitarCerosIzquierda(notario.getCodigoNotaria()));
			}
		}
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloNotarioPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (notario != null) {
			if (notario.getNombre() != null && !notario.getNombre().isEmpty()) {
				notario.setNombre("%" + notario.getNombre().toUpperCase() + "%");
			}
			if (notario.getApellidos() != null && !notario.getApellidos().isEmpty()) {
				notario.setApellidos("%" + notario.getApellidos().toUpperCase() + "%");
			}
			if (notario.getCodigoNotario()!=null && !notario.getCodigoNotario().isEmpty()){
				notario.setCodigoNotario(utiles.rellenarCeros(notario.getCodigoNotario(), 7));
			}
			if (notario.getCodigoNotaria()!=null && !notario.getCodigoNotaria().isEmpty()){
				notario.setCodigoNotaria(utiles.rellenarCeros(notario.getCodigoNotaria(), 9));
			}
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		notario = new NotarioFilterBean();

	}

	@Override
	protected Object getBeanCriterios() {
		return notario;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		notario = (NotarioFilterBean) object;
	}

	public NotarioFilterBean getNotario() {
		return notario;
	}

	public void setNotario(NotarioFilterBean notario) {
		this.notario = notario;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean isEsSeleccionadoNotario() {
		return esSeleccionadoNotario;
	}
	
	public NotarioDto getNotarioDto() {
		return notarioDto;
	}

	public void setNotarioDto(NotarioDto notarioDto) {
		this.notarioDto = notarioDto;
	}

	public void setEsSeleccionadoNotario(boolean esSeleccionadoNotario) {
		this.esSeleccionadoNotario = esSeleccionadoNotario;
	}

	public String getCodNotario() {
		return codNotario;
	}

	public void setCodNotario(String codNotario) {
		this.codNotario = codNotario;
	}
	
}
