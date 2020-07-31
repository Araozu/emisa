const app = Vue.createApp({
    template: `
    <div class="gzz_astro">
        <h1>R6Z_PERSONAL</h1>
        <grilla-datos etiqueta="Personal" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla R6Z_PERSONAL"
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
                nombre: "PerCod"
            },
            {
                tipo: "text",
                nombre: "PerNom"
            },
            {
                tipo: "number",
                nombre: "PerEmpCod"
            },
            {
                tipo: "select",
                nombre: "PerEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "r6z_personal",
            nombreCampoCod: "PerCod",
            nombreCampoEstReg: "PerEstReg",
            estadoCamposModificar: [["PerNom", "PerEmpCod"], ["PerCod", "PerEstReg"]],
            estadoCamposEliminar: [[], ["PerNom", "PerEmpCod", "PerCod", "PerEstReg"]],
            estadoCamposInactivar: [[], ["PerNom", "PerEmpCod", "PerCod", "PerEstReg"]],
            estadoCamposReactivar: [[], ["PerNom", "PerEmpCod", "PerCod", "PerEstReg"]]
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
