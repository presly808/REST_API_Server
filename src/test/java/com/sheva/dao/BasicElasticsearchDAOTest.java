package com.sheva.dao;

import com.sheva.dao.elastic.ElasticsearchClient;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteAction;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by vlad on 17.09.16.
 */
public class BasicElasticsearchDAOTest {
    @Autowired
    private ElasticsearchClient client;

    protected void truncate(String type) {
        // We need to wait the sync for elastic. it is async - so just dirty hack
        try {
            // todo
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //todo use logger
            e.printStackTrace();
        }
        SearchRequestBuilder requestBuilder = client.getClient().prepareSearch(client.getIndex()).setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_AND_FETCH).setQuery(QueryBuilders.matchAllQuery()).setNoFields();
        SearchResponse response = requestBuilder.setExplain(true).execute().actionGet();
        SearchHits hits = response.getHits();
        if (hits.getTotalHits() > 0) {
            BulkRequestBuilder bulk = client.getClient().prepareBulk();
            for (SearchHit hit : hits) {
                DeleteRequestBuilder builder = new DeleteRequestBuilder(client.getClient(), DeleteAction.INSTANCE);
                builder.setId(hit.getId());
                builder.setIndex(client.getIndex());
                builder.setType(type);
                bulk.add(builder.request());
            }
            bulk.get();
        }
    }
}
