app.component("e1c-produccion", {
    template: `
    <div class="gzz_astro">
        <h1>E1C_PRODUCCION</h1>
        <grilla-datos etiqueta="Produccion" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla E1C_PRODUCCION"
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
                nombre: "ProCod"
            },
            {
                tipo: "number",
                nombre: "ProMinCod",
                ref: "gzm_mineral"
            },
            {
                tipo: "number",
                nombre: "ProMinCan"
            },
            {
                tipo: "number",
                nombre: "ProMinCal"
            },
            {
                tipo: "number",
                nombre: "ProFecAnio"
            },
            {
                tipo: "number",
                nombre: "ProFecMes"
            },
            {
                tipo: "number",
                nombre: "ProFecDia"
            },
            {
                tipo: "select",
                nombre: "ProEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "e1c_produccion",
            nombreCampoCod: "ProCod",
            nombreCampoEstReg: "ProEstReg",
            estadoCamposModificar: [["ProMinCan", "ProMinCod", "ProMinCal", "ProFecAnio", "ProFecMes", "ProFecDia"], ["ProCod", "ProEstReg"]],
            estadoCamposEliminar: [[], ["ProMinCan", "ProMinCod", "ProMinCal", "ProFecAnio", "ProFecMes", "ProFecDia", "ProCod", "ProEstReg"]],
            estadoCamposInactivar: [[], ["ProMinCan", "ProMinCod", "ProMinCal", "ProFecAnio", "ProFecMes", "ProFecDia", "ProCod", "ProEstReg"]],
            estadoCamposReactivar: [[], ["ProMinCan", "ProMinCod", "ProMinCal", "ProFecAnio", "ProFecMes", "ProFecDia", "ProCod", "ProEstReg"]]
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
