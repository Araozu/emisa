const servidor = "http://localhost:8080/Emisa"

new Vue({
    el: "#app",
    template: `
    <div class="contenedor">
        <h1>Gestión de Base de Datos.</h1>
        <h2>Emisa - Caso de uso exploraciones mineras.</h2>
        <hr>
        <div class="tablas-referenciales">
            <h2>Tablas referenciales</h2>
            <h3>
                <a href="gzz_mineral.html">GZM_MINERAL</a>
            </h3>
            <h3>
                <a href="gzz_astros.html">GZZ_ASTROS</a>
            </h3>
            <h3>
                <a href="p8m_centro.html">P8M_CENTRO</a>
            </h3>
            <h3>
                <a href="r7c_alimento.html">R7C_ALIMENTO</a>
            </h3>
            <h3>
                <a href="r7c_empleo.html">R7C_EMPLEO</a>
            </h3>
            <h3>
                <a href="r7z_categoria.html">RZC_CATEGORIA</a>
            </h3>
        </div>
    </div>
    `,
    data() {
        return {

        }
    }
});
