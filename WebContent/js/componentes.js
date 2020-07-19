
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