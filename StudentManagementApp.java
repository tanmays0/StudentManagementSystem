import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class Student
{
    private String name;
    private String rollNumber;
    private String grade;
    public Student(String name, String rollNumber, String grade)
    {
        this.name=name;
        this.rollNumber=rollNumber;
        this.grade=grade;
    }
    public String getName()
    {
        return name;
    }
    public String getRollNumber()
    {
        return rollNumber;
    }
    public String getGrade()
    {
        return grade;
    }
    @Override
    public String toString()
    {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}
class StudentManagementSystem
{
    private List<Student> students;
    private final String filePath = "students.txt";
    public StudentManagementSystem()
    {
        students=new ArrayList<>();
        loadStudents();
    }
    public void addStudent(Student student)
    {
        students.add(student);
        saveStudents();
        System.out.println("Student added successfully.");
    }
    public void removeStudent(String rollNumber)
    {
        Student studentToRemove = null;
        for(Student student : students)
        {
            if(student.getRollNumber().equals(rollNumber))
            {
                studentToRemove=student;
                break;
            }
        }
        if(studentToRemove != null)
        {
            students.remove(studentToRemove);
            saveStudents();
            System.out.println("Student removed successfully.");
        }
        else
        {
            System.out.println("Student not found.");
        }
    }
    public Student searchStudent(String rollNumber)
    {
        for(Student student : students)
        {
            if(student.getRollNumber().equals(rollNumber))
            {
                return student;
            }
        }
        return null;
    }
    public void displayAllStudents()
    {
        if(students.isEmpty())
        {
            System.out.println("No students found.");
        }
        else
        {
            System.out.println("List of Students:");
            for(Student student : students)
            {
                System.out.println(student);
            }
        }
    }
    private void loadStudents()
    {
        try(BufferedReader br=new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while((line=br.readLine()) != null)
            {
                String[] data=line.split(",");
                if(data.length==3)
                {
                    students.add(new Student(data[0], data[1], data[2]));
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }
    private void saveStudents()
    {
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(filePath)))
        {
            for(Student student : students)
            {
                bw.write(student.getName() + "," + student.getRollNumber() + "," + student.getGrade());
                bw.newLine();
            }
        }
        catch (IOException e)
        {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }
}
public class StudentManagementApp
{
    public static void main(String[]args)
    {
        Scanner scanner=new Scanner(System.in);
        StudentManagementSystem sms=new StudentManagementSystem();
        while(true)
        {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");
            int choice = getUserChoice(scanner);
            switch(choice)
            {
                case 1:
                    addStudent(scanner, sms);
                    break;
                case 2:
                    removeStudent(scanner, sms);
                    break;
                case 3:
                    searchStudent(scanner, sms);
                    break;
                case 4:
                    sms.displayAllStudents();
                    break;
                case 5:
                    System.out.println("Exiting the application. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static int getUserChoice(Scanner scanner)
    {
        while(true)
        {
            try
            {
                return Integer.parseInt(scanner.nextLine());
            }
            catch(NumberFormatException e)
            {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    private static void addStudent(Scanner scanner, StudentManagementSystem sms)
    {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter roll number: ");
        String rollNumber = scanner.nextLine().trim();
        System.out.print("Enter grade: ");
        String grade = scanner.nextLine().trim();
        if(name.isEmpty() || rollNumber.isEmpty() || grade.isEmpty())
        {
            System.out.println("All fields are required. Please try again.");
            return;
        }
        Student student=new Student(name, rollNumber, grade);
        sms.addStudent(student);
    }
    private static void removeStudent(Scanner scanner, StudentManagementSystem sms)
    {
        System.out.print("Enter roll number of the student to remove: ");
        String rollNumber=scanner.nextLine().trim();
        sms.removeStudent(rollNumber);
    }
    private static void searchStudent(Scanner scanner, StudentManagementSystem sms)
    {
        System.out.print("Enter roll number of the student to search: ");
        String rollNumber=scanner.nextLine().trim();
        Student student=sms.searchStudent(rollNumber);
        if(student != null)
        {
            System.out.println("Student found: " + student);
        }
        else
        {
            System.out.println("Student not found.");
        }
    }
}