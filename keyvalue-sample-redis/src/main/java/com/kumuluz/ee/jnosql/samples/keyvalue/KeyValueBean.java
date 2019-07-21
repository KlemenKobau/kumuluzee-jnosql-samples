package com.kumuluz.ee.jnosql.samples.keyvalue;

import org.jnosql.artemis.key.KeyValueTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class KeyValueBean {

	@Inject
	private KeyValueTemplate keyValue;

	public Person getPersonById(long id) {
		Optional<Person> entity = keyValue.get(id, Person.class);
		return entity.orElse(null);
	}

	public Person savePerson(Person person) {
		return keyValue.put(person);
	}
}
