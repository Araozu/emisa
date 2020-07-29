package api.referencial;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public final class GZM_Mineral_Servlet extends EmisaServlet {

    static final String nombreTabla = "gzm_mineral";
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