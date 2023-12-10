import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // get data to data structures
        DataReader dataReader = new DataReader();
        List<Student> studentsList = dataReader.fetchStudentsAsArrayList("students.txt");

        // !!burayı direkt array olarak okusak daha iyi olabilir castingsiz arama yapmak icin getnextprimenum'da
        List<Integer> primeNumbersList = dataReader.fetchNumbersAsArrayList("primeNumbers.txt");
        Object[] primeNumbersArray = primeNumbersList.toArray();

        /*  CHAINING
            fetch & allocate students to table
        */
        long startAllocating = System.currentTimeMillis();
        ChainingTableClass chainingTable = new ChainingTableClass(4001);

        // fetch the students from the ArrayList,
        // then hash and allocate them into the table
        for(Student student: studentsList){
            chainingTable.add(student);
        }
        long finishAllocating = System.currentTimeMillis();

        long allocateDuration = -startAllocating+finishAllocating;
        System.out.println("Part-3 a)Fetch students from arraylist & allocate to table duration: " + allocateDuration + " ms");
        //chainingTable.displayChaining(); // to control/display chaining


        /*  CHAINING
            search random 100 students
        */
        long startRandomSearch = System.currentTimeMillis();

        // search and bring hundred random students from the table.
        List<Student> randomStudentList = bringRandomStudentsFromTable(100, chainingTable, studentsList);

        long finishRandomSearch = System.currentTimeMillis();
        long randomSearchDuration = finishRandomSearch-startRandomSearch;

        System.out.println("Part-3 b)Search & bring random students duration: " + randomSearchDuration + " ms");
        //System.out.println(randomStudentList);


        /*  OPEN ADDRESSING
            fetch & allocate students to table
*/
        long startAllocatingOpenAddressing = System.currentTimeMillis();
        OpenAdressingClass openAdressingTable = new OpenAdressingClass(401, primeNumbersArray);

        // fetch the students from the ArrayList,
        // then hash and allocate them into the table
        for(Student student: studentsList){
            openAdressingTable.add(student);
        }
        long finishAllocatingOpenAddressing = System.currentTimeMillis();

        long allocateOpenAddressingDuration = -startAllocatingOpenAddressing+finishAllocatingOpenAddressing;
        System.out.println("Part-4 a)Fetch students from arraylist & allocate to table duration: " + allocateOpenAddressingDuration + " ms");

    }

    private static List<Student> bringRandomStudentsFromTable(int i, ChainingTableClass chainingTable, List<Student> studentsList) {
        List<Student> randomStudentList = new ArrayList<>();
        Student randomStudent = new Student("null","null",0,"null","null");

        for (int count = 0; count < i; count++) {
            // loop until find a valid student
            do{
                int randomStudentID = getRandomStudentID(studentsList);
                randomStudent = chainingTable.find(randomStudentID);
            } while(randomStudent == null);

            // add to the list
            randomStudentList.add(randomStudent);
        }
        return randomStudentList;
    }

    private static int getRandomStudentID(List<Student> studentsList) {
        Random rand = new Random();
        int random = rand.nextInt(81000);

        return studentsList.get(random).getID();
    }

}
