import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;


public class GradeManager {
    static ArrayList<Student> students = new ArrayList<>();
    static Scanner scanner=new Scanner(System.in);
    public static void main(String[] args) {
        int choice;

        do {
            displayMenu();
            choice= scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1: 
                addStudent();
                break;

                case 2:
                displayStudents();
                break;

                case 3:
               searchStudentByName();
                break;

                case 4:
                editStudentByName();
                break;
                
                case 5:
                deleteStudentByName();
                break;

                case 6:
               saveToFile();
                break;

                case 7:
               loadFromFile();
                break;

                case 8:
                System.out.println("Exiting the program. Goodbye!");
                break;

                default:
                System.out.println("Invalid option. Please try again.");
            }
        }while(choice!=8);
        
    }

    static void displayMenu(){
        System.out.println("\n=== Student Grade Manager ===");
        System.out.println("1. Add Student");
        System.out.println("2. Display Students");
        System.out.println("3. Search Student by Name");
        System.out.println("4. Edit Student by Name");
        System.out.println("5. Delete Student by Name");  
        System.out.println("6. Save Student Data to File");  
        System.out.println("7. Load Student Data from file");  
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    static void addStudent(){
        System.out.println("Enter student name: ");
        String name = scanner.nextLine();

        System.out.println("Enter student marks: ");
        int marks = scanner.nextInt();
        scanner.nextLine();

        String grade = calculateGrade(marks);

        Student student = new Student(name,marks,grade);
        students.add(student);

        System.out.println("Student added successfully.");
    }
    static String calculateGrade(int marks) {
        if(marks >=90) return "A+";
        else if(marks >=80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 50) return "D";
        else return "F";
     }

    static void displayStudents() {
        if(students.isEmpty()) {
            System.out.println("No students added yet.");
        } else {
            System.out.println("\n --- Student List ---");
            for(Student s: students) {
                System.out.println("Name: "+s.name +", Marks: "+s.marks + ", Grade: "+ s.grade);
            }
        }
    }

    static Student searchStudent() {
        String searchName = scanner.nextLine().toLowerCase();

        for (Student s : students ) {
            if(s.name.toLowerCase().contains(searchName)) {
                return s;
            }
        }
        System.out.println("No student found with that name.");
        return null;
    }

    static void searchStudentByName() {
        System.out.println("Enter the student name to search: ");

      Student found =  GradeManager.searchStudent();

      if(found!=null) {
         System.out.println("Name: " + found.name + ", Marks: " + found.marks + ", Grade: " + found.grade);
      }
    }

    static void editStudentByName() {
        System.out.println("Enter the name of the student to edit: ");
      Student found =  GradeManager.searchStudent();

      if(found!=null) {
        System.out.println(" Current Name: " + found.name + ", Current Marks: " + found.marks );

        System.out.println("Enter new Name (leave blank to keep current): ");
        String newName = scanner.nextLine();

        if(!newName.isEmpty()) {
            found.name =newName;
        }

        System.out.println("Enter new marks (or -1 to keep current): ");
        int newMarks = scanner.nextInt();
        scanner.nextLine();

        if(newMarks>=0 && newMarks<=100) {
            found.marks=newMarks;
            found.grade=calculateGrade(newMarks);
        }

        System.out.println("Student updated successfully");
     }
  }

    static void deleteStudentByName() {
        System.out.println("Enter the name of the student to delete: ");
        Student found = GradeManager.searchStudent();

        if(found != null) {
            students.remove(found);
            System.out.println("Student deleted successfully");
        } 
    }

    static void saveToFile() {

        try {
            FileWriter writer = new FileWriter("students.txt");

            for (Student s: students) {
                writer.write("Name: "+ s.name + ", Marks: "+s.marks+", Grade: "+s.grade + "\n");
            }
            writer.close();
            System.out.println("Student data saved to 'students.txt' successfully.");
        } catch (Exception e) {
           System.out.println("error occurred while saving: "+e.getMessage());
        }
    }

    static void loadFromFile() {
        try {
            File file = new File("students.txt");
            Scanner fileScanner = new Scanner(file);

            students.clear();

            while(fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                String[] parts = line.split(", ");
                String name=parts[0].split(":")[1];
                int marks = Integer.parseInt(parts[1].split(": ")[1]);
                String grade = parts[2].split(": ")[1];

                students.add(new Student(name,marks,grade));
            }

            fileScanner.close();
            System.out.println("Student data loaded from 'student.txt' successfully");
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}