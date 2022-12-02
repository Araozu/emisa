package dev.araozu.emisa2.api.datos;

import dev.araozu.emisa2.api.EmisaServlet;
import dev.araozu.emisa2.api.InfoCampo;

import java.util.LinkedList;

public class E1Z_Hallazgo_Servlet extends EmisaServlet {

    static final String nombreTabla = "E1Z_HALLAZGO";
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
