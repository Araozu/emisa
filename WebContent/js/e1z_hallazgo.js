app.component("e1z-hallazgo", {
    template: `
    <div class="gzz_astro">
        <h1>E1Z_HALLAZGO</h1>
        <grilla-datos etiqueta="Hallazgo" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla E1Z_HALLAZGO"
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
                nombre: "HalCod"
            },
            {
                tipo: "number",
                nombre: "HalProCod",
                ref: "e1c_prospeccion"
            },
            {
                tipo: "number",
                nombre: "HalMinCod",
                ref: "gzm_mineral"
            },
            {
                tipo: "number",
                nombre: "HalSit"
            },
            {
                tipo: "select",
                nombre: "HalEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "e1z_hallazgo",
            nombreCampoCod: "HalCod",
            nombreCampoEstReg: "HalEstReg",
            estadoCamposModificar: [["HalProCod", "HalMinCod", "HalSit"], ["HalCod", "HalEstReg"]],
            estadoCamposEliminar: [[], ["HalProCod", "HalMinCod", "HalSit", "HalCod", "HalEstReg"]],
            estadoCamposInactivar: [[], ["HalProCod", "HalMinCod", "HalSit", "HalCod", "HalEstReg"]],
            estadoCamposReactivar: [[], ["HalProCod", "HalMinCod", "HalSit", "HalCod", "HalEstReg"]]
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
