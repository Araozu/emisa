package api.datos;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public class RZC_Factoria_Empleo_Servlet extends EmisaServlet {

    static final String nombreTabla = "r7c_factoria_empleo";
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
