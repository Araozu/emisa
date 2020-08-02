package api.datos;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public class R7C_Provision_Servlet extends EmisaServlet {

    static final String nombreTabla = "r7c_provision";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "ProvCod";
    static final String campoEstReg = "ProvEstReg";

    static {
        campos.add(new InfoCampo("ProvFacNom", "string"));
        campos.add(new InfoCampo("ProvPes", "decimal"));
        campos.add(new InfoCampo("ProvAliCan", "decimal"));
        campos.add(new InfoCampo("ProvAlimCod", "int"));
        campos.add(new InfoCampo("ProvSumCod", "int"));
        campos.add(new InfoCampo("ProvFecLleAnio", "decimal"));
        campos.add(new InfoCampo("ProvFecLleMes", "decimal"));
        campos.add(new InfoCampo("ProvFecLleDia", "decimal"));
    }

    public R7C_Provision_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
