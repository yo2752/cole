package org.gestoresmadrid.oegam2comun.conversion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

public interface Conversiones extends Serializable {

	void inicializarProvincia();

	void inicializarTablaProvincias();

	String getIdProvinciaFromSiglas(String sigla);

	String getIdTipoViaDGTFromIdTipoVia(String idTipoVia);

	String getIdTipoViaDGTFromNumeroViaDGT(String numeroVia);

	String getIdDGTFromKey(String tipo);

	String getSiglasFromIdProvincia(String idProvincia);

	String getJefaturaProvincialFromNombre(String nombre);

	String getIdProvinciaFromNombre(String nombre);

	String getSiglaProvinciaFromNombre(String nombre);

	String getNombreProvincia(String idProvincia);

	HashMap<String, String> separarDomicilio(String valor);

	boolean esUnTipoVia(String valor);

	boolean comprobarNumero(String cadena);

	String convertirModoAdjudicacion(String valor);

	String convertirTipoTasa(String tipoTasa);

	BigDecimal getCodigoMarcaFromNombreMarcaSinEditar(String marca, boolean versionMatw);

	String tipVehiNuevo(String tipVehiAntiguo);

	String tipVehiAntiguo(String tipVehiNuevo);

	String servicioDestinoNuevo(String servicioAntiguo);

	String servicioIdBDtoCodXML(String idBD);

	boolean isNifNie(String nif);

	String numRegistroLimitacionToMotivo(String valor, String tipo);

	String convertirMunicipioAntigua(String idMunicipio, String idProvincia);

	String convertirProvinciasAntigua(String idProvincia);

	String ajustarCamposIne(String campo);

	String convertirTipoCombustible(String valor);

	String getCodigoMunicipo(String idDireccion);
}
