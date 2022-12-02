package dev.araozu.emisa2.api.datos;

import dev.araozu.emisa2.api.EmisaServlet;
import dev.araozu.emisa2.api.InfoCampo;

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
