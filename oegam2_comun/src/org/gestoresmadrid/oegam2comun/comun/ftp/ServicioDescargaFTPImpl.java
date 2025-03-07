package org.gestoresmadrid.oegam2comun.comun.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDescargaFTPImpl implements ServicioDescargaFTP{
	
	private ILoggerOegam log = LoggerOegam.getLogger(this.getClass());
	private static final String PROPERTY_KEY_PROTOCOL_FTP = "ftp.importacion.datossensibles.protocolo";
	private static final String PROTOCOL_FTPS = "FTPS";

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	public List<String> listarDirectorio(String server, int port, String user, String pass, String path) throws SocketException, IOException{
		List<String> fileList = new ArrayList<String>();
		
		FTPClient ftpClient = null;
		
		if(PROTOCOL_FTPS.equals(gestorPropiedades.valorPropertie(PROPERTY_KEY_PROTOCOL_FTP))){
			ftpClient = new FTPSClient("TLS",false);
		}else{
			ftpClient = new FTPClient();
		}
		
		
		FTPClientConfig ftpClientConfig = new FTPClientConfig(FTPClientConfig.SYST_NT); //WINDOWS_NT
		ftpClient.configure(ftpClientConfig);
		
		// in case of problems; outputs all conversation to the console
		//ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out))); 
		
		ftpClient.connect(server, port);
		ftpClient.enterRemotePassiveMode();
		ftpClient.login(user, pass);
		ftpClient.enterLocalPassiveMode();
		
		FTPFile[] files = ftpClient.listFiles(path);
		 		 
		for (FTPFile file : files) {
		    String details = file.getName();
		    if (!file.isDirectory()) {
		    	fileList.add(details);
		    }
		    
		}
		ftpClient.logout();
		ftpClient.disconnect();
			
		return fileList;
	}
	
	public FicheroBean descargar(String server, int port, String user, String pass, String file, String path){

		FTPClient ftpClient = null;
		if(PROTOCOL_FTPS.equals(gestorPropiedades.valorPropertie(PROPERTY_KEY_PROTOCOL_FTP))){
			ftpClient = new FTPSClient("TLS",false);
		}else{
			ftpClient = new FTPClient();
		}
		FicheroBean downloadedFile = new FicheroBean();
		downloadedFile.setNombreYExtension(file);
        try {
    		FTPClientConfig ftpClientConfig = new FTPClientConfig(FTPClientConfig.SYST_NT);
    		ftpClient.configure(ftpClientConfig);
    		
    		// in case of problems; outputs all conversation to the console
    		//ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    		
    		ftpClient.connect(server, port);
    		ftpClient.enterRemotePassiveMode();
    		ftpClient.login(user, pass);
    		
    		ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
            String remoteFile = path+file;

            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile);
            downloadedFile.setFicheroByte(IOUtils.toByteArray(inputStream));
 
            boolean success = ftpClient.completePendingCommand();
            if (success) {
                log.info("Archivo "+downloadedFile.getNombreDocumento() +" recuperado correctamente.");
            }

            inputStream.close();
 
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
            	log.error(ex);
            }
        }
		
        return downloadedFile;
	}
	
	public Boolean borrar(String server, int port, String user, String pass, String file, String path){

		 
		FTPClient ftpClient = null;
		
		if(PROTOCOL_FTPS.equals(gestorPropiedades.valorPropertie(PROPERTY_KEY_PROTOCOL_FTP))){
			ftpClient = new FTPSClient("TLS",false);
		}else{
			ftpClient = new FTPClient();
		}
		
		Boolean resultado = false;
        try {
    		FTPClientConfig ftpClientConfig = new FTPClientConfig(FTPClientConfig.SYST_NT);
    		ftpClient.configure(ftpClientConfig);
    		
    		// in case of problems; outputs all conversation to the console
    		//ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    		
    		ftpClient.connect(server, port);
    		ftpClient.enterRemotePassiveMode();
    		ftpClient.login(user, pass);
    		
    		ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            
            String remoteFile = path+file;
            resultado = ftpClient.deleteFile(remoteFile);

 
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
            	log.error(ex);
            }
        }
		
        return resultado;
	}
	
	
}
