const servidor = "http://localhost:8080/Emisa";

const ref = Vue.ref;
const reactive = Vue.reactive;
const computed = Vue.computed;

const app =  Vue.createApp({
    template: `
    <div class="gzz_astro">
        <h1>GZZ_ASTROS</h1>
        <div class="registro">
            <h3>Registro de Astro</h3>
            <form @submit.prevent>
                <div class="estr_form">
                    <label for="AstCod">AstCod</label>
                    <input id="AstCod" type="number" name="AstCod" v-model="astCod"
                           :disabled="inputsDesactivados.astCod || operacionActual === ''">
                    <label for="AstNom">AstNom</label>
                    <input id="AstNom" type="text" name="AstNom" v-model="astNom"
                           :disabled="inputsDesactivados.astNom || operacionActual === ''">
                    <label for="AstTip">AstTip</label>
                    <select name="AstTip" id="AstTip" v-model="astTip"
                            :disabled="inputsDesactivados.astTip || operacionActual === ''"
                    >
                        <option value="0" selected>0</option>
                        <option value="1">1</option>
                    </select>
                    <label for="AstEstReg">AstEstReg</label>
                    <select name="AstEstReg" id="AstEstReg" v-model="astEstReg"
                            :disabled="inputsDesactivados.astEstReg || operacionActual === ''"
                    >
                        <option value="A" selected>A</option>
                        <option value="I">I</option>
                        <option value="*">*</option>
                    </select>
                </div>
            </form>
        </div>
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
        const astCod = ref(undefined);
        const astNom = ref(undefined);
        const astTip = ref("0");
        const astEstReg = ref("A");
        const inputsDesactivados = reactive({
            astCod: false,
            astNom: false,
            astTip: false,
            astEstReg: false
        });
        const filas = ref([]);
        const mensajeError = ref("_");
        const estadoBotones = reactive({
            adicionar: "disponible",
            modificar: "inactivo",
            eliminar: "inactivo",
            inactivar: "inactivo",
            reactivar: "inactivo",
            actualizar: "inactivo"
        });
        const operacionActual = ref("");
        const posFilaSeleccionada = ref(-1);
        const nombresColumnas = ['AstCod', 'AstNom', 'AstTip', 'AstEstReg'];

        const estilosMensajeError =
            computed(() => ({
                opacity: mensajeError.value === "_"? "0": 1
            }));

        const limpiarFormulario = () => {
            astCod.value = undefined;
            astNom.value = undefined;
            astTip.value = undefined;
            astEstReg.value = "A";
        };

        const limpiar = (limpiarFila = false) => {
            estadoBotones.adicionar = "disponible";
            estadoBotones.modificar = "inactivo";
            estadoBotones.eliminar = "inactivo";
            estadoBotones.inactivar = "inactivo";
            estadoBotones.reactivar = "inactivo";
            estadoBotones.actualizar = "inactivo";
            inputsDesactivados.astCod = false;
            inputsDesactivados.astEstReg = false;
            inputsDesactivados.astEstNom = false;
            inputsDesactivados.astTip = false;
            operacionActual.value = "";
            limpiarFormulario();
            if (limpiarFila) {
                posFilaSeleccionada.value = -1;
            }
        };

        const marcarBotones = (activos, inactivos, disponibles = []) => {
            limpiar();
            for (const a of activos) {
                estadoBotones[a] = "activo";
            }
            for (const i of inactivos) {
                estadoBotones[i] = "inactivo";
            }
            for (const d of disponibles) {
                estadoBotones[d] = "disponible";
            }
        };

        const cargarFilaAInputs = () => {
            const fila = filas[posFilaSeleccionada.value];
            astCod.value = fila.AstCod;
            astNom.value = fila.AstNom;
            astTip.value = fila.AstTip;
            astEstReg.value = fila.AstEstReg
        };

        const cambiarEstadoInputs = (activos, inactivos) => {
            for (const a of activos) {
                inputsDesactivados[a] = false;
            }
            for (const i of inactivos) {
                inputsDesactivados[i] = true;
            }
        }

        const iniciarAdicion = () => {
            if (estadoBotones.adicionar !== "disponible") return;
            limpiarFormulario();
            marcarBotones(["adicionar"], ["modificar", "eliminar", "inactivar", "reactivar"], ["actualizar"]);
            operacionActual.value = "adicionar";
        };

        const iniciarModificacion = () => {
            if (posFilaSeleccionada.value === -1 || estadoBotones.modificar !== "disponible") return;
            limpiarFormulario();
            marcarBotones(["modificar"], ["adicionar", "eliminar", "inactivar", "reactivar"], ["actualizar"]);
            operacionActual.value = "modificar";

            cargarFilaAInputs();
            cambiarEstadoInputs(["astNom", "astTip"], ["astCod", "astEstReg"]);
        };

        const iniciarEliminar = () => {
            if (posFilaSeleccionada.value === -1 || estadoBotones.modificar !== "disponible") return;
            limpiarFormulario();
            marcarBotones(["eliminar"], ["adicionar", "modificar", "inactivar", "reactivar"], ["actualizar"]);
            operacionActual.value = "eliminar";

            cargarFilaAInputs();
            astEstReg.value = "*";
            cambiarEstadoInputs([], ["astCod", "astEstReg", "astNom", "astTip"]);
        };

        const iniciarInactivar = () => {
            if (posFilaSeleccionada.value === -1 || estadoBotones.modificar !== "disponible") return;
            limpiarFormulario();
            marcarBotones(["inactivar"], ["adicionar", "modificar", "eliminar", "reactivar"], ["actualizar"]);
            operacionActual.value = "inactivar";

            cargarFilaAInputs();
            astEstReg.value = "I";
            cambiarEstadoInputs([], ["astCod", "astEstReg", "astNom", "astTip"]);
        };

        const iniciarReactivar = () => {
            if (posFilaSeleccionada.value === -1 || estadoBotones.modificar !== "disponible") return;
            limpiarFormulario();
            marcarBotones(["reactivar"], ["adicionar", "modificar", "eliminar", "inactivar"], ["actualizar"]);
            operacionActual.value = "reactivar";

            cargarFilaAInputs();
            astEstReg.value = "A";
            cambiarEstadoInputs([], ["astCod", "astEstReg", "astNom", "astTip"]);
        };

        const mostrarMensaje = (msg, ms = 2500) => {
            mensajeError.value = msg;
            setTimeout(() => {
                mensajeError.value = "_";
            }, ms);
        };

        const adicionar = async () => {
            const AstCod = parseInt(astCod.value);
            const AstNom = astNom.value.toString();
            const AstTip = parseInt(astTip.value) === 1? 1: 0;
            const AstEstReg = astEstReg.value.toString();

            const datos = { AstCod, AstNom, AstTip, AstEstReg };
            const body = [];
            for (const datosKey in datos) {
                body.push(`${encodeURIComponent(datosKey)}=${encodeURIComponent(datos[datosKey])}`);
            }

            const url = `${servidor}/api/gzz_astro/`;
            try {
                const peticion = await fetch(url, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: body.join("&")
                });

                if (peticion.ok) {

                    const resultado = await peticion.json();
                    if (resultado.count > 0) {
                        mensajeError.value = "_";
                        filas.push(datos);
                    } else {
                        mostrarMensaje("No se insertó ningún dato.", 5000);
                    }

                    limpiarFormulario();
                    limpiar();
                } else {
                    console.error(peticion);
                    mensajeError.value = "Error al adicionar los datos a la tabla GZZ_ASTROS";
                }

            } catch (e) {
                console.error(e);
                mensajeError.value = "Error al adicionar los datos a la tabla GZZ_ASTROS";
            }

        };

        const modificar = async () => {
            const AstCod = astCod.value;
            const AstNom = astNom.value.toString();
            const AstTip = astTip.value;

            const datos = { operacion: "Modificar", AstCod, AstNom, AstTip };
            const body = [];
            for (const datosKey in datos) {
                body.push(`${encodeURIComponent(datosKey)}=${encodeURIComponent(datos[datosKey])}`);
            }

            const url = `${servidor}/api/gzz_astro/?${body.join("&")}`;
            try {
                const peticion = await fetch(url, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "Access-Control-Request-Method": "PUT",
                        "Access-Control-Request-Headers": "Content-Type"
                    }
                });

                if (peticion.ok) {
                    const resultado = await peticion.json();
                    if (resultado.count > 0) {
                        mensajeError.value = "_";
                        const nuevaFila = {
                            AstCod,
                            AstNom,
                            AstTip,
                            AstEstReg: astEstReg.value
                        };
                        const posFila = posFilaSeleccionada.value;
                        filas.splice(posFila, 1, nuevaFila);
                    } else {
                        mostrarMensaje("No se modificó la fila.", 5000);
                    }

                    limpiarFormulario();
                    limpiar(true);
                } else {
                    console.error(peticion);
                    mensajeError.value = "Error al modificar la fila de la tabla GZZ_ASTROS";
                }

            } catch (e) {
                console.error(e);
                mensajeError.value = "Error al modificar la fila de la tabla GZZ_ASTROS";
            }

        };

        const eliminar= async () => {
            const AstCod = astCod.value;
            const AstNom = astNom.value.toString();
            const AstTip = astTip.value;
            const url = `${servidor}/api/gzz_astro/?AstCod=${decodeURIComponent(AstCod)}`;
            try {
                const peticion = await fetch(url, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "Access-Control-Request-Method": "PUT",
                        "Access-Control-Request-Headers": "Content-Type"
                    }
                });

                if (peticion.ok) {
                    const resultado = await peticion.json();
                    if (resultado.count > 0) {
                        mensajeError.value = "_";
                        const nuevaFila = {
                            AstCod,
                            AstNom,
                            AstTip,
                            AstEstReg: "*"
                        };
                        const posFila = posFilaSeleccionada.value;
                        filas.splice(posFila, 1, nuevaFila);
                    } else {
                        mostrarMensaje("No se eliminó la fila.", 5000);
                    }

                    limpiarFormulario();
                    limpiar(true);
                } else {
                    console.error(peticion);
                    mensajeError.value = "Error al eliminar la fila de la tabla GZZ_ASTROS";
                }

            } catch (e) {
                console.error(e);
                mensajeError.value = "Error al eliminar la fila de la tabla GZZ_ASTROS";
            }
        };

        const in_re_activar = async (esIn) => {
            const AstCod = astCod.value;
            const AstNom = astNom.value.toString();
            const AstTip = astTip.value;
            const AstEstReg = astEstReg.value;

            const datos = { operacion: esIn? "Inactivar": "Reactivar", AstCod };
            const body = [];
            for (const datosKey in datos) {
                body.push(`${encodeURIComponent(datosKey)}=${encodeURIComponent(datos[datosKey])}`);
            }

            const url = `${servidor}/api/gzz_astro/?${body.join("&")}`;
            try {
                const peticion = await fetch(url, {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                        "Access-Control-Request-Method": "PUT",
                        "Access-Control-Request-Headers": "Content-Type"
                    }
                });

                if (peticion.ok) {
                    const resultado = await peticion.json();
                    if (resultado.count > 0) {
                        mensajeError.value = "_";
                        const nuevaFila = {
                            AstCod,
                            AstNom,
                            AstTip,
                            AstEstReg
                        };
                        const posFila = posFilaSeleccionada.value;
                        filas.splice(posFila, 1, nuevaFila);
                    } else {
                        mostrarMensaje("No se modificó la fila.", 5000);
                    }

                    limpiarFormulario();
                    limpiar(true);
                } else {
                    console.error(peticion);
                    mensajeError.value = "Error al modificar la fila de la tabla GZZ_ASTROS";
                }

            } catch (e) {
                console.error(e);
                mensajeError.value = "Error al modificar la fila de la tabla GZZ_ASTROS";
            }
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
            astCod,
            astNom,
            astTip,
            astEstReg,
            inputsDesactivados,
            filas,
            mensajeError,
            estadoBotones,
            operacionActual,
            posFilaSeleccionada,
            estilosMensajeError,
            nombresColumnas,
            limpiarFormulario,
            limpiar,
            marcarBotones,
            iniciarAdicion,
            iniciarModificacion,
            iniciarEliminar,
            iniciarInactivar,
            iniciarReactivar,
            seleccionarFila,
            cerrarVentana,
            actualizar
        }
    }
});
