package com.kumuluz.ee.jnosql.column;

import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;
import org.jnosql.artemis.PreparedStatement;
import org.jnosql.artemis.column.ColumnTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ColumnBean {

	@Inject
	private ColumnTemplate column;

	@Inject
	@Database(DatabaseType.COLUMN)
	private PeopleRepository peopleRepository;

	public Person savePerson(Person person) {
		return column.insert(person);
	}

	public Long count() {
		return peopleRepository.count();
	}

	public Optional<Person> findById(Long id){
		return peopleRepository.findById(id);
	}

	public void addPeople(Collection<Person> people){
		column.insert(people);
	}

	public void deleteById(Long id) {
		peopleRepository.deleteById(id);
	}

	public void useStatement(Long id) {
		PreparedStatement statement = column.prepare("remove @id");
		statement.bind("id", id);
		statement.getSingleResult();
	}
}
