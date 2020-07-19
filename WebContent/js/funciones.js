
const usarMensajesError = () => {
    const mensajeError = Vue.ref("_");
    const estilosMensajeError = Vue.computed(() => ({
        opacity: mensajeError.value === "_"? "0": 1
    }));
    const mostrarMensaje = (msg, ms = 2500) => {
        mensajeError.value = msg;
        setTimeout(() => {
            mensajeError.value = "_";
        }, ms);
    };

    return {
        mensajeError,
        estilosMensajeError,
        mostrarMensaje
    };
};

const usarCamposAdaptados = (campos, estados, mensajeError, mostrarMensaje) => {

    // ==============================================
    //   Declaracion de estados
    // ==============================================

    const {
        recurso,
        nombreCampoCod,
        nombreCampoEstReg,
        estadoCamposModificar,
        estadoCamposEliminar,
        estadoCamposInactivar,
        estadoCamposReactivar
    } = estados;

    // ==============================================
    //   Valores reactivos
    // ==============================================

    const filas = Vue.ref([]);
    const operacionActual = Vue.ref("");
    const posFilaSeleccionada = Vue.ref(-1);
    const estadoBotones = Vue.reactive({
        adicionar: "disponible",
        modificar: "inactivo",
        eliminar: "inactivo",
        inactivar: "inactivo",
        reactivar: "inactivo",
        actualizar: "inactivo"
    });

    const camposObj = {};
    const camposDesactivadosRaw = {};
    const nombresColumnas = [];
    for (const campo of campos) {
        camposObj[campo.nombre] = undefined;
        camposDesactivadosRaw[campo.nombre] = true;
        nombresColumnas.push(campo.nombre);
    }

    const valores = Vue.reactive(camposObj);
    const camposDesactivados = Vue.reactive(camposDesactivadosRaw)



    // ==============================================
    //   Funciones
    // ==============================================

    const funActualizarValor = (nombre, valor) => {
        valores[nombre] = valor
    };

    const cambiarEstadoCampos = (activos, inactivos) => {
        for (const a of activos) {
            camposDesactivados[a] = false;
        }
        for (const i of inactivos) {
            camposDesactivados[i] = true;
        }
    }

    const limpiarCampos = () => {
        for (const k in camposObj) {
            valores[k] = undefined;
        }
    };

    const limpiar = (limpiarFila = false) => {
        estadoBotones.adicionar = "disponible";
        estadoBotones.modificar = "inactivo";
        estadoBotones.eliminar = "inactivo";
        estadoBotones.inactivar = "inactivo";
        estadoBotones.reactivar = "inactivo";
        estadoBotones.actualizar = "inactivo";
        for (const k in camposDesactivados) {
            if (camposDesactivados.hasOwnProperty(k))
                camposDesactivados[k] = false;
        }
        operacionActual.value = "";
        limpiarCampos();
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
        const fila = filas.value[posFilaSeleccionada.value];
        for (const k in fila) {
            if (fila.hasOwnProperty(k))
                valores[k] = fila[k];
        }
    };

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
        cambiarEstadoCampos(...estadoCamposModificar);
    };

    const iniciarEliminar = () => {
        if (posFilaSeleccionada.value === -1 || estadoBotones.modificar !== "disponible") return;
        limpiarCampos();
        marcarBotones(["eliminar"], ["adicionar", "modificar", "inactivar", "reactivar"], ["actualizar"]);
        operacionActual.value = "eliminar";

        cargarFilaAInputs();
        funActualizarValor(nombreCampoEstReg, "*");
        cambiarEstadoCampos(...estadoCamposEliminar);
    };

    const iniciarInactivar = () => {
        if (posFilaSeleccionada.value === -1 || estadoBotones.modificar !== "disponible") return;
        limpiarCampos();
        marcarBotones(["inactivar"], ["adicionar", "modificar", "eliminar", "reactivar"], ["actualizar"]);
        operacionActual.value = "inactivar";

        cargarFilaAInputs();
        funActualizarValor(nombreCampoEstReg, "I");
        cambiarEstadoCampos(...estadoCamposInactivar);
    };

    const iniciarReactivar = () => {
        if (posFilaSeleccionada.value === -1 || estadoBotones.modificar !== "disponible") return;
        limpiarCampos();
        marcarBotones(["reactivar"], ["adicionar", "modificar", "eliminar", "inactivar"], ["actualizar"]);
        operacionActual.value = "reactivar";

        cargarFilaAInputs();
        funActualizarValor(nombreCampoEstReg, "A");
        cambiarEstadoCampos(...estadoCamposReactivar);
    };

    const seleccionarFila = (posFila) => {
        if (operacionActual.value !== "") return;
        const botonesDisponibles = ["modificar", "eliminar", "actualizar", "reactivar", "inactivar"];
        marcarBotones([], ["adicionar"], botonesDisponibles);
        posFilaSeleccionada.value = posFila;
    };

    const generarBody = (datos) => {
        const body = [];
        for (const datosKey in datos) {
            if (datos.hasOwnProperty(datosKey))
            body.push(`${encodeURIComponent(datosKey)}=${encodeURIComponent(datos[datosKey])}`);
        }
        return body.join("&");
    }

    const realizarOperacion = async (body, metodo, enExito, enError) => {
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

    const cargarFilas = async () => {
        const url = `${servidor}/api/${recurso}/`;
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

    const enExitoModificarFila = (resultado) => {
        if (resultado.count > 0) {
            mensajeError.value = "_";
            const nuevaFila = {};
            for (const k in valores) {
                if (valores.hasOwnProperty(k))
                    nuevaFila[k] = valores[k];
            }

            const posFila = posFilaSeleccionada.value;
            filas.value.splice(posFila, 1, nuevaFila);
        } else {
            mostrarMensaje("No se modificó la fila.", 5000);
        }

        limpiarCampos();
        limpiar(true);
    };

    const adicionar = async () => {
        const datos = Object.assign({}, valores)
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
        const datos = Object.assign({operacion: "Modificar"}, valores);
        delete datos[nombreCampoEstReg];

        const body = generarBody(datos);

        const enError = (e) => {
            console.error(e);
            mensajeError.value = "Error al modificar la fila de la tabla GZZ_ASTROS";
        }

        realizarOperacion(body, "PUT", "gzz_astro", enExitoModificarFila, enError);
    };

    const eliminar = async () => {
        const body = `${nombreCampoCod}=${decodeURIComponent(valores[nombreCampoCod])}`;

        const enError = (e) => {
            console.error(e);
            mensajeError.value = "Error al eliminar la fila de la tabla GZZ_ASTROS";
        }

        realizarOperacion(body, "DELETE", "gzz_astro", enExitoModificarFila, enError);
    };

    return {
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
        adicionar,
        modificar,
        eliminar
    }
};
