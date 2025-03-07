package org.gestoresmadrid.oegam2comun.pegatinas.model.service;

import java.io.IOException;

import org.apache.commons.httpclient.auth.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class HandlerErrorRest implements ResponseErrorHandler {

private static final ILoggerOegam logger = LoggerOegam.getLogger(HandlerErrorRest.class);

	@Override
	public void handleError(ClientHttpResponse clienthttpresponse) throws IOException {
	
	    if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
	        logger.debug(HttpStatus.FORBIDDEN + " response. Throwing authentication exception");
	        throw new AuthenticationException();
	    }
	}
	
	@Override
	public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {
	
	    if (clienthttpresponse.getStatusCode() != HttpStatus.OK) {
	        logger.debug("Status code: " + clienthttpresponse.getStatusCode());
	        logger.debug("Response" + clienthttpresponse.getStatusText());
	        logger.debug(clienthttpresponse.getBody());
	
	        if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
	            logger.debug("Call returned a error 403 forbidden resposne ");
	            return true;
	        }
	    }
	    return false;
	}

}
