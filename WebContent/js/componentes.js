
app.component("grilla-datos", {
    template: `
    <div class="registro">
        <h3>Registro de {{ etiqueta }}</h3>
        <form @submit.prevent>
            <div class="estr_form">
                <template v-for="campo in campos">
                    <template v-if="campo.tipo === 'number' || campo.tipo === 'text'">
                        <label :for="campo.nombre">{{ campo.nombre }}</label>
                        <input :id="campo.nombre" 
                            :type="campo.tipo" 
                            :name="campo.nombre"
                            :value="valores[campo.nombre]"
                            :disabled="camposDesactivados[campo.nombre] === true || operacionActual === ''"
                            @input="funActualizarValor(campo.nombre, $event.target.value)">
                    </template>
                    <template v-else-if="campo.tipo === 'select'">
                        <label :for="campo.nombre">{{ campo.nombre }}</label>
                        <select :id="campo.nombre"
                            :name="campo.nombre"
                            :value="valores[campo.nombre]"
                            :disabled="camposDesactivados[campo.nombre] === true || operacionActual === ''"
                            @input="funActualizarValor(campo.nombre, $event.target.value)"
                        >
                            <option v-for="valor in campo.valores" :value="valor">{{ valor }}</option>
                        </select>
                    </template>
                </template>
            </div>
        </form>
    </div>
    `,
    props: {
        etiqueta: {
            type: String,
            required: true
        },
        campos: {
            type: Array,
            required: true
        },
        camposDesactivados: {
            tipe: Object,
            required: true
        },
        operacionActual: {
            type: String,
            required: true
        },
        valores: {
            type: Object,
            required: true
        },
        funActualizarValor: {
            type: Function,
            required: true
        }
    }
});

app.component("tabla-datos", {
    template: `
        <div class="tabla">
            <h3>{{ nombre }}</h3>
            <table class="tabla_datos">
                <thead>
                <tr>
                    <td v-for="nombreColumna in nombresColumnas">
                        {{ nombreColumna }}
                    </td>
                </tr>
                </thead>
                <tbody>
                <template v-for="(fila, pos) in filas">
                    <tr :class="pos === posFilaSeleccionada? 'fila-seleccionada': ''" 
                        @click="funAlClick(pos)"
                    >
                        <td v-for="nombreColumna in nombresColumnas">{{ fila[nombreColumna] }}</td>
                    </tr>
                </template>
                </tbody>
            </table>
        </div>
    `,
    props: {
        nombre: {
            type: String,
            required: true
        },
        nombresColumnas: {
            type: Array,
            required: true
        },
        filas: {
            type: Array,
            required: true
        },
        funAlClick: {
            type: Function,
            required: true
        },
        posFilaSeleccionada: {
            type: Number,
            required: true
        }
    }
});

app.component("grilla-botones", {
    template: `
    <div class="botones">
        <div>
            <button :class="'boton-' + estadoBotones.adicionar" @click="funAdicionar">Adicionar</button>
        </div>
        <div>
            <button :class="'boton-' + estadoBotones.modificar" @click="funModificar">Modificar</button>
        </div>
        <div>
            <button :class="'boton-' + estadoBotones.eliminar" @click="funEliminar">Eliminar</button>
        </div>
        <div>
            <button @click="funCancelar(true)">Cancelar</button>
        </div>
        <div>
            <button :class="'boton-' + estadoBotones.inactivar" @click="funInactivar">Inactivar</button>
        </div>
        <div>
            <button :class="'boton-' + estadoBotones.reactivar" @click="funReactivar">Reactivar</button>
        </div>
        <div>
            <button :class="'boton-' + estadoBotones.actualizar" @click="funActualizar">Actualizar</button>
        </div>
        <div>
            <button @click="funSalir">Salir</button>
        </div>
    </div>
    `,
    props: [
        "estadoBotones",
        "funAdicionar",
        "funModificar",
        "funEliminar",
        "funCancelar",
        "funInactivar",
        "funReactivar",
        "funActualizar",
        "funSalir"
    ]
});

app.mount("#app");