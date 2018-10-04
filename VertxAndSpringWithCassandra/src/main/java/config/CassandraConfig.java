package config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class CassandraConfig {

    private Session session;

    private Cluster cluster;

    @Value("${cassandra.keyspace-name}")
    private String keySpace;

    @Value("${cassandra.contact-points}")
    private String contact;

    @Value("${cassandra.port}")
    private Integer port;

    @PostConstruct
    public CassandraConfig getCassandraConfigBean() {
        cluster = Cluster.builder()
                .addContactPoint(contact)
                .withPort(port).build();

        session = cluster.connect(keySpace);

        return this;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
