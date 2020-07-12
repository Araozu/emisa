const servidor = "http://localhost:8080/Emisa"

new Vue({
    el: "#app",
    template: `
    <div>
        <template v-if="conexionActiva">
            <div class="gzz_astro">
                <h1>GZZ_ASTRO</h1>
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
                <div class="tabla">
                    <h3>Tabla GZZ_ASTRO</h3>
                    <table class="tabla_datos">
                        <thead>
                        <tr>
                            <td>AstCod</td>
                            <td>AstNom</td>
                            <td>AstTip</td>
                            <td>AstEstReg</td>
                        </tr>
                        </thead>
                        <tbody>
                        <template v-for="(fila, pos) in filas">
                            <tr :class="pos === posFilaSeleccionada? 'fila-seleccionada': ''" 
                                @click="seleccionarFila(pos)"
                            >
                                <td>{{ fila.AstCod }}</td>
                                <td>{{ fila.AstNom }}</td>
                                <td>{{ fila.AstTip }}</td>
                                <td>{{ fila.AstEstReg }}</td>
                            </tr>
                        </template>
                        </tbody>
                    </table>
                </div>
                <br>
                <div class="botones">
                    <div>
                        <button :class="'boton-' + estadoBotones.adicionar" @click="iniciarAdicion">Adicionar</button>
                    </div>
                    <div>
                        <button :class="'boton-' + estadoBotones.modificar" @click="iniciarModificacion">Modificar</button>
                    </div>
                    <div>
                        <button :class="'boton-' + estadoBotones.eliminar" @click="iniciarEliminar">Eliminar</button>
                    </div>
                    <div>
                        <button @click="limpiar(true)">Cancelar</button>
                    </div>
                    <div>
                        <button :class="'boton-' + estadoBotones.inactivar" @click="iniciarInactivar">Inactivar</button>
                    </div>
                    <div>
                        <button :class="'boton-' + estadoBotones.reactivar" @click="iniciarReactivar">Reactivar</button>
                    </div>
                    <div>
                        <button :class="'boton-' + estadoBotones.actualizar" @click="actualizar">Actualizar</button>
                    </div>
                    <div>
                        <button @click="cerrarVentana">Salir</button>
                    </div>
                </div>
                <div class="mensaje-error" :style="estilosMensajeError">{{ mensajeError }}</div>
            </div>
        </template>
        <template v-else>
            <p>Conexión terminada. Puedes cerrar esta pestaña.</p>
        </template>
    </div>
    `,
    data() {
        return {
            astCod: undefined,
            astNom: undefined,
            astTip: "0",
            astEstReg: "A",
            inputsDesactivados: {
                astCod: false,
                astNom: false,
                astTip: false,
                astEstReg: false
            },
            conexionActiva: true,
            filas: [],
            mensajeError: "_",
            estadoBotones: {
                adicionar: "disponible",
                modificar: "inactivo",
                eliminar: "inactivo",
                inactivar: "inactivo",
                reactivar: "inactivo",
                actualizar: "inactivo"
            },
            operacionActual: "",
            posFilaSeleccionada: -1
        }
    },
    computed: {
        estilosMensajeError() {
            return {
                opacity: this.mensajeError === "_"? "0": 1
            }
        }
    },
    methods: {
        limpiar(limpiarFila = false) {
            this.estadoBotones.adicionar = "disponible";
            this.estadoBotones.modificar = "inactivo";
            this.estadoBotones.eliminar = "inactivo";
            this.estadoBotones.inactivar = "inactivo";
            this.estadoBotones.reactivar = "inactivo";
            this.estadoBotones.actualizar = "inactivo";
            this.inputsDesactivados.astCod = false;
            this.inputsDesactivados.astEstReg = false;
            this.inputsDesactivados.astEstNom = false;
            this.inputsDesactivados.astTip = false;
            this.operacionActual = "";
            this.limpiarFormulario();
            if (limpiarFila) {
                this.posFilaSeleccionada = -1;
            }
        },
        limpiarFormulario() {
            this.astCod = undefined;
            this.astNom = undefined;
            this.astTip = undefined;
            this.astEstReg = "A";
        },
        marcarBotones(activos, inactivos, disponibles = []) {
            this.limpiar();
            for (const a of activos) {
                this.estadoBotones[a] = "activo";
            }
            for (const i of inactivos) {
                this.estadoBotones[i] = "inactivo";
            }
            for (const d of disponibles) {
                this.estadoBotones[d] = "disponible";
            }
        },
        iniciarAdicion() {
            if (this.estadoBotones.adicionar !== "disponible") return;
            this.limpiarFormulario();
            this.marcarBotones(["adicionar"], ["modificar", "eliminar", "inactivar", "reactivar"]);
            this.operacionActual = "adicionar";
        },
        iniciarModificacion() {
            if (this.posFilaSeleccionada === -1 || this.estadoBotones.modificar !== "disponible") return;
            this.limpiarFormulario();
            this.marcarBotones(["modificar"], ["adicionar", "eliminar", "inactivar", "reactivar"]);
            this.operacionActual = "modificar";

            const numFila = this.posFilaSeleccionada;
            const fila = this.filas[numFila];
            this.astCod = fila.AstCod;
            this.astNom = fila.AstNom;
            this.astTip = fila.AstTip;
            this.astEstReg = fila.AstEstReg;
            this.inputsDesactivados.astCod = true;
            this.inputsDesactivados.astEstReg = true;
        },
        iniciarEliminar() {
            if (this.posFilaSeleccionada === -1 || this.estadoBotones.modificar !== "disponible") return;
            this.limpiarFormulario();
            this.marcarBotones(["eliminar"], ["adicionar", "modificar", "inactivar", "reactivar"]);
            this.operacionActual = "eliminar";

            const numFila = this.posFilaSeleccionada;
            const fila = this.filas[numFila];
            this.astCod = fila.AstCod;
            this.astNom = fila.AstNom;
            this.astTip = fila.AstTip;
            this.astEstReg = "*";
            this.inputsDesactivados.astCod = true;
            this.inputsDesactivados.astEstReg = true;
            this.inputsDesactivados.astNom = true;
            this.inputsDesactivados.astTip = true;
        },
        iniciarInactivar() {
            if (this.posFilaSeleccionada === -1 || this.estadoBotones.modificar !== "disponible") return;
            this.limpiarFormulario();
            this.marcarBotones(["inactivar"], ["adicionar", "modificar", "eliminar", "reactivar"]);
            this.operacionActual = "inactivar";

            const numFila = this.posFilaSeleccionada;
            const fila = this.filas[numFila];
            this.astCod = fila.AstCod;
            this.astNom = fila.AstNom;
            this.astTip = fila.AstTip;
            this.astEstReg = "I";
            this.inputsDesactivados.astCod = true;
            this.inputsDesactivados.astEstReg = true;
            this.inputsDesactivados.astNom = true;
            this.inputsDesactivados.astTip = true;
        },
        iniciarReactivar() {
            if (this.posFilaSeleccionada === -1 || this.estadoBotones.modificar !== "disponible") return;
            this.limpiarFormulario();
            this.marcarBotones(["reactivar"], ["adicionar", "modificar", "eliminar", "inactivar"]);
            this.operacionActual = "reactivar";

            const numFila = this.posFilaSeleccionada;
            const fila = this.filas[numFila];
            this.astCod = fila.AstCod;
            this.astNom = fila.AstNom;
            this.astTip = fila.AstTip;
            this.astEstReg = "A";
            this.inputsDesactivados.astCod = true;
            this.inputsDesactivados.astEstReg = true;
            this.inputsDesactivados.astNom = true;
            this.inputsDesactivados.astTip = true;
        },
        mostrarMensaje(msg, ms) {
            this.mensajeError = msg;
            const vm = this;
            setTimeout(() => {
                vm.mensajeError = "_";
            }, ms);
        },
        async adicionar() {
            const AstCod = parseInt(this.astCod);
            const AstNom = this.astNom.toString();
            const AstTip = parseInt(this.astTip) === 1? 1: 0;
            const AstEstReg = this.astEstReg.toString();

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
                        this.mensajeError = "_";
                        this.filas.push(datos);
                    } else {
                        this.mostrarMensaje("No se insertó ningún dato.", 5000);
                    }

                    this.limpiarFormulario();
                    this.limpiar();
                } else {
                    console.error(peticion);
                    this.mensajeError = "Error al adicionar los datos a la tabla GZZ_ASTROS";
                }

            } catch (e) {
                console.error(e);
                this.mensajeError = "Error al adicionar los datos a la tabla GZZ_ASTROS";
            }

        },
        async modificar() {
            const AstCod = this.astCod;
            const AstNom = this.astNom.toString();
            const AstTip = this.astTip;

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
                        this.mensajeError = "_";
                        const nuevaFila = {
                            AstCod,
                            AstNom,
                            AstTip,
                            AstEstReg: this.astEstReg
                        };
                        const posFila = this.posFilaSeleccionada;
                        this.filas.splice(posFila, 1, nuevaFila);
                    } else {
                        this.mostrarMensaje("No se modificó la fila.", 5000);
                    }

                    this.limpiarFormulario();
                    this.limpiar(true);
                } else {
                    console.error(peticion);
                    this.mensajeError = "Error al modificar la fila de la tabla GZZ_ASTROS";
                }

            } catch (e) {
                console.error(e);
                this.mensajeError = "Error al modificar la fila de la tabla GZZ_ASTROS";
            }

        },
        async eliminar() {
            const AstCod = this.astCod;
            const AstNom = this.astNom.toString();
            const AstTip = this.astTip;
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
                        this.mensajeError = "_";
                        const nuevaFila = {
                            AstCod,
                            AstNom,
                            AstTip,
                            AstEstReg: "*"
                        };
                        const posFila = this.posFilaSeleccionada;
                        this.filas.splice(posFila, 1, nuevaFila);
                    } else {
                        this.mostrarMensaje("No se eliminó la fila.", 5000);
                    }

                    this.limpiarFormulario();
                    this.limpiar(true);
                } else {
                    console.error(peticion);
                    this.mensajeError = "Error al eliminar la fila de la tabla GZZ_ASTROS";
                }

            } catch (e) {
                console.error(e);
                this.mensajeError = "Error al eliminar la fila de la tabla GZZ_ASTROS";
            }
        },
        async in_re_activar(esIn) {
            const AstCod = this.astCod;
            const AstNom = this.astNom.toString();
            const AstTip = this.astTip;
            const AstEstReg = this.astEstReg;

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
                        this.mensajeError = "_";
                        const nuevaFila = {
                            AstCod,
                            AstNom,
                            AstTip,
                            AstEstReg
                        };
                        const posFila = this.posFilaSeleccionada;
                        this.filas.splice(posFila, 1, nuevaFila);
                    } else {
                        this.mostrarMensaje("No se modificó la fila.", 5000);
                    }

                    this.limpiarFormulario();
                    this.limpiar(true);
                } else {
                    console.error(peticion);
                    this.mensajeError = "Error al modificar la fila de la tabla GZZ_ASTROS";
                }

            } catch (e) {
                console.error(e);
                this.mensajeError = "Error al modificar la fila de la tabla GZZ_ASTROS";
            }
        },
        actualizar() {
            switch (this.operacionActual) {
                case "adicionar": {
                    this.adicionar();
                    break;
                }
                case "modificar": {
                    this.modificar();
                    break;
                }
                case "eliminar": {
                    this.eliminar();
                    break;
                }
                case "inactivar": {
                    this.in_re_activar(true);
                    break;
                }
                case "reactivar": {
                    this.in_re_activar(false);
                    break;
                }
            }
        },
        cerrarVentana() {
            this.conexionActiva = false;
        },
        async cargarFilas() {
            const url = `${servidor}/api/gzz_astro/`;
            try {
                const peticion = await fetch(url);
                if (peticion.ok) {
                    this.filas = await peticion.json();
                    this.mensajeError = "_";
                } else {
                    console.error(peticion);
                    this.mensajeError = "Ocurrió un error al recuperar los datos del servidor.";
                }
            } catch (e) {
                console.error(e);
                this.mensajeError = "Ocurrió un error al recuperar los datos del servidor.";
            }
        },
        seleccionarFila(posFila) {
            if (this.operacionActual !== "") return;
            const botonesDisponibles = ["modificar", "eliminar", "actualizar", "reactivar", "inactivar"];
            this.marcarBotones([], ["adicionar"], botonesDisponibles);
            this.posFilaSeleccionada = posFila;
        }
    },
    created() {
        this.cargarFilas();
    }
});
