import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.Socket;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Input utility class for reading various data types.
 */
public final class In {

    private static final String ENCODING = "UTF-8";
    private static final Locale DEFAULT_LOCALE = Locale.US;

    private static final Pattern SPACING = Pattern.compile("\\p{javaWhitespace}+");
    private static final Pattern EMPTY_STR = Pattern.compile("");
    private static final Pattern ALL_CONTENT = Pattern.compile("\\A");

    private Scanner reader;

    public In() {
        reader = new Scanner(new BufferedInputStream(System.in), ENCODING);
        reader.useLocale(DEFAULT_LOCALE);
    }

    public In(Socket socket) {
        if (socket == null) throw new IllegalArgumentException("socket is null");
        try {
            InputStream stream = socket.getInputStream();
            reader = new Scanner(new BufferedInputStream(stream), ENCODING);
            reader.useLocale(DEFAULT_LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + socket, ioe);
        }
    }

    public In(URL url) {
        if (url == null) throw new IllegalArgumentException("url is null");
        try {
            URLConnection connection = url.openConnection();
            InputStream stream = connection.getInputStream();
            reader = new Scanner(new BufferedInputStream(stream), ENCODING);
            reader.useLocale(DEFAULT_LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + url, ioe);
        }
    }

    public In(File file) {
        if (file == null) throw new IllegalArgumentException("file is null");
        try {
            FileInputStream fileStream = new FileInputStream(file);
            reader = new Scanner(new BufferedInputStream(fileStream), ENCODING);
            reader.useLocale(DEFAULT_LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + file, ioe);
        }
    }

    public In(String source) {
        if (source == null) throw new IllegalArgumentException("argument is null");
        if (source.length() == 0) throw new IllegalArgumentException("argument is empty");
        try {
            File targetFile = new File(source);
            if (targetFile.exists()) {
                FileInputStream fis = new FileInputStream(targetFile);
                reader = new Scanner(new BufferedInputStream(fis), ENCODING);
                reader.useLocale(DEFAULT_LOCALE);
                return;
            }

            URL address = getClass().getResource(source);
            if (address == null) {
                address = getClass().getClassLoader().getResource(source);
            }
            if (address == null) {
                address = new URL(source);
            }

            URLConnection conn = address.openConnection();
            InputStream is = conn.getInputStream();
            reader = new Scanner(new BufferedInputStream(is), ENCODING);
            reader.useLocale(DEFAULT_LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + source, ioe);
        }
    }

    public In(Scanner scanner) {
        if (scanner == null) throw new IllegalArgumentException("scanner is null");
        this.reader = scanner;
    }

    public boolean exists()  {
        return reader != null;
    }

    public boolean isEmpty() {
        return !reader.hasNext();
    }

    public boolean hasNextLine() {
        return reader.hasNextLine();
    }

    public boolean hasNextChar() {
        reader.useDelimiter(EMPTY_STR);
        boolean found = reader.hasNext();
        reader.useDelimiter(SPACING);
        return found;
    }

    public String readLine() {
        try {
            return reader.nextLine();
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    public char readChar() {
        reader.useDelimiter(EMPTY_STR);
        try {
            String token = reader.next();
            reader.useDelimiter(SPACING);
            return token.charAt(0);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No more tokens available");
        }
    }

    public String readAll() {
        if (!reader.hasNextLine()) return "";
        String content = reader.useDelimiter(ALL_CONTENT).next();
        reader.useDelimiter(SPACING);
        return content;
    }

    public String readString() {
        try {
            return reader.next();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No more tokens available");
        }
    }

    public int readInt() {
        try {
            return reader.nextInt();
        }
        catch (InputMismatchException e) {
            String s = reader.next();
            throw new InputMismatchException("Invalid int: " + s);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No more tokens available");
        }
    }

    public double readDouble() {
        try {
            return reader.nextDouble();
        }
        catch (InputMismatchException e) {
            String s = reader.next();
            throw new InputMismatchException("Invalid double: " + s);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No more tokens available");
        }
    }

    public float readFloat() {
        try {
            return reader.nextFloat();
        }
        catch (InputMismatchException e) {
            String s = reader.next();
            throw new InputMismatchException("Invalid float: " + s);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No more tokens available");
        }
    }

    public long readLong() {
        try {
            return reader.nextLong();
        }
        catch (InputMismatchException e) {
            String s = reader.next();
            throw new InputMismatchException("Invalid long: " + s);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No more tokens available");
        }
    }

    public short readShort() {
        try {
            return reader.nextShort();
        }
        catch (InputMismatchException e) {
            String s = reader.next();
            throw new InputMismatchException("Invalid short: " + s);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No more tokens available");
        }
    }

    public byte readByte() {
        try {
            return reader.nextByte();
        }
        catch (InputMismatchException e) {
            String s = reader.next();
            throw new InputMismatchException("Invalid byte: " + s);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No more tokens available");
        }
    }

    public boolean readBoolean() {
        try {
            String s = readString();
            if ("true".equalsIgnoreCase(s) || "1".equals(s)) return true;
            if ("false".equalsIgnoreCase(s) || "0".equals(s)) return false;
            throw new InputMismatchException("Invalid boolean: " + s);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("No more tokens available");
        }
    }

    public String[] readAllStrings() {
        String[] parts = SPACING.split(readAll());
        if (parts.length == 0 || parts[0].length() > 0) return parts;
        String[] result = new String[parts.length - 1];
        System.arraycopy(parts, 1, result, 0, parts.length - 1);
        return result;
    }

    public String[] readAllLines() {
        ArrayList<String> buffer = new ArrayList<String>();
        while (hasNextLine()) buffer.add(readLine());
        return buffer.toArray(new String[0]);
    }

    public int[] readAllInts() {
        String[] data = readAllStrings();
        int[] results = new int[data.length];
        for (int i = 0; i < data.length; i++)
            results[i] = Integer.parseInt(data[i]);
        return results;
    }

    public long[] readAllLongs() {
        String[] data = readAllStrings();
        long[] results = new long[data.length];
        for (int i = 0; i < data.length; i++)
            results[i] = Long.parseLong(data[i]);
        return results;
    }

    public double[] readAllDoubles() {
        String[] data = readAllStrings();
        double[] results = new double[data.length];
        for (int i = 0; i < data.length; i++)
            results[i] = Double.parseDouble(data[i]);
        return results;
    }

    public void close() {
        reader.close();
    }

    @Deprecated
    public static int[] readInts(String name) {
        return new In(name).readAllInts();
    }

    @Deprecated
    public static double[] readDoubles(String name) {
        return new In(name).readAllDoubles();
    }

    @Deprecated
    public static String[] readStrings(String name) {
        return new In(name).readAllStrings();
    }

    public static void main(String[] args) {
        // Logic remains for testing
    }
}