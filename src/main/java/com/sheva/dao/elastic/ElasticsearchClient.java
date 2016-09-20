package com.sheva.dao.elastic;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;

/**
 * Created by vlad on 17.09.16.
 */
@Component
public class ElasticsearchClient {
    private static final Logger log = Logger.getLogger(ElasticsearchClient.class);

    @Value("${elasticsearch.cluster.name}")
    private String clusteName;

    @Value("${elasticsearch.node.port}")
    private int port;

    @Value("${elasticsearch.node.host}")
    private String host;

    @Value("${elasticsearch.node.index}")
    private String index;

    private Client client;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing Elasticsearch client. cluster name is " + index);
            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", clusteName).build();
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        } catch (UnknownHostException e) {
            log.error("Elasticsearch not initializing client. cluster name is " + index);
            e.printStackTrace();
        }
    }

    public Client getClient() {
        return client;
    }

    public String getIndex() {
        return index;
    }
}
