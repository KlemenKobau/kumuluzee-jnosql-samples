/*
 *  Copyright (c) 2014-2017 Kumuluz and/or its affiliates
 *  and other contributors as indicated by the @author tags and
 *  the contributor list.
 *
 *  Licensed under the MIT License (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  https://opensource.org/licenses/MIT
 *
 *  The software is provided "AS IS", WITHOUT WARRANTY OF ANY KIND, express or
 *  implied, including but not limited to the warranties of merchantability,
 *  fitness for a particular purpose and noninfringement. in no event shall the
 *  authors or copyright holders be liable for any claim, damages or other
 *  liability, whether in an action of contract, tort or otherwise, arising from,
 *  out of or in connection with the software or the use or other dealings in the
 *  software. See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.kumuluz.ee.jnosql.samples.keyvalue;

import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;
import org.jnosql.artemis.PreparedStatement;
import org.jnosql.artemis.key.KeyValueTemplate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

/**
 * @author Klemen Kobau
 */
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
