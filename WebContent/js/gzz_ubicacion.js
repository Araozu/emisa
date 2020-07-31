const app = Vue.createApp({
    template: `
    <div class="gzz_astro">
        <h1>GZZ_UBICACION</h1>
        <grilla-datos etiqueta="Ubicacion" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla GZZ_UBICACION"
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
                nombre: "UbiCod"
            },
            {
                tipo: "text",
                nombre: "UbiNom"
            },
            {
                tipo: "number",
                nombre: "UbiAstCod"
            },
            {
                tipo: "number",
                nombre: "UbiCoorX"
            },
            {
                tipo: "number",
                nombre: "UbiCoorY"
            },
            {
                tipo: "number",
                nombre: "UbiCoorZ"
            },
            {
                tipo: "select",
                nombre: "UbiEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "gzz_ubicacion",
            nombreCampoCod: "UbiCod",
            nombreCampoEstReg: "UbiEstReg",
            estadoCamposModificar: [["UbiNom", "UbiAstCod", "UbiCoorX", "UbiCoorY", "UbiCoorZ"], ["UbiCod", "UbiEstReg"]],
            estadoCamposEliminar: [[], ["UbiNom", "UbiAstCod", "UbiCoorX", "UbiCoorY", "UbiCoorZ", "UbiCod", "UbiEstReg"]],
            estadoCamposInactivar: [[], ["UbiNom", "UbiAstCod", "UbiCoorX", "UbiCoorY", "UbiCoorZ", "UbiCod", "UbiEstReg"]],
            estadoCamposReactivar: [[], ["UbiNom", "UbiAstCod", "UbiCoorX", "UbiCoorY", "UbiCoorZ", "UbiCod", "UbiEstReg"]]
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
