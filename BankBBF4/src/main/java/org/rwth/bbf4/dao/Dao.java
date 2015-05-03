package org.rwth.bbf4.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T extends Object> {
	void create(T t);
	 T get(Serializable id);
	 void update(T t);
	 void deleteById(Serializable id);
	 boolean exists(Serializable id);
	
}
