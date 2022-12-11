package com.hccs.Project2Application.project2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StudentController {

	    public static StudentList FileReader(String fileName){
	        StudentList list = new StudentList();
	        try {
	            FileReader fileReader = new FileReader(fileName);
	            BufferedReader br = new BufferedReader(fileReader);

	            br.readLine();
	            String line;
	          
	            while((line = br.readLine())!= null){
	            	
	                    System.out.println(line);
	                String[] tokens = line.split(",");
	                 Student student = new Student(Integer.parseInt(tokens[0]),tokens[1],Double.parseDouble(tokens[2]),tokens[3],tokens[4]);
                   	 list.addStudent(student);
	                
	            }
	            br.close();
	            fileReader.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }

	    StudentList list = FileReader("student.txt");

	    /**
	     *
	     * @return all student
	     */
	    @GetMapping()
	    public ArrayList<Student> studentList(){
	        return list.getList();
	    }

	    /**
	     *
	     * @param name
	     * @return set of students with the same name
	     */
	    @GetMapping("/name/{name}")
	    public ArrayList<Student> searchResults(@PathVariable String name) {
	        ArrayList<Student> resultSet = new ArrayList<>();
	        list.getList().forEach(e -> {
	            if (e.getFirstName().equalsIgnoreCase(name.trim())) {
	                resultSet.add(e);
	            }
	        });
	        return resultSet;
	    }

	    /**
	     * @param gpa
	     * @param gender
	     * @return set of students
	     */
	    @GetMapping("/student")
	    public ArrayList<Student> gpaAndGender(@RequestParam String gpa, @RequestParam String gender) {
	        ArrayList<Student> resultSet = new ArrayList<>();
	        list.getList().forEach(e -> {
	            if ((e.getGpa() == Double.parseDouble(gpa)) && (e.getGender().compareToIgnoreCase(gender.trim()) == 0)) {
	                resultSet.add(e);
	            }
	        });
	        resultSet.forEach(e-> System.out.println(e));
	        return resultSet;
	    }

	    /**
	     * @return average gpa
	     */
	    @GetMapping("/gpa")
	    public double avgGPA() {
	        double totalGPA = list.getList().stream().mapToDouble(Student::getGpa).sum();
	        return (double) totalGPA / list.getList().size();
	    }
}
