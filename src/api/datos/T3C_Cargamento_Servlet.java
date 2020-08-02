package api.datos;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public class T3C_Cargamento_Servlet extends EmisaServlet {

    static final String nombreTabla = "t3c_cargamento";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "CarCod";
    static final String campoEstReg = "CarEstReg";

    static {
        campos.add(new InfoCampo("CarAlmCod", "int"));
        campos.add(new InfoCampo("CarRecCod", "int"));
        campos.add(new InfoCampo("CarNom", "string"));
        campos.add(new InfoCampo("CarMinCan", "decimal"));
        campos.add(new InfoCampo("CarFecAnio", "decimal"));
        campos.add(new InfoCampo("CarFecMes", "decimal"));
        campos.add(new InfoCampo("CarFecDia", "decimal"));
    }

    public T3C_Cargamento_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
