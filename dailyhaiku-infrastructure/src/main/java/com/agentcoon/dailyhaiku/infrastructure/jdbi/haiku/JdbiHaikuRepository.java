package com.agentcoon.dailyhaiku.infrastructure.jdbi.haiku;

import com.agentcoon.dailyhaiku.domain.Haiku;
import com.agentcoon.dailyhaiku.domain.HaikuRepository;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(JdbiHaikuMapper.class)
public abstract class JdbiHaikuRepository implements HaikuRepository {

    public static final String TABLE_NAME = "haiku";

    public static final String FIELD_ID = "id";
    public static final String VALUE_ID = "id";

    public static final String FIELD_AUTHOR = "author";
    public static final String VALUE_AUTHOR = "author";

    public static final String FIELD_BODY = "body";
    public static final String VALUE_BODY = "body";

    private static final String ALL_VALUES = ":" + VALUE_AUTHOR + ", :" + VALUE_BODY;
    private static final String ALL_FIELDS = FIELD_AUTHOR + ", " + FIELD_BODY;

    @Override
    @SqlUpdate("insert into " + TABLE_NAME + " (" + ALL_FIELDS + ") values (" + ALL_VALUES + ")")
    @GetGeneratedKeys
    public abstract Long save(@BindHaiku Haiku haiku);

    @Override
    @SqlUpdate("update " + TABLE_NAME + " set " + FIELD_AUTHOR + " = :" + VALUE_AUTHOR + ", " + FIELD_BODY + " = :" + VALUE_BODY +
            " where " + FIELD_ID + " = :" + VALUE_ID)
    public abstract void update(@BindHaiku Haiku haiku);

    @Override
    @SqlUpdate("delete from " + TABLE_NAME + " where " + FIELD_ID + " = :" + VALUE_ID)
    public abstract void deleteById(@Bind(VALUE_ID) Long id);

    @Override
    @SqlQuery("select " + FIELD_ID + ", " + ALL_FIELDS + " from " + TABLE_NAME + " where " + FIELD_ID + " = :" + VALUE_ID)
    public abstract Haiku findById (@Bind(VALUE_ID) Long id);

    @Override
    @SqlQuery("select " + FIELD_ID + ", " + ALL_FIELDS + " from " + TABLE_NAME + " order by random() limit 1")
    public abstract Haiku getRandom();
}
