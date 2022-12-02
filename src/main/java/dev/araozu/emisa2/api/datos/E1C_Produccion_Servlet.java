package dev.araozu.emisa2.api.datos;

import dev.araozu.emisa2.api.EmisaServlet;
import dev.araozu.emisa2.api.InfoCampo;

import java.util.LinkedList;

public class E1C_Produccion_Servlet extends EmisaServlet {

    static final String nombreTabla = "E1C_PRODUCCION";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "ProCod";
    static final String campoEstReg = "ProEstReg";

    static {
        campos.add(new InfoCampo("ProMinCan", "decimal"));
        campos.add(new InfoCampo("ProMinCod", "int"));
        campos.add(new InfoCampo("ProMinCal", "decimal"));
        campos.add(new InfoCampo("ProFecAnio", "decimal"));
        campos.add(new InfoCampo("ProFecMes", "decimal"));
        campos.add(new InfoCampo("ProFecDia", "decimal"));
    }

    public E1C_Produccion_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
