const app = Vue.createApp({
    template: `
    <div class="gzz_astro">
        <h1>E1C_PROSPECCION</h1>
        <grilla-datos etiqueta="Prospeccion" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla E1C_PROSPECCION"
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
                nombre: "ProsCod"
            },
            {
                tipo: "number",
                nombre: "ProsEst"
            },
            {
                tipo: "number",
                nombre: "ProsUbiCod"
            },
            {
                tipo: "select",
                nombre: "ProsEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "e1c_prospeccion",
            nombreCampoCod: "ProsCod",
            nombreCampoEstReg: "ProsEstReg",
            estadoCamposModificar: [["ProsEst", "ProsUbiCod"], ["ProsCod", "ProsEstReg"]],
            estadoCamposEliminar: [[], ["ProsEst", "ProsUbiCod", "ProsCod", "ProsEstReg"]],
            estadoCamposInactivar: [[], ["ProsEst", "ProsUbiCod", "ProsCod", "ProsEstReg"]],
            estadoCamposReactivar: [[], ["ProsEst", "ProsUbiCod", "ProsCod", "ProsEstReg"]]
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
