package com.matriculasWS.servicioWeb;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Properties;

import org.apache.log4j.Logger;

import trafico.utiles.constantes.ConstantesIVTM;

import com.matriculasWS.beans.DatosEntrada;
import com.matriculasWS.beans.DatosSalida;
import com.matriculasWS.beans.ResultadoWS;
import com.matriculasWS.modelo.ModeloObtenerMatricula;
import com.matriculasWS.utiles.ConstantesCodigosError;


public class ObtenerMatriculasWS {

	private static final Logger log = Logger.getLogger(ObtenerMatriculasWS.class);
	
	public DatosSalida consultaMatriculas (String authUser, String authPass, DatosEntrada datosEntrada){
		log.info("En el servicio de Consulta de Matrículas");
		DatosSalida datosSalida = new DatosSalida();
		datosSalida.setCodigoResultado(ConstantesCodigosError.RESULTADO_OK);
		
		try {
			log.info("Autenticando usuario...");
			Properties ficheroPropiedades = new Properties();
	        ficheroPropiedades.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(("matriculasWS.properties")));
	        String user = ficheroPropiedades.getProperty("user");
	        String pass = ficheroPropiedades.getProperty("password");
	        if(user.equals(hashString(authUser))&&pass.equals(hashString(authPass))){
	        	log.info("Usuario autenticado con éxito. Realizamos llamada al servicio..");
				ModeloObtenerMatricula modelo = new ModeloObtenerMatricula();
					datosSalida = modelo.obtenerMatriculas(datosEntrada);
	        }else{
	        	log.info("Fallo en la autenticación del usuario.");
	        	datosSalida.setCodigoResultado(ConstantesCodigosError.RESULTADO_USUARIO_PASS_INCORRECTO);
	        	datosSalida.setListaResultados(new ResultadoWS[0]);
	        	log.info(ConstantesIVTM.Usuario_o_password_incorrectos);
	        }
			
			
		} catch (Exception e) {
			datosSalida.setCodigoResultado(ConstantesCodigosError.RESULTADO_ERROR_INTERNO);
			datosSalida.setListaResultados(new ResultadoWS[0]);
		}
		
		return datosSalida;
	}
	
	private String hashString(String password) throws Exception {
		String hashword = null;
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(password.getBytes());
		BigInteger hash = new BigInteger(1, md5.digest());
		hashword = hash.toString(16);
		return hashword;
	}
}
