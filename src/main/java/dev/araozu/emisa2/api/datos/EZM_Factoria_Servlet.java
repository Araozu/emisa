package dev.araozu.emisa2.api.datos;

import dev.araozu.emisa2.api.EmisaServlet;
import dev.araozu.emisa2.api.InfoCampo;

import java.util.LinkedList;

public class EZM_Factoria_Servlet extends EmisaServlet {

    static final String nombreTabla = "EZM_FACTORIA";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "FacCod";
    static final String campoEstReg = "FacEstReg";

    static {
        campos.add(new InfoCampo("FacNom", "string"));
        campos.add(new InfoCampo("FacUbiCod", "int"));
    }

    public EZM_Factoria_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
