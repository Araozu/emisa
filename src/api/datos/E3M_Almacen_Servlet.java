package api.datos;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public class E3M_Almacen_Servlet extends EmisaServlet {

    static final String nombreTabla = "E3M_ALMACEN";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "AlmCod";
    static final String campoEstReg = "AlmEstReg";

    static {
        campos.add(new InfoCampo("AlmFacCod", "int"));
        campos.add(new InfoCampo("AlmMinCod", "int"));
        campos.add(new InfoCampo("AlmCap", "decimal"));
    }

    public E3M_Almacen_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
