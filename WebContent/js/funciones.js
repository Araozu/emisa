
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

const usarCamposAdaptados = (campos) => {

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

    return {
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
    }
};

