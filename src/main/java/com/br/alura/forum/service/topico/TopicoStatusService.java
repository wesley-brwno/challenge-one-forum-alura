package com.br.alura.forum.service.topico;

import com.br.alura.forum.constrains.StatusTopico;
import com.br.alura.forum.modelo.Topico;
import com.br.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicoStatusService {

    @Autowired
    private TopicoRepository topicoRepository;

    public void topicoSolucionado(Long topicoId) {
        Topico topico = topicoRepository.findById(topicoId).get();
        if (topico.getStatus() != StatusTopico.FECHADO) {
            topico.setStatus(StatusTopico.SOLUCIONADO);
            topicoRepository.save(topico);
        }
    }

    public void topicoNaoSolucionado(Long topicoId) {
        Topico topico = topicoRepository.findById(topicoId).get();
        if (topico.getStatus() != StatusTopico.FECHADO) {
            topico.setStatus(StatusTopico.NAO_SOLUCIONADO);
            topicoRepository.save(topico);
        }
    }
}
