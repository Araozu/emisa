const app = Vue.createApp({
    template: `
    <div class="gzz_astro">
        <h1>RZC_FACTORIA_EMPLEO</h1>
        <grilla-datos etiqueta="Factoria_Empleo" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla RZC_FACTORIA_EMPLEO"
            :nombresColumnas="nombresColumnas"
            :filas="filas"
            :funAlClick="seleccionarFila"
            :posFilaSeleccionada="posFilaSeleccionada"/>
        <br>
        <grilla-botones
            :estadoBotones="estadoBotones"
            :funAdicionar="iniciarAdicion"
            :funModificar="iniciarModificacion"
            :funEliminar="iniciarEliminar"
            :funCancelar="limpiar"
            :funInactivar="iniciarInactivar"
            :funReactivar="iniciarReactivar"
            :funActualizar="actualizar"
            :funSalir="cerrarVentana"
        />
        <div class="mensaje-error" :style="estilosMensajeError">{{ mensajeError }}</div>
    </div>`,
    setup() {
        const campos = [
            {
                tipo: "number",
                nombre: "FacEmpCod"
            },
            {
                tipo: "number",
                nombre: "FacEmpPerCod",
                ref: "r6z_personal"
            },
            {
                tipo: "number",
                nombre: "FacCod",
                ref: "ezm_factoria"
            },
            {
                tipo: "select",
                nombre: "FacEmpEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "rzc_factoria_empleo",
            nombreCampoCod: "FacEmpCod",
            nombreCampoEstReg: "FacEmpEstReg",
            estadoCamposModificar: [["FacEmpPerCod", "FacCod"], ["FacEmpCod", "FacEmpEstReg"]],
            estadoCamposEliminar: [[], ["FacEmpPerCod", "FacCod", "FacEmpCod", "FacEmpEstReg"]],
            estadoCamposInactivar: [[], ["FacEmpPerCod", "FacCod", "FacEmpCod", "FacEmpEstReg"]],
            estadoCamposReactivar: [[], ["FacEmpPerCod", "FacCod", "FacEmpCod", "FacEmpEstReg"]]
        };

        const {mensajeError, estilosMensajeError, mostrarMensaje} = usarMensajesError();
        const {
            filas,
            valores,
            funActualizarValor,
            camposDesactivados,
            limpiarCampos,
            limpiar,
            marcarBotones,
            nombresColumnas,
            operacionActual,
            posFilaSeleccionada,
            estadoBotones,
            iniciarAdicion,
            iniciarModificacion,
            iniciarEliminar,
            iniciarInactivar,
            iniciarReactivar,
            seleccionarFila,
            cargarFilas,
            actualizar,
            cerrarVentana
        } = usarCamposAdaptados(campos, estados, mensajeError, mostrarMensaje);

        setTimeout(cargarFilas, 0);

        return {
            camposDesactivados,
            filas,
            mensajeError,
            estadoBotones,
            operacionActual,
            posFilaSeleccionada,
            estilosMensajeError,
            nombresColumnas,
            limpiarFormulario: limpiarCampos,
            limpiar,
            marcarBotones,
            iniciarAdicion,
            iniciarModificacion,
            iniciarEliminar,
            iniciarInactivar,
            iniciarReactivar,
            seleccionarFila,
            cerrarVentana,
            actualizar,
            campos,
            valores,
            funActualizarValor
        }
    }
});
