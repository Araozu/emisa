app.component("t3c-recogida", {
    template: `
    <div class="gzz_astro">
        <h1>T3C_RECOGIDA</h1>
        <grilla-datos etiqueta="Recogida" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla T3C_RECOGIDA"
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
                nombre: "RecCod"
            },
            {
                tipo: "number",
                nombre: "RecViaCod",
                ref: "tzz_viaje"
            },
            {
                tipo: "number",
                nombre: "RecFecAño"
            },
            {
                tipo: "number",
                nombre: "RecFecMes"
            },
            {
                tipo: "number",
                nombre: "RecFecDia"
            },
            {
                tipo: "select",
                nombre: "RecEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "t3c_recogida",
            nombreCampoCod: "RecCod",
            nombreCampoEstReg: "RecEstReg",
            estadoCamposModificar: [["RecViaCod", "RecFecAño", "RecFecMes", "RecFecDia"], ["RecCod", "RecEstReg"]],
            estadoCamposEliminar: [[], ["RecViaCod", "RecFecAño", "RecFecMes", "RecFecDia", "RecCod", "RecEstReg"]],
            estadoCamposInactivar: [[], ["RecViaCod", "RecFecAño", "RecFecMes", "RecFecDia", "RecCod", "RecEstReg"]],
            estadoCamposReactivar: [[], ["RecViaCod", "RecFecAño", "RecFecMes", "RecFecDia", "RecCod", "RecEstReg"]]
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
