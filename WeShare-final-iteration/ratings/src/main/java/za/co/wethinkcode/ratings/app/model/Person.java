package za.co.wethinkcode.ratings.app.model;

import za.co.wethinkcode.ratings.util.Strings;

import java.time.LocalDate;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * I represent a person who participates in sharing expenses. I am identified by an email address which
 * uniquely identifies me. (No other Persons may use the same email address.)
 * <p>
 * My sole responsibility is to identify an expense participant.
 */
public class Person {

    private final String email;
    private double expenses;
    private double allClaims;
    private double unsettledClaims;
    private double settledClaims;
    private double nettExpense;
    private LocalDate timeStamp;

    public Person(String email, double expenses, double allClaims, double unsettledClaims, double settledClaims, double nettExpense, LocalDate timeStamp) {
        this.email = email;
        this.expenses = expenses;
        this.allClaims = allClaims;
        this.unsettledClaims = unsettledClaims;
        this.settledClaims = settledClaims;
        this.nettExpense = nettExpense;
        this.timeStamp = timeStamp;
    }

    public String getEmail(){
        return email;
    }

    public double getExpenses(){return expenses;}

    public double getAllClaims(){return allClaims;}

    public double getUnsettledClaims(){return unsettledClaims;}

    public double getSettledClaims(){return settledClaims;}

    public double getNettExpense() {return nettExpense;}

    public LocalDate getTimeStamp() {return timeStamp;}

    @Override
    public String toString(){
        return "Person{" +
            "id='" + email + '\'' +
            '}';
    }

    @Override
    public boolean equals( Object o ){
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;
        Person person = (Person) o;
        return email.equalsIgnoreCase( person.email );
    }

    @Override
    public int hashCode(){
        return Objects.hash( email );
    }

    public String getName(){
        String pseudonym = this.email.split( "@" )[ 0 ];
        return Strings.capitaliseFirstLetter( pseudonym );
    }
}