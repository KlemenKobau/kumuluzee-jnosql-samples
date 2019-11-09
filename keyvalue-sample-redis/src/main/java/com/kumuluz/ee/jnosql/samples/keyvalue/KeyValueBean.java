package com.kumuluz.ee.jnosql.samples.keyvalue;

import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;
import org.jnosql.artemis.PreparedStatement;
import org.jnosql.artemis.key.KeyValueTemplate;
import org.jnosql.diana.api.key.BucketManager;
import org.jnosql.query.Query;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class KeyValueBean {

	@Inject
	private KeyValueTemplate predloga;

	@Inject
	@Database(DatabaseType.KEY_VALUE)
	private RepozitorijLjudi repozitorijLjudi;

//	public Person getPersonById(long id) {
//		Optional<Person> entity = keyValue.get(id, Person.class);
//		return entity.orElse(null);
//	}
//
//	public Person savePerson(Person person) {
//		return keyValue.put(person);
//	}

	private void obZagonu(@Observes @Initialized(ApplicationScoped.class) Object niPomembno) {
		Oseba jan = new Oseba();
		jan.setId(1L);
		jan.setIme("jan");

		Oseba domen = new Oseba();
		domen.setId(2L);
		domen.setIme("domen");

		List<Oseba> seznamLjudi = new LinkedList<>();
		seznamLjudi.add(jan);
		seznamLjudi.add(domen);
		predloga.put(seznamLjudi);

		PreparedStatement ukaz = predloga.prepare("remove @id", Person.class);
		ukaz.bind("id", 1L);
		ukaz.getSingleResult();

		repozitorijLjudi.findById(1L).ifPresentOrElse(oseba -> System.err.println(oseba.getIme()),() -> System.err.println("Osebe ni"));
		repozitorijLjudi.findById(2L).ifPresentOrElse(oseba -> System.err.println(oseba.getIme()),() -> System.err.println("Osebe ni"));
		repozitorijLjudi.deleteById(2L);

	}
}
