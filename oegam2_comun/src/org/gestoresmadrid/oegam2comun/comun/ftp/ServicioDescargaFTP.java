package org.gestoresmadrid.oegam2comun.comun.ftp;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import es.globaltms.gestorDocumentos.bean.FicheroBean;

public interface ServicioDescargaFTP {
	
	public List<String> listarDirectorio(String server, int port, String user, String pass, String path) throws SocketException, IOException;
	
	public FicheroBean descargar(String server, int port, String user, String pass, String file, String path);
	
	public Boolean borrar(String server, int port, String user, String pass, String file, String path);

}
