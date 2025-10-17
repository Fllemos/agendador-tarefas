package com.javanauta.agendadortarefas.business;

import com.javanauta.agendadortarefas.business.converter.TarefasConverter;
import com.javanauta.agendadortarefas.business.dto.TarefasDTO;
import com.javanauta.agendadortarefas.business.mapper.TarefaUpdateConverter;
import com.javanauta.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.javanauta.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.javanauta.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.javanauta.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.javanauta.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;


    // ============================================================
    // ======================= GRAVAR TAREFA ======================
    // ============================================================
        public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        TarefasEntity entity = tarefasConverter.paraEntity(dto);

        return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity));
        }
    // ========================== FIM =============================


    // ============================================================
    // =================== LISTA POR PERIODO ======================
    // ============================================================
        public List<TarefasDTO> buscaTarefaPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        // 1️⃣ Busca as tarefas no banco de dados pelo período informado
        List<TarefasEntity> tarefasEncontradas = tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal);
        // 2️⃣ Converte a lista de entidades para DTOs (para enviar ao front-end)
        List<TarefasDTO> tarefasDto = tarefasConverter.paraListaTarefasDto(tarefasEncontradas);

        return tarefasDto;
        }
    // ========================== FIM =============================


    // ============================================================
    // =================== BUSCA TAREFA POR EMAIL==================
    // ============================================================
        public List<TarefasDTO>buscaTarefaPorEmail(String token){
                                String email  = jwtUtil.extrairEmailToken(token.substring(7));
            List<TarefasEntity> listaPorEmail = tarefasRepository.findByemailUsuario(email);
            List<TarefasDTO>    listaEmaildto = tarefasConverter.paraListaTarefasDto(listaPorEmail);
            return listaEmaildto;
        }
    // ========================== FIM =============================

    // ============================================================
    // =================== DELETA TAREFA POR ID  ==================
    // ============================================================
        public void deletaPorId(String id){
            try { tarefasRepository.deleteById(id);

            }catch (ResourceNotFoundException e){
                throw new ResourceNotFoundException("erro ao deletar tarefa, id " + id + " nao existe..: " + e.getCause());
            }
        }
    // ========================== FIM =============================

    // ============================================================
    // =================== ALTERAR STATUS =========================
    // ============================================================
    public TarefasDTO alteraStatus(StatusNotificacaoEnum status, String id){
        try {
            TarefasEntity entity = tarefasRepository.findById(id). // --- busca o cara na banco de dados
                    orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
            entity.setStatusNotificacaoEnum(status);  // --> atualiza com o status q recebeu
            return tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity)); // ---> atualiza (sallva)

        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("erro ao alterar a tarefa, id " + id + " nao existe..: " + e.getCause());
        }
    }
    // ========================== FIM =============================

    // ============================================================
    // =================== ALTERAR TAREFA =========================
    // ============================================================
    public TarefasDTO alterarTarefa(TarefasDTO tarefasDTO, String id){
        try {
            TarefasEntity entity = tarefasRepository.findById(id). // --- busca o cara na banco de dados
                orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));


            TarefasEntity entity1 = tarefasConverter.updateTarefa(tarefasDTO, entity);
            return  tarefasConverter.paraTarefaDTO(tarefasRepository.save(entity1));


        }   catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("erro ao alterar a atualizar a tarefa ,id " + id + " nao existe..: " + e.getCause());
        }
    }
    // ========================== FIM =============================




}