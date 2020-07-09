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
                        <button>Elimitar</button>
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
            </div>
        </template>
        <template v-else>
            <p>Conexión terminada. Puedes cerrar esta pestaña.</p>
        </template>
    </div>
    `,
    data() {
        return {
            astCod: -1,
            astNom: "",
            astTip: -1,
            conexionActiva: true
        }
    },
    methods: {
        cerrarVentana() {
            this.conexionActiva = false;
        }
    }
});
