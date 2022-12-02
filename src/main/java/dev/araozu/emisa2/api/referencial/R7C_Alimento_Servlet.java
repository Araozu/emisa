package dev.araozu.emisa2.api.referencial;

import dev.araozu.emisa2.api.EmisaServlet;
import dev.araozu.emisa2.api.InfoCampo;

import java.util.LinkedList;

public final class R7C_Alimento_Servlet extends EmisaServlet {

    static final String nombreTabla = "R7C_ALIMENTO";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "AlimCod";
    static final String campoEstReg = "AlimEstReg";

    static {
        campos.add(new InfoCampo("AlimNom", "string"));
        campos.add(new InfoCampo("AlimCos", "decimal"));
        campos.add(new InfoCampo("AlimCan", "decimal"));
    }

    public R7C_Alimento_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
