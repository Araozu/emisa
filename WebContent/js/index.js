const servidor = "http://localhost:8080/Emisa"

const app = new Vue({
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
                            <input id="AstCod" type="number" name="AstCod" v-model="astCod">
                            <label for="AstNom">AstNom</label>
                            <input id="AstNom" type="text" name="AstNom" v-model="astNom">
                            <label for="AstTip">AstTip</label>
                            <input id="AstTip" type="number" name="AstTip" v-model="astTip">
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
                        </tr>
                        </thead>
                        <tbody>
                        <template v-for="fila in filas">
                            <tr>
                                <td>{{ fila.AstCod }}</td>
                                <td>{{ fila.AstNom }}</td>
                                <td>{{ fila.AstTip }}</td>
                            </tr>
                        </template>
                        </tbody>
                    </table>
                </div>
                <br>
                <div class="botones">
                    <div>
                        <button>Adicionar</button>
                    </div>
                    <div>
                        <button>Modificar</button>
                    </div>
                    <div>
                        <button>Eliminar</button>
                    </div>
                    <div>
                        <button>Cancelar</button>
                    </div>
                    <div>
                        <button>Inactivar</button>
                    </div>
                    <div>
                        <button>Reactivar</button>
                    </div>
                    <div>
                        <button>Actualizar</button>
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
            astTip: undefined,
            conexionActiva: true,
            filas: [],
            mensajeError: "_"
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
        }
    },
    created() {
        this.cargarFilas();
    }
});
