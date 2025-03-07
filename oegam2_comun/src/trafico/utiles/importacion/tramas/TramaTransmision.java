package trafico.utiles.importacion.tramas;

import static trafico.utiles.constantes.ConstantesDGT.BEAN_PQ_TRANSMISION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_ACRED_REPR_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_ACRED_REPR_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_ARRENDADOR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CAMPO_BLANCO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CET;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CONCEPTO_REPR_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CP_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CP_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_DNI_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_DNI_ARRENDADOR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_DNI_REPRESENTANTE_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_DNI_REPRESENTANTE_ARRENDADOR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_DNI_REPRESENTANTE_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_DNI_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_DOMICILIO_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_DOMICILIO_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_ESCALERA_DOMICILIO_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_ESCALERA_DOMICILIO_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_CREACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_ITV;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_LIMITACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_MATRICULACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_NACIMIENTO_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_PRESENTACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FINANCIERA_LIMITACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_JEFATURA_TRAFICO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_LETRA_DOMICILIO_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_LETRA_DOMICILIO_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_LINEA_TRANSMISION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_LOCALIDAD_VEHICULO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MATRICULA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MODO_ADJUDICACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MOTIVO_ITV;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MUNICIPIO_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MUNICIPIO_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NOMBRE_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NOMBRE_CALLE_VEHICULO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NOMBRE_PROVINCIA_VEHICULO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NOMBRE_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NUM_CALLE_VEHICULO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NUM_DOCUMENTO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NUM_DOMICILIO_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NUM_DOMICILIO_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NUM_PROFESIONAL;
import static trafico.utiles.constantes.ConstantesDGT.TAM_OBSERVACIONES;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PISO_DOMICILIO_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PISO_DOMICILIO_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PRIMER_APELLIDO_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PRIMER_APELLIDO_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PROVINCIA_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PROVINCIA_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PUEBLO_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PUEBLO_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PUERTA_DOMICILIO_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PUERTA_DOMICILIO_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_REPRESENTANTE_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_REPRESENTANTE_ARRENDADOR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_REPRESENTANTE_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_SEGUNDO_APELLIDO_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_SEGUNDO_APELLIDO_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_SERVICIO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_SEXO_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_SEXO_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TASA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_LIMITACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_REG;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_TASA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_TRANSFERENCIA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_VIA_ADQUIRENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_VIA_TRANSMITENTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TUTELA_TRANSMISION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_ULT_CIFRAS_DOCUMENTO;
import static trafico.utiles.constantes.ConstantesDGT.VALOR_TIPO_LIMITACION;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;

import escrituras.beans.ResultBean;
import general.utiles.Anagrama;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarIntervinienteImport;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarTransmision;
import trafico.beans.daos.BeanPQTramiteTransmisionImport;
import trafico.beans.daos.BeanPQVehiculosGuardarImport;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.enumerados.ConceptoTutela;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class TramaTransmision {
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(TramaTransmision.class);
	
	private static String sinNumero = "SN";
	/**
	 * Metodo que obtiene el ResultBean que contiene los campos de la linea de registro de transmision
	 * que recibe como parametro
	 * 
	 * @param linea
	 * @return Devuelve el bean que contiene una linea con tipo de registro Transmision
	 */
	public static final ResultBean obtenerBean(String linea, String colegiadoCabecera){
		BeanPQTramiteTransmisionImport beanTramiteTransmision = new BeanPQTramiteTransmisionImport();
		
		BeanPQVehiculosGuardarImport beanVehiculo = new BeanPQVehiculosGuardarImport();
		BeanPQTramiteTraficoGuardarTransmision beanTramite = new BeanPQTramiteTraficoGuardarTransmision();
		BeanPQTramiteTraficoGuardarIntervinienteImport beanAdquiriente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		BeanPQTramiteTraficoGuardarIntervinienteImport beanRepresentanteAdquiriente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		BeanPQTramiteTraficoGuardarIntervinienteImport beanTransmitente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		BeanPQTramiteTraficoGuardarIntervinienteImport beanRepresentanteTransmitente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		// DRC@02-10-2012 Incidencia: 1801
		//BeanPQTramiteTraficoGuardarIntervinienteImport beanCompraventa = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		//BeanPQTramiteTraficoGuardarIntervinienteImport beanRepresentanteCompraventa = new BeanPQTramiteTraficoGuardarIntervinienteImport();		
		BeanPQTramiteTraficoGuardarIntervinienteImport beanArrendador = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		BeanPQTramiteTraficoGuardarIntervinienteImport beanRepresentanteArrendador = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		BeanPQTramiteTraficoGuardarIntervinienteImport beanCompraventa = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		BeanPQTramiteTraficoGuardarIntervinienteImport beanRepresentanteCompraventa = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		
		//A los intevinientes, les ponemos manualmente cual será su tipo_interviniente
		beanAdquiriente.setP_TIPO_INTERVINIENTE(TipoInterviniente.Adquiriente.getValorEnum()); //Adquiriente
		beanRepresentanteAdquiriente.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteAdquiriente.getValorEnum()); //Representante Adquiriente
		beanTransmitente.setP_TIPO_INTERVINIENTE(TipoInterviniente.TransmitenteTrafico.getValorEnum()); //Transmitente
		beanRepresentanteTransmitente.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteTransmitente.getValorEnum()); //Representante Transmitente
		// DRC@02-10-2012 Incidencia: 1801
		beanArrendador.setP_TIPO_INTERVINIENTE(TipoInterviniente.Arrendador.getValorEnum()); //Arrendador
		beanRepresentanteArrendador.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteArrendatario.getValorEnum()); //Representante Arrendador
		beanCompraventa.setP_TIPO_INTERVINIENTE(TipoInterviniente.Compraventa.getValorEnum()); //Arrendador
		beanRepresentanteCompraventa.setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteCompraventa.getValorEnum()); //Representante Arrendador
		
		//bean que devolverá el resultado de la función
		ResultBean beanResult = new ResultBean();
		
		//util para convertir 
		UtilesConversiones utilConv = ContextoSpring.getInstance().getBean(UtilesConversiones.class);

		int pos=0;		
		Boolean error = false;
		String mensajeError = "";
		
		String valor = linea.substring(pos, pos + TAM_TIPO_REG);
		//tipo de registro, en este caso "030"
		pos = pos + TAM_TIPO_REG;
		
		valor = linea.substring(pos, pos + TAM_TASA);
		beanTramite.setP_CODIGO_TASA(valor.trim());
		pos = pos + TAM_TASA;
		
		valor = linea.substring(pos, pos + TAM_FECHA_PRESENTACION);
		beanTramite.setP_FECHA_PRESENTACION(aaaammddToTimestamp(valor.trim()));
		pos = pos + TAM_FECHA_PRESENTACION;

		valor = linea.substring(pos, pos + TAM_JEFATURA_TRAFICO);
		beanTramite.setP_JEFATURA_PROVINCIAL(valor.trim());
		pos = pos + TAM_JEFATURA_TRAFICO;
		
		valor = linea.substring(pos, pos + TAM_MATRICULA);
		beanVehiculo.setP_MATRICULA(valor.trim());
		pos = pos + TAM_MATRICULA;

		valor = linea.substring(pos, pos + TAM_FECHA_MATRICULACION);
		beanVehiculo.setP_FECHA_MATRICULACION(aaaammddToTimestamp(valor));
		pos = pos + TAM_FECHA_MATRICULACION;

		valor = linea.substring(pos, pos + TAM_FECHA_ITV);
		beanVehiculo.setP_FECHA_ITV(aaaammddToTimestamp(valor.trim()));
		pos = pos + TAM_FECHA_ITV;

		valor = linea.substring(pos, pos + TAM_DNI_TRANSMITENTE);
		beanTransmitente.setP_NIF(valor.trim());
		pos = pos + TAM_DNI_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_DNI_ADQUIRENTE);
		beanAdquiriente.setP_NIF(valor.trim());
		pos = pos + TAM_DNI_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_PRIMER_APELLIDO_ADQUIRENTE);
		beanAdquiriente.setP_APELLIDO1_RAZON_SOCIAL(valor.trim());
		pos = pos + TAM_PRIMER_APELLIDO_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_SEGUNDO_APELLIDO_ADQUIRENTE);
		beanAdquiriente.setP_APELLIDO2(valor.trim());
		pos = pos + TAM_SEGUNDO_APELLIDO_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_NOMBRE_ADQUIRENTE);
		beanAdquiriente.setP_NOMBRE(valor.trim());
		pos = pos + TAM_NOMBRE_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_DOMICILIO_ADQUIRENTE);
		HashMap<String,String> domicilio = utilConv.separarDomicilio(valor.trim());
		beanAdquiriente.setP_VIA(domicilio.get("calle"));
		beanAdquiriente.setP_NUMERO(domicilio.get("numero"));
		beanAdquiriente.setP_PUERTA(domicilio.get("puerta"));
		beanAdquiriente.setP_ID_TIPO_DGT(!"".equals(domicilio.get("tipoVia"))?domicilio.get("tipoVia"):"");
		pos = pos + TAM_DOMICILIO_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_MUNICIPIO_ADQUIRENTE);
		beanAdquiriente.setP_MUNICIPIO(valor.trim());
		pos = pos + TAM_MUNICIPIO_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_PUEBLO_ADQUIRENTE);
		beanAdquiriente.setP_PUEBLO_LIT(valor.trim());
		pos = pos + TAM_PUEBLO_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_PROVINCIA_ADQUIRENTE);
		beanAdquiriente.setP_ID_PROVINCIA(utilConv.getIdProvinciaFromSiglas(valor.trim())); //Pasamos las siglas al id_provincia)
		pos = pos + TAM_PROVINCIA_ADQUIRENTE;
		
		valor = linea.substring(pos, pos + TAM_CP_ADQUIRENTE);
		beanAdquiriente.setP_COD_POSTAL(valor.trim());
		pos = pos + TAM_CP_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_SEXO_ADQUIRENTE);
		//if (!"X".equals(valor.trim().toUpperCase())) beanAdquiriente.setP_SEXO(valor.trim());
		beanAdquiriente.setP_SEXO(valor.trim());
		pos = pos + TAM_SEXO_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_FECHA_NACIMIENTO_ADQUIRENTE);
		beanAdquiriente.setP_FECHA_NACIMIENTO(aaaammddToTimestamp(valor.trim()));
		pos = pos + TAM_FECHA_NACIMIENTO_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_TIPO_TRANSFERENCIA);
		beanTramite.setP_TIPO_TRANSFERENCIA(valor.trim());
		pos = pos + TAM_TIPO_TRANSFERENCIA;

		valor = linea.substring(pos, pos + TAM_TUTELA_TRANSMISION);
		if ("S".equals(valor.trim().toUpperCase())) beanRepresentanteTransmitente.setP_CONCEPTO_REPRE(ConceptoTutela.Tutela.getNombreEnum());
		pos = pos + TAM_TUTELA_TRANSMISION;

		String tipoLimitacion = linea.substring(pos, pos + TAM_TIPO_LIMITACION);
		if ("E".equals(tipoLimitacion.trim().toUpperCase())) 
			beanTramite.setP_IEDTM("E");
		else 
			beanTramite.setP_IEDTM("");
		
		pos = pos + TAM_TIPO_LIMITACION;
		
		//Si el tipo de limitación es 'E' se recuperarn los valores de fecha y financiera 
		if(VALOR_TIPO_LIMITACION.equals(tipoLimitacion)){
			valor = linea.substring(pos, pos + TAM_FECHA_LIMITACION);
			beanTramite.setP_FECHA_IEDTM(aaaammddToTimestamp(valor.trim()));
			pos = pos + TAM_FECHA_LIMITACION;
	
			valor = linea.substring(pos, pos + TAM_CAMPO_BLANCO);
			//blancos
			pos = pos + TAM_CAMPO_BLANCO;
	
			valor = linea.substring(pos, pos + TAM_FINANCIERA_LIMITACION);
			beanTramite.setP_FINANCIERA_IEDTM(valor.trim());
			pos = pos + TAM_FINANCIERA_LIMITACION;
		} else {
			pos = pos + TAM_FECHA_LIMITACION;
			pos = pos + TAM_CAMPO_BLANCO;
			pos = pos + TAM_FINANCIERA_LIMITACION;
		}

		valor = linea.substring(pos, pos + TAM_MOTIVO_ITV);
		beanVehiculo.setP_ID_MOTIVO_ITV(valor.trim());
		pos = pos + TAM_MOTIVO_ITV;
		
		valor = linea.substring(pos, pos + TAM_NUM_DOCUMENTO);
		beanTramite.setP_REF_PROPIA(valor);
		pos = pos + TAM_NUM_DOCUMENTO;

		valor = linea.substring(pos, pos + TAM_NOMBRE_TRANSMITENTE);
		beanTransmitente.setP_NOMBRE(valor.trim());
		pos = pos + TAM_NOMBRE_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_ACRED_REPR_ADQUIRENTE);
		beanRepresentanteAdquiriente.setP_DATOS_DOCUMENTO(valor.trim());
		pos = pos + TAM_ACRED_REPR_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_REPRESENTANTE_ADQUIRENTE);
		String[] separa = valor.trim().split(", ");
		if (separa.length>1) {
			beanRepresentanteAdquiriente.setP_APELLIDO1_RAZON_SOCIAL(separa[0]);
			beanRepresentanteAdquiriente.setP_NOMBRE(separa[1]);
		}
		else beanRepresentanteAdquiriente.setP_APELLIDO1_RAZON_SOCIAL(valor.trim()); //Si hubiera que separar el nombre, cogeríamos la primera palabra como nombre
		pos = pos + TAM_REPRESENTANTE_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_REPRESENTANTE_TRANSMITENTE);
		separa = valor.split(", ");
		if (separa.length>1) {
			beanRepresentanteTransmitente.setP_APELLIDO1_RAZON_SOCIAL(separa[0]);
			beanRepresentanteTransmitente.setP_NOMBRE(separa[1]);
		}
		else beanRepresentanteTransmitente.setP_APELLIDO1_RAZON_SOCIAL(valor.trim()); //Si hubiera que separar el nombre, cogeríamos la primera palabra como nombre
		pos = pos + TAM_REPRESENTANTE_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_DNI_REPRESENTANTE_ADQUIRENTE);
		beanRepresentanteAdquiriente.setP_NIF(valor.trim());
		pos = pos + TAM_DNI_REPRESENTANTE_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_DNI_REPRESENTANTE_TRANSMITENTE);
		beanRepresentanteTransmitente.setP_NIF(valor.trim());
		pos = pos + TAM_DNI_REPRESENTANTE_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_CONCEPTO_REPR_ADQUIRENTE);
		if ("APODERADOS".equals(valor.trim())) valor = "APODERADO"; //No se muy bien por qué, pero a veces viene en plural en el fichero. Lo normalizamos.
		beanRepresentanteAdquiriente.setP_CONCEPTO_REPRE(valor);
		pos = pos + TAM_CONCEPTO_REPR_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_ARRENDADOR);
		separa = valor.split(", ");
		if (separa.length>1) {
			beanArrendador.setP_APELLIDO1_RAZON_SOCIAL(separa[0]);
			beanArrendador.setP_NOMBRE(separa[1]);
		}
		else beanArrendador.setP_APELLIDO1_RAZON_SOCIAL(valor.trim()); //Si hubiera que separar el nombre, cogeríamos la primera palabra como nombre
		pos = pos + TAM_ARRENDADOR;
		
		valor = linea.substring(pos, pos + TAM_DNI_ARRENDADOR);
		beanArrendador.setP_NIF(valor.trim());
		pos = pos + TAM_DNI_ARRENDADOR;

		valor = linea.substring(pos, pos + TAM_REPRESENTANTE_ARRENDADOR);
		separa = valor.split(", ");
		if (separa.length>1) {
			beanRepresentanteArrendador.setP_APELLIDO1_RAZON_SOCIAL(separa[0]);
			beanRepresentanteArrendador.setP_NOMBRE(separa[1]);
		}
		else beanRepresentanteArrendador.setP_APELLIDO1_RAZON_SOCIAL(valor.trim()); //Si hubiera que separar el nombre, cogeríamos la primera palabra como nombre
		pos = pos + TAM_REPRESENTANTE_ARRENDADOR;

		valor = linea.substring(pos, pos + TAM_DNI_REPRESENTANTE_ARRENDADOR);
		beanRepresentanteArrendador.setP_NIF(valor.trim());
		pos = pos + TAM_DNI_REPRESENTANTE_ARRENDADOR;
		
		// DRC@03-10-2012 Incidencia: 1801
//		valor = linea.substring(pos, pos + TAM_COMPRAVENTA);
//		separa = valor.split(", ");
//		if (separa.length>1) {
//			beanArrendador.setP_APELLIDO1_RAZON_SOCIAL(separa[0]);
//			beanArrendador.setP_NOMBRE(separa[1]);
//		}
//		else beanArrendador.setP_APELLIDO1_RAZON_SOCIAL(valor.trim()); //Si hubiera que separar el nombre, cogeríamos la primera palabra como nombre
//		pos = pos + TAM_COMPRAVENTA;
//		
//		valor = linea.substring(pos, pos + TAM_DNI_COMPRAVENTA);
//		beanArrendador.setP_NIF(valor.trim());
//		pos = pos + TAM_DNI_COMPRAVENTA;
//
//		valor = linea.substring(pos, pos + TAM_REPRESENTANTE_COMPRAVENTA);
//		separa = valor.split(", ");
//		if (separa.length>1) {
//			beanRepresentanteArrendador.setP_APELLIDO1_RAZON_SOCIAL(separa[0]);
//			beanRepresentanteArrendador.setP_NOMBRE(separa[1]);
//		}
//		else beanRepresentanteArrendador.setP_APELLIDO1_RAZON_SOCIAL(valor.trim()); //Si hubiera que separar el nombre, cogeríamos la primera palabra como nombre
//		pos = pos + TAM_REPRESENTANTE_COMPRAVENTA;
//
//		valor = linea.substring(pos, pos + TAM_DNI_REPRESENTANTE_COMPRAVENTA);
//		beanRepresentanteArrendador.setP_NIF(valor.trim());
//		pos = pos + TAM_DNI_REPRESENTANTE_COMPRAVENTA;
		// FIN DRC@03-10-2012 Incidencia: 1801

		valor = linea.substring(pos, pos + TAM_LOCALIDAD_VEHICULO);
		beanVehiculo.setP_MUNICIPIO(valor.trim());
		pos = pos + TAM_LOCALIDAD_VEHICULO;

		valor = linea.substring(pos, pos + TAM_NOMBRE_PROVINCIA_VEHICULO);
		beanVehiculo.setP_ID_PROVINCIA(utilConv.getIdProvinciaFromNombre(valor.trim()));
		pos = pos + TAM_NOMBRE_PROVINCIA_VEHICULO;

		valor = linea.substring(pos, pos + TAM_NOMBRE_CALLE_VEHICULO);
		//INTENTAMOS OBTENER EL TIPO DE VIA SI VINIERA
		String[] separo = valor.split(" ");
		if (separo.length>1) {
			String tipoViaVehiculo = separo[0];
			//Si las primeras letras son de un tipo de vía
			String tipoViaVehiculoConvertido = utilConv.getIdDGTFromKey(tipoViaVehiculo);
			if (!"-1".equals(tipoViaVehiculoConvertido)) {
				if ("6".equals(tipoViaVehiculoConvertido)) tipoViaVehiculoConvertido="41"; //calle
				if ("44".equals(tipoViaVehiculoConvertido)) tipoViaVehiculoConvertido="5"; //camino
				if ("24".equals(tipoViaVehiculoConvertido)) tipoViaVehiculoConvertido="29"; //plaza
				if ("34".equals(tipoViaVehiculoConvertido)) tipoViaVehiculoConvertido="29";
				beanVehiculo.setP_ID_TIPO_DGT(tipoViaVehiculoConvertido);
				String via = "";
				for (int i=1;i<separo.length;i++) {
					via = separo[i] + " ";					
				}
				via = via.trim();
				beanVehiculo.setP_VIA(via);
			}
		}		
		if (null==beanVehiculo.getP_VIA() || "".equals(beanVehiculo.getP_VIA())) beanVehiculo.setP_VIA(valor.trim());
		//if (!"".equals(beanVehiculo.getP_VIA())) beanVehiculo.setP_ID_TIPO_DGT("");
		pos = pos + TAM_NOMBRE_CALLE_VEHICULO;

		valor = linea.substring(pos, pos + TAM_NUM_CALLE_VEHICULO);
		beanVehiculo.setP_NUMERO(valor.trim());
		pos = pos + TAM_NUM_CALLE_VEHICULO;
		
		valor = linea.substring(pos, pos + TAM_SERVICIO);
		beanVehiculo.setP_ID_SERVICIO(utilConv.servicioDestinoNuevo(valor.trim()));
		pos = pos + TAM_SERVICIO;

		valor = linea.substring(pos, pos + TAM_MODO_ADJUDICACION);
		beanTramite.setP_MODO_ADJUDICACION(utilConv.convertirModoAdjudicacion(valor.trim())); //Hacemos una conversión lógica entre modos dgt, y modos oegam2
		pos = pos + TAM_MODO_ADJUDICACION;

		valor = linea.substring(pos, pos + TAM_NUM_PROFESIONAL);
		//le quitamos los ceros por la izquierda
		try {
			if (!(valor==null) && !("".equals(valor.trim()))) valor = (new Integer(valor.trim())).toString();
			if (valor.length()==5) {
				valor = valor.substring(0, 4);
			}
			
			while (valor.length()<4) {
				valor = "0" + valor;
			}
		} catch (Exception e) {
			log.error("el numero de colegiado no es un valor númerico válido");
			valor = "";
		}
		/*
		 * Primero comprobamos si el colegiado de la cabecera del fichero, coincide con el colegiado de trámite
		 */
		if (!colegiadoCabecera.equals(valor)) {
			if (error) mensajeError = mensajeError + ". El usuario de la cabecera del fichero no coincide con el del trámite";
			else mensajeError = "El usuario de la cabecera del fichero no coincide con el del trámite";
			error = true;
			beanResult.setError(error);
			beanResult.setMensaje(mensajeError);
			return beanResult;
		}
		/*
		 * Si el colegiado de sesión tiene permiso de administración:
		 * ---> Se guarda el número de colegiado de la linea del archivo
		 * Si el colegiado de sesión tiene permiso de colegiado:
		 * ---> Se comprueba que el número de colegiado del archivo pertenece al contrato del colegio del colegiado de sesión 
		 */
		if (getUtilesColegiado().tienePermisoAdmin()) {
			beanTramite.setP_NUM_COLEGIADO(valor);
			beanVehiculo.setP_NUM_COLEGIADO(valor);
			beanAdquiriente.setP_NUM_COLEGIADO(valor);
			beanRepresentanteAdquiriente.setP_NUM_COLEGIADO(valor);
			beanTransmitente.setP_NUM_COLEGIADO(valor);
			beanRepresentanteTransmitente.setP_NUM_COLEGIADO(valor);
			beanArrendador.setP_NUM_COLEGIADO(valor);
			beanRepresentanteArrendador.setP_NUM_COLEGIADO(valor);
		} else if (getUtilesColegiado().tienePermisoColegio()) {
			List<String> listaColegiados = getUtilesColegiado().getNumColegiadosDelContrato();
			Boolean puede = false;
			int i = 0;
			while ((!puede) && (listaColegiados.size()>i)) {
				puede = puede || valor.equals(listaColegiados.get(i));
				i++;
			}
			
			if (puede) {
				beanTramite.setP_NUM_COLEGIADO(valor);
				beanVehiculo.setP_NUM_COLEGIADO(valor);
				beanAdquiriente.setP_NUM_COLEGIADO(valor);
				beanRepresentanteAdquiriente.setP_NUM_COLEGIADO(valor);
				beanTransmitente.setP_NUM_COLEGIADO(valor);
				beanRepresentanteTransmitente.setP_NUM_COLEGIADO(valor);
				beanArrendador.setP_NUM_COLEGIADO(valor);
				beanRepresentanteArrendador.setP_NUM_COLEGIADO(valor);
			} else {
				if (error) mensajeError = mensajeError + ". El usuario ("+ getUtilesColegiado().getNumColegiadoSession() +") no tiene permisos para realizar la transmisión para el colegiado del fichero (" + valor + ")";
				else mensajeError = "El usuario ("+ getUtilesColegiado().getNumColegiadoSession() +") no tiene permisos para realizar la transmisión para el colegiado del fichero (" + valor + ")";
				error = true;				
				beanResult.setError(error);
				beanResult.setMensaje(mensajeError);
				return beanResult;
			}
		} else { 	//Si el usuario de sesión es un colegiado cualquiera
			if (valor.equals(getUtilesColegiado().getNumColegiadoSession())) {
				beanTramite.setP_NUM_COLEGIADO(valor);
				beanVehiculo.setP_NUM_COLEGIADO(valor);
				beanAdquiriente.setP_NUM_COLEGIADO(valor);
				beanRepresentanteAdquiriente.setP_NUM_COLEGIADO(valor);
				beanTransmitente.setP_NUM_COLEGIADO(valor);
				beanRepresentanteTransmitente.setP_NUM_COLEGIADO(valor);
				beanArrendador.setP_NUM_COLEGIADO(valor);
				beanRepresentanteArrendador.setP_NUM_COLEGIADO(valor);
			} else {
				if (error) mensajeError = mensajeError + ". El usuario ("+ getUtilesColegiado().getNumColegiadoSession() +") no es el mismo colegiado que el del trámite del fichero (" + valor + ")";
				else mensajeError = "El usuario ("+ getUtilesColegiado().getNumColegiadoSession() +") no es el mismo colegiado que el del trámite del fichero (" + valor + ")";
				error = true;				
				beanResult.setError(error);
				beanResult.setMensaje(mensajeError);
				return beanResult;
			}
		}
		pos = pos + TAM_NUM_PROFESIONAL;

		valor = linea.substring(pos, pos + TAM_ULT_CIFRAS_DOCUMENTO);
		//no se encuentra correspondencia
		pos = pos + TAM_ULT_CIFRAS_DOCUMENTO;

		valor = linea.substring(pos, pos + TAM_FECHA_CREACION);
		//no se encuentra correspondencia
		pos = pos + TAM_FECHA_CREACION;

		valor = linea.substring(pos, pos + TAM_PRIMER_APELLIDO_TRANSMITENTE);
		beanTransmitente.setP_APELLIDO1_RAZON_SOCIAL(valor.trim());
		pos = pos + TAM_PRIMER_APELLIDO_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_SEGUNDO_APELLIDO_TRANSMITENTE);
		beanTransmitente.setP_APELLIDO2(valor.trim());
		pos = pos + TAM_SEGUNDO_APELLIDO_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_DOMICILIO_TRANSMITENTE);
		domicilio = utilConv.separarDomicilio(valor.trim());
		beanTransmitente.setP_VIA(domicilio.get("calle"));
		beanTransmitente.setP_NUMERO(domicilio.get("numero"));
		beanTransmitente.setP_PUERTA(domicilio.get("puerta"));
		beanTransmitente.setP_ID_TIPO_DGT(!"".equals(domicilio.get("tipoVia"))?domicilio.get("tipoVia"):"");
		pos = pos + TAM_DOMICILIO_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_MUNICIPIO_TRANSMITENTE);
		beanTransmitente.setP_MUNICIPIO(valor.trim());
		pos = pos + TAM_MUNICIPIO_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_PUEBLO_TRANSMITENTE);
		beanTransmitente.setP_PUEBLO_LIT(valor.trim());
		pos = pos + TAM_PUEBLO_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_PROVINCIA_TRANSMITENTE);
		beanTransmitente.setP_ID_PROVINCIA(utilConv.getIdProvinciaFromSiglas(valor.trim())); //Pasamos las siglas al id_provincia)
		pos = pos + TAM_PROVINCIA_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_CP_TRANSMITENTE);
		beanTransmitente.setP_COD_POSTAL(valor.trim());
		pos = pos + TAM_CP_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_SEXO_TRANSMITENTE);
		//if (!"X".equals(valor.trim().toUpperCase()) && valor.trim().length()==1) beanTransmitente.setP_SEXO(valor.trim());
		beanTransmitente.setP_SEXO(valor.trim());
		pos = pos + TAM_SEXO_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_ACRED_REPR_TRANSMITENTE);
		beanRepresentanteTransmitente.setP_DATOS_DOCUMENTO(valor.trim());
		pos = pos + TAM_ACRED_REPR_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_OBSERVACIONES);
		beanTramite.setP_ANOTACIONES(valor.trim());
		pos = pos + TAM_OBSERVACIONES;

		valor = linea.substring(pos, pos + TAM_TIPO_VIA_TRANSMITENTE);
		if ("6".equals(valor.trim())) valor="41"; //calle
		if ("44".equals(valor.trim())) valor="5"; //camino
		if ("24".equals(valor.trim())) valor="29"; //plaza
		if ("34".equals(valor.trim())) valor="29";
		if (!"".equals(valor.trim())) beanTransmitente.setP_ID_TIPO_DGT(valor.trim());
		else if ("".equals(beanTransmitente.getP_ID_TIPO_DGT()) || null==beanTransmitente.getP_ID_TIPO_DGT()) beanTransmitente.setP_ID_TIPO_DGT("");
		pos = pos + TAM_TIPO_VIA_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_NUM_DOMICILIO_TRANSMITENTE);
		if (!"".equals(valor.trim())) 
			beanTransmitente.setP_NUMERO(valor.trim());
		pos = pos + TAM_NUM_DOMICILIO_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_LETRA_DOMICILIO_TRANSMITENTE);
		beanTransmitente.setP_LETRA(valor.trim());
		pos = pos + TAM_LETRA_DOMICILIO_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_ESCALERA_DOMICILIO_TRANSMITENTE);
		beanTransmitente.setP_ESCALERA(valor.trim());
		pos = pos + TAM_ESCALERA_DOMICILIO_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_PISO_DOMICILIO_TRANSMITENTE);
		beanTransmitente.setP_PLANTA(valor.trim());
		pos = pos + TAM_PISO_DOMICILIO_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_PUERTA_DOMICILIO_TRANSMITENTE);
		beanTransmitente.setP_PUERTA(!"".equals(valor.trim())?valor.trim():beanTransmitente.getP_PUERTA());
		pos = pos + TAM_PUERTA_DOMICILIO_TRANSMITENTE;

		valor = linea.substring(pos, pos + TAM_TIPO_VIA_ADQUIRENTE);
		if ("6".equals(valor.trim())) valor="41"; //calle
		if ("44".equals(valor.trim())) valor="5"; //camino
		if ("24".equals(valor.trim())) valor="29"; //plaza
		if ("34".equals(valor.trim())) valor="29";
		if (!"".equals(valor.trim())) beanAdquiriente.setP_ID_TIPO_DGT(valor.trim());
		else if ("".equals(beanAdquiriente.getP_ID_TIPO_DGT()) || null==beanAdquiriente.getP_ID_TIPO_DGT()) beanAdquiriente.setP_ID_TIPO_DGT("");
		pos = pos + TAM_TIPO_VIA_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_NUM_DOMICILIO_ADQUIRENTE);
		if (!"".equals(valor.trim())) 
			beanAdquiriente.setP_NUMERO(valor.trim());
		pos = pos + TAM_NUM_DOMICILIO_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_LETRA_DOMICILIO_ADQUIRENTE);
		beanAdquiriente.setP_LETRA(valor.trim());
		pos = pos + TAM_LETRA_DOMICILIO_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_ESCALERA_DOMICILIO_ADQUIRENTE);
		beanAdquiriente.setP_ESCALERA(valor.trim());
		pos = pos + TAM_ESCALERA_DOMICILIO_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_PISO_DOMICILIO_ADQUIRENTE);
		beanAdquiriente.setP_PLANTA(valor.trim());
		pos = pos + TAM_PISO_DOMICILIO_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_PUERTA_DOMICILIO_ADQUIRENTE);
		beanAdquiriente.setP_PUERTA(!"".equals(valor.trim())?valor.trim():beanAdquiriente.getP_PUERTA());
		pos = pos + TAM_PUERTA_DOMICILIO_ADQUIRENTE;

		valor = linea.substring(pos, pos + TAM_TIPO_TASA);
		//no hay equivalencia en el PQ
		pos = pos + TAM_TIPO_TASA;

		if(linea.length()==TAM_LINEA_TRANSMISION) {
			valor = linea.substring(pos, pos + TAM_CET);
			beanTramite.setP_CET_ITP(valor.trim());
			pos = pos + TAM_CET;
		} else {
			beanTramite.setP_CET_ITP("");
		}
		
		//Seteamos el idContrato
		BigDecimal idContrato = getUtilesColegiado().getIdContratoSessionBigDecimal();
		BigDecimal idUsuario = getUtilesColegiado().getIdUsuarioSessionBigDecimal();
		beanTramite.setP_ID_CONTRATO_SESSION(idContrato);
		beanTramite.setP_ID_USUARIO(idUsuario);
		beanAdquiriente.setP_ID_CONTRATO_SESSION(idContrato);
		beanAdquiriente.setP_ID_USUARIO(idUsuario);
		beanRepresentanteAdquiriente.setP_ID_CONTRATO_SESSION(idContrato);
		beanRepresentanteAdquiriente.setP_ID_USUARIO(idUsuario);
		beanTransmitente.setP_ID_CONTRATO_SESSION(idContrato);
		beanTransmitente.setP_ID_USUARIO(idUsuario);
		beanRepresentanteTransmitente.setP_ID_CONTRATO_SESSION(idContrato);
		beanRepresentanteTransmitente.setP_ID_USUARIO(idUsuario);
		// DRC@03-10-212 Incidencia: 1801
		beanArrendador.setP_ID_CONTRATO_SESSION(idContrato);
		beanArrendador.setP_ID_USUARIO(idUsuario);
		beanRepresentanteArrendador.setP_ID_CONTRATO_SESSION(idContrato);
		beanRepresentanteArrendador.setP_ID_USUARIO(idUsuario);		
		beanCompraventa.setP_ID_CONTRATO_SESSION(idContrato);
		beanCompraventa.setP_ID_USUARIO(idUsuario);
		beanRepresentanteCompraventa.setP_ID_CONTRATO_SESSION(idContrato);
		beanRepresentanteCompraventa.setP_ID_USUARIO(idUsuario);
		
		//Añadimos el anagrama al adquiriente
		if (beanAdquiriente.getP_NIF()!=null && beanAdquiriente.getP_APELLIDO1_RAZON_SOCIAL()!=null 
				&& !"".equals(beanAdquiriente.getP_NIF()) && !"".equals(beanAdquiriente.getP_APELLIDO1_RAZON_SOCIAL())
				&& Character.isLetter(beanAdquiriente.getP_NIF().charAt(0))) {
			beanAdquiriente.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(beanAdquiriente.getP_APELLIDO1_RAZON_SOCIAL(), beanAdquiriente.getP_NIF()));
		}
		//Añadimos el anagrama al transmitente
		if (beanTransmitente.getP_NIF()!=null && beanTransmitente.getP_APELLIDO1_RAZON_SOCIAL()!=null 
				&& !"".equals(beanTransmitente.getP_NIF()) && !"".equals(beanTransmitente.getP_APELLIDO1_RAZON_SOCIAL())
				&& Character.isLetter(beanTransmitente.getP_NIF().charAt(0))) {
			beanTransmitente.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(beanTransmitente.getP_APELLIDO1_RAZON_SOCIAL(), beanTransmitente.getP_NIF()));
		}
		//Añadimos el anagrama al arrendador
		if (beanArrendador.getP_NIF()!=null && beanArrendador.getP_APELLIDO1_RAZON_SOCIAL()!=null 
				&& !"".equals(beanArrendador.getP_NIF()) && !"".equals(beanArrendador.getP_APELLIDO1_RAZON_SOCIAL())
				&& Character.isLetter(beanArrendador.getP_NIF().charAt(0))) {
			beanArrendador.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(beanArrendador.getP_APELLIDO1_RAZON_SOCIAL(), beanArrendador.getP_NIF()));
		}
		// DRC@03-10-2012 Incidencia: 1801
		//Añadimos el anagrama al beanCompraventa
		if (beanCompraventa.getP_NIF()!=null && beanCompraventa.getP_APELLIDO1_RAZON_SOCIAL()!=null 
				&& !"".equals(beanCompraventa.getP_NIF()) && !"".equals(beanCompraventa.getP_APELLIDO1_RAZON_SOCIAL())
				&& Character.isLetter(beanCompraventa.getP_NIF().charAt(0))) {
			beanCompraventa.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(beanCompraventa.getP_APELLIDO1_RAZON_SOCIAL(), beanCompraventa.getP_NIF()));
		}
/*		//Añadimos el anagrama al representante del adquiriente
		if (beanRepresentanteAdquiriente.getP_NIF()!=null && beanRepresentanteAdquiriente.getP_APELLIDO1_RAZON_SOCIAL()!=null 
				&& !"".equals(beanRepresentanteAdquiriente.getP_NIF()) && !"".equals(beanRepresentanteAdquiriente.getP_APELLIDO1_RAZON_SOCIAL())
				&& Character.isLetter(beanRepresentanteAdquiriente.getP_NIF().charAt(0))) {
			beanRepresentanteAdquiriente.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(beanRepresentanteAdquiriente.getP_APELLIDO1_RAZON_SOCIAL(), beanRepresentanteAdquiriente.getP_NIF()));
		}
		//Añadimos el anagrama al representante del transmitente
		if (beanRepresentanteTransmitente.getP_NIF()!=null && beanRepresentanteTransmitente.getP_APELLIDO1_RAZON_SOCIAL()!=null 
				&& !"".equals(beanRepresentanteTransmitente.getP_NIF()) && !"".equals(beanRepresentanteTransmitente.getP_APELLIDO1_RAZON_SOCIAL())
				&& Character.isLetter(beanRepresentanteTransmitente.getP_NIF().charAt(0))) {
			beanRepresentanteTransmitente.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(beanRepresentanteTransmitente.getP_APELLIDO1_RAZON_SOCIAL(), beanRepresentanteTransmitente.getP_NIF()));
		}
		//Añadimos el anagrama al representante del arrendador
		if (beanRepresentanteArrendador.getP_NIF()!=null && beanRepresentanteArrendador.getP_APELLIDO1_RAZON_SOCIAL()!=null 
				&& !"".equals(beanRepresentanteArrendador.getP_NIF()) && !"".equals(beanRepresentanteArrendador.getP_APELLIDO1_RAZON_SOCIAL())
				&& Character.isLetter(beanRepresentanteArrendador.getP_NIF().charAt(0))) {
			beanRepresentanteArrendador.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(beanRepresentanteArrendador.getP_APELLIDO1_RAZON_SOCIAL(), beanRepresentanteArrendador.getP_NIF()));
		}*/
		
		String TIPO_DGT_BLANCO = "23";
		//Añadimos el tipo de vía y el número a la dirección del vehículo:
		if ((beanVehiculo.getP_VIA()!=null && !"".equals(beanVehiculo.getP_VIA())) &&
			(beanVehiculo.getP_MUNICIPIO()!=null && !"".equals(beanVehiculo.getP_MUNICIPIO())) &&
			(beanVehiculo.getP_COD_POSTAL()!=null && !"".equals(beanVehiculo.getP_COD_POSTAL())) &&
			(beanVehiculo.getP_ID_PROVINCIA()!=null && !"".equals(beanVehiculo.getP_ID_PROVINCIA()))) {
				if (!(beanVehiculo.getP_ID_TIPO_DGT()!=null && !"".equals(beanVehiculo.getP_ID_TIPO_DGT())))
					beanVehiculo.setP_ID_TIPO_DGT(TIPO_DGT_BLANCO);
				if (!(beanVehiculo.getP_NUMERO()!=null && !"".equals(beanVehiculo.getP_NUMERO())))
					beanVehiculo.setP_NUMERO(sinNumero);
		}		
		
		//Añadimos el tipo de vía y el número a la dirección del transmitente:
		if ((beanTransmitente.getP_VIA()!=null && !"".equals(beanTransmitente.getP_VIA())) &&
			(beanTransmitente.getP_MUNICIPIO()!=null && !"".equals(beanTransmitente.getP_MUNICIPIO())) &&
			(beanTransmitente.getP_COD_POSTAL()!=null && !"".equals(beanTransmitente.getP_COD_POSTAL())) &&
			(beanTransmitente.getP_ID_PROVINCIA()!=null && !"".equals(beanTransmitente.getP_ID_PROVINCIA()))) {
				if (!(beanTransmitente.getP_ID_TIPO_DGT()!=null && !"".equals(beanTransmitente.getP_ID_TIPO_DGT())))
					beanTransmitente.setP_ID_TIPO_DGT(TIPO_DGT_BLANCO);
				if (!(beanTransmitente.getP_NUMERO()!=null && !"".equals(beanTransmitente.getP_NUMERO())))
					beanTransmitente.setP_NUMERO(sinNumero);
		}
		
		//Añadimos el tipo de vía y el número a la dirección del adquiriente:
		if ((beanAdquiriente.getP_VIA()!=null && !"".equals(beanAdquiriente.getP_VIA())) &&
			(beanAdquiriente.getP_MUNICIPIO()!=null && !"".equals(beanAdquiriente.getP_MUNICIPIO())) &&
			(beanAdquiriente.getP_COD_POSTAL()!=null && !"".equals(beanAdquiriente.getP_COD_POSTAL())) &&
			(beanAdquiriente.getP_ID_PROVINCIA()!=null && !"".equals(beanAdquiriente.getP_ID_PROVINCIA()))) {
				if (!(beanAdquiriente.getP_ID_TIPO_DGT()!=null && !"".equals(beanAdquiriente.getP_ID_TIPO_DGT())))
					beanAdquiriente.setP_ID_TIPO_DGT(TIPO_DGT_BLANCO);
				if (!(beanAdquiriente.getP_NUMERO()!=null && !"".equals(beanAdquiriente.getP_NUMERO())))
					beanAdquiriente.setP_NUMERO(sinNumero);
		}		
		
		//Seteamos los beans
		beanTramiteTransmision.setBeanGuardarTransmision(beanTramite);
		beanTramiteTransmision.setBeanGuardarVehiculo(beanVehiculo);
		beanTramiteTransmision.setBeanGuardarAdquiriente(beanAdquiriente);
		beanTramiteTransmision.setBeanGuardarRepresentanteAdquiriente(beanRepresentanteAdquiriente);
		beanTramiteTransmision.setBeanGuardarTransmitente(beanTransmitente);
		beanTramiteTransmision.setBeanGuardarRepresentanteTransmitente(beanRepresentanteTransmitente);
		// DRC@02-10-2012 Incidencia: 1801
		beanTramiteTransmision.setBeanGuardarPoseedor(beanArrendador);
		beanTramiteTransmision.setBeanGuardarRepresentantePoseedor(beanRepresentanteArrendador);
		beanTramiteTransmision.setBeanGuardarArrendador(beanArrendador);
		beanTramiteTransmision.setBeanGuardarRepresentanteArrendador(beanRepresentanteArrendador);
		//beanTramiteTransmision.setBeanGuardarCompraventa(beanCompraventa);
		//beanTramiteTransmision.setBeanGuardarRepresentanteCompraVenta(beanRepresentanteCompraventa);		
		
		beanResult.addAttachment(BEAN_PQ_TRANSMISION, beanTramiteTransmision);		
		return beanResult;	
	}

	/**
	 * Convierte una fecha String AAAAMMDD en un Timestamp. Ponemos manualmente la hora a 00:00:00
	 */
	public static Timestamp aaaammddToTimestamp(String valor) {
		if (valor!=null && !("".equals(valor.trim()))) {
			String valorToTimestamp = valor.substring(0, 4) + "-" + valor.substring(4,6) + "-" + valor.substring(6, 8) + " 00:00:00";
			return Timestamp.valueOf(valorToTimestamp);
		}
		return null;
	}
	
	private static UtilesColegiado getUtilesColegiado() {
		return ContextoSpring.getInstance().getBean(UtilesColegiado.class);
	}
}