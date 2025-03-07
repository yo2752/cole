package trafico.utiles.importacion.tramas;

import static trafico.utiles.constantes.ConstantesDGT.BEAN_PQ_ALTA_MATRICULACION_IMPORT;
import static trafico.utiles.constantes.ConstantesDGT.TAM_ACRED_REPRESENTANTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_BASTIDOR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_BLOQUE_DOMICILIO_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CALLE_VEHICULO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CAMPO_BLANCO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CARBURANTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CEM;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CILINDRADA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CODIGO_ITV;
import static trafico.utiles.constantes.ConstantesDGT.TAM_COLOR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CONCEPTO_REPR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CP_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_CP_VEHICULO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_DNI_REPRESENTANTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_DNI_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_DOMICILIO_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_ESCALERA_DOMICILIO_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_EXCESO_PESO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_CREACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_ITV;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_LIMITACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_LIMITE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_MATRICULACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_NACIMIENTO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_PRESENTACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FECHA_PRIMERA_MATR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_FINANCIERA_LIMITACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_JEFATURA_TRAFICO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_KM_DOMICILIO_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_LETRA_DOMICILIO_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_LINEA_MATRICULACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MARCA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MASA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MASA_MAX;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MASA_SERVICIO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MATRICULA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MATRICULA_AYTO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MATRICULA_DIPL;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MODELO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MUNICIPIO_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_MUNICIPIO_VEHICULO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NOMBRE_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NUM_COTITULARES;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NUM_DOCUMENTO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NUM_DOMICILIO_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NUM_HOMOLOGACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_NUM_PROFESIONAL;
import static trafico.utiles.constantes.ConstantesDGT.TAM_OBSERVACIONES;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PISO_DOMICILIO_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PLAZAS;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PLAZAS_DE_PIE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_POTENCIA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_POTENCIA_MAX;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PRIMER_APELLIDO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PROCEDENCIA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PROVINCIA_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PROVINCIA_VEHICULO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PUEBLO_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PUEBLO_VEHICULO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_PUERTA_DOMICILIO_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_RELACION_POTENCIA_PESO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_REPRESENTANTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_SEGUNDO_APELLIDO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_SERVICIO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_SEXO_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TARA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TASA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_ITV;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_LIMITACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_REG;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_TARJETA_ITV;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_TASA;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_VEHICULO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TIPO_VIA_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TAM_TUTELA_MATRICULACION;
import static trafico.utiles.constantes.ConstantesDGT.TAM_ULT_CIFRAS_DOCUMENTO;
import static trafico.utiles.constantes.ConstantesDGT.TAM_VARIANTE;
import static trafico.utiles.constantes.ConstantesDGT.TAM_VERSION;
import static trafico.utiles.constantes.ConstantesDGT.TIPO_INTERVINIENTE_REPRESENTANTE_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.TIPO_INTERVINIENTE_TITULAR;
import static trafico.utiles.constantes.ConstantesDGT.VALOR_TIPO_LIMITACION;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.Utiles;

import escrituras.beans.ResultBean;
import general.utiles.Anagrama;
import trafico.beans.daos.BeanPQAltaMatriculacionImport;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarIntervinienteImport;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarMatriculacion;
import trafico.beans.daos.BeanPQVehiculosGuardarImport;
import trafico.utiles.UtilesConversiones;

//import static trafico.utiles.constantes.cons.*;

/**
 * Clase que contiene los metodos para formar la trama para la exportacion u obtener los campos a
 * partir de la trama de un tramite de matriculacion.
 * 
 * @author TB·Solutions
 *
 */
public class TramaMatriculacion {
	
	private static String sinNumero = "SN";
	
	public TramaMatriculacion() {
		
	}
	
	/**
	 * Metodo que obtiene el ResultBean que contiene los campos de la linea de registro de matriculacion
	 * que recibe como parametro
	 * 
	 * @param linea
	 * @return Devuelve el bean que contiene una linea con tipo de registro Matriculacion
	 */
	public static final ResultBean obtenerBean(String linea, String colegiadoCabecera){
		
		Utiles utiles = ContextoSpring.getInstance().getBean(Utiles.class);

		//Bean que guarda la información relativa al vehículo y su tabla
		BeanPQVehiculosGuardarImport beanVehiculo = new BeanPQVehiculosGuardarImport();
		//Beans que guarda la información relativa al titular y el representante
		BeanPQTramiteTraficoGuardarIntervinienteImport beanTitular = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		BeanPQTramiteTraficoGuardarIntervinienteImport beanRepresentante = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		//Bean con la información del trámite
		BeanPQTramiteTraficoGuardarMatriculacion beanGuardarMatriculacion = new BeanPQTramiteTraficoGuardarMatriculacion();
		
		//bean que devolverá el resultado de la función
		ResultBean bean = new ResultBean();
		
		//A los intevinientes, les ponemos manualmente cual será su tipo_interviniente
		beanTitular.setP_TIPO_INTERVINIENTE(TIPO_INTERVINIENTE_TITULAR); //Titular
		beanRepresentante.setP_TIPO_INTERVINIENTE(TIPO_INTERVINIENTE_REPRESENTANTE_TITULAR); //Representante del titular
		
		//Posición de la columna que estamos leyendo del fichero
		int pos=0;
		
		//Indicará si ha habiado algún error durante el parseo del fichero
		Boolean error = false;
		String mensajeError = "";
		List<String> listaMensajes = new ArrayList<String>();
		
		//util para convertir
		UtilesConversiones utilConv = ContextoSpring.getInstance().getBean(UtilesConversiones.class);
		
		/**
		 * PROCEDEMOS A RECUPERAR LOS VALORES DE LA LINEA DEL FICHERO, Y A GUARDARLOS EN EL BEAN QUE CORRESPONDAN
		 */
		
		//Identificador de registro (040 Matriculacion)
		String valor = linea.substring(pos, pos + TAM_TIPO_REG);	
		pos = pos + TAM_TIPO_REG;
		
		//Codigo tasas
		valor = linea.substring(pos, pos + TAM_TASA);
		beanGuardarMatriculacion.setP_CODIGO_TASA(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_TASA;
		
		//Fecha Presentación
		valor = linea.substring(pos, pos + TAM_FECHA_PRESENTACION);
		if (valor!=null && !("".equals(valor.trim()))) beanGuardarMatriculacion.setP_FECHA_PRESENTACION(aaaammddToTimestamp(valor)); //Convertimos AAAAMMDD a Timestamp
		pos = pos + TAM_FECHA_PRESENTACION;

		//Siglas Provincia de la Jefatura de Tráfico de presentación
		valor = linea.substring(pos, pos + TAM_JEFATURA_TRAFICO);
		//beanGuardarMatriculacion.setP_JEFATURA_PROVINCIAL(utilConv.getIdProvinciaFromSiglas(valor)); //Pasamos las siglas al id_provincia
		beanGuardarMatriculacion.setP_JEFATURA_PROVINCIAL(valor.trim());
		pos = pos + TAM_JEFATURA_TRAFICO;
		
		//Matrícula Vehículo
		valor = linea.substring(pos, pos + TAM_MATRICULA);
		beanVehiculo.setP_MATRICULA(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_MATRICULA;

		//Fecha Matriculación
		valor = linea.substring(pos, pos + TAM_FECHA_MATRICULACION);	
		beanVehiculo.setP_FECHA_MATRICULACION(aaaammddToTimestamp(valor));
		pos = pos + TAM_FECHA_MATRICULACION;

		//Fecha ITV
		valor = linea.substring(pos, pos + TAM_FECHA_ITV);
		if (valor!=null && !("".equals(valor.trim()))) beanVehiculo.setP_FECHA_ITV(aaaammddToTimestamp(valor)); //Convertimos AAAAMMDD a Timestamp
		pos = pos + TAM_FECHA_ITV;

		//DNI Titular
		valor = linea.substring(pos, pos + TAM_DNI_TITULAR);
		beanTitular.setP_NIF(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_DNI_TITULAR;

		//Primer apellido/Razón social/Denominación del titular
		valor = linea.substring(pos, pos + TAM_PRIMER_APELLIDO);
		beanTitular.setP_APELLIDO1_RAZON_SOCIAL(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_PRIMER_APELLIDO;

		//Segundo apellido
		valor = linea.substring(pos, pos + TAM_SEGUNDO_APELLIDO);
		beanTitular.setP_APELLIDO2(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_SEGUNDO_APELLIDO;

		//Nombre del titular
		valor = linea.substring(pos, pos + TAM_NOMBRE_TITULAR);
		beanTitular.setP_NOMBRE(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_NOMBRE_TITULAR;

		//Domicilio del titular o nombre de la calle
		valor = linea.substring(pos, pos + TAM_DOMICILIO_TITULAR);
		//beanTitular.setP_NOMBRE_VIA(!"".equals(valor.trim())?valor.trim():null);
		beanTitular.setP_VIA(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_DOMICILIO_TITULAR;

		//Municipio del titular
		valor = linea.substring(pos, pos + TAM_MUNICIPIO_TITULAR);
		beanTitular.setP_MUNICIPIO(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_MUNICIPIO_TITULAR;

		//Pueblo del titular
		valor = linea.substring(pos, pos + TAM_PUEBLO_TITULAR);
		beanTitular.setP_PUEBLO_LIT(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_PUEBLO_TITULAR;

		//Siglas provincia del titular
		valor = linea.substring(pos, pos + TAM_PROVINCIA_TITULAR);
		beanTitular.setP_ID_PROVINCIA(utilConv.getIdProvinciaFromSiglas(valor)); //Pasamos las siglas al id_provincia)
		pos = pos + TAM_PROVINCIA_TITULAR;
		
		//Codigo postal del titular
		valor = linea.substring(pos, pos + TAM_CP_TITULAR);
		beanTitular.setP_COD_POSTAL(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_CP_TITULAR;

		//Sexo del titular
		valor = linea.substring(pos, pos + TAM_SEXO_TITULAR);
		//if ("X".equals(valor.toUpperCase())) valor="";
		beanTitular.setP_SEXO(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_SEXO_TITULAR;

		//Fecha de nacimiento del titular
		valor = linea.substring(pos, pos + TAM_FECHA_NACIMIENTO);
		if (valor!=null && !("".equals(valor.trim()))) beanTitular.setP_FECHA_NACIMIENTO(aaaammddToTimestamp(valor)); //Convertimos AAAAMMDD a Timestamp
		pos = pos + TAM_FECHA_NACIMIENTO;

		//Referencia propia / numero de documento
		valor = linea.substring(pos, pos + TAM_NUM_DOCUMENTO);
		beanGuardarMatriculacion.setP_REF_PROPIA(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_NUM_DOCUMENTO;

		//Documento que acredita al representante del titular
		valor = linea.substring(pos, pos + TAM_ACRED_REPRESENTANTE);
		beanRepresentante.setP_DATOS_DOCUMENTO(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_ACRED_REPRESENTANTE;
		
		//Nombre y apellidos del representante del titular (***** SE GUARDA COMO PRIMER APELLIDO DEL REPRESENTANTE ******)
		valor = linea.substring(pos, pos + TAM_REPRESENTANTE);
		beanRepresentante.setP_APELLIDO1_RAZON_SOCIAL(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_REPRESENTANTE;

		//DNI Representante
		valor = linea.substring(pos, pos + TAM_DNI_REPRESENTANTE);
		beanRepresentante.setP_NIF(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_DNI_REPRESENTANTE;

		//Concepto en que representa al titular
		valor = linea.substring(pos, pos + TAM_CONCEPTO_REPR);
		beanRepresentante.setP_CONCEPTO_REPRE(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_CONCEPTO_REPR;

		//Marca del vehículo
		valor = linea.substring(pos, pos + TAM_MARCA);
		beanVehiculo.setP_MARCA_SIN_EDITAR(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_MARCA;

		//Modelo del vehículo
		valor = linea.substring(pos, pos + TAM_MODELO);
		beanVehiculo.setP_MODELO(!"".equals(valor.trim())?valor.trim():null); ///////VIENE UN LITERAL
		pos = pos + TAM_MODELO;

		//Número de bastidor
		valor = linea.substring(pos, pos + TAM_BASTIDOR);
		beanVehiculo.setP_BASTIDOR(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_BASTIDOR;

		//Procedencia del vehículo
		valor = linea.substring(pos, pos + TAM_PROCEDENCIA);
		beanVehiculo.setP_PROCEDENCIA(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_PROCEDENCIA;
		
		//Color del vehículo
		valor = linea.substring(pos, pos + TAM_COLOR);
		beanVehiculo.setP_ID_COLOR(!"".equals(valor.trim())?valor.trim():"-");
		pos = pos + TAM_COLOR;

		//Tipo de vehículo
		valor = linea.substring(pos, pos + TAM_TIPO_VEHICULO);
		beanVehiculo.setP_TIPO_VEHICULO(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_TIPO_VEHICULO;

		//Carburante
		valor = linea.substring(pos, pos + TAM_CARBURANTE);
		beanVehiculo.setP_ID_CARBURANTE(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_CARBURANTE;

		//Potencia
		valor = linea.substring(pos, pos + TAM_POTENCIA);
		beanVehiculo.setP_POTENCIA_FISCAL(utiles.stringToBigDecimalDosDecimales(valor.trim()));		
		pos = pos + TAM_POTENCIA;

		//Cilindrada
		valor = linea.substring(pos, pos + TAM_CILINDRADA);
		beanVehiculo.setP_CILINDRADA(!"".equals(valor.trim())?(new Integer(valor.trim()).toString()):null);
		pos = pos + TAM_CILINDRADA;
		
		//Tara
		valor = linea.substring(pos, pos + TAM_TARA);
		beanVehiculo.setP_TARA(!"".equals(valor.trim())?new Integer(valor.trim()).toString():null);
		pos = pos + TAM_TARA;
		
		//Masa
		valor = linea.substring(pos, pos + TAM_MASA);
		beanVehiculo.setP_PESO_MMA(!"".equals(valor.trim())?(new Integer(valor.trim()).toString()):null);
		pos = pos + TAM_MASA;

		//Plazas
		valor = linea.substring(pos, pos + TAM_PLAZAS);
		if (!(valor==null) && !("".equals(valor.trim()))) beanVehiculo.setP_PLAZAS(new BigDecimal(valor));
		pos = pos + TAM_PLAZAS;

		//Matrícula del ayuntamiento
		valor = linea.substring(pos, pos + TAM_MATRICULA_AYTO);
		beanVehiculo.setP_MATRI_AYUNTAMIENTO(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_MATRICULA_AYTO;

		//Exceso de peso
		valor = linea.substring(pos, pos + TAM_EXCESO_PESO);
		beanVehiculo.setP_EXCESO_PESO(!"".equals(valor.trim())?("SI".equals(valor.trim())?"S":"N"):null);
		pos = pos + TAM_EXCESO_PESO;

		//Fecha límite de validez de la matrícula
		valor = linea.substring(pos, pos + TAM_FECHA_LIMITE);
		if (valor!=null && !("".equals(valor.trim()))) beanVehiculo.setP_LIMITE_MATR_TURIS(aaaammddToTimestamp(valor));
		pos = pos + TAM_FECHA_LIMITE;

		//Fecha de la primera matrícula
		valor = linea.substring(pos, pos + TAM_FECHA_PRIMERA_MATR);
		if (valor!=null && !("".equals(valor.trim()))) beanVehiculo.setP_FECHA_PRIM_MATRI(aaaammddToTimestamp(valor));
		pos = pos + TAM_FECHA_PRIMERA_MATR;
		
		//Servicio al que se destina
		valor = linea.substring(pos, pos + TAM_SERVICIO);
		beanVehiculo.setP_ID_SERVICIO(utilConv.servicioDestinoNuevo(valor.trim()));
		pos = pos + TAM_SERVICIO;
		
		//Tutela o patria potestad
		valor = linea.substring(pos, pos + TAM_TUTELA_MATRICULACION);
		beanTitular.setP_ID_MOTIVO_TUTELA(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_TUTELA_MATRICULACION;

		//Matrícula diplomática
		valor = linea.substring(pos, pos + TAM_MATRICULA_DIPL);
		beanVehiculo.setP_DIPLOMATICO(!"".equals(valor.trim())?("SI".equals(valor.trim())?"S":"N"):null);
		pos = pos + TAM_MATRICULA_DIPL;

		//Tipo de limitación de disposición
		String tipoLimitacion = linea.substring(pos, pos + TAM_TIPO_LIMITACION);
		beanGuardarMatriculacion.setP_IEDTM(valor.trim().equals(tipoLimitacion)?"E":"");		
		pos = pos + TAM_TIPO_LIMITACION;

		//
		//Si el tipo de limitación es 'E' se recuperarn los valores de fecha y financiera. SON IEDTM
		if(VALOR_TIPO_LIMITACION.equals(tipoLimitacion)){
			valor = linea.substring(pos, pos + TAM_FECHA_LIMITACION);
			if (valor!=null && !("".equals(valor.trim()))) beanGuardarMatriculacion.setP_FECHA_IEDTM(aaaammddToTimestamp(valor));
			pos = pos + TAM_FECHA_LIMITACION;
	
			valor = linea.substring(pos, pos + TAM_CAMPO_BLANCO);
			//blancos
			pos = pos + TAM_CAMPO_BLANCO;
	
			valor = linea.substring(pos, pos + TAM_FINANCIERA_LIMITACION);
			beanGuardarMatriculacion.setP_FINANCIERA_IEDTM(!"".equals(valor.trim())?valor.trim():null);
			pos = pos + TAM_FINANCIERA_LIMITACION;
		}else{
			pos = pos + TAM_FECHA_LIMITACION;
			pos = pos + TAM_CAMPO_BLANCO;
			pos = pos + TAM_FINANCIERA_LIMITACION;
		}
		
		//Calle donde está el vehículo
		valor = linea.substring(pos, pos + TAM_CALLE_VEHICULO);
		beanVehiculo.setP_NOMBRE_VIA(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_CALLE_VEHICULO;

		//Siglas provincia donde está el vehículo
		valor = linea.substring(pos, pos + TAM_PROVINCIA_VEHICULO);
		beanVehiculo.setP_ID_PROVINCIA(utilConv.getIdProvinciaFromSiglas(valor)); //Transformamos las siglas en id_provincia
		pos = pos + TAM_PROVINCIA_VEHICULO;

		//Municipio donde está el vehículo
		valor = linea.substring(pos, pos + TAM_MUNICIPIO_VEHICULO);
		beanVehiculo.setP_MUNICIPIO(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_MUNICIPIO_VEHICULO;

		//Pueblo donde está el vehículo
		valor = linea.substring(pos, pos + TAM_PUEBLO_VEHICULO);
		beanVehiculo.setP_PUEBLO_LIT(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_PUEBLO_VEHICULO;

		//Código Postal donde está el vehículo
		valor = linea.substring(pos, pos + TAM_CP_VEHICULO);
		beanVehiculo.setP_COD_POSTAL(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_CP_VEHICULO;

		//Número de colegiado
		valor = linea.substring(pos, pos + TAM_NUM_PROFESIONAL);
		//le quitamos los ceros por la izquierda
		if (!(valor==null) && !("".equals(valor.trim()))) valor = (new Integer(valor.trim())).toString();
		if (valor.length()==5) {
			valor = valor.substring(0, 4);
		}
		while (valor.length()<4) {
			valor = "0" + valor;
		}
		/*
		 * Primero comprobamos si el colegiado de la cabecera del fichero, coincide con el colegiado de trámite
		 */
		if (!colegiadoCabecera.equals(valor)) {
			if (error) mensajeError = mensajeError + ". El usuario de la cabecera del fichero no coincide con el del trámite";
			else mensajeError = "El usuario de la cabecera del fichero no coincide con el del trámite";
			error = true;
			bean.setError(error);
			bean.setMensaje(mensajeError);
			listaMensajes.add(mensajeError);
			bean.addListaMensajes(listaMensajes);
			return bean;
		}
		/*
		 * Si el colegiado de sesión tiene permiso de administración:
		 * ---> Se guarda el número de colegiado de la linea del archivo
		 * Si el colegiado de sesión tiene permiso de colegio:
		 * ---> Se comprueba que el número de colegiado del archivo pertenece al contrato del colegio del colegiado de sesión 
		 */
		if (getUtilesColegiado().tienePermisoAdmin()) {
			beanGuardarMatriculacion.setP_NUM_COLEGIADO(valor);
			beanVehiculo.setP_NUM_COLEGIADO(valor);
			beanTitular.setP_NUM_COLEGIADO(valor);
			beanRepresentante.setP_NUM_COLEGIADO(valor);
		}
		else if (getUtilesColegiado().tienePermisoColegio()) {
			List<String> listaColegiados = getUtilesColegiado().getNumColegiadosDelContrato();
			Boolean puede = false;
			int i = 0;
			while ((!puede) && (listaColegiados.size()>i)) {
				puede = puede || valor.equals(listaColegiados.get(i));
				i++;
			}
			if (puede) {
				beanGuardarMatriculacion.setP_NUM_COLEGIADO(valor);
				beanVehiculo.setP_NUM_COLEGIADO(valor);
				beanTitular.setP_NUM_COLEGIADO(valor);
				beanRepresentante.setP_NUM_COLEGIADO(valor);
			}
			else {
				if (error) mensajeError = mensajeError + ". El usuario ("+ getUtilesColegiado().getNumColegiadoSession() +") no tiene permisos para realizar la matriculación para el colegiado del fichero (" + valor +")";
				else mensajeError = "El usuario ("+ getUtilesColegiado().getNumColegiadoSession() +") no tiene permisos para realizar la matriculación para el colegiado del fichero (" + valor +")";
				error = true;				
				bean.setError(error);
				bean.setMensaje(mensajeError);
				return bean;
			}
		}
		//Si el usuario de sesión es un colegiado cualquiera
		else {
			if (valor.equals(getUtilesColegiado().getNumColegiadoSession())) {
				beanGuardarMatriculacion.setP_NUM_COLEGIADO(valor);
				beanVehiculo.setP_NUM_COLEGIADO(valor);
				beanTitular.setP_NUM_COLEGIADO(valor);
				beanRepresentante.setP_NUM_COLEGIADO(valor);
			}
			else {
				if (error) mensajeError = mensajeError + ". El usuario ("+ getUtilesColegiado().getNumColegiadoSession() +") no es el mismo colegiado que el del trámite del fichero (" + valor + ")";
				else mensajeError = "El usuario ("+ getUtilesColegiado().getNumColegiadoSession() +") no es el mismo colegiado que el del trámite del fichero(" + valor + ")";
				error = true;				
				bean.setError(error);
				bean.setMensaje(mensajeError);
				return bean;
			}
		}
		pos = pos + TAM_NUM_PROFESIONAL;

		//6 últimas cifras de la referencia propia
		valor = linea.substring(pos, pos + TAM_ULT_CIFRAS_DOCUMENTO);
		//Tenemos la referencia propia en otro campo, para qué queremos las últimas cifras ahora?
		pos = pos + TAM_ULT_CIFRAS_DOCUMENTO;

		//Fecha de creación del documento
		valor = linea.substring(pos, pos + TAM_FECHA_CREACION);
		beanGuardarMatriculacion.setP_FECHA_ALTA(aaaammddToTimestamp(valor));
		pos = pos + TAM_FECHA_CREACION;

		//Código ITV
		valor = linea.substring(pos, pos + TAM_CODIGO_ITV);
		beanVehiculo.setP_CODITV(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_CODIGO_ITV;

		//Observaciones, aclaración a la jefatura de tráfico sobre el trámite
		valor = linea.substring(pos, pos + TAM_OBSERVACIONES);
		beanGuardarMatriculacion.setP_ANOTACIONES(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_OBSERVACIONES;

		//Tipo de vía del domicilio del titular
		valor = linea.substring(pos, pos + TAM_TIPO_VIA_TITULAR);
		if ("6".equals(valor.trim())) valor="41"; //calle
		if ("44".equals(valor.trim())) valor="5";
		if ("24".equals(valor.trim())) valor="29";
		if ("34".equals(valor.trim())) valor="29";
		//beanTitular.setP_ID_TIPO_DGT(!"".equals(valor.trim())?valor.trim():".");
		beanTitular.setP_ID_TIPO_DGT(valor.trim());
		pos = pos + TAM_TIPO_VIA_TITULAR;

		//Número del domicilio del titular
		valor = linea.substring(pos, pos + TAM_NUM_DOMICILIO_TITULAR);
		beanTitular.setP_NUMERO(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_NUM_DOMICILIO_TITULAR;
	
		//Letra del domicilio del titular
		valor = linea.substring(pos, pos + TAM_LETRA_DOMICILIO_TITULAR);
		beanTitular.setP_LETRA(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_LETRA_DOMICILIO_TITULAR;
	
		//Escalera del domicilio del titular
		valor = linea.substring(pos, pos + TAM_ESCALERA_DOMICILIO_TITULAR);
		beanTitular.setP_ESCALERA(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_ESCALERA_DOMICILIO_TITULAR;
	
		//Piso del domicilio del titular
		valor = linea.substring(pos, pos + TAM_PISO_DOMICILIO_TITULAR);
		beanTitular.setP_PLANTA(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_PISO_DOMICILIO_TITULAR;
	
		//Puerta del domicilio del titular
		valor = linea.substring(pos, pos + TAM_PUERTA_DOMICILIO_TITULAR);
		beanTitular.setP_PUERTA(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_PUERTA_DOMICILIO_TITULAR;
	
		//Km del domicilio del titular
		valor = linea.substring(pos, pos + TAM_KM_DOMICILIO_TITULAR);
		if (!(valor==null) && !("".equals(valor.trim()))) beanTitular.setP_KM(new BigDecimal(valor.trim()));
		pos = pos + TAM_KM_DOMICILIO_TITULAR;
			
		//Bloque del domicilio del titular
		valor = linea.substring(pos, pos + TAM_BLOQUE_DOMICILIO_TITULAR);
		beanTitular.setP_BLOQUE(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_BLOQUE_DOMICILIO_TITULAR;
	
		//Tipo tasas
		valor = linea.substring(pos, pos + TAM_TIPO_TASA);
		//no encuentro concordancia con los paquetes
		pos = pos + TAM_TIPO_TASA;
		
		//Tipo indicativo en la tarjeta ITV
		valor = linea.substring(pos, pos + TAM_TIPO_ITV);
		beanVehiculo.setP_TIPO_ITV(valor.trim());
		pos = pos + TAM_TIPO_ITV;

		//Variante
		valor = linea.substring(pos, pos + TAM_VARIANTE);
		beanVehiculo.setP_VARIANTE(valor.trim());
		pos = pos + TAM_VARIANTE;

		//Version
		valor = linea.substring(pos, pos + TAM_VERSION);
		beanVehiculo.setP_VERSION(valor.trim());
		pos = pos + TAM_VERSION;

		//Número de homologación (alfanumérico)
		valor = linea.substring(pos, pos + TAM_NUM_HOMOLOGACION);
		beanVehiculo.setP_N_HOMOLOGACION(!"".equals(valor.trim())?valor.trim():null);
		pos = pos + TAM_NUM_HOMOLOGACION;

		//Masa Máxima en carga técnicamente admisible
		valor = linea.substring(pos, pos + TAM_MASA_MAX);
		beanVehiculo.setP_MTMA_ITV(new Integer(valor.trim()).toString());
		pos = pos + TAM_MASA_MAX;

		//Masa del vehiculo en servicio
		valor = linea.substring(pos, pos + TAM_MASA_SERVICIO);
		//No encuentro correspondencia con los paquetes
		pos = pos + TAM_MASA_SERVICIO;

		//Potencia neta Máxima
		valor = linea.substring(pos, pos + TAM_POTENCIA_MAX);
		beanVehiculo.setP_POTENCIA_NETA(utiles.stringToBigDecimalDosDecimales(valor.trim()));	
		pos = pos + TAM_POTENCIA_MAX;

		//Relación de potencia/peso
		valor = linea.substring(pos, pos + TAM_RELACION_POTENCIA_PESO);
		//no encuentro correspondencia con los paquetes
		pos = pos + TAM_RELACION_POTENCIA_PESO;

		//Número de plazas de pie
		valor = linea.substring(pos, pos + TAM_PLAZAS_DE_PIE);
		if (!(valor==null) && !("".equals(valor.trim()))) beanVehiculo.setP_N_PLAZAS_PIE(new BigDecimal(valor.trim()));
		if (BigDecimal.ZERO.equals(beanVehiculo.getP_N_PLAZAS_PIE())) beanVehiculo.setP_N_PLAZAS_PIE(null);
		pos = pos + TAM_PLAZAS_DE_PIE;

		//Código electrónico de Matriculación (CEM)
		valor = linea.substring(pos, pos + TAM_CEM);
		beanGuardarMatriculacion.setP_CEM(valor.trim());
		//no encuentro correspondencia con los paquetes
		pos = pos + TAM_CEM;

		//número de cotitulares
		String numCotitulares = linea.substring(pos, pos + TAM_NUM_COTITULARES);
		//no encuentro correspondencia con los paquetes
		pos = pos + TAM_NUM_COTITULARES;

		
		/////////////////////////////////////// YA NO HAY COTITULARES, LOS IGNORAMOS /////////////////////////
		/*
		//Si el número de cotitulares es 0 no se importan los datos.
		if("1".equals(numCotitulares) || "2".equals(numCotitulares)){
			valor = linea.substring(pos, pos + TAM_DNI_TITULAR_2);
			bean.addCampo(CASILLA_DNI_TITULAR_2.longValue(), ID_DNI_TITULAR_2, valor);
			pos = pos + TAM_DNI_TITULAR_2;
	
			valor = linea.substring(pos, pos + TAM_PRIMER_APELLIDO_TITULAR_2);
			bean.addCampo(CASILLA_PRIMER_APELLIDO_TITULAR_2.longValue(), ID_PRIMER_APELLIDO_TITULAR_2, valor);
			pos = pos + TAM_PRIMER_APELLIDO_TITULAR_2;
	
			valor = linea.substring(pos, pos + TAM_SEGUNDO_APELLIDO_TITULAR_2);
			bean.addCampo(CASILLA_SEGUNDO_APELLIDO_TITULAR_2.longValue(), ID_SEGUNDO_APELLIDO_TITULAR_2, valor);
			pos = pos + TAM_SEGUNDO_APELLIDO_TITULAR_2;
	
			valor = linea.substring(pos, pos + TAM_NOMBRE_TITULAR_2);
			bean.addCampo(CASILLA_NOMBRE_TITULAR_2.longValue(), ID_NOMBRE_TITULAR_2, valor);
			pos = pos + TAM_NOMBRE_TITULAR_2;
	
			valor = linea.substring(pos, pos + TAM_DOMICILIO_TITULAR_2);
			bean.addCampo(CASILLA_DOMICILIO_TITULAR_2.longValue(), ID_DOMICILIO_TITULAR_2, valor);
			pos = pos + TAM_DOMICILIO_TITULAR_2;
	
			valor = linea.substring(pos, pos + TAM_RESERVADO_1);
			bean.addCampo(CASILLA_RESERVADO_1.longValue(), ID_RESERVADO_1, valor);
			pos = pos + TAM_RESERVADO_1;
	
			valor = linea.substring(pos, pos + TAM_MUNICIPIO_TITULAR_2);
			bean.addCampo(CASILLA_MUNICIPIO_TITULAR_2.longValue(), ID_MUNICIPIO_TITULAR_2, valor);
			pos = pos + TAM_MUNICIPIO_TITULAR_2;
	
			valor = linea.substring(pos, pos + TAM_PUEBLO_TITULAR_2);
			bean.addCampo(CASILLA_PUEBLO_TITULAR_2.longValue(), ID_PUEBLO_TITULAR_2, valor);
			pos = pos + TAM_PUEBLO_TITULAR_2;
	
			valor = linea.substring(pos, pos + TAM_PROVINCIA_TITULAR_2);
			bean.addCampo(CASILLA_PROVINCIA_TITULAR_2.longValue(), ID_PROVINCIA_TITULAR_2, valor);
			pos = pos + TAM_PROVINCIA_TITULAR_2;
	
			valor = linea.substring(pos, pos + TAM_CP_TITULAR_2);
			bean.addCampo(CASILLA_CP_TITULAR_2.longValue(), ID_CP_TITULAR_2, valor);
			pos = pos + TAM_CP_TITULAR_2;
	
			valor = linea.substring(pos, pos + TAM_SEXO_TITULAR_2);
			bean.addCampo(CASILLA_SEXO_TITULAR_2.longValue(), ID_SEXO_TITULAR_2, valor);
			pos = pos + TAM_SEXO_TITULAR_2;
	
			valor = linea.substring(pos, pos + TAM_FECHA_NACIMIENTO_TITULAR_2);
			bean.addCampo(CASILLA_FECHA_NACIMIENTO_TITULAR_2.longValue(), ID_FECHA_NACIMIENTO_TITULAR_2, valor);
			pos = pos + TAM_FECHA_NACIMIENTO_TITULAR_2;
			
			if("2".equals(numCotitulares)){
				valor = linea.substring(pos, pos + TAM_DNI_TITULAR_3);
				bean.addCampo(CASILLA_DNI_TITULAR_3.longValue(), ID_DNI_TITULAR_3, valor);
				pos = pos + TAM_DNI_TITULAR_3;

				valor = linea.substring(pos, pos + TAM_PRIMER_APELLIDO_TITULAR_3);
				bean.addCampo(CASILLA_PRIMER_APELLIDO_TITULAR_3.longValue(), ID_PRIMER_APELLIDO_TITULAR_3, valor);
				pos = pos + TAM_PRIMER_APELLIDO_TITULAR_3;

				valor = linea.substring(pos, pos + TAM_SEGUNDO_APELLIDO_TITULAR_3);
				bean.addCampo(CASILLA_SEGUNDO_APELLIDO_TITULAR_3.longValue(), ID_SEGUNDO_APELLIDO_TITULAR_3, valor);
				pos = pos + TAM_SEGUNDO_APELLIDO_TITULAR_3;

				valor = linea.substring(pos, pos + TAM_NOMBRE_TITULAR_3);
				bean.addCampo(CASILLA_NOMBRE_TITULAR_3.longValue(), ID_NOMBRE_TITULAR_3, valor);
				pos = pos + TAM_NOMBRE_TITULAR_3;

				valor = linea.substring(pos, pos + TAM_DOMICILIO_TITULAR_3);
				bean.addCampo(CASILLA_DOMICILIO_TITULAR_3.longValue(), ID_DOMICILIO_TITULAR_3, valor);
				pos = pos + TAM_DOMICILIO_TITULAR_3;

				valor = linea.substring(pos, pos + TAM_RESERVADO_2);
				bean.addCampo(CASILLA_RESERVADO_2.longValue(), ID_RESERVADO_2, valor);
				pos = pos + TAM_RESERVADO_2;

				valor = linea.substring(pos, pos + TAM_MUNICIPIO_TITULAR_3);
				bean.addCampo(CASILLA_MUNICIPIO_TITULAR_3.longValue(), ID_MUNICIPIO_TITULAR_3, valor);
				pos = pos + TAM_MUNICIPIO_TITULAR_3;

				valor = linea.substring(pos, pos + TAM_PUEBLO_TITULAR_3);
				bean.addCampo(CASILLA_PUEBLO_TITULAR_3.longValue(), ID_PUEBLO_TITULAR_3, valor);
				pos = pos + TAM_PUEBLO_TITULAR_3;

				valor = linea.substring(pos, pos + TAM_PROVINCIA_TITULAR_3);
				bean.addCampo(CASILLA_PROVINCIA_TITULAR_3.longValue(), ID_PROVINCIA_TITULAR_3, valor);
				pos = pos + TAM_PROVINCIA_TITULAR_3;

				valor = linea.substring(pos, pos + TAM_CP_TITULAR_3);
				bean.addCampo(CASILLA_CP_TITULAR_3.longValue(), ID_CP_TITULAR_3, valor);
				pos = pos + TAM_CP_TITULAR_3;

				valor = linea.substring(pos, pos + TAM_SEXO_TITULAR_3);
				bean.addCampo(CASILLA_SEXO_TITULAR_3.longValue(), ID_SEXO_TITULAR_3, valor);
				pos = pos + TAM_SEXO_TITULAR_3;

				valor = linea.substring(pos, pos + TAM_FECHA_NACIMIENTO_TITULAR_3);
				bean.addCampo(CASILLA_FECHA_NACIMIENTO_TITULAR_3.longValue(), ID_FECHA_NACIMIENTO_TITULAR_3, valor);
				pos = pos + TAM_FECHA_NACIMIENTO_TITULAR_3;
			}
		
		}
		*/
		
		//Tipo tarjeta ITV
		if(linea.length()>TAM_LINEA_MATRICULACION){
			valor = linea.substring(TAM_LINEA_MATRICULACION, TAM_LINEA_MATRICULACION + TAM_TIPO_TARJETA_ITV);
			beanVehiculo.setP_ID_TIPO_TARJETA_ITV(!"".equals(valor.trim())?valor.trim():null);
		}
		
		
		//Añadimos la casilla de VEHICULO USADO, true si alguna de estas casillas tiene valor.
		if(((beanVehiculo.getP_FECHA_PRIM_MATRI()!=null) &&(!esVacio(beanVehiculo.getP_FECHA_PRIM_MATRI().toString(), false)))
				||((beanVehiculo.getP_MATRI_AYUNTAMIENTO()!=null) && (!esVacio(beanVehiculo.getP_MATRI_AYUNTAMIENTO(), false)))
				||((beanVehiculo.getP_LIMITE_MATR_TURIS()!=null) &&(!esVacio(beanVehiculo.getP_LIMITE_MATR_TURIS().toString(), false)))){
			beanVehiculo.setP_VEHI_USADO("SI");
		}
		else beanVehiculo.setP_VEHI_USADO("NO");
		
		
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
		
		//Añadimos el tipo de vía y el número a la dirección del titular:
		if ((beanTitular.getP_VIA()!=null && !"".equals(beanTitular.getP_VIA())) &&
			(beanTitular.getP_MUNICIPIO()!=null && !"".equals(beanTitular.getP_MUNICIPIO())) &&
			(beanTitular.getP_COD_POSTAL()!=null && !"".equals(beanTitular.getP_COD_POSTAL())) &&
			(beanTitular.getP_ID_PROVINCIA()!=null && !"".equals(beanTitular.getP_ID_PROVINCIA()))) {
				if (!(beanTitular.getP_ID_TIPO_DGT()!=null && !"".equals(beanTitular.getP_ID_TIPO_DGT())))
					beanTitular.setP_ID_TIPO_DGT(TIPO_DGT_BLANCO);
				if (!(beanTitular.getP_NUMERO()!=null && !"".equals(beanTitular.getP_NUMERO())))
					beanTitular.setP_NUMERO(sinNumero);
		}
		
		//Añadimos el anagrama al titular
		if (beanTitular.getP_NIF()!=null && beanTitular.getP_APELLIDO1_RAZON_SOCIAL()!=null 
				&& !"".equals(beanTitular.getP_NIF()) && !"".equals(beanTitular.getP_APELLIDO1_RAZON_SOCIAL())
				&& Character.isLetter(beanTitular.getP_NIF().charAt(0))) {
			beanTitular.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(beanTitular.getP_APELLIDO1_RAZON_SOCIAL(), beanTitular.getP_NIF()));
		}
		//Y al representante
		if (beanRepresentante.getP_NIF()!=null && beanRepresentante.getP_APELLIDO1_RAZON_SOCIAL()!=null 
				&& !"".equals(beanRepresentante.getP_NIF()) && !"".equals(beanRepresentante.getP_APELLIDO1_RAZON_SOCIAL())
				&& Character.isLetter(beanRepresentante.getP_NIF().charAt(0))) {
			beanRepresentante.setP_ANAGRAMA(Anagrama.obtenerAnagramaFiscal(beanRepresentante.getP_APELLIDO1_RAZON_SOCIAL(), beanRepresentante.getP_NIF()));
		}
		
		//Añadimos el id_usuario y el id_contrato de sesión
		BigDecimal idContrato = getUtilesColegiado().getIdContratoSessionBigDecimal();
		BigDecimal idUsuario = getUtilesColegiado().getIdUsuarioSessionBigDecimal();
		beanGuardarMatriculacion.setP_ID_CONTRATO_SESSION(idContrato);
		beanGuardarMatriculacion.setP_ID_USUARIO(idUsuario);
		beanTitular.setP_ID_CONTRATO_SESSION(idContrato);
		beanTitular.setP_ID_USUARIO(idUsuario);
		beanRepresentante.setP_ID_CONTRATO_SESSION(idContrato);
		beanRepresentante.setP_ID_USUARIO(idUsuario);
		
		//Bean que contiene a los otros necesarios para la matriculación. Lo meteremos en el ResultBean devuelto por la función
		BeanPQAltaMatriculacionImport beanAlta = new BeanPQAltaMatriculacionImport();
		
		beanAlta.setBeanGuardarMatriculacion(beanGuardarMatriculacion);
		beanAlta.setBeanGuardarTitular(beanTitular);
		beanAlta.setBeanGuardarRepresentante(beanRepresentante);
		beanAlta.setBeanGuardarVehiculo(beanVehiculo);
		beanAlta.setBeanGuardarArrendatario(new BeanPQTramiteTraficoGuardarIntervinienteImport());
		beanAlta.setBeanGuardarConductorHabitual(new BeanPQTramiteTraficoGuardarIntervinienteImport());
		
		
		
		bean.addAttachment(BEAN_PQ_ALTA_MATRICULACION_IMPORT, beanAlta);
		
		return bean;
	}

	
	/**
	 * Metodo que a partir de un TramitesBean de registro de matriculacion devuelve el String que 
	 * contiene la linea a almacenar en el fichero a exportar
	 * 
	 * @param bean
	 * @return Devuelve el String con la linea de registro de matriculacion
	 */
/*	public static final String obtenerLinea(ImportacionDGTMatriculacionBean bean){
		StringBuffer line = new StringBuffer();
		String campo;
		
		campo = bean.getValor(CASILLA_TIPO_REG.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_TIPO_REG));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_TASA.longValue());
		if(null==campo){
			line.append(changeSize("", TAM_TASA));
		}else if("EXENTO".equals(campo) || "".equals(campo)){
			line.append(changeSize(campo, TAM_TASA, ' ', true));
		}else{
			line.append(changeSize(campo, TAM_TASA, '0', false));
		}
		
		campo = bean.getValor(CASILLA_FECHA_PRESENTACION.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(bean.getValor(CASILLA_FECHA_CREACION.longValue()));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_JEFATURA_TRAFICO.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_JEFATURA_TRAFICO));
		}else{
			line.append(changeSize(campo, TAM_JEFATURA_TRAFICO, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_MATRICULA.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_MATRICULA));
		}else{
			line.append(changeSize(campo, TAM_MATRICULA, ' ', false));
		}

		campo = bean.getValor(CASILLA_FECHA_MATRICULACION.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(bean.getValor(CASILLA_FECHA_CREACION.longValue()));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_FECHA_ITV.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_FECHA_ITV));
		}else{
			line.append(changeSize(campo, TAM_FECHA_ITV));
		}

		campo = bean.getValor(CASILLA_DNI_TITULAR.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_DNI_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_DNI_TITULAR, '0', false));
		}
		
		campo = bean.getValor(CASILLA_PRIMER_APELLIDO.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_PRIMER_APELLIDO));
		}else{
			line.append(changeSize(campo, TAM_PRIMER_APELLIDO, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_SEGUNDO_APELLIDO.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_SEGUNDO_APELLIDO));
		}else{
			line.append(changeSize(campo, TAM_SEGUNDO_APELLIDO, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_NOMBRE_TITULAR.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_NOMBRE_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_NOMBRE_TITULAR, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_DOMICILIO_TITULAR.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_DOMICILIO_TITULAR));
		}else{
			campo = recortaCadena(campo, TAM_DOMICILIO_TITULAR);
			line.append(changeSize(campo, TAM_DOMICILIO_TITULAR, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_MUNICIPIO_TITULAR.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_MUNICIPIO_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_MUNICIPIO_TITULAR, ' ', false));
		}

		campo = bean.getValor(CASILLA_PUEBLO_TITULAR.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_PUEBLO_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_PUEBLO_TITULAR, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_PROVINCIA_TITULAR.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_PROVINCIA_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_PROVINCIA_TITULAR, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_CP_TITULAR.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_CP_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_CP_TITULAR, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_SEXO_TITULAR.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_SEXO_TITULAR));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_FECHA_NACIMIENTO.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_FECHA_NACIMIENTO));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_NUM_DOCUMENTO.longValue());
		if(null != campo && !"".equals(campo)){		
			line.append(changeSize(campo, TAM_NUM_DOCUMENTO, ' ', false));
		}else{
			campo = bean.getValor(CASILLA_NUM_EXPEDIENTE.longValue());
			line.append(changeSize(campo, TAM_NUM_DOCUMENTO, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_ACRED_REPRESENTANTE.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_ACRED_REPRESENTANTE));
		}else{
			line.append(changeSize(campo, TAM_ACRED_REPRESENTANTE, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_REPRESENTANTE.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_REPRESENTANTE));
		}else{
			line.append(changeSize(campo, TAM_REPRESENTANTE, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_DNI_REPRESENTANTE.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_DNI_REPRESENTANTE));
		}else{
			line.append(changeSize(campo, TAM_DNI_REPRESENTANTE, '0', false));
		}
		
		campo = bean.getValor(CASILLA_CONCEPTO_REPR.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_CONCEPTO_REPR));
		}else{
			line.append(changeSize(campo, TAM_CONCEPTO_REPR, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_MARCA.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_MARCA));
		}else{
			line.append(changeSize(campo, TAM_MARCA, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_MODELO.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_MODELO));
		}else{
			line.append(changeSize(campo, TAM_MODELO, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_BASTIDOR.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_BASTIDOR));
		}else{
			line.append(changeSize(campo, TAM_BASTIDOR, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_PROCEDENCIA.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_PROCEDENCIA));
		}else{
			line.append(changeSize(campo, TAM_PROCEDENCIA, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_COLOR.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_COLOR));
		}else{
			line.append(changeSize(campo, TAM_COLOR, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_TIPO_VEHICULO.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_TIPO_VEHICULO));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_CARBURANTE.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_CARBURANTE));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_POTENCIA.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_POTENCIA));
		}else{
			line.append(changeSize(campo, TAM_POTENCIA, '0', false));
		}

		campo = bean.getValor(CASILLA_CILINDRADA.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_CILINDRADA));
		}else{
			line.append(changeSize(campo, TAM_CILINDRADA, '0', false));
		}

		campo = bean.getValor(CASILLA_TARA.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_TARA));
		}else{
			line.append(changeSize(campo, TAM_TARA, '0', false));
		}

		campo = bean.getValor(CASILLA_MASA.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_MASA));
		}else{
			line.append(changeSize(campo, TAM_MASA, '0', false));
		}

		campo = bean.getValor(CASILLA_PLAZAS.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_PLAZAS));
		}else{
			line.append(changeSize(campo, TAM_PLAZAS, '0', false));
		}
		
		campo = bean.getValor(CASILLA_MATRICULA_AYTO.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_MATRICULA_AYTO));
		}else{
			line.append(changeSize(campo, TAM_MATRICULA_AYTO, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_EXCESO_PESO.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_EXCESO_PESO));
		}else{
			line.append(campo);
		}

		campo = bean.getValor(CASILLA_FECHA_LIMITE.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_FECHA_LIMITE));
		}else{
			line.append(campo);
		}

		campo = bean.getValor(CASILLA_FECHA_PRIMERA_MATR.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_FECHA_PRIMERA_MATR));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_SERVICIO.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_SERVICIO));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_TUTELA.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_TUTELA_MATRICULACION));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_MATRICULA_DIPL.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_MATRICULA_DIPL));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_TIPO_LIMITACION.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_TIPO_LIMITACION));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_FECHA_LIMITACION.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_FECHA_LIMITACION));
		}else{
			line.append(campo);
		}

		//Campo inutil
		line.append(changeSize("", TAM_CAMPO_BLANCO));
		
		campo = bean.getValor(CASILLA_FINANCIERA_LIMITACION.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_FINANCIERA_LIMITACION));
		}else{
			line.append(changeSize(campo, TAM_FINANCIERA_LIMITACION, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_CALLE_VEHICULO.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_CALLE_VEHICULO));
		}else{
			campo = recortaCadena(campo, TAM_CALLE_VEHICULO);
			line.append(changeSize(campo, TAM_CALLE_VEHICULO, ' ', false));
		}

		campo = bean.getValor(CASILLA_PROVINCIA_VEHICULO.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_PROVINCIA_VEHICULO));
		}else{
			line.append(changeSize(campo, TAM_PROVINCIA_VEHICULO, ' ', false));
		}

		campo = bean.getValor(CASILLA_MUNICIPIO_VEHICULO.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_MUNICIPIO_VEHICULO));
		}else{
			line.append(changeSize(campo, TAM_MUNICIPIO_VEHICULO, ' ', false));
		}

		campo = bean.getValor(CASILLA_PUEBLO_VEHICULO.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_PUEBLO_VEHICULO));
		}else{
			line.append(changeSize(campo, TAM_PUEBLO_VEHICULO, ' ', false));
		}

		campo = bean.getValor(CASILLA_CP_VEHICULO.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_CP_VEHICULO));
		}else{
			line.append(changeSize(campo, TAM_CP_TITULAR, ' ', false));
		}

		campo = bean.getValor(CASILLA_NUM_PROFESIONAL.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_NUM_PROFESIONAL));
		}else{
			line.append(changeSize(campo, TAM_NUM_PROFESIONAL, '0', false));
		}
		
		campo = bean.getValor(CASILLA_ULT_CIFRAS_DOCUMENTO.longValue());
		if(null==campo || "".equals(campo)){
			if(bean.getValor(CASILLA_NUM_DOCUMENTO.longValue())!=null && !"".equals(bean.getValor(CASILLA_NUM_DOCUMENTO.longValue()))
					&& bean.getValor(CASILLA_NUM_DOCUMENTO.longValue()).length()>=TAM_ULT_CIFRAS_DOCUMENTO){
				line.append(bean.getValor(CASILLA_NUM_DOCUMENTO.longValue()).substring(bean.getValor(CASILLA_NUM_DOCUMENTO.longValue()).length()-TAM_ULT_CIFRAS_DOCUMENTO));
			}else{
				line.append(bean.getValor(CASILLA_NUM_EXPEDIENTE.longValue()).substring(bean.getValor(CASILLA_NUM_EXPEDIENTE.longValue()).length()-TAM_ULT_CIFRAS_DOCUMENTO));
			}
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_FECHA_CREACION.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("", TAM_FECHA_CREACION));
		}else{
			line.append(campo);
		}

		campo = bean.getValor(CASILLA_CODIGO_ITV.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_CODIGO_ITV));
		}else{
			line.append(changeSize(campo, TAM_CODIGO_ITV, ' ', false));
		}

		campo = bean.getValor(CASILLA_OBSERVACIONES.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_OBSERVACIONES));
		}else{
			line.append(changeSize(campo, TAM_OBSERVACIONES, ' ', false));
		}

		campo = bean.getValor(CASILLA_TIPO_VIA_TITULAR.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_TIPO_VIA_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_TIPO_VIA_TITULAR, ' ', false));
		}

		campo = bean.getValor(CASILLA_NUM_DOMICILIO_TITULAR.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_NUM_DOMICILIO_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_NUM_DOMICILIO_TITULAR, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_LETRA_DOMICILIO_TITULAR.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_LETRA_DOMICILIO_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_LETRA_DOMICILIO_TITULAR, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_ESCALERA_DOMICILIO_TITULAR.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_ESCALERA_DOMICILIO_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_ESCALERA_DOMICILIO_TITULAR, ' ', false));
		}

		campo = bean.getValor(CASILLA_PISO_DOMICILIO_TITULAR.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_PISO_DOMICILIO_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_PISO_DOMICILIO_TITULAR, ' ', false));
		}

		campo = bean.getValor(CASILLA_PUERTA_DOMICILIO_TITULAR.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_PUERTA_DOMICILIO_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_PUERTA_DOMICILIO_TITULAR, ' ', false));
		}

		campo = bean.getValor(CASILLA_KM_DOMICILIO_TITULAR.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_KM_DOMICILIO_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_KM_DOMICILIO_TITULAR, ' ', false));
		}

		campo = bean.getValor(CASILLA_BLOQUE_DOMICILIO_TITULAR.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_BLOQUE_DOMICILIO_TITULAR));
		}else{
			line.append(changeSize(campo, TAM_BLOQUE_DOMICILIO_TITULAR, ' ', false));
		}

		campo = bean.getValor(CASILLA_TIPO_TASA.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_TIPO_TASA));
		}else{
			line.append(changeSize(campo, TAM_TIPO_TASA, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_TIPO_ITV.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_TIPO_ITV));
		}else{
			line.append(changeSize(campo, TAM_TIPO_ITV, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_VARIANTE.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_VARIANTE));
		}else{
			line.append(changeSize(campo, TAM_VARIANTE, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_VERSION.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_VERSION));
		}else{
			line.append(changeSize(campo, TAM_VERSION, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_NUM_HOMOLOGACION.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_NUM_HOMOLOGACION));
		}else{
			line.append(changeSize(campo, TAM_NUM_HOMOLOGACION, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_MASA_MAX.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_MASA_MAX, '0', false));
		}else{
			line.append(changeSize(campo, TAM_MASA_MAX, '0', false));
		}
		
		campo = bean.getValor(CASILLA_MASA_SERVICIO.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_MASA_SERVICIO));
		}else{
			line.append(changeSize(campo, TAM_MASA_SERVICIO, '0', false));
		}
		
		campo = bean.getValor(CASILLA_POTENCIA_MAX.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_POTENCIA_MAX));
		}else{
			line.append(changeSize(campo, TAM_POTENCIA_MAX, '0', false));
		}
		
		campo = bean.getValor(CASILLA_RELACION_POTENCIA_PESO.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_RELACION_POTENCIA_PESO));
		}else{
			line.append(changeSize(campo, TAM_RELACION_POTENCIA_PESO, '0', false));
		}
		
		campo = bean.getValor(CASILLA_PLAZAS_DE_PIE.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_PLAZAS_DE_PIE));
		}else{
			line.append(changeSize(campo, TAM_PLAZAS_DE_PIE, '0', false));
		}
		
		campo = bean.getValor(CASILLA_CEM.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_CEM));
		}else{
			line.append(changeSize(campo, TAM_CEM, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_NUM_COTITULARES.longValue());
		if(null==campo || "".equals(campo)){		
			line.append(changeSize("0", TAM_NUM_COTITULARES));
		}else{
			line.append(campo);
		}
		
		campo = bean.getValor(CASILLA_DNI_TITULAR_2.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_DNI_TITULAR_2));
		}else{
			line.append(changeSize(campo, TAM_DNI_TITULAR_2, '0', false));
		}
		
		campo = bean.getValor(CASILLA_PRIMER_APELLIDO_TITULAR_2.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_PRIMER_APELLIDO_TITULAR_2));
		}else{
			line.append(changeSize(campo, TAM_PRIMER_APELLIDO_TITULAR_2, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_SEGUNDO_APELLIDO_TITULAR_2.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_SEGUNDO_APELLIDO_TITULAR_2));
		}else{
			line.append(changeSize(campo, TAM_SEGUNDO_APELLIDO_TITULAR_2, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_NOMBRE_TITULAR_2.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_NOMBRE_TITULAR_2));
		}else{
			line.append(changeSize(campo, TAM_NOMBRE_TITULAR_2, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_DOMICILIO_TITULAR_2.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_DOMICILIO_TITULAR_2));
		}else{
			campo = recortaCadena(campo, TAM_DOMICILIO_TITULAR_2);
			line.append(changeSize(campo, TAM_DOMICILIO_TITULAR_2, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_RESERVADO_1.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_RESERVADO_1));
		}else{
			line.append(changeSize(campo, TAM_RESERVADO_1, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_MUNICIPIO_TITULAR_2.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_MUNICIPIO_TITULAR_2));
		}else{
			line.append(changeSize(campo, TAM_MUNICIPIO_TITULAR_2, ' ', false));
		}

		campo = bean.getValor(CASILLA_PUEBLO_TITULAR_2.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_PUEBLO_TITULAR_2));
		}else{
			line.append(changeSize(campo, TAM_PUEBLO_TITULAR_2, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_PROVINCIA_TITULAR_2.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_PROVINCIA_TITULAR_2));
		}else{
			line.append(changeSize(campo, TAM_PROVINCIA_TITULAR_2, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_CP_TITULAR_2.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_CP_TITULAR_2));
		}else{
			line.append(changeSize(campo, TAM_CP_TITULAR_2, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_SEXO_TITULAR_2.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_SEXO_TITULAR_2));
		}else{
			line.append(changeSize(campo, TAM_SEXO_TITULAR_2, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_FECHA_NACIMIENTO_TITULAR_2.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_FECHA_NACIMIENTO_TITULAR_2));
		}else{
			line.append(campo);
		}

		campo = bean.getValor(CASILLA_DNI_TITULAR_3.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_DNI_TITULAR_3));
		}else{
			line.append(changeSize(campo, TAM_DNI_TITULAR_3, '0', false));
		}
		
		campo = bean.getValor(CASILLA_PRIMER_APELLIDO_TITULAR_3.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_PRIMER_APELLIDO_TITULAR_3));
		}else{
			line.append(changeSize(campo, TAM_PRIMER_APELLIDO_TITULAR_3, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_SEGUNDO_APELLIDO_TITULAR_3.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_SEGUNDO_APELLIDO_TITULAR_3));
		}else{
			line.append(changeSize(campo, TAM_SEGUNDO_APELLIDO_TITULAR_3, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_NOMBRE_TITULAR_3.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_NOMBRE_TITULAR_3));
		}else{
			line.append(changeSize(campo, TAM_NOMBRE_TITULAR_3, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_DOMICILIO_TITULAR_3.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_DOMICILIO_TITULAR_3));
		}else{
			campo = recortaCadena(campo, TAM_DOMICILIO_TITULAR_3);
			line.append(changeSize(campo, TAM_DOMICILIO_TITULAR_3, ' ', false));
		}

		campo = bean.getValor(CASILLA_RESERVADO_2.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_RESERVADO_2));
		}else{
			line.append(changeSize(campo, TAM_RESERVADO_2, ' ', false));
		}

		campo = bean.getValor(CASILLA_MUNICIPIO_TITULAR_3.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_MUNICIPIO_TITULAR_3));
		}else{
			line.append(changeSize(campo, TAM_MUNICIPIO_TITULAR_3, ' ', false));
		}

		campo = bean.getValor(CASILLA_PUEBLO_TITULAR_3.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_PUEBLO_TITULAR_3));
		}else{
			line.append(changeSize(campo, TAM_PUEBLO_TITULAR_3, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_PROVINCIA_TITULAR_3.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_PROVINCIA_TITULAR_3));
		}else{
			line.append(changeSize(campo, TAM_PROVINCIA_TITULAR_3, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_CP_TITULAR_3.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_CP_TITULAR_3));
		}else{
			line.append(changeSize(campo, TAM_CP_TITULAR_3, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_SEXO_TITULAR_3.longValue());
		if(null==campo || "".equals(campo)){
			line.append(changeSize("", TAM_SEXO_TITULAR_3));
		}else{
			line.append(changeSize(campo, TAM_SEXO_TITULAR_3, ' ', false));
		}
		
		campo = bean.getValor(CASILLA_FECHA_NACIMIENTO_TITULAR_3.longValue());
		if(null == campo || "".equals(campo)){
			line.append(changeSize("", TAM_FECHA_NACIMIENTO_TITULAR_3));
		}else{
			line.append(campo);
		}
		
		return line.toString();
	}
	
	private static String changeSize(String vacio, int tam) {
		String cadena = "";
		for (int i=0;i<tam;i++) {
			cadena = cadena + " ";
		}
		return cadena;
	}
	
	private static String changeSize(String campo, int tam, char a, boolean b) {
		String cadena = "";
		for (int i=campo.length();i<tam;i++) {
			cadena = cadena + " ";
		}
		return campo+cadena;
	}
	*/

	/**
	 * Si una cadena tiene tamaño mayor que el que se recibe, se recorta hasta ese tamaño.
	 * @param cadena
	 * @param tam
	 * @return
	 */
	public static String recortaCadena(String cadena, int tam){
		String retorno = cadena;
		
		if(cadena!=null && cadena.length() > tam){
			retorno = cadena.substring(0, tam);
		}
		return retorno;
	}
	
	/**
	 * Comprueba si el valor pasado como parametro es vacio o no
	 * @param valor String con el valor a validar
	 * @param conEspacios Indica si se admite que el valor sea todo espacios (blancos) o no
	 * @return Devuelve <code>true</code> si el valor es vacio (<code>null</code>, cadena vacía
	 * o espacios en caso de no admitirlos)
	 */
	public static boolean esVacio(String valor, boolean conEspacios){
		boolean vacio = (valor == null || valor.length() == 0);
		if (!vacio && !conEspacios){
			vacio = "".equals(valor.trim());
		}
		
		return vacio;
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
