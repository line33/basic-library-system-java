import java.util.*;

/**
 * @author Amadi Ifeanyi
 * @class Main
 */
public class Main {
    static List<Book> bookStorage = new ArrayList<>();
    static Scanner scanner;
    static final String ADD_BOOKS = "Add Books";
    static final String RETURN_BOOKS = "Return Books";
    static final String BORROW_BOOKS = "Borrow Books";
    static String screenMessage = "Welcome to our basic library. What would you like to do?";

    public static void main(String[] args) {

        Main.scanner = new Scanner(System.in);
        startupScreen();
    }

    public static void startupScreen()
    {
        String selection = getStartupSelection();

        // update screen message
        screenMessage = "What would you like to do next?";

        if (selection.equalsIgnoreCase(ADD_BOOKS))
        {
            addBook();
        }
        else if (selection.equalsIgnoreCase(BORROW_BOOKS))
        {
            borrowBook();
        }
        else if (selection.equalsIgnoreCase(RETURN_BOOKS))
        {
            returnBook();
        }
    }

    public static String getStartupSelection()
    {
        System.out.println(screenMessage);
        String[] options = {ADD_BOOKS, BORROW_BOOKS, RETURN_BOOKS, "Exit"};
        String selection = "";

        for (int index=0; index < options.length; index++)
        {
            System.out.println((index+1) + ". " + options[index]);
        }

        System.out.print("(1 - "+options.length+") : ");

        try{

            int selectionInt = scanner.nextInt();

            selection = options[selectionInt-1];

            // exit application
            if (selection.equalsIgnoreCase("EXIT"))
            {
                System.out.println("Thank you and good bye!");
            }
        }
        catch (Exception e)
        {
            System.out.println("Invalid Selection. Please try again");
        }

        return selection;
    }

    public static void addBook()
    {
        String bookTitle = "";
        while(bookTitle.isEmpty())
        {
            System.out.print("Enter book title: ");
            String entry = scanner.next();

            if (!entry.trim().isEmpty())
            {
                bookTitle = entry;
            }
        }

        // get author
        String authorName = "";
        while (authorName.isEmpty())
        {
            System.out.print("Enter author name: ");
            String entry = scanner.next();

            if (entry.trim().isEmpty())
            {
                System.out.println("Invalid author name.");
            }
            else
            {
                authorName = entry;
            }
        }

        // get quantity
        int quanity = 0;
        while (quanity == 0)
        {
            try
            {
                System.out.print("Enter quantity: ");
                quanity = scanner.nextInt();
                if (quanity == 0)
                {
                    System.out.println("Quantity cannot be zero.");
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid quantity.");
            }
        }

        boolean canAdd = true;

        // check if book already exists.
        for (int index = 0; index < bookStorage.size(); index++)
        {
            Book book = bookStorage.get(index);
            if (book.bookTitle.equalsIgnoreCase(bookTitle))
            {
                canAdd = false;

                // update quantity
                book.quantity += quanity;

                // update book storage
                bookStorage.set(index, book);

                break;
            }
        }

        // add to books
        if (canAdd) bookStorage.add(new Book(bookTitle, authorName, quanity));

        // all good
        System.out.printf("Book '%s' has been %s successfully!%n%n", bookTitle, (canAdd ? "added" : "updated"));

        // load startup screen
        startupScreen();
    }

    public static void borrowBook()
    {
        String bookTitle = "";
        while(bookTitle.isEmpty())
        {
            System.out.print("Enter book title: ");
            String entry = scanner.next();

            if (!entry.trim().isEmpty())
            {
                bookTitle = entry;
            }
        }

        // get number of books
        int number = 0;
        while (number == 0)
        {
            try
            {
                System.out.print("Enter number of books to borrow: ");
                number = scanner.nextInt();
                if (number == 0) System.out.println("Number of books cannot be zero.");
            }
            catch (Exception e)
            {
                System.out.println("Invalid number.");
            }
        }

        boolean bookExists = false;

        // check if book exists
        for (int index = 0; index < bookStorage.size(); index++)
        {
            Book book = bookStorage.get(index);
            if (book.bookTitle.equalsIgnoreCase(bookTitle)) {
                bookExists = true;
                // Check if the requested number of books is available in the library.
                if (number > book.quantity)
                {
                    System.out.println("We currently do not have the total number of books requested.");
                }
                else
                {
                    // updated quantity
                    book.quantity -= number;
                    bookStorage.set(index, book);

                    // all good
                    System.out.printf("(%d) '%s' book[s] has been borrowed to you%n%n", number, bookTitle);
                }

                break;
            }
        }

        if (!bookExists) System.out.printf("Book '%s' does not exists%n%n", bookTitle);

        // load startup screen
        startupScreen();
    }

    public static void returnBook(){

        String bookTitle = "";
        while(bookTitle.isEmpty())
        {
            System.out.print("Enter book title: ");
            String entry = scanner.next();

            if (!entry.trim().isEmpty())
            {
                bookTitle = entry;
            }
        }

        boolean bookExists = false;

        // check if book belong to the book library
        for (int index = 0; index < bookStorage.size(); index++)
        {
            Book book = bookStorage.get(index);
            if (book.bookTitle.equalsIgnoreCase(bookTitle))
            {
                bookExists = true;

                // get number of books
                int number = 0;
                while (number == 0)
                {
                    try
                    {
                        System.out.print("Enter number of books to return: ");
                        number = scanner.nextInt();
                        if (number == 0) System.out.println("Number of books cannot be zero.");
                    }
                    catch (Exception e)
                    {
                        System.out.println("Invalid number.");
                    }
                }

                // If they do, update the quantity and display a success message.
                // update quantity
                book.quantity += number;

                // update book storage
                bookStorage.set(index, book);

                // all good
                System.out.printf("(%d) '%s' book[s] has been returned successfully%n%n", number, bookTitle);

                break;
            }
        }

        if (!bookExists) System.out.printf("Book '%s' does not exists%n%n", bookTitle);

        // load startup screen
        startupScreen();
    }
}

class Book
{
    String bookTitle;
    String authorName;
    int quantity = 1;

    public Book(String title, String author, int quantity)
    {
        this.bookTitle = title;
        this.authorName = author;
        this.quantity = quantity;
    }
}