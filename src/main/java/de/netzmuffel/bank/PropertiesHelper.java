package de.netzmuffel.bank;

import java.util.Locale;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.configuration.PropertiesConfiguration;

import de.netzmuffel.bank.model.enumerator.Error;
import de.netzmuffel.bank.model.enumerator.Property;

public class PropertiesHelper {

	public static void checkPropertiesInFile() {
		checkLanguageProperty();
		checkEncryptionProperty();
	}

	private static void checkEncryptionProperty() {
		try {
			PropertiesHelper.getPropertiesConfiguration().getBoolean(Property.ENCRYPTENABLED.toString());
		} catch (ConversionException e) {
			Error.APPLICATION_BOOLEAN_CONVERSION_EXCEPTION.showDialog();
			setProperty(Property.ENCRYPTENABLED, false);
		} catch (NoSuchElementException e1) {
			setProperty(Property.ENCRYPTENABLED, false);
		}
	}

	public static void checkLanguageProperty() {
		String loc = PropertiesHelper.getPropertiesConfiguration().getString(Property.LANGUAGE.toString());

		if (loc == null || loc.equals("")) {
			setProperty(Property.LANGUAGE, Locale.GERMAN.toString());
		}
	}

	public static Locale getLanguage() {
		checkLanguageProperty();
		return new Locale(PropertiesHelper.getPropertiesConfiguration().getString(Property.LANGUAGE.toString()));
	}

	public static boolean isEncryptionEnabled() {
		return PropertiesHelper.getPropertiesConfiguration().getBoolean(Property.ENCRYPTENABLED.toString());
	}

	/**
	 * Gets the properties configuration.
	 *
	 * @return the properties configuration
	 */
	public static PropertiesConfiguration getPropertiesConfiguration() {
		try {
			return new PropertiesConfiguration(Config.getCfgFile());
		} catch (ConfigurationException e) {
			Error.CONFIGURATION_CANNOT_READ_CONFIGFILE.showDialog(e);
			System.exit(1);
		}
		return new PropertiesConfiguration();

	}

	public static void setProperty(Property p, Object b) {
		PropertiesConfiguration cfg = PropertiesHelper.getPropertiesConfiguration();
		cfg.setProperty(p.toString(), b);

		try {
			cfg.save();
		} catch (ConfigurationException e) {
			Error.CONFIGURATION_SAVE_THE_PROPERTY.showDialog(e);
		}
	}

}
