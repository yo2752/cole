package trafico.validaciones.ivtm;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;

import hibernate.entities.personas.Direccion;
import hibernate.entities.personas.Municipio;
import hibernate.entities.personas.PersonaDireccion;
import hibernate.entities.trafico.IntervinienteTrafico;
import hibernate.entities.trafico.MarcaDgt;
import hibernate.entities.trafico.TipoInterviniente;
import hibernate.entities.trafico.Vehiculo;
import trafico.dto.TramiteTraficoDto;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.constantes.ConstantesIVTM;
import trafico.utiles.enumerados.TipoVehiculoIvtm;
import utilidades.estructuras.Fecha;
//TODO MPC. Cambio IVTM. Clase nueva.
public class ValidacionIvtm {

	private static final String CODIGOESIBANENNUMERO = "1428";
	private static final String MODULOIBAN = "97";
	private static final int MODULOCCC = 11;
	private static IVTMModeloMatriculacionInterface ivtmModeloMtr = new IVTMModeloMatriculacionImpl();
	
	/**
	 * Validaciones para el Alta - Modificacion de Autoliquidaci�n del IVTM de Madrid
	 * 
	 */

	public static List<String> validarAlta(TramiteTraficoDto tramite, IvtmMatriculacionDto ivtmMatriculacionDto){
		List<String> errores = new ArrayList<String>();
		if (tramite == null || ivtmMatriculacionDto==null){
			errores.add(ConstantesIVTM.TEXTO_NO_TRAMITE);
			return errores;
		}
		/* Comprobamos que es de Madrid. Se est� comprobando el domicilio del titular. Cuando se cambie (si se cambia) al domicilio del veh�culo, hay que cambiar este m�todo*/
		anadirErroresEsMadrid(tramite, errores);
		// TODO est� pendiente comprobar que fechas se utilizan
		comprobarFechaIVTM(ivtmMatriculacionDto.getFechaPago(), errores, ConstantesIVTM.FECHA_IVTM);
		anadirErroresIbanIVTM(ivtmMatriculacionDto.getIban(), errores);
		anadirErroresBonificacionMedioAmbiente(ivtmMatriculacionDto.isBonmedam(), ivtmMatriculacionDto.getPorcentajebonmedam(),errores);
		anadirErroresTitular(tramite, errores);
		anadirErroresRepresentante(tramite, errores);
		anadirErroresVehiculo(tramite, errores);
		return errores;
	}
	
	public static List<String> validarModificacion(TramiteTraficoDto tramite, IvtmMatriculacionDto ivtmMatriculacionDto){
		List<String> errores = new ArrayList<String>();
		if(tramite == null || ivtmMatriculacionDto==null ){
			errores.add(ConstantesIVTM.TEXTO_NO_TRAMITE);
			return errores;
		}
		anadirErroresNumAutoliquidacion(ivtmMatriculacionDto.getNrc(), errores);
		comprobarFechaIVTM(ivtmMatriculacionDto.getFechaPago(), errores, ConstantesIVTM.FECHA_IVTM);
		errores.addAll(validarAlta(tramite, ivtmMatriculacionDto));
		return errores;
	}
	
	public static List<String> validarPago(TramiteTraficoDto tramite, IvtmMatriculacionDto ivtmMatriculacionDto) {
		List<String> errores = new ArrayList<String>();
		if (tramite == null || ivtmMatriculacionDto == null) {
			errores.add(ConstantesIVTM.TEXTO_NO_TRAMITE);
			return errores;
		}
		anadirErroresNumAutoliquidacion(ivtmMatriculacionDto.getNrc(), errores);
		anadirErroresEmisor(ivtmMatriculacionDto.getEmisor(), errores);
		anadirErroresImporte(ivtmMatriculacionDto.getImporte(), errores);
		anadirErroresDigitoControl(ivtmMatriculacionDto.getDigitoControl(), errores);
		anadirErroresGestor(ivtmMatriculacionDto.getCodGestor(), errores);
		anadirErroresFechaPago(ivtmMatriculacionDto.getFechaPago(), errores);
		anadirErroresTitular(tramite, errores);
		// TODO: saber de donde se saca este dato
		//anadirErroresDistritoMunicipal(ivtmMatriculacionDto.getFechaPago(), errores);
		return errores;
	}
	
	private static void anadirErroresFechaPago(Fecha fechaPago, List<String> errores) {
		if (fechaPago == null || fechaPago.isfechaNula()) {
			errores.add(ConstantesIVTM.TEXTO_FECHA_PAGO_OBLIGATORIO);
		}
	}
	
	private static void anadirErroresEmisor(String emisor, List<String> errores) {
		if (emisor == null || emisor.isEmpty()) {
			errores.add(ConstantesIVTM.TEXTO_EMISOR_OBLIGATORIO);
		}
	}
	
	private static void anadirErroresGestor(String codigoGestor, List<String> errores) {
		if (codigoGestor == null || codigoGestor.isEmpty()) {
			errores.add(ConstantesIVTM.TEXTO_GESTOR_OBLIGATORIO);
		}
	}
	
	private static void anadirErroresDigitoControl(String digitoControl, List<String> errores) {
		if (digitoControl == null || digitoControl.isEmpty()) {
			errores.add(ConstantesIVTM.TEXTO_IVTM_DIGITO_CONTROL);
		}
	}
	
	private static void anadirErroresImporte(BigDecimal importe, List<String> errores) {
		if (importe == null) {
			errores.add(ConstantesIVTM.TEXTO_IMPORTE_OBLIGATORIO);
		}
	}
	
	private static void anadirErroresNumAutoliquidacion(String nrcIvtm, List<String> errores) {
		if(nrcIvtm==null || ! longitudSinBlancosMayor0(nrcIvtm) ){
			errores.add(ConstantesIVTM.TEXTO_AUTOLIQUIDACION_OBLIGATORIA);
		}
	}
	
	private static void anadirErroresBonificacionMedioAmbiente(Boolean bonmedam, BigDecimal porcentajebonmedam, List<String> errores) {
		if (bonmedam != null && bonmedam){
			try {
				if (porcentajebonmedam == null || porcentajebonmedam.intValue()<0 || porcentajebonmedam.intValue()>100){
					errores.add(ConstantesIVTM.TEXTO_BONIFICACION_MEDIO_AMBIENTE_OBLIGATORIO);
				}
			} catch (Exception e) {
				errores.add(ConstantesIVTM.TEXTO_BONIFICACION_MEDIO_AMBIENTE_OBLIGATORIO);
			}
		}
	}

	public static boolean validarIbanIVTM(String iban){
		String ibanComprobar = iban.substring(4,24) +CODIGOESIBANENNUMERO+iban.substring(2,4);
		try {
			return new BigInteger(ibanComprobar).mod(new BigInteger(MODULOIBAN)).intValue() ==1;
		} catch (NumberFormatException e){
			return false;
		}
			
	}
	
	private static boolean validarFormatoCCC(String ccc) {
		int [] factores = new int[]{1,2,4,8,5,10,9,7,3,6};
		return validarParteCCC("00" + ccc.substring(0,8), ccc.substring(8,9), factores) && validarParteCCC(ccc.substring(10, 20), ccc.substring(9,10), factores);
	}
	
	private static boolean validarParteCCC (String parteCuenta, String digitoControl, int[] factores){
		int contador = 0;
		for (int i=0; i<factores.length;i++){
			try {
				contador += Integer.parseInt(parteCuenta.substring(i,i+1)) * factores[i];
			} catch (NumberFormatException e){
				return false;
			}
		}
		int primerDigitoCompuesto = MODULOCCC-(contador % MODULOCCC);
		if (primerDigitoCompuesto == MODULOCCC){
			primerDigitoCompuesto = 0;
		} else if (primerDigitoCompuesto == MODULOCCC-1){
			primerDigitoCompuesto = 1;
		}
		try {
			return Integer.parseInt(digitoControl) == primerDigitoCompuesto;
		} catch (NumberFormatException e){
			return false;
		}
	}
	
	private static void anadirErroresIbanIVTM(String iban, List<String> errores) {
		if ( iban==null || iban.isEmpty()){
			return; 
		}
		if (iban.length() != 24 || !iban.substring(0,2).equals("ES")){
			errores.add(ConstantesIVTM.TEXTO_IBAN_OBLIGATORIO);
			return;
		}
		if (!validarIbanIVTM(iban) || !validarFormatoCCC(iban.substring(4,24))){
			errores.add(ConstantesIVTM.TEXTO_IBAN_OBLIGATORIO);
		}
		/* Se podr�a tambi�n comprobar que el CCC es v�lido (digitol de control v�lido) */
	}

	private static void anadirErroresVehiculo(TramiteTraficoDto tramite, List<String> errores) {
		if (tramite== null || tramite.getVehiculo()==null){
			errores.add(ConstantesIVTM.TEXTO_VEHICULO_OBLIGATORIO);
		} else {
			Vehiculo vehiculo = tramite.getVehiculo();
			anadirErroresBastidor(vehiculo.getBastidor(), errores);
			anadirErroresMarcaVehiculo(vehiculo.getMarcaDgt(), errores);
			anadirErroresModeloVehiculo(vehiculo.getModelo(), errores);
			if (vehiculo.getTipoVehiculoBean()==null || vehiculo.getTipoVehiculoBean().getTipoVehiculo()==null || vehiculo.getTipoVehiculoBean().getTipoVehiculo().isEmpty()){
				errores.add(ConstantesIVTM.TEXTO_TIPO_VEHICULO_OBLIGATORIO);
			} else {
				TipoVehiculoIvtm tipoVehiculoIvtm = ivtmModeloMtr.obtenerTipoVehiculo(vehiculo.getTipoVehiculoBean().getTipoVehiculo());
				anadirErroresCriterioConstruccion(tipoVehiculoIvtm, vehiculo, errores);
				anadirErroresCarburante(tipoVehiculoIvtm, vehiculo.getIdCarburante(), errores);
			}
			anadirErroresTipoServicio(vehiculo.getIdServicio(), errores);
		}
	}

	private static void anadirErroresTipoServicio(String servicio, List<String> errores) {
		if (servicio == null ||  servicio.isEmpty() || servicio.equals("-1")){
			errores.add(ConstantesIVTM.TEXTO_SERVICIO_OBLIGATORIO);
		}
		
	}

	private static void anadirErroresCarburante(TipoVehiculoIvtm tipoVehiculoIvtm, String carburante, List<String> errores) {
		if (tipoVehiculoIvtm!= null && !tipoVehiculoIvtm.equals(TipoVehiculoIvtm.Remolques) && (carburante == null || carburante.isEmpty() || carburante.equals("-1"))){
			errores.add(ConstantesIVTM.TEXTO_CARBURANTE_OBLIGATORIO);
		}
	}

	private static void anadirErroresCriterioConstruccion(TipoVehiculoIvtm tipoVehiculoIvtm, Vehiculo vehiculo, List<String> errores) {
			anadirErroresTipoVehiculo (tipoVehiculoIvtm, vehiculo, errores);
		
	}

	private static void anadirErroresTipoVehiculo(TipoVehiculoIvtm tipo, Vehiculo vehiculo, List<String> errores) {
		if (tipo==null){
			errores.add(ConstantesIVTM.TEXTO_VEHICULO_NO_AUTOLIQUIDABLE);
			return;
		}
		switch (tipo) {
		case Turismo:
		case Tractores:
			if (vehiculo.getPotenciaFiscal()== null || vehiculo.getPotenciaFiscal().doubleValue()<=0){
				errores.add(ConstantesIVTM.TEXTO_POTENCIA_OBLIGATORIA);
			}
			break;
		case Camiones:
		case Remolques:
			try{
				if (vehiculo.getPesoMma()== null || vehiculo.getTara() == null || vehiculo.getPesoMma().equals(0) || vehiculo.getTara().equals(0) || Double.parseDouble(vehiculo.getPesoMma())-Double.parseDouble(vehiculo.getTara())<=0){
					errores.add(ConstantesIVTM.TEXTO_PESO_OBLIGATORIO);
				}
			} catch (NumberFormatException e) {
				errores.add(ConstantesIVTM.TEXTO_PESO_OBLIGATORIO);
			}
			break;
		case Autobuses:
			try {
				if (vehiculo.getPlazas() == null || vehiculo.getPlazas().intValue()<=0){
					errores.add(ConstantesIVTM.TEXTO_PLAZAS_OBLIGATORIA);
				}
			} catch (NumberFormatException e) {
				errores.add(ConstantesIVTM.TEXTO_PLAZAS_OBLIGATORIA);
			}
			break;
		case Ciclomotores:
		case Motocicletas:
			try {
				if (vehiculo.getCilindrada()== null || Double.parseDouble(vehiculo.getCilindrada())<=0){
					errores.add(ConstantesIVTM.TEXTO_CILINDRADA_OBLIGATORIA);
				}
			} catch (NumberFormatException e) {
				errores.add(ConstantesIVTM.TEXTO_CILINDRADA_OBLIGATORIA);
			}
			break;
		case Otro:	 
		default:
			errores.add(ConstantesIVTM.TEXTO_VEHICULO_NO_AUTOLIQUIDABLE);
			break;
		}		
	}

	private static void anadirErroresModeloVehiculo(String modelo, List<String> errores) {
		if (modelo == null || ! longitudSinBlancosMayor0(modelo)){
			errores.add(ConstantesIVTM.TEXTO_MODELO_OBLIGATORIO);
		}
	}

	private static void anadirErroresMarcaVehiculo(MarcaDgt marca, List<String> errores) {
		if ( marca == null || (marca.getMarca()== null && marca.getCodigoMarca() ==0)){
			errores.add(ConstantesIVTM.TEXTO_MARCA_OBLIGATORIA);
		}
		/* No se est� comprobando que el marca sea una de las registradas, aunque el html ya lo est� haciendo */
	}

	private static void anadirErroresBastidor(String bastidor, List<String> errores) {
		if (bastidor == null || ! longitudSinBlancosMayor0(bastidor)){
			errores.add(ConstantesIVTM.TEXTO_BASTIDOR_OBLIGATORIO);
		}
		/* No se est� comprobando que el bastidor est� bien formado */
	}

	private static void comprobarFechaIVTM(Fecha fecha, List<String> errores, String tipo) {
		if(fecha == null || fecha.isfechaNula()){
			errores.add(ConstantesIVTM.TEXTO_FECHA_OBLIGATORIA+" "+tipo);
		}else{
			Date fechaActual = new Date();
			try {
				if(fecha.getDate().after(fechaActual)){
					errores.add(ConstantesIVTM.TEXTO_FECHA_POSTERIOR+" "+tipo);
				}
			} catch (ParseException e) {
				errores.add(ConstantesIVTM.TEXTO_ERROR_FECHA+e.getMessage());
			}
		}
	}
	
	

	private static void anadirErroresTitular(TramiteTraficoDto tramite, List<String> errores) {
		PersonaDireccion personaDireccion = obtenerPersonaDireccion(tramite, org.gestoresmadrid.core.model.enumerados.TipoInterviniente.Titular.getValorEnum());
		if (personaDireccion == null){
			errores.add(ConstantesIVTM.TEXTO_FALTA_TITULAR);
		} else {
			anadirErroresPersona(personaDireccion, errores, org.gestoresmadrid.core.model.enumerados.TipoInterviniente.Titular.getNombreEnum());
		}
	}
	
	private static void anadirErroresRepresentante(TramiteTraficoDto tramite, List<String> errores) {
		PersonaDireccion personaDireccion = obtenerPersonaDireccion(tramite, org.gestoresmadrid.core.model.enumerados.TipoInterviniente.RepresentanteTitular.getValorEnum());
		if (personaDireccion!= null){
			anadirErroresPersona(personaDireccion, errores, org.gestoresmadrid.core.model.enumerados.TipoInterviniente.RepresentanteTitular.getNombreEnum());
		}
	}
	
	public static PersonaDireccion obtenerPersonaDireccion(TramiteTraficoDto tramite, String tipoBuscado){
		if (tramite== null ||tramite.getIntervinienteTraficos()==null || tramite.getIntervinienteTraficos().size()<1){
			return null;
		}
		List<IntervinienteTrafico> listaIntervinientes = tramite.getIntervinienteTraficos();
		PersonaDireccion personaDireccion = null;
		for (IntervinienteTrafico interviniente : listaIntervinientes){
			TipoInterviniente tipo = interviniente.getTipoIntervinienteBean();
			String tipoInterviniente = tipo.getTipoInterviniente();
			if (tipoInterviniente.equals(tipoBuscado)) {	
				personaDireccion = interviniente.getPersonaDireccion();
			}
		}
		return personaDireccion;
	}
	
	private static void anadirErroresPersona(PersonaDireccion personaDireccion,  List<String> errores, String tipo) {
		if (personaDireccion == null || personaDireccion.getPersona()==null || personaDireccion.getPersona().getTipoPersona()==null || personaDireccion.getDireccion()==null){
			errores.add(ConstantesIVTM.TEXTO_OBLIGATORIO_TIPO_PERSONA+" "+tipo);
		} else {
			if (personaDireccion.getPersona().getId()!=null){
				anadirErroresNIF(personaDireccion.getPersona().getId().getNif(), errores, tipo);
			} else {
				errores.add (ConstantesIVTM.TEXTO_OBLIGATORIO_DOCUMENTO_IDENTIFICACION+" "+tipo);
			}
			
			if (esPersonaFisica(personaDireccion.getPersona().getTipoPersona())){
				anadirErroresApellido1(personaDireccion.getPersona().getApellido1RazonSocial(), errores, tipo);
				anadirErroresNombre(personaDireccion.getPersona().getNombre(), errores, tipo);
			} else {
				anadirErroresApellido1(personaDireccion.getPersona().getApellido1RazonSocial(), errores, tipo);
			}
			anadirErroresDireccion(personaDireccion.getDireccion(), errores, tipo);
		}
	}

	private static void anadirErroresNombre(String nombre, List<String> errores, String tipo) {
		if (nombre==null || ! longitudSinBlancosMayor0(nombre)){
			errores.add(ConstantesIVTM.TEXTO_OBLIGATORIO_NOMBRE+" "+tipo);
		}
	}	

	private static void anadirErroresApellido1(String apellido1RazonSocial,	List<String> errores, String tipo) {
		if (apellido1RazonSocial==null || ! longitudSinBlancosMayor0(apellido1RazonSocial)){
			errores.add(ConstantesIVTM.TEXTO_OBLIGATORIO_APELLIDO+" "+tipo);
		}
	}

	private static void anadirErroresNIF(String nif, List<String> errores, String tipo) {
		/* Se podr�a tambien comprobar que el NIF, tarjeta de residente o CIF est� correctamente formado */
		if (nif==null || ! longitudSinBlancosMayor0(nif)){
			errores.add (ConstantesIVTM.TEXTO_OBLIGATORIO_DOCUMENTO_IDENTIFICACION+" "+tipo);
		}
	}

	

	private static void anadirErroresDireccion(Direccion direccion, List<String> errores, String tipo) {
		if (direccion == null){
			errores.add(ConstantesIVTM.TEXTO_OBLIGATORIA_DIRECCION+" "+tipo);
		} else {
			anadirErroresMunicipio(direccion.getMunicipio(), errores, tipo);
			anadirErroresCodigoPostal(direccion.getCodPostalCorreos(), errores, tipo);
			anadirErroresTipovia(direccion.getIdTipoVia(), errores, tipo);
			anadirErroresNombreVia(direccion.getNombreVia(), errores, tipo);
			anadirErroresNumero(direccion.getNumero(), errores, tipo);
		}
	}

	private static void anadirErroresTipovia(String tipoVia, List<String> errores, String tipo) {
		if (tipoVia == null || ! longitudSinBlancosMayor0(tipoVia)){
			errores.add(ConstantesIVTM.TEXTO_OBLIGATORIO_TIPO_VIA+" "+tipo);
		}		
	}

	private static void anadirErroresNumero(String numero, List<String> errores, String tipo) {
		if (numero == null || ! longitudSinBlancosMayor0(numero)){
			errores.add(ConstantesIVTM.TEXTO_OBLIGATORIO_NUMERO_VIA+" "+tipo);
		}
		/* No se est� comprobando que sea un n�mero, pero en el html de alta de matriculaci�n si se limita a que sea un n�mero */
	}

	private static void anadirErroresNombreVia(String nombreVia, List<String> errores, String tipo) {
		if (nombreVia == null || ! longitudSinBlancosMayor0(nombreVia)){
			errores.add(ConstantesIVTM.TEXTO_OBLIGATORIO_NOMBRE_VIA+" "+tipo);
		}
	}

	private static void anadirErroresMunicipio(Municipio municipio,	List<String> errores, String tipo) {
		if (municipio == null || municipio.getId()==null || municipio.getId().getIdMunicipio() == null || !longitudSinBlancosMayor0(municipio.getId().getIdMunicipio())){
			errores.add(ConstantesIVTM.TEXTO_OBLIGATORIO_MUNICIPIO+" "+tipo);
		}
	}

	private static void anadirErroresCodigoPostal(String codPostal,	List<String> errores, String tipo) {
		if (codPostal==null){
			errores.add(ConstantesIVTM.TEXTO_OBLIGATORIO_CODIGO_POSTAL+" "+tipo);
		} else if (codPostal.length()!=5){
			errores.add(ConstantesIVTM.TEXTO_OBLIGATORIO_CODIGO_POSTAL+" "+tipo);
		} else {
			try {
				Integer.parseInt(codPostal);
			} catch (NumberFormatException e) {
				errores.add(ConstantesIVTM.TEXTO_OBLIGATORIO_CODIGO_POSTAL+" "+tipo);
			}
		}
	}

	private static void anadirErroresEsMadrid(TramiteTraficoDto tramite, List<String> errores) {
		PersonaDireccion personaDireccion = obtenerPersonaDireccion(tramite, org.gestoresmadrid.core.model.enumerados.TipoInterviniente.Titular.getValorEnum());
		if (personaDireccion == null || personaDireccion.getDireccion()==null || personaDireccion.getDireccion().getMunicipio()==null || personaDireccion.getDireccion().getMunicipio().getProvincia() == null || personaDireccion.getDireccion().getMunicipio().getProvincia().getIdProvincia()==null || ! personaDireccion.getDireccion().getMunicipio().getProvincia().getIdProvincia().equals(ConstantesIVTM.PROVINCIA_MADRID) || personaDireccion.getDireccion().getMunicipio().getId()==null || personaDireccion.getDireccion().getMunicipio().getId().getIdMunicipio()==null || !personaDireccion.getDireccion().getMunicipio().getId().getIdMunicipio().equals(ConstantesIVTM.MUNICIPIO_MADRID)){
			errores.add(ConstantesIVTM.TEXTO_MUNICIPIO_MADRID_OBLIGATORIO);
		}
	}
	
	private static boolean longitudSinBlancosMayor0(String campo) {
		return campo.trim().length()>0;
	}
	
	private static boolean esPersonaFisica(String tipoPersona){
		return tipoPersona!=null && tipoPersona.equalsIgnoreCase(TipoPersona.Fisica.getValorEnum());
	}
	
	private boolean esPersonaJuridica (String tipoPersona){
		return tipoPersona!=null && tipoPersona.equalsIgnoreCase(TipoPersona.Juridica.getValorEnum());
	}

	/** Fin de Validaciones para el Alta - Modificacion de Autoliquidaci�n del IVTM de Madrid */

}
