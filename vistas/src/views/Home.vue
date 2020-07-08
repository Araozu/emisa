<template>
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
</template>

<script>
    import { ref } from "vue";

    export default {
        setup () {
            const astCod = ref(-1);
            const astNom = ref("");
            const astTip = ref(-1);
            const conexionActiva = ref(true);

            const cerrarVentana = () => conexionActiva.value = false;

            return {
                astCod,
                astNom,
                astTip,
                conexionActiva,
                cerrarVentana
            }
        }
    }

</script>

<style scoped>

    .gzz_astro {
        border: solid 1px black;
        margin: 1rem;
        padding: 1rem;
        border-radius: var(--tamanio-border-radius);
        max-width: 50rem;
    }

    .estr_form {
        display: grid;
        grid-template-columns: 20% auto;
        grid-column-gap: 1rem;
        grid-row-gap: 1rem;
    }

    .estr_form * {
        display: inline-block;
    }

    .registro, .tabla {
        border: solid 1px black;
        padding: 1rem;
        border-radius: var(--tamanio-border-radius);
    }

    .registro h3, .tabla h3 {
        margin: 0 0 2rem 0;
    }

    .botones {
        display: grid;
        grid-template-columns: auto auto auto auto;
        grid-row-gap: 1rem;
    }

    .botones div {
        text-align: center;
    }

    .botones div button {
        cursor: pointer;
    }

    .tabla_datos {
        display: table;
        width: 100%;

    }

    .tabla_datos, .tabla_datos td {
        border: 1px solid #151515;
        border-collapse: collapse;
    }

    .tabla_datos td {
        padding: 0.5rem;
    }


</style>
