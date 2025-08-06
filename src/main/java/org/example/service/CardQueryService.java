package org.example.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.example.dto.CardDetailsDTO;
import org.example.exception.EntityNotFoundException;
import org.example.persistence.dao.CardDAO;
import org.example.persistence.dao.MovementsCardDAO;
import org.example.persistence.entity.MovementsCardEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardQueryService {

    private final Connection connection;

    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException {
        var dao = new CardDAO(connection);
        return dao.findById(id);
    }

    public List<MovementsCardEntity > getMovements(final Long cardId) throws SQLException {
        var dao = new CardDAO(connection);
        var optional = dao.findById(cardId);
        if (optional.isEmpty()){
            throw new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId));
        }
        var movementsCardDAO = new MovementsCardDAO(connection);
        return movementsCardDAO.findByCardId(cardId);
    }

}
