import java.util.Scanner;

public class DataLayerMenu 
{
    Scanner scanner = new Scanner(System.in);
    int choice;
    String username;
    String password;
    String fName;
    String lName;
    String email;
    String phone;
    String location;

    public void Menu()
    {
        System.out.print("Login\n1. Faculty\n2. Student\n3. Guest\n4. Exit\nSelection: ");
        choice = scanner.nextInt();
        System.out.println("");

        switch (choice) 
        {
            case 1:
                System.out.print("1. Login\n2. Sign Up\nSelection: ");
                choice = scanner.nextInt();
                System.out.println("");
                switch (choice) 
                {
                    case 1:
                        System.out.print("Faculty\nUsername: ");
                        username = scanner.next();
                        System.out.print("Password: ");
                        password = scanner.next();
                        try
                        {
                            System.out.println("SUCCESS in Faculty Login");
                        }
                        catch (Exception e)
                        {
                            System.out.println("ERROR in Faculty Login");
                        }
                    break;

                    case 2:
                        //add abstracts and skills depending on method 
                        System.out.print("Faculty\nFirst Name: ");
                        fName = scanner.next();
                        System.out.print("Last Name: ");
                        lName = scanner.next();
                        System.out.print("Email: ");
                        email = scanner.next();
                        System.out.print("Phone Number: ");
                        phone = scanner.next();
                        System.out.print("Location: ");
                        location = scanner.next();
                        System.out.print("Username: ");
                        username = scanner.next();
                        System.out.print("Password: ");
                        password = scanner.next();
                        try
                        {
                            System.out.println("SUCCESS in Faculty Sign Up");
                        }
                        catch (Exception e)
                        {
                            System.out.println("ERROR in Faculty Sign Up");
                        }
                    break;
                
                    default:
                        System.out.println("Please enter a valid option");
                    break;
                }
            break;

            case 2:
            break;

            case 3:
            break;

            case 4:
                System.out.println("Goodbye!\n");
                scanner.close();
                System.exit(0);
            break;
        
            default:
                System.out.println("Please enter a valid menu option");
            break;
        }
    }

    public DataLayerMenu()
    {
        Menu();
    }

    public static void main(String[] args) 
    {
        new DataLayerMenu();
    }
}
