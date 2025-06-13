package br.com.challeng.literalura.service.traducao;

import br.com.challeng.literalura.service.ConsumoAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TraduzirTexto {

    public static String obterTraducaos(String textoParaTraducao) throws JsonProcessingException {

        String endereco = "https://api.mymemory.translated.net/get?q="+ textoParaTraducao.replace(" ", "+").replace("\"", "%22")+"&langpair=en%7cpt";

        ConsumoAPI obterdados = new ConsumoAPI();
        String json = obterdados.obterDados(endereco);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        // Extrai o campo translatedText
        String traducao = root.path("responseData").path("translatedText").asText();
        return  traducao;



    }
}
