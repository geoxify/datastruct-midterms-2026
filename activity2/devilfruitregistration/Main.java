package activity2.devilfruitregistration;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        String fileName = "activity2/devilfruit.txt";
        File file = new File(fileName);

        Scanner fileReader = new Scanner(file);
        String aNames[] = new String[50];
        String aDevilFruit[] = new String[50];
        String aType[] = new String[50];
        String aStatus[] = new String[50];
        int ctr = 0;
        while (fileReader.hasNextLine()) {
            aNames[ctr] = fileReader.nextLine();
            aDevilFruit[ctr] = fileReader.nextLine();
            aType[ctr] = fileReader.nextLine();
            aStatus[ctr] = fileReader.nextLine();
            ctr++;
        }

        for (int a = 0; a < ctr; a++) { // print the array
            System.out.printf("%d. Name: %s\n", a + 1, aNames[a]);
            System.out.printf("""
                         Devil Fruit: %s
                         Type: %s
                         Status: %s
                    """, aDevilFruit[a], aType[a], aStatus[a]);
        }

        fileReader.close();

    }
}