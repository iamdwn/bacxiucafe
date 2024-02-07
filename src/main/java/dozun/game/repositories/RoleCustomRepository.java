package dozun.game.repositories;

import com.zaxxer.hikari.HikariDataSource;
import dozun.game.entities.RoleEntity;
import dozun.game.entities.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class RoleCustomRepository {
    @Autowired
    HikariDataSource dataSource;

    @PersistenceContext
    private EntityManager entityManager;

    public List<RoleEntity> getRole(UserEntity user) {
        String databaseName = "";
        String pattern = "/([^/]*)$";
        String connectionUrl = dataSource.getJdbcUrl().toString();
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(connectionUrl);
        if (m.find()) {
            databaseName = m.group(1);
    }
            StringBuilder sql = new StringBuilder().append("SELECT " + databaseName + ".role.name as name FROM " + databaseName + ".user " +
                    "join " + databaseName + ".user_role on " + databaseName + ".user.id = " + databaseName + ".user_role.users_id " +
                    "join " + databaseName + ".role on " + databaseName + ".role.id = " + databaseName + ".user_role.roles_id");
            sql.append(" Where 1=1");
            if (user.getUsername() != null) {
                sql.append(" and username = :username");
            }
            NativeQuery<RoleEntity> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());

            if (user.getUsername() != null) {
                query.setParameter("username", user.getUsername());
            }
            query.addScalar("name", StandardBasicTypes.STRING);
            query.setResultTransformer(Transformers.aliasToBean(RoleEntity.class));
            return query.list();
        }
    }
