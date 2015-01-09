package pl.mjedynak;

import com.gs.collections.api.list.MutableList;
import com.gs.collections.impl.list.mutable.FastList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static ch.lambdaj.Lambda.sum;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CollectionsTest {

    private List<Person> people;
    private Person robin;
    private Person shinji;
    private Person tom;

    @Before
    public void setUp() {
        robin = new Person("Robin van Persie", 29);
        shinji = new Person("Shinji Kagawa", 23);
        tom = new Person("Tom Cleverley", 23);
        people = asList(robin, shinji, tom);
    }

    @Test
    public void selectsByAge() {
        List<Person> resultIteratively = new ArrayList<>();
        for (Person person : people) {
            if (person.getAge() == 23) {
                resultIteratively.add(person);
            }
        }

        List<Person> resultWithLambda = select(people, having(on(Person.class).getAge(), equalTo(23)));

        List<Person> resultJava8 = people.stream().filter(p -> p.getAge() == 23).collect(toList());

        MutableList<Person> gsPeople = FastList.newList(people);
        MutableList<Person> resultWithGSCollections = gsPeople.select(p -> p.getAge() == 23);

        List<Person> expectedResult = asList(shinji, tom);
        assertThat(resultIteratively, is(expectedResult));
        assertThat(resultWithLambda, is(expectedResult));
        assertThat(resultJava8, is(expectedResult));
        assertThat(resultWithGSCollections, is(expectedResult));
    }

    @Test
    public void sumsAge() {
        int resultIteratively = 0;
        for (Person person : people) {
            resultIteratively += person.getAge();
        }

        int resultWithLambda = sum(people, on(Person.class).getAge());

        int resultWithJava8 = people.stream().mapToInt(Person::getAge).sum();

        MutableList<Person> gsPeople = FastList.newList(people);
        int resultWithGSCollections = (int) gsPeople.sumOfInt(Person::getAge);

        int expectedResult = 75;
        assertThat(resultIteratively, is(expectedResult));
        assertThat(resultWithLambda, is(expectedResult));
        assertThat(resultWithJava8, is(expectedResult));
        assertThat(resultWithGSCollections, is(expectedResult));
    }

}
