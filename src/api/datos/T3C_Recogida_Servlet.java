package api.datos;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public class T3C_Recogida_Servlet extends EmisaServlet {

    static final String nombreTabla = "T3C_RECOGIDA";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "RecCod";
    static final String campoEstReg = "RecEstReg";

    static {
        campos.add(new InfoCampo("RecViaCod", "int"));
        campos.add(new InfoCampo("RecFecAnio", "decimal"));
        campos.add(new InfoCampo("RecFecMes", "decimal"));
        campos.add(new InfoCampo("RecFecDia", "decimal"));
    }

    public T3C_Recogida_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
