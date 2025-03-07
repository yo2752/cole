package org.gestoresmadrid.core.arrendatarios.model.enumerados;

import java.math.BigDecimal;

public enum EstadoCaycEnum {
    
    Iniciado("1", "Iniciado") {
        public String toString() {
            return "1";
        }
    },
    Validado("2", "Validado") {
        public String toString() {
            return "2";
        }
    },
    Pdte_Respuesta_DGT("3", "Pendiente Respuesta DGT") {
        public String toString() {
            return "3";
        }
    },
    Finalizado("4", "Finalizado") {
        public String toString() {
            return "4";
        }
    },
    Finalizado_Con_Error("5", "Finalizada Con Error") {
        public String toString() {
            return "5";
        }
    },
    Anulado("6", "Anulado") {
        public String toString() {
            return "6";
        }
    };
    
    private String valorEnum;
    private String nombreEnum;
    
    private EstadoCaycEnum(String valorEnum, String nombreEnum) {
        this.valorEnum = valorEnum;
        this.nombreEnum = nombreEnum;
    }
    
    public String getValorEnum() {
        return valorEnum;
    }
    
    public void setValorEnum(String valorEnum) {
        this.valorEnum = valorEnum;
    }
    
    public String getNombreEnum() {
        return nombreEnum;
    }
    
    public void setNombreEnum(String nombreEnum) {
        this.nombreEnum = nombreEnum;
    }
    
    public static EstadoCaycEnum convertir(String valorEnum) {
        if (valorEnum != null && !valorEnum.isEmpty()) {
            for (EstadoCaycEnum estado : EstadoCaycEnum.values()) {
                if (estado.getValorEnum().equals(valorEnum)) { return estado; }
            }
        }
        return null;
    }
    
    public static String convertirTexto(String valor) {
        if (valor != null) {
            for (EstadoCaycEnum estado : EstadoCaycEnum.values()) {
                if (estado.getValorEnum().equals(valor)) { return estado.getNombreEnum(); }
            }
        }
        return null;
    }
    
    public static String convertirNombre(String nombre) {
        if (nombre != null) {
            for (EstadoCaycEnum estado : EstadoCaycEnum.values()) {
                if (estado.getNombreEnum().equals(nombre)) { return estado.getValorEnum(); }
            }
        }
        return null;
    }
    
    public static String convertirTexto(EstadoCaycEnum estadoDev) {
        if (estadoDev != null) {
            for (EstadoCaycEnum estado : EstadoCaycEnum.values()) {
                if (estado.getValorEnum() == estadoDev.getValorEnum()) { return estado.getNombreEnum(); }
            }
        }
        return null;
    }
    
    public static String convertirEstadoBigDecimal(BigDecimal estado) {
        if (estado != null) { return convertirTexto(estado.toString()); }
        return "";
    }
}
