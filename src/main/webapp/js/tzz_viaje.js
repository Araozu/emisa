app.component("tzz-viaje", {
    template: `
    <div class="gzz_astro">
        <h1>TZZ_VIAJE</h1>
        <grilla-datos etiqueta="Viaje" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla TZZ_VIAJE"
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
                nombre: "ViaCod"
            },
            {
                tipo: "number",
                nombre: "ViaNavCod",
                ref: "tzm_nave"
            },
            {
                tipo: "number",
                nombre: "ViaFacCod",
                ref: "ezm_factoria"
            },
            {
                tipo: "number",
                nombre: "FecDesAnio"
            },
            {
                tipo: "number",
                nombre: "FecDesMes"
            },
            {
                tipo: "number",
                nombre: "FecDesDia"
            },
            {
                tipo: "number",
                nombre: "FecLleAnio"
            },
            {
                tipo: "number",
                nombre: "FecLleMes"
            },
            {
                tipo: "number",
                nombre: "FecLleDia"
            },
            {
                tipo: "select",
                nombre: "ViaEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "tzz_viaje",
            nombreCampoCod: "ViaCod",
            nombreCampoEstReg: "ViaEstReg",
            estadoCamposModificar: [["ViaNavCod", "ViaFacCod", "FecDesAnio", "FecDesMes", "FecDesDia", "FecLleAnio", "FecLleMes", "FecLleDia"], ["ViaCod", "ViaEstReg"]],
            estadoCamposEliminar: [[], ["ViaNavCod", "ViaFacCod", "FecDesAnio", "FecDesMes", "FecDesDia", "FecLleAnio", "FecLleMes", "FecLleDia", "ViaCod", "ViaEstReg"]],
            estadoCamposInactivar: [[], ["ViaNavCod", "ViaFacCod", "FecDesAnio", "FecDesMes", "FecDesDia", "FecLleAnio", "FecLleMes", "FecLleDia", "ViaCod", "ViaEstReg"]],
            estadoCamposReactivar: [[], ["ViaNavCod", "ViaFacCod", "FecDesAnio", "FecDesMes", "FecDesDia", "FecLleAnio", "FecLleMes", "FecLleDia", "ViaCod", "ViaEstReg"]]
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
