package com.agentcoon.dailyhaiku.infrastructure.jdbi.haiku;

import com.agentcoon.dailyhaiku.domain.Haiku;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.agentcoon.dailyhaiku.domain.Haiku.HaikuBuilder.aHaiku;

public class JdbiHaikuMapper implements ResultSetMapper<Haiku> {

    @Override
    public Haiku map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        Haiku.HaikuBuilder builder = aHaiku();

        builder.withId(resultSet.getLong(JdbiHaikuRepository.FIELD_ID))
                .withAuthor(resultSet.getString(JdbiHaikuRepository.FIELD_AUTHOR))
                .withBody(resultSet.getString(JdbiHaikuRepository.FIELD_BODY));

        return builder.build();
    }
}
