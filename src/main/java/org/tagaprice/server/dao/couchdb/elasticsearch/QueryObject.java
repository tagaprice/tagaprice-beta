package org.tagaprice.server.dao.couchdb.elasticsearch;

import org.svenson.JSONProperty;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.Query;
import org.tagaprice.server.dao.couchdb.elasticsearch.query.QueryWrapper;

public class QueryObject {
	private Integer m_from = 0;
	private Integer m_size = 10;
	private Query m_query = null;
	private String m_fields[] = null;
	
	public QueryObject() {}
	
	public QueryObject query(Query query) {
		m_query = query;
		return this;
	}
	
	public QueryObject from(int from) {
		m_from = from;
		return this;
	}
	
	public QueryObject size(int size) {
		m_size = size;
		return this;
	}
	
	public QueryObject fields(String... fields) {
		m_fields = fields;
		return this;
	}
	
	@JSONProperty(ignore=true)
	public Query getQuery() {
		return m_query;
	}
	
	@JSONProperty("query")
	public QueryWrapper getQueryWrapper() {
		return new QueryWrapper(m_query);
	}
	
	@JSONProperty(ignoreIfNull=true)
	public String[] getFields() {
		return m_fields;
	}
	
	@JSONProperty(ignoreIfNull=true)
	public int getFrom() {
		return m_from;
	}
	
	@JSONProperty(ignoreIfNull=true)
	public Integer getSize() {
		return m_size;
	}
}
