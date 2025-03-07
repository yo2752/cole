package org.gestoresmadrid.oegamImportacion.direccion.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.gestoresmadrid.core.direccion.model.vo.DgtMunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;

public interface ServicioDireccionImportacion extends Serializable {

	public static String DIRECCION = "DIRECCION";
	public static String NUEVA_DIRECCION = "NUEVA_DIRECCION";

	public static String CREACION = "CREACION";

	public static String CONVERSION_DIRECCION_INE = "direccionIne";
	public static String CONVERSION_DIRECCION_CORREOS = "direccionCorreos";

	public static ArrayList<String> DIRECCION_NO_ACTIVA = new ArrayList<String>(Arrays.asList("NO", "no", "No", "N"));

	/*************************** Nuevos metodos importacion ******************************************/

	Boolean codigoProvinciaCorrecta(String idProvincia);

	Boolean codigoMunicipioCorrecto(String idProvincia, String idMunicipio);

	Boolean esTipoViaCorrecta(String idTipoVia);

	TipoViaVO getTipoViaPorIdDgt(String idTipoViaDgt);

	TipoViaVO getTipoViaPorNombre(String nombre);

	Boolean esPuebloCorrecto(String idProvincia, String idMunicipio, String pueblo);

	ResultadoValidacionImporBean buscarPersonaDireccion(String numColegiado, String nif);

	ResultadoBean guardarActualizarDireccionVeh(DireccionVO direccionVO, String matricula, String tipoTramite);

	ResultadoBean guardarActualizarDirPersona(DireccionVO direccion, String nif, String numColegiado, String tipoTramite);

	String obtenerNombreMunicipio(String idMunicipio, String idProvincia);

	MunicipioVO getMunicipio(String idMunicipio, String idProvincia);

	MunicipioVO getMunicipioPorNombre(String nombreMunicipio, String idProvincia);

	DgtMunicipioVO getMunicipioDGT(BigDecimal idDgtMunicipio, String idProvincia);

	DgtMunicipioVO getMunicipioDGTPorNombre(String municipio, String idProvincia);

	String obtenerNombreProvincia(String idProvincia);

	String obtenerCodigoPostal(String idMunicipio, String idProvincia);

	Boolean codigoMunicipioCorrectoDGT(String idProvincia, String municipio);

	String obtenerCodigoPostalDGT(BigDecimal idDgtMunicipio, String idProvincia);

	Boolean esPuebloCorrectoDgt(String codigoPostal, String pueblo);
}
