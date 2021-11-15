package api.datos;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public class E1C_Prospeccion_Servlet extends EmisaServlet {

    static final String nombreTabla = "E1C_PROSPECCION";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "ProsCod";
    static final String campoEstReg = "ProsEstReg";

    static {
        campos.add(new InfoCampo("ProsEst", "decimal"));
        campos.add(new InfoCampo("ProsUbiCod", "int"));
    }

    public E1C_Prospeccion_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
