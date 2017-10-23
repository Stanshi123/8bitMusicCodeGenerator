import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class EightBitMusicCodeGenerator {

    // Frequencies for notes in C4
    private static final short C4_frequency[] = {262, 277, 294, 311, 330, 349, 370, 392, 415, 440, 466, 494};
    private static PrintWriter pw = null;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Type the music name:");
        String musicName = scan.next();
        try {
            pw = new PrintWriter(musicName + ".c");
            init();
            spashScreen();
            scan.nextLine();
            while (true) {
                System.out.println("Type next note in the format of note duration note name (type q to quit)"
                        +"\n\tExample: 16 C#3 or 8 C4 or 4 Cb3"
                        +"\n\t16 -- 16th note\n\t 8 -- 8th note\n\t 4 -- quater note\n\t 2 -- half note\n\t 1 -- whole note");
                String inputLine = scan.nextLine();
                String [] inputs = inputLine.split(" ");
                if (inputs.length == 1)
                {
                    if (inputs[0].equals("q")) {
                        break;
                    } else {
                        System.out.println("Wrong input");
                        continue;
                    }
                } else if (inputs.length == 0) {
                    System.out.println("Wrong input");
                    continue;
                }
                int duration = Integer.parseInt(inputs[0]);
                printNote(duration,inputs[1]);

            }

        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error input!");;
        }

        pw.println("}");

        if (pw != null) {
            pw.close();
        }
        scan.close();
    }

    private static void init(){
        pw.println("#include <avr/io.h>\n");
        pw.println("#include <util/delay.h>");
        pw.println("#include \"lcd.h\"");
        pw.println("#include \"musicUtil.h\"");
        pw.println("// Frequencies for notes in C4 \n const unsigned short C4_frequency[12] =" +
                " { 262, 277, 294, 311, 330, 349, 370, 392, 415, 440, 466, 494};");
        pw.println("int main(void) {");
        pw.println("\t// Initialize DDR and PORT registers and LCD\n" +
                "\tDDRC = 0x00;\n" +
                "\tDDRB = 0x10;\n" +
                "\tPORTC |= (1 << PC1) | (1 << PC5);\n" +
                "\tlcd_init();");
    }

    private static void spashScreen() {
        pw.println("\tlcd_writecommand(1);");
        pw.println("\tlcd_moveto(0,0);");
        pw.println("\tlcd_stringout(\"8888888888888888\");");
        pw.println("\tlcd_moveto(1,0);");
        pw.println("\tlcd_stringout(\"8888888888888888\");");
        pw.println("\t _delay_ms(1000);");
        pw.println("\tlcd_writecommand(1);");
    }

    private static void printNote(int duration, String noteName) throws Exception {
        switch (duration) {
            case 1:
                printWholeNote(noteName);
                return;
            case 2:
                printHalfNote(noteName);
                return;
            case 4:
                printQuarterNote(noteName);
                return;
            case 8:
                printEighthNote(noteName);
                return;
            case 16:
                printSixteenthNote(noteName);
                return;
            default:
                throw new Exception();

        }
    }

    private static void printWholeNote(String noteName) throws Exception{
        int frequency = getNoteFrequency(noteName);
        pw.println("\tplay_whole_note(" + frequency + ");");
    }

    private static void printHalfNote(String noteName) throws Exception {
        int frequency = getNoteFrequency(noteName);
        pw.println("\tplay_Half_note(" + frequency + ");");
    }

    private static void printQuarterNote(String noteName) throws Exception{
        int frequency = getNoteFrequency(noteName);
        pw.println("\tplay_Quarter_note(" + frequency + ");");
    }

    private static void printEighthNote(String noteName) throws Exception {
        int frequency = getNoteFrequency(noteName);
        pw.println("\tplay_EighthNote_note(" + frequency + ");");
    }

    private static void printSixteenthNote(String noteName) throws Exception {
        int frequency = getNoteFrequency(noteName);
        pw.println("\tplay_Sixteenth_note(" + frequency + ");");
    }

    private static int getNoteFrequency(String noteName) throws Exception {
        char name = noteName.charAt(0);
        int flag = 0;
        int high;
        if (noteName.charAt(1) == 'b') {
            flag = -1;
            high = Character.getNumericValue(noteName.charAt(2));
        } else if (noteName.charAt(1) == '#') {
            flag = 1;
            high = Character.getNumericValue(noteName.charAt(2));
        } else {
            high = Character.getNumericValue(noteName.charAt(1));
        }
        double multiplier = 1;
        if (high > 8) {
            throw new Exception();
        } else if (high >= 4) {
            multiplier = Math.pow(2,high-4);
        } else if (high > 0) {
            multiplier = Math.pow(2,-4+high);
        }
        return getNoteFrequencyHelper(flag,multiplier,name);
    }

    private static int getNoteFrequencyHelper(int flag, double multiplier, char name) throws Exception {
        switch (name) {
            case 'D':
                return (int) Math.floor(C4_frequency[roundTo12(flag,2)] * multiplier);
            case 'E':
                return (int) Math.floor(C4_frequency[roundTo12(flag,4)] * multiplier);
            case 'F':
                return (int) Math.floor(C4_frequency[roundTo12(flag,5)] * multiplier);
            case 'G':
                return (int) Math.floor(C4_frequency[roundTo12(flag,7)] * multiplier);
            case 'A':
                return (int) Math.floor(C4_frequency[roundTo12(flag,9)] * multiplier);
            case 'B':
                return (int) Math.floor(C4_frequency[roundTo12(flag,11)] * multiplier);
            case 'C':
                return (int) Math.floor(C4_frequency[roundTo12(flag,0)] * multiplier);
            default:
                throw new Exception();
        }
    }

    private static int roundTo12(int flag,int index) {
        if (flag + index > 11) {
            return 0;
        } else if (flag + index < 0) {
            return 11;
        } else {
            return index+flag;
        }
    }
}

