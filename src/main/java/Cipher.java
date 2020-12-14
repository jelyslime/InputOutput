
import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Date: 12/14/2020 Time: 10:34 AM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
public class Cipher {
    private final String plainAlphabet = "абвгдежзийклмнопрстуфхцчшщъьюя";
    private String cipheredAlphabet;
    private String password;
    private final HashMap<Character, Character> plainToCiphered;
    private final HashMap<Character, Character> CipheredToPlain;

    public Cipher() {
        plainToCiphered = new HashMap<>();
        CipheredToPlain = new HashMap<>();
    }

    public boolean setPassword(File file) {
        String password = readFile(file);
        String passwordToLower = password.toLowerCase();
        StringBuilder remadePassword = new StringBuilder();
        for (int i = 0; i < passwordToLower.length(); i++) {
            char pos = passwordToLower.charAt(i);
            if (plainAlphabet.indexOf(pos) != -1) {
                remadePassword.append(pos);
            }
        }
        setPassword(remadePassword.toString());
        setCipheredAlphabet();
        setPlainAlphabetToCipheredMap();
        setCipheredToPlainMap();

        return true;
    }


    public String cipher(File source, File destination) {
        String text = readFile(source);
        StringBuilder cipherText = new StringBuilder(text);

        for (int i = 0; i < cipherText.length(); i++) {
            char tmpValue = cipherText.charAt(i);
            if (getPlainToCiphered().containsKey(tmpValue)) {
                cipherText.setCharAt(i, getPlainToCiphered().get(tmpValue));
            }
        }

        writeFile(destination, cipherText.toString());
        return cipherText.toString();
    }


    public String decipher(File source, File destination) {
        String text = readFile(source);
        StringBuilder decipherText = new StringBuilder(text);

        for (int i = 0; i < decipherText.length(); i++) {
            char tmpValue = decipherText.charAt(i);
            if (getCipheredToPlain().containsKey(tmpValue)) {
                decipherText.setCharAt(i, getCipheredToPlain().get(tmpValue));
            }
        }
        writeFile(destination, decipherText.toString());
        return decipherText.toString();
    }

    public String getPlainAlphabet() {
        return plainAlphabet;
    }

    protected String readFile(File file) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF_8))) {
            int i;
            while ((i = input.read()) != -1) {
                result.append(((char) i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    protected void writeFile(File file, String toWrite) {
        char[] text = toWrite.toCharArray();
        try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), UTF_8))) {
            output.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected HashMap<Character, Character> setPlainAlphabetToCipheredMap() {
        String PlainAlphabet = this.getPlainAlphabet();
        String cipheredAlphabet = getCipheredAlphabet();
        String plainToUpper = plainAlphabet.toUpperCase();
        String cipherToUpper = cipheredAlphabet.toUpperCase();

        for (int i = 0; i < PlainAlphabet.length(); i++) {
            char key = PlainAlphabet.charAt(i);
            char value = cipheredAlphabet.charAt(i);
            plainToCiphered.put(key, value);
            key = plainToUpper.charAt(i);
            value = cipherToUpper.charAt(i);
            plainToCiphered.put(key, value);
        }

        return plainToCiphered;
    }

    protected HashMap<Character, Character> setCipheredToPlainMap() {
        Set<Map.Entry<Character, Character>> entrySet = getPlainToCiphered().entrySet();
        for (Map.Entry<Character, Character> characterCharacterEntry : entrySet) {
            char key = characterCharacterEntry.getValue();
            char value = characterCharacterEntry.getKey();
            CipheredToPlain.put(key, value);
        }

        return CipheredToPlain;
    }

    protected String setCipheredAlphabet() {
        String plainAlphabet = getPlainAlphabet();
        String password = getPassword();

        StringBuilder result = new StringBuilder();
        result.append(password);
        for (int i = 0; i < plainAlphabet.length(); i++) {
            if (password.indexOf(plainAlphabet.charAt(i)) == -1) {
                char toAppend = plainAlphabet.charAt(i);
                result.append(toAppend);
            }
        }
        setCipheredAlphabet(result.toString());

        return result.toString();
    }

    protected String getCipheredAlphabet() {
        return cipheredAlphabet;
    }

    protected void setCipheredAlphabet(String cipheredAlphabet) {
        this.cipheredAlphabet = cipheredAlphabet;
    }

    protected String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected HashMap<Character, Character> getPlainToCiphered() {
        return plainToCiphered;
    }

    protected HashMap<Character, Character> getCipheredToPlain() {
        return CipheredToPlain;
    }
}
