import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Book {
    private String bookID;
    private String title;
    private String author;
    private boolean isIssued;

    // Constructor
    public Book(String bookID, String title, String author) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    // Issue the book
    public void issueBook() {
        isIssued = true;
    }

    // Return the book
    public void returnBook() {
        isIssued = false;
    }

    // Getters
    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return isIssued;
    }
}

class Member {
    private String memberID;
    private String name;
    private ArrayList<Book> issuedBooks;

    // Constructor
    public Member(String memberID, String name) {
        this.memberID = memberID;
        this.name = name;
        this.issuedBooks = new ArrayList<>();
    }

    // Issue a book to the member
    public void issueBook(Book book) {
        issuedBooks.add(book);
    }

    // Return a book from the member
    public void returnBook(Book book) {
        issuedBooks.remove(book);
    }

    // Getters
    public String getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Book> getIssuedBooks() {
        return issuedBooks;
    }
}

class Library {
    private Map<String, Book> books;
    private Map<String, Member> members;

    // Constructor
    public Library() {
        books = new HashMap<>();
        members = new HashMap<>();
    }

    // Add a new book to the library
    public void addBook(Book book) {
        books.put(book.getBookID(), book);
        System.out.println("Book '" + book.getTitle() + "' added to the library.");
    }

    // Add a new member to the library
    public void addMember(Member member) {
        members.put(member.getMemberID(), member);
        System.out.println("Member '" + member.getName() + "' added to the library.");
    }

    // Issue a book to a member
    public void issueBook(String bookID, String memberID) {
        Book book = books.get(bookID);
        Member member = members.get(memberID);

        if (book != null && member != null && !book.isIssued()) {
            book.issueBook();
            member.issueBook(book);
            System.out.println("Book '" + book.getTitle() + "' issued to " + member.getName() + ".");
        } else {
            System.out.println("Cannot issue book. Either the book is already issued or member/book not found.");
        }
    }

    // Return a book from a member
    public void returnBook(String bookID, String memberID) {
        Book book = books.get(bookID);
        Member member = members.get(memberID);

        if (book != null && member != null && book.isIssued()) {
            book.returnBook();
            member.returnBook(book);
            System.out.println("Book '" + book.getTitle() + "' returned by " + member.getName() + ".");
        } else {
            System.out.println("Cannot return book. Either the book was not issued or member/book not found.");
        }
    }

    // Display all issued books and their holders
    public void getIssuedBooks() {
        System.out.println("Currently issued books:");
        for (Book book : books.values()) {
            if (book.isIssued()) {
                Member holder = members.values().stream()
                        .filter(member -> member.getIssuedBooks().contains(book))
                        .findFirst().orElse(null);
                System.out.println("Book: " + book.getTitle() + " | Issued to: " + (holder != null ? holder.getName() : "Unknown"));
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System:");
            System.out.println("1. Add New Book");
            System.out.println("2. Add New Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Issued Books");
            System.out.println("6. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Add new book
                    System.out.print("Enter Book ID: ");
                    String bookID = scanner.next();
                    System.out.print("Enter Book Title: ");
                    String title = scanner.next();
                    System.out.print("Enter Book Author: ");
                    String author = scanner.next();
                    library.addBook(new Book(bookID, title, author));
                    break;

                case 2:
                    // Add new member
                    System.out.print("Enter Member ID: ");
                    String memberID = scanner.next();
                    System.out.print("Enter Member Name: ");
                    String memberName = scanner.next();
                    library.addMember(new Member(memberID, memberName));
                    break;

                case 3:
                    // Issue book
                    System.out.print("Enter Book ID to issue: ");
                    String issueBookID = scanner.next();
                    System.out.print("Enter Member ID to issue to: ");
                    String issueMemberID = scanner.next();
                    library.issueBook(issueBookID, issueMemberID);
                    break;

                case 4:
                    // Return book
                    System.out.print("Enter Book ID to return: ");
                    String returnBookID = scanner.next();
                    System.out.print("Enter Member ID returning the book: ");
                    String returnMemberID = scanner.next();
                    library.returnBook(returnBookID, returnMemberID);
                    break;

                case 5:
                    // View issued books
                    library.getIssuedBooks();
                    break;

                case 6:
                    // Exit
                    System.out.println("Exiting the system.");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
