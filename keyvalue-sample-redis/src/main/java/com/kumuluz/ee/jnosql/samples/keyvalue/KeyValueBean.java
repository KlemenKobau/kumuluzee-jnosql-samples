package com.kumuluz.ee.jnosql.samples.keyvalue;

import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;
import org.jnosql.artemis.PreparedStatement;
import org.jnosql.artemis.key.KeyValueTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class KeyValueBean {

	@Inject
	private KeyValueTemplate keyValue;

	@Inject
	@Database(DatabaseType.KEY_VALUE)
	private PeopleRepository peopleRepository;

	public Optional<Person> findById(Long id){
		return peopleRepository.findById(id);
	}

	public void addPeople(Collection<Person> people){
		keyValue.put(people);
	}

	public void deleteById(Long id) {
		peopleRepository.deleteById(id);
	}

	public void useStatement(Long id) {
		PreparedStatement statement = keyValue.prepare("remove @id", Person.class);
		statement.bind("id", id);
		statement.getSingleResult();
	}
}
