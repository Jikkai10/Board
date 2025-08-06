package org.example.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.exception.EntityNotFoundException;
import org.example.persistence.dao.BoardColumnDAO;
import org.example.persistence.entity.BoardColumnEntity;
import org.example.persistence.entity.MovementsCardEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardColumnQueryService {

    private final Connection connection;

    public Optional<BoardColumnEntity> findById(final Long id) throws SQLException {
        var dao = new BoardColumnDAO(connection);
        return dao.findById(id);
    }

    public List<List<MovementsCardEntity>> getMovementsCards(final BoardColumnEntity boardColumn) throws SQLException {
        var dao = new BoardColumnDAO(connection);
        var optional = dao.findById(boardColumn.getId());
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("A coluna de id %s não foi encontrada".formatted(boardColumn.getId()));
        }
        List<List<MovementsCardEntity>> movements = new ArrayList<>();
        var service = new CardQueryService(connection);
        for (var card : optional.get().getCards()) {
            movements.add(service.getMovements(card.getId()));
        }
        return movements;
    }

}
