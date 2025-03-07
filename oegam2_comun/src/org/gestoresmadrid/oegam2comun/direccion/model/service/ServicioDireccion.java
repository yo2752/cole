package org.gestoresmadrid.oegam2comun.direccion.model.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.DgtMunicipioDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.LocalidadDgtDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ViaDto;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;

import escrituras.beans.ResultBean;

public interface ServicioDireccion extends Serializable {

	public static String DIRECCION = "DIRECCION";
	public static String NUEVA_DIRECCION = "NUEVA_DIRECCION";

	public static String CREACION = "CREACION";

	public static String CONVERSION_DIRECCION_INE = "direccionIne";
	public static String CONVERSION_DIRECCION_CORREOS = "direccionCorreos";
	public static String CONVERSION_DIRECCION_BIEN = "direccionBienes";
	public static String CONVERSION_DIRECCION_REGISTRO = "direccionRegistro";

	public static ArrayList<String> DIRECCION_NO_ACTIVA = new ArrayList<String>(Arrays.asList("NO", "no", "No", "N"));

	DireccionDto getDireccionDto(Long idDireccion);

	DireccionVO getDireccionVO(Long idDireccion);

	ResultBean guardar(DireccionVO direccion);

	ResultBean guardarActualizar(DireccionDto direccion);

	ResultBean guardarActualizarVehiculo(DireccionVO direccionVO, String matricula, String conversion);

	ResultBean guardarActualizarPersona(DireccionVO direccionVO, String nif, String numColegiado, String tipoTramite, String conversion);

	boolean esModificada(DireccionVO direccionVO, DireccionVO direccionBBDD);

	MunicipioDto getMunicipio(String idMunicipio, String idProvincia);

	String obtenerNombreMunicipio(String idMunicipio, String idProvincia);

	String obtenerCodigoPostal(String idMunicipio, String idProvincia);

	String obtenerCodigoPostalSiUnico(String idMunicipio, String idProvincia);

	ProvinciaDto getProvincia(String idProvincia);

	String obtenerNombreProvincia(String idProvincia);

	TipoViaDto getTipoVia(String idTipoVia);

	TipoViaDto getTipoViaDgt(String idTipoViaDgt);

	String obtenerNombreTipoVia(String idTipoVia);

	DgtMunicipioDto getDgtMunicipio(String idProvincia, String municipio);

	PuebloDto getPueblo(String idProvincia, String idMunicipio, String pueblo);

	LocalidadDgtDto getLocalidadDgt(String codigoPostal, String pueblo);

	ViaDto getVia(String idProvincia, String idMunicipio, String idTipoVia, String via);

	void eliminar(Long idDireccion);

	ResultBean guardarActualizarBien(DireccionVO direccionVO);

	ResultBean actualizar(DireccionVO direccionVO);

	ResultadoCtitBean validarDireccion(DireccionVO direccion, String mensaje);
}
