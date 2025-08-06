package org.example.persistence.entity;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class MovementsCardEntity {
    private Long cardId;
    private Long fromColumnId;
    private Long toColumnId;
    private OffsetDateTime movedAt;
}
