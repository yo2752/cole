package trafico.beans;

import general.utiles.UtilesCadenaCaracteres;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.EnvioDataVO;



public class EnvioDataPantalla{
	
	private String proceso; 
	private String fechaEnvio;
	private String correcta;
	private String numExpediente;
	private String respuesta;
	private String cola;
	private Date fechaUltCola;
	
	public EnvioDataPantalla(EnvioDataVO envioData){
		this.proceso = envioData.getId().getProceso();
		this.fechaEnvio = getFechaFormateada(envioData.getFechaEnvio());
		this.correcta = envioData.getId().getCorrecta();
		this.cola = envioData.getId().getCola();
		
		if(envioData.getNumExpediente() == null){
			this.numExpediente = "n.a";
		}else{
			this.numExpediente = envioData.getNumExpediente().toString();
		}
		this.respuesta = UtilesCadenaCaracteres.nl2br(envioData.getRespuesta());
		
		if(envioData.getId().getCola() == null){
			this.cola = "n.a";
		}else{
			this.cola = envioData.getId().getCola().toString();
		}
		
		this.respuesta = UtilesCadenaCaracteres.nl2br(envioData.getRespuesta());
		
		this.fechaUltCola = envioData.getFechaEnvio();
		
	}
	
	public EnvioDataPantalla(){}
	
	private static String getFechaFormateada(Date fecha){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return simpleDateFormat.format(fecha);
	}
	
	public static ArrayList<EnvioDataPantalla> convertir(List<EnvioDataVO> listaEnvioData){
		ArrayList<EnvioDataPantalla> listaEnvioDataPantalla = new ArrayList<EnvioDataPantalla>();
		for(EnvioDataVO envioData : listaEnvioData){
			EnvioDataPantalla envioDataPantalla = new EnvioDataPantalla(envioData);
			listaEnvioDataPantalla.add(envioDataPantalla);
		}
		return listaEnvioDataPantalla;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public String getCorrecta() {
		return correcta;
	}

	public void setCorrecta(String correcta) {
		this.correcta = correcta;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getCola() {
		return cola;
	}

	public void setCola(String cola) {
		this.cola = cola;
	}

	public Date getFechaUltCola() {
		return fechaUltCola;
	}

	public void setFechaUltCola(Date fechaUltCola) {
		this.fechaUltCola = fechaUltCola;
	}
	
	
}
