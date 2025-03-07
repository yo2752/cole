package org.gestoresmadrid.oegam2.remesa.controller.action;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.OficinaLiquidadoraDto;
import org.gestoresmadrid.oegam2comun.notario.model.service.ServicioNotario;
import org.gestoresmadrid.oegam2comun.notario.view.dto.NotarioDto;
import org.gestoresmadrid.oegam2comun.remesa.model.service.ServicioRemesas;
import org.gestoresmadrid.oegam2comun.remesa.view.bean.ResumenPresentacionRemesas;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import escrituras.utiles.enumerados.Provincias;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AltaRemesasAction extends ActionBase{

	private static final long serialVersionUID = 2275307843144017097L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(AltaRemesasAction.class);
	
	private static final String ALTA_REMESA_600 = "altaRemesa600";
	private static final String ALTA_REMESA_601 = "altaRemesa601";
	private static final String MODIFICAR_MODELO_600 = "aModelo600";
	private static final String MODIFICAR_MODELO_601 = "aModelo601";
	private static final String POP_UP_PRESENTAR = "popPupPresentarRemesa";
	
	private RemesaDto remesa;
	private String codigo;
	private String tipoModelo;
	private BigDecimal numExpediente;
	private String tipoInterviniente;
	private String porcentaje;
	private DatosBancariosFavoritosDto datosBancarios;
	private Boolean esResumenP;
	private Boolean esModeloPresentable; 
	private ResumenPresentacionRemesas resumenPresentacion;
	
	@Autowired
	private ServicioNotario servicioNotario;
	
	@Autowired
	private ServicioContrato servicioContrato;
	
	@Autowired
	private ServicioRemesas servicioRemesas;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public String guardar(){
		try{
			tipoModelo = remesa.getModelo().getModelo();
			ResultBean resultado = servicioRemesas.guardarRemesa(remesa,codigo,porcentaje,utilesColegiado.getNumColegiadoSession(),utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(resultado.getError()){
				aniadirMensajeError(resultado);
				cargarValoresIniciales(false);
			}else{
				if(resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()){
					addActionMessage("La remesa se ha guardado con los siguientes errores: ");
					for(String mensaje : resultado.getListaMensajes()){
						addActionError(mensaje);
					}
				}else{
					addActionMessage("La remesa se ha guardado correctamente");
				}
				BigDecimal numExpediente = (BigDecimal)resultado.getAttachment("numExpediente");
				ResultBean resultObtRemesa = obtenerRemesa(numExpediente);
				if(resultObtRemesa.getError()){
					aniadirMensajeError(resultObtRemesa);
				}
			}
			
		}catch(Exception e){
			log.error("Ha sucedido un error a la hora de guardar la remesa de pantalla, error: ",e);
			addActionError("Ha sucedido un error a la hora de guardar la remesa");
			remesa.setModelo(servicioRemesas.getModelo(Modelo.convertirTexto(remesa.getModelo().getModelo())));
		}
		return getPaginaPorModelo();
	}
	
	public String eliminarBien(){
		try {
			if(codigo != null && !codigo.isEmpty()){
				ResultBean resultado = servicioRemesas.eliminarBien(remesa.getIdRemesa(),codigo);
				if(resultado == null || !resultado.getError()){
					addActionMessage("Los bienes se han eliminado correctamente.");
				}else{
					aniadirMensajeError(resultado);
				}
			}else{
				addActionError("Debe seleccionar algún bien para eliminar.");
			}
			ResultBean resultObtRemesa = obtenerRemesa(remesa.getNumExpediente());
			if(resultObtRemesa.getError()){
				aniadirMensajeError(resultObtRemesa);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los bienes de la remesa, error: ",e);
			addActionError("Ha sucedido un error a la hora de eliminar los bienes de la remesa.");
			remesa.setModelo(servicioRemesas.getModelo(Modelo.convertirTexto(remesa.getModelo().getModelo())));
		}
		return getPaginaPorModelo();
	}
	
	public String notarioNuevo(){
		if(codigo != null && !codigo.isEmpty()){
			NotarioDto notarioDto = servicioNotario.getNotarioPorId(codigo);
			if(notarioDto == null){
				addActionError("El notario seleccionado no existe.");
			}
			ResultBean resultObtRemesa = obtenerRemesa(remesa.getNumExpediente());
			if(resultObtRemesa.getError()){
				aniadirMensajeError(resultObtRemesa);
			}
			remesa.setNotario(notarioDto);
		}else{
			addActionError("Debe seleccionar algun notario.");
			remesa.setModelo(servicioRemesas.getModelo(Modelo.convertirTexto(remesa.getModelo().getModelo())));
		}
		return getPaginaPorModelo();
	}
	
	public String eliminarInterviniente(){
		ResultBean resultado = null;
		if(codigo != null && !codigo.isEmpty()){
			resultado = servicioRemesas.eliminarIntervinientesRemesa(remesa.getIdRemesa(),codigo);
			if(resultado == null || !resultado.getError()){
				addActionMessage("Los intervinientes se han eliminado correctamente.");
			}else{
				aniadirMensajeError(resultado);
			}
		}else{
			addActionError("Debe seleccionar interviniente para eliminar.");
		}
		ResultBean resultObtRemesa = obtenerRemesa(remesa.getNumExpediente());
		if(resultObtRemesa.getError()){
			aniadirMensajeError(resultObtRemesa);
		}
		return getPaginaPorModelo();
	}
	
	public String guardarBienes(){
		if(codigo != null && !codigo.isEmpty()){
			ResultBean resultado = servicioRemesas.guardarBienesPantalla(remesa,codigo,utilesColegiado.getIdUsuarioSessionBigDecimal(),utilesColegiado.getNumColegiadoSession());
			BigDecimal numExpediente = null;
			if(resultado.getError()){
				addActionError("La remesa no se ha podido guardar por los siguientes errores: ");
				aniadirMensajeError(resultado);
				numExpediente = remesa.getNumExpediente();
			}else{
				addActionMessage("Los bienes se han guardado correctamente");
				if(resultado.getListaMensajes() != null && !resultado.getListaMensajes().isEmpty()){
					addActionMessage("La remesa se ha guardado con los siguientes errores: ");
					for(String mensaje : resultado.getListaMensajes()){
						addActionError(mensaje);
					}
				}
				ResultBean resultBean = servicioRemesas.gestionarConceptoRemesa((Long)resultado.getAttachment("idRemesa"));
				if(resultBean != null && resultBean.getError()){
					aniadirMensajeError(resultBean);
				}
				numExpediente = (BigDecimal)resultado.getAttachment("numExpediente");
			}
			ResultBean resultObtRemesa = obtenerRemesa(numExpediente);
			if(resultObtRemesa.getError()){
				aniadirMensajeError(resultObtRemesa);
			}
		}else{
			addActionError("Debe seleccioar algun bien para poder guardarlo");
			remesa.setModelo(servicioRemesas.getModelo(Modelo.convertirTexto(remesa.getModelo().getModelo())));
		}
		return getPaginaPorModelo();
	}

	public String alta(){
		if(numExpediente != null){
			ResultBean resultado = servicioRemesas.getRemesaPorNumExpediente(numExpediente,true);
			if(!resultado.getError()){
				remesa = (RemesaDto) resultado.getAttachment("remesaDto");
				esModeloPresentable = (Boolean) resultado.getAttachment("existePresentable");
			}else{
				aniadirMensajeError(resultado);
				remesa.setModelo(servicioRemesas.getModelo(Modelo.convertirTexto(remesa.getModelo().getModelo())));
			}
		}else{
			cargarValoresIniciales(true);
		}
		return getPaginaPorModelo();
	}
	
	public String modificarMd(){
		String pagina = "";
		if(Modelo.Modelo600.getValorEnum().equals(remesa.getModelo().getModelo())){
			if(numExpediente != null){
				pagina = MODIFICAR_MODELO_600;
			}else{
				addActionError("Debe seleccionar un modelo para porder modificarlo.");
				pagina = ALTA_REMESA_600;
			}
		}else if(Modelo.Modelo601.getValorEnum().equals(remesa.getModelo().getModelo())){
			if(numExpediente != null){
				pagina = MODIFICAR_MODELO_601;
			}else{
				addActionError("Debe seleccionar un modelo para porder modificarlo.");
				pagina = ALTA_REMESA_601;
			}
		}
		return pagina;
	}
	
	public String generar(){
		ResultBean resultado = servicioRemesas.generarModelo(remesa.getNumExpediente(),utilesColegiado.getIdUsuarioSessionBigDecimal());
		if(resultado.getError()){
			addActionError("Los modelos no se han podido generar por los siguientes errores: ");
			aniadirMensajeError(resultado);
		}else{
			addActionMessage("Los modelos se han generado correctamente.");
		}
		ResultBean resultObtRemesa = obtenerRemesa(remesa.getNumExpediente());
		if(resultObtRemesa.getError()){
			aniadirMensajeError(resultObtRemesa);
		}
		return getPaginaPorModelo();
	}
	
	public String presentar(){
		if(codigo != null && !codigo.isEmpty()){
			ResultBean resultPresentacion = servicioRemesas.presentarModelos(codigo,datosBancarios,utilesColegiado.getIdUsuarioSessionBigDecimal());
			if(!resultPresentacion.getError()){
				rellenarResumenPresentacion(resultPresentacion);
				esResumenP = true;
				esModeloPresentable = (Boolean) resultPresentacion.getAttachment("existePresentable");
			}else{
				aniadirMensajeError(resultPresentacion);
			}
		}else{
			addActionError("Debe seleccionar algún modelo para para presentar.");
		}
		ResultBean resultObtRemesa = obtenerRemesa(remesa.getNumExpediente());
		if(resultObtRemesa.getError()){
			aniadirMensajeError(resultObtRemesa);
		}
		return getPaginaPorModelo();
	}
	
	@SuppressWarnings("unchecked")
	private void rellenarResumenPresentacion(ResultBean resultPresentacion) {
		List<String> listaErrores = (List<String>) resultPresentacion.getAttachment("listaErrores");
		List<String> listaOk = (List<String>) resultPresentacion.getAttachment("listaOk");
		int numOk = (Integer) resultPresentacion.getAttachment("numOk");
		int numErrores = (Integer) resultPresentacion.getAttachment("numErrores");
		if(resumenPresentacion == null){
			resumenPresentacion = new ResumenPresentacionRemesas();
		}
		resumenPresentacion.setListaMensajesErrores(listaErrores);
		resumenPresentacion.setListaMensajesOk(listaOk);
		resumenPresentacion.setNumErrores(numErrores);
		resumenPresentacion.setNumOk(numOk);
	}

	public String validar(){
		ResultBean resultado = servicioRemesas.guardarRemesa(remesa,codigo,porcentaje,remesa.getNumColegiado(),utilesColegiado.getIdUsuarioSessionBigDecimal());
		if(!resultado.getError()){
			ResultBean resultBean = servicioRemesas.gestionarConceptoRemesa((Long)resultado.getAttachment("idRemesa"));
			if(resultBean != null && resultBean.getError()){
				aniadirMensajeError(resultBean);
			}else{
				ResultBean resultValidar = servicioRemesas.validarRemesa(remesa.getNumExpediente(),utilesColegiado.getIdUsuarioSessionBigDecimal());
				if(resultValidar == null || !resultValidar.getError()){
					addActionMessage("La remesa se ha validado correctamente.");
				}else{
					aniadirMensajeError(resultValidar);
				}
			}
		}else{
			aniadirMensajeError(resultado);
		}
		ResultBean resultObtRemesa = obtenerRemesa(remesa.getNumExpediente());
		if(resultObtRemesa.getError()){
			aniadirMensajeError(resultObtRemesa);
		}
		return getPaginaPorModelo();
	}
	
	private ResultBean obtenerRemesa(BigDecimal numExpediente) {
		ResultBean resultado = new ResultBean(false);
		if(numExpediente != null){
			resultado = servicioRemesas.getRemesaPorNumExpediente(numExpediente,true);
			if(resultado != null && !resultado.getError()){
				remesa = (RemesaDto) resultado.getAttachment("remesaDto");
				esModeloPresentable = (Boolean) resultado.getAttachment("existePresentable");
			}else if(resultado == null || resultado.getError()){
				remesa.setModelo(servicioRemesas.getModelo(Modelo.convertirTexto(remesa.getModelo().getModelo())));
			}
		}else{
			remesa.setModelo(servicioRemesas.getModelo(Modelo.convertirTexto(remesa.getModelo().getModelo())));
		}
		return resultado;
	}

	private void cargarValoresIniciales(Boolean esInicial) {
		if(remesa == null){
			remesa = new RemesaDto();
		}	
		if(esInicial){
			//se carga directo madrid 
			OficinaLiquidadoraDto oficinaLiquidadora = new OficinaLiquidadoraDto();
			oficinaLiquidadora.setIdProvincia(String.valueOf(Provincias.Madrid.getValorEnum()));
			remesa.setOficinaLiquidadora(oficinaLiquidadora);
		}else{
			ResultBean result = servicioRemesas.getRemesaPorNumExpediente(remesa.getNumExpediente(),true);
			if(result != null && !result.getError()){
				RemesaDto remesaBBDD = (RemesaDto) result.getAttachment("remesaDto");
				remesa.setListaBienesRusticos(remesaBBDD.getListaBienesRusticos());
				remesa.setListaBienesUrbanos(remesaBBDD.getListaBienesUrbanos());
				remesa.setListaOtrosBienes(remesaBBDD.getListaOtrosBienes());
				remesa.setListaModelos(remesaBBDD.getListaModelos());
				remesa.setListaPartSujPasivo(remesaBBDD.getListaPartSujPasivo());
				remesa.setListaPartTransmitente(remesaBBDD.getListaPartTransmitente());
				remesa.setListaSujetosPasivos(remesaBBDD.getListaSujetosPasivos());
				remesa.setListaTransmitentes(remesaBBDD.getListaTransmitentes());
			}
		}
		remesa.setModelo(servicioRemesas.getModelo(Modelo.convertirTexto(tipoModelo)));
		remesa.setPresentador(servicioRemesas.getPresentadorContrato(servicioContrato.getContratoDto(utilesColegiado.getIdContratoSessionBigDecimal())));
		remesa.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		remesa.setContrato(servicioContrato.getContratoDto(utilesColegiado.getIdContratoSessionBigDecimal()));
	}

	private String getPaginaPorModelo(){
		String pagina = "";
		if(Modelo.Modelo600.getValorEnum().equals(remesa.getModelo().getModelo())){
			pagina = ALTA_REMESA_600;
		}else if(Modelo.Modelo601.getValorEnum().equals(remesa.getModelo().getModelo())){
			pagina = ALTA_REMESA_601;
		}
		return pagina;
	}
	
	public String cargarPopUpPresentar(){
		return POP_UP_PRESENTAR;
	}

	public RemesaDto getRemesa() {
		return remesa;
	}

	public void setRemesa(RemesaDto remesa) {
		this.remesa = remesa;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getTipoModelo() {
		return tipoModelo;
	}

	public void setTipoModelo(String tipoModelo) {
		this.tipoModelo = tipoModelo;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public DatosBancariosFavoritosDto getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(DatosBancariosFavoritosDto datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public Boolean getEsResumenP() {
		return esResumenP;
	}

	public void setEsResumenP(Boolean esResumenP) {
		this.esResumenP = esResumenP;
	}

	public ResumenPresentacionRemesas getResumenPresentacion() {
		return resumenPresentacion;
	}

	public void setResumenPresentacion(
			ResumenPresentacionRemesas resumenPresentacion) {
		this.resumenPresentacion = resumenPresentacion;
	}

	public Boolean getEsModeloPresentable() {
		return esModeloPresentable;
	}

	public void setEsModeloPresentable(Boolean esModeloPresentable) {
		this.esModeloPresentable = esModeloPresentable;
	}

}
