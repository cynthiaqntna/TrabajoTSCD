import org.junit.Test;

import java.util.List;
import java.util.Map;



public class NGramDataHandlerTest {

    @Test
    public void testProcessTokens() {
        // Configuración de la prueba
        int n = 3;
        NGramDataHandler dataHandler = new NGramDataHandler(n);

        // Datos de prueba
        List<String> tokens = List.of("The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog");

        // Procesar tokens
        dataHandler.processTokens(tokens);

        // Verificar que la estructura de datos interna esté correctamente poblada
        Map<String, Map<String, Integer>> nGrams = dataHandler.getNGrams();
        assertEquals(7, nGrams.size());  // Esto dependerá de la lógica exacta de tu implementación
        assertEquals(1, nGrams.get("The quick").get("brown").intValue());
        assertEquals(1, nGrams.get("quick brown").get("fox").intValue());
        // Agrega más verificaciones según sea necesario
    }

    // Puedes agregar más pruebas según sea necesario para tu aplicación.
}
