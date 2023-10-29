import java.util.ArrayList;
import java.util.Date; // Import Date class
import java.util.List;

// Enum for booking status
enum BookingStatus {
    REQUESTED, PENDING, CONFIRMED, CHECKED_IN, CANCELED, ABANDONED
}

// Class representing a Movie
class Movie {
    private String title;
    private String description;
    private int durationInMinutes;

    public Movie(String title, String description, int durationInMinutes) {
        this.title = title;
        this.description = description;
        this.durationInMinutes = durationInMinutes;
    }

    public String getTitle() {
        return title;
    }
}

// Class representing a Show
class Show {
    private Movie movie;
    private Date startTime;
    private int totalSeats;
    private List<Booking> bookings;

    public Show(Movie movie, Date startTime, int totalSeats) {
        this.movie = movie;
        this.startTime = startTime;
        this.totalSeats = totalSeats;
        this.bookings = new ArrayList<>();
    }

    public boolean isAvailable() {
        return bookings.size() < totalSeats;
    }

    public boolean makeBooking(Customer customer, int numberOfSeats) {
        if (isAvailable() && numberOfSeats > 0) {
            Booking booking = new Booking(this, customer, numberOfSeats);
            bookings.add(booking);
            return true;
        }
        return false;
    }
}

// Class representing a Customer
class Customer {
    private String name;
    private List<Booking> bookings;

    public Customer(String name) {
        this.name = name;
        this.bookings = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}

// Class representing a Booking
class Booking {
    private Show show;
    private Customer customer;
    private int numberOfSeats;
    private BookingStatus status;

    public Booking(Show show, Customer customer, int numberOfSeats) {
        this.show = show;
        this.customer = customer;
        this.numberOfSeats = numberOfSeats;
        this.status = BookingStatus.REQUESTED;
    }

    public void confirm() {
        status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        status = BookingStatus.CANCELED;
    }
}

// Reservation System
class ReservationSystem {
    private List<Movie> movies;
    private List<Show> shows;
    private List<Customer> customers;

    public ReservationSystem() {
        this.movies = new ArrayList<>();
        this.shows = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addShow(Show show) {
        shows.add(show);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Show findShowByMovieAndTime(Movie movie, Date startTime) {
        for (Show show : shows) {
            if (show.movie == movie && show.startTime.equals(startTime)) {
                return show;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        ReservationSystem reservationSystem = new ReservationSystem();

        Movie movie1 = new Movie("Movie 1", "Description 1", 120);
        Movie movie2 = new Movie("Movie 2", "Description 2", 110);

        reservationSystem.addMovie(movie1);
        reservationSystem.addMovie(movie2);

        Show show1 = new Show(movie1, new Date(), 100);
        Show show2 = new Show(movie1, new Date(), 80);
        Show show3 = new Show(movie2, new Date(), 120);

        reservationSystem.addShow(show1);
        reservationSystem.addShow(show2);
        reservationSystem.addShow(show3);

        Customer customer1 = new Customer("Customer 1");
        Customer customer2 = new Customer("Customer 2");

        reservationSystem.addCustomer(customer1);
        reservationSystem.addCustomer(customer2);

        // Making a booking
        Show targetShow = reservationSystem.findShowByMovieAndTime(movie1, new Date());
        if (targetShow != null) {
            boolean success = targetShow.makeBooking(customer1, 2);
            if (success) {
                Booking booking = customer1.bookings.get(0);
                booking.confirm();
                System.out.println("Booking confirmed for " + customer1.getName());
            } else {
                System.out.println("Booking failed.");
            }
        }
    }
}
