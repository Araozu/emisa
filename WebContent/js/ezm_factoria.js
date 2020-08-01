
const app = Vue.createApp({
    template: `
    <div class="gzz_astro">
        <h1>EZM_FACTORIA</h1>
        <grilla-datos etiqueta="Factoria" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla EZM_FACTORIA"
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
                nombre: "FacCod"
            },
            {
                tipo: "text",
                nombre: "FacNom"
            },
            {
                tipo: "number",
                nombre: "FacUbiCod",
                ref: "gzz_ubicacion"
            },
            {
                tipo: "select",
                nombre: "FacEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            recurso: "ezm_factoria",
            nombreCampoCod: "FacCod",
            nombreCampoEstReg: "FacEstReg",
            estadoCamposModificar: [["FacNom", "FacUbiCod"], ["FacCod", "FacEstReg"]],
            estadoCamposEliminar: [[], ["FacNom", "FacUbiCod", "FacCod", "FacEstReg"]],
            estadoCamposInactivar: [[], ["FacNom", "FacUbiCod", "FacCod", "FacEstReg"]],
            estadoCamposReactivar: [[], ["FacNom", "FacUbiCod", "FacCod", "FacEstReg"]]
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
