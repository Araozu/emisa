const app =  Vue.createApp({
    template: `
    <div class="gzz_astro">
        <h1>RZC_EMPLEO</h1>
        <grilla-datos etiqueta="Mineral" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla RZC_EMPLEO"
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
                nombre: "EmpCod"
            },
            {
                tipo: "text",
                nombre: "EmpNom"
            },
            {
                tipo: "number",
                nombre: "EmpSue"
            },
            {
                tipo: "select",
                nombre: "EmpEst",
                valores: [0, 1]
            },
            {
                tipo: "number",
                nombre: "EmpViaCod",
            },
            {
                tipo: "number",
                nombre: "EmpCatCod"
            },
            {
                tipo: "select",
                nombre: "EmpEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "rzc_empleo",
            nombreCampoCod: "AlimCod",
            nombreCampoEstReg: "AlimEstReg",
            estadoCamposModificar: [["EmpNom", "EmpSue", "EmpEst", "EmpViaCod", "EmpCatCod"], ["EmpCod", "EmpEstReg"]],
            estadoCamposEliminar: [[], ["EmpCod", "EmpNom", "EmpSue", "EmpEst", "EmpViaCod", "EmpCatCod", "EmpEstReg"]],
            estadoCamposInactivar: [[], ["EmpCod", "EmpNom", "EmpSue", "EmpEst", "EmpViaCod", "EmpCatCod", "EmpEstReg"]],
            estadoCamposReactivar: [[], ["EmpCod", "EmpNom", "EmpSue", "EmpEst", "EmpViaCod", "EmpCatCod", "EmpEstReg"]]
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
