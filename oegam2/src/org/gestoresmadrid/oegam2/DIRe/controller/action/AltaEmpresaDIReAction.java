package org.gestoresmadrid.oegam2.DIRe.controller.action;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum;
import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIRe_AdministradorVO;
import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIRe_ContactoVO;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.ServicioEmpresaDIRe;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.ServicioEmpresa_AdministradorDIRe;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.ServicioEmpresa_ContactoDIRe;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans.ResultConsultaEmpresaDIReBean;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.dto.EmpresaDIReDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;

public class AltaEmpresaDIReAction extends ActionBase {

	private static final long serialVersionUID = 7243112805847289462L;
	private static final String ALTA_DIRe = "alta_AltaEmpresaDIRe";
	//private static final String LISTADO_DIRe = "consulta_ConsultaEmpresaDIRe";

	private EmpresaDIReDto empresaDIReDto;
	private EmpresaDIRe_ContactoVO empresaDIRe_ContactoVO;
	private EmpresaDIRe_AdministradorVO empresaDIRe_AdministradorVO;

	// Mal, hay que pasar a bean
	private List<EmpresaDIRe_ContactoVO> lista;
	private List<EmpresaDIRe_AdministradorVO> lista_Administrador;

	private BigDecimal numExpediente;

	// Contactos 
	private String Contacto_nombre;
	private String Contacto_apellido1;
	private String Contacto_apellido2;
	private String Contacto_descripcion;
	private String Contacto_id;

	private String Administrador_dni;
	private String Administrador_certificado;
	private String Administrador_descripcion;
	private String Administrador_id;

	@Autowired
	private ServicioEmpresaDIRe servicioEmpresaDIRe;
	@Autowired
	private ServicioEmpresa_ContactoDIRe servicioEmpresa_ContactoDIRe;
	@Autowired
	private ServicioEmpresa_AdministradorDIRe servicioEmpresa_AdministradorDIRe;
	@Autowired
	UtilesColegiado utilesColegiado;

	public String alta() {
		this.setContacto_nombre("");
		this.setContacto_apellido1("");
		this.setContacto_apellido2("");
		this.setContacto_descripcion("");

		if (numExpediente != null) {
			getEmpresaDIReDto(numExpediente, "Cargado correctamente");

			lista = servicioEmpresa_ContactoDIRe.getEmpresaDIRe_ContactosPorExpediente(numExpediente);
			setLista_Administrador(servicioEmpresa_AdministradorDIRe.getEmpresaDIRe_AdministradorPorExpediente(numExpediente));
		} else {
			cargarValoresIniciales();
		}
		return ALTA_DIRe;
	}

	public List<EmpresaDIRe_ContactoVO> getLista() {
		return lista;
	}

	public void setLista(List<EmpresaDIRe_ContactoVO> lista) {
		this.lista = lista;
	}

	public String alta_contacto() {
		EmpresaDIRe_ContactoVO objeto= new EmpresaDIRe_ContactoVO();
		objeto.setNombre(Contacto_nombre);
		objeto.setApellido1(Contacto_apellido1);
		objeto.setApellido2(Contacto_apellido2);
		objeto.setDescripcion(Contacto_descripcion);
		objeto.setNumExpediente(empresaDIReDto.getNumExpediente());
		servicioEmpresa_ContactoDIRe.Guardar_Contacto(objeto);
		if (empresaDIReDto.getNumExpediente() != null) {
			getEmpresaDIReDto(empresaDIReDto.getNumExpediente(), "Cargado correctamente");
		} else {
			cargarValoresIniciales();
		}
		return ALTA_DIRe;
	}
	public String borrar_contacto() {
		empresaDIRe_ContactoVO = new EmpresaDIRe_ContactoVO();
		BigDecimal id = new BigDecimal(Contacto_id);
		empresaDIRe_ContactoVO.setId(id);
		servicioEmpresa_ContactoDIRe.Borrar_Contacto(empresaDIRe_ContactoVO);

		if (empresaDIReDto.getNumExpediente() != null) {
			getEmpresaDIReDto(empresaDIReDto.getNumExpediente(), "Cargado correctamente");
		} else {
			cargarValoresIniciales();
		}
		return ALTA_DIRe;
	}

	public String alta_administrador() {
		EmpresaDIRe_AdministradorVO objeto= new EmpresaDIRe_AdministradorVO();
		objeto.setDni(Administrador_dni);
		objeto.setCertificado(Administrador_certificado);
		objeto.setDescripcion(Administrador_descripcion);

		objeto.setNumExpediente(empresaDIReDto.getNumExpediente());
		servicioEmpresa_AdministradorDIRe.Guardar_Administrador(objeto);
		if (empresaDIReDto.getNumExpediente() != null) {
			getEmpresaDIReDto(empresaDIReDto.getNumExpediente(), "Cargado correctamente");
		} else {
			cargarValoresIniciales();
		}
		return ALTA_DIRe;
	}
	public String borrar_administrador() {
		empresaDIRe_AdministradorVO = new EmpresaDIRe_AdministradorVO();
		BigDecimal id=new BigDecimal(Administrador_id);
		empresaDIRe_AdministradorVO.setId(id);
		servicioEmpresa_AdministradorDIRe.Borrar_Administrador(empresaDIRe_AdministradorVO);

		if (empresaDIReDto.getNumExpediente() != null) {
			getEmpresaDIReDto(empresaDIReDto.getNumExpediente(), "Cargado correctamente");
		} else {
			cargarValoresIniciales();
		}
		return ALTA_DIRe;
	}

	public EmpresaDIRe_ContactoVO getEmpresaDIRe_ContactoVO() {
		return empresaDIRe_ContactoVO;
	}

	public void setEmpresaDIRe_ContactoVO(EmpresaDIRe_ContactoVO empresaDIRe_ContactoVO) {
		this.empresaDIRe_ContactoVO = empresaDIRe_ContactoVO;
	}

	public EmpresaDIReDto getEmpresaDIReDto() {
		return empresaDIReDto;
	}

	public void setEmpresaDIReDto(EmpresaDIReDto empresaDIReDto) {
		this.empresaDIReDto = empresaDIReDto;
	}

	public ServicioEmpresaDIRe getServicioEmpresaDIRe() {
		return servicioEmpresaDIRe;
	}

	public void setServicioEmpresaDIRe(ServicioEmpresaDIRe servicioEmpresaDIRe) {
		this.servicioEmpresaDIRe = servicioEmpresaDIRe;
	}

	public String guardar() {
		empresaDIReDto.setIdContrato(empresaDIReDto.getContrato().getIdContrato());

		ResultConsultaEmpresaDIReBean resultado = servicioEmpresaDIRe.guardarEmpresaDIRe(empresaDIReDto, utilesColegiado.getIdUsuarioSessionBigDecimal());

		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getEmpresaDIReDto(resultado.getNumExpediente(), "EmpresaDIRe guardada correctamente");
		}
		return ALTA_DIRe;
	}

	public String validar() {
		ResultConsultaEmpresaDIReBean resultado = servicioEmpresaDIRe.validarEmpresaDIRe(empresaDIReDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getEmpresaDIReDto(resultado.getNumExpediente(), "EmpresaDIRe validado correctamente");
		}
		return ALTA_DIRe;
	}

	public String consultar() {
		ResultConsultaEmpresaDIReBean resultado = servicioEmpresaDIRe.consultarEmpresaDIRe(empresaDIReDto.getNumExpediente(), utilesColegiado.getIdUsuarioSessionBigDecimal());
		if (resultado.getError()) {
			addActionError(resultado.getMensaje());
		} else {
			getEmpresaDIReDto(resultado.getNumExpediente(), "Consultar de EmpresaDIRe generada.");
		}
		return ALTA_DIRe;
	}

	private void cargarValoresIniciales() {
		empresaDIReDto = new EmpresaDIReDto();

		ContratoDto contrato = new ContratoDto();
		contrato.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal());

		empresaDIReDto.setContrato(contrato);
		empresaDIReDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		empresaDIReDto.setOperacion(TipoOperacionCaycEnum.Alta_EmpresaDIRe.getValorEnum());
		empresaDIReDto.setCodigoDire(" ");
	}

	private void getEmpresaDIReDto(BigDecimal numExpedienteDto, String mensajeOk) {
		numExpediente = (BigDecimal) numExpedienteDto;
		ResultConsultaEmpresaDIReBean resultado = servicioEmpresaDIRe.getEmpresaDIReDto(numExpediente);
		if (resultado.getError()) {
			addActionError("Error al recuperar el EmpresaDIRe guardado con numExpediente:" + numExpediente);
		} else {
			empresaDIReDto = (EmpresaDIReDto) resultado.getEmpresaDIReDto();
			ContratoDto contrato = new ContratoDto();
			contrato.setIdContrato(resultado.empresaDIReDto.getIdContrato());
			empresaDIReDto.setContrato(contrato);
			lista = servicioEmpresa_ContactoDIRe.getEmpresaDIRe_ContactosPorExpediente(numExpediente);
			setLista_Administrador(servicioEmpresa_AdministradorDIRe.getEmpresaDIRe_AdministradorPorExpediente(numExpediente));
			addActionMessage(mensajeOk);
		}
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getContacto_nombre() {
		return Contacto_nombre;
	}

	public void setContacto_nombre(String contacto_nombre) {
		Contacto_nombre = contacto_nombre;
	}

	public String getContacto_apellido1() {
		return Contacto_apellido1;
	}

	public void setContacto_apellido1(String contacto_apellido1) {
		Contacto_apellido1 = contacto_apellido1;
	}

	public String getContacto_apellido2() {
		return Contacto_apellido2;
	}

	public void setContacto_apellido2(String contacto_apellido2) {
		Contacto_apellido2 = contacto_apellido2;
	}

	public String getContacto_descripcion() {
		return Contacto_descripcion;
	}

	public void setContacto_descripcion(String contacto_descripcion) {
		Contacto_descripcion = contacto_descripcion;
	}

	public String getContacto_id() {
		return Contacto_id;
	}

	public void setContacto_id(String contacto_id) {
		Contacto_id = contacto_id;
	}

	public String getAdministrador_dni() {
		return Administrador_dni;
	}

	public void setAdministrador_dni(String administrador_dni) {
		Administrador_dni = administrador_dni;
	}

	public String getAdministrador_certificado() {
		return Administrador_certificado;
	}

	public void setAdministrador_certificado(String administrador_certificado) {
		Administrador_certificado = administrador_certificado;
	}

	public String getAdministrador_descripcion() {
		return Administrador_descripcion;
	}

	public void setAdministrador_descripcion(String administrador_descripcion) {
		Administrador_descripcion = administrador_descripcion;
	}

	public String getAdministrador_id() {
		return Administrador_id;
	}

	public void setAdministrador_id(String administrador_id) {
		Administrador_id = administrador_id;
	}

	public List<EmpresaDIRe_AdministradorVO> getLista_Administrador() {
		return lista_Administrador;
	}

	public void setLista_Administrador(List<EmpresaDIRe_AdministradorVO> lista_Administrador) {
		this.lista_Administrador = lista_Administrador;
	}

}