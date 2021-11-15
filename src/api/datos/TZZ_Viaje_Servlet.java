package api.datos;

import api.EmisaServlet;
import api.InfoCampo;

import java.util.LinkedList;

public class TZZ_Viaje_Servlet extends EmisaServlet {

    static final String nombreTabla = "TZZ_VIAJE";
    static LinkedList<InfoCampo> campos = new LinkedList<>();
    static final String campoId = "ViaCod";
    static final String campoEstReg = "ViaEstReg";

    static {
        campos.add(new InfoCampo("ViaNavCod", "int"));
        campos.add(new InfoCampo("ViaFacCod", "int"));
        campos.add(new InfoCampo("FecDesAnio", "decimal"));
        campos.add(new InfoCampo("FecDesMes", "decimal"));
        campos.add(new InfoCampo("FecDesDia", "decimal"));
        campos.add(new InfoCampo("FecLleAnio", "decimal"));
        campos.add(new InfoCampo("FecLleMes", "decimal"));
        campos.add(new InfoCampo("FecLleDia", "decimal"));
    }

    public TZZ_Viaje_Servlet() {
        super(nombreTabla, campos, campoId, campoEstReg);
    }

}
