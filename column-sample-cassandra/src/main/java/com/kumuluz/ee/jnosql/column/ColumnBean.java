package com.kumuluz.ee.jnosql.column;

import com.kumuluz.ee.jnosql.common.DatabaseLogic;
import com.kumuluz.ee.jnosql.common.DatabaseTemplate;
import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class ColumnBean {

//	@Inject
//	private ColumnTemplate template;

	@Inject
	@Database(DatabaseType.COLUMN)
	private PersonRepository personRepository;

	@Inject
	@DatabaseLogic(DatabaseType.COLUMN)
	private DatabaseTemplate template;

//	public Person getPersonById(long id) {
//		Optional<Person> entity = template.find(Person.class, id);
//		return entity.orElse(null);
//	}

	public Person savePerson(Person person) {
		return template.insert(person);
	}

	public Long count() {
		return personRepository.count();
	}

	private void init(@Observes @Initialized(ApplicationScoped.class) Object object) {
		Person jan = new Person();
		jan.setId(1L);
		jan.setName("jan");

		Person jan2 = new Person();
		jan2.setId(2L);
		jan2.setName("jan");

		List<Person> personList = new LinkedList<>();
		personList.add(jan);
		personList.add(jan2);
		Iterable<Person> out = template.insert(personList);

		System.err.println(personRepository.count());
		personRepository.deleteById(1L);
		personRepository.deleteById(2L);
	}

}
