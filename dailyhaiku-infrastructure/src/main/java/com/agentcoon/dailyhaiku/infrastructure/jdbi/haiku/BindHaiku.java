package com.agentcoon.dailyhaiku.infrastructure.jdbi.haiku;

import com.agentcoon.dailyhaiku.domain.Haiku;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.BinderFactory;
import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

import java.lang.annotation.*;

@BindingAnnotation(BindHaiku.BindHaikuFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindHaiku {

    class BindHaikuFactory implements BinderFactory {

        @Override
        public Binder build(Annotation annotation) {

            return (Binder<BindHaiku, Haiku>) (q, bind, haiku) -> {
                q.bind(JdbiHaikuRepository.VALUE_ID, haiku.getId());
                q.bind(JdbiHaikuRepository.VALUE_AUTHOR, haiku.getAuthor());
                q.bind(JdbiHaikuRepository.VALUE_BODY, haiku.getBody());
            };
        }
    }
}
