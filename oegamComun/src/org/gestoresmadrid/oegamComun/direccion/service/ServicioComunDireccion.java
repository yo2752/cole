package org.gestoresmadrid.oegamComun.direccion.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.oegamComun.direccion.view.bean.ResultadoDireccionBean;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoPersonaBean;

public interface ServicioComunDireccion extends Serializable {

	public static String CONVERSION_DIRECCION_CORREOS = "direccionCorreos";
	public static String CONVERSION_DIRECCION_INE = "direccionIne";

	public static ArrayList<String> DIRECCION_NO_ACTIVA = new ArrayList<String>(Arrays.asList("NO", "no", "No", "N"));

	ResultadoPersonaBean guardarActualizarPersona(DireccionVO direccionVO, String nif, String numColegiado, String tipoTramite, String conversion);

	String obtenerNombreTipoVia(String idTipoVia);

	String obtenerNombreProvincia(String idProvincia);

	String obtenerNombreMunicipio(String idMunicipio, String idProvincia);

	String obtenerNombreProvinciaSitex(String idProvincia);

	String obtenerNombreMunicipioSitex(String idMunicipio, String idProvincia);

	ResultadoDireccionBean tratarDirVehiculo(DireccionVO direccion, String matricula, String conversion);

	DireccionVO guardarActualizarDireccion(DireccionVO direccion);

	ResultadoPersonaBean tratarPersonaDireccion(DireccionVO direccion, String nif, String numColegiado, String tipoTramite, BigDecimal numExpediente, Long idUsuario, String conversion);

}