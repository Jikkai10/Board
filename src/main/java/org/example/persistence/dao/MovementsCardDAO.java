package org.example.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.persistence.converter.OffsetDateTimeConverter.toOffsetDateTime;
import org.example.persistence.entity.MovementsCardEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MovementsCardDAO {
    private final Connection connection;

    public List<MovementsCardEntity> findByCardId(final Long cardId) throws SQLException{
        List<MovementsCardEntity> entities = new ArrayList<>();
        var sql = "SELECT card_id, from_column_id, to_column_id, moved_at FROM movements_cards WHERE card_id = ? ORDER BY moved_at";
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, cardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()){
                var entity = new MovementsCardEntity();
                entity.setCardId(resultSet.getLong("card_id"));
                entity.setFromColumnId(resultSet.getLong("from_column_id"));
                entity.setToColumnId(resultSet.getLong("to_column_id"));
                entity.setMovedAt(toOffsetDateTime(resultSet.getTimestamp("moved_at")));
                entities.add(entity);
            }
            return entities;
        }
    }
}
