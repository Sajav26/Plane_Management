public class Person {
    private String firstName;
    private String surName;
    private String emailValid;

    public Person(String firstName, String surName, String emailValid) {
        this.firstName = firstName;
        this.surName = surName;
        setEmailValid(emailValid);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return this.surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmailValid() {
        return this.emailValid;
    }

    public void setEmailValid(String emailValid) {
        if (isValidEmail(emailValid)) {
            this.emailValid = emailValid;
        } else {
            System.out.println("Invalid email format");
            this.emailValid = "Invalid email";
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }

    public void printInfo() {
        System.out.println("Firstname: " + this.firstName);
        System.out.println("Surname: " + this.surName);
        System.out.println("Valid Email: " + this.emailValid);
    }
}
