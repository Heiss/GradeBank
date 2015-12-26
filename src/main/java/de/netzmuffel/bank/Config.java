/*
 *
 */
package de.netzmuffel.bank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.enumerator.Shutdown;

/**
 * The Class Config.
 */
public final class Config {

	/** The Constant SOFTWARE_NAME. */
	private static final String SOFTWARE_NAME = "GradeBank";

	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = "GradeBank";

	private static final String CFG_FOLDER = "./cfg/";

	/** The Constant CFG_FILE. */
	private static final String CFG_FILE = CFG_FOLDER + "config.properties";

	/** The Constant VERSION_FILE. */
	private static final String VERSION_FILE = CFG_FOLDER + "version.properties";

	/** The Constant logger. */
	public final static Logger logger = LoggerFactory.getLogger(App.class);

	public static final String SERVER_VERSION_FILE = "http://gradebank.netzmuffel.de/version.properties";

	public static final String SERVER_UPDATER_FILE = "http://gradebank.netzmuffel.de/GradeBank-Update.jar";

	public static final String UPDATE_PROGRAM_FILE = "./GradeBank-Update.jar";

	public static final String DATABASE_USER = "root";

	public static final String DATABASE_USERPWD = "admin";

	public static final String dbUrl = "jdbc:h2:file:" + CFG_FOLDER + "Database" + Config.getDatabaseName();

	/**
	 * Gets the cfg file name.
	 *
	 * @return the cfg file name
	 */
	public static File getCfgFile() {
		return new File(CFG_FILE);
	}

	/**
	 * Gets the database name.
	 *
	 * @return the database name
	 */
	public static String getDatabaseName() {
		return DATABASE_NAME;
	}

	/**
	 * Gets the software name.
	 *
	 * @return the software name
	 */
	public static String getSoftwareName() {
		return SOFTWARE_NAME;
	}

	public static File getVersionFile() {
		return new File(VERSION_FILE);
	}

	public static File getServerVersionFile() {
		return new File(SERVER_VERSION_FILE);
	}

	public static int getVersion() {
		return 0;
	}

	public static int getLatestVersion() {
		try {
			URL url = new URL(Config.SERVER_VERSION_FILE);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

			String line = br.readLine();
			if (!line.equals("")) {
				return Integer.valueOf(line);
			}
		} catch (MalformedURLException e) {
			Error.CONFIGURATION_WRONG_URL_WEBFILE.showDialog();
			e.printStackTrace();
		} catch (IOException e) {
			Error.CONFIGURATION_CANNOT_READ_VERSION_FROM_WEBFILE.showDialog(e);
		}

		return 0;
	}

	public static int getLocalVersion() {
		if (!Config.getVersionFile().exists()) {
			resetVersionFile();
		}

		try {
			@SuppressWarnings("resource")
			Scanner in = new Scanner(new FileReader(Config.getVersionFile()));

			if (in.hasNextInt()) {
				return in.nextInt();
			}
		} catch (FileNotFoundException e) {
			Error.CONFIGURATION_CANNOT_FIND_OFFLINEFILE.showDialog(e);
		} catch (InputMismatchException e) {
			Error.CONFIGURATION_OFFLINEVERSION_NOT_INTEGER.showDialog(e);
		} catch (NoSuchElementException e) {
			Error.CONFIGURATION_OFFLINEVERSION_NO_INTEGER_FOUND.showDialog(e);
		}

		resetVersionFile();
		return 0;
	}

	private static void resetVersionFile() {
		try {
			FileWriter fw = new FileWriter(Config.getVersionFile());
			fw.write("0");

			if (fw != null) {
				fw.close();
			}
		} catch (FileNotFoundException e) {
			Error.CONFIGURATION_CANNOT_FIND_OFFLINEFILE.showDialog(e);
		} catch (IOException e) {
			Error.CONFIGURATION_CANNOT_WRITE_IN_VERSIONFILE.showDialog(e);
		}
	}

	public static void shutdown(Shutdown kill, Database db) {
		if (db != null) {
			try {
				db.shutdown();
			} catch (SQLException e) {
				Error.DATABASE_CANNOT_CLOSE_CONNECTION.showDialog(e);
			}
		}

		switch (kill) {
			case Okay:
				Config.logger.info("Program closed without a given error.");
				break;
			case Error:
				Config.logger.info("There was an error.");
				break;
			case Restart:
				Config.logger.info("Program closed for restart, maybe an update.");
				break;
			default:
				break;
		}

		System.exit(kill.ordinal());
	}

	public static String getCfgFolder() {
		return CFG_FOLDER;
	}

	public static String getDatabaseFilePath() {
		return Config.getCfgFolder() + "Database" + Config.getDatabaseName() + ".mv.db";
	}

}
