import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
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

    public static final int NEWLINE = 27;


    int lexLen = 0;
    int index = 0;
    int charArrLen;
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
            charArrLen = charArr.size();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR - cannot open front.in \n");
        }
    }
    void lex() {
        lexeme.clear();
        getNonBlank();
        switch (charClass){
            case LETTER:
                addChar();
                getChar();
                while (charClass == LETTER || charClass == DIGIT) { //while loop causes it to go on forever unless you have semi colons at the end of every statement.
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
                lookup();
                getChar();
                if(nextChar != '\n') {
                    addChar();
                }
        }
        System.out.println("Next token is: " + nextToken + " The next lexeme is " + lexeme);

    }

    void addChar() {
        if(lexLen <= 98){
            lexeme.add(nextChar);
            lexLen++;// Do I need this???
            // Should i make it = 0???
        } else{
            System.out.println("Error - lexeme is too long");
            lexeme.set(3190, 'l'); // should throw an error hopefully.
        }
    }

    void getChar(){
        nextChar = charArr.get(index);
        index++;
        if(Character.isLetter(nextChar)){
            charClass = LETTER;
        } else if(Character.isDigit(nextChar)) {
            charClass = DIGIT;
        }
        else {
            charClass = UNKNOWN;
        }
    }

    void getNonBlank(){
        while(nextChar == ' '){
            getChar();
        }
    }


    int lookup(){
        switch(nextChar){
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            case '=':
                addChar();
                nextToken = ASSIGN_OP;
                break;
            case '\n':
                nextToken = NEWLINE;
        }
        return nextToken;
    }


    void run(){
        getChar();
        while(index < charArrLen){
            lex();
        }
    }


}
