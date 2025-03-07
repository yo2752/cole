package com.matriculasWS.test;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import com.matriculasWS.beans.DatosEntrada;
import com.matriculasWS.beans.DatosSalida;
import com.matriculasWS.beans.ResultadoWS;
import com.matriculasWS.servicioWeb.ObtenerMatriculasWSServiceLocator;

public class TestMatriculaWS {

	@Autowired
	GestorPropiedades gestorPropiedades;

	public TestMatriculaWS() {
		
		try {
			DatosEntrada datosEntrada = new DatosEntrada();
//			datosEntrada.setFechaInicio(new TipoFecha(1,1,2011));
//			datosEntrada.setFechaFin(new TipoFecha(31,10,2011));
			ArrayList<String> listadoAuto = new ArrayList<String>();
			listadoAuto.add("21115000040000134");
			listadoAuto.add("21115000040000135");
			listadoAuto.add("21115000040000110");
			listadoAuto.add("21115000040000132");
			listadoAuto.add("21115000040000133");
			datosEntrada.setListaAutoliquidaciones(listadoAuto.toArray(new String[listadoAuto.size()]));
			ObtenerMatriculasWSServiceLocator locator = new ObtenerMatriculasWSServiceLocator();
			String usuario = "munimadrid";
			String password = gestorPropiedades.valorPropertie("pass.munimadrid");
			DatosSalida datosSalida =  locator.getObtenerMatriculasWS().consultaMatriculas(usuario, password, datosEntrada);
			System.out.println("Resultado: "+datosSalida.getCodigoResultado().getResultado());
			System.out.println("Codigo: "+datosSalida.getCodigoResultado().getCodigo());
			System.out.println("Mensaje: "+datosSalida.getCodigoResultado().getMensaje());
			int i=0;
			if (datosSalida.getListaResultados() != null){
				System.out.println("Tamanio: "+datosSalida.getListaResultados().length);
				for(ResultadoWS resultado : datosSalida.getListaResultados()){
					System.out.println("Resultado "+(i++)+":" );
					System.out.println("Bastidor: "+resultado.getBastidor());
					System.out.println("Matricula: "+resultado.getMatricula());
					System.out.println("Autoliquidacion: "+resultado.getNumAutoliquidacion());
				}
			}
			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestMatriculaWS();
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
