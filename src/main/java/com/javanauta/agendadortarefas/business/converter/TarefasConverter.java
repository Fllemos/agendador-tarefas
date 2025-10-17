package com.javanauta.agendadortarefas.business.converter;

import com.javanauta.agendadortarefas.business.dto.TarefasDTO;
import com.javanauta.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TarefasConverter {



    // DO FRONT PARA TABELA
    public TarefasEntity paraEntity(TarefasDTO dto) {
            return TarefasEntity.builder()
                    .id(dto.getId())
                    .nomeTarefa(dto.getNomeTarefa())
                    .descricao(dto.getDescricao())
                    .dataCriacao(dto.getDataCriacao())
                    .dataEvento(dto.getDataEvento())
                    .emailUsuario(dto.getEmailUsuario())
                    .dataAlteracao(dto.getDataAlteracao())
                    .statusNotificacaoEnum(dto.getStatusNotificacaoEnum())
                    .build();
    }

    // DA TABELA PARA O FRONT
    public TarefasDTO paraTarefaDTO(TarefasEntity  entity) {
        return TarefasDTO.builder()
                .id(entity.getId())
                .nomeTarefa(entity.getNomeTarefa())
                .descricao(entity.getDescricao())
                .dataCriacao(entity.getDataCriacao())
                .dataEvento(entity.getDataEvento())
                .emailUsuario(entity.getEmailUsuario())
                .dataAlteracao(entity.getDataAlteracao())
                .statusNotificacaoEnum(entity.getStatusNotificacaoEnum())
                .build();
    }


    // ============================================================
    // ===== DO FRONT PARA TABELA EM LISTA (DTO -> ENTITY) ========
    // ============================================================

    // Converte uma lista de objetos TarefasDTO (vindos do front-end)
    // em uma lista de objetos TarefasEntity (usados para salvar no banco de dados)
    public List<TarefasEntity> paraListaEntity(List<TarefasDTO> tarefasDTOList) {
        // Cria uma nova lista onde serão armazenadas as entidades convertidas
        List<TarefasEntity> entities = new ArrayList<>();

        // Verifica se a lista recebida não é nula (para evitar NullPointerException)
        if (tarefasDTOList != null) {
            // Faz um loop em cada item da lista de DTOs
            for (TarefasDTO tarefasDTOLists : tarefasDTOList) {
                // Converte o DTO atual em Entity e adiciona na nova lista
                entities.add(paraListaTarefasEntity(tarefasDTOLists));
            }
        }

        // Retorna a lista de entidades convertidas
        return entities;
    }

    // Converte um único objeto TarefasDTO em TarefasEntity
    public TarefasEntity paraListaTarefasEntity(TarefasDTO dto){
        // Caso o DTO recebido seja nulo, retorna null (evita erro)
        if (dto == null) return null;

        // Constrói uma nova entidade com os dados copiados do DTO
        return TarefasEntity.builder()
                .id(dto.getId())
                .nomeTarefa(dto.getNomeTarefa())
                .descricao(dto.getDescricao())
                .dataCriacao(dto.getDataCriacao())
                .dataEvento(dto.getDataEvento())
                .emailUsuario(dto.getEmailUsuario())
                .dataAlteracao(dto.getDataAlteracao())
                .statusNotificacaoEnum(dto.getStatusNotificacaoEnum())
                .build(); // Retorna a entidade construída
    }


    // ============================================================
    // ============ V O L T A (Entidade -> DTO) ===================
    // ============================================================

    // Converte uma lista de entidades (vindas do banco)
    // em uma lista de DTOs (para enviar ao front-end)
    public List<TarefasDTO> paraListaTarefasDto(List<TarefasEntity> entity) {
        // Cria uma nova lista onde serão armazenados os DTOs convertidos
        List<TarefasDTO> dtos = new ArrayList<>();

        // Percorre cada entidade recebida
        for (TarefasEntity tarefasEntity : entity) {
            // Converte a entidade atual em DTO e adiciona na lista
            dtos.add(paraListaTarefasDTO(tarefasEntity));
        }

        // Retorna a lista de DTOs
        return dtos;
    }

    // Converte uma única entidade em DTO
    public TarefasDTO paraListaTarefasDTO(TarefasEntity entity){
        // Cria e retorna um novo DTO com os dados copiados da entidade
        return TarefasDTO.builder()
                .id(entity.getId())
                .nomeTarefa(entity.getNomeTarefa())
                .descricao(entity.getDescricao())
                .dataCriacao(entity.getDataCriacao())
                .dataEvento(entity.getDataEvento())
                .emailUsuario(entity.getEmailUsuario())
                .dataAlteracao(entity.getDataAlteracao())
                .statusNotificacaoEnum(entity.getStatusNotificacaoEnum())
                .build(); // Retorna o DTO construído
    }




    /// ============================================================
    /// ================== tratamente para atualização dos dados da tarefasuario
    /// ============================================================
    public TarefasEntity updateTarefa(TarefasDTO tarefasDTO, TarefasEntity entity) {

        entity.setNomeTarefa(Optional.ofNullable(tarefasDTO.getNomeTarefa())
                .orElse(entity.getNomeTarefa()));

        entity.setDescricao(Optional.ofNullable(tarefasDTO.getDescricao())
                .orElse(entity.getDescricao()));

        entity.setDataCriacao(Optional.ofNullable(tarefasDTO.getDataCriacao())
                .orElse(entity.getDataCriacao()));

        entity.setDataEvento(Optional.ofNullable(tarefasDTO.getDataEvento())
                .orElse(entity.getDataEvento()));

        entity.setEmailUsuario(Optional.ofNullable(tarefasDTO.getEmailUsuario())
                .orElse(entity.getEmailUsuario()));

        entity.setDataAlteracao(Optional.ofNullable(tarefasDTO.getDataAlteracao())
                .orElse(entity.getDataAlteracao()));

        entity.setStatusNotificacaoEnum(Optional.ofNullable(tarefasDTO.getStatusNotificacaoEnum())
                .orElse(entity.getStatusNotificacaoEnum()));

        return entity;
    }
    ///  =========================   FIM   ==========================









}
