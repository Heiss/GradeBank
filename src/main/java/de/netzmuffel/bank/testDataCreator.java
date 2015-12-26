/*
 * 
 */
package de.netzmuffel.bank;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;

import de.netzmuffel.bank.model.Course;
import de.netzmuffel.bank.model.CourseStudent;
import de.netzmuffel.bank.model.Grade;
import de.netzmuffel.bank.model.Student;
import de.netzmuffel.bank.model.Teacher;
import de.netzmuffel.bank.model.Topic;
import de.netzmuffel.bank.model.enumerator.Subject;

/**
 * The Class testDataCreator.
 */
public class testDataCreator {
	
	public Teacher te1, te2;
	public Student st1, st2;
	public Course co1, co2;
	public Topic to1, to2;
	public Grade gr1, gr2, gr3, gr4, gr5;
	public CourseStudent cs1, cs2, cs3;

	/**
	 * Instantiates a new test data creator.
	 */
	public testDataCreator() {
		createStudents();
		createTeacher();
		createTopic();
		createCourse();
		createCourseStudents();
		createGrades();
	}

	/**
	 * Creates the topic.
	 */
	private void createTopic() {
		to1 = new Topic("Klausur");
		to2 = new Topic("Lineare Gleichungen");
	}

	/**
	 * Creates the grades.
	 */
	private void createGrades() {
		gr1 = new Grade(3, cs1, to1);
		gr2 = new Grade(10, cs2, to1);
		gr3 = new Grade(1, cs3, to2);
		gr4 = new Grade(-1, cs1, to2);
		gr5 = new Grade(2, cs2, to2);
	}

	/**
	 * Creates the course.
	 */
	private void createCourse() {
		co1 = new Course("10a", te1, Subject.Art, false);
		co2 = new Course("8a", te2, Subject.ComputerScience, true);
	}

	/**
	 * Creates the teacher.
	 */
	private void createTeacher() {
		te1 = new Teacher("Mustermann", "Maxi", "test3@steron.de", "0123456789");
		te2 = new Teacher("Schusterling", "Maximilian", "test4@steron.de", "0176356789");
	}

	/**
	 * Creates the students.
	 */
	private void createStudents() {
		st1 = new Student("Mustermann", "Max", "test@steron.de", "0123456789");
		st2 = new Student("Schuster", "Maxim", "test2@steron.de", "0176356789");
	}

	/**
	 * Creates the course students.
	 */
	private void createCourseStudents() {
		cs1 = new CourseStudent(co1, st1);
		cs2 = new CourseStudent(co1, st2);
		cs3 = new CourseStudent(co2, st2);
	}

	/**
	 * Exec.
	 *
	 * @param db the db
	 * @throws SQLException the SQL exception
	 */
	public void exec(Database db) throws SQLException {
		Dao<Student, Integer> daoS = db.getStudentDAO();
		Dao<Teacher, Integer> daoT = db.getTeacherDAO();
		Dao<Course, Integer> daoC = db.getCourseDAO();
		Dao<CourseStudent, Integer> daoCS = db.getCourseStudentDAO();
		Dao<Grade, Integer> daoG = db.getGradeDAO();
		Dao<Topic, Integer> daoTo = db.getTopicDAO();

		daoT.create(te1);
		daoT.create(te2);

		daoTo.create(to1);
		daoTo.create(to2);

		daoS.create(st1);
		daoS.create(st2);

		daoC.create(co1);
		daoC.create(co2);

		daoCS.create(cs1);
		daoCS.create(cs2);
		daoCS.create(cs3);

		daoG.create(gr1);
		daoG.create(gr2);
		daoG.create(gr3);
		daoG.create(gr4);
		daoG.create(gr5);
	}

}
