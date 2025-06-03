package final_project.momeasy.domain.room_condition.repository;

import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomConditionBulkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<RoomCondition> roomConditions) {
        String sql = "INSERT INTO room_condition (child_id, humidity,temperature, created_at) VALUES (?,?,?,?)";
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                RoomCondition roomCondition = roomConditions.get(i);
                ps.setLong(1,roomCondition.getChild().getId());
                ps.setFloat(2,roomCondition.getHumidity());
                ps.setFloat(3,roomCondition.getTemperature());
                ps.setTimestamp(4, now);
            }
            @Override
            public int getBatchSize() {
                return roomConditions.size();
            }
        });
    }
}
