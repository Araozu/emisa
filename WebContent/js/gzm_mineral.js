const servidor = "http://localhost:8080/Emisa";

const ref = Vue.ref;
const reactive = Vue.reactive;
const computed = Vue.computed;

const app =  Vue.createApp({
    template: `
    <div class="gzz_astro">
        <h1>GZM_MINERAL</h1>
        <grilla-datos etiqueta="Mineral" :campos="campos" :camposDesactivados="camposDesactivados"
            :operacionActual="operacionActual"
            :valores="valores"
            :funActualizarValor="funActualizarValor"/>
        <br>
        <tabla-datos
            nombre="Tabla GZM_MINERAL"
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
                nombre: "MinCod"
            },
            {
                tipo: "text",
                nombre: "MinNom"
            },
            {
                tipo: "select",
                nombre: "MinEstReg",
                valores: ["A", "I", "*"]
            }
        ];
        const estados = {
            nombreCampoEstReg: "MinEstReg",
            estadoCamposModificar: [["astNom", "astTip"], ["astCod", "astEstReg"]],
            estadoCamposEliminar: [[], ["astCod", "astEstReg", "astNom", "astTip"]],
            estadoCamposInactivar: [[], ["astCod", "astEstReg", "astNom", "astTip"]],
            estadoCamposReactivar: [[], ["astCod", "astEstReg", "astNom", "astTip"]]
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
            generarBody,
            realizarOperacion,
            cargarFilas,
            enExitoModificarFila,
            adicionar
        } = usarCamposAdaptados("gzm_mineral", campos, estados, mensajeError, mostrarMensaje);

        const modificar = async () => {
            const AstCod = MinCod.value;
            const AstNom = MinNom.value.toString();
            const AstTip = astTip.value;

            const body = generarBody({ operacion: "Modificar", AstCod, AstNom, AstTip });

            const enError = (e) => {
                console.error(e);
                mensajeError.value = "Error al modificar la fila de la tabla GZZ_ASTROS";
            }

            realizarOperacion(body, "PUT", "gzz_astro", enExitoModificarFila, enError);
        };

        const eliminar = async () => {
            const AstCod = MinCod.value;
            const body = `AstCod=${decodeURIComponent(AstCod)}`;

            const enError = (e) => {
                console.error(e);
                mensajeError.value = "Error al eliminar la fila de la tabla GZZ_ASTROS";
            }

            realizarOperacion(body, "DELETE", "gzz_astro", enExitoModificarFila, enError);
        };

        const in_re_activar = async (esIn) => {
            const AstCod = MinCod.value;

            const body = generarBody({ operacion: esIn? "Inactivar": "Reactivar", AstCod });

            const enError = (e) => {
                console.error(e);
                mensajeError.value = "Error al modificar la fila de la tabla GZZ_ASTROS";
            };
            realizarOperacion(body, "PUT", "gzz_astro", enExitoModificarFila, enError);
        };

        const actualizar = async () => {
            switch (operacionActual.value) {
                case "adicionar": {
                    adicionar();
                    break;
                }
                case "modificar": {
                    modificar();
                    break;
                }
                case "eliminar": {
                    eliminar();
                    break;
                }
                case "inactivar": {
                    in_re_activar(true);
                    break;
                }
                case "reactivar": {
                    in_re_activar(false);
                    break;
                }
            }
        };

        const cerrarVentana = () => {
            window.location.assign("./");
        };

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
