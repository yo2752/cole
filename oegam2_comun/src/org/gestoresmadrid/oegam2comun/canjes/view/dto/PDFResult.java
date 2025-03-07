package org.gestoresmadrid.oegam2comun.canjes.view.dto;

public class PDFResult {

	private byte[] pdfListadoColegio;
    private byte[] pdfListadoColegiado;

    public PDFResult(byte[] pdfListadoColegio, byte[] pdfListadoColegiado) {
        this.pdfListadoColegio = pdfListadoColegio;
        this.pdfListadoColegiado = pdfListadoColegiado;
    }

    public byte[] getPdfListadoColegio() {
        return pdfListadoColegio;
    }

    public byte[] getPdfListadoColegiado() {
        return pdfListadoColegiado;
    }
	
}
