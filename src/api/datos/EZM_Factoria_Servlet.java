package api.datos;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public class EZM_Factoria_Servlet extends EmisaServlet {

    static final String nombreTabla = "ezm_factoria";
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
