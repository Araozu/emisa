app.component("r7c-alimento", {
    template: `
    <div class="gzz_astro">
        <h1>R7C_ALIMENTO</h1>
        <grilla-datos etiqueta="Alimento" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla R7C_ALIMENTO"
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
                nombre: "AlimCod"
            },
            {
                tipo: "text",
                nombre: "AlimNom"
            },
            {
                tipo: "number",
                nombre: "AlimCos"
            },
            {
                tipo: "number",
                nombre: "AlimCan"
            },
            {
                tipo: "select",
                nombre: "AlimEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "r7c_alimento",
            nombreCampoCod: "AlimCod",
            nombreCampoEstReg: "AlimEstReg",
            estadoCamposModificar: [["AlimNom", "AlimCos", "AlimCan"], ["AlimCod", "AlimEstReg"]],
            estadoCamposEliminar: [[], ["AlimCod", "AlimNom", "AlimCos", "AlimCan", "AlimEstReg"]],
            estadoCamposInactivar: [[], ["AlimCod", "AlimNom", "AlimCos", "AlimCan", "AlimEstReg"]],
            estadoCamposReactivar: [[], ["AlimCod", "AlimNom", "AlimCos", "AlimCan", "AlimEstReg"]]
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
