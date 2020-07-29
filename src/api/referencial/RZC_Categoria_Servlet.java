package api.referencial;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public final class RZC_Categoria_Servlet extends EmisaServlet {

    static final String nombreTabla = "r7c_categoria";
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
