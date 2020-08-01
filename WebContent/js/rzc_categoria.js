const app =  Vue.createApp({
    template: `
    <div class="gzz_astro">
        <h1>RZC_CATEGORIA</h1>
        <grilla-datos etiqueta="Categoria" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla RZC_CATEGORIA"
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
                nombre: "CatCod"
            },
            {
                tipo: "text",
                nombre: "CatNom"
            },
            {
                tipo: "number",
                nombre: "CatSuel"
            },
            {
                tipo: "select",
                nombre: "CatEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "rzc_categoria",
            nombreCampoCod: "CatCod",
            nombreCampoEstReg: "CatEstReg",
            estadoCamposModificar: [["CatNom", "CatSuel"], ["CatCod", "CatEstReg"]],
            estadoCamposEliminar: [[], ["CatNom", "CatSuel", "CatCod", "CatEstReg"]],
            estadoCamposInactivar: [[], ["CatNom", "CatSuel", "CatCod", "CatEstReg"]],
            estadoCamposReactivar: [[], ["CatNom", "CatSuel", "CatCod", "CatEstReg"]]
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
