package de.netzmuffel.bank.model;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.h2.tools.DeleteDbFiles;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;

import de.netzmuffel.bank.Database;
import de.netzmuffel.bank.testDataCreator;

public class TestCourse {
	final static Logger logger = LoggerFactory.getLogger(TestCourse.class);

	private static Database db = null;
	private static testDataCreator tdc = null;

	private static Dao<Course, Integer> daoCourse = null;
	private static Dao<Student, Integer> daoStudent = null;
	private static Dao<Teacher, Integer> daoTeacher = null;
	private static Dao<CourseStudent, Integer> daoCourseStudent = null;
	private static Dao<Grade, Integer> daoGrade = null;

	@BeforeClass
	public static void setUp() {
		// Delete the testDB, so the tests running on a fresh new test database
		DeleteDbFiles.execute("./", "testDB", false);
		System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "warning");
		tdc = new testDataCreator();
		try {
			db = new Database();
			tdc.exec(db);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}

		daoCourse = db.getCourseDAO();
		daoTeacher = db.getTeacherDAO();
		daoStudent = db.getStudentDAO();
		daoCourseStudent = db.getCourseStudentDAO();
		daoGrade = db.getGradeDAO();
	}

	@Before
	public void beforeTest() {
		try {
			// clear all tables and restore the test datas, so every test has
			// the same conditions
			TableUtils.clearTable(db.getConn(), CourseStudent.class);
			TableUtils.clearTable(db.getConn(), Course.class);
			TableUtils.clearTable(db.getConn(), Teacher.class);
			TableUtils.clearTable(db.getConn(), Student.class);
			TableUtils.clearTable(db.getConn(), Grade.class);
			tdc.exec(db);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	@After
	public void afterTest() {

	}

	@AfterClass
	public static void finishTests() {
		try {
			db.shutdown();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testToString() {
		try {
			for (Course course : daoCourse.queryForAll()) {
				logger.info(course.toString());
			}
			for (Grade grade : daoGrade.queryForAll()) {
				logger.info(grade.toString());
			}
			for (Student student : daoStudent.queryForAll()) {
				logger.info(student.toString());
			}
			for (Teacher teacher : daoTeacher.queryForAll()) {
				logger.info(teacher.toString());
			}
			for (CourseStudent courseStudent : daoCourseStudent.queryForAll()) {
				logger.info(courseStudent.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGrade1() {
		try {
			List<Grade> gr1 = daoGrade.queryForMatching(tdc.gr1);
			assertTrue(gr1.size() == 1);

			List<Grade> gr2 = daoGrade.queryForMatching(tdc.gr2);
			assertTrue(gr2.size() == 1);

			List<Grade> gr3 = daoGrade.queryForMatching(tdc.gr3);
			assertTrue(gr3.size() == 1);

			Grade gr4 = daoGrade.queryForAll().get(0);
			List<Grade> grList1 = daoGrade.queryForMatching(gr4);
			assertTrue(grList1.size() == 1);
			assertTrue(grList1.get(0).equals(gr4));

			Grade gr5 = daoGrade.queryForAll().get(2);
			List<Grade> grList2 = daoGrade.queryForMatching(gr5);
			assertTrue(grList2.get(0).equals(gr5));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveGrade1() {
		try {
			assertTrue(daoGrade.delete(tdc.gr1) == 1);
			assertTrue(daoGrade.delete(tdc.gr1) == 0);
			assertTrue(daoGrade.delete(tdc.gr2) == 1);
			assertTrue(daoGrade.delete(tdc.gr2) == 0);
			assertTrue(daoGrade.delete(tdc.gr3) == 1);
			assertTrue(daoGrade.delete(tdc.gr3) == 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddStudentToCourse1() {
		CourseStudent cs = new CourseStudent(tdc.co2, tdc.st1);

		try {
			// Check wether the courseStudent is already existing in this course
			QueryBuilder<CourseStudent, Integer> qb = daoCourseStudent.queryBuilder();
			qb.where().eq("course_id", tdc.co2).and().eq("student_id", tdc.st1);
			PreparedQuery<CourseStudent> pq = qb.prepare();
			List<CourseStudent> csList = daoCourseStudent.query(pq);

			assertTrue(csList.size() == 0);
			assertTrue(daoCourseStudent.create(cs) == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddStudent1() {
		Student st1 = new Student("Gammel", "Tom", "qeqwa@tasr.de", "018554123");
		Student st2 = new Student("Müller", "Mimi", "flasodi@asfe.com", "02557432102");

		try {
			assertTrue(daoStudent.create(st1) == 1);
			assertTrue(daoStudent.create(st2) == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
