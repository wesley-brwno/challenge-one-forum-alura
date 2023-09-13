package com.br.alura.forum.service.reposta;

import com.br.alura.forum.DTO.resposta.RespostaUsuarioData;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConverteObjectsParaRespostaUsuarioData {

    public List<RespostaUsuarioData> convertObjectToRespostaUsuarioData(List<Object[]> objectRespostas) {
        List<RespostaUsuarioData> respostas = new ArrayList<>();

        for (Object[] row : objectRespostas) {
            Long id = (long) row[0];
            String nome = (String) row[1];
            String mensagem = (String) row[2];
            LocalDateTime data = (LocalDateTime) row[3];
            Boolean solcao = (Boolean) row[4];
            respostas.add(new RespostaUsuarioData(id, nome, mensagem, data, solcao));
        }
        return respostas;
    }
}
