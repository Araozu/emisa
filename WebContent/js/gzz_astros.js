const app =  Vue.createApp({
    template: `
    <div class="gzz_astro">
        <h1>GZZ_ASTROS</h1>
        <grilla-datos etiqueta="Astros" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla GZZ_ASTROS"
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
                nombre: "AstCod"
            },
            {
                tipo: "text",
                nombre: "AstNom"
            },
            {
                tipo: "select",
                nombre: "AstTip",
                valores: [0, 1]
            },
            {
                tipo: "select",
                nombre: "AstEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "gzz_astro",
            nombreCampoCod: "AstCod",
            nombreCampoEstReg: "AstEstReg",
            estadoCamposModificar: [["AstNom", "AstTip"], ["AstCod", "AstEstReg"]],
            estadoCamposEliminar: [[], ["AstCod", "AstEstReg", "AstNom", "AstTip"]],
            estadoCamposInactivar: [[], ["AstCod", "AstEstReg", "AstNom", "AstTip"]],
            estadoCamposReactivar: [[], ["AstCod", "AstEstReg", "AstNom", "AstTip"]]
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
