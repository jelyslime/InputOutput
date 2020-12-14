import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Date: 12/14/2020 Time: 11:32 AM
 * <p>
 *
 *
 * @author Vladislav_Zlatanov
 */


public class Main {
    public static void main(String[] args) {
       // menu();

//        //Pre entered paths.
//        File passwordFile = Paths.get("src/main/resources/password.txt").toFile();
//        File source = Paths.get("src/main/resources/text.txt").toFile();
//        File destinationToCypher = Paths.get("src/main/resources/textDestination.txt").toFile();
//        File destinationToDeCypher = Paths.get("src/main/resources/textDestinationDeCypher.txt").toFile();
//
//        Cipher cypher = new Cipher();
//        cypher.setPassword(passwordFile);
//        cypher.cipher(source,destinationToCypher);
//        cypher.decipher(destinationToCypher,destinationToDeCypher);
    }

    public static void menu(){
        Scanner input = new Scanner(System.in);
        System.out.println("Epam's Caesar cipher.");
        System.out.println("This cypher works only with bulgarian alphabet and UTF-8 encryption");
        System.out.println("Enter your password directory.");

        String password = input.next();
        File passwordFile = Paths.get(password).toFile();
        Cipher cipher = new Cipher();
        cipher.setPassword(passwordFile);
        boolean choose=true;
        while (choose){
            System.out.println("1.Cipher text.");
            System.out.println("2.Decipher text");
            System.out.println("3.Exit");
            int level = input.nextInt();
            if (level == 1){
                System.out.println("Enter source directory.");
                String source = input.next();
                System.out.println("Enter destination directory.");
                String destination = input.next();
                File sourceFile = Paths.get(source).toFile();
                File destinationFile = Paths.get(destination).toFile();
                cipher.cipher(sourceFile,destinationFile);
            } else if(level == 2) {
                System.out.println("Enter source directory.");
                String source = input.next();
                System.out.println("Enter destination directory.");
                String destination = input.next();
                File sourceFile = Paths.get(source).toFile();
                File destinationFile = Paths.get(destination).toFile();
                cipher.cipher(sourceFile,destinationFile);
            } else {
                choose = false;
            }
        }
    }
}
