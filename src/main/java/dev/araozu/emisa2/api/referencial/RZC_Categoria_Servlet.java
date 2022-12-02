package dev.araozu.emisa2.api.referencial;

import dev.araozu.emisa2.api.EmisaServlet;
import dev.araozu.emisa2.api.InfoCampo;

import java.util.LinkedList;

public final class RZC_Categoria_Servlet extends EmisaServlet {

    static final String nombreTabla = "RZC_CATEGORIA";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "CatCod";
    static final String campoEstReg = "CatEstReg";
    static {
        campos.add(new InfoCampo("CatNom", "string"));
        campos.add(new InfoCampo("CatSuel", "decimal"));
    }

    public RZC_Categoria_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
