package dev.araozu.emisa2.api.datos;

import dev.araozu.emisa2.api.EmisaServlet;
import dev.araozu.emisa2.api.InfoCampo;

import java.util.LinkedList;

public class RZC_Factoria_Empleo_Servlet extends EmisaServlet {

    static final String nombreTabla = "RZC_FACTORIA_EMPLEO";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "FacEmpCod";
    static final String campoEstReg = "FacEmpEstReg";

    static {
        campos.add(new InfoCampo("FacEmpPerCod", "int"));
        campos.add(new InfoCampo("FacCod", "int"));
    }

    public RZC_Factoria_Empleo_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
