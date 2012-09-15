package pl.mjedynak;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.max;
import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.select;
import static ch.lambdaj.Lambda.sum;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LambdaJTest {

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


        assertThat(resultIteratively, is(asList(shinji, tom)));
        assertThat(resultWithLambda, is(asList(shinji, tom)));
    }

    @Test
    public void sumsAge() {
        int resultIteratively = 0;
        for (Person person : people) {
            resultIteratively += person.getAge();
        }

        int resultWithLambda = sum(people, on(Person.class).getAge());

        assertThat(resultIteratively, is(75));
        assertThat(resultWithLambda, is(75));
    }

}
