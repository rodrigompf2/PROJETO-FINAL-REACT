package br.com.serratec.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.serratec.dto.Endereco;

@Service
public class ViaCepService {

    private final RestTemplate restTemplate = new RestTemplate();

    public Endereco buscarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, Endereco.class);
    }
}