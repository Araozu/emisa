package dev.araozu.emisa2.api.referencial;

import dev.araozu.emisa2.api.EmisaServlet;
import dev.araozu.emisa2.api.InfoCampo;

import java.util.LinkedList;

public final class GZM_Mineral_Servlet extends EmisaServlet {

    static final String nombreTabla = "GZM_MINERAL";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "MinCod";
    static final String campoEstReg = "MinEstReg";

    static {
        campos.add(new InfoCampo("MinNom", "string"));
    }

    public GZM_Mineral_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}