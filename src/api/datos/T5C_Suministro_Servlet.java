package api.datos;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public class T5C_Suministro_Servlet extends EmisaServlet {

    static final String nombreTabla = "T5C_SUMINISTRO";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "SumCod";
    static final String campoEstReg = "SumEstReg";

    static {
        campos.add(new InfoCampo("SumViaCod", "int"));
    }

    public T5C_Suministro_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
