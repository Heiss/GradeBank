/*
 *
 */
package de.netzmuffel.bank;

import java.io.File;
import java.sql.SQLException;

import org.h2.tools.ChangeFileEncryption;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import de.netzmuffel.bank.model.Course;
import de.netzmuffel.bank.model.CourseStudent;
import de.netzmuffel.bank.model.Grade;
import de.netzmuffel.bank.model.Student;
import de.netzmuffel.bank.model.Teacher;
import de.netzmuffel.bank.model.Topic;
import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.enumerator.Property;

/**
 * The Class Database.
 */
final public class Database {

	/** The conn. */
	private JdbcConnectionSource conn;

	/** The course dao. */
	private Dao<Course, Integer> courseDAO;

	/** The student dao. */
	private Dao<Student, Integer> studentDAO;

	/** The teacher dao. */
	private Dao<Teacher, Integer> teacherDAO;

	/** The grade dao. */
	private Dao<Grade, Integer> gradeDao;

	/** The course student dao. */
	private Dao<CourseStudent, Integer> courseStudentDao;

	/** The topic dao. */
	private Dao<Topic, Integer> topicDao;

	/**
	 * Instantiates a new database.
	 *
	 * @param jdbcConnectionString
	 *            the jdbc connection string
	 * @throws SQLException
	 *             the SQL exception
	 */
	public Database(String FilePWD) throws SQLException {
		this.conn = new JdbcConnectionSource(Config.dbUrl + ";CIPHER=AES", Config.DATABASE_USER,
				FilePWD + " " + Config.DATABASE_USERPWD);
		this.createDAOandTables();
	}

	public Database() throws SQLException {
		this.conn = new JdbcConnectionSource(Config.dbUrl, Config.DATABASE_USER, Config.DATABASE_USERPWD);
		this.createDAOandTables();
	}

	public Database(JdbcConnectionSource conn) throws SQLException {
		this.conn = conn;
		this.conn.initialize();
		this.createDAOandTables();
	}

	private void createDAOandTables() throws SQLException {
		this.courseDAO = DaoManager.createDao(this.conn, Course.class);
		this.studentDAO = DaoManager.createDao(this.conn, Student.class);
		this.teacherDAO = DaoManager.createDao(this.conn, Teacher.class);
		this.gradeDao = DaoManager.createDao(this.conn, Grade.class);
		this.courseStudentDao = DaoManager.createDao(this.conn, CourseStudent.class);
		this.topicDao = DaoManager.createDao(this.conn, Topic.class);

		TableUtils.createTableIfNotExists(this.conn, Course.class);
		TableUtils.createTableIfNotExists(this.conn, Student.class);
		TableUtils.createTableIfNotExists(this.conn, Teacher.class);
		TableUtils.createTableIfNotExists(this.conn, Grade.class);
		TableUtils.createTableIfNotExists(this.conn, CourseStudent.class);
		TableUtils.createTableIfNotExists(this.conn, Topic.class);
	}

	/**
	 * Gets the student dao.
	 *
	 * @return the student dao
	 */
	public Dao<Student, Integer> getStudentDAO() {
		return this.studentDAO;
	}

	/**
	 * Gets the teacher dao.
	 *
	 * @return the teacher dao
	 */
	public Dao<Teacher, Integer> getTeacherDAO() {
		return this.teacherDAO;
	}

	/**
	 * Gets the course dao.
	 *
	 * @return the course dao
	 */
	public Dao<Course, Integer> getCourseDAO() {
		return this.courseDAO;
	}

	/**
	 * Gets the grade dao.
	 *
	 * @return the grade dao
	 */
	public Dao<Grade, Integer> getGradeDAO() {
		return this.gradeDao;
	}

	/**
	 * Gets the course student dao.
	 *
	 * @return the course student dao
	 */
	public Dao<CourseStudent, Integer> getCourseStudentDAO() {
		return this.courseStudentDao;
	}

	/**
	 * Gets the topic dao.
	 *
	 * @return the topic dao
	 */
	public Dao<Topic, Integer> getTopicDAO() {
		return this.topicDao;
	}

	/**
	 * Shutdown.
	 *
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void shutdown() throws SQLException {
		this.conn.close();
	}

	/**
	 * Gets the conn.
	 *
	 * @return the conn
	 */
	public JdbcConnectionSource getConn() {
		return this.conn;
	}

	public void EncryptDatabase(String pwd) throws SQLException {
		org.h2.Driver.load();

		String[] args = new String[] { "-dir", Config.getCfgFolder(), "-db", "Database" + Config.getDatabaseName(),
				"-cipher", "AES", "-encrypt", pwd };
		ChangeFileEncryption.main(args);
	}

	public void DecryptDatabase(String pwd) throws SQLException {
		org.h2.Driver.load();

		String[] args = new String[] { "-dir", Config.getCfgFolder(), "-db", "Database" + Config.getDatabaseName(),
				"-cipher", "AES", "-decrypt", pwd };
		ChangeFileEncryption.main(args);
	}

	public boolean deleteDatabaseFile() {
		try {
			this.shutdown();
		} catch (SQLException e) {
			Error.DATABASE_CANNOT_CLOSE_CONNECTION.showDialog(e);
		}

		File dbFile = new File(Config.getDatabaseFilePath());
		if (dbFile.isFile() && dbFile.exists() && dbFile.delete()) {
			PropertiesHelper.setProperty(Property.ENCRYPTENABLED, false);
			return true;
		} else {
			System.out.println(dbFile.getAbsolutePath());
			Error.DATABASE_FILE_NOT_FOUND.showDialog();
			return false;
		}
	}

	/**
	 * Initialize and opens the connection to the database
	 *
	 * @throws SQLException
	 */
	public void initializeConnection() throws SQLException {
		this.getConn().initialize();
	}

	/**
	 * Create a new or initialize a connection to a database file
	 *
	 * @return Database
	 * @throws SQLException
	 */

	public static Database createNewDatabaseConnection() throws SQLException {
		return new Database();
	}

}
