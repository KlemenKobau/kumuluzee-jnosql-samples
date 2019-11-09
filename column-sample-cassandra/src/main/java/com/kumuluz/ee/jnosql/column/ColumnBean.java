package com.kumuluz.ee.jnosql.column;

import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;
import org.jnosql.artemis.PreparedStatement;
import org.jnosql.artemis.column.ColumnTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class ColumnBean {

	@Inject
	private ColumnTemplate predloga;

	@Inject
	@Database(DatabaseType.COLUMN)
	private RepozitorijOseb repozitorijLjudi;

//	@Inject
//	@DatabaseLogic(DatabaseType.COLUMN)
//	private DatabaseTemplate template;

//	public Person getPersonById(long id) {
//		Optional<Person> entity = template.find(Person.class, id);
//		return entity.orElse(null);
//	}

	public Oseba savePerson(Oseba oseba) {
		return predloga.insert(oseba);
	}

	public Long count() {
		return repozitorijLjudi.count();
	}

	private void init(@Observes @Initialized(ApplicationScoped.class) Object object) {
		Oseba jan = new Oseba();
		jan.setId(1L);
		jan.setIme("jan");

		Oseba domen = new Oseba();
		domen.setId(2L);
		domen.setIme("domen");
		domen.setPhones(List.of("phone1"));

		List<Oseba> seznamLjudi = new LinkedList<>();
		seznamLjudi.add(jan);
		seznamLjudi.add(domen);
		Iterable<Oseba> out = predloga.insert(seznamLjudi);
		out.forEach(oseba -> System.err.println(oseba.getId()));

		PreparedStatement ukaz = predloga.prepare("delete from Oseba where id=@id"); // spremenimo poizvedbo
		ukaz.bind("id", 1L);
		ukaz.getSingleResult();

		repozitorijLjudi.findById(1L).ifPresentOrElse(oseba -> System.err.println(oseba.getIme()),() -> System.err.println("Osebe ni"));
		repozitorijLjudi.findById(2L).ifPresentOrElse(oseba -> System.err.println(oseba.getIme()),() -> System.err.println("Osebe ni"));
		repozitorijLjudi.deleteById(1L);
		repozitorijLjudi.deleteById(2L);

	}

}
