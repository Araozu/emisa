app.component("p8m-centro", {
    template: `
    <div class="gzz_astro">
        <h1>P8M_CENTRO</h1>
        <grilla-datos etiqueta="Centro" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla P8M_CENTRO"
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
                nombre: "CenCod"
            },
            {
                tipo: "text",
                nombre: "CenNom"
            },
            {
                tipo: "number",
                nombre: "CenMinCod",
                ref: "gzm_mineral"
            },
            {
                tipo: "select",
                nombre: "CenEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "p8m_centro",
            nombreCampoCod: "CenCod",
            nombreCampoEstReg: "CenEstReg",
            estadoCamposModificar: [["CenNom", "CenMinCod"], ["CenCod", "CenEstReg"]],
            estadoCamposEliminar: [[], ["CenCod", "CenNom", "CenMinCod", "CenEstReg"]],
            estadoCamposInactivar: [[], ["CenCod", "CenNom", "CenMinCod", "CenEstReg"]],
            estadoCamposReactivar: [[], ["CenCod", "CenNom", "CenMinCod", "CenEstReg"]]
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
