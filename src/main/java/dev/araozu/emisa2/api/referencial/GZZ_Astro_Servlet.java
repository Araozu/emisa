package dev.araozu.emisa2.api.referencial;

import dev.araozu.emisa2.api.EmisaServlet;
import dev.araozu.emisa2.api.InfoCampo;

import java.util.LinkedList;

public final class GZZ_Astro_Servlet extends EmisaServlet {

    static final String nombreTabla = "GZZ_ASTROS";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "AstCod";
    static final String campoEstReg = "AstEstReg";

    static {
        campos.add(new InfoCampo("AstNom", "string"));
        campos.add(new InfoCampo("AstTip", "decimal"));
    }

    public GZZ_Astro_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
