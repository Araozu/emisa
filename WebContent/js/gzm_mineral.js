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
        const MinCod = ref(undefined);
        const MinNom = ref(undefined);
        const astTip = ref("0");
        const MinEstReg = ref("A");

        const {mensajeError, estilosMensajeError, mostrarMensaje} = usarMensajesError();
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
        const {
            filas,
            valores,
            funActualizarValor,
            camposDesactivados,
            cambiarEstadoCampos,
            limpiarCampos,
            limpiar,
            marcarBotones,
            cargarFilaAInputs,
            nombresColumnas,
            operacionActual,
            posFilaSeleccionada,
            estadoBotones
        } = usarCamposAdaptados(campos);

        // ==============================================
        //   Operaciones especificas
        // ==============================================

        const iniciarAdicion = () => {
            if (estadoBotones.adicionar !== "disponible") return;
            limpiarCampos();
            marcarBotones(["adicionar"], ["modificar", "eliminar", "inactivar", "reactivar"], ["actualizar"]);
            operacionActual.value = "adicionar";
        };

        const iniciarModificacion = () => {
            if (posFilaSeleccionada.value === -1 || estadoBotones.modificar !== "disponible") return;
            limpiarCampos();
            marcarBotones(["modificar"], ["adicionar", "eliminar", "inactivar", "reactivar"], ["actualizar"]);
            operacionActual.value = "modificar";

            cargarFilaAInputs();
            cambiarEstadoCampos(["astNom", "astTip"], ["astCod", "astEstReg"]);
        };

        const iniciarEliminar = () => {
            if (posFilaSeleccionada.value === -1 || estadoBotones.modificar !== "disponible") return;
            limpiarCampos();
            marcarBotones(["eliminar"], ["adicionar", "modificar", "inactivar", "reactivar"], ["actualizar"]);
            operacionActual.value = "eliminar";

            cargarFilaAInputs();
            MinEstReg.value = "*";
            cambiarEstadoCampos([], ["astCod", "astEstReg", "astNom", "astTip"]);
        };

        const iniciarInactivar = () => {
            if (posFilaSeleccionada.value === -1 || estadoBotones.modificar !== "disponible") return;
            limpiarCampos();
            marcarBotones(["inactivar"], ["adicionar", "modificar", "eliminar", "reactivar"], ["actualizar"]);
            operacionActual.value = "inactivar";

            cargarFilaAInputs();
            MinEstReg.value = "I";
            cambiarEstadoCampos([], ["astCod", "astEstReg", "astNom", "astTip"]);
        };

        const iniciarReactivar = () => {
            if (posFilaSeleccionada.value === -1 || estadoBotones.modificar !== "disponible") return;
            limpiarCampos();
            marcarBotones(["reactivar"], ["adicionar", "modificar", "eliminar", "inactivar"], ["actualizar"]);
            operacionActual.value = "reactivar";

            cargarFilaAInputs();
            MinEstReg.value = "A";
            cambiarEstadoCampos([], ["astCod", "astEstReg", "astNom", "astTip"]);
        };

        const generarBody = (datos) => {
            const body = [];
            for (const datosKey in datos) if (datos.hasOwnProperty(datosKey)) {
                body.push(`${encodeURIComponent(datosKey)}=${encodeURIComponent(datos[datosKey])}`);
            }
            return body.join("&");
        }

        const realizarOperacion = async (body, metodo, recurso, enExito, enError) => {
            const bodyEnUrl = metodo === "GET" || metodo === "PUT" || metodo === "DELETE";
            const url = `${servidor}/api/${recurso}/${bodyEnUrl? '?' + body: ''}`;
            try {
                const datos = {
                    method: metodo,
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                }
                if (!bodyEnUrl) datos.body = body;
                const peticion = await fetch(url, datos);
                if (peticion.ok) {
                    const resultado = await peticion.json();
                    enExito(resultado);
                } else {
                    enError(peticion);
                }
            } catch (e) {
                enError(e);
            }
        };

        const enExitoModificarFila = (resultado) => {
            if (resultado.count > 0) {
                mensajeError.value = "_";
                const nuevaFila = {
                    AstCod: MinCod.value,
                    AstNom: MinNom.value.toString(),
                    AstTip: astTip.value,
                    AstEstReg: MinEstReg.value
                };
                const posFila = posFilaSeleccionada.value;
                filas.value.splice(posFila, 1, nuevaFila);
            } else {
                mostrarMensaje("No se modificó la fila.", 5000);
            }

            limpiarCampos();
            limpiar(true);
        };

        const adicionar = async () => {
            const AstCod = parseInt(MinCod.value);
            const AstNom = MinNom.value.toString();
            const AstTip = parseInt(astTip.value) === 1? 1: 0;
            const AstEstReg = MinEstReg.value.toString();

            const datos = { AstCod, AstNom, AstTip, AstEstReg };
            const body = generarBody(datos);

            const enExito = (resultado) => {
                if (resultado.count > 0) {
                    mensajeError.value = "_";
                    filas.value.push(datos);
                } else {
                    mostrarMensaje("No se insertó ningún dato.", 5000);
                }
                limpiarCampos();
                limpiar();
            };

            const enError = (e) => {
                console.error(e);
                mensajeError.value = "Error al adicionar los datos a la tabla GZZ_ASTROS";
            };

            realizarOperacion(body, "POST", "gzz_astro", enExito, enError);
        };

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

        const cargarFilas = async () => {
            const url = `${servidor}/api/gzz_astro/`;
            try {
                const peticion = await fetch(url);
                if (peticion.ok) {
                    filas.value = await peticion.json();
                    mensajeError.value = "_";
                } else {
                    console.error(peticion);
                    mensajeError.value = "Ocurrió un error al recuperar los datos del servidor.";
                }
            } catch (e) {
                console.error(e);
                mensajeError.value = "Ocurrió un error al recuperar los datos del servidor.";
            }
        };

        const seleccionarFila = (posFila) => {
            if (operacionActual.value !== "") return;
            const botonesDisponibles = ["modificar", "eliminar", "actualizar", "reactivar", "inactivar"];
            marcarBotones([], ["adicionar"], botonesDisponibles);
            posFilaSeleccionada.value = posFila;
        };

        setTimeout(cargarFilas, 0);

        return {
            MinCod,
            MinNom,
            MinEstReg,
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
