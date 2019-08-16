package com.kumuluz.ee.jnosql.column;

import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;
import org.jnosql.artemis.column.ColumnTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class ColumnBean {

	@Inject
	private ColumnTemplate template;

	@Inject
	@Database(DatabaseType.COLUMN)
	private PersonRepository personRepository;

	public Person getPersonById(long id) {
		Optional<Person> entity = template.find(Person.class, id);
		return entity.orElse(null);
	}

	public Person savePerson(Person person) {
		return template.insert(person);
	}

	public Long count() {
		return personRepository.count();
	}
}
