package api.referencial;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public final class P8M_Centro_Servlet extends EmisaServlet {

    static final String nombreTabla = "P8M_CENTRO";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "CenCod";
    static final String campoEstReg = "CenEstReg";

    static {
        campos.add(new InfoCampo("CenNom", "string"));
        campos.add(new InfoCampo("CenMinCod", "int"));
    }

    public P8M_Centro_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}