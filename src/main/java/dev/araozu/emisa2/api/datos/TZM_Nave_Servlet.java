package dev.araozu.emisa2.api.datos;

import dev.araozu.emisa2.api.EmisaServlet;
import dev.araozu.emisa2.api.InfoCampo;

import java.util.LinkedList;

public class TZM_Nave_Servlet extends EmisaServlet {

    static final String nombreTabla = "TZM_NAVE";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "NavCod";
    static final String campoEstReg = "NavEstReg";

    static {
        campos.add(new InfoCampo("NavNom", "string"));
    }

    public TZM_Nave_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
