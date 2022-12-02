app.component("t3c-cargamento", {
    template: `
    <div class="gzz_astro">
        <h1>T3C_CARGAMENTO</h1>
        <grilla-datos etiqueta="Cargamento" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla T3C_CARGAMENTO"
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
                nombre: "CarCod"
            },
            {
                tipo: "number",
                nombre: "CarAlmCod",
                ref: "e3m_almacen"
            },
            {
                tipo: "number",
                nombre: "CarRecCod",
                ref: "t3c_recogida"
            },
            {
                tipo: "string",
                nombre: "CarNom"
            },
            {
                tipo: "number",
                nombre: "CarMinCan"
            },
            {
                tipo: "number",
                nombre: "CarFecAnio"
            },
            {
                tipo: "number",
                nombre: "CarFecMes"
            },
            {
                tipo: "number",
                nombre: "CarFecDia"
            },
            {
                tipo: "select",
                nombre: "CarEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "t3c_cargamento",
            nombreCampoCod: "FacCod",
            nombreCampoEstReg: "FacEstReg",
            estadoCamposModificar: [["CarAlmCod", "CarRecCod", "CarNom", "CarMinCan", "CarFecAnio", "CarFecMes", "CarFecDia"], ["CarCod", "CarEstReg"]],
            estadoCamposEliminar: [[], ["CarAlmCod", "CarRecCod", "CarNom", "CarMinCan", "CarFecAnio", "CarFecMes", "CarFecDia", "CarCod", "CarEstReg"]],
            estadoCamposInactivar: [[], ["CarAlmCod", "CarRecCod", "CarNom", "CarMinCan", "CarFecAnio", "CarFecMes", "CarFecDia", "CarCod", "CarEstReg"]],
            estadoCamposReactivar: [[], ["CarAlmCod", "CarRecCod", "CarNom", "CarMinCan", "CarFecAnio", "CarFecMes", "CarFecDia", "CarCod", "CarEstReg"]]
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
