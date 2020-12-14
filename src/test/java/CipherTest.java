import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doNothing;

/**
 * Date: 12/14/2020 Time: 11:53 AM
 * <p>
 *
 * @author Vladislav_Zlatanov
 */
@RunWith(MockitoJUnitRunner.class)
public class CipherTest {
    @Mock
    private File source;

    @Spy
    private Cipher cipher;

    private static final String plainAlphabet = "абвгдежзийклмнопрстуфхцчшщъьюя";

    private static final String password = "епам";

    private static final String cipheredAlphabet = "епамбвгджзийклнорстуфхцчшщъьюя";

    private static final String expectedDecipherText = "Главна Буква Проверка Текст за криптиране. спец знаци - !@№$%/.";

    private static final String expectedCipheredText = "Мйеале Пуиае Орнаврие Твист де иржотжрелв. совц длецж - !@№$%/.";

    private static HashMap<Character, Character> expectedPlainToCipher;
    private static HashMap<Character, Character> expectedCipherToPlain;

    @BeforeClass
    public static void setUp() {
        setExpectedPlainToCipher();
        setExpectedCipherToPlain();
    }


    @Test
    public void setCipheredAlphabet_Should_setCipheredAlphabet_When_PasswordAndPlainAlphabetArePresent() {
        //arrange
        doReturn(plainAlphabet).when(cipher).getPlainAlphabet();
        doReturn(password).when(cipher).getPassword();

        //act
        String actual = cipher.setCipheredAlphabet();

        //assert
        assertEquals(actual, cipheredAlphabet);
    }

    @Test
    public void setPlainAlphabetToCipheredMap_Should_CreateMap_When_AlphabetsArePresent() {
        //arrange
        doReturn(plainAlphabet).when(cipher).getPlainAlphabet();
        doReturn(cipheredAlphabet).when(cipher).getCipheredAlphabet();

        //act
        HashMap<Character, Character> result = cipher.setPlainAlphabetToCipheredMap();

        //assert
        assertEquals(result, expectedPlainToCipher);
    }

    @Test
    public void setCipheredToPlainMap_Should_CreateMap_When_AlphabetsArePresent() {
        //arrange
        doReturn(expectedPlainToCipher).when(cipher).getPlainToCiphered();

        //act
        HashMap<Character, Character> result = cipher.setCipheredToPlainMap();

        //assert
        assertEquals(result, expectedCipherToPlain);
    }

    @Test
    public void setPassword_Should_SetPassword_When_FileReturnsPassword() {
        //arrange
        doReturn(password).when(cipher).readFile(source);

        //act
        boolean result = cipher.setPassword(source);

        //assert
        assertTrue(result);
    }

    @Test
    public void cipher_Should_CipherNonCipheredText_When_SourceAndDestinationAreLegit() {
        //arrange
        doReturn(expectedDecipherText).when(cipher).readFile(source);
        doReturn(expectedPlainToCipher).when(cipher).getPlainToCiphered();
        doNothing().when(cipher).writeFile(source, expectedCipheredText);

        //act
        String actual = cipher.cipher(source, source);

        //assert
        assertEquals(expectedCipheredText, actual);
    }

    @Test
    public void decipher_Should_decipherCipheredText_When_SourceAndDestinationAreLegit() {
        //arrange
        doReturn(expectedCipheredText).when(cipher).readFile(source);
        doReturn(expectedCipherToPlain).when(cipher).getCipheredToPlain();
        doNothing().when(cipher).writeFile(source, expectedDecipherText);

        //act
        String actual = cipher.decipher(source, source);

        //assert
        assertEquals(expectedDecipherText, actual);
    }

    protected static void setExpectedCipherToPlain() {
        expectedCipherToPlain = new HashMap<>();
        Set<Map.Entry<Character, Character>> entrySet = expectedPlainToCipher.entrySet();
        for (Map.Entry<Character, Character> characterCharacterEntry : entrySet) {
            char key = characterCharacterEntry.getValue();
            char value = characterCharacterEntry.getKey();
            expectedCipherToPlain.put(key, value);
        }
    }

    protected static void setExpectedPlainToCipher() {
        expectedPlainToCipher = new HashMap<>();

        String plainToUpper = plainAlphabet.toUpperCase();
        String cypherToUpper = cipheredAlphabet.toUpperCase();

        for (int i = 0; i < plainAlphabet.length(); i++) {
            char key = plainAlphabet.charAt(i);
            char value = cipheredAlphabet.charAt(i);
            expectedPlainToCipher.put(key, value);
            key = plainToUpper.charAt(i);
            value = cypherToUpper.charAt(i);
            expectedPlainToCipher.put(key, value);
        }
    }
}
