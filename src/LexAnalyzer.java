import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LexAnalyzer {
    /* Character classes */
    public static final int LETTER = 0;
    public static final int DIGIT = 1;
    public static final int UNKNOWN = 99;

    /*Token Codes */
    public static final int INT_LIT = 10;
    public static final int IDENT = 11;
    public static final int ASSIGN_OP = 20;
    public static final int ADD_OP = 21;
    public static final int SUB_OP = 22;
    public static final int MULT_OP = 23;
    public static final int DIV_OP = 24;
    public static final int LEFT_PAREN = 25;
    public static final int RIGHT_PAREN = 26;



    //lexLen will just be lexeme.size()
    int token;
    int nextToken;

    char nextChar;
    List<Character> charArr = new ArrayList<Character>();
    List<Character> lexeme = new ArrayList<Character>();

    int charClass;


    LexAnalyzer(){
        String filePath = "inputFile.txt"; // path to the file
        try(FileReader reader = new FileReader(filePath)) {
            int character;
            while ((character = reader.read()) != -1){
                charArr.add((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR - cannot open front.in \n");
        }
    }
    void lex(char character) {
        if(character == ' '){
            return;
        } else {
            switch (charClass){
                case LETTER:
                    addChar();
                    getChar();
                    while (charClass == LETTER || charClass == DIGIT) {
                        addChar();
                        getChar();
                    }
                    nextToken = IDENT;
                    break;
                case DIGIT:
                    addChar();
                    getChar();
                    while (charClass == DIGIT){
                        addChar();
                        getChar();
                    }
                    nextToken = INT_LIT;
                    break;
                case UNKNOWN:
                    //lookup(nextChar());
                    getChar();
                    addChar();

            }
        }
    }

    void addChar() {
        if(lexeme.size() <= 98){
            lexeme.add(charArr.get(lexeme.size() + 1));
            // Should i make it = 0???
        } else{
            System.out.println("Error - lexeme is too long");
            lexeme.set(3190, 'l'); // should throw an error hopefully.
        }
    }

    void getChar(){
        if(Character.isLetter(lexeme.getLast())){
            charClass = LETTER;
        } else if (Character.isDigit(lexeme.getLast())) {
            charClass = DIGIT;
        }
        else {
            charClass = UNKNOWN;
        }
    }


    void run(){
        addChar();
        getChar();
        for (Character character : charArr) {
            lex(character);
        }
    }


}