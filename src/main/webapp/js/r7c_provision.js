app.component("r7c-provision", {
    template: `
    <div class="gzz_astro">
        <h1>R7C_PROVISION</h1>
        <grilla-datos etiqueta="Provision" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla R7C_PROVISION"
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
                nombre: "ProvCod"
            },
            {
                tipo: "number",
                nombre: "ProvPes"
            },
            {
                tipo: "number",
                nombre: "ProvAliCan"
            },
            {
                tipo: "number",
                nombre: "ProvAlimCod",
                ref: "r7c_alimento"
            },
            {
                tipo: "number",
                nombre: "ProvSumCod",
                ref: "t5c_suministro"
            },
            {
                tipo: "number",
                nombre: "ProvFecLleAnio"
            },
            {
                tipo: "number",
                nombre: "ProvFecLleMes"
            },
            {
                tipo: "number",
                nombre: "ProvFecLleDia"
            },
            {
                tipo: "select",
                nombre: "ProvEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "r7c_provision",
            nombreCampoCod: "ProvCod",
            nombreCampoEstReg: "ProvEstReg",
            estadoCamposModificar: [["ProvPes", "ProvAliCan", "ProvAlimCod", "ProvSumCod", "ProvFecLleAnio", "ProvFecLleMes", "ProvFecLleDia"], ["ProvCod", "ProvEstReg"]],
            estadoCamposEliminar: [[], ["ProvPes", "ProvAliCan", "ProvAlimCod", "ProvSumCod", "ProvFecLleAnio", "ProvFecLleMes", "ProvFecLleDia", "ProvCod", "ProvEstReg"]],
            estadoCamposInactivar: [[], ["ProvPes", "ProvAliCan", "ProvAlimCod", "ProvSumCod", "ProvFecLleAnio", "ProvFecLleMes", "ProvFecLleDia", "ProvCod", "ProvEstReg"]],
            estadoCamposReactivar: [[], ["ProvPes", "ProvAliCan", "ProvAlimCod", "ProvSumCod", "ProvFecLleAnio", "ProvFecLleMes", "ProvFecLleDia", "ProvCod", "ProvEstReg"]]
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
