const app = Vue.createApp({
    template: `
    <div class="gzz_astro">
        <h1>E3M_ALMACEN</h1>
        <grilla-datos etiqueta="Almacen" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla E3M_ALMACEN"
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
                nombre: "AlmCod"
            },
            {
                tipo: "number",
                nombre: "AlmFacCod"
            },
            {
                tipo: "number",
                nombre: "AlmMinCod"
            },
            {
                tipo: "number",
                nombre: "AlmCap"
            },
            {
                tipo: "select",
                nombre: "AlmEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "e3m_almacen",
            nombreCampoCod: "AlmCod",
            nombreCampoEstReg: "AlmEstReg",
            estadoCamposModificar: [["AlmFacCod", "AlmMinCod", "AlmCap"], ["AlmCod", "AlmEstReg"]],
            estadoCamposEliminar: [[], ["AlmFacCod", "AlmMinCod", "AlmCap", "AlmCod", "AlmEstReg"]],
            estadoCamposInactivar: [[], ["AlmFacCod", "AlmMinCod", "AlmCap", "AlmCod", "AlmEstReg"]],
            estadoCamposReactivar: [[], ["AlmFacCod", "AlmMinCod", "AlmCap", "AlmCod", "AlmEstReg"]]
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
