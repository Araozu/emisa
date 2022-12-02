package dev.araozu.emisa2.api.referencial;

import dev.araozu.emisa2.api.EmisaServlet;
import dev.araozu.emisa2.api.InfoCampo;

import java.util.LinkedList;

public final class RZC_Empleo_Servlet extends EmisaServlet {

    static final String nombreTabla = "RZC_EMPLEO";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "EmpCod";
    static final String campoEstReg = "EmpEstReg";

    static {
        campos.add(new InfoCampo("EmpNom", "string"));
        campos.add(new InfoCampo("EmpSue", "decimal"));
        campos.add(new InfoCampo("EmpEst", "decimal"));
        campos.add(new InfoCampo("EmpViaCod", "int"));
        campos.add(new InfoCampo("EmpCatCod", "int"));
    }

    public RZC_Empleo_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
