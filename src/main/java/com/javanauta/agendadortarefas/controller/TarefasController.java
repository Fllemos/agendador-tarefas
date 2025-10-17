package com.javanauta.agendadortarefas.controller;

import com.javanauta.agendadortarefas.business.TarefasService;
import com.javanauta.agendadortarefas.business.dto.TarefasDTO;
import com.javanauta.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.javanauta.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private static final Logger log = LoggerFactory.getLogger(TarefasController.class);
    private final TarefasService tarefasService;


    // ============================================================
    // ======================= GRAVAR TAREFA ======================
    // ============================================================
    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(@RequestBody TarefasDTO dto,
                                                    @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.gravarTarefa(token, dto));
    }
    // ========================== FIM =============================



    // ============================================================
    // =================== LISTA POR PERIODO ======================
    // ============================================================
    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>>buscaListaDeTarefasPorDTO(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal){
            return ResponseEntity.ok(tarefasService.buscaTarefaPorPeriodo(dataInicial,dataFinal));
    }
    // ========================== FIM =============================


    // ============================================================
    // =================== BUSCA TAREFA POR EMAIL =================
    // ============================================================
    @GetMapping
    public ResponseEntity<List<TarefasDTO>>buscaTarefasPorEmail(@RequestHeader("Authorization") String token){
           return ResponseEntity.ok(tarefasService.buscaTarefaPorEmail(token));
    }
    // ========================== FIM =============================

    // ============================================================
    // =================== DELETA TAREFA POR ID  ==================
    // ============================================================
    @DeleteMapping
    public ResponseEntity<Void>deletaId(@RequestParam("id") String id){
        try { tarefasService.deletaPorId(id);
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("erro no delete " +id );
        }
        return  ResponseEntity.ok().build();
    }
    // ========================== FIM =============================

    // ============================================================
    // =================== ALTERAR STATUS =========================
    // ============================================================
    @PatchMapping
    public ResponseEntity<TarefasDTO> alterarStatus(@RequestParam("status") StatusNotificacaoEnum status,@RequestParam("id") String id){
        try {
            tarefasService.alteraStatus(status, id);
        }
            catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("erro no alterar status " +id );
        }
        return  ResponseEntity.ok().build();
    }
    // ========================== FIM =============================



    // ============================================================
    // =================== ALTERAR TAREFA =========================
    // ============================================================
    @PutMapping
    public ResponseEntity<TarefasDTO>alteraTarefa(@RequestBody TarefasDTO dto,@RequestParam("id") String id){
        try{
            return  ResponseEntity.ok(tarefasService.alterarTarefa(dto, id));

        } catch (RuntimeException e) {
            throw new RuntimeException("erro no alteração de tarefa , id informaco.....:" + id);
        }
    }
    // ========================== FIM =============================







}












//    @PostMapping
//    public ResponseEntity<TarefasDTO> gravarTarefa(@RequestBody TarefasDTO dto,
//                                                   @RequestHeader("Authorization") String token) {
//        log.info("📩 Recebendo requisição POST /tarefas");
//        log.debug("🧾 Body recebido: {}", dto);
//        log.debug("🔑 Token recebido: {}", token);
//
//        TarefasDTO response = tarefasService.gravarTarefa(token, dto);
//
//        log.info("✅ Tarefa gravada com sucesso. Retornando DTO: {}", response);
//        return ResponseEntity.ok(dto);
//    }

