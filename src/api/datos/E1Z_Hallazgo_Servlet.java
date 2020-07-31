package api.datos;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public class E1Z_Hallazgo_Servlet extends EmisaServlet {

    static final String nombreTabla = "e1z_hallazgo";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "HalCod";
    static final String campoEstReg = "HalEstReg";

    static {
        campos.add(new InfoCampo("HalProCod", "int"));
        campos.add(new InfoCampo("HalMinCod", "int"));
        campos.add(new InfoCampo("HalSit", "int"));
    }

    public E1Z_Hallazgo_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
