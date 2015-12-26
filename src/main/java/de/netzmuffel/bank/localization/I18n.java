package de.netzmuffel.bank.localization;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import de.netzmuffel.bank.exception.EmptyResourceException;

/**
 * From: https://blog.codecentric.de/2012/01/internationalisierung-mit-java-
 * resourcebundle-und-kompilierabhangigkeiten/ Entry-Point for Messages.
 * Provides access to ResourceBundles.<br>
 * <br>
 * Messages can be accessed by calling {@link Messages#get()}.nameOfMessage().
 * This class is using a Java Proxy class to resolve the messages.<br>
 * <br>
 * Enum-Translations can be accessed by calling
 * {@link Messages#getEnumText(Displayable)}. The enum has to implement
 * {@link Displayable} and there has to be an entry in the message.properties
 * with the following pattern: "Enum_" + Enum-Short-Classname + "_" + Enum-Value
 */
public final class I18n {

	/** The Constant PREFIX_ENUM. */
	protected static final String PREFIX_ENUM = "Enum_";

	/** The Constant BUNDLE_NAME. */
	private static final String BUNDLE_NAME = "language.App";

	/** The messages. */
	private static AppMessages messages = (AppMessages) Proxy.newProxyInstance(//
			AppMessages.class.getClassLoader(), //
			new Class[] { AppMessages.class }, //
			new MessageResolver());

	/**
	 * The Class MessageResolver.
	 */
	private static class MessageResolver implements InvocationHandler {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
		 * java.lang.reflect.Method, java.lang.Object[])
		 */
		public Object invoke(Object proxy, Method method, Object[] args) {
			return I18n.getString(method.getName(), args);
		}
	}

	/**
	 * Gets the.
	 *
	 * @return the app messages
	 */
	public static AppMessages get() {
		return messages;
	}

	/**
	 * Gets the enum text.
	 *
	 * @param enumValue
	 *            the enum value
	 * @return the enum text
	 */
	public static String getEnumText(Displayable enumValue) {
		String key = PREFIX_ENUM + enumValue.getClass().getSimpleName() + "_" + enumValue.toString();
		return getString(key, null);
	}

	/**
	 * Gets the string.
	 *
	 * @param key
	 *            the key
	 * @param args
	 *            the args
	 * @return the string
	 */
	private static String getString(String key, Object[] args) {
		Locale locale = Locale.getDefault();
		try {
			String message = ResourceBundle.getBundle(BUNDLE_NAME).getString(key);

			if (args != null) {
				MessageFormat formatter = new MessageFormat(message, locale);
				message = formatter.format(args);
			}

			if (message.equals("")) {
				throw new EmptyResourceException();
			}

			return message;
		} catch (MissingResourceException e) {
			return "Could not find !" + key + '!';
		} catch (EmptyResourceException e) {
			return "Missing description for !" + key + '!';
		}
	}
}
