package com.matriculasWS.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmDatosEntradaMatriculasWS;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmDatosSalidaMatriculasWS;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmResultadoWSMatriculasWS;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.matriculasWS.beans.DatosEntrada;
import com.matriculasWS.beans.DatosSalida;
import com.matriculasWS.beans.ResultadoWS;
import com.matriculasWS.beans.TipoFecha;
import com.matriculasWS.utiles.ConstantesCodigosError;

import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.constantes.ConstantesIVTM;

public class ModeloObtenerMatricula {

	// log de errores	
	private static final Logger log = Logger.getLogger(ModeloObtenerMatricula.class);	

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	public ModeloObtenerMatricula() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * Obtenemos los nombres de via de la tabla VIA
	 * @return List<Via> en caso de error devuelve null
	 */
	public  DatosSalida obtenerMatriculas(DatosEntrada datosEntrada){
		log.info("Inicio: obtenerMatriculas");
		DatosSalida datosSalida = new DatosSalida();
		datosSalida = comprobarDatosEntrada(datosEntrada);
		
		if (! (datosSalida.getCodigoResultado() == ConstantesCodigosError.RESULTADO_OK)){
			log.info("Fin: obtenerMatriculas: NOK");
			return datosSalida;
		}
		
		IvtmDatosEntradaMatriculasWS ivtmDatosEntrada = new IvtmDatosEntradaMatriculasWS();
		datosSalida = convertirDatosEntradaSiSePuede(datosEntrada, ivtmDatosEntrada, datosSalida);
		if (datosSalida.getCodigoResultado()!=null && !datosSalida.getCodigoResultado().equals(ConstantesCodigosError.RESULTADO_OK)){
			return datosSalida;
		}
		
		log.info("Llamado a consulta de Busqueda.");
		//Realiza la llamada para la consulta
		IVTMModeloMatriculacionInterface ivtmModelo = new IVTMModeloMatriculacionImpl();
		IvtmDatosSalidaMatriculasWS ivtmDatosSalida = ivtmModelo.recuperarMatriculasWS(ivtmDatosEntrada);
		
		//Transformamos la información de respuesta
		datosSalida.setListaResultados(convertirAResultadoWS(ivtmDatosSalida.getListaResultados()));
		datosSalida.setCodigoResultado(ConstantesCodigosError.RESULTADO_OK);
		if (datosSalida.getListaResultados() == null || datosSalida.getListaResultados().length==0 ){
			datosSalida.setCodigoResultado(ConstantesCodigosError.RESULTADO_NO_HAY_RESULTADOS);
			datosSalida.setListaResultados(new ResultadoWS[0]);
			log.info("Resultado: " + ConstantesIVTM.RESULTADO_NO_HAY_RESULTADOS);
		}
		
		//Imprimimos el resultado en los logs 
		if (datosSalida != null && datosSalida.getCodigoResultado() !=null){
			log.info("CodigoResultado :"+datosSalida.getCodigoResultado().getCodigo());
			log.info("MensajeResultado :"+datosSalida.getCodigoResultado().getMensaje());
			log.info("Resultado :"+datosSalida.getCodigoResultado().getResultado());
		}
		log.info("Fin: obtenerMatriculas");
		return datosSalida; 
	}
	
	private DatosSalida convertirDatosEntradaSiSePuede (DatosEntrada datosEntrada,  IvtmDatosEntradaMatriculasWS ivtmDatosEntrada, DatosSalida  datosSalida){
		try {
			if (datosEntrada.getFechaInicio() != null && datosEntrada.getFechaInicio().getDia() != null && datosEntrada.getFechaInicio().getMes() != null && datosEntrada.getFechaInicio().getAnio() != null){
				Date  fechaInicio = obtenerFecha(datosEntrada.getFechaInicio());
				log.info("Dato de Entrada: fechaInicio: "+datosEntrada.getFechaInicio().getDia()+"/"+datosEntrada.getFechaInicio().getMes()+"/"+datosEntrada.getFechaInicio().getAnio());
				if (fechaInicio == null){
					datosSalida.setCodigoResultado(ConstantesCodigosError.RESULTADO_ERROR_PARAMETROS_FECHA);
					log.info("Dato de Entrada: fechaInicio: "+ConstantesCodigosError.RESULTADO_ERROR_PARAMETROS_FECHA);
					return datosSalida;
				}
				ivtmDatosEntrada.setFechaInicio(utilesFecha.getFechaConDate(fechaInicio));
			}
		} catch (Exception e) {
			log.error("Error en fecha inicio Introducida");
			datosSalida.setCodigoResultado(ConstantesCodigosError.RESULTADO_ERROR_PARAMETROS_FECHA);
		}
		try{	
			if (datosEntrada.getFechaFin() != null && datosEntrada.getFechaFin().getDia() != null && datosEntrada.getFechaFin().getMes() != null && datosEntrada.getFechaFin().getAnio() != null){
				Date  fechaFin = obtenerFecha(datosEntrada.getFechaFin());
				log.info("Dato de Entrada: fechaFin: "+datosEntrada.getFechaFin().getDia()+"/"+datosEntrada.getFechaFin().getMes()+"/"+datosEntrada.getFechaFin().getAnio());
				if (fechaFin == null){
					datosSalida.setCodigoResultado(ConstantesCodigosError.RESULTADO_ERROR_PARAMETROS_FECHA);
					log.info("Dato de Entrada: fechaFin: "+ConstantesCodigosError.RESULTADO_ERROR_PARAMETROS_FECHA);
					return datosSalida;
				}
				
				ivtmDatosEntrada.setFechaFin(utilesFecha.getFechaConDate(fechaFin));
			}
		} catch (Exception e) {
			log.error("Error en fecha de fin Introducida");
			datosSalida.setCodigoResultado(ConstantesCodigosError.RESULTADO_ERROR_PARAMETROS_FECHA);
		}
		if (datosEntrada.getListaAutoliquidaciones()!=null){
			for (int i=0; i<datosEntrada.getListaAutoliquidaciones().length; i++){
				datosEntrada.getListaAutoliquidaciones()[i]= datosEntrada.getListaAutoliquidaciones()[i].replaceAll("[\n,\r, ]","");
			}
		}
		ivtmDatosEntrada.setListaAutoliquidaciones(datosEntrada.getListaAutoliquidaciones());
		return datosSalida;
	}
	
	private ResultadoWS[] convertirAResultadoWS(IvtmResultadoWSMatriculasWS[] listaResultados) {
		ResultadoWS[] resultado = null;
		if (listaResultados!=null){
			resultado = new ResultadoWS[listaResultados.length];
			for (int i=0; i<listaResultados.length;i++){
				ResultadoWS r = new ResultadoWS();
				r.setBastidor(listaResultados[i].getBastidor());
				r.setDescripcion(listaResultados[i].getDescripcion());
				r.setMatricula(listaResultados[i].getMatricula());
				r.setNumAutoliquidacion(listaResultados[i].getNumAutoliquidacion());
				resultado[i]=r;
			}
		}
		return resultado;
	}

	private static DatosSalida comprobarDatosEntrada(DatosEntrada datosEntrada) {
		DatosSalida datosSalida = new DatosSalida();
		datosSalida.setCodigoResultado(ConstantesCodigosError.RESULTADO_OK);
		boolean existeFechaInicio = datosEntrada.getFechaInicio()!=null && datosEntrada.getFechaInicio().getDia()!=null && datosEntrada.getFechaInicio().getMes()!=null && datosEntrada.getFechaInicio().getAnio()!=null;
		boolean existeFechaFin = datosEntrada.getFechaFin()!=null && datosEntrada.getFechaFin().getDia()!=null && datosEntrada.getFechaFin().getMes()!=null && datosEntrada.getFechaFin().getAnio()!=null;
		boolean existeAutoliquidaciones = datosEntrada.getListaAutoliquidaciones()!=null && datosEntrada.getListaAutoliquidaciones().length>0;
		if (!existeAutoliquidaciones){
			if (!(existeFechaInicio && existeFechaFin)){
				datosSalida.setCodigoResultado(ConstantesCodigosError.RESULTADO_FALTAN_PARAMETROS);
			}
		} else {
			if (existeFechaInicio || existeFechaFin){
				datosSalida.setCodigoResultado(ConstantesCodigosError.RESULTADO_DEMASIADOS_PARAMETROS);
			}
		}
		return datosSalida;
	}
	
	
	public Date obtenerFecha(TipoFecha fecha){
		
		if(isfechaNula(fecha))
			return null;
		
		if (!isfechaValida( fecha)){
			return null;
		}
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
			Date fechaIni = dateFormat.parse(
					utiles.changeSize(String.valueOf(fecha.getDia()), 2, '0', false) + 
					utiles.changeSize(String.valueOf(fecha.getMes()), 2, '0', false) +
					String.valueOf(fecha.getAnio()));
			return fechaIni;
		}catch(Exception e){
			log.error(e);
			return new Date();
		}
		
	}

	public static boolean isfechaNula( TipoFecha fecha){
		return fecha.getDia()==0 || fecha.getDia()<0 || "".equals(String.valueOf(fecha.getDia())) || fecha.getMes()==0 
		|| "".equals(String.valueOf(fecha.getMes()) ) || fecha.getMes()<0 || fecha.getAnio()<0
		|| fecha.getAnio()==0 || "".equals(String.valueOf(fecha.getAnio()));
	}
	
	public static boolean isfechaValida( TipoFecha fecha){
		
		if(fecha.getMes()<0 || fecha.getMes()>12){
			return false;
		}
		
		if(fecha.getAnio()<1900){
			return false;
		}
		
		Integer mes = fecha.getMes();
		Integer dia = fecha.getDia();
		if((mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) && (dia>31)){
			return false;
		}
		
		if(mes == 2 && dia >29){
			return false;
		}
		
		if((mes==4 || mes == 6|| mes == 9|| mes == 11) && (dia > 30)){
			return false;
		}
		
		return true;
	}

}
