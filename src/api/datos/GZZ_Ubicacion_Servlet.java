package api.datos;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public class GZZ_Ubicacion_Servlet extends EmisaServlet {

    static final String nombreTabla = "GZZ_UBICACION";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "UbiCod";
    static final String campoEstReg = "UbiEstReg";

    static {
        campos.add(new InfoCampo("UbiNom", "string"));
        campos.add(new InfoCampo("UbiAstCod", "int"));
        campos.add(new InfoCampo("UbiCoorX", "decimal"));
        campos.add(new InfoCampo("UbiCoorY", "decimal"));
        campos.add(new InfoCampo("UbiCoorZ", "decimal"));
    }

    public GZZ_Ubicacion_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
