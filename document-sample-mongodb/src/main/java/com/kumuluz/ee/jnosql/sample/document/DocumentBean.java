package com.kumuluz.ee.jnosql.sample.document;

import org.jnosql.artemis.document.DocumentTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class DocumentBean {

	@Inject
	private DocumentTemplate template;

	public Person getPersonById(long id) {
		Optional<Person> entity = template.find(Person.class, id);
		return entity.orElse(null);
	}

	public Person savePerson(Person person) {
		return template.insert(person);
	}
}
