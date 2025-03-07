package org.gestoresmadrid.oegam2comun.modelo600_601.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.view.dto.IntervinienteModelosDto;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.bean.Modelo600_601Bean;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import escrituras.beans.ResultBean;

public interface ServicioModelo600_601 extends Serializable{

	public static String TIPO_DOC_PUBLICO = "PUB";
	public static String CODIGO_REMESA = "00001";
	public static int TAM_CODIGO_DECLARACION = 13;
	public static int TAM_CODIGO_REMESA = 5;
	public static String ENCODING_XML_ISO = "ISO-8859-1";
	public static String ENCODING_XML_UTF8 = "UTF-8";
	public static String NOMBRE_FICHERO = "Liquidacion_";
	public static String NOMBRE_ESCRITURA = "Escritura_";
	public static String cobrarCreditos = "cobrar.creditos.cam";
	public static String RUTA_XSD = "/org/gestoresmadrid/oegam2/modelo600601/schemas/";
	public static final String RUTA_XSD_MODELO_600 = RUTA_XSD+"600_V1.2.xsd";
	public static final String RUTA_XSD_MODELO_601 = RUTA_XSD+"601_V1.2.xsd";
	// Cambiar estas líneas por las de arriba cuando se proceda al desarrollo de los Modelos 600 y 601
	//public static final String RUTA_XSD_MODELO_600 = RUTA_XSD+"600_V1.3.xsd";
	//public static final String RUTA_XSD_MODELO_601 = RUTA_XSD+"601_V1.3.xsd";

	public BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	public ResultBean generarModeloRemesa(RemesaVO remesaVO, BigDecimal idUsuario);

	ResultBean calcularLiquidacionModelosPorRemesa(Modelo600_601VO modelo600601VO);

	public ModeloDto getModelo(Modelo modelo);

	public IntervinienteModelosDto getPresentador(BigDecimal idContrato);

	public IntervinienteModelosDto getPresentadorContrato(ContratoDto contrato);

	public ResultBean getModeloDto(BigDecimal numExpediente, Long idModelo, Boolean modeloCompleto);

	public ResultBean buscarPersona(String nif, BigDecimal idContrato, Long idModelo, String tipoInterviniente);

	public String getGrupoValidacion(String codigo);

	public String getMontoBonificacion(String codigo);

	public String getFundamentoExencion(String codigo);

	public ResultBean eliminarBien(Long idModelo, String codigo, BigDecimal idUsuario);

	public ResultBean eliminarIntervinientesModelos(Long idModelo, String codigo);

	public ResultBean guardarBienesPantalla(Modelo600_601Dto modeloDto, String codigos, BigDecimal idUsuario, String numColegiado, File fichero, String fileName);

	public ResultBean guardarModelo(Modelo600_601Dto modeloDto, String numColegiado, BigDecimal idUsuario, File fichero, String fileName, boolean mostrarEscritura);

	public List<Modelo600_601Bean> convertirListaEnBeanPantalla(List<Modelo600_601VO> lista);

	public ResultBean validarModelo(BigDecimal numExpediente, BigDecimal idUsuario);

	public ResultBean gestionarConceptoModelo(Long idModelo);

	public ResultBean autoLiquidarModelo(BigDecimal numExpediente, BigDecimal idUsuario, Modelo600_601Dto modeloDto);

	public ResultBean actualizarModelo600601(Modelo600_601VO modeloVO);

	public ResultBean cambiarEstado(BigDecimal numExpediente, BigDecimal nuevoEstado, BigDecimal idUsuario, Boolean acciones);

	public Modelo600_601VO getModeloVOPorNumExpediente(BigDecimal numExpediente, Boolean compelto);

	public void borrarXmlPresentacionSiexiste(String nombreXml, BigDecimal numExpediente);

	public ResultBean imprimirDocumento(BigDecimal numExpediente, String idResultadosModelo, String tipoFichero);

	public ResultBean validarContratosPresentacionEnBloque(String codSeleccionados, BigDecimal idContrato);

	ResultBean guardarEscritura(BigDecimal numExp, File fichero, String fileName);

	ResultBean presentar(BigDecimal numExp,	DatosBancariosFavoritosDto datosBancarios, BigDecimal idUsuario, File fichero, String fileName);

	public BigDecimal getEstadoModelo(BigDecimal numExpediente);

	public ResultBean presentarModeloRemesasBloque(Modelo600_601VO modelo600_601VO, DatosBancariosFavoritosDto datosBancarios, BigDecimal idUsuario);

	public List<Modelo600_601Dto> getListaModelosPorExpedientesYContrato(String codSeleccionados, Long idContrato);

	public ResultBean validarModeloImpresion(BigDecimal numExpediente);

	String getNombreFicheroModelo(Long idModelo, String justificante, String tipoFichero);

	public ResultBean cambiarEstadoModelosRemesa(Long idRemesa, BigDecimal estadoNuevo, BigDecimal idUsuario);

	List<Modelo600_601Dto> getListaModelosPorIdRemesa(Long idRemesa,Boolean completo);

	public ResultBean eliminarModelosRemesa(Long idRemesa);

	List<Modelo600_601VO> getListaModelosPorIdRemesaVO(Long idRemesa, Boolean completo);

	public ResultBean esEstadoAnulado(BigDecimal numExpediente);

	public ResultBean anularModelo(BigDecimal bigDecimal, BigDecimal idUsuario);

	public ResultBean importarModelo600(File fichero, String idContrato, BigDecimal idUsuario);

	public ResultBean importarModelo601(File fichero, String idContrato, BigDecimal idUsuario);

	public ResultBean calcularLiquidacion(Modelo600_601Dto modeloDto);

	ResultBean actualizarModelo600601ConEvolucion(Modelo600_601VO modelo600_601, BigDecimal idUsuario);

	public ResultBean aplicarBonificacion(Modelo600_601VO modeloVO);

}