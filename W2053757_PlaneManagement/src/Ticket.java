import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class Ticket {
    private String row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(String row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getSeat(){
        return seat;
    }

    public void setSeat(int seat){
        this.seat = seat;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public Person getPerson(){
        return person;
    }

    public void  setPerson(Person person){
        this.person = person;
    }

    public void printTicketInfo() {
        System.out.println("\nTicket Information: ");
        System.out.println("Row: "+ row);
        System.out.println("Seat: "+ seat);
        System.out.println("Price: £"+ price);
        System.out.println("Passenger Information: ");
        person.printInfo();
    }

    public void saveToFile(){
        String fileName = row + seat + ".txt";
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileName))){
            writer.println("Ticket Information: ");
            writer.println("Row: "+row);
            writer.println("Seat: "+seat);
            writer.println("Price: £"+ price);
            writer.println();
            writer.println("Passenger Information: ");
            writer.println("FirstName: "+ person.getFirstName());
            writer.println("SurName: "+ person.getSurName());
            writer.println("Valid Email: "+ person.getEmailValid());
            System.out.println("Ticket saved to file: "+ fileName);
        } catch (IOException e){
            System.out.println("Error occurred while saving ticket to file: "+ e.getMessage());
        }
    }
}