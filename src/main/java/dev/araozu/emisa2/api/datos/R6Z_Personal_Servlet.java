package dev.araozu.emisa2.api.datos;

import dev.araozu.emisa2.api.EmisaServlet;
import dev.araozu.emisa2.api.InfoCampo;

import java.util.LinkedList;

public class R6Z_Personal_Servlet extends EmisaServlet {

    static final String nombreTabla = "R6Z_PERSONAL";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "PerCod";
    static final String campoEstReg = "PerEstReg";

    static {
        campos.add(new InfoCampo("PerNom", "string"));
        campos.add(new InfoCampo("PerEmpCod", "int"));
    }

    public R6Z_Personal_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
