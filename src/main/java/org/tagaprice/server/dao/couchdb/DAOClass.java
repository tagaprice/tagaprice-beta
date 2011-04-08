package org.tagaprice.server.dao.couchdb;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Server;
import org.jcouchdb.db.ServerImpl;
import org.tagaprice.server.dao.IDAOClass;
import org.tagaprice.shared.entities.ASimpleEntity;

public class DAOClass<T extends ASimpleEntity> implements IDAOClass<T> {
	protected Database m_db;
	Class<? extends T> m_class;
	
	protected DAOClass(Class<? extends T> classObject, String dbName) {
		Server server = new ServerImpl("localhost");
		// TODO use server.setCredentials() to authenticate
		if (!server.listDatabases().contains(dbName)) {
			server.createDatabase(dbName);
		}
		m_db = new Database(server, dbName);
		m_class = classObject;
	}

	@Override
	public T create(T entity) {
		m_db.createDocument(entity);
		return entity;
	}

	@Override
	public T get(String id, String revision) {
		return m_db.getDocument(m_class, id);
	}
	
	@Override
	public T get(String id) {
		return get(id, null);
	}

	@Override
	public T update(T entity) {
		m_db.createDocument(entity);
		return entity;
	}

	@Override
	public void delete(T entity) {
		m_db.delete(entity);
	}
}
