package api.referencial;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public final class GZZ_Astro_Servlet extends EmisaServlet {

    static final String nombreTabla = "GZZ_ASTRO";
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
