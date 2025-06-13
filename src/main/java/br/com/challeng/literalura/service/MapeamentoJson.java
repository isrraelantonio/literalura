package br.com.challeng.literalura.service;

import br.com.challeng.literalura.models.DadosLivro;
import br.com.challeng.literalura.models.Livro;
import br.com.challeng.literalura.service.traducao.TraduzirTexto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.HashMap;
import java.util.Map;

public class MapeamentoJson {

public String obterNovoJson(String endereco) throws JsonProcessingException {

    ConsumoAPI api = new ConsumoAPI();
    String results = api.obterDados(endereco);
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(results);
    JsonNode resultado = root.path("results");

    String titulo = "";
    String download = "";
    String lingua = "";
    String autor = "";
    String nascimmento = "";
    String morte = "";


        if (resultado.isArray()) {
            for (JsonNode book : resultado) {
                titulo = book.get("title").asText();
                download = book.get("download_count").asText();
                lingua = book.get("languages").get(0).asText();
                for (JsonNode dados : book.get("authors")) {
                    autor = dados.get("name").asText();
                    nascimmento = dados.get("birth_year").asText();
                    morte = dados.get("death_year").asText();

                }

            }

        }

        Map<String, String> mapa = new HashMap<>();
        mapa.put("autor", autor);
        mapa.put("nascimento", nascimmento);
        mapa.put("morte", morte);
        mapa.put("titulo", titulo);
        mapa.put("download", download);
        mapa.put("lingua", lingua);

        String json = mapper.writeValueAsString(mapa);
        return json;


}


}
