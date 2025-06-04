package final_project.momeasy.domain.fever_record.repository;

import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FeverRecordBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<FeverRecord> FeverRecords) {
        String sql = "INSERT INTO fever_record (child_id, fever, created_at) VALUES (?,?,?)";
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException{
                FeverRecord feverRecord = FeverRecords.get(i);
                ps.setLong(1,feverRecord.getChild().getId());
                ps.setFloat(2,feverRecord.getFever());
                ps.setTimestamp(3, now);
            }
            @Override
            public int getBatchSize() {
                return FeverRecords.size();
            }
        });
    }
}
